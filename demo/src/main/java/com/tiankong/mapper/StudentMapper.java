package com.tiankong.mapper;

import com.tiankong.pojo.Student;
import com.tiankong.pojo.StudentQuerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    /* 添加外校学生 */
    void insert(Student studen);

    List<Student> list(StudentQuerParam studentQuerParam);

    int update(Student studen);

    int deleteById(String id);
}
