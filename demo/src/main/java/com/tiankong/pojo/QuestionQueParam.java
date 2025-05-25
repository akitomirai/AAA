package com.tiankong.pojo;

import lombok.Data;

@Data
public class QuestionQueParam {
    private Integer current = 1;
    private Integer pageSize = 10;
    private Integer id;
    private Integer courseId;
    private Integer chapterId;
    private Integer paragraphId;
    private Integer knowledgeId;
    private String type;

    public Integer getOffset() {
        return (current - 1) * pageSize;
    }
}
