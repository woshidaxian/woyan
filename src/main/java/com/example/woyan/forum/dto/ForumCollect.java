package com.example.woyan.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@NoArgsConstructor
@IdClass(ForumCollectPK.class)
public class ForumCollect {
    @Id
    private Long userId;
    @Id
    private Long forumId;
    private boolean isCollect;

    public ForumCollect(long userId, long forumId){
        this.userId = userId;
        this.forumId =forumId;
        this.isCollect = true;
    }
}
