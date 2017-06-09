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
</script>

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

    var userId = $('#_userId').val();
      
 	if(userId == "") {
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


function findMemberPassword(){
    var userId = $('#_userId').val();
      
    if(validateForm() == false) return;
    progress(true);
    
    $.ajax({
           url  : "/guest/joinmember/sendEmailForForgotUserPassword.yum",
           data      : {
        	   userId : userId,
               actionMode : ACTION_MODE
           },
           success : callbackFindMemberPassword
    });  
}
     
function callbackFindMemberPassword(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      progress(false);
      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	   infoPopup(data.message);
      }
}

</script>


<body>

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
	               <td colspan="4">
	                    <span class="subtitle">비밀번호 찾기</span>
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
						   <br/>
						   안녕하세요.<br/>
						   <b>쿠빵</b>몰에서는 <span style="color: #B0346B;">모바일(핸드폰) 번호</span>를 아이디로 사용합니다..<br/><br/>
						   
						   <span style="color: #BFBEC5;">
						   아이디(모바일번호)를 입력하시고 오른쪽 하단에 있는 "비밀번호 찾기" 버튼을 클릭하시면 사용하시고 계신 비밀번호가 등록하신 메일로 발송됩니다.
						   <br/>
						   고객님의 이름/닉네임은 로그인 후 마이페이지에서 언제든지 수정이 가능하십니다.<br/><br/>
						   감사합니다.
						   </span>
	               </td>
	           </tr>

	           <tr><td colspan="4" style="height: 20px;"></td></tr>                                         	                          
               <tr>
                    <td colspan="4">
	                    <table class="action_button_table" width="100%">
	                         <tr>
	                              <td>
	                                   <a href="javascript:findMemberPassword();" class="btn btn-primary">비밀번호 찾기</a>
	                              </td>
	                         </tr>
	                    </table>
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