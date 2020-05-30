package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@Controller
public class PublishController {


    @SuppressWarnings("all")
    @Autowired
    private QuestionService questionService;

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publishExit/{id}")
    public String edit(@PathVariable("id")int id,
                       Model model){
        QuestionDTO question = questionService.getDTOById( id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "questionId",required = false) String id,
                            HttpServletRequest request,
                            Model model) {
        long questionId = -1;
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title==null||title.equals("")){
            model.addAttribute("publishError", "标题不能为空");
            return "publish";
        }

        if (description==null||description.equals("")){
            model.addAttribute("publishError", "问题内容不能为空");
            return "publish";
        }

        if (tag==null||tag.equals("")){
            model.addAttribute("publishError", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("publishError", "用户未登录");
            return "publish";
        }
        if (!id.equals("")) questionId = Long.parseLong(id);
        questionService.insertOrUpdate(questionId,title,description,user.getId(),tag);
        return "redirect:/";

    }
}
