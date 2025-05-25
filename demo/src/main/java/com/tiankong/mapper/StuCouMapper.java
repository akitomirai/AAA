package com.tiankong.mapper;

import com.tiankong.pojo.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StuCouMapper {

    void insertBatch(List<StudentCourse> list);

    void deleteByIds(List<String> list);
}
