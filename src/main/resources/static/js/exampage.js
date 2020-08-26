$(function(){
    var min=60;
    var sec=0;
    var timer = setInterval(function(){
        if(sec<=0){
            min--;
            sec=59;
            if(min<=0){
                sec=0;
                min=0
                clearInterval(timer);
            }
        }else{
            sec--;
        }
        $('.last-time').text(min+'分'+sec+'秒');
    },1000)
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    alert(getUrlParam("examId"));
    $.ajax({
        url:'http://120.78.75.14:8006/joinExam',
        type:'put',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data:{"id":getUrlParam("examId")},
        success:function(data){
            console.log(data);
        },
        error:function(e){
            console.log(e)
        }
    })
})