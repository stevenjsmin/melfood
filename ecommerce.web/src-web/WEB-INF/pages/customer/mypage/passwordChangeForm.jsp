<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
$(document).ready(function() {
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<script type="text/javascript">
var checkObject = [];
function validateForm(){
	var count;
    var elementObj = "";
    var validation = true;
    
    // 값검증 결과 초기화
    for(count=0; count < checkObject.length; count++ ){
         elementObj = "#" + checkObject[count];
         $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
    }
    checkObject = [];
    var prefix = "- &nbsp;&nbsp;";
    var message = "";

    var passwordCurrent = $('#passwordCurrent').val();
    var password1 = $('#password1').val();
    var password2 = $('#password2').val();
      
 	if(passwordCurrent == "") {
 		message = message + prefix + "현재 사용하고 있는 비밀번호 입력은 필수입니다.<br>";
 		checkObject[checkObject.length] = "passwordCurrent";
        validation = false;
 	}
 	
 	if(password1 == "") {
 		message = message + prefix + "새로운 비밀번호 입력은 필수입니다.<br>";
 		checkObject[checkObject.length] = "password1";
 		validation = false;
 	} else {
 	 	if(password1.length < 4) {
 	 		message = message + prefix + "사용자 비밀번호 길이는 최소한 4문자 이상이어야합니다.<br>";
 	 		checkObject[checkObject.length] = "password1";
 	 		validation = false;
 	 	} else {
 	 	 	if(password1 != password2) {
 	 	 		message = message + prefix + "비밀번호와 확인비밀번호가 일치하지 않습니다.<br>";
 	 	 		checkObject[checkObject.length] = "password1";
 	 	 		checkObject[checkObject.length] = "password2";
 	 	 		validation = false;
 	 	 	} 	 		
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


function changePassword(){
    var passwordCurrent = $('#passwordCurrent').val();
    var password1 = $('#password1').val();
    var password2 = $('#password2').val();
      
    if(validateForm() == false) return;
    progress(true);
      
    $.ajax({
           url  : "/customer/mypage/passwordChange.yum",
           data      : {
        	   passwordCurrent : passwordCurrent,
        	   password1 : password1,
        	   password2 : password2,
               actionMode : ACTION_MODE
           },
           success : callbackChangePassword
    });  
}
     
function callbackChangePassword(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      progress(false);
      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
			BootstrapDialog.show({
	            title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
	            message: '정상적으로 비밀번호가 변경되었습니다. 감사합니다.',
	            type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            buttons: [{
                  label: '확인',
                  action: function(dialog) {
                	  goHome();
                  }
                }]
	        });     	  
      }
}

</script>


<body>

<div id="agreementStmtPopup"></div>
<div class="row">
	<div class="col-sm-8">
          <table class="detail_table">
               <colgroup>
                    <col width="150px" />
                    <col width="250px" />
                    <col width="150px" />
                    <col width="300px" />
               </colgroup> 
	           <tr>
	               <td colspan="4">
	                    <span class="subtitle">비밀번호 변경</span>
	                    <hr class="subtitle"/>
	               </td>
	           </tr>               
               <tr>
                    <td class="label"><span class="required">* </span>현재 비밀번호</td>
                    <td class="value"><input class="form-control" type="password" id="passwordCurrent" name="passwordCurrent" placeholder="현재 비밀번호" value='' maxlength="20"/></td>
                    <td></td>
                    <td></td>
               </tr>	               
               <tr>
                    <td class="label"><span class="required">* </span>새 비밀번호</td>
                    <td class="value"><input class="form-control" type="password" id="password1" name="password1" placeholder="새 비밀번호" value='' maxlength="20"/></td>
                    <td class="label"><span class="required">* </span>새 비밀번호 재입력</td>
                    <td class="value"><input class="form-control" type="password" id="password2" name="password2" placeholder="입력하신 비밀번호를 다시한번 입력해주세요" value='' maxlength="20"/></td>
               </tr>	               
	           <tr><td colspan="4" style="height: 10px;"></td></tr> 
          </table>	
	      <table class="action_button_table" width="99%">
	             <tr>
	                  <td>
	                       <a href="javascript:changePassword();" class="btn btn-primary">비밀번호 변경</a>
	                  </td>
	             </tr>
	      </table>          
          <br/>
          <br/>
          <br/>
	</div>
</div>
     
<script type="text/javascript">
     var ACTION_MODE = "ADD";
</script>     
</body>
</html>