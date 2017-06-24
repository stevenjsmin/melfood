var checkObject = [];

$(document).ready(function() {
	
	$("#dob").kendoDatePicker({
         	format: "yyyy-MM-dd",
         	start: "year"
    });
    var datepicker1 = $("#dob").data("kendoDatePicker");
    $("#dob").click(function() {
         datepicker1.open();
    });
    

}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(userId){
	document.location.href = "/customer/mypage/myDetailInfo.yum?userId=" + userId;
}
 
function goModify(){
	document.location.href = "/customer/mypage/modifyMyDetailInfo.yum?userId=" + _USERID;
} 
    
function validateForm(){
	
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

    var userName = $('#userName').val();
    var dob = $('#dob').val(); // 필수는 아니지만 있는 경우 형식체크
    var email = $('#email').val(); // 필수
    var useSocialMessenger = $('#useSocialMessenger').val();
    var useSocialMessengerId = $('#useSocialMessengerId').val();
    var addressState = $('#addressState').val();
    var addressPostcode = $('#addressPostcode').val();
    var addressSuburb = $('#addressSuburb').val();
    var addressStreet = $('#addressStreet').val();
    
    
    if(email != "") {
        if (!validateEmail(email)) {
            message = message + prefix + "입력하신 이메일 주소 형식이 올바르지 않습니다.<br>";
            checkObject[checkObject.length] = "email";
            validation = false;
        }
    }

 	
    if(dob != ""){
        var parsedDate = kendo.parseDate(dob, "yyyy-MM-dd");
        if (!parsedDate) {
            message = message + prefix + "올바른 생년월일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
            checkObject[checkObject.length] = "dob";
            validation = false;
        }
    }
 	
 	if(useSocialMessenger != "" && useSocialMessengerId == "") {
 		message = message + prefix + "메신저 종류가 설정되었으면 메신지 아이디는 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "useSocialMessengerId";
 		validation = false;
 	}
 	if(useSocialMessenger == "" && useSocialMessengerId != "") {
 		message = message + prefix + "메신저ID를 지정한경우 메신저 종류는 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "useSocialMessenger";
 		validation = false;
 	}

    if(addressPostcode != "") {
        if(!validatePostcode(addressPostcode)) {
            message = message + prefix + "우편번호가 올바른 형식이 아닙니다.<br>";
            checkObject[checkObject.length] = "addressPostcode";
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
 

function save(){
      var userName = $('#userName').val();
      var userNameReal = $('#userNameReal').val();
      var sex = $('#sex').val();
      var dob = $('#dob').val();
      var email = $('#email').val();
      var telephone = $('#telephone').val();
      var useSocialMessenger = $('#useSocialMessenger').val();
      var useSocialMessengerId = $('#useSocialMessengerId').val();
      var addressState = $('#addressState').val();
      var addressPostcode = $('#addressPostcode').val();
      var addressSuburb = $('#addressSuburb').val();
      var addressStreet = $('#addressStreet').val();

      if(validateForm() == false) return;

      progress(true);
      $.ajax({
           url  : "/customer/mypage/saveUser.yum",
           data      : {
             userName : userName,
             userNameReal : userNameReal,
             sex : sex,
             dob : dob,
             email : email,
             telephone : telephone,
             useSocialMessenger : useSocialMessenger,
             useSocialMessengerId : useSocialMessengerId,
             addressState : addressState,
             addressPostcode : addressPostcode,
             addressSuburb : addressSuburb,
             addressStreet : addressStreet,
             actionMode : ACTION_MODE
           },
           success : callbackSave
      });  
}
     
function callbackSave(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      progress(false);
      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	  goDetailInfo(data.userId);
      }
}




