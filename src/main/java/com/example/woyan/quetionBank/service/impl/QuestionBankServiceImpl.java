package com.example.woyan.quetionBank.service.impl;

import com.example.woyan.quetionBank.dao.QuestionBankRepo;
import com.example.woyan.quetionBank.dao.ResolveQuestionRepo;
import com.example.woyan.quetionBank.dto.QuestionBank;
import com.example.woyan.quetionBank.dto.ResolveQuestion;
import com.example.woyan.quetionBank.service.QuestionBankService;
import com.example.woyan.quetionBank.vo.ReturnQuestion;
import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.user.dto.User;
import com.example.woyan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionBankServiceImpl implements QuestionBankService {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionBankRepo questionBankRepo;
    @Autowired
    private ResolveQuestionRepo resolveQuestionRepo;

    @Override
    public ReturnCode getQuestions(int pageNum, long proCourseId) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(Sort.Direction.ASC, "questionId"));
        Page<QuestionBank> questionBanks = questionBankRepo.findAllByProCourseId(proCourseId, pageable);
        List<ReturnQuestion> returnQuestions = new ArrayList<>();
        for (QuestionBank questionBank: questionBanks){
            questionBank.setAnswer("");
            ResolveQuestion resolveQuestion = resolveQuestionRepo.findByUserIdAndQuestionId(user.getUserId(), questionBank.getQuestionId());
            returnQuestions.add(new ReturnQuestion(questionBank,
                    resolveQuestion == null,
                   null));
        }
        return new ReturnCode<>("200", returnQuestions);
    }

    @Override
    public ReturnCode getQuestionById(long id) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        QuestionBank questionBank = questionBankRepo.findByQuestionId(id);
        if (questionBank == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ResolveQuestion resolveQuestion = resolveQuestionRepo.findByUserIdAndQuestionId(user.getUserId(), id);
        return new ReturnCode<>("200", new ReturnQuestion(questionBank,
                resolveQuestion == null,
                resolveQuestion == null?null: resolveQuestion.getAnswer()));
    }

    @Override
    public ReturnCode addQuestionAnswerById(long id, String answer) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        QuestionBank questionBank = questionBankRepo.findByQuestionId(id);
        if (questionBank == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        resolveQuestionRepo.save(new ResolveQuestion(user.getUserId(),id,answer));
        return new ReturnCode<>("200", true);
    }
}
