package study.community.controller;

import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.QuestionDTO;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;
import study.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;



    @GetMapping("/")
    public String index(HttpServletRequest request,Model model){
        Cookie[] cookies = request.getCookies();
        //持久化用户登录
        //建立cookie，从数据库中通过token获取用户信息
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String  token = cookie.getValue();
                    User user = userMapper.findToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionDTOS = questionService.list();
        model.addAttribute("questions",questionDTOS);
        return "index";
    }

}
