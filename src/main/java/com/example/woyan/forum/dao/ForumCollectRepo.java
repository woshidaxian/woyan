package com.example.woyan.forum.dao;

import com.example.woyan.forum.dto.ForumCollect;
import com.example.woyan.forum.dto.ForumCollectPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCollectRepo extends JpaRepository<ForumCollect, ForumCollectPK> {
    ForumCollect findByUserIdAndForumId(long userId, long forumId);
}
