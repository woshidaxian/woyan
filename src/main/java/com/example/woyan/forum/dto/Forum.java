package com.example.woyan.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;
    // 用户id
    private long userId;
    // 论坛文章标题
    private String title;
    // 论坛文章内容
    @Column(columnDefinition = "longtext")
    private String content;
    // 文章发表时间
    private Date time;
    // 最近回复时间
    private Date replyTime;
    // 回复数
    private long replyNum;
    // 点赞数
    private long goodNum;
    // 查看数
    private long seeNum;
    // 标签（用 "," 分开各个标签）
    private String labels;
    // 学校代码
    private long schoolCode;
    // 是否已经删除
    private boolean isDelete;

    public Forum(long userId, String title, String content, String labels, long schoolCode) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.time = new Date();
        this.replyTime = this.time;
        this.replyNum = 0;
        this.goodNum = 0;
        this.seeNum = 0;
        this.labels = labels;
        this.schoolCode = schoolCode;
        this.isDelete = false;
    }
}
