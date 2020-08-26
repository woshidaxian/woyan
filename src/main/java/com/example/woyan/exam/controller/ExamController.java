package com.example.woyan.exam.controller;

import com.example.woyan.exam.service.ExamService;
import com.example.woyan.returnmsg.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;

@RestController
@CrossOrigin
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/getExams")
    public ReturnCode getExams(@RequestParam(required = false,defaultValue = "0") long schoolCode,
                               @RequestParam(required = false,defaultValue = "0") long proCourseId,
                               @RequestParam int pageNum){
        return examService.getExams(schoolCode, proCourseId, pageNum);
    }

    @PutMapping("/addExamAppoint")
    public ReturnCode addExamAppoint(@RequestParam long id){
        return examService.addExamAppoint(id);
    }

    @PutMapping("/joinExam")
    public ReturnCode joinExam(@RequestParam long id){
        return examService.joinExam(id);
    }

    @GetMapping("/getMyExamInfo")
    public ReturnCode getMyExamInfo(@RequestParam int pageNum){
        return examService.getMyExamInfo(pageNum);
    }

    @GetMapping("/getMyExamAppoint")
    public ReturnCode getMyExamAppoint(@RequestParam int pageNum){
        return examService.getMyExamAppoint(pageNum);
    }

    @GetMapping("/getExamDetail")
    public ReturnCode getExamDetail(@RequestParam long id){
        return examService.getExamDetail(id);
    }

    @PutMapping("/saveExamAnswer")
    public ReturnCode saveExamAnswer(@RequestParam long problemId,
                                     @RequestParam String answer){
        return examService.saveExamAnswer(problemId, answer);
    }

    @PutMapping("/saveExam")
    public ReturnCode saveExam(@RequestParam long id){
        return examService.saveExam(id);
    }

}
