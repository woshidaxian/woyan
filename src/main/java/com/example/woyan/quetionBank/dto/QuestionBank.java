package com.example.woyan.quetionBank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class QuestionBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    //题目
    private String title;
    // 专业课id
    private long proCourseId;
    // 标准答案
    private String answer;
    // 题目类型（1.选择、2.判断、3.简答，4.答题）
    private int genre;
    // 选项（如果是选择题）
    private String options;
}
