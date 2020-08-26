var userData = null; // 用户数据
var schoolCode = sessionStorage.getItem("WoYanSchoolCode");  // 学校代码
var schoolName = sessionStorage.getItem("WoYanSchool");
chooseSchool(schoolCode, schoolName);

// 修改超链接的方法
var linkList = ["index.html","videos.html","material.html","examination.html","exam.html","kaoyannews.html","forum.html"];
function changeHref(linkUrl) {
    schoolCode = sessionStorage.getItem("WoYanSchoolCode");
    sessionStorage.setItem("WoYanSchoolCode",userData.schoolCode);
    var links = $(".layui-nav-item");
    for(var i = 0;i < links.length; ++i){
        links.eq(i).children("a").eq(0).attr("href",linkList[i] + linkUrl);
    }
}

// 存储学校并修改学校显示的方法
function chooseSchool(scode, sname) {
    if (scode === null || sname === null){
        return;
    }
    // console.log("chooseSchool: " + scode + ", " + sname);
    sessionStorage.setItem("WoYanSchoolCode",scode);
    sessionStorage.setItem("WoYanSchool",sname);
    schoolCode = scode;
    schoolName = sname;
    // 修改学校文字信息
    $('.school-name').text(sname)
}

// 获取URL参数的方法
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function disposeResponse(res) {
    if(res.data !== undefined){
        if (res.status === "202"){
            $(".sign-register").click();
        }else {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(data.data);
            });
        }
    }
}

// 获取用户信息的方法
function showUserInfo() {
    if (sessionStorage.getItem("token") !== undefined){
        $.ajax({
            url:'/getUserInfo',
            type:'get',
            headers:{
                "Authorization": sessionStorage.getItem("token")
            },
            async:false,
            success:function(data){
                if (data.status === "200"){
                    userData = data.data;
                    $(".sign-register").html(`
                        <img src="${userData.headimg}" alt="" class="head-logo">&nbsp;&nbsp;${userData.account}
                    `);
                    if (schoolCode == null || schoolName == null){
                        chooseSchool(userData.schoolCode, userData.school);
                    }

                }else{
                    disposeResponse(data)
                }
            },
            error:function(){
                alert('错误');
            }
        })
    }
}
showUserInfo();

// 获取所有省、学校信息的方法
function getSchoolInfo() {
    $.ajax({
        url:'/getAllGraduateSchool',
        type:'get',
        headers:{
            "Authorization": sessionStorage.getItem("token")
        },
        async:false,
        success:function(data){
            if (data.status == "200"){
                console.log(data.data)
                var provinceArray = data.data;
                var schoolArray = provinceArray[0].schools;
                for(var i=0;i<provinceArray.length;i++){
                    $('.top-area-a').append('<span class="province-style" index="'+ i +'">' + provinceArray[i].province.name + '</span>')
                }
                $('.province-style').eq(0).addClass('active-province');
                $('.province-style').click(function(){
                    $('.province-style').removeClass('active-province');
                    $('.province-style').eq($(this).attr('index')).addClass('active-province');
                    schoolArray = provinceArray[$(this).attr('index')].schools;
                    console.log(schoolArray)
                    $('.center-school-li').remove();
                    for(var j=0;j<schoolArray.length;j++){
                        $('.center-right').append('<span class="center-school-li" index="'+ j +'">'+ schoolArray[j].name +'</span>')
                    }
                    $('.center-school-li').click(function(){
                        // $('.school-name').text(schoolArray[$(this).attr('index')].name);
                        console.log(sign.type);
                        if(sign.type==1){
                            console.log(schoolArray[$(this).attr('index')]);
                            chooseSchool(schoolArray[$(this).attr('index')].schoolCode, schoolArray[$(this).attr('index')].name);
                        }else if(sign.type==0){
                            sign.registerSchool = schoolArray[$(this).attr('index')].name
                        }else{
                            sign.registerSchool1 = schoolArray[$(this).attr('index')].name
                        }
                        $('#con-school').fadeOut();
                    });
                });
                for(var j=0;j<schoolArray.length;j++){
                    $('.center-right').append('<span class="center-school-li" index="'+ j +'">'+ schoolArray[j].name +'</span>')
                }
                $('.center-school-li').click(function(){
                    // $('.school-name').text(schoolArray[$(this).attr('index')].name);
                    console.log(sign.type);
                    if(sign.type==1){
                        console.log(schoolArray[$(this).attr('index')]);
                        chooseSchool(schoolArray[$(this).attr('index')].schoolCode, schoolArray[$(this).attr('index')].name);
                    }else if(sign.type==0){
                        sign.registerSchool = schoolArray[$(this).attr('index')].name
                    }else{
                        sign.registerSchool1 = schoolArray[$(this).attr('index')].name
                    }
                    $('#con-school').fadeOut();
                });
            }else{
                disposeResponse(data);
            }
        },
        error:function(){
            alert('错误');
        }
    })
}
getSchoolInfo();

$('.school-div').click(function(){
    sign.type=1;
    $('#con-school').fadeIn();
})
$('.sign-register').click(function(){
    sign.signIsShow=true
});
var sign = new Vue({
    el:"#signInBlock",
    data:{
        signIsShow:false,
        isActive:false,
        SignIsActive:true,
        nowSign:true,
        register_step:true,
        moreHeight:true,
        btnIsActive:false,
        textCode:"获取验证码",
        time:60,
        timer:0,
        type:0,
        signAccount:'',
        signPassword:'',
        registerAccount:'',
        registerPassword:'',
        registerPhone:'',
        registerVerificationCode:'',
        registerSchool:'',
        registerSchool1:'',
        registerYear:'',
        registerObject:'',
        isChecked:false
    },
    methods:{
        btnS:function(){
            this.SignIsActive=true;
            this.isActive=false;
            this.nowSign=true;
            this.register_step=true;
            document.getElementsByClassName('block-white')[0].style.height='350px'
        },
        btnR:function(){
            this.isActive=true;
            this.SignIsActive=false;
            this.nowSign=false;
            document.getElementsByClassName('block-white')[0].style.height='510px'
        },
        btnSignX:function(){
            this.signIsShow=false;
            this.btnS()
        },
        btnSign:function(){
            $.ajax({
                url:'http://120.78.75.14:8006/login',
                type:'post',
                data:{"account":this.signAccount,"password":this.signPassword},
                success:function(data){
                    if (data.token !== undefined){
                        sessionStorage.setItem("token",data.token); // 保存token
                        location.reload()
                    }else {
                        disposeResponse(data)
                    }
                },
                error:function(){
                    alert('错误');
                }
            })
        },
        btnGetCode:function(){
            if(this.registerPhone==''){
                alert('手机号不能为空！！！')
            }else{
                $.ajax({
                    url:'/getCode',
                    type:'put',
                    data:{
                        phone: this.registerPhone,
                        type:1
                    },
                    success:function(data){
                        console.log(data);
                    },
                    error:function(){
                        alert('错误');
                    }
                })
                this.btnIsActive=true;
                this.timer = setInterval(() => {
                    if(this.time>=0){
                    this.textCode=this.time + '秒后重试';
                    this.time--;
                }else{
                    this.textCode='获取验证码';
                    this.btnIsActive=false;
                    clearInterval(this.timer);
                }
            },1000);
            }
        },
        btnNextStep:function(){
            this.register_step=false
        },
        btnRegister:function(){
            if(!this.registerAccount||!this.registerPassword||!this.registerPhone||!this.registerVerificationCode||this.registerSchool){
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg("信息不完整！");
                });
            }else {

            }
        },
        btnSchoolO:function(){
            this.type=0;
            $('#con-school').fadeIn();
        },
        btnSchool1:function(){
            this.type=2;
            $('#con-school').fadeIn();
        },
        btnObject:function(){
            alert(this.isChecked);
        }
    }
})
layui.use('element', function(){
    var element = layui.element;
})
