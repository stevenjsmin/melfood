$(document).ready(function() {
	
	$("#contractStartDate").kendoDatePicker({
         	format: "yyyy-MM-dd",
         	start: "year"
    });
    var datepicker1 = $("#contractStartDate").data("kendoDatePicker");
    $("#contractStartDate").click(function() {
         datepicker1.open();
    });
    
    $("#contractEndDate").kendoDatePicker({
    	format: "yyyy-MM-dd",
    	start: "year"
    });
    var datepicker2 = $("#contractEndDate").data("kendoDatePicker");
    $("#contractEndDate").click(function() {
    	datepicker2.open();
    });
    
    
     $("#fileUpload").kendoUpload({
        async: {
            saveUrl: "/admin/contractmgt/uploadFile.yum",
            removeUrl: "/admin/contractmgt/removeFile.yum",
            removeField: "removeFile",
            autoUpload: true,
            batch: true,
        },
        showFileList: false,
        localization: {
            statusFailed: "Failed to file upload. Please check it again and retry.",
            uploadSelectedFiles: "Upload file(s)"
        },
        complete: onComplete,
        error: onError
    });
     
    function onComplete(e) {
    	transferFileToAttachement();
    } 
    function onError(e) {
        var files = e.files;
        if (e.operation == "upload") {
            warningPopup("Failed to upload : " + files[0].name);
        }
    }       
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(userId, contractSeq){
	document.location.href = "/admin/contractmgt/detailContractForm.yum?userId=" + userId + "&contractSeq=" + contractSeq;
}
    
function goList() {
	document.location.href = "/admin/contractmgt/Main.yum";
} 

function goModify(userId, contractSeq){
	document.location.href = "/admin/contractmgt/modifyContractInfoForm.yum?userId=" + userId + "&contractSeq=" + contractSeq;
} 

function add(){
	document.location.href = "/admin/contractmgt/registContractInfoForm.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
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

    var sellerId = $('#sellerId').val();
    var contractSeq = $('#contractSeq').val();
    var contractStatus = $('#contractStatus').val();
    var contractStartDate = $('#contractStartDate').val();
    var contractEndDate = $('#contractEndDate').val();
      
 	if(sellerId == "") {
 		message = message + prefix + "Selller ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "sellerId";
        validation = false;
 	}
 	if(contractStatus == "") {
 		message = message + prefix + "계약상태 지정은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "contractStatus";
 		validation = false;
 	}
 	if(contractStartDate == "") {
 		message = message + prefix + "계약 시작일 지정은 필수입니다.<br>";
 		checkObject[checkObject.length] = "contractStartDate";
 		validation = false;
 	} else {
        var parsedDate = kendo.parseDate(contractStartDate, "yyyy-mm-dd");
        if (!parsedDate) {
            message = message + prefix + "올바른 계약시작일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
            checkObject[checkObject.length] = "contractStartDate";
            validation = false;
        }
 	}
 	if(contractEndDate == "") {
 		message = message + prefix + "계약 종료일 지정은 필수입니다.<br>";
 		checkObject[checkObject.length] = "contractEndDate";
 		validation = false;
 	} else {
        var parsedDate = kendo.parseDate(contractEndDate, "yyyy-mm-dd");
        if (!parsedDate) {
            message = message + prefix + "올바른 계약종료일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
            checkObject[checkObject.length] = "contractEndDate";
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
	
	var sellerId = $('#sellerId').val();
	var contractSeq = $('#contractSeq').val();
    var contractStatus = $('#contractStatus').val();
    var contractStartDate = $('#contractStartDate').val();
    var contractEndDate = $('#contractEndDate').val();
    var contractDescription = $('#contractDescription').val();
      
      
      $.ajax({
           url  : "/admin/contractmgt/saveContractInfo.yum",
           data      : {
             userId : sellerId,
             contractSeq : contractSeq,
             contractStatus : contractStatus,
             contractStartDate : contractStartDate,
             contractEndDate : contractEndDate,
             contractDescription : contractDescription,
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
			BootstrapDialog.show({
	            title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
	            message: '정상적으로 저장 되었습니다.',
	            type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            buttons: [{
                  label: '확인',
                  action: function(dialog) {
                    goList();
                  }
                }]
	        });     	  
    	    // goList();
      }
}

function deleteInfo(userId, contractSeq){
	
	BootstrapDialog.confirm({
        title: 'WARNING  :: 호주가 즐거운 이유, 멜푸드!!',
        message: '정말 삭제하시겠습니까? 삭제하는 경우 모든 계약정보가 영구히 삭제됩니다.',
        type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
        closable: true, // Default value is false
        draggable: true, // Default value is false
        btnCancelLabel: 'Cancel', // Default value is 'Cancel',
        btnOKLabel: 'OK', // Default value is 'OK',
        btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
        callback: function(result) {
            if(result) {
			      $.ajax({
			           url  : "/admin/contractmgt/deleteContractInfo.yum",
			           data      : {
			             userId : userId,
			             contractSeq : contractSeq
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


function transferFileToAttachement(){
	var sellerId = $('#sellerId').val();
	var contractSeq = $('#contractSeq').val();
	$.ajax({
           url  : "/admin/contractmgt/transferFileToAttachement.yum",
           data      : {
             userId : sellerId,
             contractSeq : contractSeq
           },
           success : callbackTransferFileToAttachement
      });  
}
     
function callbackTransferFileToAttachement(data) {
      var message = data.message;
      var resultCode = data.resultCode;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
			infoPopup("정상적으로 파일을 업로드하여 계약정보에 첨부하였습니다.");
			search();
      }
}
