package com.tiankong.controller;

import com.tiankong.pojo.PageResult;
import com.tiankong.pojo.Question;
import com.tiankong.pojo.QuestionQueParam;
import com.tiankong.utils.Result;
import com.tiankong.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /* 添加题目 */
    @PostMapping("/add")
    public Result addQuestion(@RequestBody Question question) {
        log.info("添加题目:{}",question);
        questionService.add(question);
        return Result.success(question.getId());
    }

    /* 分页查询 */
    @GetMapping("/getQuestionByPage")
    public Result pageQuestion(QuestionQueParam questionQueParam) {
        log.info("分页查询:{}",questionQueParam);
        PageResult<Question> pageResult = questionService.page(questionQueParam);
        return Result.success(pageResult);
    }

    /* 按id删除题目 */
    @GetMapping("/deleteById")
    public Result deleteById(Integer id) {
        log.info("删除题目:{}",id);
        questionService.deleteById(id);
        return Result.success();
    }

    /* 按id更新题目 */
    @PostMapping("/update")
    public Result updateById(@RequestBody Question question) {
        log.info("修改题目:{}",question);
        questionService.updateById(question);
        return Result.success();
    }

    /* 按id查询题目 */
    @GetMapping("/getQuestionById")
    public Result getQuestionById(Integer id) {
        log.info("查找题目:{}",id);
        Question question = questionService.getQuestionById(id);
        return Result.success(question);
    }
}
