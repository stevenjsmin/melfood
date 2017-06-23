<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function validateFormStopSelling(){

            var count;
            var elementObj = "";
            var validation = true;

            // Initialize
            for(count=0; count < checkObject.length; count++ ){
                elementObj = "#" + checkObject[count];
                $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
            }
            checkObject = [];
            var prefix = "- &nbsp;&nbsp;";
            var message = "";

            var groupPurchaseId = ${product.groupPurchaseId};
            var productId = ${product.productId};
            var stopSelling = $('#stopSelling').val();
            var stopSellingReason = $('#stopSellingReason').val();


            if(groupPurchaseId == "") {
                message = message + prefix + "공동구매ID는 필수 항목입니다.<br>";
                checkObject[checkObject.length] = "groupPurchaseId";
                validation = false;
            }

            if(productId == "") {
                message = message + prefix + "상품ID는 필수 항목입니다.<br>";
                checkObject[checkObject.length] = "productId";
                validation = false;
            }

            if(stopSelling == "Y") {
                if(stopSellingReason == "" || stopSellingReason == null ) {
                    message = message + prefix + "판매 중지로 설정된 경우 설정된 이유를 입력하셔야합니다.<br>";
                    checkObject[checkObject.length] = "stopSellingReason";
                    validation = false;
                }
            }

            // 검증된 필드들을 마킹한다.
            for(count=0; count < checkObject.length; count++ ){
                elementObj = "#" + checkObject[count];
                $(elementObj).css({'background':'#fffacd','border-color':'#DF0000','border' : '1px solid #f00'});
            }
            if(validation == false){
                // 오류가 있는 경우 경고 창을 보여준다.
                warningPopup(message);
            }

            return validation;
        }

        function stopSellingUpdate(){
            var groupPurchaseId = ${product.groupPurchaseId};
            var productId = ${product.productId};
            var stopSelling = $('#stopSelling').val();
            var stopSellingReason = $('#stopSellingReason').val();

            if(validateFormStopSelling() == false) return;

            if(stopSelling == "N" || stopSelling == '' || stopSelling == null) {
                stopSelling = "N";
                stopSellingReason = "";
            } else {
                stopSelling = "Y";
            }


            $.ajax({
                url  : "/admin/grouppurchase/product/stopSellingUpdate.yum",
                data : {
                    groupPurchaseId : ${product.groupPurchaseId},
                    productId : ${product.productId},
                    stopSelling : stopSelling,
                    stopSellingReason : stopSellingReason
                },
                success : callbackStopSellingUpdate
            });
        }

        function callbackStopSellingUpdate(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                parent.refreshForProductList();
                parent.closeStopSelling();
            }
        }

    </script>
</head>
<body>

<span class="subtitle"> 상품 기본정보</span>
<hr class="subtitle"/>
<table>
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="200px" />
                    <col width="250px" />
                    <col width="200px" />
                    <col width="300px" />
                </colgroup>
                <tr>
                    <td class="label">상품명</td>
                    <td class="value">${product.productName}</td>
                    <td class="label">상품ID</td>
                    <td class="value">${product.productId}</td>
                </tr>
                <tr>
                    <td class="label">판매자</td>
                    <td class="value">${product.productOwner}</td>
                    <td class="label">단가</td>
                    <td class="value">$ ${product.unitPrice}</td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle"> 판매정지 설정</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td class="label">상태 :</td>
                    <td class="value"><c:out value="${cbxStopSelling}" escapeXml="false"/></td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td class="label">공동구매 정지이유 :</td>
                    <td class="value" colspan="2"><input class="form-control" type="text" id="stopSellingReason" name="stopSellingReason" value='${product.stopSellingReason}' placeholder="공동 구매가 정지된 이유"/></td>
                    <td></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Extra buttons -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<table class="action_button_table">
    <tr>
        <td>
            <button type="button" class="btn btn-default" onclick="parent.closeStopSelling();">Cancel</button>&nbsp;
            <button type="button" class="btn btn-primary" onclick="stopSellingUpdate();">Update</button>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var GROUP_PURCHASE_ID = "";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>