package com.example.woyan.school.service;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.school.dto.School;
import com.example.woyan.school.vo.ReturnSchools;

import java.util.List;

public interface SchoolService {
    /**
     * 根据学校代码获取学校信息
     * @param schoolCode 学校代码
     * @return School
     */
    School getSchoolByCode(long schoolCode);

    /**
     * 获取所有的学校信息（用于填写用户本身所在学校）
     * @return 省->学校
     */
    ReturnCode getAllSchool();

    /**
     * 获取所有的学校信息（用于填写用户本身所在学校）
     * @return 省->学校
     */
    ReturnCode getAllGraduateSchool();

    /**
     * 根据学校代码获取所有的学院和专业课
     * @param schoolCode 学校代码
     * @return 所有学院
     */
    ReturnCode getAllProCourseBySchoolCode(long schoolCode);
}
