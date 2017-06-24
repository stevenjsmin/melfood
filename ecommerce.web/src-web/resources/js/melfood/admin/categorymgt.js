$(document).ready(function() {
	
     $("#displayOrder").kendoNumericTextBox({
     	max: 100,
     	min: 1,
        format: "n0"
     });
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(categoryId){
	document.location.href = "/admin/categorymgt/detailCategoryForm.yum?categoryId=" + categoryId;
}
    
function goList() {
	document.location.href = "/admin/categorymgt/Main.yum";
} 

function goModify(categoryId){
	document.location.href = "/admin/categorymgt/modifyCategoryForm.yum?categoryId=" + categoryId;
} 

function add(){
	document.location.href = "/admin/categorymgt/registCategoryForm.yum";
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

    var categoryId = $('#categoryId').val();
    var categoryName = $('#categoryName').val();
    var categoryDescription = $('#categoryDescription').val();
      
 	if(categoryId == "") {
 		message = message + prefix + "카테고리ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "categoryId";
        validation = false;
 	}
 	if(categoryName == "") {
 		message = message + prefix + "카테고리 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "categoryName";
 		validation = false;
 	}
 	if(categoryDescription == "") {
 		message = message + prefix + "카테설명은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "categoryDescription";
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
      var categoryId = $('#categoryId').val();
      var categoryName = $('#categoryName').val();
      var categoryDescription = $('#categoryDescription').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/admin/categorymgt/saveCategory.yum",
           data      : {
             categoryId : categoryId,
             categoryName : categoryName,
             categoryDescription : categoryDescription,
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
			           url  : "/admin/categorymgt/codeDelete.yum",
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
