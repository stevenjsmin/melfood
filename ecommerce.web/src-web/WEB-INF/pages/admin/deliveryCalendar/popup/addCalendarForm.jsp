<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#yyyyMmDd").kendoDatePicker({
                format: "yyyy-MM-dd",
                start: "year"
            });
            var datepicker1 = $("#yyyyMmDd").data("kendoDatePicker");
            $("#yyyyMmDd").click(function () {
                datepicker1.open();
            });

            $("#deliveryLimitKm").kendoNumericTextBox({
                format: "0", decimals: 0, min: 0, max: 2000
            });

            $("#deliveryFeePerKm").kendoNumericTextBox({
                max: 99999,
                min: 0.00,
                step: 1.00,
                format: "c2"
            });

            $("#deliveryBasicFee").kendoNumericTextBox({
                max: 99999,
                min: 0.00,
                step: 1.00,
                format: "c2"
            });


        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>


    <script type="text/javascript">

        function addDeliveryCalendarFromScreen() {

            if (validateForm() == false) return;

            var sellerId = $('#sellerId').val();
            var yyyyMmDd = $('#yyyyMmDd').val();
            var amPm = $('#amPm').val();

            var deliveryBaseAddressPostcode = $('#deliveryBaseAddressPostcode').val();
            var deliveryBaseAddressState = $('#deliveryBaseAddressState').val();
            var deliveryBaseAddressSuburb = $('#deliveryBaseAddressSuburb').val();
            var deliveryBaseAddressStreet = $('#deliveryBaseAddressStreet').val();
            var deliveryBaseAddressNote = $('#deliveryBaseAddressNote').val();

            var deliveryFeePerKm = $('#deliveryFeePerKm').val();
            var deliveryBasicFee = $('#deliveryBasicFee').val();
            var deliveryLimitKm = $('#deliveryLimitKm').val();


            progressWithId(true, "addCalendarForm");
            $.ajax({
                url: "/admin/deliverycalendarmgt/addCalendar.yum",
                data: {
                    sellerId: sellerId,
                    yyyyMmDd: yyyyMmDd,
                    amPm: amPm,
                    deliveryBaseAddressPostcode: deliveryBaseAddressPostcode,
                    deliveryBaseAddressState: deliveryBaseAddressState,
                    deliveryBaseAddressSuburb: deliveryBaseAddressSuburb,
                    deliveryBaseAddressStreet: deliveryBaseAddressStreet,
                    deliveryBaseAddressNote: deliveryBaseAddressNote,
                    deliveryFeePerKm: deliveryFeePerKm,
                    deliveryBasicFee: deliveryBasicFee,
                    deliveryLimitKm: deliveryLimitKm
                },
                success: callbackAddDeliveryCalendar
            });

        }

        function callbackAddDeliveryCalendar(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            progressWithId(false, "addCalendarForm");
            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                parent.search();
            }
        }
    </script>

    <script type="text/javascript">
        var checkObject = [];

        function validateForm() {
            var count;
            var elementObj = "";
            var validation = true;

            // 값검증 결과 초기화
            for (count = 0; count < checkObject.length; count++) {
                elementObj = "#" + checkObject[count];
                $(elementObj).css({'background': '#ECF5FF', 'border-color': '#DFDFDF'});
            }
            checkObject = [];
            var prefix = "- &nbsp;&nbsp;";
            var message = "";

            var sellerId = $('#sellerId').val();
            var yyyyMmDd = $('#yyyyMmDd').val();

            var deliveryBaseAddressPostcode = $('#deliveryBaseAddressPostcode').val();
            var deliveryBaseAddressState = $('#deliveryBaseAddressState').val();
            var deliveryBaseAddressSuburb = $('#deliveryBaseAddressSuburb').val();
            var deliveryBaseAddressStreet = $('#deliveryBaseAddressStreet').val();
            var deliveryBaseAddressNote = $('#deliveryBaseAddressNote').val();

            if (sellerId == "") {
                message = message + prefix + "Selller ID 값은 필수값입니다.<br>";
                checkObject[checkObject.length] = "sellerId";
                validation = false;
            }
            if (yyyyMmDd == "") {
                message = message + prefix + "배송일 지정은 필수입니다.<br>";
                checkObject[checkObject.length] = "yyyyMmDd";
                validation = false;
            } else {
                var parsedDate = kendo.parseDate(yyyyMmDd, "yyyy-mm-dd");
                if (!parsedDate) {
                    message = message + prefix + "올바른 배송일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
                    checkObject[checkObject.length] = "yyyyMmDd";
                    validation = false;
                }
            }

            if (deliveryBaseAddressNote == "") {
                message = message + prefix + "배송지역 이름을 입력해세요.<br>";
                checkObject[checkObject.length] = "deliveryBaseAddressNote";
                validation = false;
            }
            if (deliveryBaseAddressState == "") {
                message = message + prefix + "배송지역의 State를 올바르게 지정해주세요.<br>";
                checkObject[checkObject.length] = "deliveryBaseAddressState";
                validation = false;
            }
            if (deliveryBaseAddressPostcode == "") {
                message = message + prefix + "배송지역의 Postcode를 올바르게 지정해주세요.<br>";
                checkObject[checkObject.length] = "deliveryBaseAddressPostcode";
                validation = false;
            }
            if (deliveryBaseAddressSuburb == "") {
                message = message + prefix + "배송지역의 Suburb를 올바르게 지정해주세요.<br>";
                checkObject[checkObject.length] = "deliveryBaseAddressSuburb";
                validation = false;
            }
            if (deliveryBaseAddressStreet == "") {
                message = message + prefix + "배송지역의 Street를 올바르게 지정해주세요.<br>";
                checkObject[checkObject.length] = "deliveryBaseAddressStreet";
                validation = false;
            }


            // 검증된 필드들을 마킹한다.
            for (count = 0; count < checkObject.length; count++) {
                elementObj = "#" + checkObject[count];
                $(elementObj).css({'background': '#fffacd', 'border-color': '#DF0000', 'border': '1px solid #f00'});
            }
            if (validation == false) {
                // 오류가 있는 경우 경고 창을 보여준다.
                warningPopup(message);
            }
            return validation;
        }

    </script>


</head>

<body>
<table id="addCalendarForm">
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="150px"/>
                    <col width="200px"/>
                    <col width="100px"/>
                    <col width="200px"/>
                </colgroup>
                <tr>
                    <td class="label"><span class="required">* </span>Seller</td>
                    <td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
                    <td class="value"></td>
                    <td class="value"></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Date</td>
                    <td class="value"><input id="yyyyMmDd" name="yyyyMmDd" value=""/></td>
                    <td class="label">AM/PM</td>
                    <td class="value"><c:out value="${cbxAmPm}" escapeXml="false"/></td>
                </tr>
                <tr style="height: 20px;">
                    <td colspan="4"></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <span class="subtitle"> 배송할 지역주소</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td class="label"><span class="required">* </span>배송지역이름</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="deliveryBaseAddressNote" name="deliveryBaseAddressNote" value=''/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>State</td>
                    <td class="value"><c:out value="${cbxDeliveryBaseAddressState}" escapeXml="false"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Postcode</td>
                    <td class="value" style="padding-left: 3px;">
                        <table>
                            <tr>
                                <td><input class="form-control" type="text" id="deliveryBaseAddressPostcode" name="deliveryBaseAddressPostcode" value='' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;"
                                         onclick="setSuburbCbx('deliveryBaseAddressPostcode', 'deliveryBaseAddressSuburb')">
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="label"><span class="required">* </span>Suburb</td>
                    <td class="value">
                        <div id="cbx_deliveryBaseAddressSuburb">
                            <input class="form-control" type="text" id="deliveryBaseAddressSuburb" name="deliveryBaseAddressSuburb" value=''/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>Street</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="deliveryBaseAddressStreet" name="deliveryBaseAddressStreet" value=''/></td>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="4" style="height: 20px;">&nbsp;</td>
                </tr>

                <tr>
                    <td class="label">기본배달 서비스비 </td>
                    <td class="value"><input id="deliveryBasicFee" name="deliveryBasicFee" value="0"></input></td>
                    <td class="label">배달비 / Km </td>
                    <td class="value"><input id="deliveryFeePerKm" name="deliveryFeePerKm" value="0"></input></td>
                </tr>
                <tr>
                    <td class="label">배송제한거리(Km)</td>
                    <td class="value"><input id="deliveryLimitKm" name="deliveryLimitKm" value="0"></input></td>
                    <td></td>
                    <td></td>
                </tr>


                <tr>
                    <td colspan="4" style="height: 20px;">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: right;">
                        <button type="button" class="btn btn-default btn-sm" onclick="parent.closeOptionWindow();">Close</button>
                        &nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" onclick="addDeliveryCalendarFromScreen();">Add</button>
                        &nbsp;&nbsp;
                    </td>
                </tr>

                <tr>
                    <td colspan="4" style="height: 20px;">&nbsp;</td>
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