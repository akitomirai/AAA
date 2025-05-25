package com.tiankong.pojo;

import lombok.Data;

@Data
public class StudentQuerParam {
    private Integer current = 1;
    private Integer pageSize = 10;
    private String id;
    private String name;
}
