package com.example.woyan.exam.dao;

import com.example.woyan.exam.dto.ProblemAnswer;
import com.example.woyan.exam.dto.ProblemAnswerPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemAnswerRepo extends JpaRepository<ProblemAnswer, ProblemAnswerPK> {
    ProblemAnswer findByUserIdAndProblemId(long userId, long problemId);
}
