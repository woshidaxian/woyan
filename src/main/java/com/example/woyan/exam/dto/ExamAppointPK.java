package com.example.woyan.exam.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExamAppointPK implements Serializable {
    private Long userId;
    private Long examId;
}
