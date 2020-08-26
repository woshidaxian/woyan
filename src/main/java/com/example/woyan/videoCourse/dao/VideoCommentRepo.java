package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepo extends JpaRepository<VideoComment,Long> {
    Page<VideoComment> findAllByVideoIdAndReCommentId(long videoId, long reCommentId, Pageable pageable);
    Page<VideoComment> findAllByReCommentId(long reCommentId, Pageable pageable);
    VideoComment findByCommentId(long commentId);
}
