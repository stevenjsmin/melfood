<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	$(document).ready(function() {

	}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<script type="text/javascript">
	function openMemberAgreementStmt() {
		$("#agreementStmtPopup").kendoWindow({
			content : "/guest/joinmember/openMemberJoinAgreement.yum",
			actions : [ "Minimize", "Maximize", "Close" ],
			title : "이용약관",
			modal : true,
			iframe : true
		});

		var popupwid_dialog = $("#agreementStmtPopup").data("kendoWindow");
		popupwid_dialog.setOptions({
			width : 700,
			height : 600
		});
		popupwid_dialog.center();

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
				'background' : '#ECF5FF',
				'border-color' : '#DFDFDF'
			});
		}
		checkObject = [];
		var prefix = "- &nbsp;&nbsp;";
		var message = "";

		var userId = $('#_userId').val();
		var userName = $('#_userName').val();
		var password = $('#_password').val();
		var password2 = $('#_password2').val();
		var email = $('#_email').val();
	    var addressState = $('#addressState').val();
	    var addressPostcode = $('#addressPostcode').val();
	    var addressSuburb = $('#addressSuburb').val();
	    var addressStreet = $('#addressStreet').val();		

		if (userId == "") {
			message = message + prefix + "사용자ID(모바일번호)는 필수입니다.<br>";
			checkObject[checkObject.length] = "_userId";
			validation = false;
		} else {
			var mobileRegex = new RegExp(/04[\d]{8}/);
			var valid = mobileRegex.test(userId);
			if (!valid) {
				message = message + prefix
						+ "입력하신 사용자ID(Mobile번호) 형식이 올바르지 않습니다.<br>";
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
				message = message + prefix
						+ "사용자 비밀번호 길이는 최소한 4문자 이상이어야합니다.<br>";
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
		if (userName == "") {
			message = message + prefix + "사용자 이름은 필수입니다.<br>";
			checkObject[checkObject.length] = "_userName";
			validation = false;
		} else {
			if (userName.length < 2) {
				message = message + prefix
						+ "사용자 이름의 길이는 최소한 2문자 이상이어야합니다.<br>";
				checkObject[checkObject.length] = "userName";
				validation = false;
			}
		}
		if (email == "") {
			message = message + prefix + "사용자 이메일주소 입력은 필수입니다.<br>";
			checkObject[checkObject.length] = "_email";
			validation = false;
		} else {
			var emailRegex = new RegExp(
					/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
			var valid = emailRegex.test(email);
			if (!valid) {
				message = message + prefix + "입력하신 이메일 주소 형식이 올바르지 않습니다.<br>";
				checkObject[checkObject.length] = "_email";
				validation = false;
			}
		}
		
	 	if(addressState == "") {
	 		message = message + prefix + "사용자 주소:State 지정은 필수 항목입니다.<br>";
	 		checkObject[checkObject.length] = "addressState";
	 		validation = false;
	 	} 	
	 	if(addressPostcode == "") {
	 		message = message + prefix + "사용자 주소:우편번호 지정은 필수 항목입니다.<br>";
	 		checkObject[checkObject.length] = "addressPostcode";
	 		validation = false;
	 	} else {
	 	 	if(!validatePostcode(addressPostcode)) {
	 	 		message = message + prefix + "우편번호가 올바른 형식이 아닙니다.<br>";
	 	 		checkObject[checkObject.length] = "addressPostcode";
	 	 		validation = false;
	 	 	}
	 	} 			
	    if(addressSuburb == ""){
	 		message = message + prefix + "사용자 주소:Surburb 지정은 필수 항목입니다.<br>";
	 		checkObject[checkObject.length] = "addressSuburb";
	 		validation = false;
	    } 	
	    
	    if(addressStreet == "" || addressStreet.length < 5){
	    	message = message + prefix + "올바른 사용자 주소:Street 를 입력해주세요.<br>";
	    	checkObject[checkObject.length] = "addressStreet";
	    	validation = false;
	    } 
	 	
	 	
		// 검증된 필드들을 마킹한다.
		for (count = 0; count < checkObject.length; count++) {
			elementObj = "#" + checkObject[count];
			$(elementObj).css({
				'background' : '#fffacd',
				'border-color' : '#DF0000',
				'border' : '1px solid #f00'
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
		var userName = $('#_userName').val();
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
			url : "/guest/joinmember/joinMember.yum",
			data : {
				userId : userId,
				userName : userName,
				password : password,
				password2 : password2,
				email : email,
				addressState : addressState,
				addressPostcode : addressPostcode,
				addressSuburb : addressSuburb,
				addressStreet : addressStreet,
				actionMode : ACTION_MODE
			},
			success : callbackJoinMember
		});
	}

	function callbackJoinMember(data) {
		var message = data.message;
		var resultCode = data.resultCode;

		progress(false);
		if (resultCode != "0") {
			warningPopup(data.message);
		} else {
			BootstrapDialog.show({
				title : 'INFO  :: 호주가 즐거운 이유, 쿠빵!!',
				message : '정상적으로 회원가입이 되었습니다. 감사합니다.',
				type : BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
				buttons : [ {
					label : '확인',
					action : function(dialog) {
						goHome();
					}
				} ]
			});
		}
	}

	function setSuburbCbx(postcodeObjId, objName) {
		var postcode = $('#' + postcodeObjId).val();
		$.ajax({
			url : "/common/postcode/suburbCmbx.yum?postcode=" + postcode + "&objName=" + objName,
			success : callbackSetSuburbCbx
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
</script>


<body>

	<div id="agreementStmtPopup"></div>
	<div class="row">
		<div class="col-sm-8">
			<table class="detail_table">
				<colgroup>
					<col width="200px" />
					<col width="250px" />
					<col width="200px" />
					<col width="250px" />
				</colgroup>
				<tr>
					<td colspan="4"><span class="subtitle">회원가입 신청</span>
						<hr class="subtitle" /></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>아이디 (모바일번호)</td>
					<td class="value"><input class="form-control" type="text"
						id="_userId" name="_userId" placeholder="Mobile 번호" value=''
						maxlength="10" /></td>
					<td><span style="color: #BFBEC5;">공백없는 숫자로 구성된 모바일번호</span></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3" class="value" style="padding-top: 5px;"><span
						style="font-weight: bold; color: #2251B0;">회원아이디 생성 규칙에 관한
							안내</span><br />
					<br /> <b>쿠빵</b>몰에서는 아이디를 사용하시는 <span
						style="color: #B0346B;">모바일(핸드폰) 번호</span>로만 받습니다.
					<br /> <span style="color: #BFBEC5;"> ID를 모바일번호로 사용하시더라도 Mall관리자 또는 상판품 판매자와 같이 반드시
							업무상 연락이 필요한 경우에만 공개됩니다.<br /><br />고객님의 개인정보 보호를 위하여 일반 웹싸이트 혹은 쇼핑몰처럼 아이디를 공개/노출 하는
							것이 아니라 고객님의 이름/닉네임만 공개될것이며, 고객님의 이름/닉네임은 로그인 후 마이페이지에서 언제든지 수정이
							가능하십니다. </span></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>비밀번호</td>
					<td class="value"><input class="form-control" type="password"
						id="_password" name="_password" placeholder="비밀번호" value=''
						maxlength="20" /></td>
					<td class="label"><span class="required">* </span>비밀번호 재확인</td>
					<td class="value"><input class="form-control" type="password"
						id="_password2" name="_password2"
						placeholder="입력하신 비밀번호를 다시한번 입력해주세요" value='' maxlength="20" /></td>
				</tr>
				<tr>
					<td colspan="4" style="height: 10px;"></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>이름/닉네임</td>
					<td class="value"><input class="form-control" type="text"
						id="_userName" name="_userName" placeholder="이름/닉네임"
						value='${userDefaultName}' maxlength="30" /></td>
					<td colspan="2"><span style="color: #BFBEC5;">입력하시지 않는
							경우 새이름 또는 만화캐릭터이름이 자동 부여됩니다.</span></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>이메일</td>
					<td class="value"><input class="form-control" type="text"
						id="_email" name="_email" placeholder="이메일 주소" value=''
						maxlength="50" /></td>
					<td colspan="2"><span style="color: #BFBEC5;">아이디,비밀번호
							분실시 또는 인보이스 발송시 필요한 정보입니다.</span></td>
				</tr>
				<tr>
					<td colspan="4" style="height: 20px;"></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>State</td>
					<td class="value"><c:out value="${cbxAddressState}"
							escapeXml="false" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>Postcode</td>
					<td class="value" style="padding-left: 3px;">
						<table>
							<tr>
								<td><input class="form-control" type="text"
									id="addressPostcode" name="addressPostcode"
									value='${user.addressPostcode}' style="width: 80px;"
									maxlength="4" /></td>
								<td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td>
							</tr>
						</table>
					</td>
					<td class="label"><span class="required">* </span>Suburb</td>
					<td class="value"><div id="cbx_addressSuburb">
							<input class="form-control" type="text" id="addressSuburb"
								name="addressSuburb" value='' />
						</div></td>
				</tr>
				<tr>
					<td class="label"><span class="required">* </span>Street</td>
					<td class="value" colspan="2"><input class="form-control"
						type="text" id="addressStreet" name="addressStreet"
						value='' /></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4" style="height: 20px;"></td>
				</tr>
				<tr>
					<td colspan="4">
						<table class="action_button_table" width="100%">
							<tr>
								<td><a href="javascript:openMemberAgreementStmt();"
									class="btn btn-default">회원가입 및 관리규정에관한 약관</a> <a
									href="javascript:registerMember();" class="btn btn-primary">회원가입</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br /> <br /> <br />
		</div>
	</div>

	<script type="text/javascript">
		var ACTION_MODE = "ADD";
	</script>
</body>
</html>