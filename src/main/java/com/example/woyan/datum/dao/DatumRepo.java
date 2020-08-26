package com.example.woyan.datum.dao;

import com.example.woyan.datum.dto.Datum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatumRepo extends JpaRepository<Datum,Long> {
    Page<Datum> findAllBySchoolCodeAndProCourseId(long schoolCode, long proCourseId, Pageable pageable);
    Page<Datum> findAllBySchoolCode(long schoolCode,Pageable pageable);
    Datum findByDatumId(long id);
    Page<Datum> findAllByTitleLikeOrIntroLikeAndSchoolCode(String key, String key2, long schoolCode, Pageable pageable);
}
