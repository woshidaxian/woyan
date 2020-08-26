package com.example.woyan.user.service.impl;

import com.example.woyan.ali.dto.ALiSMSCode;
import com.example.woyan.ali.repo.ALiSMSCodeRepo;
import com.example.woyan.ali.service.AliService;
import com.example.woyan.ali.service.impl.AliServiceImpl;
import com.example.woyan.config.MyPasswordEncoder;
import com.example.woyan.school.dao.SchoolRepo;
import com.example.woyan.user.dto.User;
import com.example.woyan.user.dao.UserRepo;
import com.example.woyan.returnmsg.ReturnCode;
import com.example.woyan.user.service.UserService;
import com.example.woyan.user.vo.ReturnUser;
import com.example.woyan.videoCourse.dto.MyVCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepo userRepo;
    @Autowired
    private void setUserRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    @Autowired
    private AliService aliService;
    @Autowired
    private SchoolRepo schoolRepo;
    @Autowired
    private ALiSMSCodeRepo ALiSmsCodeRepo;

    // 帐号：长度5~16，可以包含字母，数字和下划线
    private static final String ACCOUNT_MATCHER = "^[a-zA-Z0-9_]{5,16}$";
    // 密码：长度至少6~16位，需要包含 数字、字母、特殊符号中的至少两种
    private static final String PASS_MATCHER = "^(?![a-zA-Z]+$)(?![0-9]+$)(?![\\W_]+$)[a-zA-Z0-9\\W_]{6,16}$";

    /**
     * 自定义生成UserDetail
     * @param s 用户名
     * @return  UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) {
        if (getUserByToken() != null){
            // 已登录
            throw new BadCredentialsException("logined");
        }
        UserDetails userDetails = null;
        User user = userRepo.findFirstByAccount(s);
        if (user == null){ //如果用户为空，抛出异常
            throw new BadCredentialsException("NoUser");
        }
        Collection<GrantedAuthority> authorities = getAuthorities(user);
        // 默认用户权限正常（没有封号、禁言等）
        userDetails = new org.springframework.security.core.userdetails.User(s,
                user.getPassword(),true,true,true,true,authorities);
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getAuthority()));
        return authorities;
    }

    @Override
    public ReturnCode register(String account, String password, String phone, long schoolCode, long originalCode, String code) {
        // 如果用户名已存在
        if (userRepo.findFirstByAccount(account) != null){
            return ReturnCode.REGISTER_ACCOUNT_EXIST;
        }
        // 如果手机号已存在
        if (userRepo.findByPhone(phone) != null){
            return ReturnCode.REGISTER_PHONE_EXIST;
        }
        // 如果帐号不符合要求
        if (!Pattern.matches(ACCOUNT_MATCHER, account)){
            return ReturnCode.REGISTER_WRONG_ACCOUNT;
        }
        // 如果密码不符合要求
        if (!Pattern.matches(PASS_MATCHER, password)){
            return ReturnCode.REGISTER_PASSWORD;
        }
        // 如果要报考的学校不存在 或者 原学校不存在
        if (schoolRepo.findBySchoolCode(schoolCode) == null || schoolRepo.findBySchoolCode(originalCode) == null){
            return ReturnCode.REGISTER_NOT_FOUND_SCHOOL;
        }
        // 如果验证码不存在或者验证码超时
        ALiSMSCode ALiSmsCode = ALiSmsCodeRepo.findByPhoneAndGenre(phone, AliServiceImpl.TYPE_REGISTER);
        if (ALiSmsCode == null || ALiSmsCode.getDate().getTime() > new Date().getTime() || !ALiSmsCode.getCode().equals(code)){
            return ReturnCode.REGISTER_WRONG_CODE;
        }
        ALiSmsCodeRepo.delete(ALiSmsCode);
        User user = new User(account, new MyPasswordEncoder().encode(password), phone, schoolCode, originalCode);
        userRepo.save(user);
        userRepo.flush();
        return new ReturnCode<>("200", true);
    }

    @Override
    public ReturnCode getCode(String phone, int type) {
        if(type != AliServiceImpl.TYPE_REGISTER && type != AliServiceImpl.TYPE_CHANGE_PASS){
            return ReturnCode.UNKNOWN_WRONG;
        }
        ReturnCode returnCode = aliService.sendSMS(phone, type);
        if (returnCode.getStatus().equals("200")){
            return new ReturnCode<>("200", true);
        }
        return returnCode;
    }

    @Override
    public ReturnCode changeUser(String password, String oldPassword, String phone, String code, MultipartFile headimg) {
        //找出token中的User信息
        User user = getUserByToken();
        //如果用户不存在，返回
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        //如果有密码，则判断旧密码是否一致，一致可以修改密码（通过手机号修改没写）
        if (password != null){
            MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();
            // 判断新密码是否符合要求
            if (Pattern.matches(PASS_MATCHER, password)){
                if(oldPassword != null){
                    // 如果旧密码 一致，判断新密码是否符合要求，如果旧密码错误，返回错误信息
                    if (myPasswordEncoder.matches(oldPassword,user.getPassword())){
                        // 修改密码
                        user.setPassword(myPasswordEncoder.encode(password));
                    }else {
                        return ReturnCode.LOGIN_WRONG_ACCOUNT_OR_PASSWORD;
                    }
                } else if (code != null) {
                    // 如果用户没有输入旧密码，默认根据手机号验证码修改密码
                    ALiSMSCode ALiSmsCode = ALiSmsCodeRepo.findByPhoneAndGenre(user.getPhone(), AliServiceImpl.TYPE_CHANGE_PASS);
                    if (ALiSmsCode == null){
                        return ReturnCode.UNKNOWN_WRONG;
                    }
                    // 如果验证码超时
                    if (ALiSmsCode.getDate().getTime() <= new Date().getTime()){
                        return ReturnCode.CODE_TIME_OUT;
                    }
                    // 如果验证码正确，修改密码
                    if (ALiSmsCode.getCode().equals(code)){
                        user.setPassword(myPasswordEncoder.encode(password));
                        ALiSmsCodeRepo.delete(ALiSmsCode);
                        ALiSmsCodeRepo.flush();
                    }else {
                        return ReturnCode.REGISTER_WRONG_CODE;
                    }

                }
            }else {
                // 如果新密码不符合要求，返回错误信息
                return ReturnCode.REGISTER_PASSWORD;
            }
        }
        userRepo.save(user);
        userRepo.flush();
        return new ReturnCode<User>("200",user);
    }

    @Override
    public ReturnCode getUserInfo(){
        User user = getUserByToken();
        if (user == null){
            return ReturnCode.LOGIN_NO_USER;
        }
        return new ReturnCode<>("200", new ReturnUser(schoolRepo.findBySchoolCode(user.getSchoolCode()).getName(),
                schoolRepo.findBySchoolCode(user.getOriginalCode()).getName(), user));
    }

    @Override
    public User getUserByToken() {
        MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();
        logger.info("user:" + myPasswordEncoder.encode("user"));
        logger.info("admin:" + myPasswordEncoder.encode("admin"));
        logger.info("upper:" + myPasswordEncoder.encode("upper"));
        logger.info("teacher:" + myPasswordEncoder.encode("teacher"));
        logger.info("check:" + myPasswordEncoder.encode("check"));
        logger.info("user:" + myPasswordEncoder.encode("user"));

        try{
            return userRepo.findFirstByAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User getUserById(long userId) {
        return userRepo.findByUserId(userId);
    }
}
