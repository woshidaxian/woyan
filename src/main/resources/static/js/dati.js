$(function(){
    var nowChoose = -1;
    $('.choose-div').on('click','.a-choose',function(){
        $('.a-choose').removeClass('ischoose');
        $(this).addClass('ischoose');
        $('.choose-style').eq(nowChoose).removeClass('fa-dot-circle-o');
        $('.choose-style').eq(nowChoose).addClass('fa-circle-o');
        $('.choose-style').eq($(this).index()).removeClass('fa-circle-o');
        $('.choose-style').eq($(this).index()).addClass('fa-dot-circle-o');
        nowChoose=$(this).index();
    })
})