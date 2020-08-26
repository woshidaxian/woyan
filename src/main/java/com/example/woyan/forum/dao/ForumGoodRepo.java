package com.example.woyan.forum.dao;

import com.example.woyan.forum.dto.ForumGood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumGoodRepo extends JpaRepository<ForumGood,Long> {
    ForumGood findByUserIdAndForumId(long userId, long forumId);
}
