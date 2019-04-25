<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.min.js"></script>


<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_main" style="width: 600px;height: 400px;;margin-top: 30px;margin-left: 30px"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statistics_main'));

    $.ajax({
        url: '${pageContext.request.contextPath}/user/userAmount',
        dataType: 'JSON',
        success: function (data) {
            // 指定图表的配置项和数据
            console.log(data);
            var option = {
                title: {
                    text: '用户注册信息',
                    subtext: '近三周'
                },
                tooltip: {},
                legend: {
                    data: ['用户数量']
                },
                xAxis: {
                    data: data.time
                },
                yAxis: {},
                series: [{
                    name: '人数',
                    type: 'bar',
                    data: data.amount
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })


    //接收信息
    var goEasy = new GoEasy({
        appkey: "BC-07858a5c21ad4fefaa551ffab72e40bb"
    });
    goEasy.subscribe({
        channel: "cmfz",
        onMessage: function (message) {
            var mess = JSON.stringify(message);
            console.log(mess)
            myChart.setOption({
                series: [{
                    name: '人数',
                    data: mess.amount
                }]
            });
        }
    });


</script>
