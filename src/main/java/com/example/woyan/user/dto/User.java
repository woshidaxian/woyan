package com.example.woyan.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "userId",columnList = "userId",unique = true),
                @Index(name = "account",columnList = "account",unique = true),
                @Index(name = "phone",columnList = "phone",unique = true)
        }
)
public class User {
    private static final String AUTH_USER = "W0YAN_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    // 帐号
    @Column(unique = true)
    private String account;
    // 密码
    @JsonIgnore
    private String password;
    // 手机号
    @Column(unique = true)
    private String phone;
    // 头像
    private String headimg;
    // 学校代码
    private long schoolCode;
    // 权限
    @JsonIgnore
    private String authority;
    // 原来的学校代码
    private long originalCode;
    // 论坛文章数
    private long forumNum;
    // 考研年份
    private int year;

    public User(String account, String password, String phone, long schoolCode, long originalCode) {
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.schoolCode = schoolCode;
        this.originalCode = originalCode;
        this.authority = AUTH_USER;
        this.forumNum = 0;
    }
}
