$(function(){
    $('.btn-up').click(function(){
        $('.center-box-1').css({
            display:'none'
        });
        $('.center-box-2').css({
            display:'block'
        })
        $('.top-span-1').removeClass('span-active');
        $('.top-span-3').addClass('span-active')
    });
    $('.payway-1').click(function(){
        $(this).addClass('pay-active');
        $('.payway-2').removeClass('pay-active');
    })
    $('.payway-2').click(function(){
        $(this).addClass('pay-active');
        $('.payway-1').removeClass('pay-active');
    })
    // 支付15分钟倒计时
    var sec=00;
    var min=15
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
        $('.pay-time').text(min+':'+sec);
    },1000)
})