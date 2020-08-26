package com.example.woyan.forum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@NoArgsConstructor
@IdClass(ForumGoodPK.class)
public class ForumGood {
    @Id
    private Long forumId;
    @Id
    private Long userId;
    private boolean isGood;

    public ForumGood(Long forumId, Long userId) {
        this.forumId = forumId;
        this.userId = userId;
        isGood = true;
    }
}
