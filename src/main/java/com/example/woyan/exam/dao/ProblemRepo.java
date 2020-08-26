package com.example.woyan.exam.dao;

import com.example.woyan.exam.dto.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepo extends JpaRepository<Problem, Long> {
    List<Problem> findAllByExamIdOrderByOrderNumAsc(long examId);
    Problem findByProblemId(long id);
}
