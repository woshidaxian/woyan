package com.example.woyan.forum.dao;

import com.example.woyan.forum.dto.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepo extends JpaRepository<Forum, Long> {
    Page<Forum> findAllBySchoolCodeAndDelete(long schoolCode, boolean isDelete, Pageable pageable);
    Page<Forum> findAllBySchoolCodeAndDeleteAndTitleLike(long schoolCode, boolean isDelete, String key, Pageable pageable);
    Forum findByForumId(long forumId);
}
