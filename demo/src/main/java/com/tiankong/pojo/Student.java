package com.tiankong.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Student {

    /**
     * 学号（主键）
     */
    private String id;
    /**
     * 学校名称
     */
    private String universityName;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 性别（0:男 1:女）
     */
    private Integer gender;
    /**
     * 专业编码
     */
    private String majorNo;
    /**
     * 年龄（非负整数）
     */
    private Integer age;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 身份证号（唯一）
     */
    private String cardNo;
    /**
     * 有效期至
     */
    private LocalDate expireDate;
    /**
     * 责任教师ID
     */
    private String teacherId;
    /**
     * 创建时间（自动生成）
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间（自动更新）
     */
    private LocalDateTime updateTime;

    /** 课程id
     */
    private List<String> courseIds;
}
