package study.community.controller;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @SuppressWarnings("all")
    @Autowired
    private QuestionMapper questionMapper;

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title==null||title.equals("")){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }

        if (description==null||description.equals("")){
            model.addAttribute("error", "问题内容不能为空");
            return "publish";
        }

        if (tag==null||tag.equals("")){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        Cookie[] cookies = request.getCookies();
        //持久化用户登录
        //建立cookie，从数据库中通过token获取用户信息
        User user = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        if (user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question(
                title,
                description,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                user.getId(),
                tag
        );
        questionMapper.create(question);
        return "redirect:/";

    }
}
