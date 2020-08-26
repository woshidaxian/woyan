package com.example.woyan.exam.vo;

import com.example.woyan.exam.dto.Exam;
import com.example.woyan.util.TextUtil;
import lombok.Data;

import java.util.List;

@Data
public class ReturnExam {
    private static TextUtil textUtil = new TextUtil();

    private Long examId;
    // 考试名称
    private String name;
    // 考试状态（0：报名中，1：已结束）
    private int status;
    // 开始时间
    private String beginTime;
    // 结束时间
    private String endTime;
    // 专业课Id
    private long proCourseId;
    // 学校代码
    private long schoolCode;
    // 满分
    private int mark;
    // 用户取得的分数
    private int score;

    //所有的题目和用户的答案
    List<ReturnProblem> problems;

    public ReturnExam(Exam exam, int score, List<ReturnProblem> problems) {
        this.examId = exam.getExamId();
        this.name = exam.getName();
        this.status = exam.getStatus();
        this.beginTime = textUtil.formatTime(exam.getBeginTime());
        this.endTime = textUtil.formatTime(exam.getEndTime());
        this.proCourseId = exam.getProCourseId();
        this.schoolCode = exam.getSchoolCode();
        this.mark = exam.getMark();
        this.score = score;
        this.problems = problems;
    }
}
