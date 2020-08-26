package com.example.woyan.returnmsg;

import com.example.woyan.forum.dto.ForumComment;
import com.example.woyan.videoCourse.dto.VCourseComment;
import com.example.woyan.videoCourse.dto.VideoComment;
import com.example.woyan.util.TextUtil;
import lombok.Data;

@Data
public class ReturnComment {
    private static TextUtil textUtil = new TextUtil();
    private long commentId;
    // 视频课程id 或者 视频id
    private long uId;
    // 发表评论的用户
    private String account;
    // 被评论的用户id
    private String replyAccount;
    // 被评论的评论id（default 0，即如果为0则是评论给视频的，如果非0是评论给其他人的评论的）
    private long reCommentId;
    // 评论内容
    private String content = "";
    // 评论时间
    private String time;
    // 是否删除
    private boolean isDelete;
    ReturnReplyList replies;

    public ReturnComment(String account, String replyAccount, VCourseComment vCourseComment, ReturnReplyList replies) {
        this.commentId = vCourseComment.getCommentId();
        this.uId = vCourseComment.getVcourseId();
        this.account = account;
        this.replyAccount = replyAccount;
        this.reCommentId = vCourseComment.getReCommentId();
        if (!isDelete){
            this.content = vCourseComment.getContent();
        }
        this.time = textUtil.formatTime(vCourseComment.getTime());
        this.replies = replies;
        this.isDelete = vCourseComment.isDelete();
    }

    public ReturnComment(String account, String replyAccount, VideoComment videoComment, ReturnReplyList replies) {
        this.commentId = videoComment.getCommentId();
        this.uId = videoComment.getVideoId();
        this.account = account;
        this.replyAccount = replyAccount;
        this.reCommentId = videoComment.getReCommentId();
        if (!isDelete) {
            this.content = videoComment.getContent();
        }
        this.time = textUtil.formatTime(videoComment.getTime());
        this.replies = replies;
        this.isDelete = videoComment.isDelete();
    }

    public ReturnComment(String account, String replyAccount, ForumComment forumComment, ReturnReplyList replies) {
        this.commentId = forumComment.getCommentId();
        this.uId = forumComment.getForumId();
        this.account = account;
        this.replyAccount = replyAccount;
        this.reCommentId = forumComment.getReCommentId();
        if (!isDelete) {
            this.content = forumComment.getContent();
        }
        this.time = textUtil.formatTime(forumComment.getTime());
        this.replies = replies;
        this.isDelete = forumComment.isDelete();
    }
}
