package com.example.woyan.videoCourse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class VideoComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    // 视频id
    private long videoId;
    // 发表评论的用户id
    private long userId;
    // 被评论的用户id
    private long replyId;
    // 被评论的评论id
    private long reCommentId;
    // 评论内容
    private String content;
    // 评论时间
    private Date time;
    // 是否已经删除
    private boolean isDelete;

    public VideoComment(long videoId, long userId, long replyId, long reCommentId, String content) {
        this.videoId = videoId;
        this.userId = userId;
        this.replyId = replyId;
        this.reCommentId = reCommentId;
        this.content = content;
        this.time = new Date();
        this.isDelete = false;
    }
}
