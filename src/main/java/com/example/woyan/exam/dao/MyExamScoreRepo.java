package com.example.woyan.exam.dao;

import com.example.woyan.exam.dto.MyExamScore;
import com.example.woyan.exam.dto.MyExamScorePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyExamScoreRepo extends JpaRepository<MyExamScore, MyExamScorePK> {
    MyExamScore findByUserIdAndExamId(long userId, long examId);
    Page<MyExamScore> findAllByUserId(long userId, Pageable pageable);
}
