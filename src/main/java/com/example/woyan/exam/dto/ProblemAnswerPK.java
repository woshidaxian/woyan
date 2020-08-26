package com.example.woyan.exam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemAnswerPK implements Serializable {
    private Long userId;
    private Long problemId;
}
