package com.example.woyan.quetionBank.vo;

import com.example.woyan.quetionBank.dto.QuestionBank;
import com.example.woyan.quetionBank.dto.ResolveQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnQuestion {
    private QuestionBank question;
    private boolean isDone;
    private String answer;
}
