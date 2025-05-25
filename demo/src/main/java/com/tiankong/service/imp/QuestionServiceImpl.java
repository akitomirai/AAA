package com.tiankong.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiankong.mapper.QuestionMapper;
import com.tiankong.pojo.PageResult;
import com.tiankong.pojo.Question;
import com.tiankong.pojo.QuestionQueParam;
import com.tiankong.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void add(Question question) {
        questionMapper.add(question);
    }

    @Override
    public PageResult<Question> page(QuestionQueParam questionQueParam) {
        PageHelper.startPage(questionQueParam.getCurrent(), questionQueParam.getPageSize());
        List<Question> questions = questionMapper.list(questionQueParam);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        return new PageResult<Question>(pageInfo.getTotal(),questions);
    }

    @Override
    public void deleteById(Integer id) {
        questionMapper.deleteById(id);
    }

    @Override
    public void updateById(Question question) {
        questionMapper.updateById(question);
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.getQusetionById(id);
    }
}
