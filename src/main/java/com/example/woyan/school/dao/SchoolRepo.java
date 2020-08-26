package com.example.woyan.school.dao;

import com.example.woyan.school.dto.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepo extends JpaRepository<School,Long> {
    School findBySchoolCode(long schoolCode);
    List<School> findAllByProvinceId(long provinceId);
}
