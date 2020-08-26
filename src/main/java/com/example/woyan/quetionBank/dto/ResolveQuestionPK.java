package com.example.woyan.quetionBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolveQuestionPK implements Serializable {
    private Long userId;
    private Long questionId;
}
