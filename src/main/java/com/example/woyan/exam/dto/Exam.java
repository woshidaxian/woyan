package com.example.woyan.exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examId;
    // 考试名称
    private String name;
    // 考试状态（0：报名中，1：已结束）
    private int status;
    // 开始时间
    private Date beginTime;
    // 结束时间
    private Date endTime;
    // 专业课Id
    private long proCourseId;
    // 学校代码
    private long schoolCode;
    // 满分
    private int mark;

    public Exam(String name, Date beginTime, Date endTime, long proCourseId, long schoolCode, int mark) {
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.proCourseId = proCourseId;
        this.schoolCode = schoolCode;
        this.mark = mark;
        this.status = 0;
    }
}
