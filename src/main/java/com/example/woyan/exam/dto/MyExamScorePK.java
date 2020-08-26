package com.example.woyan.exam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyExamScorePK implements Serializable {
    private Long examId;
    private Long userId;
}
