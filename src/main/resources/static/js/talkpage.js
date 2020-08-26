var schoolCode = 11481;
var forumId = '';
var aName = 'gang';
function getForumById(){
    $.ajax({
        url:'http://120.78.75.14:8006/getForumById',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data:{"id":getUrlParam('forumId')},
        success:function(data){
            if(data.status==200){
                console.log(data);
                forumId = data.data.forumId;
                $('.talkpage-route').html('首页 > 论坛 > '+data.data.title);
                $('.talkpage-title').append(data.data.title);
                $('.img-head').attr("src",data.data.headimg);
                $('.talkpage-writer').text(data.data.account);
                $('.talkpage-time span').text(data.data.time);
                $('.talkpage-num').eq(0).text(data.data.goodNum);
                $('.talkpage-num').eq(1).text(data.data.replyNum);
                $('.talkpage-num').eq(2).text(data.data.seeNum);
                $('.box-content').html(data.data.content);
                if(data.data.collect){
                    $('.collect').css({color:'orange'})
                }
            }
        },
        error:function(e){
            console.log(e)
        }
    })
}
getForumById()
function commentContent(data){
    if(data.delete){
        return '<span style="color:gray">已删除</span>'
    }else{
        return data.content;
    }
}
function reComment(data){
    if(data.length!=0){
        var box = '';
        for(var i=0;i<data.length;i++){
            box += `
            <div class="erjireply-div">
                <div class="erjireply-top">
                    <span class="erjireply-writer">`+ data[i].account +` 
                    <span style="color:gray">回复</span>  <span style="color:orange">`+ data[i].replyAccount +`</span></span>&nbsp;:&nbsp;
                    <span class="erjireply-content">`+ data[i].content +`</span>
                </div>
                <div class="erjireply-bottom clearfix">
                    <div class="erjireply-time">
                        发表于 
                    <span>`+ data[i].time +`</span>
                </div>
                    <div class="erjireply-mess">
                        <span>赞</span>
                        <span>99</span>
                        ·
                        <span class="btn-reply-o" acc="`+ data[i].account +`">回复</span>
                    </div>
                </div>
            </div>`
        }
        return '<div class="erji-reply clearfix">'+ box +'</div>'
    }else{
        return '<div class="erji-reply clearfix"></div>';
    }
}
// reply回复
$('.box-comment').on('click','.btn-reply-o',function(){
    $('.erjireply-inp-div').remove();
    var replybox = `
        <div class="erjireply-inp-div clearfix">
            <input type="text" class="erjiinp-reply" placeholder="回复 `+ $(this).attr("acc") +`">
            <input type="button" class="erjibtn-reply" value="回复" replyacc="`+ $(this).attr("acc") +`">
        </div>`
    $(this).parent().parent().parent().parent().append(replybox);
    $('.erjiinp-reply').focus();
})
$('.box-comment').on('click','.btn-reply-p',function(){
    $('.erjireply-inp-div').remove();
    var replybox = `
        <div class="erjireply-inp-div clearfix">
            <input type="text" class="erjiinp-reply" placeholder="回复 `+ $(this).attr("acc") +`">
            <input type="button" class="erjibtn-reply" value="回复" replyacc="`+ $(this).attr("acc") +`">
        </div>`
    $('.erji-reply').eq(parseInt($(this).attr("index"))).append(replybox);
    $('.erjiinp-reply').focus();
})
$('.box-comment').on('click','.erjibtn-reply',function(){
    var text = $('.erjiinp-reply').val();
    var box = `
        <div class="erjireply-div">
            <div class="erjireply-top">
                <span class="erjireply-writer">`+ aName +` 
                <span style="color:gray">回复</span>  <span style="color:orange">`+ $('.erjibtn-reply').attr("replyacc") +`</span></span>&nbsp;:&nbsp;
                <span class="erjireply-content">`+ text +`</span>
            </div>
            <div class="erjireply-bottom clearfix">
                <div class="erjireply-time">
                    发表于 
                <span></span>
            </div>
                <div class="erjireply-mess">
                    <span>赞</span>
                    <span>0</span>
                    ·
                    <span class="btn-reply-o" acc="`+ aName +`">回复</span>
                </div>
            </div>
        </div>`
    $(this).parent().parent().append(box);
    $('.erjireply-inp-div').remove();
    addForumComment(text,getUrlParam('forumId'),6,1);
})
// 获取评论
function getForumComment(){
    $.ajax({
        url:'http://120.78.75.14:8006/getForumComment',
        type:'get',
        headers:{ "Authorization": sessionStorage.getItem("token") },
        data:{"id":getUrlParam('forumId'),"pageNum":0},
        success:function(data){
            console.log(data);
            if(data.status==200){
                $('.comment-top').append(data.data.comments.length+'条回帖');
                for(var i =0;i<data.data.comments.length;i++){
                    var box=`
                        <div class="a-reply-box clearfix" index="`+ data.data.comments[i].commentId +`">
                            <div class="reply-box-left">
                                <img src="`+ data.data.comments[i].headimg +`" alt="" class="reply-img-head">
                            </div>
                            <div class="reply-box-right">
                                <div class="reply-writer">`+ data.data.comments[i].account +`</div>
                                <div class="reply-text">`+ commentContent(data.data.comments[i]) +`</div>
                                <div class="reply-mess-div clearfix">
                                    <div class="reply-time">
                                        发表于 
                                        <span>`+ data.data.comments[i].time +`</span>
                                    </div>
                                    <div class="reply-mess">
                                        <span>赞</span>
                                        <span>`+ data.data.comments[i].goodNum +`</span>
                                        ·
                                        <span class="btn-reply-p" index="`+ i +`" acc="`+ data.data.comments[i].account +`">回复</span>
                                        <span>`+ data.data.comments[i].replyNum +`</span>
                                    </div>
                                </div>`+ reComment(data.data.comments[i].replies) +`
                            </div>
                        </div>
                        <hr>`
                    $('.box-comment').append(box);
                }
            }
        },
        error:function(e){
            console.log(e);
        }
    })
}
getForumComment();
// 热门话题
function getHotForumLabels(){
    $.ajax({
        url:'http://120.78.75.14:8006/getHotForumLabels',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        success:function(data){
            if(data.status==200){
                // console.log(data);
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
// 近期热贴
function getHotForums(){
    $.ajax({
        url:'http://120.78.75.14:8006/getHotForums',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data:{"schoolCode":schoolCode},
        success:function(data){
            if(data.status==200){
                // console.log(data);
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
$('.latelytalk').on('click','.a-latelytalk',function(){
    var forumid = $(this).attr('index');
    alert(forumid);
    location.href = 'talkpage.html?forumId='+ forumid +''
})
// 收藏
$('.collect').click(function(){
    $.ajax({
        url:'http://120.78.75.14:8006/addForumCollect',
        type:'put',
        headers:{ "Authorization": sessionStorage.getItem("token") },
        data:{"id":forumId},
        success:function(data){
            // console.log(data);
            if(data.status==200){
                if(data.data){
                    $('.collect').css({color:'orange'});
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg("收藏成功");
                    });
                }else{
                    $('.collect').css({color: 'rgb(160, 160, 160)'});
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg("取消收藏");
                    });
                }
            }
        },
        error:function(e){
            console.log(e)
        }
    })
})
// 点赞
$('.btn-zan').click(function(){
    $.ajax({
        url:'http://120.78.75.14:8006/addForumGood',
        type:'put',
        headers:{ "Authorization": sessionStorage.getItem("token") },
        data:{"id":forumId},
        success:function(data){
            if(data.status==200){
                if(data.data){
                    $('.btn-zan').css({color:'orange'});
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg("<span class='fa fa-thumbs-up' style='font-size:20px'></span>");
                    });
                    $('.talkpage-num').eq(0).text(parseInt($('.talkpage-num').eq(0).text())+1);
                    $('.talkpage-num').eq(0).css({color:'orange'});
                }else{
                    $('.btn-zan').css({color: 'rgb(160, 160, 160)'});
                    $('.talkpage-num').eq(0).text(parseInt($('.talkpage-num').eq(0).text())-1);
                    $('.talkpage-num').eq(0).css({color: 'rgb(160, 160, 160)'});
                }
            }
        },
        error:function(e){
            console.log(e);
        }
    })
})
function addForumComment(content,id,replyId,reCommentId){
    $.ajax({
        url:'http://120.78.75.14:8006/addForumComment',
        type:'put',
        headers:{ "Authorization": sessionStorage.getItem("token") },
        data:{"content":content,"id":id,"reCommentId":reCommentId,"replyId":replyId},
        success:function(data){
            console.log(data);
            return data;
        },
        error:function(e){
            console.log(e);
        }
    })
}