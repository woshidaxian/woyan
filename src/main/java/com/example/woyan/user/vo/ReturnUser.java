package com.example.woyan.user.vo;

import com.example.woyan.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReturnUser {
    private Long userId;
    // 帐号
    private String account;
    // 手机号
    private String phone;
    // 头像
    private String headimg;
    // 学校代码
    private long schoolCode;
    private String school;
    // 原来的学校代码
    private long originalCode;
    private String originalSchool;
    // 论坛文章数
    private long forumNum;

    public ReturnUser(String school, String originalSchool, User user){
        this.school = school;
        this.schoolCode = user.getSchoolCode();
        this.originalSchool = originalSchool;
        this.originalCode = user.getOriginalCode();
        this.userId = user.getUserId();
        this.account = user.getAccount();
        this.phone = user.getPhone();
        this.headimg = user.getHeadimg();
        this.forumNum = user.getForumNum();
    }
}
