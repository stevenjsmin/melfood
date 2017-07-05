<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<!doctype html>
<head>

    <style>
        /**
         * 상위에서 정의된 레이아웃 재정의함
         */
        .content {
            width: 100%;
            padding: 10px 0px 0px 0px
        }
        h2 {
            color: #6e6e6e;
            font-family: verdana;
            font-size: 300%;
            text-align: center;
            font-weight: bold;
        }
        .gppurchase {
            border:2px solid;
            color: #FBFBFB;
        }
        .gppurchase:hover {
            border-color: #69B7F5;
        }
        .fa {
            color: #606060;
        }
        .circular-square {
            border-top-left-radius: 50% 50%;
            border-top-right-radius: 50% 50%;
            border-bottom-right-radius: 50% 50%;
            border-bottom-left-radius: 50% 50%;
        }
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.1), rgba(0,0,0,0.7)),url(/resources/image/pizza-desktop.jpg);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 150px;
        }
    </style>

    <script>
        $(document).ready(function() {

            // https://owlcarousel2.github.io/OwlCarousel2/docs/api-options.html
            var owl = $('.owl-carousel');
            owl.owlCarousel({
                items: 1,
                center: false,
                margin: 0,
                nav: false,
                loop: false,
                autoWidth: false,
                autoplay: false
            });
           /**
            // 스크롤하는 부분이 하나이면 상관없는데 여러개일때는 모두 같이 움직이는 현상이 있음
            owl.on('mousewheel', '.owl-stage', function(e) {
                if (e.deltaY > 0) {
                    owl.trigger('next.owl');
                } else {
                    owl.trigger('prev.owl');
                }
                e.preventDefault();
            });
             */

           displayGroupPurchaseMarketPlace();
        })
    </script>


    <script type="text/javascript">
        function goChangePage(page){
            document.location.href = "/common/auth/welcome.yum?page=" + page;
        }
        function goGroupPurchaseMain(groupPurchaseId){
            document.location.href = "/grouppurchase/Main.yum?groupPurchaseId=" + groupPurchaseId;
        }
    </script>


    <script type="text/javascript">
        function displayGroupPurchaseMarketPlace() {

            var MelfoodGmap = new Object();

            MelfoodGmap.mapName = '멜푸드지도';
            MelfoodGmap.mapStyleNo = 7;
            MelfoodGmap.mapIsMultipleMark = 'Y';
            MelfoodGmap.mapZoomLevel = 10;
            MelfoodGmap.mapAddress = '4 Torresdale Road, South Morang VIC 3752';

            //groupPurchaseAlllist
            var points = [];
            var address = "";
            var message = "";
            var gmap_latitude = "";
            var gmap_longitude = "";

            <c:forEach var="entry" items="${groupPurchaseAlllist}" varStatus="count" begin="0">
                message = "<table style='width: 350px;'>";
                message = message + "<tr style='height: 35px;'>";
                message = message + "  <td style='color:#900C3E;font-weight: bold;' colspan='2'>${entry.groupPurchaseTitle}</td>";
                message = message + "</tr>";
                message = message + "<tr style='height: 20px;'>";
                message = message + "  <td style='width: 25px;text-align: center;'><i class='fa fa-map-marker fa-lg' aria-hidden='true'></i></td>";
                message = message + "  <td><span style='color: #0095DA;font-weight:bold;'>${entry.marketAddressSuburb}</span> ${entry.marketAddressPostcode}</td>";
                message = message + "</tr>";
                message = message + "<tr style='height: 20px;'>";
                message = message + "  <td style='width: 25px;text-align: center;'><i class='fa fa-clock-o fa-lg' aria-hidden='true'></i></td>";
                message = message + "  <td>${entry.marketOpenStartDate} <span style='text-decoration: underline;'>${entry.marketOpenStartTime} ~ ${entry.marketOpenEndTime}</span></td>";
                message = message + "</tr>";

                if('${entry.stopSelling}' == 'Y') {
                    message = message + "<tr style='height: 20px;'>";
                    message = message + "  <td style='text-align: right;padding-top: 15px;' colspan='2'><b style='color: #EB7D3C;'>공.구 마감 </b>: ${entry.stopSellingReason}</td>";
                    message = message + "</tr>";
                } else {
                    message = message + "<tr style='height: 20px;'>";
                    message = message + "  <td style='text-align: right;' colspan='2'><a href=\"javascript:goGroupPurchaseMain('${entry.groupPurchaseId}\')\">공.구하러 가기</a> &nbsp;&nbsp;<i class='fa fa-sign-in' aria-hidden='true'></i></td>";
                    message = message + "</tr>";
                }

                message = message + "</table>";

                address = "${entry.marketAddressStreet}" + " " + "${entry.marketAddressSuburb}" + " " + "${entry.marketAddressState}" + " " + "${entry.marketAddressPostcode}";
                gmap_latitude = "${entry.marketGmapLatitude}";
                gmap_longitude = "${entry.marketGmapLongitude}";

                if('${entry.stopSelling}' == 'Y') {
                    // points.push({address:address, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/ms/micons/yellow.png'});
                    points.push({latitude:gmap_latitude, longitude:gmap_longitude, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/ms/micons/yellow.png'});

                } else {

                    if(0 <= ${count.index} && ${count.index} < 1) {
                        // points.push({address:address, message: message, clickEvent: true, iconUrl:'http://maps.google.com/mapfiles/kml/paddle/${count.index + 1}.png'});
                        points.push({latitude:gmap_latitude, longitude:gmap_longitude, address:address, message: message, clickEvent: true, iconUrl:'http://maps.google.com/mapfiles/kml/paddle/${count.index + 1}.png'});

                    } else if(1 <= ${count.index} && ${count.index} < 10) {
                        //points.push({address:address, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/kml/paddle/${count.index + 1}.png'});
                        points.push({latitude:gmap_latitude, longitude:gmap_longitude, address:address, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/kml/paddle/${count.index + 1}.png'});

                    } else {
                        // points.push({address:address, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/ms/micons/red-dot.png'});
                        points.push({latitude:gmap_latitude, longitude:gmap_longitude, address:address, message: message, clickEvent: false, iconUrl:'http://maps.google.com/mapfiles/ms/micons/red-dot.png'});

                    }
                }

            </c:forEach>

            MelfoodGmap.mapMultiplePoints = points;

            markAddressOnGMap(MelfoodGmap);

        }
    </script>

</head>

<body>
<div class="row">
    <div class="col-sm-12" align="right">
        <table>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${previousPage == 0 && currPage == previousPage}">
                            <i class="fa fa-step-backward" aria-hidden="true"></i>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:goChangePage('${previousPage}')"><i class="fa fa-step-backward" aria-hidden="true" style="color: #2185E8;"></i></a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="padding-left: 10px;padding-right: 10px;">공구일정</td>
                <td>
                    <c:choose>
                        <c:when test="${nextPage == 99999 }">
                            <i class="fa fa-step-forward" aria-hidden="true">
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:goChangePage('${nextPage}')"><i class="fa fa-step-forward" aria-hidden="true" style="color: #2185E8;"></i></a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </div>
</div>

<!-- 공동구매목록-->
<c:forEach var="groupPurchase" items="${groupPurchaselist}" varStatus="count1" begin="0">

    <div class="row gppurchase" style="height: 170px;">
        <div class="col-sm-3">
            <table style="width: 100%;color: #606060;">
                <tr style="height: 30px;"><td colspan="3" style="font-size: 15px;font-weight: bold;color: #2A2A2A;">${groupPurchase.groupPurchaseTitle}</td></tr>
                <tr style="height: 25px;">
                    <td style="width: 25px;text-align: center;"><i class="fa fa-map-marker fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;height: 25px;">${groupPurchase.marketAddressSuburb}</td>
                </tr>
                <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;">
                            ${groupPurchase.marketOpenStartDate} <span style="color:#606060;">${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</span>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${groupPurchase.deliverable == 'Y'}">
                        <tr style="height: 25px;">
                            <td style="width: 35px;text-align: center;"><i class="fa fa-truck" aria-hidden="true"></i></td>
                            <td style="font-weight: bold;">배달 가능</td>
                        </tr>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${groupPurchase.minimumPurchaseAmount > 0.0}">
                        <tr style="height: 25px;">
                            <td></td>
                            <td style="font-weight: bold;color: #FF5832;">
                                Minimum order $ <fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.minimumPurchaseAmount}" />
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
                <tr style="height: 25px;"><td></td><td>&nbsp;</td></tr>
            </table>
        </div>


        <div class="col-sm-2">
            <table style="width: 100%;color: #606060;">
                    <c:choose>
                        <c:when test="${groupPurchase.stopSelling == 'N'}">
                                <tr><td><img src="/resources/image/good_grp_buy.jpg" style="width: 80px;"></td></tr>
                                <tr><td style="text-align: right;padding-top: 5px;"><a href="javascript:goGroupPurchaseMain('${groupPurchase.groupPurchaseId}')">공동구매 참여하기 <img src="/resources/image/click-here.png" style="width: 40px;"> </a></td></tr>
                            </table>
                        </c:when>
                        <c:when test="${groupPurchase.stopSelling == 'Y'}">
                            <table style="width: 100%;">
                                <tr><td><img src="/resources/image/close-order.png" style="width: 80px;"></td></tr>
                                <tr><td style="text-align: right;padding-top: 5px;color: #BE0712;">${groupPurchase.stopSellingReason}</td></tr>
                            </table>
                        </c:when>
                        <c:otherwise>
                            ?
                        </c:otherwise>
                    </c:choose>
            </table>
        </div>


        <div class="col-sm-5" style="padding-left: 50px;">
            <div class="owl-carousel owl-theme" style="padding-top: 5px;">
                <c:forEach var="groupPurchaseImage" items="${groupPurchase.groupPurchaseImages}" varStatus="count" begin="0">
                    <div class="item">
                        <img src="/img/?f=${groupPurchaseImage.imageFileId}" style="width: 130px;"/>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>

</c:forEach>






<!-- 협동조합 파트너스 -->
<div class="row" align="center" style="background-color: #1E1E1E;padding-top: 5px;padding-bottom: 5px;">
    <div class="col-sm-12"></div>
</div>
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;color: #DDDEE0;padding-top: 5px;width: 150px;padding-left: 50px;">협동조합 파트너스</td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td style="text-align: left;color: #DDDEE0;padding-top: 5px;padding-left: 10px;height: 40px;font-size: 15px;">
                        <span style="font-weight: bold;text-decoration: underline">로즈베이커리</span>
                        | <span style="font-weight: bold;text-decoration: underline">족발과의 동침</span>
                        | <span style="font-weight: bold;text-decoration: underline">조선김치</span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>


<!-- 하단에 지도표시 -->
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div id='map-canvas' style="width: 100%;height: 450px;"></div>
    </div>
</div>



</body>
</html>