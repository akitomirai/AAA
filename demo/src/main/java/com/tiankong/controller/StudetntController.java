package com.tiankong.controller;

import com.tiankong.pojo.PageResult;
import com.tiankong.utils.Result;
import com.tiankong.pojo.Student;
import com.tiankong.pojo.StudentQuerParam;
import com.tiankong.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/studenOutside")
public class StudetntController {

    @Autowired
    private StudentService studentService;

    /* 添加外校学生 */
    @PostMapping("/add")
    public Result addStuden(@RequestBody Student student) {
        log.info("添加外校学生:{}", student);
        String id = student.getId();
        try {
            studentService.add(student);
            return Result.success(id);
        } catch (Exception e){
            return Result.error("学号已存在");
        }
    }

    /* 分页查询 */
    @GetMapping("/getOutsideByPage")
    public Result page(StudentQuerParam studentQuerParam) {
        log.info("分页查询:{}", studentQuerParam);
        PageResult<Student> pageResult = studentService.page(studentQuerParam);
        return Result.success(pageResult);
    }

    /* 更新学生信息 */
    @PostMapping("/update")
    public Result update(@RequestBody Student studen) {
        log.info("修改学生信息:{}",studen);
        studentService.update(studen);
        return Result.success("true");
    }

    /* 根据id删除学生 */
    @GetMapping("/deleteById")
    public Result deleteById(String id) {
        log.info("删除学生:{}",id);
        int affectedRows = studentService.deleteById(id);
        if (affectedRows > 0) {
            return Result.success("true");
        } else {
            return Result.error("删除失败，学号不存在");
        }
    }
}
