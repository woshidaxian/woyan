package com.example.woyan.exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@IdClass(MyExamScorePK.class)
public class MyExamScore {
    @Id
    private Long examId;
    @Id
    private Long userId;
    private Date beginTime;
    private int score;
    // 状态：（1：批改中，2）
    private int genre;

    public MyExamScore(Exam exam, Long userId) {
        this.examId = exam.getExamId();
        this.userId = userId;
        this.beginTime = exam.getBeginTime();
        this.score = -1;
        this.genre = 1;
    }
}
