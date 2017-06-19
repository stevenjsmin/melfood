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

            $('#spanDiscountRateValue').show();
            $('#spanDiscountFixedAmount').hide();
            $('#discountFixedAmount').val('0');

            $("#groupPurchaseNotice").kendoEditor({
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
                    <td class="value" colspan="3"><input class="form-control" type="text" id="groupPurchaseTitle" name="groupPurchaseTitle" value='' placeholder="공동구매 제목"/></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Subtitle :</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="groupPurchaseSubtitle" name="groupPurchaseSubtitle" value='' placeholder="부제목"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>공동구매 주관자 :</td>
                    <td class="value"><c:out value="${cbxPurchaseOrganizer}" escapeXml="false"/></td>
                    <td class="label">최소 참여금액 :</td>
                    <td class="value"><input id="minimumPurchaseAmount" name="minimumPurchaseAmount" value="0.00"></input></td>
                </tr>
                <tr>
                    <td class="label">할인비율/금액 :</td>
                    <td class="value"><c:out value="${cbxDiscountMethod}" escapeXml="false"/></td>
                    <td class="label">비율/금액 :</td>
                    <td class="value">
                        <span id="spanDiscountRateValue" style="display: none;"><input id="discountRateValue" name="discountRateValue" value="0.00"></input></span>
                        <span id="spanDiscountFixedAmount" style="display: none;"><input id="discountFixedAmount" name="discountFixedAmount" value="0.00"></input></span>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 공동구매 상태정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">공동구매 시작일시 :</td>
                    <td class="value"><input id="orderingStartDt" name="orderingStartDt" value=""></input></td>
                    <td class="label">공동구매 종료일시 :</td>
                    <td class="value"><input id="orderingEndDt" name="orderingEndDt" value=""></input></td>
                </tr>
                <tr>
                    <td class="label">상태 :</td>
                    <td class="value"><c:out value="${cbxStopSelling}" escapeXml="false"/></td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td class="label">공동구매 정지이유 :</td>
                    <td class="value_end" colspan="2"><input class="form-control" type="text" id="stopSellingReason" name="stopSellingReason" value='' placeholder="공동 구매가 정지된 이유"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 공동구매 장소/주소</span>
                        <hr class="subtitle"/>
                    </td>
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
                                <td><input class="form-control" type="text" id="marketAddressPostcode" name="marketAddressPostcode" value='' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('marketAddressPostcode', 'marketAddressSuburb')"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="label">Suburb</td>
                    <td class="value">
                        <div id="cbx_marketAddressSuburb"><input class="form-control" type="text" id="marketAddressSuburb" name="marketAddressSuburb" value=''/></div>
                    </td>
                </tr>
                <tr>
                    <td class="label">Street</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="marketAddressStreet" name="marketAddressStreet" value=''/></td>
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
                    <td class="value" colspan="4"><textarea class="form-control" style="height:100px;" id="groupPurchaseNotice" name="groupPurchaseNotice"></textarea></td>
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
                        <a href="javascript:parent.closeGroupPurchaseRegistPopup();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                        <a href="javascript:save();" class="btn btn-primary">Save</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>

<script type="text/javascript">
    var ACTION_MODE = "ADD";
</script>
</body>
</html>