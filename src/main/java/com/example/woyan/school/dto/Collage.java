package com.example.woyan.school.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Collage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collageId;
    private String name;
    private long schoolCode;

    public Collage(String name, long schoolCode) {
        this.name = name;
        this.schoolCode = schoolCode;
    }
}
