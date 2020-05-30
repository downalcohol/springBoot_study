package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.community.dto.CommentCreateDTO;
import study.community.dto.CommentDTO;
import study.community.dto.QuestionDTO;
import study.community.service.CommentService;
import study.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id ,
                           Model model){
        QuestionDTO questionDTO = questionService.getDTOById(id);

        List<CommentDTO> comments = commentService.listByQuestionId((long)id);
        //累计阅读量
        questionService.incView(id);
         model.addAttribute("question",questionDTO);
         model.addAttribute("comments",comments);
        return "question";
    }
}
