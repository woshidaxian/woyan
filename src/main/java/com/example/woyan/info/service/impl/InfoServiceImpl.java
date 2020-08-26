package com.example.woyan.info.service.impl;

import com.example.woyan.info.dao.GraduateInfoRepo;
import com.example.woyan.info.dao.InfoThemeRepo;
import com.example.woyan.info.dto.GraduateInfo;
import com.example.woyan.info.service.InfoService;
import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.school.dto.School;
import com.example.woyan.school.service.SchoolService;
import com.example.woyan.user.dto.User;
import com.example.woyan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private InfoThemeRepo infoThemeRepo;
    @Autowired
    private GraduateInfoRepo graduateInfoRepo;

    @Override
    public ReturnCode getInfoList(int pageNum, long schoolCode, long themeId) {
        User user = userService.getUserByToken();
        // 如果用户不存在
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果学校代码为0，或者学校不存在，默认为用户选择的学校
        if (schoolCode == 0 || schoolService.getSchoolByCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        Page<GraduateInfo> graduateInfos;
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(Sort.Direction.DESC, "time"));
        if (themeId == 0 || infoThemeRepo.findByThemeId(themeId) == null){
            graduateInfos = graduateInfoRepo.findAll(pageable);
        }else {
            graduateInfos = graduateInfoRepo.findAllByThemeId(themeId, pageable);
        }
        for (GraduateInfo info : graduateInfos){
            info.setContent("");
        }
        return new ReturnCode<>("200", graduateInfos);
    }

    @Override
    public ReturnCode getInfoById(long id) {
        return new ReturnCode<>("200", graduateInfoRepo.findByInfoId(id));
    }
}
