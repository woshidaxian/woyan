package com.example.woyan.videoCourse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyVCoursePK implements Serializable {
    // 用户id
    private Long userId;
    // 课程 id
    private Long vcourseId;
}
