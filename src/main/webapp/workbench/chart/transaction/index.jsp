<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>mytitle</title>
    <script src="ECharts/echarts.min.js"></script>
    <script src="jquery/jquery-1.11.1-min.js"></script>
    <script>
        $(function () {
            getCharts();
        });
        function getCharts() {
            $.ajax({
                url:"workbench/transaction/getCharts.do",
                type:"get",
                dataType:"json",
                success:function (data) {
                    var myChart=echarts.init(document.getElementById('main'));
                    var option = {
                        title:{
                            text:'交易饼状图',
                            subtext:'统计交易阶段数量饼状图',
                        },
                        legend: {
                            top: 'bottom'
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                mark: { show: true },
                                dataView: { show: true, readOnly: false },
                                restore: { show: true },
                                saveAsImage: { show: true }
                            }
                        },
                        series: [
                            {
                                name: '数据视图',
                                type: 'pie',
                                radius: [50, 250],
                                center: ['50%', '50%'],
                                roseType: 'area',
                                itemStyle: {
                                    borderRadius: 8
                                },
                                data: data.dataList/*[
                                    { value: 40, name: 'rose 1' },
                                    { value: 38, name: 'rose 2' },
                                    { value: 32, name: 'rose 3' },
                                    { value: 30, name: 'rose 4' },
                                    { value: 28, name: 'rose 5' },
                                    { value: 26, name: 'rose 6' },
                                    { value: 22, name: 'rose 7' },
                                    { value: 18, name: 'rose 8' }
                                ]*/
                            }
                        ]
                    };
                    myChart.setOption(option);
                }
            });

        }
    </script>
</head>
<body>
    <div id="main" style="width:900px;height:800px"></div>
</body>
</html>