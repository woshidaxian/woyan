package com.example.woyan.videoCourse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VCourseRecommendPK implements Serializable {
    private Long schoolCode;
    private Long vcourseId;
}
