$(function(){
    var schoolCode = 11481;
    var examNum = 0;
    var isPublic = 0;
    if(sessionStorage.getItem("isPublic")==0){
        isPublic=0;
    }else if(sessionStorage.getItem("isPublic")==1){
        isPublic=1;
        $(".exam-select").find('option[value="专业课"]').attr("selected","selected");
    }else if(sessionStorage.getItem("isPublic")==2){
        isPublic=2;
        $(".exam-select").find('option[value="公共课"]').attr("selected","selected");
    }
    function getStatus(status){
        // 根据status值判断考试状态0-报名中，1-已结束，2-进行中
        if(status==0){
            return `<div class="exam-status status-blue">报名中</div>`
        }else if(status==1){
            return `<div class="exam-status status-red">已结束</div>`
        }else if(status==2){
            return `<div class="exam-status status-green">进行中</div>`
        }else{
            return "error"
        }
    }
    function getPublic(public){
        if(public){
            return "公共课"
        }else{
            return "专业课"
        }
    }
    function getBtn(status){
        if(status==0){
            return '报名';
        }else if(status==1){
            return '查看';
        }else if(status==2){
            return '进入考试';
        }
    }
    function getDot(status){
        if(status==0){
            return 'layui-bg-blue';
        }else if(status==1){
            return 'layui-bg-red';
        }else if(status==2){
            return 'layui-bg-green';
        }
    }
    function getBgColor(status){
        if(status==0){
            return 'bg-blue';
        }else if(status==1){
            return 'bg-red';
        }else if(status==2){
            return 'bg-green';
        }
    }
    function getExams(page,isPublic){
        $.ajax({
            url: 'http://120.78.75.14:8006/getExams',
            type: 'get',
            async:false,
            headers: { "Authorization": sessionStorage.getItem("token") },
            data: { "pageNum": page ,"schoolCode":schoolCode,"isPublic":isPublic},
            success: function (data) {
                console.log(data);
                if(data.status==200){
                    examNum = data.data.examNum;
                    for(var i =0;i<data.data.exams.length;i++){
                        var box=`
                        <div class="a-exam-box `+ getBgColor(data.data.exams[i].status) +`" examId="`+ data.data.exams[i].examId +`">
                            <div class="exam-name"><span class="layui-badge-dot `+ getDot(data.data.exams[i].status) +` right8px"></span>`+ data.data.exams[i].name +`</div>
                            <div class="exam-begin-time">`+ data.data.exams[i].beginTime +`</div>
                            <div class="exam-end-time">`+ data.data.exams[i].endTime +`</div>`+ getStatus(data.data.exams[i].status) +`
                            <div class="exam-subject">`+ data.data.exams[i].proCourseName +`</div>
                            <div class="exam-type">`+ getPublic(data.data.exams[i].public) +`</div>
                            <div class="exam-do">
                                <button type="button" class="layui-btn layui-btn-sm btn-begin">`+ getBtn(data.data.exams[i].status) +`</button>
                            </div>
                        </div>`
                        $('.exam-box').append(box);
                    }
                }
            },
            error: function (e) {
                alert(e)
            }
        })
    }
    getExams(0,isPublic);
    layui.use(['laypage', 'layer'], function(){
        var laypage = layui.laypage
        ,layer = layui.layer;
        //自定义样式
        laypage.render({
            elem: 'page',
            count: examNum,
            limit: 20,
            theme: '#00B935',
            jump:function(obj,first){
                if(!first){
                    $('.exam-box').empty();
                    getExams(obj.curr-1,isPublic)
                }
            }
        });
    })  
    // 下啦选择
    $('.exam-select').change(function(){
        if($(this).val()=='全部'){
            sessionStorage.setItem("isPublic","0");
        }else if($(this).val()=='专业课'){
            sessionStorage.setItem("isPublic","1");
        }else if($(this).val()=='公共课'){
            sessionStorage.setItem("isPublic","2");
        }
        location.reload();
    })
    $(document).on('click','.btn-begin',function(){
        var examid = $(this).parent().parent().attr("examId");
        if($(this).text()=='报名'){
            var layer = layui.layer;
            layer.open({
                title: '报名提醒',
                content: '您确认要报名该考试吗？',
                yes:function(index, layero){
                    $.ajax({
                        url:'http://120.78.75.14:8006/addExamAppoint',
                        type:'put',
                        headers: { "Authorization": sessionStorage.getItem("token") },
                        data:{"id":examid},
                        success:function(data){
                            console.log(data);
                            if(data.status==200){
                                var layer = layui.layer;
                                layui.use('layer', function(){
                                    layer.msg("报名成功");
                                });
                            }else{
                                var layer = layui.layer;
                                layui.use('layer', function(){
                                    layer.msg("报名失败");
                                });
                            }
                        },
                        error:function(e){
                            console.log(e)
                        }
                    })
                    layer.close(index);
                }
            }); 
        }else if($(this).text()=='查看'){
            location.href = 'exampage.html'
        }else if($(this).text()=='进入考试'){
            location.href = 'exampage.html?examId='+$(this).parent().parent().attr("examId");
        }
    })
})