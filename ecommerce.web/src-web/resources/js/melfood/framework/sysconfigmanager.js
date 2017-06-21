$(document).ready(function() {
	
     $("#displayOrder").kendoNumericTextBox({
     	max: 100,
     	min: 1,
        format: "n0"
     });
     
	$("#stage_multiSelect").kendoMultiSelect({
    	animation: {
    		close: {
    	    	effects: "zoom:out",
    	    	duration: 300
    	    }
    	} 
    });     
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(stage, key){
	document.location.href = "/framework/sysconfigmanager/detailSystemConfigForm.yum?stage=" + stage + "&key=" + key;
}
    
function goList() {
	document.location.href = "/framework/sysconfigmanager/Main.yum";
} 

function goModify(){
	document.location.href = "/framework/sysconfigmanager/modifySystemConfigForm.yum?stage=" + _STAGE + "&key=" + _KEY;
} 

function add(){
	document.location.href = "/framework/sysconfigmanager/registSystemConfigForm.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
}
    
function validateForm(){
	var count;
    var elementObj = "";
    var checkObject = [];
    var validation = true;
    
    // 값검증 결과 초기화
    for(count=0; count < checkObject.length; count++ ){
         elementObj = "#" + checkObject[count];
         $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
    }
    var prefix = "- &nbsp;&nbsp;";
    var message = "";

    var stages = "";
    if(ACTION_MODE == 'ADD') {
    	  var multiStages = $("#stage_multiSelect").data("kendoMultiSelect");
    	  var stageArray = multiStages.value();
    	  for(var i=0; i<stageArray.length; i++){
    		  stages = stages + stageArray[i] + ",";
    	  }      
    } else {
    	  stages = $('#stage').val();
    }    
        
    var key = $('#key').val();
    var value = $('#value').val();
    var useYn = $('#useYn').val();
      
 	if(stages == "") {
 		message = message + prefix + "Stage 값은 필수값입니다.<br>";
 		if(ACTION_MODE == 'ADD') {
 			checkObject[checkObject.length] = "stage_multiSelect";
 		} else {
 			checkObject[checkObject.length] = "stage";
 		}
        validation = false;
 	}
 	if(key == "") {
 		message = message + prefix + "Key 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "key";
        validation = false;
 	}
 	if(value == "") {
 		message = message + prefix + "Value 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "value";
 		validation = false;
 	}
 	if(useYn == "") {
 		message = message + prefix + "사용여부 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "useYn";
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
      var stages = "";
      if(ACTION_MODE == 'ADD') {
    	  var multiStages = $("#stage_multiSelect").data("kendoMultiSelect");
    	  var stageArray = multiStages.value();
    	  for(var i=0; i<stageArray.length; i++){
    		  stages = stages + stageArray[i] + ",";
    	  }      
      } else {
    	  stages = $('#stage').val();
      }
      var key = $('#key').val();
      var value = $('#value').val();
      var description = $('#description').val();
      var useYn = $('#useYn').val();
      
      if(validateForm() == false) return;
      
      $.ajax({
           url  : "/framework/sysconfigmanager/saveSystemConfig.yum",
           data      : {
             stage : stages,
             key : key,
             value : value,
             description : description,
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
    	  goList();
      }
}

function deleteInfo(stage,key){
	
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
			           url  : "/framework/sysconfigmanager/systemConfigDelete.yum",
			           data      : {
			             stage : stage,
			             key : key
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
