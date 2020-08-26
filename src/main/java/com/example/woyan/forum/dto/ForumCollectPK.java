package com.example.woyan.forum.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForumCollectPK implements Serializable {
    private Long userId;
    private Long forumId;
}
