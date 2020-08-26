package com.example.woyan.ali.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class ALiSMSCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;
    // 手机号
    @Column(unique = true)
    private String phone;
    // 验证码
    private String code;
    // 验证码类型 1 注册 2修改密码
    private int genre;
    // 验证码有效时间
    private Date date;

    public ALiSMSCode(String phone, String code, int genre, Date date) {
        this.phone = phone;
        this.code = code;
        this.genre = genre;
        this.date = date;
    }
}
