package com.example.woyan.school.service.impl;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.school.dao.CollageRepo;
import com.example.woyan.school.dao.ProCourseRepo;
import com.example.woyan.school.dao.ProvinceRepo;
import com.example.woyan.school.dao.SchoolRepo;
import com.example.woyan.school.dto.Collage;
import com.example.woyan.school.dto.Province;
import com.example.woyan.school.dto.School;
import com.example.woyan.school.service.SchoolService;
import com.example.woyan.school.vo.ReturnProCourses;
import com.example.woyan.school.vo.ReturnSchools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepo schoolRepo;
    @Autowired
    private ProvinceRepo provinceRepo;
    @Autowired
    private CollageRepo collageRepo;
    @Autowired
    private ProCourseRepo proCourseRepo;

    @Override
    public School getSchoolByCode(long schoolCode) {
        if (schoolCode == 0){
            return null;
        }
        return schoolRepo.findBySchoolCode(schoolCode);
    }

    @Override
    public ReturnCode getAllSchool() {
        List<Province> provinces = provinceRepo.findAll();
        List<ReturnSchools> returnSchools = new ArrayList<>();
        for (Province province : provinces){
            returnSchools.add(new ReturnSchools(province, schoolRepo.findAllByProvinceId(province.getProvinceId())));
        }
        return new ReturnCode<>("200", returnSchools);
    }

    @Override
    public ReturnCode getAllGraduateSchool() {
        List<Province> provinces = provinceRepo.findAll();
        List<ReturnSchools> returnSchools = new ArrayList<>();
        for (Province province : provinces){
            List<School> schools = schoolRepo.findAllByProvinceId(province.getProvinceId());
            for (School school : schools){
                if (!school.isGraduate()){
                    schools.remove(school);
                }
            }
            returnSchools.add(new ReturnSchools(province, schools));
        }
        return new ReturnCode<>("200", returnSchools);
    }

    @Override
    public ReturnCode getAllProCourseBySchoolCode(long schoolCode) {
        List<Collage> collages = collageRepo.findAllBySchoolCode(schoolCode);
        List<ReturnProCourses> returnProCourses = new ArrayList<>();
        for (Collage collage: collages){
            returnProCourses.add(new ReturnProCourses(collage, proCourseRepo.findAllByCollageId(collage.getCollageId())));
        }
        return new ReturnCode<>("200", returnProCourses);
    }
}
