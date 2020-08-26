package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.VCourseRecommend;
import com.example.woyan.videoCourse.dto.VCourseRecommendPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VCourseRecommendRepo extends JpaRepository<VCourseRecommend, VCourseRecommendPK> {
    Page<VCourseRecommend> findAllBySchoolCode(long schoolCode, Pageable pageable);
}
