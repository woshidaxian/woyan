package com.example.woyan.exam.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface ExamService {
    /**
     * 获取所有的考试信息
     * @param schoolCode 学校代码（不传或者传0，默认为已经报考的学校）
     * @param proCourseId 专业课Id（不传或者为0，默认是所有专业课的考试）
     * @param pageNum 第几页
     * @return ArrayList of ReturnExam
     */
    ReturnCode getExams(long schoolCode, long proCourseId, int pageNum);

    /**
     * 增加考试预约
     * @param id 考试的id
     * @return true or false
     */
    ReturnCode addExamAppoint(long id);

    /**
     * 参加考试
     * @param id 考试id
     * @return true or false
     */
    ReturnCode joinExam(long id);

    /**
     * 获取我的考试信息（已经参与并且拥有成绩的）
     * @param pageNum 第几页
     * @return ArrayList of MyExam
     */
    ReturnCode getMyExamInfo(int pageNum);

    /**
     * 分页获取我的考试预约信息
     * @param pageNum 第几页
     * @return ArrayList of Exam
     */
    ReturnCode getMyExamAppoint(int pageNum);

    /**
     * 获取一次考试的详细信息，包括所有的题目、我的答案、得分等
     * @param id 考试的id
     * @return 所有的题目、我的答案、得分等
     */
    ReturnCode getExamDetail(long id);

    /**
     * 保存考试答案
     * @param problemId 考试题目id
     * @param answer 结果（如果是多选题用","-英文状态下的逗号分隔每个选项答案）
     * @return true or false
     */
    ReturnCode saveExamAnswer(long problemId, String answer);

    /**
     * 交卷
     * @param id 考试id
     * @return true or false
     */
    ReturnCode saveExam(long id);
}
