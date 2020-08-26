package com.example.woyan.quetionBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ResolveQuestionPK.class)
public class ResolveQuestion {
    @Id
    private Long userId;
    @Id
    private Long questionId;
    private String answer;
}
