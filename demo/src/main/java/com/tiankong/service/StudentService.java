package com.tiankong.service;

import com.tiankong.pojo.PageResult;
import com.tiankong.pojo.Student;
import com.tiankong.pojo.StudentQuerParam;

import java.util.List;

public interface StudentService {
    void add(Student studen);

    PageResult<Student> page(StudentQuerParam studentQuerParam);

    void update(Student studen);

    int deleteById(String id);
}
