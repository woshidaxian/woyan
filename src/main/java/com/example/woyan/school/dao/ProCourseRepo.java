package com.example.woyan.school.dao;

import com.example.woyan.school.dto.ProCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProCourseRepo extends JpaRepository<ProCourse, Long> {
    List<ProCourse> findAllByCollageId(long collageId);
}
