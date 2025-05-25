package com.tiankong.service;

import com.tiankong.pojo.PageResult;
import com.tiankong.pojo.Question;
import com.tiankong.pojo.QuestionQueParam;

public interface QuestionService {
    void add(Question question);

    PageResult<Question> page(QuestionQueParam questionQueParam);

    void deleteById(Integer id);

    void updateById(Question question);

    Question getQuestionById(Integer id);
}
