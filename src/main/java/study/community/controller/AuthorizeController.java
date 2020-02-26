package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.AccessTokenDTO;
import study.community.dto.GithubUser;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @SuppressWarnings("all")
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
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(client_id,client_secret,code,redirect_uri,state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUesr(accessToken);
        if(githubUser != null && githubUser.getId() != null){
            String token = UUID.randomUUID().toString();
            User user = new User(
                    String.valueOf(githubUser.getId()),
                    githubUser.getName(),
                    token,
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    githubUser.getAvatarUrl());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
