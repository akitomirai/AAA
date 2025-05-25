package com.tiankong.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Directory {

    private Integer id;
    private String name;
    private Integer clevel;
    private String content;
    private Integer parentId;
    private String childId;

    private List<Directory> children = new ArrayList<>();
}