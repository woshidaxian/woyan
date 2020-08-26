$(function(){
    var banksNum = 0;
    $('.sort-style').click(function(){
        $('.sort-style').removeClass('sort-active');
        $(this).addClass('sort-active');
        
    })  
    function star(a){
        var b='';
        var j=0;
        while(j<a){
            b+=`&nbsp;<i class="layui-icon layui-icon-rate-solid star-style"></i>`
            j++;
        }
        return b;
    }
    function getQuestionBanks(){
        $.ajax({
            url:"http://120.78.75.14:8006/getQuestionBanks",
            type:'get',
            async:false,
            headers: { "Authorization": sessionStorage.getItem("token") },
            data:{"pageNum":0,"isPublic":1},
            success:function(data){
                console.log(data)
                if(data.status==200){
                    banksNum = data.data.bankNum
                    for(var i=0;i<data.data.banks.length;i++){
                        var box = `
                        <div class="a-exam-box" examId="`+ data.data.banks[i].questionBankId +`">
                            <div class="a-exam">
                                <h1>`+ data.data.banks[i].title +`</h1>
                                <div class="a-exam-logo">
                                    <img src="`+ data.data.banks[i].cover +`" alt="" class="logo-img">
                                </div>
                                <div class="a-exam-hot">热度指数：`+ data.data.banks[i].hot +`</div>
                                <div class="a-exam-give">
                                    <div>学姐提供</div>
                                    <div>共`+ data.data.banks[i].questionNum +`道题</div>
                                </div>
                                <div class="btn-begin">开始答题</div>
                            </div>
                            <div class="nandu">
                                <span class="star-t">难度系数:</span>`+ star(data.data.banks[i].degree) +`
                            </div>
                        </div>`
                        $('.con-exam').append(box);
                    }
                }
            },
            error:function(e){
                console.log(e);
            }
        })
    }
    getQuestionBanks()
    layui.use(['laypage', 'layer'], function(){
        var laypage = layui.laypage
        ,layer = layui.layer;
        //自定义样式
        laypage.render({
            elem: 'page',
            count: banksNum,
            limit:30,
            theme: '#25bb9b'
        });
    })
})