package com.example.woyan.info.dao;

import com.example.woyan.info.dto.InfoTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoThemeRepo extends JpaRepository<InfoTheme, Long> {
    InfoTheme findByThemeId(long themeId);
}
