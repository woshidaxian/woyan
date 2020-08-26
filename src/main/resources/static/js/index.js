$(function () {
    layui.use('carousel', function () {
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#test1',
            width: '100%',//设置容器宽度
            arrow: 'always',//始终显示箭头
            height: '330px'
        });
    });
    var myChart0 = echarts.init(document.getElementById('chart0'));
    var option0 = {
        title: {
            text: '杭州电子科技大学研究生各专业人数统计',
            left:'center'
        },
        tooltip:{},
        color:['#8dc1a9'],
        grid: {
            left: '1%',
            right: '1%',
            bottom: '1%',
            containLabel: true
        },
        xAxis: {
            data: ["专业1", "专业2", "专业3", "专业4", "专业5", "专业6","专业1", "专业2", "专业3", "专业4", "专业5", "专业6","专业1", "专业2", "专业3", "专业4", "专业5", "专业6","专业1", "专业2", "专业3", "专业4"]
        },
        yAxis: {},
        series: [{
            name: '人数',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5, 20, 36, 10, 1,5,8,9,10,20]
        }]
    };
    myChart0.setOption(option0);
})