package com.example.woyan.forum.vo;

import com.example.woyan.forum.dto.Forum;
import com.example.woyan.util.TextUtil;
import lombok.Data;

@Data
public class ReturnForum {
    private static TextUtil textUtil = new TextUtil();

    private long forumId;
    // 用户帐号
    private String account;
    // 论坛文章标题
    private String title;
    // 论坛文章内容
    private String content;
    // 文章发表时间
    private String time;
    // 最近回复时间
    private String replyTime;
    // 回复数
    private long replyNum;
    // 点赞数
    private long goodNum;
    // 查看数
    private long seeNum;
    // 标签（用 "," 分开各个标签）
    private String[] labels;
    // 学校代码
    private long schoolCode;
    // 是否已经删除
    private boolean isDelete;
    // 是否收藏
    private boolean isCollect;

    public ReturnForum(String account, Forum forum, boolean isCollect) {
        this.forumId = forum.getForumId();
        this.account = account;
        this.title = forum.getTitle();
        this.content = forum.getContent();
        this.time = textUtil.formatTime(forum.getTime());
        this.replyTime = textUtil.formatTime(forum.getReplyTime());
        this.replyNum = forum.getReplyNum();
        this.goodNum = forum.getGoodNum();
        this.seeNum = forum.getSeeNum();
        this.labels = forum.getLabels().split(",");
        this.schoolCode = forum.getSchoolCode();
        this.isDelete = forum.isDelete();
        this.isCollect = isCollect;
    }
}
