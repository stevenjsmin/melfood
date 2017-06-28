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
    </style>

    <script>
        $(document).ready(function() {
            var owl = $('.owl-carousel');
            owl.owlCarousel({
                margin: 5,
                nav: false,
                loop: false,
                responsive: {
                    0: {
                        items: 1
                    },
                    600: {
                        items: 3
                    },
                    1000: {
                        items: 5
                    }
                }
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


    <c:forEach var="groupPurchase" items="${groupPurchaselist}" varStatus="count1" begin="0">

        <div class="row gppurchase" style="height: 170px;">
            <div class="col-sm-4">
                <table style="width: 100%;color: #606060;">
                    <tr style="height: 30px;"><td colspan="3" style="font-size: 15px;font-weight: bold;color: #2A2A2A;">${groupPurchase.groupPurchaseTitle}</td></tr>
                    <tr style="height: 25px;">
                        <td style="width: 25px;text-align: center;"><i class="fa fa-map-marker fa-lg" aria-hidden="true"></i></td>
                        <td style="font-weight: bold;height: 25px;">${groupPurchase.marketAddressSuburb}</td>
                        <c:choose>
                            <c:when test="${groupPurchase.deliverable == 'Y' && groupPurchase.minimumPurchaseAmount > 0.0}"><c:set var = "rowSpan" scope = "session" value = "5"/></c:when>
                            <c:when test="${groupPurchase.deliverable == 'Y' && groupPurchase.minimumPurchaseAmount == 0.0}"><c:set var = "rowSpan" scope = "session" value = "4"/></c:when>
                            <c:when test="${groupPurchase.deliverable == 'N' && groupPurchase.minimumPurchaseAmount > 0.0}"><c:set var = "rowSpan" scope = "session" value = "4"/></c:when>
                            <c:when test="${groupPurchase.deliverable == 'N' && groupPurchase.minimumPurchaseAmount == 0.0}"><c:set var = "rowSpan" scope = "session" value = "3"/></c:when>
                        </c:choose>
                        <td rowspan="${rowSpan}" style="width: 200px;">
                            <c:choose>
                                <c:when test="${groupPurchase.stopSelling == 'N'}">
                                    <table style="width: 100%;">
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
                        </td>
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

            <div class="col-sm-8" style="padding-left: 50px;">
                <div class="owl-carousel owl-theme" style="padding-top: 5px;">
                    <c:forEach var="groupPurchaseImage" items="${groupPurchase.groupPurchaseImages}" varStatus="count2" begin="0">
                        <div class="item">
                            <img src="/img/?f=${groupPurchaseImage.imageFileId}" style="height: 120px;"/>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>

    </c:forEach>






<div class="row" align="center" style="background-color: #1E1E1E;margin-top: 40px;padding-top: 10px;padding-bottom: 10px;">
    <div class="col-sm-12">
        <table style="width: 100%;">
            <tr>
                <td style="text-align: left;vertical-align: top;color: #797979;padding-top: 5px;width: 120px;">협동조합 파트너스</td>
                <td>
                    <table align="center">
                        <tr>
                            <td style="text-align: center;" id="mousefollow"><img class="circular-square" title="로즈 베이커리" src="/resources/image/sample/partner_1.jpg"/></td>
                            <td style="width: 10px;">&nbsp;</td>
                            <td style="text-align: center"><img class="circular-square" title="조선김치" src="/resources/image/sample/partner_2.jpg" /></td>
                            <td style="width: 10px;">&nbsp;</td>
                            <td style="text-align: center"><img class="circular-square" title="족과의 동침" src="/resources/image/sample/partner_3.jpg" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>


<div class="row">
    <div class="col-sm-12" style="padding-right: 0px;padding-left: 0px">
        <img src="/resources/image/pizza-desktop.jpg" style="width: 100%;">
    </div>
</div>








</body>
</html>