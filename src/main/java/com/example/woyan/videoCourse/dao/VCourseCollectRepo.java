package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.VCourseCollect;
import com.example.woyan.videoCourse.dto.VCourseCollectPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VCourseCollectRepo extends JpaRepository<VCourseCollect, VCourseCollectPK> {
    VCourseCollect findFirstByUserIdAndVcourseId(long userId,long VCourseId);
}
