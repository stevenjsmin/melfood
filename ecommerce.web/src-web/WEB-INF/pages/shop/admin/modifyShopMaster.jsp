<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/admin/shopmaster.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#notice").kendoEditor({
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
                    <td class="label"><span class="required">* </span>Shop 이름 </td>
                    <td class="value" colspan="3"><input class="form-control" type="text" id="shopName" name="shopName" value='${shopMaster.shopName}' placeholder="가계 이름"/></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Shop 운영자 </td>
                    <td class="value"><c:out value="${cbxShopOwner}" escapeXml="false"/></td>
                    <td class="label"><span class="required">* </span>Shop Template </td>
                    <td class="value"><c:out value="${cbxTemplateId}" escapeXml="false"/></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>최소 참여금액 </td>
                    <td class="value"><input id="minimumPurchaseAmount" name="minimumPurchaseAmount" value="${shopMaster.minimumPurchaseAmount}"></input></td>
                    <td class="label">최대 참여금액 </td>
                    <td class="value"><input id="maximumPurchaseAmount" name="maximumPurchaseAmount" value="${shopMaster.maximumPurchaseAmount}"></input></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>할인비율 </td>
                    <td class="value"><input id="discountRateValue" name="discountRateValue" value="${shopMaster.discountRateValue}"></input></td>
                    <td class="label"><span class="required">* </span>Shop 크리딧 </td>
                    <td class="value"><input id="shopCredit" name="shopCredit" value="${shopMaster.shopCredit}"></input></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> Shop 주소</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>State</td>
                    <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Postcode</td>
                    <td class="value" style="padding-left: 3px;">
                        <table>
                            <tr>
                                <td><input class="form-control" type="text" id="addressPostcode" name="addressPostcode" value='${shopMaster.addressPostcode}' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="label"><span class="required">* </span>Suburb</td>
                    <td class="value">
                        <div id="cbx_addressSuburb"><input class="form-control" type="text" id="addressSuburb" name="addressSuburb" value='${shopMaster.addressSuburb}'/></div>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Street</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="addressStreet" name="addressStreet" value='${shopMaster.addressStreet}'/></td>
                    <td></td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle"> 배송서비스</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>배달가능여부 </td>
                    <td class="value"><c:out value="${cbxDeliveryService}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">기본배달 서비스비 </td>
                    <td class="value"><input id="deliveryBaseCharge" name="deliveryBaseCharge" value="${shopMaster.deliveryBaseCharge}"></input></td>
                    <td class="label">배달비 / Km </td>
                    <td class="value"><input id="deliveryFeePerKm" name="deliveryFeePerKm" value="${shopMaster.deliveryFeePerKm}"></input></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle" style="color: #b3b3b3;font-weight: normal;"> 배송비 Base주소 : 배송비 계산을 위한 기준 주소(입력하지 않으면 Shop주소가 사용됩니다.)</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">State</td>
                    <td class="value"><c:out value="${cbxForDeliverCalcAddressState}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">Postcode</td>
                    <td class="value" style="padding-left: 3px;">
                        <table>
                            <tr>
                                <td><input class="form-control" type="text" id="forDeliverCalcAddressPostcode" name="forDeliverCalcAddressPostcode" value='${shopMaster.forDeliverCalcAddressPostcode}' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('forDeliverCalcAddressPostcode', 'forDeliverCalcAddressSuburb')"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="label">Suburb</td>
                    <td class="value">
                        <div id="cbx_forDeliverCalcAddressSuburb"><input class="form-control" type="text" id="forDeliverCalcAddressSuburb" name="forDeliverCalcAddressSuburb" value='${shopMaster.forDeliverCalcAddressSuburb}'/></div>
                    </td>
                </tr>
                <tr>
                    <td class="label">Street</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="forDeliverCalcAddressStreet" name="forDeliverCalcAddressStreet" value='${shopMaster.forDeliverCalcAddressStreet}'/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">배송제한거리(Km)</td>
                    <td class="value"><input id="deliveryLimitKm" name="deliveryLimitKm" value="${shopMaster.deliveryLimitKm}"></input></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 샵 안내(Notice)</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="value" colspan="4"><textarea class="form-control" style="height:100px;" id="notice" name="notice">${shopMaster.notice}</textarea></td>
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
                        <a href="javascript:goDetailInfo('${shopMaster.shopId}');" class="btn btn-info">&nbsp;&nbsp; 취소 &nbsp;&nbsp;</a>
                        <a href="javascript:save();" class="btn btn-primary">저장</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>

<script type="text/javascript">
    var SHOP_ID = "${shopMaster.shopId}";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>