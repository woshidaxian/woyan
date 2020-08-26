package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.VCourseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VCourseCommentRepo extends JpaRepository<VCourseComment,Long> {
    VCourseComment findByCommentId(long vCourseId);
    Page<VCourseComment> findAllByVcourseIdAndReCommentId(long vCourseId,long reCommentId, Pageable pageable);
    Page<VCourseComment> findAllByReCommentId(long reCommentId, Pageable pageable);
}
