package study.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.dto.GithubUser;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.model.UserExample;

import java.util.List;

@Service
public class UserService {

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    public User insertOrUpdate(GithubUser githubUser, String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> dbUsers = userMapper.selectByExample(userExample);
        User user = new User();
        user.setName(githubUser.getName());
        user.setToken(token);
        user.setAvatarUrl(githubUser.getAvatarUrl());
        if (dbUsers.size() != 0) {
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(user,userExample);
        } else {
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }
        return user;
    }

}
