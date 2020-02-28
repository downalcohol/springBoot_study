package study.community.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.QuestionDTO;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          HttpServletRequest request,
                          Model model) {
        Cookie[] cookies = request.getCookies();
        //持久化用户登录
        //建立cookie，从数据库中通过token获取用户信息
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        List<QuestionDTO> questionDTOS = questionService.listById(pageNum, user.getId());
        model.addAttribute("profileQuestions", questionDTOS);
        model.addAttribute("profilePageInfo", questionService.getPageInfo());
        return "profile";
    }
}
