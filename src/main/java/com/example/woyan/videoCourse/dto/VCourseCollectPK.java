package com.example.woyan.videoCourse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VCourseCollectPK implements Serializable {
    private Long userId;
    private Long vcourseId;
}
