package com.example.woyan.videoCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MyVCoursePK.class)
public class MyVCourse {
    // 用户id
    @Id
    private Long userId;
    // 课程 id
    @Id
    private Long vcourseId;
    // 还可以看的此处（默认为 5*课程视频数）
    private int canSee;
}
