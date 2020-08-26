package com.example.woyan.exam.vo;

import com.example.woyan.exam.dto.Problem;
import com.example.woyan.util.TextUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class ReturnProblem {
    private static TextUtil textUtil = new TextUtil();
    private Problem problem;
    private String answer;
    // 做题时间
    private String time;

    public ReturnProblem(Problem problem, String answer, Date time) {
        this.problem = problem;
        this.answer = answer;
        this.time = textUtil.formatTime(time);
    }
}
