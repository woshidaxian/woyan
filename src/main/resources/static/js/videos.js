$(function(){

    var vCoursePageData = [];

    function getProCoursesByCollage(xueyuan){
        var zhuanye = [{"proCourseId": 0, "name": "不限", "collageId": xueyuan.collage.collageId}];
        if (xueyuan.proCourses.length > 0){
            for (var i = 0;i < xueyuan.proCourses.length; ++ i){
                zhuanye.push(xueyuan.proCourses[i]);
            }
        }
        return zhuanye;
    }

    function showProCourses(zhuanye) {
        $('.zhuanye-right').empty();
        for(var j =0;j<zhuanye.length;j++){
            $('.zhuanye-right').append('<span class="zhuanye-style" collageid="'+zhuanye[j].collageId+'" procourseid="'+zhuanye[j].proCourseId+'" index="'+ j +'">'+ zhuanye[j].name +'</span>')
        }
        // $('.zhuanye-style').eq(0).addClass('zhuanye-active');
        $('.zhuanye-style').click(function(){
            console.log("专业 click");
            $('.zhuanye-style').removeClass('zhuanye-active');
            $('.zhuanye-style').eq($(this).attr('index')).addClass('zhuanye-active');
            getAllVCourses(0, $(this).attr('collageid'), $(this).attr('procourseid'));
        })
        $('.zhuanye-style').eq(0).click();
    }

    // 每次切换地区的时候，给所有可选学校添加新的点击事件，获取所有的专业课内容
    $('.province-style').click(function () {
        $('.center-school-li').click(function () {
            console.log("第二个点击事件");
            getProCourses()
        });
    });


    function getProCourses() {
        console.log(userData);
        console.log(schoolCode);
        $(".xueyuan-right").empty();
        $(".zhuanye-right").empty();
        if (sessionStorage.getItem("token") !== undefined){
            $.ajax({
                url:'/getAllProCourseBySchoolCode',
                type:'get',
                headers:{
                    "Authorization": sessionStorage.getItem("token")
                },
                data:{
                    "schoolCode": schoolCode
                },
                success:function(data){
                    console.log(data.data);
                    var xueyuan = data.data;
                    for(var i=0;i<xueyuan.length;i++){
                        $('.xueyuan-right').append('<span  class="xueyuan-style" index="'+ i +'">'+ xueyuan[i].collage.name +'</span>')
                    }
                    $('.xueyuan-style').eq(0).addClass('xueyuan-active');
                    $('.xueyuan-style').click(function(){
                        console.log("学院 click");
                        $('.xueyuan-style').removeClass('xueyuan-active');
                        $('.xueyuan-style').eq($(this).attr('index')).addClass('xueyuan-active');
                        showProCourses(getProCoursesByCollage(xueyuan[$(this).attr('index')]))
                    })
                    $('.xueyuan-style').eq(0).click();
                },
                error:function(){
                    alert('错误');
                }
            })
        }
    }
    getProCourses();

    function getAllVCourses(pageNum, collageId, proCourseId) {
        console.log("getAlVCourses:" + pageNum + ", " + collageId + ", " + proCourseId);
        if (vCoursePageData[proCourseId] == null){
            vCoursePageData[proCourseId] = [];
        }
        if(proCourseId == 0 || vCoursePageData[proCourseId][pageNum] == null){
            $.ajax({
                url:'/getVCourseList',
                type:'get',
                headers:{
                    "Authorization": sessionStorage.getItem("token")
                },
                data:{
                    "pageNum": pageNum,
                    "schoolCode": schoolCode,
                    "collageId": collageId,
                    "proCourseId": proCourseId,
                    "order": "new"
                },
                success:function(data){
                    console.log(data);
                    if(pageNum === 0){
                        layui.use(['laypage', 'layer'], function(){
                            var laypage = layui.laypage
                                ,layer = layui.layer;
                            //自定义样式
                            laypage.render({
                                elem: 'page',
                                count: data.data.vcourseNum,
                                limit:12,
                                theme: '#00B935'
                                ,jump: function(obj, first){
                                    //首次不执行
                                    if(!first){
                                        getAllVCourses(obj.curr-1, collageId, proCourseId);
                                    }
                                }
                            });
                        });
                    }
                    vCoursePageData[proCourseId][pageNum] = data.data;
                    showVCourses(data.data);
                },
                error:function(){
                    alert('错误');
                }
            })
        }else {
            // 如果已经请求过数据，直接显示
            showVCourses(vCoursePageData[proCourseId][pageNum])
        }
    }
    
    function showVCourses(data) {
        console.log(data);
        $('.left-bar-v').empty();
        var zhuanyeke = data.vcourses;
        for(var i=0;i<zhuanyeke.length;i++){
            $('.left-bar-v').append(`
            <div class="v-div" vcourseid="${zhuanyeke[i].vcourseId}" >
                <div class="v-div-top">
                    <div class="v-div-top-img" style="background:url('`+ zhuanyeke[i].cover +`');background-size:100% 100%;"></div>
                    <!-- <div class="v-div-label">计算机</div> -->
                </div>
                <div class="v-div-bottom">
                    <div class="v-div-title">`+ zhuanyeke[i].title +`</div>
                    <div class="v-div-intro">
                        `+ zhuanyeke[i].intro +`
                    </div>
                    <div class="v-div-ppp">
                        <span class="v-span-price">`+ (zhuanyeke[i].price == 0?"免费":"￥"+zhuanyeke[i].price.toFixed(2)) +`</span>
                        <span class="v-span-comment">`+ zhuanyeke[i].seeNum +`人查看</span>
                    </div>
                </div>
            </div>
        `);
        }
        $('.left-bar-v').on('click','.v-div',function(){
            window.open('videosDetial.html?id=' + $(this).attr("vcourseid"));
        })
    }
})
