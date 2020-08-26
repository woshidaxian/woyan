package com.example.woyan.videoCourse.dao;

import com.example.woyan.videoCourse.dto.MyVCourse;
import com.example.woyan.videoCourse.dto.MyVCoursePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyVCourseRepo extends JpaRepository<MyVCourse, MyVCoursePK> {
    MyVCourse findByUserIdAndVcourseId(long userId, long vCourseId);
    Page<MyVCourse> findAllByUserId(long userId, Pageable pageable);
}
