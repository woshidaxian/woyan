package com.example.woyan.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        indexes = {
                @Index(name = "schoolCode",columnList = "schoolCode",unique = true)
        }
)
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    // 学校名称
    private String name;
    // 学校所在省id
    private long provinceId;
    // 学校代码
    private long schoolCode;
    // 学校是否为可以考研的学校
    private boolean isGraduate;
}
