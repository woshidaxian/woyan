$(function(){
    var infoId = getUrlParam('infoId')
    alert(infoId);
    $.ajax({
        url:'http://120.78.75.14:8006/getInfoById',
        type:'get',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data:{"id":infoId},
        success:function(data){
            console.log(data);
            $('.news-box').html(data.data.content);
            $('.erjititle').text(data.data.subhead);
            $('.news-title').text(data.data.title);
            $('.news-time').text(data.data.time);
        },
        error:function(e){
            console.log(e);
        }
    })
})