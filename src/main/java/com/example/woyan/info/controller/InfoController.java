package com.example.woyan.info.controller;

import com.example.woyan.info.service.InfoService;
import com.example.woyan.returnmsg.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/getInfoList")
    public ReturnCode getInfoList(@RequestParam int pageNum,
                                  @RequestParam(required = false,defaultValue = "0") long schoolCode,
                                  @RequestParam(required = false,defaultValue = "0") long themeId){
        return infoService.getInfoList(pageNum, schoolCode, themeId);
    }

    @GetMapping("/getInfoById")
    public ReturnCode getInfoById(@RequestParam long id){
        return infoService.getInfoById(id);
    }

}
