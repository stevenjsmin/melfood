<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="melfood.framework.Ctx" %>
<%@ page import="melfood.framework.auth.SessionUserInfo" %>
<%@ page import="melfood.framework.system.BeanHelper" %>
<%@ page import="melfood.framework.role.Role"%>
<%@ page import="melfood.framework.user.User"%>
<%@ page import="melfood.framework.auth.AuthService"%>
<%@ page import="melfood.framework.auth.AuthServiceImpl"%>

<%
    AuthService userService = new AuthServiceImpl();
    SessionUserInfo sessionUser = BeanHelper.getSessionUser();

    String customerMobile = "";
    String customerEmail = "";
    if(sessionUser != null) {
        customerMobile = sessionUser.getUser().getMobile() != null ? sessionUser.getUser().getMobile() : "";
        customerEmail = sessionUser.getUser().getEmail() != null ? sessionUser.getUser().getEmail() : "";
        pageContext.setAttribute("customerEmail", customerEmail);
        pageContext.setAttribute("customerMobile", customerMobile);
    } else {
        pageContext.setAttribute("customerEmail", "");
        pageContext.setAttribute("customerMobile", "");
    }

%>

<script type="text/javascript">
    var checkObject = [];
    function validateSendMessage() {
        var count;
        var elementObj = "";
        var validation = true;

        // 값검증 결과 초기화
        for (count = 0; count < checkObject.length; count++) {
            elementObj = "#" + checkObject[count];
            $(elementObj).css({
                'background': '#ECF5FF',
                'border-color': '#DFDFDF'
            });
        }
        checkObject = [];
        var prefix = "- &nbsp;&nbsp;";
        var message = "";

        var customerMobile = $('#customerMobile').val();
        var customerEmail = $('#customerEmail').val();
        var customerQuestion = $('#customerQuestion').val();

        if(customerMobile == "" && customerEmail == "" ) {
            message = message + prefix + "Email 또는 모바일 번호는 필수 입력항목입니다. <br>";
            checkObject[checkObject.length] = "customerMobile";
            checkObject[checkObject.length] = "customerEmail";
            validation = false;
        }

        if(customerMobile != "" ) {
            var mobileRegex = new RegExp(/04[\d]{8}/);
            var valid = mobileRegex.test(customerMobile);
            if (!valid) {
                message = message + prefix + "입력하신 Mobile번호 형식이 올바르지 않습니다.<br>";
                checkObject[checkObject.length] = "customerMobile";
                validation = false;
            }
        }
        if(customerEmail != "" ) {
            if (!validateEmail(customerEmail)) {
                message = message + prefix + "입력하신 이메일 주소 형식이 올바르지 않습니다.<br>";
                checkObject[checkObject.length] = "customerEmail";
                validation = false;
            }
        }

        if(customerQuestion == "") {
            message = message + prefix + "문의하시고자하는 내용을 입력해주세요.<br>";
            checkObject[checkObject.length] = "customerQuestion";
            validation = false;
        }


        // 검증된 필드들을 마킹한다.
        for (count = 0; count < checkObject.length; count++) {
            elementObj = "#" + checkObject[count];
            $(elementObj).css({
                'background': '#fffacd',
                'border-color': '#DF0000',
                'border': '1px solid #f00'
            });
        }
        if (validation == false) {
            // 오류가 있는 경우 경고 창을 보여준다.
            warningPopup(message);
        }
        return validation;
    }

    function sendQuestionMessage() {
        var customerMobile = $('#customerMobile').val();
        var customerEmail = $('#customerEmail').val();
        var customerQuestion = $('#customerQuestion').val();

        if (validateSendMessage() == false) return;
        progress(true);

        $.ajax({
            url: "/guest/common/sendQuestionMessage.yum",
            data: {
                customerMobile: customerMobile,
                customerEmail: customerEmail,
                customerQuestion: customerQuestion
            },
            success: callbackSendQuestionMessage
        });
    }

    function callbackSendQuestionMessage(data) {
        var message = data.message;
        var resultCode = data.resultCode;

        progress(false);
        if (resultCode != "0") {
            warningPopup(data.message);
        } else {
            $('#customerMobile').val('');
            $('#customerEmail').val('');
            $('#customerQuestion').val('');

            infoPopup('정상적으로 문의하신 내용이 접수되었습니다. 빠른 시일내에 답변드리도록 하겠습니다.<br/><br/> 감사합니다.');
        }
    }
</script>

<div class="row" style="padding-top: 10px;">
    <div class="col-sm-4">
        <!-- 회사소개 -->
        <!-- 기타안내 -->
        <table style="width: 100%;">
            <tr>
                <td style="font-size: 13px;font-weight: bold;color: #A2A4A4;">
                    Copyright © 2017 <span style="font-size: 13px;color: #BCBCBC;">Melfood </span>@ 우와한 가족들의 협동조합
                </td>
            </tr>
            <tr><td style="height: 20px;"></td></tr>
            <tr>
                <td style="color: #A2A4A4;font-size: 13px">
                    4 Torresdale Road, South Morang VIC 3752, Australia<br/>
                    ABN : 25 879 723 890 :: <a href="http://abr.business.gov.au/SearchByAbn.aspx?SearchText=25879723890" style="color: #337AB7;">http://abr.business.gov.au/</a><br/><br/>
                    <table style="color: #A2A4A4;font-size: 13px">
                        <tr><td>Monday to Friday 8.30am to 5:30pm (AEST/AEDT)</td></tr>
                        <tr><td>0422 육삼이 삼삼팔</td></tr>
                    </table>
                </td>
            </tr>
            <tr style="height: 20px;"><td></td></tr>
            <tr><td><a href="/welcome/you.yum" style="font-size: 15px;font-weight: bold;color: #A2A4A4;">공굴러가입 ::: 입점안내</a></td></tr>
        </table>

    </div>
    <div class="col-sm-5">
        <!-- 문의사항 -->
        <table style="width: 100%;">
            <colgroup>
                <col width="30px"/>
                <col width="*"/>
            </colgroup>
            <tr>
                <td colspan="2">
                    <span style="font-size: 13px;color: #A2A4A4;font-weight: bold;">Contact Us | Question | Leave your message </span>
                </td>
            </tr>
            <tr><td style="height: 20px;" colspan="2"></td></tr>
            <tr>
                    <td></td>
                    <td style="padding: 5px;"><input class="form-control" type="text" id="customerMobile" name="customerMobile" value='${customerMobile}' placeholder="모바일 번호" maxlength="10" style="width: 200px;background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;"/></td>
            </tr>
            <tr>
                <td></td>
                <td style="padding: 5px;"><input class="form-control" type="text" id="customerEmail" name="customerEmail" value='${customerEmail}' placeholder="이메일 주소" style="background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;"/></td>
            </tr>
            <tr>
                <td></td>
                <td style="padding: 5px;"><textarea class="form-control" rows="3" id="customerQuestion" name="customerQuestion" style="background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;" maxlength="250"></textarea></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right;padding: 5px;"><button type="submit" class="btn btn-default" style="background-color: #7E7C7F;border-color: #5e5e5e;" onclick="sendQuestionMessage();">SEND MESSAGE</button></td>
            </tr>
        </table>
    </div>
</div>
