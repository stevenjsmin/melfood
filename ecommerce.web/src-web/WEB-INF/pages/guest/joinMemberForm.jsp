<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        TABLE.detail_table TD.label1 {
            background-color: #EAEAEA;
            font-weight: bold;
            text-align: right;
            vertical-align: middle;
            color: #ACADAF;
            display: table-cell;
        }
    </style>

    <script type="text/javascript">
        $(document).ready(function () {
              // Member join form
            $("#joinMemberForm").collapse('show');
            $("#mobileValidateCheckForm").collapse();
            $("#mobileValidateCheckForm").addClass("hide");

            // Mobile validation
            //$("#mobileValidateCheckForm").collapse('show');
            //$("#joinMemberForm").collapse();
            //$("#joinMemberForm").addClass("hide");

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <style type="text/css">
        #addressState {
            color: #BFBEC5;
        }
    </style>
</head>

<script type="text/javascript">
    function openMemberAgreementStmt() {
        $("#agreementStmtPopup").kendoWindow({
            content: "/guest/joinmember/openMemberJoinAgreement.yum",
            actions: ["Minimize", "Maximize", "Close"],
            title: "이용약관",
            modal: true,
            iframe: true,
            position:{ top:"100", left:"25%"}
        });

        var popupwid_dialog = $("#agreementStmtPopup").data("kendoWindow");
        popupwid_dialog.setOptions({
            width: 700,
            height: 320
        });
        //popupwid_dialog.center();

        $("#agreementStmtPopup").data("kendoWindow").open();
    }
    function closeMemberAgreementStmtWindow() {
        var win_dialog = $("#agreementStmtPopup").data("kendoWindow");
        win_dialog.close();
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
            $(elementObj).css({
                'background': '#ECF5FF',
                'border-color': '#DFDFDF'
            });
        }
        checkObject = [];
        var prefix = "- &nbsp;&nbsp;";
        var message = "";

        var userId = $('#_userId').val();
        var email = $('#_email').val();
        var password = $('#_password').val();
        var password2 = $('#_password2').val();
        var addressPostcode = $('#addressPostcode').val();

        if (userId == "") {
            message = message + prefix + "사용자ID(모바일번호)는 필수입니다.<br>";
            checkObject[checkObject.length] = "_userId";
            validation = false;
        } else {
            var mobileRegex = new RegExp(/04[\d]{8}/);
            var valid = mobileRegex.test(userId);
            if (!valid) {
                message = message + prefix + "입력하신 사용자ID(Mobile번호) 형식이 올바르지 않습니다.<br>";
                checkObject[checkObject.length] = "_userId";
                validation = false;
            }
        }
        if (password == "") {
            message = message + prefix + "사용자 비밀번호 입력은 필수입니다.<br>";
            checkObject[checkObject.length] = "_password";
            validation = false;
        } else {
            if (password.length < 4) {
                message = message + prefix + "사용자 비밀번호 길이는 최소한 4문자 이상이어야합니다.<br>";
                checkObject[checkObject.length] = "_password";
                validation = false;
            } else {
                if (password != password2) {
                    message = message + prefix + "비밀번호와 확인비밀번호가 일치하지 않습니다.<br>";
                    checkObject[checkObject.length] = "_password";
                    checkObject[checkObject.length] = "_password2";
                    validation = false;
                }
            }
        }

        if(email != "") {
            if (!validateEmail(email)) {
                message = message + prefix + "입력하신 이메일 주소 형식이 올바르지 않습니다.<br>";
                checkObject[checkObject.length] = "_email";
                validation = false;
            }
        }

        if(addressPostcode != "") {
            if(!validatePostcode(addressPostcode)) {
                message = message + prefix + "우편번호가 올바른 형식이 아닙니다.<br>";
                checkObject[checkObject.length] = "addressPostcode";
                validation = false;
            }
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

    function registerMember() {
        var userId = $('#_userId').val();
        // var userName = $('#_userName').val();
        var userName = '';
        var userNameReal = $('#_userNameReal').val();
        if (userNameReal == '') userNameReal = userName;
        var password = $('#_password').val();
        var password2 = $('#_password2').val();
        var email = $('#_email').val();
        var addressState = $('#addressState').val();
        var addressPostcode = $('#addressPostcode').val();
        var addressSuburb = $('#addressSuburb').val();
        var addressStreet = $('#addressStreet').val();

        if (validateForm() == false) return;
        progress(true);

        $.ajax({
            url: "/guest/joinmember/joinMember.yum",
            data: {
                userId: userId,
                userName: userName,
                userNameReal: userNameReal,
                password: password,
                password2: password2,
                email: email,
                addressState: addressState,
                addressPostcode: addressPostcode,
                addressSuburb: addressSuburb,
                addressStreet: addressStreet,
                actionMode: ACTION_MODE
            },
            success: callbackJoinMember
        });
    }

    function callbackJoinMember(data) {
        var message = data.message;
        var resultCode = data.resultCode;

        progress(false);
        if (resultCode != "0") {
            warningPopup(data.message);
        } else {
            // goHome();
            $("#mobileValidateCheckForm").collapse('show');
            $("#mobileValidateCheckForm").addClass('show');
            $("#joinMemberForm").collapse();
            $("#joinMemberForm").addClass("hide");
            $('#_userId').val();
            $('#mobileNumber').html($('#_userId').val());
        }
    }

    function setSuburbCbx(postcodeObjId, objName) {
        var postcode = $('#' + postcodeObjId).val();
        $.ajax({
            url: "/common/postcode/suburbCmbx.yum?postcode=" + postcode + "&objName=" + objName,
            success: callbackSetSuburbCbx
        });
    }
    function callbackSetSuburbCbx(data) {
        var objName = data.objName;

        if (data.objValue != "" && data.objValue != null) {
            $('#cbx_' + objName).html(data.objValue);
        } else {
            $('#cbx_' + objName).html('<input class="form-control" type="text" id="' + objName + '" name="' + objName + '" value=""/>');
        }
    }

    function checkPostcode() {
        warningPopup('Postcode를 입력하시고 <img src="/resources/image/lookup.png"> 아이콘을 클릭하여 Suburb를 선택해주세요');
    }
</script>

<script type="text/javascript">
    function validateMobileNumber() {
        var userId = $('#_userId').val();
        var mobileValidCheckCode = $('#mobileValidCheckCode').val();

        if (validateForm() == false) return;
        progress(true);

        $.ajax({
            url: "/guest/joinmember/checkMobileValidCode.yum",
            data: {
                userId: userId,
                mobileValidCheckCode: mobileValidCheckCode
            },
            success: callbackValidateMobileNumber
        });
    }

    function callbackValidateMobileNumber(data) {
        var message = data.message;
        var resultCode = data.resultCode;

        progress(false);
        if (resultCode != "0") {
            warningPopup(data.message);
        } else {
            BootstrapDialog.show({
                title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
                message: '정상적으로 회원가입이 되었습니다. 감사합니다.',
                type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
                buttons: [{
                    label: '확인',
                    action: function (dialog) {
                        goHome();
                    }
                }]
            });
        }
    }
</script>


<body>

<div class="collapse" id="joinMemberForm">

    <div id="agreementStmtPopup"></div>
    <div class="row">
        <div class="col-sm-8">
            <table class="detail_table">
                <colgroup>
                    <col width="200px"/>
                    <col width="250px"/>
                    <col width="200px"/>
                    <col width="250px"/>
                </colgroup>
                <tr>
                    <td colspan="4"><span class="subtitle">회원가입 신청</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>아이디 (모바일번호)</td>
                    <td class="value"><input class="form-control" type="text" id="_userId" name="_userId" placeholder="Mobile 번호" value='' maxlength="10"/></td>
                    <td><span style="color: #BFBEC5;">공백없는 숫자로 구성된 모바일번호</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3" class="value" style="padding-top: 5px;">
                        <b>멜푸드</b>몰에서는 아이디를 사용하시는 <span style="color: #B0346B;">모바일(핸드폰) 번호</span>로만 받습니다.<br/><br/>
                        <!--<span style="color: #BFBEC5;">고객님의 개인정보 보호를 위하여 고객님의 ID(모바일번호)는 공개/노출되지 않습니다. </span> -->
                        </td>
                </tr>
                <tr>
                    <td class="label"><span class="required">* </span>비밀번호</td>
                    <td class="value"><input class="form-control" type="password" id="_password" name="_password" placeholder="비밀번호" value='' maxlength="20"/></td>
                    <td class="label"><span class="required">* </span>비밀번호 재확인</td>
                    <td class="value"><input class="form-control" type="password" id="_password2" name="_password2" placeholder="입력하신 비밀번호를 다시한번 입력해주세요" value='' maxlength="20"/></td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <br/>
                        <span class="subtitle">선택 사항</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td class="label1">State</td>
                    <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                    <td colspan="2"><span style="color: #BFBEC5;">고객님께 상품을 배달해 드려야하는 경우 사용됩니다.</span></td>
                </tr>
                <tr>
                    <td class="label1">Postcode</td>
                    <td class="value" style="padding-left: 3px;">
                        <table>
                            <tr>
                                <td><input class="form-control" style="background-color: #FFFFFF;" type="text" id="addressPostcode" name="addressPostcode" value='${user.addressPostcode}' style="width: 80px;" maxlength="4"/></td>
                                <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="label1">Suburb</td>
                    <td class="value">
                        <div id="cbx_addressSuburb">
                            <input class="form-control" style="background-color: #FFFFFF;" type="text" id="addressSuburb" name="addressSuburb" value='' onkeypress="checkPostcode()"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label1">Street</td>
                    <td class="value" colspan="2"><input class="form-control" style="background-color: #FFFFFF;" type="text" id="addressStreet" name="addressStreet" value=''/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="value" colspan="4" style="color: #337AB7;text-align: right;">** 입력한 주소정보는 다음 공.구일정을 계획/<b>배달</b>하는데 사용됩니다.</td>
                </tr>

                <tr><td colspan="4" style="height: 20px;"></td></tr>
                <tr>
                    <td class="label">이름/닉네임</td>
                    <td class="value"><input class="form-control" style="background-color: #FFFFFF;color: #1AAF54;" type="text" id="_userNameReal" name="_userNameReal" placeholder="당신의 이름/닉네임을 입력해주세요" value='${userDefaultName}' maxlength="30"/></td>
                    <td colspan="2"><span style="color: #BFBEC5;"><b>이름</b>/<b>닉네임</b>에 기본으로 입력된 이름은 소설 태백산맥 등장인물 중 한사람입니다.</span></td>
                </tr>
                <tr>
                    <td class="label1">이메일</td>
                    <td class="value"><input class="form-control" style="background-color: #FFFFFF;" type="text" id="_email" name="_email" placeholder="이메일 주소 @" value='' maxlength="50"/></td>
                    <td colspan="2"><span style="color: #BFBEC5;">아이디,비밀번호 분실시 또는 인보이스 발송시 필요한 정보입니다.</span></td>
                </tr>

                <tr><td colspan="4" style="height: 20px;"></td></tr>
                <tr>
                    <td colspan="4">
                        <table class="action_button_table" width="100%">
                            <tr>
                                <td><a href="javascript:openMemberAgreementStmt();" class="btn btn-warning">회원정보 보호에 관한 약속</a> <a href="javascript:registerMember();" class="btn btn-primary">회원가입</a> </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <br/> <br/> <br/>
        </div>
    </div>
</div>


<div class="collapse" id="mobileValidateCheckForm">
    <div class="row">
        <div class="col-sm-8">
            <table class="detail_table">
                <colgroup>
                    <col width="200px"/>
                    <col width="250px"/>
                    <col width="200px"/>
                    <col width="250px"/>
                </colgroup>

                <tr>
                    <td colspan="4">
                        <div class="panel panel-success">
                            <div class="panel-heading"><b>모바일 번호 인증</b></div>
                            <div class="panel-body">
                                회원으로 가입해주셔서 감사합니다. <br/><br/>
                                <b>Melfood</b> 에서는 건전하고 투명한 거래를 위하여 등록하신 모바일번호(사용자ID)에 대해서 간단한 인증절차가 필요합니다.<br/><br/>
                                감사합니다.
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle">Mobile 번호 인증</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">아이디 (모바일번호)</td>
                    <td class="value" style="font-weight:bold;"><span id="mobileNumber" style="font-weight: bold;color: #870636;"></span></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">인증코드(숫자 4자리)</td>
                    <td class="value"><input class="form-control" type="text" id="mobileValidCheckCode" name="mobileValidCheckCode" placeholder="인증코드" value='' maxlength="4"/></td>
                    <td class="value" colspan="2"><span style="color: #BFBEC5;">위 모바일 번호로 보내드린 인증코드 숫자 4자리를 입력해주세요.</span></td>
                </tr>

                <tr>
                    <td colspan="4" style="height: 20px;"></td>
                </tr>
                <tr>
                    <td></td>
                    <td style="padding-left: 5px;"><a href="javascript:validateMobileNumber();" class="btn btn-primary">모바일 번호인증</a></td>
                    <td colspan="2"></td>
                </tr>
            </table>
            <br/> <br/> <br/>
        </div>
    </div>
</div>

<script type="text/javascript">
    var ACTION_MODE = "ADD";
</script>
</body>
</html>