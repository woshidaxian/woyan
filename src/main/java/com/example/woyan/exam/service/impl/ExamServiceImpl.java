package com.example.woyan.exam.service.impl;

import com.example.woyan.exam.dao.*;
import com.example.woyan.exam.dto.*;
import com.example.woyan.exam.service.ExamService;
import com.example.woyan.exam.vo.ReturnExam;
import com.example.woyan.exam.vo.ReturnProblem;
import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.school.service.SchoolService;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ExamRepo examRepo;
    @Autowired
    private ExamAppointRepo examAppointRepo;
    @Autowired
    private MyExamScoreRepo myExamScoreRepo;
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private ProblemAnswerRepo problemAnswerRepo;

    @Override
    public ReturnCode getExams(long schoolCode, long proCourseId, int pageNum) {
        User user = userService.getUserByToken();
        // 如果用户没有登录，返回对应信息
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果没有选择学校，默认为用户报考学校
        if (schoolService.getSchoolByCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 分页查找还没有开始的考试，每页30条
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(Sort.Direction.ASC, "beginTime"));
        Page<Exam> examPage;
        if (proCourseId == 0){
            examPage = examRepo.findAllByStatusAndSchoolCode(0, schoolCode, pageable);
        }else {
            examPage = examRepo.findAllByStatusAndSchoolCodeAndProCourseId(0, schoolCode, proCourseId, pageable);
        }
        return new ReturnCode<>("200", examPage);
    }

    @Override
    public ReturnCode addExamAppoint(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        Exam exam = examRepo.findByExamId(id);
        // 如果预约的考试不存在，返回未知错误
        if (exam == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ExamAppoint examAppoint = new ExamAppoint(user.getUserId(), exam);
        examAppointRepo.save(examAppoint);
        examAppointRepo.flush();
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode joinExam(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        Exam exam = examRepo.findByExamId(id);
        // 如果预约的考试不存在，返回未知错误
        if (exam == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 判断用户是否预约这场考试
        ExamAppoint examAppoint = examAppointRepo.findByUserIdAndExamId(user.getUserId(), exam.getExamId());
        if (examAppoint == null){
            return ReturnCode.HAVE_NO_APPOINT;
        }
        examAppoint.setBegin(true);
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode getMyExamInfo(int pageNum) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(Sort.Direction.ASC, "beginTime"));
        Page<MyExamScore> myExamScores = myExamScoreRepo.findAllByUserId(user.getUserId(), pageable);
        List<ReturnExam> returnExams = new ArrayList<>();
        for (MyExamScore score : myExamScores){
            returnExams.add(new ReturnExam(examRepo.findByExamId(score.getExamId()), score.getScore(), null));
        }
        return new ReturnCode<>("200", returnExams);
    }

    @Override
    public ReturnCode getMyExamAppoint(int pageNum) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(Sort.Direction.ASC, "beginTime"));
        Page<ExamAppoint> examAppoints = examAppointRepo.findAllByUserId(user.getUserId(), pageable);
        List<ReturnExam> returnExams = new ArrayList<>();
        for (ExamAppoint appoint : examAppoints){
            returnExams.add(new ReturnExam(examRepo.findByExamId(appoint.getExamId()), 0, null));
        }
        return new ReturnCode<>("200", returnExams);
    }

    @Override
    public ReturnCode getExamDetail(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果考试不存在，返回位置错误
        Exam exam = examRepo.findByExamId(id);
        if (exam == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 找到该场考试的所有题目，按照题号排序
        List<Problem> problems = problemRepo.findAllByExamIdOrderByOrderNumAsc(exam.getExamId());
        // 如果当前考试还没有结束，则不会返回答案
        if (exam.getEndTime().getTime() >= new Date().getTime()){
            for (Problem problem : problems){
                problem.setAnswer("");
            }
        }
        List<ReturnProblem> returnProblems = new ArrayList<>();
        for (Problem problem : problems){
            // 找到用户的答案
            ProblemAnswer problemAnswer = problemAnswerRepo.findByUserIdAndProblemId(user.getUserId(), problem.getProblemId());
            returnProblems.add(new ReturnProblem(problem, problemAnswer.getAnswer(), problemAnswer.getTime()));
        }
        ReturnExam returnExam = new ReturnExam(exam,
                myExamScoreRepo.findByUserIdAndExamId(user.getUserId(),exam.getExamId()).getScore(),
                returnProblems);
        return new ReturnCode<>("200", returnExam);
    }

    @Override
    public ReturnCode saveExamAnswer(long problemId, String answer) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 判断题目是否存在
        Problem problem = problemRepo.findByProblemId(problemId);
        if (problem == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ProblemAnswer problemAnswer = problemAnswerRepo.findByUserIdAndProblemId(user.getUserId(), problemId);
        if (problemAnswer == null){
            problemAnswer = new ProblemAnswer(user.getUserId(), problemId, answer);
        }else {
            problemAnswer.setAnswer(answer);
        }
        problemAnswerRepo.save(problemAnswer);
        problemAnswerRepo.flush();
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode saveExam(long id) {
        User user = userService.getUserByToken();
        // 如果用户不存在，返回未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果考试不存在，返回位置错误
        Exam exam = examRepo.findByExamId(id);
        if (exam == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ExamAppoint examAppoint = examAppointRepo.findByUserIdAndExamId(user.getUserId(), exam.getExamId());
        // 判断是否已经交卷
        if (examAppoint.isFinished()){
            return ReturnCode.EXAM_SAVED;
        }
        examAppoint.setFinished(true);
        MyExamScore myExamScore = new MyExamScore(exam, user.getUserId());
        examAppointRepo.save(examAppoint);
        examAppointRepo.flush();
        myExamScoreRepo.save(myExamScore);
        myExamScoreRepo.flush();
        return new ReturnCode<>("200", true);
    }
}
