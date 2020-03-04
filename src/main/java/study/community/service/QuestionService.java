package study.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.CORBA.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.dto.QuestionDTO;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.QuestionExtMapper;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.QuestionExample;
import study.community.model.User;
import study.community.model.UserExample;

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

    @SuppressWarnings("all")
    @Autowired
    private QuestionExtMapper questionExtMapper;

    PageInfo pageInfo = new PageInfo<>();

    public void incView(int id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> list(int pageNum) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(pageNum, 10);
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());
        pageInfo = new PageInfo(questions);
        for (Question question : questions) {
            //System.out.println(question.getDescription());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }


    public PageInfo<Question> getPageInfo() {
        return pageInfo;
    }

    public List<QuestionDTO> listById(int pageNum, Integer id) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageHelper.startPage(pageNum, 10);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        pageInfo = new PageInfo(questions);
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;

    }

    public QuestionDTO getDTOById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void insertOrUpdate(int questionId, String title, String description, Integer id, String tag) {
        Question dbQuestion = questionMapper.selectByPrimaryKey(questionId);
        Question question = new Question();
        //title,description,System.currentTimeMillis(),System.currentTimeMillis(),id,tag
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        if (dbQuestion != null) {
            question.setGmtModified(question.getGmtCreate());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(questionId);
            int update = questionMapper.updateByExampleSelective(question, questionExample);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        } else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCreator(id);
            questionMapper.insert(question);
        }
    }
}
