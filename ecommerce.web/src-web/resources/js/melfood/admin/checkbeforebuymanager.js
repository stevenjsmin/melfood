var checkObject = [];

$(document).ready(function() {
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(id){
	document.location.href = "/admin/checkbeforebuy/getCheckBeforeBuy.yum?id=" + id;
}
    
function goModify(id){
	document.location.href = "/admin/checkbeforebuy/modify.yum?id=" + id;
}

function goList() {
	document.location.href = "/admin/checkbeforebuy/Main.yum";
} 


function add(){
	document.location.href = "/admin/checkbeforebuy/regist.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
}
    
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

	var sellerId = $('#sellerId').val();
    var subject = $('#subject').val();
    var contents = $('#contents').val();
    var isDefault = $('#isDefault').val();
    var confirmStatus = $('#confirmStatus').val();
      
 	if(sellerId == "") {
 		message = message + prefix + "판매자 지정은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "sellerId";
        validation = false;
 	}
 	if(subject == "") {
 		message = message + prefix + "제목은 필수입력입니다.<br>";
 		checkObject[checkObject.length] = "subject";
 		validation = false;
 	}
 	if(contents == "") {
 		message = message + prefix + "구매전 확인사항 내용입력은 필수항목입니다.<br>";
 		checkObject[checkObject.length] = "contents";
        validation = false;
 	}
 	if(isDefault == "") {
 		message = message + prefix + "기본선택여부 선택은 필수항목입니다.<br>";
 		checkObject[checkObject.length] = "isDefault";
 		validation = false;
 	}
 	if(confirmStatus == "") {
 		message = message + prefix + "구매전 확인사항입력내용 승인상택 선택은 필수항목입니다.<br>";
 		checkObject[checkObject.length] = "confirmStatus";
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
	  var sellerId = $('#sellerId').val();
      var subject = $('#subject').val();
      var contents = $('#contents').val();
      var isDefault = $('#isDefault').val();
      var confirmStatus = $('#confirmStatus').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/admin/checkbeforebuy/saveModify.yum",
           data      : {
        	   sellerId : sellerId,
        	   subject : subject,
        	   contents : contents,
        	   isDefault : isDefault,
        	   confirmStatus : confirmStatus,
        	   id : ID,
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
    	  goList();
      }
}

function deleteInfo(){
	
	BootstrapDialog.confirm({
        title: 'WARNING  :: 호주가 즐거운 이유, 멜푸드!!',
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
			           url  : "/admin/checkbeforebuy/deleteCheckBeforeBuy.yum",
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
    	  goList();
      }
}
