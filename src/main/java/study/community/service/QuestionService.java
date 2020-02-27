package study.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.config.PageHelperConfig;
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


    PageInfo pageInfo = new PageInfo<>();

    public List<QuestionDTO> list(int pageNum) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(pageNum, 10);
        List<Question> questions = questionMapper.all();
        pageInfo = new PageInfo(questions);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }


    public PageInfo<Question> getPageInfo() {
        return pageInfo;
    }

}
