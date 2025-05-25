package com.tiankong.mapper;

import com.tiankong.pojo.Question;
import com.tiankong.pojo.QuestionQueParam;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface QuestionMapper {
    void add(Question question);

    List<Question> list(QuestionQueParam questionQueParam);

    void deleteById(Integer id);

    void updateById(Question question);

    Question getQusetionById(Integer id);
}
