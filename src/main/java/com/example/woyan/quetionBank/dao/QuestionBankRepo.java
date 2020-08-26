package com.example.woyan.quetionBank.dao;

import com.example.woyan.quetionBank.dto.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBankRepo extends JpaRepository<QuestionBank, Long> {
    Page<QuestionBank> findAllByProCourseId(long proCourseId, Pageable pageable);
    QuestionBank findByQuestionId(long questionId);
}
