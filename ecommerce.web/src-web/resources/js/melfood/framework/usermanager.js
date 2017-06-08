var checkObject = [];

$(document).ready(function() {
	
	$("#passwordFailureCnt").kendoNumericTextBox({
     	max: 10,
     	min: 0,
        format: "n0"
    });
     
	$("#dob").kendoDatePicker({
         	format: "yyyy-MM-dd",
         	start: "year"
    });
    var datepicker1 = $("#dob").data("kendoDatePicker");
    $("#dob").click(function() {
         datepicker1.open();
    });
    
	$("#userRoles").kendoMultiSelect({
    	animation: {
    		close: {
    	    	effects: "zoom:out",
    	    	duration: 300
    	    }
    	} 
    });
	

}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(userId){
	document.location.href = "/framework/usermanager/detailUserForm.yum?userId=" + userId;
}
    
function goList() {
	document.location.href = "/framework/usermanager/Main.yum";
} 

function goModify(){
	document.location.href = "/framework/usermanager/modifyUserForm.yum?userId=" + _USERID;
} 

function add(){
	document.location.href = "/framework/usermanager/registUserForm.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
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

	var userId = $('#userId').val();
    var userName = $('#userName').val();
    var dob = $('#dob').val();
    var email = $('#email').val();
    var mobile = $('#mobile').val();
    var useSocialMessenger = $('#useSocialMessenger').val();
    var useSocialMessengerId = $('#useSocialMessengerId').val();
    var useYn = $('#useYn').val();
    var passwordFailureCnt = $('#passwordFailureCnt').val();
    var applyStatus = $('applyStatus').val();
      
 	if(userId == "") {
 		message = message + prefix + "사용자 ID는 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "userId";
        validation = false;
 	}

 	if(userName == "") {
 		message = message + prefix + "사용자 가명(Nickname)은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "userName";
 		validation = false;
 	}
 	
    if(dob != ""){
        var parsedDate = kendo.parseDate(dob, "yyyy-MM-dd");
        if (!parsedDate) {
            message = message + prefix + "올바른 생년월일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
            checkObject[checkObject.length] = "dob";
            validation = false;
        }
    }
 	
 	if(email == "" && mobile == "") {
 		message = message + prefix + "Email, 모바일 번호중 하나는 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "email";
 		checkObject[checkObject.length] = "mobile";
 		validation = false;
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
 	if(useYn == "") {
 		message = message + prefix + "사용여부는 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "useYn";
        validation = false;
 	}
 	if(applyStatus == "") {
 		message = message + prefix + "가입상태 선택은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "applyStatus";
 		validation = false;
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
      var userId = $('#userId').val();
      var userName = $('#userName').val();
      var userNameReal = $('#userNameReal').val();
      if(userNameReal == '') userNameReal = userName;
      var sex = $('#sex').val();
      var dob = $('#dob').val();
      var email = $('#email').val();
      var mobile = $('#mobile').val();
      var telephone = $('#telephone').val();
      var useSocialMessenger = $('#useSocialMessenger').val();
      var useSocialMessengerId = $('#useSocialMessengerId').val();
      var addressState = $('#addressState').val();
      var addressPostcode = $('#addressPostcode').val();
      var addressSuburb = $('#addressSuburb').val();
      var addressStreet = $('#addressStreet').val();
      var useYn = $('#useYn').val();
      var passwordFailureCnt = $('#passwordFailureCnt').val();
      var applyStatus = $('#applyStatus').val();
      
      var multiAllowedRoles = $("#userRoles").data("kendoMultiSelect");
      var allowedRolesArray = multiAllowedRoles.value();
      var allowedRoles = "";
      for(var i=0; i<allowedRolesArray.length; i++){
     		allowedRoles = allowedRoles + allowedRolesArray[i] + ",";
      }
      
      var abn = $('#abn').val();
      var acn = $('#acn').val();
      var sellerDeliveryAddressStreet = $('#sellerDeliveryAddressStreet').val();
      var sellerDeliveryAddressSuburb = $('#sellerDeliveryAddressSuburb').val();
      var sellerDeliveryAddressState = $('#sellerDeliveryAddressState').val();
      var sellerDeliveryAddressPostcode = $('#sellerDeliveryAddressPostcode').val();
      var sellerPickupAddressStreet = $('#sellerPickupAddressStreet').val();
      var sellerPickupAddressSuburb = $('#sellerPickupAddressSuburb').val();
      var sellerPickupAddressState = $('#sellerPickupAddressState').val();
      var sellerPickupAddressPostcode = $('#sellerPickupAddressPostcode').val();
      var sellerIntroduction = $('#sellerIntroduction').val();
      var sellerBusinessName = $('#sellerBusinessName').val();
      var sellerHaveMinimumPayment = $('#sellerHaveMinimumPayment').val();
      var sellerMinimumPaymentForPickup = $('#sellerMinimumPaymentForPickup').val();
      var sellerMinimumPaymentForDeliver = $('#sellerMinimumPaymentForDeliver').val();
      var sellerIsMandatoryChooseDeliveryPickupDate = $('#sellerIsMandatoryChooseDeliveryPickupDate').val();
      if(sellerIsMandatoryChooseDeliveryPickupDate == '' || sellerIsMandatoryChooseDeliveryPickupDate == undefined ) sellerIsMandatoryChooseDeliveryPickupDate = 'Y';
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/usermanager/saveUser.yum",
           data      : {
             userId : userId,
             userName : userName,
             userNameReal : userNameReal,
             sex : sex,
             dob : dob,
             email : email,
             mobile : mobile,
             telephone : telephone,
             useSocialMessenger : useSocialMessenger,
             useSocialMessengerId : useSocialMessengerId,
             addressState : addressState,
             addressPostcode : addressPostcode,
             addressSuburb : addressSuburb,
             addressStreet : addressStreet,
             useYn : useYn,
             passwordFailureCnt : passwordFailureCnt,
             applyStatus : applyStatus,
             userTypes : allowedRoles,
             abn : abn,
             acn : acn,
             sellerDeliveryAddressStreet : sellerDeliveryAddressStreet,
             sellerDeliveryAddressSuburb : sellerDeliveryAddressSuburb,
             sellerDeliveryAddressState : sellerDeliveryAddressState,
             sellerDeliveryAddressPostcode : sellerDeliveryAddressPostcode,
             sellerPickupAddressStreet : sellerPickupAddressStreet,
             sellerPickupAddressSuburb : sellerPickupAddressSuburb,
             sellerPickupAddressState : sellerPickupAddressState,
             sellerPickupAddressPostcode : sellerPickupAddressPostcode,
             sellerIntroduction : sellerIntroduction,
             sellerBusinessName : sellerBusinessName,
             sellerHaveMinimumPayment : sellerHaveMinimumPayment,
             sellerMinimumPaymentForPickup : sellerMinimumPaymentForPickup,
             sellerMinimumPaymentForDeliver : sellerMinimumPaymentForDeliver,
             sellerIsMandatoryChooseDeliveryPickupDate : sellerIsMandatoryChooseDeliveryPickupDate,
             actionMode : ACTION_MODE
           },
           success : callbackSave
      });  
}
     
function callbackSave(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	  goDetailInfo(data.userId);
    	  //goList();
      }
}



function setInitialize(userId){
	$.ajax({ url : "/framework/usermanager/initialUserAccount.yum?userId=" + userId,
             success : callbackSetInitialize
    });  
}

function callbackSetInitialize(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
			BootstrapDialog.show({
	            title: 'INFO  :: 호주가 즐거운 이유, 쿠빵!!',
	            message: '정상적으로 초기화 되었습니다.',
	            type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            buttons: [{
                  label: '확인',
                  action: function(dialog) {
                    goDetailInfo(data.userId);
                  }
                }]
	        });      	  
      }
}


function setSuburbCbx(postcodeObjId, objName){
	var postcode = $('#' + postcodeObjId).val();
	$.ajax({ url : "/common/postcode/suburbCmbx.yum?postcode=" + postcode + "&objName=" + objName,
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



function deleteInfo(){
	
	BootstrapDialog.confirm({
        title: 'WARNING  :: 호주가 즐거운 이유, 쿠빵!!',
        message: '정말 삭제하시겠습니까?',
        type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
        closable: true, // <-- Default value is false
        draggable: true, // <-- Default value is false
        btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
        btnOKLabel: 'OK', // <-- Default value is 'OK',
        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
        callback: function(result) {
            if(result) {
			      $.ajax({
			           url  : "/framework/usermanager/deleteUser.yum",
			           data      : {
			             userId : _USERID
			           },
			           success : callbackDeleteInfo
			      });            	
            }
        }
    });   	
}
     
function callbackDeleteInfo(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	  goList();
      }
}


function closeProfilePhotoWindow() {
	var win_dialog = $("#userProfilePhotoPopup").data("kendoWindow");
	win_dialog.close();
} 

function deleteProfilePhoto (userId) {
    $.ajax({
         url  : "/framework/usermanager/deleteProfileImage.yum",
         data      : {
           userId : userId
         },
         success : callbackDeleteProfilePhoto
    });    		
}

function callbackDeleteProfilePhoto(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	  $("#profilePhotoId").attr("src","/resources/image/profile_photo.png");
      }
}

function openPopupForRegisterProfilePhoto(userId) {
    $("#userProfilePhotoPopup").kendoWindow({
        content: "/framework/usermanager/profileImageUploadForm.yum?userId=" + userId,
        actions: [ "Minimize", "Maximize","Close" ],
        title: "Modify Product image",
        modal: true,
        iframe: true
		});
  
	var popup_dialog = $("#userProfilePhotoPopup").data("kendoWindow");
	popup_dialog.setOptions({
	            width: 650,
	            height: 300
	          });
	popup_dialog.center();
	      
	$("#userProfilePhotoPopup").data("kendoWindow").open();   		
}
