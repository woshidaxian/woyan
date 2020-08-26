package com.example.woyan.returnmsg;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnCommentList {
    // 总共有多少条评论
    private long commentCount;
    // 总共有几页
    private int commentPage;
    private List<ReturnComment> comments;
}
