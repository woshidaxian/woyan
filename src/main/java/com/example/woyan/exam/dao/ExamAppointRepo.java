package com.example.woyan.exam.dao;

import com.example.woyan.exam.dto.ExamAppoint;
import com.example.woyan.exam.dto.ExamAppointPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

public interface ExamAppointRepo extends JpaRepository<ExamAppoint, ExamAppointPK> {
    ExamAppoint findByUserIdAndExamId(long userId, long examId);
    Page<ExamAppoint> findAllByUserId(long userId, Pageable pageable);
}
