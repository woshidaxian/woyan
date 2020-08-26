package com.example.woyan.quetionBank.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface QuestionBankService {
    /**
     * 分页获取题库中的所有题目
     * @param pageNum 第几页
     * @param proCourseId 专业课id
     * @return Question数组 + 总数 + 总页数 + 是否已经做过
     */
    ReturnCode getQuestions(int pageNum, long proCourseId);

    /**
     * 根据id， 获取一题的详细信息
     * @return Question
     */
    ReturnCode getQuestionById(long id);

    /**
     * 提交答案
     * @param id 题目id
     * @param answer 结果
     * @return Question的 详细信息 和 标准答案
     */
    ReturnCode addQuestionAnswerById(long id, String answer);
}
