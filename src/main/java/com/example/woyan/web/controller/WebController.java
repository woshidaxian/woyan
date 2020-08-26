package com.example.woyan.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/buymaterial.html")
    public String getBuyMaterialHTML(){
        return "/html/buymaterial.html";
    }

    @GetMapping("/buyvideos.html")
    public String getBuyVideosHTML(){
        return "/html/buyvideos.html";
    }

    @GetMapping("/exam.html")
    public String getExamHTML(){
        return "/html/exam.html";
    }

    @GetMapping("/examination.html")
    public String getExamInationHTML(){
        return "/html/examination.html";
    }

    @GetMapping("/forum.html")
    public String getForumHTML(){
        return "/html/forum.html";
    }

    @GetMapping("/freema.html")
    public String getFreeMaHTML(){
        return "/html/freema.html";
    }

    @GetMapping("/index.html")
    public String getIndexHTML(){
        return "/html/index.html";
    }

    @GetMapping("/kaoyannews.html")
    public String getKaoYanNewsHTML(){
        return "/html/kaoyannews.html";
    }

    @GetMapping("/material.html")
    public String getMaterialHTML(){
        return "/html/material.html";
    }

    @GetMapping("/news.html")
    public String getNewsHTML(){
        return "/html/news.html";
    }

    @GetMapping("/nofreema.html")
    public String getNoFreeMaHTML(){
        return "/html/nofreema.html";
    }

    @GetMapping("/talkpage.html")
    public String getTalkPageHTML(){
        return "/html/talkpage.html";
    }

    @GetMapping("/videos.html")
    public String getVideosHTML(){
        return "/html/videos.html";
    }

    @GetMapping("/videosDetial.html")
    public String getVideosDetailHTML(){
        return "/html/videosDetail.html";
    }
}
