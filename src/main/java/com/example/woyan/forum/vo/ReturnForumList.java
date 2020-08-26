package com.example.woyan.forum.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnForumList {
    private long forumNum;
    private int forumPage;
    private List<ReturnForum> forums;
}
