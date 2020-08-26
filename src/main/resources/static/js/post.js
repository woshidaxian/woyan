$(function(){
    // 富文本
    var E = window.wangEditor
    var editor = new E('#editor1','#editor2')
    editor.create();
    var onLabel=[];
    var onType=[];
    $('.add-label').one("click",function(){
        var labelArray = ["标签一","标签二","标签三","标签四","标签五","标签六","标签七"]
        for(var i=0;i<labelArray.length;i++){
            $('.label-array').append('<span class="label-style" ison=1>'+ labelArray[i] +'</span>')
        }
        $('.label-array').slideDown(100);
    })
    $('.add-label-type').one("click",function(){
        var typeArray = ["经验心得","交流答疑","初试宝典","复试宝典","资源分享","我要提问"]
        for(var i=0;i<typeArray.length;i++){
            $('.type-array').append('<span class="type-style" ison=1>'+ typeArray[i] +'</span>')
        }
        $('.type-array').slideDown(100);
    })
    $('.label-array').on("click",'.label-style',function(){
        if($(this).attr('ison')=='1'&&onLabel.length<5){
            $(this).addClass('label-style-active')
            $(this).attr('ison',0)
            onLabel.push($(this).text());
            $('.last-label-num').text(parseInt($('.last-label-num').text())-1);
        }else if($(this).attr('ison')=='0'){
            $(this).removeClass('label-style-active')
            $(this).attr('ison',1)
            onLabel.splice($.inArray($(this).text(),onLabel),1);
            $('.last-label-num').text(parseInt($('.last-label-num').text())+1);
        }
    })
    $('.type-array').on("click",'.type-style',function(){
        if($(this).attr('ison')=='1'&&onType.length<1){
            $(this).addClass('type-style-active')
            $(this).attr('ison',0)
            onType.push($(this).text());
            $('.last-type-num').text(parseInt($('.last-type-num').text())-1);
        }else if($(this).attr('ison')=='0'){
            $(this).removeClass('type-style-active')
            $(this).attr('ison',1)
            onType.splice($.inArray($(this).text(),onType),1);
            $('.last-type-num').text(parseInt($('.last-type-num').text())+1);
        }
    })
    $('.inp-post-title').on('keyup',function(){
        $('.count-word-num').text($(this).val().length+'/40');
        if($(this).val().length>40){
            $(this).val($(this).val().substring(0,40));
            $('.count-word-num').text('40/40');
        }
    })

    // 发布
    $('.btn-fabu').click(function(){
        if(!$('.inp-post-title').val()||!editor.txt.text()||onLabel.length==0||onType.length==0){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg("内容不完整!");
            });
        }else{
            $.ajax({
                url:'http://120.78.75.14:8006/addForum',
                type:'post',
                headers: { "Authorization": sessionStorage.getItem("token") },
                data:{
                    "title":$('.inp-post-title').val(),
                    "content":editor.txt.html(),
                },
                success:function(data){

                },
                error:function(e){
                    console.log(e);
                }
            })
        }
    })
    $.ajax({
        url:'http://120.78.75.14:8006/getForumLabels',
        type:'get',
        headers:{ "Authorization": sessionStorage.getItem("token") },
        data:{},
        success:function(data){
            console.log(data);
        },
        error:function(e){
            console.log(e);
        }
    })
})