package com.example.woyan.quetionBank.controller;

import com.example.woyan.quetionBank.service.QuestionBankService;
import com.example.woyan.returnmsg.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;

    @GetMapping("/getQuestions")
    public ReturnCode getQuestions(@RequestParam int pageNum,
                                   @RequestParam long proCourseId){
        return questionBankService.getQuestions(pageNum, proCourseId);
    }

    @GetMapping("/getQuestionById")
    public ReturnCode getQuestionById(@RequestParam long id){
        return questionBankService.getQuestionById(id);
    }

    @PutMapping("/addQuestionAnswerById")
    public ReturnCode addQuestionAnswerById(@RequestParam long id,
                                            @RequestParam String answer){
        return questionBankService.addQuestionAnswerById(id, answer);
    }

}
