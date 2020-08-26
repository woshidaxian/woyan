// 下一页需要清空box
var forumNum = 0;
var forumType=0;
var orderType='';
var schoolCode = 11481;
var typeName=['全部','经验心得','交流答疑','初试宝典','复试宝典','资源分享','我要提问']
if(!sessionStorage.getItem("orderType")){
    orderType='newr'
    $('.sort-li').eq(0).addClass('sort-active');
}else{
    orderType=sessionStorage.getItem("orderType");
    if(orderType=='newr'){
        $('.sort-li').eq(0).addClass('sort-active');
    }else if(orderType=='new'){
        $('.sort-li').eq(1).addClass('sort-active');
    }else if(orderType=='hot'){
        $('.sort-li').eq(2).addClass('sort-active');
    }
}
if(sessionStorage.getItem("forumType")){
    forumType=sessionStorage.getItem("forumType");
    $('.type-li').eq(parseInt(forumType)).addClass('li-active');
}else{
    $('.type-li').eq(forumType).addClass('li-active');
}
sessionStorage.setItem("token", "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdHkiOlsiVzBZQU5fVVNFUiJdLCJzdWIiOiJhbGV4ZmVpIiwiZXhwIjoxNTc1NDU2NjU0fQ.uIi4KKi3iWSCXdQcjVdK1YtKUmZ8jdJ8smeyU1_Zb0LS_9ez4ruiDxtcaDu6MP28Bj0HCjmIWzldjLhoS1vqRg")
function getHotForumLabels(){
    $.ajax({
        url:'http://120.78.75.14:8006/getHotForumLabels',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        success:function(data){
            if(data.status==200){
                console.log(data);
                for(var i=0;i<data.data.length;i++){
                    var box = `<span class="a-hottalk" flabelId=`+ data.data[i].flabelId +`>`+ data.data[i].name +`</span>`;
                    $('.hottalk-a').append(box);
                }
            }
        },
        error:function(e){
            console.log(e);
        }
    })
}
getHotForumLabels();
function getHotForums(){
    $.ajax({
        url:'http://120.78.75.14:8006/getHotForums',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data:{"schoolCode":schoolCode},
        success:function(data){
            if(data.status==200){
                console.log(data);
                for(var i=0;i<data.data.length;i++){
                    var box = `
                    <div class="a-latelytalk" index="`+ data.data[i].forumId +`">
                        <div class="latelytalk-title">`+ data.data[i].title +`</div>
                        <div class="latelytalk-mess clearfix">
                            <div class="latelytalk-time">
                                发表于 
                                <span class="talktime">`+ data.data[i].time +`</span>
                            </div>
                            <div class="latelytalk-reply">
                                回复
                                ·
                                <span class="talkreply">`+ data.data[i].replyNum +`</span>
                            </div>
                        </div>
                    </div>
                    `
                    $('.latelytalk').append(box);
                }
            }
        },
        error:function(e){
            console.log(e);
        }
    })
}
getHotForums();
function getForumList(num,order,type) {
    $.ajax({
        url: 'http://120.78.75.14:8006/getForumList',
        type: 'get',
        async: false,
        headers: { "Authorization": sessionStorage.getItem("token") },
        data: { "pageNum": num, "schoolCode": schoolCode ,"order":order,"genre":typeName[type]},
        success: function (data) {
            console.log(data);
            if (data.status == 200) {
                forumNum = data.data.forumNum;
                for(var i=0;i<data.data.forums.length;i++){
                    var oLabels = '';
                    for(var j=0;j<data.data.forums[i].labels.length;j++){
                        oLabels+=`<span class="label-ding">`+ data.data.forums[i].labels[j] +`</span>`
                    }
                    var box = 
                        `
                            <div class="a-forum-box clearfix" index=`+ data.data.forums[i].forumId +`>
                                <div class="box-left-head">
                                    <img src="`+ data.data.forums[i].headimg +`" alt="" class="head-img">
                                </div>
                                <div class="box-right">
                                    <div class="box-right-top">
                                        <div class="box-title">`+data.data.forums[i].title+`</div>
                                        <div class="box-label">`+ oLabels +`</div>
                                    </div>
                                    <div class="box-right-bottom clearfix">
                                        <div class="box-writer">`+ data.data.forums[i].account +`</div>
                                        <div class="box-time">`+ data.data.forums[i].time +`</div>
                                        <div class="box-where">发表在<span class="where">[我要提问]</span></div>
                                        <div class="box-new">`+ data.data.forums[i].replyTime +`回复</div>
                                        <div class="box-mess-div">
                                            <span>回复</span>
                                            <span>`+ data.data.forums[i].replyNum +`</span>
                                            <span class="dian">·</span>
                                            <span>赞</span>
                                            <span>`+ data.data.forums[i].goodNum +`</span>
                                            <span class="dian">·</span>
                                            <span>浏览</span>
                                            <span>`+ data.data.forums[i].seeNum +`</span>
                                        </div>
                                    </div>
                                </div>
                            </div>            
                        `
                    $('.forum-box-div').append(box);
                }
            }
        },
        error: function () {
            alert(1);
        }
    })
}
getForumList(0,orderType,forumType);
layui.use(['laypage', 'layer'], function () {
    var laypage = layui.laypage
        , layer = layui.layer;
    laypage.render({
        elem: 'page',
        count: forumNum,
        limit: 30,
        theme: '#00B935',
        jump: function (obj, first) {
            if (!first) {
                $('.forum-box-div').empty();
                getForumList(obj.curr-1,orderType,forumType);
            }
        }
    });
})
$('.sort-li').click(function(){
    sessionStorage.setItem("orderType",$(this).attr('otype'));
    location.reload();
})
$('.type-li').click(function(){
    sessionStorage.setItem("forumType",$(this).attr("index"));
    location.reload();
})
$('.forum-box-div').on('click','.a-forum-box',function(){
    var forumid=$(this).attr('index');
    alert(forumid);
    location.href = 'talkpage.html?forumId='+ forumid +'';
})
$('.latelytalk').on('click','.a-latelytalk',function(){
    var forumid = $(this).attr('index');
    alert(forumid);
    location.href = 'talkpage.html?forumId='+ forumid +''
})
$('.release-btn').click(function(){
    location.href='post.html';
})