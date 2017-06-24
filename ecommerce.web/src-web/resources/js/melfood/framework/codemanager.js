var checkObject = [];

$(document).ready(function() {
	
     $("#displayOrder").kendoNumericTextBox({
     	max: 100,
     	min: 1,
        format: "n0"
     });
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(category, type, value){
	document.location.href = "/framework/codemanager/detailCodeForm.yum?category=" + category + "&type=" + type + "&value=" + value;
}
    
function goList() {
	document.location.href = "/framework/codemanager/Main.yum";
} 

function goModify(){
	document.location.href = "/framework/codemanager/modifyCodeForm.yum?category=" + CATEGORY + "&type=" + TYPE + "&value=" + VALUE;
} 

function add(){
	document.location.href = "/framework/codemanager/registCodeForm.yum";
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

    var category = $('#category').val();
    var type = $('#type').val();
    var value = $('#value').val();
    var label = $('#label').val();
      
 	if(category == "") {
 		message = message + prefix + "카테고리 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "category";
        validation = false;
 	}
 	if(type == "") {
 		message = message + prefix + "타입 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "type";
 		validation = false;
 	}
 	if(value == "") {
 		message = message + prefix + "코드 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "value";
 		validation = false;
 	}
 	if(label == "") {
 		message = message + prefix + "코드 레이블값 필수값입니다.<br>";
 		checkObject[checkObject.length] = "label";
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
      var category = $('#category').val();
      var type = $('#type').val();
      var value = $('#value').val();
      var label = $('#label').val();
      var useYn = $('#useYn').val();
      var displayOrder = $('#displayOrder').val();
      var description = $('#description').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/codemanager/saveCode.yum",
           data      : {
             category : category,
             type : type,
             value : value,
             label : label,
             useYn : useYn,
             displayOrder : displayOrder,
             description : description,
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
    	  /* 
    	  if(ACTION_MODE == "MODIFY") {
    		   infoPopup("코드정보가 정상적으로 수정되었습니다.");
    	  } else {
    		   infoPopup("코드정보가 정상적으로 추가되었습니다.");
    	  } */
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
			           url  : "/framework/codemanager/codeDelete.yum",
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
