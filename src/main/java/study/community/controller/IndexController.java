package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.QuestionDTO;
import study.community.mapper.UserMapper;
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
    public String index(
            @RequestParam(value = "pageNum" ,defaultValue = "1")int pageNum,
            HttpServletRequest request,
            Model model){

        List<QuestionDTO> questionDTOS = questionService.list(pageNum);
        model.addAttribute("questions",questionDTOS);
        model.addAttribute("pageInfo",questionService.getPageInfo());
        return "index";
    }

}
