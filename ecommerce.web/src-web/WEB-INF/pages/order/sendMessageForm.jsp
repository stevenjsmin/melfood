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
        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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


            var contents = $('#contents').val();

            if (contents == "") {
                message = message + prefix + "Message 입력은 필수입니다.<br>";
                checkObject[checkObject.length] = "contents";
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


        function sendMessage() {
            var contents = $('#contents').val();
            var sendEmail = $('#sendEmail').prop('checked');
            var sendSMS = $('#sendSMS').prop('checked');

            if (validateForm() == false) return;

            progress(true);
            $.ajax({
                url: "/framework/communicationmanager/sendMessage.yum",
                data: {
                    contents: contents,
                    sendEmail: sendEmail,
                    sendSMS: sendSMS,
                    receiverUserId : '${receiverUser.userId}'
                },
                success: callbackSave
            });
        }

        function callbackSave(data) {

            var message = data.message;
            var resultCode = data.resultCode;

            progress(false);
            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                // infoPopup(data.message);
                BootstrapDialog.alert({
                    title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
                    message: "메시지가 정상적으로 발송되었습니다.",
                    type: BootstrapDialog.TYPE_PRIMARY, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
                    closable: true, // Default value is false
                    draggable: true, // Default value is false
                    buttonLabel: '닫기', // Default value is 'OK',
                    btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
                    callback: function(result) {
                        if(result) {
                            parent.closesendMessagePopup();
                        }
                    }
                });

            }
        }
    </script>


</head>

<body>
<div class="row">
    <div class="col-sm-12">
        <table class="detail_table" style="width: 100%;">
            <tr>
                <td class="value" style="padding-bottom: 3px;color: #ACADAF;">
                    TO : <span style="color: #3c3c3c;">${receiverUser.userName}</span>
                    ${receiverUser.userId} / ${receiverUser.email}
                </td>
            </tr>
            <tr>
                <td class="value"><textarea class="form-control" rows="5" id="contents" name="contents" maxlength="80"></textarea></td>
            </tr>
            <tr>
                <td class="value" style="padding-bottom: 3px;color: #ACADAF;text-align: right;">80자</td>
            </tr>

            <c:choose>
                <c:when test="${receiverUser.userId != null && fn:startsWith(receiverUser.userId, '04') && fn:length(receiverUser.userId) == 10 && type == 'sms'}">
                    <tr style="height: 25px;">
                        <td style="text-align: right;">Send SMS <input type="checkbox" id="sendSMS" name="sendSMS"> </td>
                    </tr>
                </c:when>
            </c:choose>

            <c:choose>
                <c:when test="${receiverUser.email != null && type == 'email'}">
                    <tr style="height: 25px;">
                        <td style="text-align: right;">Send Email <input type="checkbox" id="sendEmail" name="sendEmail"> </td>
                    </tr>
                </c:when>
            </c:choose>





            <tr style="height: 10px;"><td>&nbsp;</td></tr>
        </table>

        <table class="action_button_table" width="100%">
            <tr>
                <td>
                    <a href="javascript:parent.closeSendMessagePopup();" class="btn btn-info">&nbsp;&nbsp; Cancel &nbsp;&nbsp;</a>
                    <a href="javascript:sendMessage();" class="btn btn-primary">Send</a>
                </td>
            </tr>
        </table>
    </div>
</div>


</body>
</html>