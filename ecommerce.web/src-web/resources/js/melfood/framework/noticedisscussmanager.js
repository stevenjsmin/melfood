var checkObject = [];

$(document).ready(function() {
	
     $("#displayOrder").kendoNumericTextBox({
     	max: 100,
     	min: 1,
        format: "n0"
     });
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(id){
	document.location.href = "/framework/noticedisscussmanager/noticeDiscuss.yum?id=" + id;
}
    
function goList() {
	document.location.href = "/framework/noticedisscussmanager/Main.yum";
} 

function goModify(id){
	document.location.href = "/framework/noticedisscussmanager/noticeDiscussModifyForm.yum?id=" + id;
} 

function add(){
	document.location.href = "/framework/noticedisscussmanager/noticeDiscussRegistForm.yum";
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

    var subject = $('#subject').val();
    var writeTo = $('#writeTo').val();
    var isForAllSeller = $('#isForAllSeller').val();
    var isForAllCustomer = $('#isForAllCustomer').val();
    var isForNotice = $('#isForNotice').val();
    var contents = $('#contents').val();

 	if(subject == "") {
 		message = message + prefix + "제목은 필수 입력항목입니다.<br>";
 		checkObject[checkObject.length] = "subject";
        validation = false;
 	}
 	if(writeTo == "") {
 		message = message + prefix + "대상자 지정은 필수 입력항목입니다.<br>";
 		checkObject[checkObject.length] = "writeTo";
 		validation = false;
 	}
 	if(isForAllSeller == "") {
 		message = message + prefix + "Is For Seller 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "isForAllSeller";
 		validation = false;
 	}
 	if(isForAllCustomer == "") {
 		message = message + prefix + "Is For Customer 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "isForAllCustomer";
        validation = false;
 	}
 	if(isForNotice == "") {
 		message = message + prefix + "Is For Notice 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "isForNotice";
        validation = false;
 	}
 	if(contents == "") {
 		message = message + prefix + "대화내용은 입력항목입니다.<br>";
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
      var id = ID;
      var category = $('#category').val();
      var subject = $('#subject').val();
      var contents = $('#contents').val();
      var writer = $('#writer').val();
      var writeFrom = $('#writeFrom').val();
      var writeTo = $('#writeTo').val();
      var isForAllSeller = $('#isForAllSeller').val();
      var isForAllCustomer = $('#isForAllCustomer').val();
      var isForNotice = $('#isForNotice').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/noticedisscussmanager/saveModify.yum",
           data      : {
               id : id,
               category : category,
               subject : subject,
               contents : contents,
               writer : writer,
               writeFrom : writeFrom,
               writeTo : writeTo,
               isForAllSeller : isForAllSeller,
               isForAllCustomer : isForAllCustomer,
               isForNotice : isForNotice,
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
        title: 'WARNING  :: 호주가 즐거운 이유, MelFood !!',
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
			           url  : "/framework/noticedisscussmanager/deleteNoticeDiscuss.yum",
			           data      : {
			             id : ID,
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
