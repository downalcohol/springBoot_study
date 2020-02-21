package study.community.provider;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import okhttp3.*;
import org.springframework.stereotype.Component;
import study.community.dio.AccessTokenDTO;
import study.community.dio.GithubUser;

/**
 * 存放git需要以及提供的信息
 */
@Component
public class GithubProvider {

    public GithubProvider() {
    }

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType );
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string().
                    split("&")[0].split("=")[1];
            return string;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUesr(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("https://api.github.com/user?access_token="+accessToken).
                build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string,GithubUser.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
