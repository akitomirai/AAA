package com.tiankong.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiankong.mapper.StuCouMapper;
import com.tiankong.mapper.StudentMapper;
import com.tiankong.pojo.PageResult;
import com.tiankong.pojo.Student;
import com.tiankong.pojo.StudentCourse;
import com.tiankong.pojo.StudentQuerParam;
import com.tiankong.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StuCouMapper stuCouMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Student student) {
        try {
            studentMapper.insert(student);
            // Service层方法片段
            List<String> courseIds = student.getCourseIds();
            List<StudentCourse> list;

            if (!CollectionUtils.isEmpty(courseIds)) {
                // 获取当前学生的ID
                String studentId = student.getId();  // 假设Student类有getId()方法
                // 转换为StudentCourse列表
                list = courseIds.stream()
                        .distinct()     //去重
                        .map(courseId -> new StudentCourse(studentId, courseId))
                        .collect(Collectors.toList());
                // 批量插入关联关系
                stuCouMapper.insertBatch(list);
            }
        } finally {

        }

    }

    @Override
    public PageResult<Student> page(StudentQuerParam studentQuerParam) {
        PageHelper.startPage(studentQuerParam.getCurrent(),studentQuerParam.getPageSize());
        List<Student> students = studentMapper.list(studentQuerParam);
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return new PageResult<Student>(pageInfo.getTotal(),students);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Student student) {
        //1.根据ID修改学生基本信息
        int ass = studentMapper.update(student);
        //2.1删除学生课程信息
        stuCouMapper.deleteByIds(Arrays.asList(student.getId()));
        //2.2添加学生课程信息
        // Service层方法片段
        List<String> courseIds = student.getCourseIds();
        List<StudentCourse> list;

        if (!CollectionUtils.isEmpty(courseIds)) {
            // 获取当前学生的ID
            String studentId = student.getId();  // 假设Student类有getId()方法
            // 转换为StudentCourse列表
            list = courseIds.stream()
                    .distinct()     //去重
                    .map(courseId -> new StudentCourse(studentId, courseId))
                    .collect(Collectors.toList());
            // 批量插入关联关系
            stuCouMapper.insertBatch(list);
        }
    }

    @Override
    public int deleteById(String id) {
        return studentMapper.deleteById(id);
    }
}
