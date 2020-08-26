package com.example.woyan.returnmsg;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnReplyList {
    // 共有多少条子评论
    private long replyNum;
    // 子评论共分几页
    private int replyPage;
    // 子评论
    private List<ReturnReply> replies;
}
