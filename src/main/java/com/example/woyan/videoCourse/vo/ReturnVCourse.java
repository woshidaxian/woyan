package com.example.woyan.videoCourse.vo;

import com.example.woyan.videoCourse.dto.VCourse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnVCourse {
    private Long vcourseId;
    // 用户id
    private long userId;
    private String account;
    // 标题
    private String title;
    // 封面
    private String cover;
    // 简介
    private String intro;
    // 评论数
    private int commentNum;
    // 查看数
    private int seeNum;
    // 专业课id
    private long proCourseId;
    // 价格
    private int price;
    // 总有多少课时
    private int videoNum;
    // 免费的课程数
    private int freeNum;
    // 学校代码
    private long schoolCode;
    // 是否收藏
    private boolean isCollect;

    public ReturnVCourse(String account, VCourse vCourse, boolean isCollect){
        this.account = account;
        this.vcourseId = vCourse.getVcourseId();
        this.userId = vCourse.getUserId();
        this.title = vCourse.getTitle();
        this.cover = vCourse.getCover();
        this.intro = vCourse.getIntro();
        this.commentNum = vCourse.getCommentNum();
        this.seeNum = vCourse.getSeeNum();
        this.proCourseId = vCourse.getProCourseId();
        this.price = vCourse.getPrice();
        this.videoNum = vCourse.getVideoNum();
        this.freeNum = vCourse.getFreeNum();
        this.schoolCode = vCourse.getSchoolCode();
        this.isCollect = isCollect;
    }
}
