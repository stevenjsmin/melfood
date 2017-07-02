<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<head>
    <style>
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
        }
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),url(/resources/image/generic_backdrop.png);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 210px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".amountOfOrder").kendoNumericTextBox({
                max: 99999,
                min: 0,
                format: "n0"
            });

            if('${groupPurchase.deliverable}' == 'Y'){
                $.ajax({
                    url: "/grouppurchase/checkDeliverable.yum",
                    data: {
                        groupPurchaseId: ${groupPurchase.groupPurchaseId}
                    },
                    success: callbackCheckDeliverable
                });
            }

            // 공.구 장소를 지도상으로 보여준다.
            displayGroupPurchaseMarketPlace();

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <script type="text/javascript">
        // document.location.href = "/common/auth/welcome.yum";

        function callbackCheckDeliverable(data) {
            var resultCode = data.resultCode;
            var mapResultCode = data.mapResultCode;
            var mapResultMessage = data.mapResultMessage;

            var cutomerAddress = data.cutomerAddress;
            var deliveryFee = data.deliveryFee;
            var estimatedDeliveryTime = data.estimatedDeliveryTime;

            var distance = data.distance;
            var duration = data.duration;

            if(resultCode == "OK"){
                $("#DELIVERY_SERVICE_DETAIL").show();
                $("#cutomerAddress").html(cutomerAddress);
                $("#estimatedDeliveryTime").html(estimatedDeliveryTime);
                $("#distance").html(distance);
                $("#deliveryFee").html(deliveryFee);

            } else if(resultCode == "CUSTOMER_ADDR_INVALID"){
                $("#CUSTOMER_ADDR_INVALID").show();
                $("#customerAddrInvalidddress").html(cutomerAddress);

            } else  if(resultCode == "NO_DELIVERABLE_SERVICE_AREA") {
                $("#NO_DELIVERABLE_SERVICE_AREA").show();
                $("#noDeliverableServiceAreaMessage").html(mapResultMessage);
            } else {
                $("#UNKNOWN_ERROR").show();
                $("#unknownErrorCutomerAddress").html(cutomerAddress);
                $("#unknownErrorMessage").html(mapResultMessage);
            }
        }

    </script>

    <script type="text/javascript">
        function groupPurchaseOrganizer() {

            $("#purchaseOrganizerPopup").kendoWindow({
                content: "/grouppurchase/purchaseOrganizerInfo.yum?purchaseOrganizer=" + "${groupPurchase.purchaseOrganizer}",
                actions: ["Minimize", "Maximize", "Close"],
                title: "내가잘사는 방법:: MelFood",
                modal: true,
                iframe: true,
                position:{ top:"200", left:"25%"}
            });

            var popup_dialog = $("#purchaseOrganizerPopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 800,
                height: 350
            });
            // popup_dialog.center();

            $("#purchaseOrganizerPopup").data("kendoWindow").open();
        }
        function closePurchaseOrganizerPopup() {
            var win_dialog = $("#purchaseOrganizerPopup").data("kendoWindow");
            win_dialog.close();
        }


        function deliverySchedulePopup() {

            $("#deliverySchedulePopup").kendoWindow({
                content: "/grouppurchase/deliverySchedule.yum?groupPurchaseId=" + "${groupPurchase.groupPurchaseId}",
                actions: ["Minimize", "Maximize", "Close"],
                title: "내가잘사는 방법:: MelFood",
                modal: true,
                iframe: true,
                position:{ top:"300", left:"25%"}
            });

            var popup_dialog = $("#deliverySchedulePopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 800,
                height: 350
            });
            // popup_dialog.center();

            $("#deliverySchedulePopup").data("kendoWindow").open();
        }
        function closeDeliverySchedulePopup() {
            var win_dialog = $("#deliverySchedulePopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>

    <script type="text/javascript">
        function displayGroupPurchaseMarketPlace() {
            var MelfoodGmap = new Object();
            MelfoodGmap.mapName = '멜푸드지도';
            MelfoodGmap.mapStyleNo = 6;
            MelfoodGmap.mapIsMultipleMark = 'N';
            MelfoodGmap.mapZoomLevel = 18;
            MelfoodGmap.mapAddress = '${groupPurchase.marketAddressStreet} ${groupPurchase.marketAddressSuburb} ${groupPurchase.marketAddressState} ${groupPurchase.marketAddressPostcode}';

            var mapMessage = "<table style='width: 380px;height: 70px;'>";
                mapMessage = mapMessage + "<tr>"
                mapMessage = mapMessage + "  <td style='width: 40px;text-align: center;'><i class='fa fa-clock-o fa-lg' aria-hidden='true'></i></td>"
                mapMessage = mapMessage + "  <td>${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</td>"
                mapMessage = mapMessage + "</tr>"
                mapMessage = mapMessage + "<tr>"
                mapMessage = mapMessage + "  <td></td>"
                mapMessage = mapMessage + "  <td>근처에오셔서 위치를 잘 모르실때 공.구 진행자에게 전화주세요</td>"
                mapMessage = mapMessage + "</tr>"
                mapMessage = mapMessage + "<tr>"
                mapMessage = mapMessage + "  <td style='width: 40px;text-align: center;'><i class='fa fa-mobile fa-lg' aria-hidden='true'></i></td>"
                mapMessage = mapMessage + "  <td style='color:#900C3E;'>${organizer.mobile}</td>"
                mapMessage = mapMessage + "</tr>"
                mapMessage = mapMessage + "</table>"
            MelfoodGmap.mapMessage = mapMessage;

            markAddressOnGMap(MelfoodGmap);
            markStreeViewOnGMap(MelfoodGmap);
            $("#streetViewMap").show();
            $("#map-street-canvas").show();
        }
    </script>

</head>

<body>
<div id="purchaseOrganizerPopup"></div>
<div id="deliverySchedulePopup"></div>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 상단 [시작] :: 공동구매 개요 -->
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper">
            <table align="center">
                <tr>
                    <td style="padding: 0px 30px 0px 0px;">
                        <img src="/img/?f=${firstImageId}" style="height: 100px;"/>
                    </td>
                    <td>
                        <table align="center"  style="color: #FFFFFF;">
                            <tr>
                                <td>
                                    <div style="font-weight: bold;font-size: 25px;">
                                        ${groupPurchase.groupPurchaseTitle}
                                    </div>
                                    <i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>&nbsp;&nbsp;
                                    <span style="font-size: 13px;font-weight: bold;">
                                    ${groupPurchase.marketAddressStreet}
                                    ${groupPurchase.marketAddressSuburb}
                                    ${groupPurchase.marketAddressState}
                                    ${groupPurchase.marketAddressPostcode}
                                    </span>
                                </td>
                            </tr>

                            <c:choose>
                                <c:when test="${groupPurchase.marketAddressComment != '' && groupPurchase.marketAddressComment != null}">
                                    <tr>
                                        <td style="padding-top: 5px;padding-left: 20px;color: #b3b3b3;">${groupPurchase.marketAddressComment}</td>
                                    </tr>
                                </c:when>
                            </c:choose>

                            <tr>
                                <td>
                                    <table style="color: #FFFFFF;margin-top: 20px;width: 100%;">
                                        <tr style="height: 50px;">
                                            <td style="padding-right: 20px;">
                                                <table style="color: #FFFFFF;width: 100%;">
                                                    <tr>
                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                                                        <td>
                                                            ${groupPurchase.marketOpenStartDate}
                                                            <span style="text-decoration: underline;">${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</span>
                                                        </td>
                                                    </tr>

                                                    <c:choose>
                                                        <c:when test="${groupPurchase.discountMethod != '' && groupPurchase.discountMethod != null}">
                                                            <c:choose>
                                                                <c:when test="${groupPurchase.discountMethod == 'FIXED' && groupPurchase.discountFixedAmount != '' && groupPurchase.discountFixedAmount != null}">
                                                                    <tr>
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-usd" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.discountFixedAmount}" /> Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:when test="${groupPurchase.discountMethod == 'RATE' && groupPurchase.discountRateValue != '' && groupPurchase.discountRateValue != null}">
                                                                    <tr>
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-percent" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###" value="${groupPurchase.discountRateValue * 100}" /> (Percent) Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:when>
                                                    </c:choose>

                                                    <c:choose>
                                                        <c:when test="${groupPurchase.deliverable == 'Y'}">
                                                            <tr>
                                                                <td style="width: 25px;text-align: center;"><i class="fa fa-truck" aria-hidden="true"></i></td>
                                                                <td style="padding-top: 5px;color: #AFB1B1;">배달 가능</td>
                                                            </tr>
                                                        </c:when>
                                                    </c:choose>

                                                </table>


                                            </td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;"><a href="javascript:groupPurchaseOrganizer()" style="color: #69B7F5;">공동구매 진행자정보</a></td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;">
                                                <div class="alert alert-danger" role="alert" style="padding: 5px 20px 5px 20px;">
                                                     <span style="color: #606060;">Minimum order $ <fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.minimumPurchaseAmount}" />
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

    </div>
</div>
<!-- 페이지 상단 [끝]:: 공동구매 개요 -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 중간 [시작] :: 오른쪽:아이템목록 왼쪽:주문내역 -->
<div class="row" style="padding-top: 10px;">

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 오른쪽:아이템목록 -->
    <div class="col-sm-7">


        <div class="row">
            <div class="col-sm-12">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-gift fa-3x" aria-hidden="true" style="color:#6F0E06;"></i></td>
                                <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">공동구매 아이템</span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 20px;">
                        <p>${groupPurchase.groupPurchaseNotice}</p>
                    </div>

                    <!-- Table -->
                    <table class="table bootstrap-tbl">
                        <tr>
                        <c:forEach var="groupPurchaseProduct" items="${groupPurchaseProducts}" varStatus="count1" begin="0">
                            <tr>
                                <td style="vertical-align: top;text-align: center;width: 120px;">
                                    <c:choose>
                                        <c:when test="${groupPurchaseProduct.product.productImage.imageFileId != 0 and groupPurchaseProduct.product.productImage.imageFileId != null}">
                                            <img id="profilePhotoId" src="/img/?f=${groupPurchaseProduct.product.productImage.imageFileId}" style="width: 120px;" class="circular-square">
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-gift fa-5x" aria-hidden="true" style="color: #c7d0d2;"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="vertical-align: middle;padding-bottom: 10px;padding-top: 10px;">

                                    <table style="width: 100%;">
                                        <tr><td colspan="3" style="height: 25px;font-weight: bold;font-size: 15px;color: #BE0712;">${groupPurchaseProduct.product.name}</td></tr>
                                        <c:choose>
                                            <c:when test="${groupPurchaseProduct.product.productOptionGroups != null && fn:length(groupPurchaseProduct.product.productOptionGroups) gt 0 }">
                                                <c:forEach var="productOptionGroup" items="${groupPurchaseProduct.product.productOptionGroups}" varStatus="count1" begin="0">
                                                    <tr>
                                                        <td style="color: #797979; text-align: right;">${productOptionGroup.optionLabel} : </td>
                                                        <td style="padding: 5px 5px;color: #505050;"><c:out value="${productOptionGroup.optionCmbx}" escapeXml="false"/></td>
                                                        <td></td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                        <tr>
                                            <td style="color: #797979; text-align: right;width: 150px;">단가 :</td>
                                            <td style="padding: 5px 5px;color: #505050;width: 200px;">$ ${groupPurchaseProduct.product.unitPrice}</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">생산자 :</td>
                                            <td style="padding: 5px 5px;">${groupPurchaseProduct.product.sellerName}</td>
                                            <td>
                                                <table>
                                                    <tr>
                                                        <td style="padding-left: 40px;">
                                                            <input type="text" class="amountOfOrder" id="amountOfOrder$_{groupPurchaseProduct.product.prodId}" name="amountOfOrder$_{groupPurchaseProduct.product.prodId}" value='0' maxlength="2" style="width: 100px; text-align: center;font-weight: bold;"/>
                                                        </td>
                                                        <td style="padding-left: 20px;">
                                                            <i class="fa fa-minus-square fa-2x" aria-hidden="true" style="color: #797979;"></i>
                                                            <i class="fa fa-plus-square fa-2x" aria-hidden="true" style="color: #797979;"></i>

                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>

                                </td>

                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>


        <!-- 배달서비스 -->
        <c:choose>
            <c:when test="${groupPurchase.deliverable == 'Y'}">
                <div class="row" style="padding-top: 20px;">
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                <table style="width: 100%;">
                                    <tr>
                                        <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-truck fa-3x" aria-hidden="true" style="color:#514747;"></i></td>
                                        <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">배달서비스</span></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="panel-body" style="padding-left: 20px;padding-bottom: 20px;padding-top: 20px;padding-right: 100px;">

                                <div class="alert alert-warning" id="CUSTOMER_ADDR_INVALID" style="display: none;">
                                    <table style="width: 100%;">
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td style="color: #900C3E;">현재 고객님의 주소가 유효하지 않습니다.</td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>
                                        <tr style="height: 30px;">
                                            <td></td>
                                            <td><span style="color: #A2A4A4;">현재 고객님의 주소 :</span> <span id="customerAddrInvalidddress" style="color: #505050;"></span> </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td style="text-align: right;"> <a href="/customer/mypage/myDetailInfo.yum">My 푸드 > 개인정보 변경</a> 에서 주소를 수정하실수 있습니다.</td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="alert alert-warning" id="NO_DELIVERABLE_SERVICE_AREA"  style="display: none;">
                                    <table style="width: 100%;">
                                        <colgroup>
                                            <col style="width: 80px;">
                                            <col style="width: 10px;">
                                            <col>
                                        </colgroup>
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info fa-2x" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td></td>
                                            <td style="color: #F15F4C;"><span id="noDeliverableServiceAreaMessage"></span></td>
                                        </tr>
                                        <tr style="height: 30px;">
                                            <td style="text-align: right" colspan="3"><a href="javascript:deliverySchedulePopup();">배송가능 지역보기</a></td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="DELIVERY_SERVICE_DETAIL"  style="display: none;">
                                    <table class="table table-striped">
                                        <colgroup>
                                            <col style="width: 100px;">
                                            <col style="width: 10px;">
                                            <col style="width: *;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">고객님주소</td>
                                            <td>:</td>
                                            <td><span id="cutomerAddress"></span> &nbsp;&nbsp;<a href="/customer/mypage/myDetailInfo.yum" style="font-size: 10px;">주소변경</a></td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">거리</td>
                                            <td>:</td>
                                            <td><span id="distance">0</span> Km</td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">배송예정시간</td>
                                            <td>:</td>
                                            <td><span id="estimatedDeliveryTime"></span></td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">배송비</td>
                                            <td>:</td>
                                            <td>$ <span id="deliveryFee">0.00</span> (기본서비스 + 서비스비/KM)</td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" style="text-align: right; padding-right: 20px;color: #505050;font-weight: bold;">
                                                <label style="color: #117899;">배송서비스를 이용하시겠습니까 ? &nbsp;&nbsp;&nbsp;<input type="checkbox" value="" style="transform: scale(1.5);"></label>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="alert alert-warning" id="UNKNOWN_ERROR" style="display: none;">
                                    <table style="width: 100%;">
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td style="color: #900C3E;">죄송합니다. 배송비를 계산할 수가 없어 배송서비스를 이용하실수 없습니다.</td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>
                                        <tr style="height: 30px;">
                                            <td></td>
                                            <td><span style="color: #A2A4A4;" id="unknownErrorMessage"></span></td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>
                                        <tr>
                                            <td></td>
                                            <td>
                                                <table style="width: 100%;">
                                                    <tr>
                                                        <td style="color: #797979; text-align: right;width: 80px;">고객님주소</td>
                                                        <td style="width: 10px;"> : </td>
                                                        <td><span id="unknownErrorCutomerAddress"></span> &nbsp;&nbsp;<a href="/customer/mypage/myDetailInfo.yum" style="font-size: 10px;">주소변경</a></td>
                                                    </tr>
                                                </table>

                                            </td>
                                        </tr>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:when>
        </c:choose>



        <div class="row" style="padding-top: 20px;">
            <div class="col-sm-12">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-map fa-3x" aria-hidden="true" style="color:#514747;"></i></td>
                                <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">장소</span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 20px;">
                        <table>
                            <tr style="height: 25px;">
                                <td style="width: 50px; text-align: center;"><i class="fa fa-map-marker fa-lg" aria-hidden="true"></i></td>
                                <td>${groupPurchase.marketAddressStreet} ${groupPurchase.marketAddressSuburb} ${groupPurchase.marketAddressState} ${groupPurchase.marketAddressPostcode}</td>
                            </tr>
                            <tr style="height: 25px;">
                                <td style="width: 50px; text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                                <td>${groupPurchase.marketOpenStartDate} <span style="text-decoration: underline;">${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</span></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="padding-top: 10px;"><p style="color: #797979;">${groupPurchase.marketAddressComment}</p></td>
                            </tr>
                        </table>


                        <table style="width: 100%;border-color: #D6D6D6;">
                            <tr>
                                <td style="padding-left: 50px; padding-right: 50px;padding-top: 30px;">
                                    <div id='map-canvas' style="width: 100%;height: 450px;"></div>

                                    <div id="streetViewMap" style="padding-top: 20px;">
                                        <span class="subtitle"> Street view</span>
                                        <hr class="subtitle"/>
                                        <div id='map-street-canvas' style="width: 100%; height: 450px;"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </div>
        </div>





    </div>
    <!-- 페이지 중간 [끝] :: 오른쪽:아이템목록 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 오른쪽:주문내역 -->
    <div class="col-sm-6">

    </div>
    <!-- 페이지 중간 [끝] :: 오른쪽:주문내역 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


</div>
<!-- 페이지 중간 [끝] :: :: 오른쪽:아이템목록 왼쪽:주문내역  -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

</body>
</html>