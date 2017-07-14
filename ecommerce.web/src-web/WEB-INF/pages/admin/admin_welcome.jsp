<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<head>

    <script type="text/javascript">
        $(document).ready(function () {
            Highcharts.chart('next_prefer_grp_purchase_pie', {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                credits: {enabled: false},
                title: { text: '다음 공.구 선호요일', style: {display: 'none'}},
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    name: '요일',
                    colorByPoint: true,
                    data: [
                        { name: '월', y: 56.33},
                        { name: '화', y: 24.03, sliced: true, selected: true },
                        { name: '수',y: 5.38},
                        { name: '목', y: 4.77},
                        { name: '금', y: 0.91},
                        { name: '토',y: 0.2},
                        { name: '일',y: 5.0}
                    ]
                }]
            });





            Highcharts.chart('container', {
                chart: {
                    type: 'column'
                },
                title: { text: 'Top 5 surburb', style: {display: 'none'}},
                credits: {enabled: false},
                xAxis: {
                    categories: [
                        'Box Hill',
                        'Ringwood',
                        'Glen Waverley',
                        'Clayton',
                        'Melbourne'
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: { text: 'Sales rate (%)'}
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    showInLegend: false,
                    name: 'Sales',
                    data: [40, 20, 15, 5, 2]

                }]
            });



            getHeatMapData();

        });
    </script>
    <script type="text/javascript">
        /**
         * MARK DATA ON GOOGLE MAP ************************************************************* */
        function getHeatMapData(){

            $.ajax({
                url : "/admin/dashboard/getHeatmapData.yum",
                data : {
                    input_year : '2017',
                    input_month : '07'
                },
                success : callbackGetHeatMapData
            });
        }
        function callbackGetHeatMapData(data){
            var list = data.list;
            var mapPoints = new Array();

            list.forEach(function(data) {
                mapPoints.push(data);
            });

            var MelfoodGmap = new Object();

            MelfoodGmap.mapName = '멜푸드지도';
            MelfoodGmap.wintitle = '멜푸드';
            MelfoodGmap.mapStyleNo = 7;
            MelfoodGmap.mapIsMultipleMark = 'Y';
            MelfoodGmap.mapZoomLevel = 10;
            MelfoodGmap.mapBindObjID = 'map_canvas';
            MelfoodGmap.mapHeatOpacity = 1;
            MelfoodGmap.mapHeatRadius = 20;
            MelfoodGmap.mapType = 'HEAT';
            MelfoodGmap.mapIcon = "/resources/image/map_marker16.png";
            MelfoodGmap.mapIconSize = "20,34";

            markPointsOnGHeatMap(mapPoints, MelfoodGmap);
        }
    </script>

</head>

<body>


<div class="row">
    <div class="col-sm-8">
        <div class="row">
            <!-- 좌 -->
            <div class="col-sm-6">
                <div class="row">
                    <!-- 다음 공.구 선호 요일 -->
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-heading" style="font-weight: bold;color: #3c3c3c;">다음 공.구 선호 요일</div>
                            <div class="panel-body">
                                <table border="0">
                                    <tr>
                                        <td>
                                            <div id="next_prefer_grp_purchase_pie" style="width: 400px; height: 200px; min-width: 310px; margin: 0 auto"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center;">
                                            <table align="center">
                                                <tr>
                                                    <td style="font-weight: bold;font-size: 18px;color: #737273;">AM</td>
                                                    <td style="width: 30px;text-align: center;">:</td>
                                                    <td style="font-weight: bold;font-size: 18px;color: #737273;">89 %</td>
                                                    <td style="width: 50px;">,</td>
                                                    <td style="font-weight: bold;font-size: 18px;color: #737273;">PM</td>
                                                    <td style="width: 30px;text-align: center;">:</td>
                                                    <td style="font-weight: bold;font-size: 18px;color: #737273;">11 %</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <!-- QnA -->
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-heading" style="font-weight: bold;color: #3c3c3c;">QnA 처리</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th style="text-align: center;color: #575758;">처리 전</th>
                                        <th style="text-align: center;color: #575758;">처리 중</th>
                                        <th style="text-align: center;color: #575758;">처리 완료</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td style="text-align: center;color: #D54F3C;font-weight: bold;font-size: 20px;">5 건</td>
                                        <td style="text-align: center;color: #337AB7;font-weight: bold;font-size: 20px;">2 건</td>
                                        <td style="text-align: center;color: #002F00;font-weight: bold;font-size: 20px;">20 건</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <!-- Top 5 Suburb -->
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-heading" style="font-weight: bold;color: #3c3c3c;">Top 5 Suburb (Sales %)</div>
                            <div class="panel-body">
                                <table border="0">
                                    <tr>
                                        <td>
                                            <div id="container" style="width: 400px; height: 200px; min-width: 310px; margin: 0 auto"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 우 : 히트맵 -->
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading" style="font-weight: bold;color: #3c3c3c;">공.굴러 활동</div>
                    <div class="panel-body">
                        <div id="map_canvas" style="width: 100%; height: 400px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



</div>
</body>
</html>