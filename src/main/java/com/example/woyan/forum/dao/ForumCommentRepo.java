package com.example.woyan.forum.dao;

import com.example.woyan.forum.dto.ForumComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumCommentRepo extends JpaRepository<ForumComment, Long> {
    ForumComment findByCommentId(long commentId);
    Page<ForumComment> findAllByForumIdAndReCommentId(long forumId, long reCommentId, Pageable pageable);
    Page<ForumComment> findAllByReCommentId(long reCommentId, Pageable pageable);
}
