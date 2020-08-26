var myChart1 = echarts.init(document.getElementById('chart1'));
var myChart2 = echarts.init(document.getElementById('chart2'));
var myChart3 = echarts.init(document.getElementById('chart3'));
var myChart4 = echarts.init(document.getElementById('chart4'));
// 指定图表的配置项和数据
var option1 = {
    title: {
        text: '报考杭州电子科技大学研究生各学院人数统计',
    },
    tooltip:{},
    legend: {
        data:['人数'],
        left:'right'
    },
    color:['#8dc1a9'],
    grid: {
        left: '1%',
        right: '1%',
        bottom: '1%',
        containLabel: true
    },
    xAxis: {
        data: ["学院1", "学院2", "学院3", "专业4", "专业5", "专业6","专业1", "专业2", "专业3", "专业4", "专业5", "专业6","专业1", "专业2", "专业3", "专业4",]
    },
    yAxis: {},
    series: [{
        name: '人数',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5, 20, 36, 10]
    }]
};
var option2 = {
    title : {
        text: '报考杭电男女比例',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'right',
        data: ['男','女']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            color: ['#8dc1a9','#e69d87','#f49f42'],
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'男'},
                {value:310, name:'女'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
var option3 = {
    title : {
        text: '本专业/跨专业',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['跨专业','本专业']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            color: ['#e69d87','#f49f42'],
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'跨专业'},
                {value:310, name:'本专业'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
var option4 = {
    title: {
        text: '历年专业一的报考人数与录取人数概况'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    color:['#59b59b','#8cdbc5'],
    legend: {
        data: ['报考人数', '录取人数'],
        left:'right'
    },
    grid: {
        left: '1%',
        right: '1%',
        bottom: '1%',
        containLabel: true
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    xAxis: {
        type: 'category',
        data: ['2009','2010','2011','2012','2013','2014','2015','2016','2017','2018','2019']
    },
    series: [
        {
            name: '报考人数',
            type: 'bar',
            data: [103, 239, 234, 100, 144, 630,103, 239, 234, 100, 144, 630]
        },
        {
            name: '录取人数',
            type: 'bar',
            data: [15, 28, 31, 14, 111, 607,15, 28, 31, 14, 111, 607]
        }
    ]
};

// 使用刚指定的配置项和数据显示图表。
myChart1.setOption(option1);
myChart2.setOption(option2);
myChart3.setOption(option3);
myChart4.setOption(option4);