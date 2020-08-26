package com.example.woyan.school.dao;

import com.example.woyan.school.dto.Collage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollageRepo extends JpaRepository<Collage, Long> {
    Collage findFirstBySchoolCode(long schoolCode);
    List<Collage> findAllBySchoolCode(long schoolCode);
}
