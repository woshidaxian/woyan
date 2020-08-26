$(function(){
    var schoolCode = sessionStorage.getItem("WoYanSchoolCode");  // 学校代码
    var schoolName = sessionStorage.getItem("WoYanSchool");

    // 获取URL参数的方法
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

    function getVCourseDetails(){
        $.ajax({
            url:'/getVCourseById',
            type:'get',
            headers:{
                "Authorization": sessionStorage.getItem("token")
            },
            data:{
                "id": getUrlParam("id"),
            },
            success:function(data){
                console.log(data);
                if (data.status === "200"){
                    // 修改背景
                    // $(".con-top-bg").css("background","url(" + data.data.vcourse.cover + ")");
                    // 修改作者信息
                    // $('.head-img').css("background",'url(' + data.data.vcourse.headimg + ')');
                    $('.head-name').text(data.data.vcourse.account);
                    // 修改时长信息，购买人数

                    // 修改购买信息
                    $(".box-price").text((data.data.vcourse.price == 0?"免费":"￥"+data.data.vcourse.price.toFixed(2)));
                    // 修改课程介绍

                    // 修改课程章节
                }
            },
            error:function(){
                alert('错误');
            }
        })
    }
    getVCourseDetails();

    $('.nav-ul-li').click(function(){
        if($(this).index()){
            $('.nav-ul-li').removeClass('li-active');
            $(this).addClass('li-active');
            $('.center-box-1').css({
                display:'none'
            });
            $('.center-box-2').css({
                display:'block'
            })
        }else{
            $('.nav-ul-li').removeClass('li-active');
            $(this).addClass('li-active');
            $('.center-box-2').css({
                display:'none'
            });
            $('.center-box-1').css({
                display:'block'
            })
        }
    })
    $(document).on('click','.btn-buy',function(){
        location.href = 'buyvideos.html'
    })
})