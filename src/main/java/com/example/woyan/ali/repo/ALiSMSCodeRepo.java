package com.example.woyan.ali.repo;

import com.example.woyan.ali.dto.ALiSMSCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ALiSMSCodeRepo extends JpaRepository<ALiSMSCode, Long> {
    ALiSMSCode findByPhoneAndGenre(String phone, int genre);
}
