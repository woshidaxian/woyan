package com.example.woyan.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;
    // 所属考试Id
    private long examId;
    // 属于考试中的第几道题目
    private int orderNum;
    // 分值
    private int score;
    // 题目（富文本？图片？）
    private String title;
    // 题目类型（1.选择、2.判断、3.简答，4.答题）
    private int genre;
    // 个别题型会有选项（多个选项之间"\r\n"分隔）
    private String options;
    // 该题的正确答案
    private String answer;
}
