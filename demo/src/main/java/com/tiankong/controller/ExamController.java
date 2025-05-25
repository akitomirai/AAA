package com.tiankong.controller;

import com.tiankong.pojo.Exam;
import com.tiankong.utils.Result;
import com.tiankong.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/add")
    public Result addExam(@RequestBody Exam exam) {
        examService.addExam(exam);
        return Result.success();
    }

}
