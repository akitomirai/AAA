package com.tiankong.service.imp;

import com.tiankong.mapper.ExamMapper;
import com.tiankong.pojo.Exam;
import com.tiankong.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public void addExam(Exam exam) {

    }
}
