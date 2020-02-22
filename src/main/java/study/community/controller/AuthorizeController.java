package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dio.AccessTokenDTO;
import study.community.dio.GithubUser;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.provider.GithubProvider;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    String client_id;
    @Value("${github.client.secret}")
    String client_secret;
    @Value("${github.redirect.uri}")
    String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(client_id,client_secret,code,redirect_uri,state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUesr(accessToken);
        if(githubUser != null){
            User user = new User(UUID.randomUUID().toString(),
                    githubUser.getName(),
                    String.valueOf(state),
                    System.currentTimeMillis(),
                    System.currentTimeMillis());
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
