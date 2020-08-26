package com.example.woyan.videoCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;
    // 视频标题
    private String title;
    // 属于课程中第几个课时
    private int orderNum;
    // 课程id
    private long vcourseId;
    // 视频链接
    @Column(nullable = false)
    private String videoSrc;
    // 评论数
    private int commentNum;
}
