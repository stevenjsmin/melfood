<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            if('${groupPurchase.discountMethod}' == 'RATE') {
                $('#spanDiscountRateValue').show();
                $('#spanDiscountFixedAmount').hide();
            } else {
                $('#spanDiscountRateValue').hide();
                $('#spanDiscountFixedAmount').show()
            }

            $("#groupPurchaseNotice").kendoEditor({
                tools :["bold","italic","underline","justifyLeft","justifyCenter","justifyRight","insertUnorderedList","insertOrderedList","createLink","unlink","insertImage","createTable","formatting","fontSize","foreColor"],
                messages: {fontSizeInherit: "Default"},
                encoded: false
            });

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <style>
        /** To force height of kendoEditor */
        .k-content{  height:100px !important; }
    </style>
</head>


<body>
<table>
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="250px"/>
                    <col width="250px"/>
                    <col width="250px"/>
                    <col width="250px"/>
                </colgroup>

                <tr>
                    <td colspan="4">
                        <span class="subtitle"> 기본정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Title :</td>
                    <td class="value" colspan="3"><input class="form-control" type="text" id="groupPurchaseTitle" name="groupPurchaseTitle" value='${groupPurchase.groupPurchaseTitle}' placeholder="공동구매 제목"/></td>
                </tr>
                <tr>
                    <td class="label">Subtitle :</td>
                    <td class="value"><input class="form-control" type="text" id="groupPurchaseSubtitle" name="groupPurchaseSubtitle" value='${groupPurchase.groupPurchaseSubtitle}' placeholder="부제목"/></td>
                    <td class="label"><span class="required">* </span>공동구매 주관자 :</td>
                    <td class="value"><c:out value="${cbxPurchaseOrganizer}" escapeXml="false"/></td>
                </tr>
                <tr>
                    <td class="label">최소 참여금액 :</td>
                    <td class="value"><input id="minimumPurchaseAmount" name="minimumPurchaseAmount" value="${groupPurchase.minimumPurchaseAmount}"></input></td>
                    <td class="label">최대 참여금액 :</td>
                    <td class="value"><input id="maximumPurchaseAmount" name="maximumPurchaseAmount" value="${groupPurchase.maximumPurchaseAmount}"></input></td>
                </tr>
                <tr>
                    <td class="label">할인비율/금액 :</td>
                    <td class="value"><c:out value="${cbxDiscountMethod}" escapeXml="false"/></td>
                    <td class="label">비율/금액 :</td>
                    <td class="value">
                        <span id="spanDiscountRateValue"><input id="discountRateValue" name="discountRateValue" value="${groupPurchase.discountRateValue}"></input></span>
                        <span id="spanDiscountFixedAmount"><input id="discountFixedAmount" name="discountFixedAmount" value="${groupPurchase.discountFixedAmount}"></input></span>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 배송서비스</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">배달가능여부 :</td>
                    <td class="value"><c:out value="${cbxDeliverable}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">기본배달 서비스비 :</td>
                    <td class="value"><input id="deliveryBasicFee" name="deliveryBasicFee" value="${groupPurchase.deliveryBasicFee}"></input></td>
                    <td class="label">배달비 / Km :</td>
                    <td class="value"><input id="deliveryFeePerKm" name="deliveryFeePerKm" value="${groupPurchase.deliveryFeePerKm}"></input></td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 공동구매 상태정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>주문 시작일시 :</td>
                    <td class="value"><input id="orderStartDt" name="orderStartDt" value="${groupPurchase.orderStartDt}"></input></td>
                    <td class="label"><span class="required">* </span>주문 종료일시 :</td>
                    <td class="value"><input id="orderEndDt" name="orderEndDt" value="${groupPurchase.orderEndDt}"></input></td>
                </tr>
                <tr>
                    <td class="label">상태 :</td>
                    <td class="value"><c:out value="${cbxStopSelling}" escapeXml="false"/></td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td class="label">공동구매 정지이유 :</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="stopSellingReason" name="stopSellingReason" value='${groupPurchase.stopSellingReason}' placeholder="공동 구매가 정지된 이유"/></td>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 공동구매 장소.주소/시간</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td class="label"><span class="required">* </span>마켓오픈 시작일시 :</td>
                    <td class="value"><input id="marketOpenStartDt" name="marketOpenStartDt" value="${groupPurchase.marketOpenStartDt}"></input></td>
                    <td class="label"><span class="required">* </span>마켓오픈 시작일시 :</td>
                    <td class="value"><input id="marketOpenEndDt" name="marketOpenEndDt" value="${groupPurchase.marketOpenEndDt}"></input></td>
                </tr>
                <tr>
                    <td class="label">State</td>
                    <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">Postcode</td>
                    <td class="value" style="padding-left: 3px;">
                        <table>
                            <tr>
                                <td><input class="form-control" type="text" id="marketAddressPostcode" name="marketAddressPostcode" value='${groupPurchase.marketAddressPostcode}' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('marketAddressPostcode', 'marketAddressSuburb')"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="label">Suburb</td>
                    <td class="value">
                        <div id="cbx_marketAddressSuburb"><input class="form-control" type="text" id="marketAddressSuburb" name="marketAddressSuburb" value='${groupPurchase.marketAddressSuburb}'/></div>
                    </td>
                </tr>
                <tr>
                    <td class="label">Street</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="marketAddressStreet" name="marketAddressStreet" value='${groupPurchase.marketAddressStreet}'/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">비고</td>
                    <td class="value" colspan="3"><input class="form-control" type="text" id="marketAddressComment" name="marketAddressComment" value='${groupPurchase.marketAddressComment}'/></td>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 기타공지 메모</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="value" colspan="4"><textarea class="form-control" style="height:100px;" id="groupPurchaseNotice" name="groupPurchaseNotice">${groupPurchase.groupPurchaseNotice}</textarea></td>
                </tr>
                <tr style="height: 10px;"><td colspan="4"></td></tr>


            </table>
        </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table class="action_button_table" width="100%">
                <tr>
                    <td>
                        <a href="javascript:parent.closeGroupPurchasePopupWithoutRefresh();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                        <a href="javascript:save();" class="btn btn-primary">Modify</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>

<script type="text/javascript">
    var GROUP_PURCHASE_ID = "${groupPurchase.groupPurchaseId}";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>