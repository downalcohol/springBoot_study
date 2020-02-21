package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dio.AccessTokenDTO;
import study.community.dio.GithubUser;
import study.community.provider.GithubProvider;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(
                "Iv1.994bc79e4a21d0c5",
                "a8859f51af261047566ff5bb84e56c127e327cbf",
                code,
                "http://localhost:8080/callback",
                state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUesr(accessToken);
        return "index";
    }
}
