package com.example.woyan.returnmsg;

public class ReturnCode<T> {
    //注册的返回信息
    public static final ReturnCode<String> REGISTER_WRONG_ACCOUNT = new ReturnCode<String>("211","用户名不符合要求");
    public static final ReturnCode<String> REGISTER_PASSWORD = new ReturnCode<String>("212","密码不符合要求");
    public static final ReturnCode<String> REGISTER_ACCOUNT_EXIST = new ReturnCode<String>("213","用户名已存在");
    public static final ReturnCode<String> REGISTER_PHONE_EXIST = new ReturnCode<String>("214","手机号已存在");
    public static final ReturnCode<String> REGISTER_WRONG_CODE = new ReturnCode<String>("215","验证码错误");
    public static final ReturnCode<String> REGISTER_NOT_FOUND_SCHOOL = new ReturnCode<String>("216","学校不存在");
    //登录的返回信息
    public static final ReturnCode<String> LOGIN_WRONG_ACCOUNT_OR_PASSWORD = new ReturnCode<String>("201","用户名或密码错误");
    public static final ReturnCode<String> LOGIN_NO_USER = new ReturnCode<String>("202","用户尚未登陆");
    public static final ReturnCode<String> CODE_TIME_OUT = new ReturnCode<String>("203","验证码超时");
    public static final ReturnCode<String> LOGIN_LOGINED = new ReturnCode<String>("204","用户已登录，请刷新页面");
    // 视频信息
    public static final ReturnCode<String> NO_BOUGHT = new ReturnCode<String>("301","视频尚未购买");
    public static final ReturnCode<String> NO_SEE_NUM = new ReturnCode<String>("302","当前课程可观看次数为0");
    // 其他
    public static final ReturnCode<String> UNKNOWN_WRONG = new ReturnCode<String>("303","未知错误");
    public static final ReturnCode<String> ITEM_DELETED = new ReturnCode<String>("304","已删除");
    public static final ReturnCode<String> TOO_OFTEN = new ReturnCode<String>("305","请求过于频繁");
    // 考试信息
    public static final ReturnCode<String> HAVE_NO_APPOINT = new ReturnCode<>("501", "预约信息不存在");
    public static final ReturnCode<String> EXAM_SAVED = new ReturnCode<>("502", "用户已交卷");


    private String status;
    private T data;

    public ReturnCode(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
