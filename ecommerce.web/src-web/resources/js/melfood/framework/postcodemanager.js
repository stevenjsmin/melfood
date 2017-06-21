$(document).ready(function() {
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(postcodeId){
	document.location.href = "/framework/postcodemanager/detailPostcodeForm.yum?postcodeId=" + postcodeId;
}
    
function goList() {
	document.location.href = "/framework/postcodemanager/Main.yum";
} 

function goModify(postcodeId){
	document.location.href = "/framework/postcodemanager/modifyPostcodeForm.yum?postcodeId=" + postcodeId;
} 

function add(){
	document.location.href = "/framework/postcodemanager/registPostcodeForm.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
}
    
function validateForm(){
	
    var count;
    var elementObj = "";
    var validation = true;
    var checkObject = [];
    
    // Initialize
    for(count=0; count < checkObject.length; count++ ){
        elementObj = "#" + checkObject[count];
        $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
    }
    checkObject = [];
    var prefix = "- &nbsp;&nbsp;";
    var message = "";

    var state = $('#state').val();
    var suburb = $('#suburb').val();
    var postcode = $('#postcode').val();
      
 	if(state == "") {
 		message = message + prefix + "State 선택은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "state";
        validation = false;
 	}
 	if(suburb == "") {
 		message = message + prefix + "Suburb 입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "suburb";
 		validation = false;
 	}
 	if(postcode == "") {
 		message = message + prefix + "Postcode 입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "postcode";
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
      var postcodeId = $('#postcodeId').val();
      var state = $('#state').val();
      var suburb = $('#suburb').val();
      var postcode = $('#postcode').val();
      var longitude = $('#longitude').val();
      var latitude = $('#latitude').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/postcodemanager/savePostcode.yum",
           data      : {
        	 postcodeId : postcodeId,
             state : state,
             suburb : suburb,
             postcode : postcode,
             longitude : longitude,
             latitude : latitude,
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
			           url  : "/framework/postcodemanager/postcodeDelete.yum",
			           data      : {
			             suburb : _SUBURB,
			             postcode : _POSTCODE
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
