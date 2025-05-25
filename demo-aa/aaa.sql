-- ----------------------------
-- 1. 角色表（role）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `role` (
  `id` TINYINT UNSIGNED AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(20) UNIQUE NOT NULL COMMENT '角色名称（student/teacher/manager）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='角色表';


-- ----------------------------
-- 2. 用户表（user）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '用户ID',
  `user_account` VARCHAR(20) UNIQUE NOT NULL COMMENT '账号（学号/工号）',
  `user_password` VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
  `user_name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `role_id` TINYINT UNSIGNED NOT NULL COMMENT '角色ID（关联role表）',
  `gender` CHAR(1) DEFAULT NULL COMMENT '性别（B=男，G=女）',
  `birthday` DATE DEFAULT NULL COMMENT '生日（教师专用）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='统一用户表';


-- ----------------------------
-- 3. 课程目录表（course_category）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `course_category` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '目录ID',
  `category_name` VARCHAR(100) NOT NULL COMMENT '名称（课程大类/章节/小节/知识点）',
  `parent_id` INT UNSIGNED DEFAULT NULL COMMENT '父节点ID（顶级为NULL）',
  `clevel` TINYINT NOT NULL COMMENT '层级（1=大类，2=章节，3=小节，4=知识点）',
  `responsible_teacher_id` INT UNSIGNED DEFAULT NULL COMMENT '负责人教师ID（关联user表）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_responsible_teacher_id` (`responsible_teacher_id`),
  CONSTRAINT `fk_category_teacher` FOREIGN KEY (`responsible_teacher_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB COMMENT='课程目录表';


-- ----------------------------
-- 4. 专业表（major）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `major` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '专业ID',
  `major_name` VARCHAR(50) UNIQUE NOT NULL COMMENT '专业名称',
  `level` TINYINT NOT NULL COMMENT '层次（0=本科，1=专科）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='专业表';


-- ----------------------------
-- 5. 班级表（class_info）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `class_info` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '班级ID',
  `class_name` VARCHAR(50) UNIQUE NOT NULL COMMENT '班级名称',
  `entry_year` YEAR NOT NULL COMMENT '入学年份',
  `level` TINYINT NOT NULL COMMENT '层次（继承专业层次）',
  `major_id` INT UNSIGNED NOT NULL COMMENT '专业ID（关联major表）',
  `counselor_name` VARCHAR(50) DEFAULT NULL COMMENT '辅导员姓名',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_major_id` (`major_id`),
  CONSTRAINT `fk_class_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='班级表';


-- ----------------------------
-- 6. 课程表（subject）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `subject` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '课程ID',
  `subject_name` VARCHAR(100) NOT NULL COMMENT '课程名称',
  `subject_id` VARCHAR(20) UNIQUE NOT NULL COMMENT '课程号',
  `course_category_id` INT UNSIGNED NOT NULL COMMENT '课程大类ID（关联course_category表，clevel=1）',
  `level` TINYINT NOT NULL COMMENT '层次（0=本科，1=专科）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_category_id` (`course_category_id`),
  CONSTRAINT `fk_subject_category` FOREIGN KEY (`course_category_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='课程表';


-- ----------------------------
-- 7. 课程与专业关联表（subject_major_relation）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `subject_major_relation` (
  `subject_id` INT UNSIGNED NOT NULL COMMENT '课程ID（关联subject表）',
  `major_id` INT UNSIGNED NOT NULL COMMENT '专业ID（关联major表）',
  PRIMARY KEY (`subject_id`, `major_id`),
  CONSTRAINT `fk_subject_major_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_subject_major_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='课程与专业关联表';


-- ----------------------------
-- 8. 试卷表（exam_paper）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_paper` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '试卷ID',
  `exam_name` VARCHAR(100) UNIQUE NOT NULL COMMENT '试卷/作业名称',
  `course_id` INT UNSIGNED NOT NULL COMMENT '课程大类ID（关联course_category表，clevel=1）',
  `total_score` INT NOT NULL COMMENT '总分',
  `difficulty` TINYINT DEFAULT NULL COMMENT '难度（1-5）',
  `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  `is_published` TINYINT NOT NULL DEFAULT 0 COMMENT '是否发布（0=未发布，1=已发布）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  CONSTRAINT `fk_exam_paper_course` FOREIGN KEY (`course_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='试卷/作业表';


-- ----------------------------
-- 9. 题目表（question）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `question` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '题目ID',
  `question_type` VARCHAR(20) NOT NULL COMMENT '类型（choice/programFill等）',
  `content` TEXT NOT NULL COMMENT '题干',
  `options` JSON DEFAULT NULL COMMENT '选项（选择题专用）',
  `answer` TEXT NOT NULL COMMENT '正确答案',
  `analysis` TEXT DEFAULT NULL COMMENT '解析',
  `difficulty` TINYINT DEFAULT 1 COMMENT '难度（1-5）',
  `course_category_id` INT UNSIGNED NOT NULL COMMENT '所属知识点ID（关联course_category表，clevel=4）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_category_id` (`course_category_id`),
  CONSTRAINT `fk_question_category` FOREIGN KEY (`course_category_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='题目表';


-- ----------------------------
-- 10. 用户试卷表（exam_user_paper）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `exam_user_paper` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '用户试卷ID',
  `exam_paper_id` INT UNSIGNED NOT NULL COMMENT '试卷ID（关联exam_paper表）',
  `student_id` VARCHAR(20) NOT NULL COMMENT '学生学号（关联user表user_account）',
  `status` TINYINT NOT NULL COMMENT '状态（1=未开始，2=进行中，3=待批改，4=已完成）',
  `submit_time` DATETIME DEFAULT NULL COMMENT '提交时间',
  `total_score` INT DEFAULT NULL COMMENT '总分',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_exam_paper_id` (`exam_paper_id`),
  KEY `idx_student_id` (`student_id`),
  CONSTRAINT `fk_exam_user_paper_exam` FOREIGN KEY (`exam_paper_id`) REFERENCES `exam_paper` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_exam_user_paper_student` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_account`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='用户试卷表';


-- ----------------------------
-- 11. 用户答案表（user_answer）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_answer` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '用户答案ID',
  `exam_user_paper_id` INT UNSIGNED NOT NULL COMMENT '用户试卷ID（关联exam_user_paper表）',
  `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID（关联question表）',
  `user_answer` TEXT NOT NULL COMMENT '用户答案',
  `score` INT DEFAULT NULL COMMENT '得分（教师批改）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作答时间',
  PRIMARY KEY (`id`),
  KEY `idx_exam_user_paper_id` (`exam_user_paper_id`),
  KEY `idx_question_id` (`question_id`),
  CONSTRAINT `fk_user_answer_exam` FOREIGN KEY (`exam_user_paper_id`) REFERENCES `exam_user_paper` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='用户答案表';


-- ----------------------------
-- 12. 错题集表（student_error_question）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `student_error_question` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '错题ID',
  `student_id` VARCHAR(20) NOT NULL COMMENT '学生学号（关联user表user_account）',
  `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID（关联question表）',
  `exam_user_paper_id` INT UNSIGNED NOT NULL COMMENT '用户试卷ID（关联exam_user_paper表）',
  `user_answer` TEXT NOT NULL COMMENT '用户答案',
  `correct_answer` TEXT NOT NULL COMMENT '正确答案',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '错题生成时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_question_id` (`question_id`),
  KEY `idx_exam_user_paper_id` (`exam_user_paper_id`),
  CONSTRAINT `fk_error_student` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_account`) ON DELETE CASCADE,
  CONSTRAINT `fk_error_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_error_exam` FOREIGN KEY (`exam_user_paper_id`) REFERENCES `exam_user_paper` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='学生错题集表';


-- ----------------------------
-- 13. 教学计划表（teaching_plan）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `teaching_plan` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '教学计划ID',
  `year_term` VARCHAR(50) NOT NULL COMMENT '学年学期（如2024-2025学年第二学期）',
  `course_id` INT UNSIGNED NOT NULL COMMENT '课程ID（关联subject表）',
  `teacher_id` INT UNSIGNED NOT NULL COMMENT '教师ID（关联user表）',
  `major_id` INT UNSIGNED NOT NULL COMMENT '专业ID（关联major表）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_major_id` (`major_id`),
  CONSTRAINT `fk_plan_course` FOREIGN KEY (`course_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_plan_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_plan_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='教学计划表';


-- ----------------------------
-- 14. 教学计划与班级关联表（plan_class_relation）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `plan_class_relation` (
  `plan_id` INT UNSIGNED NOT NULL COMMENT '教学计划ID（关联teaching_plan表）',
  `class_id` INT UNSIGNED NOT NULL COMMENT '班级ID（关联class_info表）',
  PRIMARY KEY (`plan_id`, `class_id`),
  CONSTRAINT `fk_plan_class_plan` FOREIGN KEY (`plan_id`) REFERENCES `teaching_plan` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_plan_class_class` FOREIGN KEY (`class_id`) REFERENCES `class_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='教学计划与班级关联表';


-- ----------------------------
-- 15. 练习表（practice）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `practice` (
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT '练习ID',
  `practice_name` VARCHAR(100) UNIQUE NOT NULL COMMENT '练习名称',
  `course_id` INT UNSIGNED NOT NULL COMMENT '课程大类ID（关联course_category表，clevel=1）',
  `chapter_id` INT UNSIGNED DEFAULT NULL COMMENT '章节ID（关联course_category表，clevel=2）',
  `paragraph_id` INT UNSIGNED DEFAULT NULL COMMENT '小节ID（关联course_category表，clevel=3）',
  `choice_num` INT NOT NULL DEFAULT 0 COMMENT '选择题数量',
  `judge_num` INT NOT NULL DEFAULT 0 COMMENT '判断题数量',
  `create_user_id` INT UNSIGNED NOT NULL COMMENT '创建人ID（教师用户，关联user表）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`),
  KEY `idx_paragraph_id` (`paragraph_id`),
  KEY `idx_create_user_id` (`create_user_id`),
  CONSTRAINT `fk_practice_course` FOREIGN KEY (`course_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_practice_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_practice_paragraph` FOREIGN KEY (`paragraph_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_practice_create_user` FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='练习表';