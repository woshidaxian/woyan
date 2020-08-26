package com.example.woyan.videoCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vcourseId;
    // 用户id
    private long userId;
    // 标题
    private String title;
    // 封面
    private String cover;
    // 简介
    private String intro;
    // 评论数
    private int commentNum;
    // 查看数
    private int seeNum;
    // 价格
    private int price;
    // 总有多少课时
    private int videoNum;
    // 免费的课程数
    private int freeNum;
    // 学校代码
    private long schoolCode;
    // 学院 id
    private long collageId;
    // 专业课id
    private long proCourseId;
}
