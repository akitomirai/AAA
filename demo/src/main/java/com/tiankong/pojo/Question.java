package com.tiankong.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.ibatis.type.TypeReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Question {

    private Long id;
    // 基础信息
    private Long courseId;
    private Long chapterId;
    private Long paragraphId;
    private String type;
    private Integer difficult;
    
    // 内容相关
    private String content;

    @JsonProperty("answer")
    private List<String> answer = new ArrayList<>();
    @JsonProperty("answer")
    private String jsonAnswer;

    private String analysis;
    @JsonProperty("knowledgeId")
    private List<Long> knowledgeId = new ArrayList<>();
    @JsonProperty("knowledgeId")
//    @Column(name = "knowledge_id")  // 映射数据库字段 knowledge_id
    private String jsonKnowledgeId;

    @JsonProperty("chose")
    private List<String> chose = new ArrayList<>();
    @JsonProperty("chose")
    private String jsonChose;
    
    // 时间戳
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

//    @PostLoad
//    public void postLoad() {
//        this.answer = parseJson(jsonAnswer, new TypeReference<List<String>>() {});
//        this.knowledgeId = parseJson(jsonKnowledgeId, new TypeReference<List<Long>>() {});
//        this.chose = parseJson(jsonChose, new TypeReference<List<String>>() {});
//    }
//
//    private <T> List<T> parseJson(String json, TypeReference<List<T>> typeRef) {
//        if (json == null || json.isEmpty()) return new ArrayList<>();
//        try {
//            return new ObjectMapper().readValue(json, typeRef);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("JSON解析失败: " + json, e);
//        }
//    }

    @JsonProperty("answer")
    public void setAnswer(List<String> answer) {
        this.answer = answer;
        this.jsonAnswer = serializeToJson(answer);
    }

    @JsonProperty("knowledgeId")
    public void setKnowledgeId(List<Long> knowledgeId) {
        this.knowledgeId = knowledgeId;
        this.jsonKnowledgeId = serializeToJson(knowledgeId);
    }

    @JsonProperty("chose")
    public void setChose(List<String> chose) {
        this.chose = chose;
        this.jsonChose = serializeToJson(chose);
    }

    private String serializeToJson(Object value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON序列化失败", e);
        }
    }

}
