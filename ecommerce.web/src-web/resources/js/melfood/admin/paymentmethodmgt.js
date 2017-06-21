function openPaymentMethodPopup(){
    $("#paymentMethodPopup").kendoWindow({
        content: "/admin/paymentmethodmgt/registPaymentMethodForm.yum",
        actions: [ "Minimize", "Maximize","Close" ],
        title: "Add payment method",
        modal: true,
        iframe: true
		});
  
	var popup_dialog = $("#paymentMethodPopup").data("kendoWindow");
	popup_dialog.setOptions({
	            width: 800,
	            height: 350
	          });
	popup_dialog.center();
	      
	$("#paymentMethodPopup").data("kendoWindow").open(); 
}
function closePaymentMethodPopup() {
	var win_dialog = $("#paymentMethodPopup").data("kendoWindow");
	win_dialog.close();
} 

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
}

function disableOrEnableBankdetail(obj) {
	var paymentMethod = obj.value;
	if(paymentMethod == 'ACCOUNT_TRANSFER') {
		$('#bankDetailInfo').show();
	} else {
		$('#bankDetailInfo').hide();
		
	}
}
    
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

    var userId = $('#userId').val();
    var paymentMethod = $('#paymentMethod').val();
    var bankName = $('#bankName').val();
    var bankBsb = $('#bankBsb').val();
    var bankAccountNo = $('#bankAccountNo').val();
    var bankAccountOwnerName = $('#bankAccountOwnerName').val();
      
 	if(userId == "") {
 		message = message + prefix + "사용자ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "userId";
        validation = false;
 	}
 	if(paymentMethod == "") {
 		message = message + prefix + "결재수단은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "paymentMethod";
 		validation = false;
 	}
 	
 	if(paymentMethod == "ACCOUNT_TRANSFER") {
 		if(bankName == "") {
 			message = message + prefix + "결재수단이 계좌이체인경우 은행지정은 필수 항목입니다.<br>";
 			checkObject[checkObject.length] = "bankName";
 			validation = false;
 		}
 		if(bankBsb == "") {
 			message = message + prefix + "결재수단이 계좌이체인경우 은행BSB 번호는 필수 항목입니다.<br>";
 			checkObject[checkObject.length] = "bankBsb";
 			validation = false;
 		}
 		if(bankAccountNo == "") {
 			message = message + prefix + "결재수단이 계좌이체인경우 은행계좌번호는 필수 항목입니다.<br>";
 			checkObject[checkObject.length] = "bankAccountNo";
 			validation = false;
 		}
 		if(bankAccountOwnerName == "") {
 			message = message + prefix + "결재수단이 계좌이체인경우 계좌주명은 필수 항목입니다.<br>";
 			checkObject[checkObject.length] = "bankAccountOwnerName";
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
	
	  if(validateForm() == false) return;
	
      var userId = $('#userId').val();
      var paymentMethod = $('#paymentMethod').val();
      var bankName = $('#bankName').val();
      var bankBsb = $('#bankBsb').val();
      var bankAccountNo = $('#bankAccountNo').val();
      var bankAccountOwnerName = $('#bankAccountOwnerName').val();
      var useYn = $('#useYn').val();
      
      $.ajax({
           url  : "/admin/paymentmethodmgt/savePaymentMethod.yum",
           data      : {
        	   userId : userId,
        	   paymentMethod : paymentMethod,
        	   bankName : bankName,
        	   bankBsb : bankBsb,
        	   bankAccountNo : bankAccountNo,
        	   bankAccountOwnerName : bankAccountOwnerName,
        	   useYn : useYn,
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
    	  infoPopup("정상적으로 저장 되었습니다");
    	  parent.search();
      }
}

function deleteInfo(){
	
	BootstrapDialog.confirm({
        title: 'WARNING  :: 호주가 즐거운 이유, 쿠빵!!',
        message: '정말 삭제하시겠습니까?',
        type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
        closable: true, // Default value is false
        draggable: true, // Default value is false
        btnCancelLabel: 'Cancel', // Default value is 'Cancel',
        btnOKLabel: 'OK', // Default value is 'OK',
        btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
        callback: function(result) {
            if(result) {
			      $.ajax({
			           url  : "/admin/paymentmethodmgt/codeDelete.yum",
			           data      : {
			             category : CATEGORY,
			             type : TYPE,
			             value : VALUE
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
    	  // goList();
      }
}


