package com.example.woyan.user.dao;

import com.example.woyan.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findFirstByAccount(String account);
    User findByUserId(long user_id);
    User findByPhone(String phone);
}
