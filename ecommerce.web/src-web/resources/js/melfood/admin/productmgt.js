$(document).ready(function() {
	
	$("#validateUntil").kendoDatePicker({
         	format: "yyyy-MM-dd",
         	start: "year"
    });
    var datepicker1 = $("#validateUntil").data("kendoDatePicker");
    $("#validateUntil").click(function() {
         datepicker1.open();
    });
    
	$("#inStockCnt").kendoNumericTextBox({
     	 max: 99999,
     	 min: 0,
         format: "n0"
    });

	$("#unitPrice").kendoNumericTextBox({
	   max: 99999,
	   min: 0.00,
	   format: "c2"
    });

	$("#extraCharge").kendoNumericTextBox({
		max: 99999,
		min: 0.00,
		format: "c2"
	});
    
	$("#sellingCommissionFee").kendoNumericTextBox({
    	max: 99999,
    	min: 1,
    	format: "c2"
    });
	
	$("#sellingCommissionRate").kendoNumericTextBox({
		format: "p0",
        min: 0,
        max: 0.1,
        step: 0.01
	});

	$("#contractDescription").kendoEditor({
	  encoded: false
	});	
	
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(prodId){
	document.location.href = "/admin/productmgt/overviewProductInfo.yum?prodId=" + prodId;
}
    
function goList() {
	document.location.href = "/admin/productmgt/Main.yum";
} 

function goModify(prodId){
	document.location.href = "/admin/productmgt/modifyProductMasterInfoForm.yum?prodId=" + prodId;
} 
function add(){
	document.location.href = "/admin/productmgt/registProductMasterInfoForm.yum";
}

function search(){
  	$('#grid_panel_main').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main').data('kendoGrid').refresh();
}
function goProdManagement(goUrl) {
	document.location.href = goUrl + '?prodId=' + _PRODID;
}
function changeCommissionType(obj) {
	var commissionType = obj.value;
	if(commissionType == 'FEE') {
		$('#spanCommissionFee').show();
		$('#spanCommissionRate').hide();
		$('#spanCommissionRate').val('');
	} else if(commissionType == 'RATE') {
		$('#spanCommissionRate').show();
		$('#spanCommissionFee').hide();
		$('#spanCommissionFee').val('');
		
	} else if(commissionType == 'NONE') {
		$('#spanCommissionRate').hide();
		$('#spanCommissionFee').hide();
		$('#spanCommissionFee').val('');
		$('#spanCommissionRate').val('');
	} else {
		$('#spanCommissionRate').hide();
		$('#spanCommissionFee').hide();
		
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

      var prodId = $('#prodId').val();
      var name = $('#name').val();
      var seller = $('#seller').val();
      var categoryId = $('#categoryId').val();
      var registerStatus = $('#registerStatus').val();
      var validateUntil = $('#validateUntil').val();
      var inStockCnt = $('#inStockCnt').val();
      var unitPrice = $('#unitPrice').val();
      var sellingStatus = $('#sellingStatus').val();
      var sellingCommissionType = $('#sellingCommissionType').val();
      var sellingCommissionRate = $('#sellingCommissionRate').val();
      var sellingCommissionFee = $('#sellingCommissionFee').val();
      var description = $('#description').val();
      
 	if(prodId == "") {
 		message = message + prefix + "상품ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "prodId";
        validation = false;
 	}
 	if(name == "") {
 		message = message + prefix + "상품명은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "name";
 		validation = false;
 	}
 	if(seller == "") {
 		message = message + prefix + "판매자지정은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "seller";
 		validation = false;
 	}
 	if(categoryId == "") {
 		message = message + prefix + "상품 분류선택은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "categoryId";
 		validation = false;
 	}
 	if(registerStatus == "") {
 		message = message + prefix + "상품등록상태 선택은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "registerStatus";
 		validation = false;
 	}
 	if(validateUntil == "") {
 		message = message + prefix + "상품등록 만료일은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "validateUntil";
 		validation = false;
 	}
 	if(inStockCnt == "") {
 		message = message + prefix + "상품재고량 필수값입니다.<br>";
 		checkObject[checkObject.length] = "inStockCnt";
 		validation = false;
 	}
 	if(unitPrice == "") {
 		message = message + prefix + "상품당가 항목은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "unitPrice";
 		validation = false;
 	}
 	if(sellingStatus == "") {
 		message = message + prefix + "상품 판매상태 지정은 필수입니다.<br>";
 		checkObject[checkObject.length] = "sellingStatus";
 		validation = false;
 	}

 	if(sellingCommissionType == "") {
 		message = message + prefix + "상품 커미션 타입은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "sellingCommissionType";
 		validation = false;
 	}
 	
 	if(sellingCommissionType != "" && sellingCommissionType == 'RATE') {
	 	if(sellingCommissionRate == "") {
	 		message = message + prefix + "상품 커미션 타입이 %인 경우 퍼센테이지 값은 필수값입니다.<br>";
	 		checkObject[checkObject.length] = "sellingCommissionRate";
	 		validation = false;
	 	} 		
 	}
 	
 	if(sellingCommissionType != "" && sellingCommissionType == 'FEE') { 		
 		if(sellingCommissionFee == "") {
 			message = message + prefix + "상품 커미션 타입이 고정값인 경우 커미션 액수는 필수값입니다.<br>";
 			checkObject[checkObject.length] = "sellingCommissionRate";
 			validation = false;
 		} 		
 	}
 	
 	if(sellingCommissionType != "" && sellingCommissionType == 'NONE') {
 		$('#sellingCommissionRate').val('');
 		$('#sellingCommissionRate').val('');
 	}
 	
 	if(description == "") {
 		message = message + prefix + "상품 설명은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "description";
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
	
	if(validateForm() == false) return;
	
      var prodId = $('#prodId').val();
      var name = $('#name').val();
      var seller = $('#seller').val();
      var categoryId = $('#categoryId').val();
      var registerStatus = $('#registerStatus').val();
      var sellingStatus = $('#sellingStatus').val();
      var validateUntil = $('#validateUntil').val();
      var inStockCnt = $('#inStockCnt').val();
      var unitPrice = $('#unitPrice').val();
      var description = $('#description').val();
      var sellingCommissionType = $('#sellingCommissionType').val();
      var sellingCommissionRate = $('#sellingCommissionRate').val();
      var sellingCommissionFee = $('#sellingCommissionFee').val();
      var tags = $('#tags').val();
      var checkBeforeBuy = $('#checkBeforeBuy').val();
      
      $.ajax({
           url  : "/admin/productmgt/saveProductMasterInfo.yum",
           data      : {
             prodId : prodId,
             name : name,
             seller : seller,
             categoryId : categoryId,
             registerStatus : registerStatus,
             sellingStatus : sellingStatus,
             validateUntil : validateUntil,
             inStockCnt : inStockCnt,
             unitPrice : unitPrice,
             description : description,
             sellingCommissionType : sellingCommissionType,
             sellingCommissionRate : sellingCommissionRate,
             sellingCommissionFee : sellingCommissionFee,
             tags : tags,
             checkBeforeBuy : checkBeforeBuy,
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
	            title: 'INFO  :: 호주가 즐거운 이유, 쿠빵!!',
	            message: '정상적으로 저장 되었습니다.',
	            type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            buttons: [{
                  label: '확인',
                  action: function(dialog) {
                	if(ACTION_MODE == 'ADD') {
                		goList();
                	} else {
                		goDetailInfo(_PRODID);
                	}
                  }
                }]
	        });     	  
    	    // goList();
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
			           url  : "/admin/productmgt/codeDelete.yum",
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



function validateProdOptionForm(){
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

    var prodId = $('#prodId').val();
    var optionSeq = $('#optionSeq').val();
    var optionItem = $('#optionItem').val();
      
 	if(prodId == "") {
 		message = message + prefix + "PROD_ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "prodId";
        validation = false;
 	}
 	if(optionSeq == "") {
 		message = message + prefix + "PROD_OPTION_SEQ 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "optionSeq";
        validation = false;
 	}
 	if(optionItem == "") {
 		message = message + prefix + "상품옵션 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "optionItem";
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

function saveProdOption(){
    var prodId = $('#prodId').val();
    var optionSeq = $('#optionSeq').val();
    var optionItem = $('#optionItem').val();
    var isMandatory = $('#isMandatory').val();
    
    if(validateProdOptionForm() == false) return;
    
    $.ajax({
         url  : "/admin/productmgt/option/saveProductOption.yum",
         data      : {
        	 prodId : prodId,
        	 optionSeq : optionSeq,
        	 optionItem : optionItem,
        	 isMandatory : isMandatory,
             actionMode : ACTION_MODE
         },
         success : callbackSaveProdOption
    });  
}
   
function callbackSaveProdOption(data) {
    var message = data.message;
    var resultCode = data.resultCode;

    if (resultCode != "0") {
         warningPopup(data.message);
    } else {
    	parent.search();
    	parent.closeOptionWindow();
    }
}


function validateProdOptionValueForm(){
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

    var prodId = $('#prodId').val();
    var optionSeq = $('#optionSeq').val();
    var valueSeq = $('#valueSeq').val(); // Only for modify
    var valueLabel = $('#valueLabel').val(); 
    var inStockCnt = $('#inStockCnt').val(); 
    var unitPrice = $('#unitPrice').val(); 
    var extraCharge = $('#extraCharge').val(); 
    var useYn = $('#useYn').val(); 
      
 	if(prodId == "") {
 		message = message + prefix + "PROD_ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "prodId";
        validation = false;
 	}
 	if(optionSeq == "") {
 		message = message + prefix + "PROD_OPTION_SEQ 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "optionSeq";
        validation = false;
 	}
 	
 	if(ACTION_MODE == 'MODIFY'){
 	 	if(valueSeq == "") {
 	 		message = message + prefix + "VALUE_SEQ 값은 필수값입니다.<br>";
 	 		checkObject[checkObject.length] = "valueSeq";
 	        validation = false;
 	 	}
 	}
 	
 	if(valueLabel == "") {
 		message = message + prefix + "상품옵션 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "valueLabel";
        validation = false;
 	}
 	
 	if(inStockCnt == "") {
 		message = message + prefix + "상품옵션 값의 재고수량 설정은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "inStockCnt";
 		validation = false;
 	}
 	
 	if(extraCharge == "") {
 		message = message + prefix +"추가비용 설정은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "extraCharge";
 		validation = false;
 	}
 	
 	if(unitPrice == "") {
 		message = message + prefix +"상품 단가 설정은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "unitPrice";
 		validation = false;
 	}
 	
 	if(useYn == "") {
 		message = message + prefix + "상품옵션 값의 사용여부 설정값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "inStockCnt";
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

function saveProdOptionValue(){
    var prodId = $('#prodId').val();
    var optionSeq = $('#optionSeq').val();
    var valueSeq = $('#valueSeq').val(); // Only for modify
    var valueLabel = $('#valueLabel').val(); 
    var inStockCnt = $('#inStockCnt').val(); 
    var unitPrice = $('#unitPrice').val(); 
    var extraCharge = $('#extraCharge').val(); 
    var useYn = $('#useYn').val(); 
    
    if(validateProdOptionValueForm() == false) return;
    
    $.ajax({
         url  : "/admin/productmgt/option/optionValue/saveProductOption.yum",
         data      : {
        	 prodId : prodId,
        	 optionSeq : optionSeq,
        	 valueSeq : valueSeq,
        	 valueLabel : valueLabel,
        	 inStockCnt : inStockCnt,
        	 unitPrice : unitPrice,
        	 extraCharge : extraCharge,
        	 useYn : useYn,
             actionMode : ACTION_MODE
         },
         success : callbackSaveProdOptionValue
    });  
}
   
function callbackSaveProdOptionValue(data) {
    var message = data.message;
    var resultCode = data.resultCode;

    if (resultCode != "0") {
         warningPopup(data.message);
    } else {
    	parent.search();
    	parent.closeOptionWindow();
    }
}


function validateProdImageForm(){
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

    var prodId = $('#prodId').val();
    var imageSeq = $('#imageSeq').val();
    var displayOrder = $('#displayOrder').val(); 
    var imageDescription = $('#imageDescription').val(); 
      
 	if(prodId == "") {
 		message = message + prefix + "PROD_ID 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "prodId";
        validation = false;
 	}
 	if(displayOrder == "") {
 		message = message + prefix + "Display 순번 값은 필수값입니다.<br>";
 		checkObject[checkObject.length] = "displayOrder";
 		validation = false;
 	}
 	if(ACTION_MODE == 'MODIFY'){
 	 	if(imageSeq == "") {
 	 		message = message + prefix + "IMAGE_SEQ 값은 필수값입니다.<br>";
 	 		checkObject[checkObject.length] = "imageSeq";
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


function saveProdImage(){
    var prodId = $('#prodId').val();
    var imageSeq = $('#imageSeq').val();
    var displayOrder = $('#displayOrder').val(); 
    var imageDescription = $('#imageDescription').val(); 
    
    if(validateProdImageForm() == false) return;
    
    $.ajax({
         url  : "/admin/productmgt/image/saveProductImageInfo.yum",
         data      : {
        	 prodId : prodId,
        	 imageSeq : imageSeq,
        	 displayOrder : displayOrder,
        	 imageDescription : imageDescription,
             actionMode : ACTION_MODE
         },
         success : callbackSaveProdImage
    });  
}
   
function callbackSaveProdImage(data) {
    var message = data.message;
    var resultCode = data.resultCode;

    if (resultCode != "0") {
         warningPopup(data.message);
    } else {
    	parent.search();
    	parent.closeImageWindow();
    }
}

function openImageInNewTab(fileId) {
	  var url = '/img/?f=' + fileId;
	  var win = window.open(url, '_blank');
	  win.focus();
}
