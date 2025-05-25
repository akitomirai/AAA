package com.tiankong.controller;

import com.tiankong.pojo.Directory;
import com.tiankong.utils.Result;
import com.tiankong.service.DirectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tiankong.pojo.dto.DirectoryDTO;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    // 获取课程列表（支持按目录id筛选）
    @GetMapping("/getCourses")
    public Result getKnowledgePoints(@RequestParam(required = false) Integer id ) {
        log.info("获取知识点id的列表:{}", id);
        List<Directory> tree = directoryService.getDirectoryTree(id);

        return Result.success(DirectoryDTO.convertList(tree));
    }

    // 新增目录节点
    @PostMapping("/add")
    public Result addDirectory(@RequestBody Directory directory) {
        log.info("添加知识点:{}", directory);
        directoryService.addDirectory(directory);
        return Result.success("true");
    }

    // 删除节点
    @GetMapping("/delete")
    public Result deleteDirectory(@RequestParam Integer id) {
        log.info("删除知识点:{}",id);
        Integer num = directoryService.deleteById(id);
        return Result.success(num);
    }

    @GetMapping("/updateName")
    public Result updateName(@RequestParam Integer id,@RequestParam String name) {
        Directory directory = new Directory();
        directory.setId(id);
        directory.setName(name);
        directoryService.updateChildIds(directory);
        return Result.success("true");
    }
}
