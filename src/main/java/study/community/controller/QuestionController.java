package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.community.dto.QuestionDTO;
import study.community.model.Question;
import study.community.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")int id ,
                           Model model){
        QuestionDTO questionDTO = questionService.getDTOById(id);
         model.addAttribute("question",questionDTO);
        return "question";
    }
}
