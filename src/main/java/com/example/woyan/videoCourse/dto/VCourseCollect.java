package com.example.woyan.videoCourse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@IdClass(VCourseCollectPK.class)
public class VCourseCollect {
    @Id
    private Long userId;
    @Id
    private Long vcourseId;
    private boolean isCollect;

    public VCourseCollect(long userId,long vcourseId){
        this.userId = userId;
        this.vcourseId = vcourseId;
        this.isCollect = true;
    }
}
