package com.example.woyan.quetionBank.dao;

import com.example.woyan.quetionBank.dto.ResolveQuestion;
import com.example.woyan.quetionBank.dto.ResolveQuestionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolveQuestionRepo extends JpaRepository<ResolveQuestion, ResolveQuestionPK> {
    ResolveQuestion findByUserIdAndQuestionId(long userId, long questionId);
}
