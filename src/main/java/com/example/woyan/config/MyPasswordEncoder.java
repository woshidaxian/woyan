package com.example.woyan.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密
 */
public class MyPasswordEncoder implements PasswordEncoder {
    // 密码加密
    @Override
    public String encode(CharSequence charSequence) {
        return new BCryptPasswordEncoder().encode(charSequence.toString());
    }

    @Override
    // 判断密码是否和数据库中加密后的密码相同（第一个参数是输入的密码，第二个是数据库中存储的密码）
    public boolean matches(CharSequence charSequence, String s) {
        return new BCryptPasswordEncoder().matches(charSequence, s);
    }
}
