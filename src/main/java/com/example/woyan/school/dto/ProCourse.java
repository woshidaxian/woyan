package com.example.woyan.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proCourseId;
    // 专业课名称
    private String name;
    // 所属学院id
    private long collageId;
}
