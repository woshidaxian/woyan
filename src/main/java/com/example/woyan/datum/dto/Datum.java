package com.example.woyan.datum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datumId;
    // 专业课id
    private long proCourseId;
    // 资料标题
    private String title;
    // 资料封面
    private String cover;
    // 资料价格
    private int price;
    // 资料简介
    private String intro;
    // 详细的图文介绍
    @Column(columnDefinition = "text")
    private String presentation;
    // 查看数
    private long seeNum;
    // 学校id
    private long schoolCode;
    // 如果是免费资料，会有资料的路径
    private String datumUrl;
}
