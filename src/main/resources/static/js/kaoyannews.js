var themeName =['全部','经验心得','小道消息','招生信息','校园信息','研究生生活','专业课','资料课本信息'];
var pageNum = 0;
var theme = themeName[0]
// 0,8-14
function getInfoList(page,theme) {
	$.ajax({
		url: 'http://120.78.75.14:8006/getInfoList',
		type: 'get',
		headers: { "Authorization": sessionStorage.getItem("token") },
		data: { "pageNum": page ,"schoolCode":'11481',"theme":theme},
		success: function (data) {
			console.log(data);
			if(data.status==200){
				var dataArray = data.data.content;
				if(dataArray.length){
					$('.news-box').empty();
					for(var i=0;i<dataArray.length;i++){
						var box = `
							<div class="a-news-box clearfix" index="`+ dataArray[i].infoId +`">
								<div class="news-img-box">
									<div class="news-img" style="background:url(`+ dataArray[i].cover +`) no-repeat;background-position:center;background-size:cover;"></div>
								</div>
								<div class="news-mess">
									<div class="news-title">`+ dataArray[i].title +`</div>
									<div class="news-title-m">`+ dataArray[i].subhead +`</div>
									<div class="news-from clearfix">
										<div class="news-from-left">
											来自主题：
											<span class="news-theme">`+ dataArray[i].theme +`</span>&nbsp;|&nbsp;
											<span class="news-writer">蜗研网的朋友们</span>
										</div>
										<div class="news-from-right">
											<span class="fa fa-clock-o"></span>
											<span class="news-time">`+ dataArray[i].time +`</span>
										</div>
									</div>
								</div>
							</div>`
						$('.news-box').append(box);
					}
				}else{
					$('.btn-more').remove();
				}
			}
		},
		error: function (e) {
			alert(e)
		}
	})
}
getInfoList(pageNum,theme);
// 获取轮播图
function getInfoSlideshow(){
	$.ajax({
		url:'http://120.78.75.14:8006/getInfoSlideshow',
		type:'get',
		headers: { "Authorization": sessionStorage.getItem("token") },
		data:{},
		success:function(data){
			if(data.status==200){
				var box = '';
				for(var i=0;i<data.data.length;i++){
					box+=`<img src="`+ data.data[i].src +`" alt="">`
				}
				$('.news-lunbo').append('<div class="layui-carousel" id="test1"><div carousel-item>'+ box +'</div></div>')
				layui.use('carousel', function () {
					var carousel = layui.carousel;
					//建造实例
					carousel.render({
						elem: '#test1',
						width: '100%',
						height: '340px', //设置容器宽度
						arrow: 'always' //始终显示箭头
					});
				});
			}
		},
		error:function(e){
			console.log(e);
		}
	})
}
getInfoSlideshow()
function getInfoRecommend(){
	$.ajax({
		url:'http://120.78.75.14:8006/getInfoRecommend',
		type:'get',
		headers:{ "Authorization": sessionStorage.getItem("token") },
		data:{},
		success:function(data){
			console.log(data);
			if(data.status==200){
				for(var i=0;i<data.data.length;i++){
					var box = `
						<div class="a-recommend-box-s" index="`+ data.data[i].infoId +`" style="background:url(`+ data.data[i].cover +`) no-repeat;background-size:cover;background-position:center">
							<div class="recommend-box-s-t">
								<div class="box-s-text">`+ data.data[i].title +`</div>
							</div>
						</div>`
					$('.news-right-recommend').append(box);
				}
			}
		},
		error:function(e){
			console.log(e);
		}
	})
}
getInfoRecommend();
$('.type-ul-li').click(function(){
	$('.news-box').empty();
	$('.type-ul-li').removeClass('type-li-active');
	$(this).addClass('type-li-active');
	alert(themeName[$(this).index()])
	theme = themeName[$(this).index()]
	pageNum = 0;
	getInfoList(pageNum,theme)
})
// 加载更多
$('.btn-more').click(function(){
	pageNum = pageNum + 1;
	getInfoList(pageNum,theme)
})
$('.news-box').on('click','.a-news-box',function(){
	window.open('news.html?infoId='+$(this).attr("index"));
})
$('.news-right-recommend').on('click','.a-recommend-box-s',function(){
	window.open('news.html?infoId='+$(this).attr("index"));
})