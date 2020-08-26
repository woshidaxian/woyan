package com.example.woyan.ali.service;

import com.example.woyan.returnmsg.ReturnCode;

public interface AliService {
    /**
     * 发送短信的方法
     * @param phone 手机号
     * @param type 注册（1）/修改密码（2）
     * @return 发送成功： 200 验证码 失败：201 false
     */
    ReturnCode sendSMS(String phone, int type);
}
