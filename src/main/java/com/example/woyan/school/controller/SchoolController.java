package com.example.woyan.school.controller;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.school.dao.SchoolRepo;
import com.example.woyan.school.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.RC2ParameterSpec;

@RestController
@CrossOrigin
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    /**
     * 已测试：2019年11月20日 20:27:55
     * @return 所有省和学校
     */
    @GetMapping("/getAllSchool")
    public ReturnCode getAllSchool(){
        return schoolService.getAllSchool();
    }

    /**
     * 已测试：2019年11月20日 20:28:23
     * @return 所有可以考研的学校
     */
    @GetMapping("/getAllGraduateSchool")
    public ReturnCode getAllGraduateSchool(){
        return schoolService.getAllGraduateSchool();
    }

    /**
     * 根据学校代码获取所有的专业课(已测试：2019年11月20日 21:47:21)
     * @param schoolCode 学校代码
     * @return 学院数组 + 专业课数组
     */
    @GetMapping("/getAllProCourseBySchoolCode")
    public ReturnCode getAllProCourseBySchoolCode(@RequestParam long schoolCode){
        return schoolService.getAllProCourseBySchoolCode(schoolCode);
    }
}
