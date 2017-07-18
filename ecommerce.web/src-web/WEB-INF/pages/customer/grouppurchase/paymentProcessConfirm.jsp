<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<!doctype html>
<head>
    <script src="/resources/js/melfood/framework/grouppurchasepayment.js?ver=<%=Ctx.releaseVersion%>"></script>

    <style>
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
        }

        .venueInfoWrapper {
            background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url(/resources/image/generic_backdrop.png);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 210px;
        }

        .k-upload {
            height: 30px;
        }
        .k-dropzone {
            padding-top: 3px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#fileUpload").kendoUpload({
                async: {
                    saveUrl: "/customer/mypage/myorder/acctransferreceiptUpload.yum?thanks=${orderMaster.orderMasterId}",
                    removeUrl: "/customer/mypage/myorder/acctransferreceiptRemove.yum?thanks=${orderMaster.orderMasterId}",
                    removeField: "removeFile",
                    autoUpload: true,
                    batch: true,
                },
                showFileList: false,
                localization: {
                    statusFailed: "Failed to file upload. Please check it again and retry.",
                    uploadSelectedFiles: "Upload file(s)"
                },
                multiple: false,
                complete: onComplete,
                error: onError,
                success: onSuccess
            });

            function onComplete(e) {
                //search();
            }

            function onSuccess(e) {
                var data = e.response;

                if (data.resultCode != '0') {
                    var htmlMessage = "<b>영수증파일 첨부 갱신 실패 : </b>" + data.message + "<br/> *** 나중에 '<b>My푸드 > 구매기록 조회</b>' 에서 첨부하셔도 됩니다.";
                    warningPopup(htmlMessage);
                    $("#paymentAccTransferReceiptMessage").html(htmlMessage);
                    $("#paymentAccTransferReceipt").html("");

                } else {
                    $("#paymentAccTransferReceiptMessage").html("");

                    if(data.receiptFileNo != undefined && data.receiptFileNo != '' ){
                        infoPopup("정상적으로 영수증 파일이 등록되었습니다.");
                        var htmlMessage = "첨부해주신 영수증 파일이 있습니다 : " +  data.receiptFileName
                            + " &nbsp;&nbsp; "
                            + "<a href=\"javascript:downloadFile('" + data.receiptFileNo + "');\"><img src=\"\/resources\/css/images\/gic\/ic_file_download_black_18dp_1x.png\"/>";
                        $("#paymentAccTransferReceipt").html(htmlMessage);
                    }
                }
            }

            function onError(e) {
                var files = e.files;
                if (e.operation == "upload") {
                    warningPopup("Failed to upload : " + files[0].name);
                }
            }

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function groupPurchaseOrganizer() {

            $("#purchaseOrganizerPopup").kendoWindow({
                content: "/grouppurchase/purchaseOrganizerInfo.yum?purchaseOrganizer=" + "${groupPurchase.purchaseOrganizer}",
                actions: ["Minimize", "Maximize", "Close"],
                title: "내가잘사는 방법:: MelFood",
                modal: true,
                iframe: true,
                position: {top: "200", left: "25%"}
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

    </script>

    <script type="text/javascript">
        function askQuestion(receiverUserId) {

            $("#askQuestionPopup").kendoWindow({
                content: "/framework/communicationmanager/sendMessageFormWIthHistory.yum?receiverUserId=" + receiverUserId,
                actions: ["Minimize", "Maximize", "Close"],
                title: "SEND MESSAGE",
                modal: true,
                iframe: true
            });

            var popup_dialog = $("#askQuestionPopup").data("kendoWindow");
            popup_dialog.setOptions({
                pinned: true,
                width: 650, height: 500,
                open: function (e) {
                    this.wrapper.css({top: 100});
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
        function goMainPage() {

            progress(true);


            var whatday = $("input[name='survey_for_next']:checked").val();
            var ampm = $("input[name='survey_for_next_ampm']:checked").val();


            $.ajax({
                url: "/survey/preferNextGroupPurchaseDay.yum",
                data: {
                    whatday    : whatday,
                    ampm    : ampm
                },
                success: callbackGoMainPage
            });
        }
        function callbackGoMainPage(data) {
            var message = data.message;
            var resultCode = data.resultCode;
            var thanks = data.thanks;

            progress(false);

            if (resultCode != "0") {
                document.location.href = "/";
            } else {
                document.location.href = "/";
            }
        }
    </script>
</head>

<body>
<div id="askQuestionPopup"></div>
<div id="purchaseOrganizerPopup"></div>

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
                        <table align="center" style="color: #FFFFFF;">
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
                                                    <tr style="height: 20px;">
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
                                                                    <tr style="height: 20px;">
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-usd" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.discountFixedAmount}"/> Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:when test="${groupPurchase.discountMethod == 'RATE' && groupPurchase.discountRateValue != '' && groupPurchase.discountRateValue != null}">
                                                                    <tr style="height: 20px;">
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-percent" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###" value="${groupPurchase.discountRateValue * 100}"/> (Percent) Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:when>
                                                    </c:choose>

                                                    <c:choose>
                                                        <c:when test="${groupPurchase.deliverable == 'Y'}">
                                                            <tr style="height: 20px;">
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
                                                    <tr style="height: 25px;">
                                                        <td style="width: 20px;text-align: center;"><i class="fa fa-address-card-o" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                                        <td style="width: 10px;"></td>
                                                        <td><a href="javascript:groupPurchaseOrganizer()" style="color: #69B7F5;">공동구매 진행자정보</a><br/></td>
                                                    </tr>
                                                    <tr style="height: 25px;">
                                                        <td style="width: 20px;text-align: center;"><i class="fa fa-comment" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                                        <td style="width: 10px;"></td>
                                                        <td><a href="javascript:askQuestion('${groupPurchase.purchaseOrganizer}')" style="color: #69B7F5;"> ?!! 물어보세요</a><br/></td>
                                                    </tr>
                                                </table>


                                            </td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;">
                                                <div class="alert alert-danger" role="alert" style="padding: 5px 20px 5px 20px;background-color: transparent;border: solid 4px #F2DEDE;">
                                                     <span style="color: #F2DEDE;">최소주문 금액 : $ <fmt:formatNumber type="number" pattern="##0.00" value="${groupPurchase.minimumPurchaseAmount}" />
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
                    <div class="panel-heading" style="height: 50px;">
                        <table style="width: 100%;height: 100%;">
                            <tr>
                                <td style="padding-left: 20px;text-align: left;font-size: 15px;font-weight: bold;color: #333333;"><span style="color: #F15F4C;font-size: 15px;font-weight: bold;">주문완료</span> :: 주문해 주셔서 감사합니다.</td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 20px;">
                        <p>${groupPurchase.groupPurchaseNotice}</p>
                    </div>

                    <div class="row">
                        <div class="col-sm-12" style="padding-left: 40px; ">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th style="color: #575758;">주문 상품</th>
                                    <th style="color: #575758;">옵션</th>
                                    <th style="width: 100px;text-align: right;color: #575758;">단가</th>
                                    <th style="width: 130px;text-align: right;color: #575758;">수량</th>
                                    <th style="width: 130px;text-align: right;color: #575758;">소계</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="orderMasterProduct" items="${orderMaster.orderMasterProduct}" varStatus="count" begin="0">
                                    <tr>
                                        <td style="color: #002F00;">${orderMasterProduct.productName}</td>
                                        <td style="color: #797979;">
                                            <c:forEach var="productOption" items="${orderMasterProduct.orderMasterProductOptionList}" varStatus="count1" begin="0">
                                                <span style="color: #b3b3b3;">${productOption.optionName}</span> : ${productOption.optionValue} &nbsp;&nbsp;&nbsp;
                                            </c:forEach>
                                        </td>
                                        <td style="color: #797979;text-align: right;">${orderMasterProduct.unitPrice} $</td>
                                        <td style="color: #797979;text-align: right;">${orderMasterProduct.numberOfOrder}</td>
                                        <td style="color: #797979;text-align: right;">${orderMasterProduct.numberOfOrder * orderMasterProduct.unitPrice} $</td>
                                    </tr>
                                </c:forEach>

                                <tr>
                                    <td style="color: #575758; text-align: right;font-weight: bold;" colspan="4">상품주문 합</td>
                                    <td style="color: #575758;text-align: right;font-weight: bold;">${orderMaster.amountTotalProduct} $</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${groupPurchase.deliverable == 'Y'}">
                            <div class="row">
                                <div class="col-sm-12" style="padding-left: 40px; padding-top: 20px; ">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th style="color: #575758;">배송서비스 - 거리(Km)</th>
                                            <th style="width: 170px;text-align: right;color: #575758;">기본서비스 Charge</th>
                                            <th style="width: 120px;text-align: right;color: #575758;">요금/Km</th>
                                            <th style="width: 100px;text-align: right;color: #575758;">소계</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:choose>
                                            <c:when test="${orderMaster.isPickupOrDelivery == 'D'}">
                                                <tr>
                                                    <td style="color: #002F00;">${orderMaster.deliveryDistance} KM <span style="font-size: 10px;color: #b3b3b3;">(공.구 장소로부터의 거리입니다 : Avoid toll drive way)</span></td>
                                                    <td style="color: #797979;text-align: right;">${orderMaster.deliveryBaseCharge} $</td>
                                                    <td style="color: #797979;text-align: right;">${orderMaster.deliveryFeePerKm} $</td>
                                                    <td style="color: #797979;text-align: right;">${orderMaster.amountTotalDelivery} $</td>
                                                </tr>
                                            </c:when>
                                            <c:when test="${orderMaster.isPickupOrDelivery == 'P'}">
                                                <tr>
                                                    <td style="color: #8B8A8A;text-align: right;" colspan="4">배송서비스 이용 않함</td>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>


                    <div class="row">
                        <div class="col-sm-12" style="padding-left: 40px; padding-top: 20px; ">
                            <table class="table table-striped" border="0">
                                <thead>
                                <tr>
                                    <th style="text-align: right;color: #575758;">할인 (<i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i><i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i>)</th>
                                    <th style="width: 130px;text-align: right;color: #575758;">할인금액</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td style="color: #797979;text-align: right;"> -
                                        <c:choose>
                                            <c:when test="${groupPurchase.discountMethod != '' && groupPurchase.discountMethod != null}">
                                                <c:choose>
                                                    <c:when test="${groupPurchase.discountMethod == 'FIXED' && groupPurchase.discountFixedAmount != '' && groupPurchase.discountFixedAmount != null}">
                                                            <fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.discountFixedAmount}" /> $
                                                    </c:when>
                                                    <c:when test="${groupPurchase.discountMethod == 'RATE' && groupPurchase.discountRateValue != '' && groupPurchase.discountRateValue != null}">
                                                        <fmt:formatNumber type="number" pattern="###" value="${groupPurchase.discountRateValue * 100}" /> %
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td style="color: #797979;text-align: right;width: 90px;"> - ${orderMaster.amountTotalDiscount} $</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-sm-12" style="padding-left: 40px; padding-top: 20px;padding-bottom: 40px;">
                            <table class="table table-striped">
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td style="color: #737273;text-align: right;font-weight: bold;font-size: 15px;">Total</td>
                                    <td style="color: #737273;text-align: right;font-weight: bold;font-size: 15px;width: 100px;text-decoration: underline;">${orderMaster.amountTotal} $</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>




                </div>
            </div>
        </div>
    </div>
    <!-- 페이지 중간 [끝] :: 왼쪽:아이템목록 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 오른쪽:주문내역 -->
    <div class="col-sm-3" style="padding-right: 20px;padding-left: 20px;">

        <div class="panel panel-success">

            <div class="panel-heading" style="background-color: #C8E297;">
                <table style="width: 100%;">
                    <tr>
                        <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-bell fa-3x" aria-hidden="true" style="color: #F15F4C;"></i></td>
                        <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;color: #F15F4C;">고맙습니다.</span></td>
                    </tr>
                </table>
            </div>
            <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 20px;padding-top: 20px;">

                <c:choose>
                    <c:when test="${orderMaster.paymentMethod == '계좌이체'}">
                            <!-- 계좌이체 결재인경우 수금받을 계좌정보 -->
                            <div id="detailinfo_for_acc_transfer" style="padding-bottom: 20px;">
                                <table width="100%;" style="font-size: 5px;">
                                    <tr>
                                        <td colspan="3" style="text-align: right;">
                                            <span class="subtitle" style="color: #737273;text-align: right;">송금하실 계좌정보</span>
                                            <hr class="subtitle"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">은행명</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankName">${orderMaster.paymentBankName}</td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">BSB - Account no</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankBsbBankAccountNo">${orderMaster.paymentBankBsb} - ${orderMaster.paymentAccountNo}8</td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Account Holder</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #8D9999;" id="payment_bankAccountOwnerName">${orderMaster.paymentAccountHolderName}</td>
                                    </tr>
                                </table>
                            </div>

                            <!-- 계좌이체 결재인경우 영수증 첨부 안내 -->
                            <div id="notice_for_acc_transfer">
                                <div class="alert alert-warning" style="border-left: 6px solid #F15F4C;padding-left: 5px;">송금하신 후 <span style="text-decoration: underline;">영수증 이미지를 업로드</span>해 주시면 빠르게 처리해 드리겠습니다.</div>
                            </div>

                            <!-- 영수증 업로드 -->
                            <div id="acc_transfer_receipt_upload" style="margin-bottom: 10px;">
                                <table style="width: 100%;">
                                    <tr>
                                        <td>
                                            <table class="detail_table">
                                                <tr>
                                                    <td class="label">계좌이체 영수증 첨부하기</td>
                                                    <td class="value" style="padding-right: 0px;"><input type="file" name="files" id="fileUpload"/></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="color: #8B8A8A;padding-top: 5px;padding-left: 20px; text-align: right;">
                                            <div id="paymentAccTransferReceipt" style="font-size: 10px;">
                                                <c:choose>
                                                    <c:when test="${orderMaster.paymentAccTransferReceipt != null && receiptFileNo != null}">
                                                        첨부해주신 영수증 파일이 있습니다 : ${receiptFileName} <a href="javascript:downloadFile('${receiptFileNo}');"><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"/></a>
                                                    </c:when>
                                                    <c:otherwise> *** 나중에 '<b>My푸드 > 구매기록 조회</b>' 에서 첨부하셔도 됩니다.</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div id="paymentAccTransferReceiptMessage"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                    </c:when>
                    <c:when test="${orderMaster.paymentMethod == '만나서결재'}">
                        <div style="padding-bottom: 20px;">
                            <table width="100%;" style="font-size: 5px;">
                                <tr>
                                    <td style="text-align: right;">
                                        <span class="subtitle" style="color: #737273;text-align: right;">만나서결재</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                </tr>
                                <tr>
                                    <td style="padding-top: 10px;">
                                        <div>
                                            <div class="alert alert-warning" style="border-left: 6px solid #F15F4C;padding-left: 5px;">만나서 결재하실때 미리 액수에 맞춰서 잔돈을 준비해주시면 감사하겠습니다.</div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </c:when>
                    <c:when test="${orderMaster.paymentMethod == '신용카드결재'}">
                    </c:when>
                    <c:when test="${orderMaster.paymentMethod == 'PAYPAL'}">
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>



                <!-- 설문조사 -->
                <div style="margin-bottom: 10px;padding-top: 40px;">
                    <table width="100%;" style="font-size: 5px;" border="0">
                        <tr>
                            <td colspan="6" style="text-align: right;">
                                <span class="subtitle" style="color: #737273;text-align: right;">선호하시는 공동구매 요일을 선택해 주실래요 ? ^^</span>
                                <hr class="subtitle"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;">월 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="월"> </td>

                            <td style="text-align: right;font-size: 13px;color: #A6D364;width: 70px;">토 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="토"> </td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;">화 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="화"> </td>

                            <td style="text-align: right;font-size: 13px;color: #A6D364;">일 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="일"> </td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;">수 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="수"> </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;">목 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="목"> </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;">금 요일</td>
                            <td style="width: 20px;text-align: center">:</td>
                            <td style="width: 20px;color: #514747;vertical-align: middle;"><input type="radio" name="survey_for_next" value="금"> </td>
                            <td colspan="3"></td>
                        </tr>
                        <tr>
                            <td style="text-align: right;font-size: 13px;color: #333333;padding-right: 7px;" colspan="6">
                                오전 <input type="radio" name="survey_for_next_ampm" value="오전">&nbsp;&nbsp;
                                오후 <input type="radio" name="survey_for_next_ampm" value="오후">
                            </td>
                        </tr>

                    </table>
                </div>


                <div style="margin-bottom: 10px;padding-top: 30px;">
                    <table style="width: 100%;">
                        <tr>
                            <td style="text-align: right;">
                                <button type="button" class="btn btn-default" style="color: #FFFFFF;background-color: #F15F4C;" onclick="goMainPage();"><i class="fa fa-home" aria-hidden="true"></i> 메인페이지로 가기</button>
                            </td>
                        </tr>
                    </table>
                </div>

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