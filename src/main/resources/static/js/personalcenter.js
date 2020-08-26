$('.personal-ul-li').click(function () {
    var i = $(this).index();
    $('.personal-ul-li').removeClass('personal-ul-li-active');
    $(this).addClass('personal-ul-li-active');
    $('.boxstyle').css({ display: 'none' })
    $('.boxstyle').eq(i).css({ display: 'block' })
    switch (i) {
        case 0: getVideos(); break;
        case 1: getExam(); break;
        case 2: getMess(); break;
        case 3: getCollect(); break;
        case 4: getForum(); break;
        case 5: getOrder(); break;
        case 6: setMess(); break;
    }
})
$('.personal-ul-li').eq(0).click();
// 视频课程
function getVideos() {
    for(var i=0;i<9;i++){
        var box =`  <div class="box0-abox clearfix">
                        <div class="box0-abox-left">
                            <img src="../img/1.jpg" alt="" class="scource-img">
                        </div>
                        <div class="box0-abox-right">
                            <div class="abox-title">测试嗯字测试文字测试文字</div>
                                <div>
                                    <span class="abox-havedone">已学0%</span>
                                    <span class="abox-havetime">用时0分</span>
                                    <span class="abox-haveat">学习至01课程导学</span>
                                </div>
                            <div class="abox-btn-div clearfix">
                                <div class="btn-study">继续学习</div>
                            </div>
                        </div>
                    </div>
                `
        $('.right-box-0').append(box);
    }
    
}
// 我的考试
function getExam() {
    
}
// 我的消息
function getMess() {
    var messData = [
        {

        }
    ]
}
// 我的收藏
function getCollect() {
    var collectData = [
        {

        }
    ]
}
// 我的发帖
function getForum() {
    var forumData = [
        {

        }
    ]
}
// 订单管理
function getOrder() {
    var orderData = [
        {

        }
    ]
}
// 设置
function setMess() {
}
