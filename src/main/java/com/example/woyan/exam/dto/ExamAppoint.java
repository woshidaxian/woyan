package com.example.woyan.exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

// 考试预约（每天定时删除）
@Data
@Entity
@NoArgsConstructor
@IdClass(ExamAppointPK.class)
public class ExamAppoint {
    @Id
    private Long userId;
    @Id
    private Long examId;
    // 考试开始时间
    private Date beginTime;
    // 用户是否参加了考试
    private boolean isBegin;
    // 是否 已经交卷
    private boolean isFinished;

    public ExamAppoint(Long userId, Exam exam) {
        this.userId = userId;
        this.examId = exam.getExamId();
        this.beginTime = exam.getBeginTime();
        this.isBegin = false;
        this.isFinished = false;
    }
}
