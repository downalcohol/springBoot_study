package study.community.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.dto.QuestionDTO;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @SuppressWarnings("all")
    @Autowired
    private QuestionMapper questionMapper;

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<QuestionDTO> questionDTOS= new ArrayList<>();
        Question[] questions = questionMapper.all();
        for (Question question:questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
