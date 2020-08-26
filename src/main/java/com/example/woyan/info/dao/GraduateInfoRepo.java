package com.example.woyan.info.dao;

import com.example.woyan.info.dto.GraduateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduateInfoRepo extends JpaRepository<GraduateInfo, Long> {
    Page<GraduateInfo> findAllByThemeId(long themeId, Pageable pageable);
    Page<GraduateInfo> findAll(Pageable pageable);
    GraduateInfo findByInfoId(long infoId);
}
