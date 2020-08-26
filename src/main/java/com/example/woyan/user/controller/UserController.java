package com.example.woyan.user.controller;

import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 修改用户信息（已测试：2019年11月20日 20:28:48） *修改头像没写
     * @param password 修改后的密码
     * @param oldPassword 修改前的密码
     * @param phone 修改后的手机号
     * @param code 修改手机号需要的验证码
     * @param headimg 修改头像
     * @return User
     */
    @PostMapping("/changeUser")
    public ReturnCode changeUser(@RequestParam(required = false) String password,
                                 @RequestParam(required = false) String oldPassword,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) String code,
                                 @RequestParam(required = false)MultipartFile headimg){
        return userService.changeUser(password,oldPassword,phone,code,headimg);
    }

    /**
     * 已测试：2019年11月20日 20:58:43
     */
    @PostMapping("/register")
    public ReturnCode register(@RequestParam String account,
                               @RequestParam String password,
                               @RequestParam String phone,
                               @RequestParam long schoolCode,
                               @RequestParam long originalCode,
                               @RequestParam String code){
        return userService.register(account, password, phone, schoolCode, originalCode, code);
    }

    /**
     * 已测试：2019年11月20日 20:59:13
     */
    @GetMapping("/getUserInfo")
    public ReturnCode getUserInfo(){
        return userService.getUserInfo();
    }

    /**
     * 已测试：2019年11月20日 20:59:46
     */
    @PutMapping("/getCode")
    public ReturnCode getCode(@RequestParam String phone,
                              @RequestParam int type){
        return userService.getCode(phone, type);
    }
}
