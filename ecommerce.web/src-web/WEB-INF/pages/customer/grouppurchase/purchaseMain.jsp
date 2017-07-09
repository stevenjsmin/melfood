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
                change: parseProductOrderValue,
                spin: parseProductOrderValue,
                max: 99999,
                min: 0,
                format: "n0"
            });

            progressWithId(true, 'dilivery_service_row');
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
        function callbackCheckDeliverable(data) {
            var resultCode = data.resultCode;
            var mapResultCode = data.mapResultCode;
            var mapResultMessage = data.mapResultMessage;

            var cutomerAddress = data.cutomerAddress;
            var deliveryFee = data.deliveryFee;
            var estimatedDeliveryTime = data.estimatedDeliveryTime;

            var distance = data.distance;
            var duration = data.duration;

            progressWithId(false, 'dilivery_service_row');
            if(resultCode == "OK"){
                $("#DELIVERY_SERVICE_DETAIL").show();
                $("#cutomerAddress").html(cutomerAddress);
                $("#estimatedDeliveryTime").html(estimatedDeliveryTime);
                $("#distance").html(distance);
                $("#deliveryFee").html(deliveryFee);
                $("#deliveryServiceFee").val(deliveryFee);

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
            var points = [];

            var MelfoodGmap = new Object();
            MelfoodGmap.mapName = '멜푸드지도';
            MelfoodGmap.mapStyleNo = 6;
            MelfoodGmap.mapIsMultipleMark = 'N';
            MelfoodGmap.mapZoomLevel = 15;

            // 단일 포인트를 나타탈때는 MelfoodGmap에 직접 위치 정보를 설정해준다
            MelfoodGmap.mapAddress = '${groupPurchase.marketAddressStreet} ${groupPurchase.marketAddressSuburb} ${groupPurchase.marketAddressState} ${groupPurchase.marketAddressPostcode}';
            MelfoodGmap.mapLatitude = "${groupPurchase.marketGmapLatitude}";
            MelfoodGmap.mapLongitude = "${groupPurchase.marketGmapLongitude}";

            var message = "<table style='width: 300px;height: 70px;'>";
                message = message + "<tr>"
                message = message + "  <td style='width: 30px;text-align: center;'><i class='fa fa-clock-o fa-lg' aria-hidden='true'></i></td>"
                message = message + "  <td>${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</td>"
                message = message + "</tr>"
                message = message + "<tr>"
                message = message + "  <td style='width: 30px;text-align: center;'><i class='fa fa-mobile fa-lg' aria-hidden='true'></i></td>"
                message = message + "  <td>공.구 진행자 모바일 : <span style='color:#900C3E;'>${organizer.mobile}</span></td>"
                message = message + "</tr>"
                message = message + "</table>"

            // 한개의 포인트만 담는다.
            points.push({latitude:'${groupPurchase.marketGmapLatitude}', longitude:'${groupPurchase.marketGmapLongitude}', message: message, clickEvent: true, iconUrl:'/resources/image/map_marker12.png'});
            MelfoodGmap.mapMultiplePoints = points;

            markAddressOnGMap(MelfoodGmap);
            markStreeViewByCoordinate(MelfoodGmap);

        }
    </script>

    <script type="text/javascript">
        function increaseDecreaseOrderAmount(objectId, downOrUp) {
            var currentAmount = $("#" + objectId).val();
            var newAmount = currentAmount;

            var numerictextbox = $("#" + objectId).data("kendoNumericTextBox");

            if(downOrUp == "DOWN") {
                if(currentAmount == 0 || currentAmount < 0 ) {
                    newAmount = 0;
                } else {
                    newAmount = Number(currentAmount) - Number(1);
                }
            } else {
                if(currentAmount >= 99){
                    newAmount = 99;
                } else {
                    newAmount = Number(currentAmount) + Number(1);
                }
            }

            $("#" + objectId).val(newAmount);
            numerictextbox.value($("#" + objectId).val());

            // 상품의 총주문 가격을 구한다.
            parseProductOrderValue();
        }
    </script>

    <script type="text/javascript">
        function parseProductOrderValue() {
            var amount = 0;
            var totalProdAmount = 0;
            var deliveryFee = 0;
            var subTotal = 0.0;
            var toBeDiscountAmount = 0.0;
            var finalAmount = 0.0;
            <c:forEach var="groupPurchaseProduct" items="${groupPurchaseProducts}" varStatus="count" begin="0">
                amount = $("#amountOfOrder_" + ${groupPurchaseProduct.product.prodId}).val();

                if(amount != 0){
                    totalProdAmount = Number(totalProdAmount) + Number(amount * ${groupPurchaseProduct.product.unitPrice});
                }
            </c:forEach>

            $('#payment_totalAmountForProduct').html(toCurrency(totalProdAmount, false));

            <c:choose>
                <c:when test="${groupPurchase.deliverable == 'Y'}">
                    if($('#useDeliveryService_' + ${groupPurchase.groupPurchaseId}).prop('checked') == true) {
                        deliveryFee = Number($("#deliveryServiceFee").val());
                    }

                    $('#payment_deliveryFee').html(toCurrency(deliveryFee, false));

                </c:when>
            </c:choose>

            subTotal = Number(totalProdAmount) + Number(deliveryFee);

            <c:choose>
                <c:when test="${groupPurchase.discountMethod == 'RATE'}">
                    // toBeDiscountAmount =  (${groupPurchase.discountRateValue * 100}/100) * subTotal; // 배송비를 포함해서 할인했을 경우
                    toBeDiscountAmount =  (${groupPurchase.discountRateValue * 100}/100) * totalProdAmount; // 제품 구매비만 할인했을 경우
                </c:when>
                <c:when test="${groupPurchase.discountMethod == 'FIXED'}">
                    toBeDiscountAmount = subTotal - ${groupPurchase.discountFixedAmount};
                </c:when>
            </c:choose>
            if(toBeDiscountAmount >= subTotal ) toBeDiscountAmount = 0;
            $('#payment_toBeDiscountAmount').html(toCurrency(toBeDiscountAmount, false));

            finalAmount = subTotal - toBeDiscountAmount;
            $('#payment_finalAmount').html(toCurrency(finalAmount, false));

        }
    </script>

    <script type="text/javascript">
        function getPaymentMethodInfo(obj) {

            var methodSeq = obj.value;

            if(methodSeq != '' & methodSeq != null) {
                $.ajax({
                    url: "/grouppurchase/getPaymentMethodInfo.yum",
                    data: {
                        purchaseOrganizer: '${groupPurchase.purchaseOrganizer}',
                        methodSeq: obj.value
                    },
                    success: callbackGetPaymentMethodInfo
                });

            } else {
                $('#detail_info_back_account').hide();
            }

        }
        function callbackGetPaymentMethodInfo(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            var bankName = data.paymentMethod.bankNameCodeName;
            var bankBsb = data.paymentMethod.bankBsb;
            var bankAccountNo = data.paymentMethod.bankAccountNo;
            var bankAccountOwnerName = data.paymentMethod.bankAccountOwnerName;

            var paymentMethod = data.paymentMethod.paymentMethod;

            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                if(paymentMethod == 'ACCOUNT_TRANSFER') {
                    $('#detail_info_back_account').show();
                    $('#payment_bankTransfer_message').show();

                    $('#payment_bankName').html(bankName);
                    $('#payment_bankBsbBankAccountNo').html(bankBsb + " - " + bankAccountNo);
                    $('#payment_bankAccountOwnerName').html(bankAccountOwnerName);
                } else {
                    $('#detail_info_back_account').hide();
                    $('#payment_bankTransfer_message').hide();
                }
            }
        }
    </script>


    <script type="text/javascript">
        function askQuestion(receiverUserId){

            $("#askQuestionPopup").kendoWindow({
                content: "/framework/communicationmanager/sendMessageFormWIthHistory.yum?receiverUserId=" + receiverUserId,
                actions: [ "Minimize", "Maximize","Close" ],
                title: "SEND MESSAGE",
                modal: true,
                iframe: true
            });

            var popup_dialog = $("#askQuestionPopup").data("kendoWindow");
            popup_dialog.setOptions({
                pinned: true,
                width: 650,height: 500,
                open: function (e) {
                    this.wrapper.css({ top: 100 });
                }
            });
            popup_dialog.center().open();

        }
        function closeAskQuestionPopupPopup() {
            var win_dialog = $("#askQuestionPopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>


    <script type="text/javascript">
        function doPaymentProcess() {

        }
    </script>


</head>

<body>
<div id="askQuestionPopup"></div>
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
                                            <td style="padding: 20px 20px;">
                                                <table>
                                                    <tr>
                                                        <td style="width: 20px;text-align: center;"><i class="fa fa-address-card-o" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                                        <td style="width: 10px;"></td>
                                                        <td><a href="javascript:groupPurchaseOrganizer()" style="color: #69B7F5;">공동구매 진행자정보</a><br/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="width: 20px;text-align: center;"><i class="fa fa-comment" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                                        <td style="width: 10px;"></td>
                                                        <td><a href="javascript:askQuestion('${groupPurchase.purchaseOrganizer}')" style="color: #69B7F5;"> ?!! 물어보세요</a><br/></td>
                                                    </tr>
                                                </table>




                                            </td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;">
                                                <div class="alert alert-danger" role="alert" style="padding: 5px 20px 5px 20px;">
                                                     <span style="color: #606060;">최소주문 금액 : $ <fmt:formatNumber type="number" pattern="##0.00" value="${groupPurchase.minimumPurchaseAmount}" />
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
    <!-- 페이지 중간 [시작] :: 왼쪽:아이템목록 -->
    <div class="col-sm-6" style="padding-right: 5px;padding-left: 5px;">

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
                        <c:forEach var="groupPurchaseProduct" items="${groupPurchaseProducts}" varStatus="count" begin="0">
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
                                                <c:choose>
                                                    <c:when test="${groupPurchaseProduct.stopSelling == 'Y' }">

                                                        <table style="width: 200px;">
                                                            <tr><td style="padding-left: 10px;color: #58A578;font-size: 14px;font-weight: bold;">판매정지</td></tr>
                                                            <tr><td style="text-align: right;color: #505050;padding-left: 10px;">${groupPurchaseProduct.stopSellingReason}</td></tr>
                                                        </table>
                                                        <input type="hidden" id="amountOfOrder_${groupPurchaseProduct.product.prodId}" name="amountOfOrder_${groupPurchaseProduct.product.prodId}" value='0'/>

                                                    </c:when>
                                                    <c:otherwise>
                                                        <table style="width: 200px;">
                                                            <tr>
                                                                <td style="padding-left: 10px;">
                                                                    <input type="text" class="amountOfOrder" id="amountOfOrder_${groupPurchaseProduct.product.prodId}" name="amountOfOrder_${groupPurchaseProduct.product.prodId}" value='0' maxlength="2" style="width: 100px; text-align: center;font-weight: bold;color: #0080C5;" onchange="parseProductOrderValue()" onkeyup="parseProductOrderValue()"/>
                                                                </td>
                                                                <td style="padding-left: 20px;">
                                                                    <i class="fa fa-minus-square fa-2x" aria-hidden="true" style="color: #797979;cursor: pointer;" onclick="increaseDecreaseOrderAmount('amountOfOrder_${groupPurchaseProduct.product.prodId}', 'DOWN')"></i>
                                                                    <i class="fa fa-plus-square fa-2x" aria-hidden="true" style="color: #797979;cursor: pointer;" onclick="increaseDecreaseOrderAmount('amountOfOrder_${groupPurchaseProduct.product.prodId}', 'UP')"></i>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </c:otherwise>
                                                </c:choose>









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
                <div class="row" style="padding-top: 20px;" >
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
                            <div class="panel-body" style="padding-left: 20px;padding-bottom: 20px;padding-top: 20px;padding-right: 100px;" id="dilivery_service_row">

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
                                    <div style="text-align: right;font-size: 10px;color: #58A578;" align="right"> 배송비는 공.구 장소를 기준으로 Drive거리(Km)에 따라 계산됩니다.(Avoid toll road)  </div>
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
                                                <label style="color: #117899;">배송서비스를 이용하시겠습니까 ? &nbsp;&nbsp;&nbsp;<input type="checkbox" id="useDeliveryService_${groupPurchase.groupPurchaseId}" value="" style="transform: scale(1.5);" onchange="parseProductOrderValue()"></label>
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
                                    <input type="hidden" id="deliveryServiceFee" value="${deliveryFee}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:when>
        </c:choose>

        <!-- 구글지도 및 스트릿뷰 -->
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
    <!-- 페이지 중간 [끝] :: 왼쪽:아이템목록 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 오른쪽:주문내역 -->
    <div class="col-sm-3"style="padding-right: 20px;padding-left: 20px;">

        <div class="panel panel-success">

            <div class="panel-heading" style="background-color: #C8E297;">
                <table style="width: 100%;">
                    <tr>
                        <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-shopping-cart fa-3x" aria-hidden="true"></i></td>
                        <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">내 주문</span></td>
                    </tr>
                </table>
            </div>
            <div class="panel-body" style="padding-left: 100px;padding-right: 10px;padding-bottom: 30px;padding-top: 10px;">

                <table border="0" style="width: 100%;">
                    <colgroup>
                        <col style="width: 60%;">
                        <col style="width: 10%;">
                        <col style="width: 30%;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <td style="text-align: right;">상품구매</td>
                            <td style="text-align: center;"><i class="fa fa-plus" aria-hidden="true"></i></td>
                            <td style="color: #797979; text-align: right;font-size: 15px;"><span id="payment_totalAmountForProduct" style="font-size: 15px;">0.00</span> $</td>
                        </tr>
                        <tr style="height: 15px;">
                            <td colspan="3">&nbsp;</td>
                        </tr>
                        <tr style="height: 15px;">
                            <td style="text-align: right;">배송서비스</td>
                            <td style="text-align: center;"><i class="fa fa-plus" aria-hidden="true"></i></td>
                            <td style="color: #797979; text-align: right;font-size: 15px;"><span id="payment_deliveryFee" style="font-size: 15px;">0.00</span> $</td>
                        </tr>
                        <tr style="height: 30px;">
                            <td style="text-align: right;color: #58A578;">
                                할인
                                <c:choose>
                                    <c:when test="${groupPurchase.discountMethod != '' && groupPurchase.discountMethod != null}">
                                        <c:choose>
                                            <c:when test="${groupPurchase.discountMethod == 'FIXED' && groupPurchase.discountFixedAmount != '' && groupPurchase.discountFixedAmount != null}">
                                                ($ <fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.discountFixedAmount}" />)
                                            </c:when>
                                            <c:when test="${groupPurchase.discountMethod == 'RATE' && groupPurchase.discountRateValue != '' && groupPurchase.discountRateValue != null}">
                                                (<fmt:formatNumber type="number" pattern="###" value="${groupPurchase.discountRateValue * 100}" /> %)
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td style="text-align: center;"><i class="fa fa-arrow-down" aria-hidden="true" style="color: #58A578;"></i></td>
                            <td style="color: #58A578; text-align: right;font-size: 15px;">- <span id="payment_toBeDiscountAmount" style="font-size: 15px;color: #58A578;">0.00</span> $</td>
                        </tr>
                        <tr>
                            <td colspan="3" style="padding-left: 30%;">
                                <hr class="subtitle-gray"/>
                            </td>
                        </tr>
                        <tr style="height: 5px;"><td colspan="3">&nbsp;</td></tr>
                        <tr style="height: 15px;">
                            <td style="text-align: right;font-size: 20px;font-weight: bold;color: #797979;">총</td>
                            <td style="text-align: center;"></td>
                            <td style="color: #797979; text-align: right;font-size: 20px;font-weight: bold;color: #797979;">
                                <span id="payment_finalAmount" style="font-size: 20px;font-weight: bold;color: #797979;">0.00</span> $
                            </td>
                        </tr>
                        <tr style="height: 30px;padding-top: 50px;">
                            <td style="color: #C3C5C8; text-align: right;font-size: 13px;" colspan="3"><br/>결재방법</td>
                        </tr>
                        <tr style="height: 40px;">
                            <td style="color: #797979; text-align: right;font-size: 15px;" colspan="3"><c:out value="${cbxPaymentMethod}" escapeXml="false"/></td>
                        </tr>
                        <tr id="detail_info_back_account" style="height: 40px;display: none;">
                            <td colspan="3" style="padding-top: 0px;padding-bottom: 10px;">
                                <table width="100%;" style="font-size: 5px;">
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">은행명</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankName"></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">BSB - Account no</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankBsbBankAccountNo"></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Account Holder</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #8D9999;" id="payment_bankAccountOwnerName"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>


                        <tr style="height: 30px;padding-top: 50px;">
                            <td style="color: #C3C5C8; text-align: right;font-size: 13px;" colspan="3"><br/>주문메모</td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <div class="form-group">
                                    <textarea class="form-control" rows="3" id="comment" placeholder="주문시 메모하고 싶은 내용을 입력해주세요."></textarea>
                                </div>
                            </td>
                        </tr>


                        <tr style="height: 25px;">
                            <td colspan="3" style="background-color: #F15F4C;text-align: center;"><a href="#" style="color: #FFFFFF;font-weight: bold;font-size: 15px;">결재하기 > </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div style="padding: 10px;display: none;" id="payment_bankTransfer_message">
               <div class="alert alert-warning" style="border-left: 6px solid #F15F4C;padding-left: 5px;">계좌이체를 선택하신경우 결재후 계좌송금 <span style="text-decoration: underline;">영수증 이미지를 업로드</span>해 주시면 빠르게 처리해 드리겠습니다.</div>
            </div>
        </div>


    </div>
    <!-- 페이지 중간 [끝] :: 오른쪽:주문내역 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


</div>
<!-- 페이지 중간 [끝] :: :: 오른쪽:아이템목록 왼쪽:주문내역  -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

</body>
</html>