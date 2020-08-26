package com.example.woyan.exam.dao;

import com.example.woyan.exam.dto.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam, Long> {
    Page<Exam> findAllByStatusAndSchoolCode(int status, long schoolCode, Pageable pageable);
    Page<Exam> findAllByStatusAndSchoolCodeAndProCourseId(int status, long schoolCode, long  proCourseId, Pageable pageable);
    Exam findByExamId(long id);
}
