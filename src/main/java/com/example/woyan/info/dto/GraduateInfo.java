package com.example.woyan.info.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class GraduateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;
    // 标题
    private String title;
    // 发表时间
    private Date time;
    // 内容
    @Column(columnDefinition = "longtext")
    private String content;
    // 主题
    private long themeId;
    // 学校
    private long schoolCode;

    public GraduateInfo(String title, String content, long themeId, long schoolCode) {
        this.title = title;
        this.content = content;
        this.themeId = themeId;
        this.schoolCode = schoolCode;
        this.time = new Date();
    }
}
