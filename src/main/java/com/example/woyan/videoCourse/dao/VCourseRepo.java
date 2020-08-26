package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.VCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VCourseRepo extends JpaRepository<VCourse,Long> {
    Page<VCourse> findAllByCollageId(long collageId,Pageable pageable);
    Page<VCourse> findAllByCollageIdAndProCourseId(long schoolCode,long proCourseId,Pageable pageable);
    VCourse findByVcourseId(long vcourseId);
    Page<VCourse> findAllByTitleLikeOrIntroLike(String key1,String key2,Pageable pageable);
}
