package com.example.woyan.exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

// 保存用户实时做题情况
@Entity
@Data
@NoArgsConstructor
@IdClass(ProblemAnswerPK.class)
public class ProblemAnswer {
    @Id
    private Long userId;
    @Id
    private Long problemId;
    private String answer;
    private Date time;

    public ProblemAnswer(Long userId, Long problemId, String answer) {
        this.userId = userId;
        this.problemId = problemId;
        this.answer = answer;
        this.time = new Date();
    }
}
