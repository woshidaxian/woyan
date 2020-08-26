package com.example.woyan.datum.service.impl;

import com.example.woyan.datum.service.DatumService;
import com.example.woyan.school.service.SchoolService;
import com.example.woyan.user.dto.User;
import com.example.woyan.datum.dto.Datum;
import com.example.woyan.datum.dto.DatumCollect;
import com.example.woyan.school.dao.SchoolRepo;
import com.example.woyan.user.dao.UserRepo;
import com.example.woyan.datum.dao.DatumCollectRepo;
import com.example.woyan.datum.dao.DatumRepo;
import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.datum.vo.ReturnDatum;
import com.example.woyan.datum.vo.ReturnDatumList;
import com.example.woyan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DatumServiceImpl implements DatumService {
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private DatumRepo datumRepo;
    @Autowired
    private DatumCollectRepo datumCollectRepo;

    @Override
    public ReturnCode getDatumList(long schoolCode, long proCourseId, int pageNum, String order) {
        User user = userService.getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果没有学校代码，默认为用户报考的学校
        if (schoolService.getSchoolByCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 分页
        // 默认按照最新排序，如果设置排序方式为最热，则按照最热排序
        String property = "seeNum";
        Sort.Direction direction = Sort.Direction.DESC;
        if(order.equals("new")){
            property = "datumId";
            direction = Sort.Direction.DESC;
        }else if (order.equals("price")){
            property = "price";
            direction = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(direction,property));
        // 如果专业课id为0，表示查找该学校下的所有资料
        Page<Datum> datumPage = null;
        if (proCourseId == 0){
            datumPage = datumRepo.findAllBySchoolCode(schoolCode, pageable);
        }else { // 如果专业课id不为0，就根据专业课和学校代码
            datumPage = datumRepo.findAllBySchoolCodeAndProCourseId(schoolCode, proCourseId, pageable);
        }
        // 添加收藏等数据
        List<ReturnDatum> datumList = new ArrayList<>();
        for (Datum datum : datumPage){
            // 返回列表不需要详细简介
            datum.setPresentation("");
            DatumCollect collect = datumCollectRepo.findByUserIdAndDatumId(user.getUserId(), datum.getDatumId());
            datumList.add(new ReturnDatum(datum, collect !=null && collect.isCollect()));
        }
        return new ReturnCode<>("200", new ReturnDatumList(datumPage.getTotalElements(),
                datumPage.getTotalPages(), datumList));
    }

    @Override
    public ReturnCode getDatumById(long id) {
        Datum datum = datumRepo.findByDatumId(id);
        // 如果资料不存在
        if (datum == null){
            return ReturnCode.UNKNOWN_WRONG;
        }
        User user = userService.getUserByToken();
        ReturnDatum returnDatum = new ReturnDatum(datum, false);
        // 如果用户登录，修改收藏信息
        if (user != null){
            DatumCollect collect = datumCollectRepo.findByUserIdAndDatumId(user.getUserId(),datum.getDatumId());
            returnDatum.setCollect(collect != null && collect.isCollect());
        }
        return new ReturnCode<>("200", returnDatum);
    }

    @Override
    public ReturnCode addDatumCollect(long id) {
        User user = userService.getUserByToken();
        // 如果用户未登录
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果资料不存在
        Datum datum = datumRepo.findByDatumId(id);
        if (datum == null) {
            return ReturnCode.UNKNOWN_WRONG;
        }
        // 判断收藏情况
        DatumCollect collect = datumCollectRepo.findByUserIdAndDatumId(user.getUserId(), id);
        if (collect == null){ // 如果没有收藏过，添加新的收藏情况
            collect = new DatumCollect(user.getUserId(), id);
            datumCollectRepo.save(collect);
        }else { // 否则修改收藏状态
            collect.setCollect(!collect.isCollect());
            datumCollectRepo.save(collect);
        }
        datumCollectRepo.flush();
        return new ReturnCode<>("200", collect.isCollect());
    }

    @Override
    public ReturnCode searchDatum(String key, String order, int pageNum, long schoolCode) {
        User user = userService.getUserByToken();
        // 如果用户没有登陆
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        // 如果没有学校代码，默认为用户报考的学校
        if (schoolService.getSchoolByCode(schoolCode) == null){
            schoolCode = user.getSchoolCode();
        }
        // 默认按热度排序
        String property = "seeNum";
        Sort.Direction direction = Sort.Direction.DESC;
        if(order.equals("new")){
            property = "datumId";
            direction = Sort.Direction.DESC;
        }else if (order.equals("price")){
            property = "price";
            direction = Sort.Direction.ASC;
        }
        // 分页搜索
        Pageable pageable = PageRequest.of(pageNum, 30, Sort.by(direction,property));
        key = "%" + key + "%";
        Page<Datum> datumPage = datumRepo.findAllByTitleLikeOrIntroLikeAndSchoolCode(key,key,schoolCode,pageable);
        List<ReturnDatum> datumList = new ArrayList<>();
        for (Datum datum : datumPage){
            DatumCollect collect = datumCollectRepo.findByUserIdAndDatumId(user.getUserId(),datum.getDatumId());
            datumList.add(new ReturnDatum(datum, collect != null && collect.isCollect()));
        }
        return new ReturnCode<>("200", new ReturnDatumList(datumPage.getTotalElements(),datumPage.getTotalPages(),datumList));
    }
}
