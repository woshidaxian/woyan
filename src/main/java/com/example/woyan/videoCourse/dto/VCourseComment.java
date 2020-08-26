package com.example.woyan.videoCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        indexes = {
                @Index(name = "vcourseId",columnList = "vcourseId"),
                @Index(name = "reCommentId",columnList = "reCommentId")
        }
)
public class VCourseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    // 视频课程id
    private long vcourseId;
    // 发表评论的用户id
    private long userId;
    // 被评论的用户id
    private long replyId;
    // 被评论的评论id（default 0，即如果为0则是评论给视频的，如果非0是评论给其他人的评论的）
    private long reCommentId;
    // 评论内容
    private String content;
    // 评论时间
    private Date time;
    // 是否已经删除
    private boolean isDelete;

    public VCourseComment(long userId, long vcourseId, long replyId, long reCommentId, String content) {
        this.userId = userId;
        this.vcourseId = vcourseId;
        this.replyId = replyId;
        this.reCommentId = reCommentId;
        this.content = content;
        isDelete = false;
        this.time = new Date();
    }
}
