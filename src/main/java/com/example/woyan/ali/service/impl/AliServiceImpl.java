package com.example.woyan.ali.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.woyan.ali.dto.ALiSMSCode;
import com.example.woyan.ali.repo.ALiSMSCodeRepo;
import com.example.woyan.ali.service.AliService;
import com.example.woyan.returnmsg.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AliServiceImpl implements AliService {
    @Autowired
    private ALiSMSCodeRepo aLiSmsCodeRepo;

    public static final int TYPE_REGISTER = 1;
    public static final int TYPE_CHANGE_PASS = 2;

    // 短信签名
    private static final String SIGN_NAME = "蜗研考研";
    // 注册用的模版
    private static final String REGISTER_TEMPLATE_CODE = "SMS_177540614";
    // 修改密码用的模版
    private static final String CHANGE_PASS_TEMPLATE_CODE = "SMS_177550623";

    private static final String ACCESS_KEY_ID = "LTAI4FvALacmTc5E5yq3hmfk";
    private static final String ACCESS_KEY_SECRET = "QxV1GqXWd6T0nABC768aBtbv7RumE3";


    private Logger logger = LoggerFactory.getLogger(AliServiceImpl.class);

    @Override
    public ReturnCode sendSMS(String phone, int type) {

        if (type != TYPE_REGISTER && type != TYPE_CHANGE_PASS){
            return new ReturnCode<>("201", false);
        }

        ALiSMSCode aLiSMSCode = aLiSmsCodeRepo.findByPhoneAndGenre(phone, type);
        // 同一个手机号 一分钟只允许调用一次
        // 如果一分钟以内已经发送过短信，返回 请求过于频繁
        if (aLiSMSCode != null && aLiSMSCode.getDate().getTime() - 9*60*1000 > new Date().getTime()){
            return ReturnCode.TOO_OFTEN;
        }
        // 设置短信过期时间为10分钟
        if (aLiSMSCode == null){
            aLiSMSCode = new ALiSMSCode(phone, getSMSCode(), type, new Date(new Date().getTime() + 10*60*1000));
        }else {
            aLiSMSCode.setCode(getSMSCode());
            aLiSMSCode.setDate(new Date(new Date().getTime() + 10*60*1000));
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 设置签名
        request.putQueryParameter("SignName", SIGN_NAME);
        // 设置模版
        request.putQueryParameter("TemplateCode", type == 1?REGISTER_TEMPLATE_CODE:CHANGE_PASS_TEMPLATE_CODE);
        // 设置验证码
        Map<String, String> code = new HashMap<>();
        code.put("code", aLiSMSCode.getCode());
        String codeJSON = JSON.toJSONString(code);
        request.putQueryParameter("TemplateParam", codeJSON);
        // 设置手机号码
        request.putQueryParameter("PhoneNumbers", phone);
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            if (jsonObject.get("Code").equals("OK")){
                logger.info(phone + " save new Code: " + aLiSMSCode.getCode());
                aLiSmsCodeRepo.save(aLiSMSCode);
                return new ReturnCode<>("200", aLiSMSCode);
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ReturnCode<>("201", false);
    }

    // 生成六位
    private String getSMSCode() {
        int code = new Random(new Date().getTime()).nextInt() % 1000000;
        if (code < 0){
            code = -code;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(code));
        while (stringBuilder.length() < 6){
            stringBuilder.insert(0, 0);
        }
        return stringBuilder.toString();
    }
}
