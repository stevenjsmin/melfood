var checkObject = [];

$(document).ready(function() {
	
     $("#displayOrder").kendoNumericTextBox({
     	max: 100,
     	min: 1,
        format: "n0"
     });
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(id){
	document.location.href = "/framework/communicationmanager/getCommunication.yum?id=" + id;
}
    
function goModify(id){
	document.location.href = "/framework/communicationmanager/modify.yum?id=" + id;
}

function goList(category) {
	document.location.href = "/framework/communicationmanager/Main.yum?category=" + category;
} 


function add(){
	document.location.href = "/framework/communicationmanager/regist.yum";
}

function search(){
    var category = $('#category').val();
    if(category == "") {
        $('#category').css({'background':'#fffacd','border-color':'#DF0000','border' : '1px solid #f00'});
        warningPopup("검색하고자하는 Category를 먼저 선택해주세요");
        return;
    }

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

    var subject = $('#subject').val();
    var category = $('#category').val();
    var writeFrom = $('#writeFrom').val();
    var isForAllSeller = $('#isForAllSeller').val();
    var isForAllCustomer = $('#isForAllCustomer').val();
    var contents = $('#contents').val();


 	if(category == "") {
 		message = message + prefix + "글 분류선택은 필수입력입니다.<br>";
 		checkObject[checkObject.length] = "category";
        validation = false;

        if(subject == "") {
            message = message + prefix + "글 제목은 필수입력입니다.<br>";
            checkObject[checkObject.length] = "subject";
            validation = false;
        }
 	} else {
        if(category == "NOTICE") {
            if(subject == "") {
                message = message + prefix + "글 제목은 필수입력입니다.<br>";
                checkObject[checkObject.length] = "subject";
                validation = false;
            }
        }
    }

 	if(writeFrom == "") {
 		message = message + prefix + "공지자(From) 지정은 필수입니다.<br>";
 		checkObject[checkObject.length] = "writeFrom";
        validation = false;
 	}
 	if(isForAllSeller == "") {
 		message = message + prefix + "For all seller 선택은 필수항목입니다.<br>";
 		checkObject[checkObject.length] = "isForAllSeller";
 		validation = false;
 	}
 	if(isForAllCustomer == "") {
 		message = message + prefix + "For all customer 선택은 필수항목입니다.<br>";
 		checkObject[checkObject.length] = "isForAllCustomer";
 		validation = false;
 	}
 	if(contents == "") {
 		message = message + prefix + "등록하려는 내용을 입력해주세요.<br>";
 		checkObject[checkObject.length] = "contents";
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
      var subject = $('#subject').val();
      var category = $('#category').val();
      var writeFrom = $('#writeFrom').val();
      var writeTo = $('#writeTo').val();
      var isForAllSeller = $('#isForAllSeller').val();
      var isForAllCustomer = $('#isForAllCustomer').val();
      var contents = $('#contents').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/communicationmanager/saveModify.yum",
           data      : {
        	   subject : subject,
               category : category,
        	   writeFrom : writeFrom,
        	   writeTo : writeTo,
        	   isForAllSeller : isForAllSeller,
        	   isForAllCustomer : isForAllCustomer,
        	   contents : contents,
        	   id : ID,
        	   actionMode : ACTION_MODE
           },
           success : callbackSave
      });  
}
     
function callbackSave(data) {
      var message = data.message;
      var resultCode = data.resultCode;
      var category = data.category;

      if (resultCode != "0") {
           warningPopup(data.message);
      } else {
    	  goList(category);
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
			           url  : "/framework/communicationmanager/codeDelete.yum",
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
