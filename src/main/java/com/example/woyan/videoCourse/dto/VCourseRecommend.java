package com.example.woyan.videoCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VCourseRecommendPK.class)
public class VCourseRecommend {
    @Id
    private Long schoolCode;
    @Id
    private long vcourseId;
}
