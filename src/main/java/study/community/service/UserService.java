package study.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.dto.GithubUser;
import study.community.mapper.UserMapper;
import study.community.model.User;

@Service
public class UserService {

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    public User insertOrUpdate(GithubUser githubUser, String token) {
        User dbUser = userMapper.findByGithubId(githubUser.getId());
        User user = new User();
        user.setName(githubUser.getName());
        user.setToken(token);
        user.setAvatarUrl(githubUser.getAvatarUrl());
        if (dbUser != null) {
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        } else {
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }
        return user;
    }

}
