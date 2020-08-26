package com.example.woyan.user.service;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.user.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 修改用户信息
     * @param password 修改后的密码
     * @param oldPassword 修改前的密码
     * @param phone 修改后的手机号
     * @param code 修改手机号需要的验证码
     * @param headimg 修改头像
     * @return User
     */
    public ReturnCode changeUser(String password,String oldPassword,String phone,String code,MultipartFile headimg);

    /**
     * 注册
     * @param account 帐号
     * @param password 密码
     * @param phone 手机号
     * @param schoolCode 报考的学校
     * @param originalCode 原来所在学校
     * @param code 手机验证码
     * @return 200:true
     */
    public ReturnCode register(String account, String password, String phone, long schoolCode, long originalCode, String code);

    /**
     * 发送验证码
     * @param phone 手机号
     * @param type 类型： 1、注册，2、修改用户信息
     * @return 200:true
     */
    public ReturnCode getCode(String phone, int type);

    /**
     * 获取用户信息的方法
     * @return User
     */
    public ReturnCode getUserInfo();

    /**
     * 根据请求的token获取用户信息
     * @return 如果存在返回User，否则返回null
     */
    public User getUserByToken();

    /**
     * 根据id获取用户信息
     * @param userId 用户id
     */
    public User getUserById(long userId);
}
