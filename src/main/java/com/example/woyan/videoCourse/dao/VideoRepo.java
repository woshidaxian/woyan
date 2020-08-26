package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepo extends JpaRepository<Video,Long> {
    List<Video> findAllByVcourseId(long vCourseId);
    Video findByVideoId(long id);
}
