var checkObject = [];

$(document).ready(function() {

    $("#orderStartDt").kendoDateTimePicker({
        format: "yyyy-MM-dd hh:mm tt",
        start: "year"
    });
    var datepicker1 = $("#orderStartDt").data("kendoDateTimePicker");
    $("#orderStartDt").click(function() {
        datepicker1.open();
    });

    $("#orderEndDt").kendoDateTimePicker({
        format: "yyyy-MM-dd hh:mm tt",
        start: "year"
    });
    var datepicker2 = $("#orderEndDt").data("kendoDateTimePicker");
    $("#orderEndDt").click(function() {
        datepicker2.open();
    });


    $("#marketOpenStartDt").kendoDateTimePicker({
        format: "yyyy-MM-dd hh:mm tt",
        start: "year"
    });
    var datepicker3 = $("#marketOpenStartDt").data("kendoDateTimePicker");
    $("#marketOpenStartDt").click(function() {
        datepicker3.open();
    });

    $("#marketOpenEndDt").kendoDateTimePicker({
        format: "yyyy-MM-dd hh:mm tt",
        start: "year"
    });
    var datepicker4 = $("#marketOpenEndDt").data("kendoDateTimePicker");
    $("#marketOpenEndDt").click(function() {
        datepicker4.open();
    });


    $("#minimumPurchaseAmount").kendoNumericTextBox({
        max: 99999,
        min: 0.00,
        step: 0.50,
        format: "c2"
    });

    $("#discountFixedAmount").kendoNumericTextBox({
        max: 99999,
        min: 0,
        step: 0.50,
        format: "c2"
    });

    $("#discountRateValue").kendoNumericTextBox({
        format: "p1",
        max: 1.0,
        min: 0.00,
        step: 0.01
    });

}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function goDetailInfo(groupPurchaseId){
	document.location.href = "/admin/grouppurchase/groupPurchaseDetailInfo.yum?groupPurchaseId=" + groupPurchaseId;
}

function goList() {
	document.location.href = "/admin/grouppurchase/Main.yum";
}

function search(){
  	$('#grid_panel_main_1').data('kendoGrid').dataSource.read();
   	$('#grid_panel_main_1').data('kendoGrid').refresh();
}

function validateForm(){

    var count;
    var elementObj = "";
    var validation = true;

    // Initialize
    for(count=0; count < checkObject.length; count++ ){
        elementObj = "#" + checkObject[count];
        $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
    }
    checkObject = [];
    var prefix = "- &nbsp;&nbsp;";
    var message = "";

	var groupPurchaseTitle = $('#groupPurchaseTitle').val();
    var groupPurchaseSubtitle = $('#groupPurchaseSubtitle').val();
    var purchaseOrganizer = $('#purchaseOrganizer').val();
    var minimumPurchaseAmount = $('#minimumPurchaseAmount').val();
    var discountMethod = $('#discountMethod').val();
    var discountRateValue = $('#discountRateValue').val();
    var discountFixedAmount = $('#discountFixedAmount').val();
    var orderStartDt = $('#orderStartDt').val();
    var orderEndDt = $('#orderEndDt').val();
    var stopSelling = $('#stopSelling').val();
    var stopSellingReason = $('#stopSellingReason').val();
    var marketAddressState = $('#marketAddressState').val();
    var marketAddressPostcode = $('#marketAddressPostcode').val();
    var marketAddressStreet = $('#marketAddressStreet').val();
    var marketAddressSuburb = $('#marketAddressSuburb').val();
    var marketAddressComment = $('#marketAddressComment').val();
    var marketOpenEndDt = $('#marketOpenEndDt').val();
    var marketOpenStartDt = $('#marketOpenStartDt').val();
    var groupPurchaseNotice = $('#groupPurchaseNotice').val();

 	if(groupPurchaseTitle == "") {
 		message = message + prefix + "공동구매 제목은 필수입력 항목입니다.<br>";
 		checkObject[checkObject.length] = "groupPurchaseTitle";
        validation = false;
 	}

 	if(purchaseOrganizer == "") {
 		message = message + prefix + "공동생성자는 필수선택 항목입니다.<br>";
 		checkObject[checkObject.length] = "purchaseOrganizer";
 		validation = false;
 	}

 	if(minimumPurchaseAmount == "") {
 		message = message + prefix + "최소금액 입력은 필수입력 항목입니다.<br>";
 		checkObject[checkObject.length] = "minimumPurchaseAmount";
 		validation = false;
 	}

 	if(discountMethod == "") {
 		message = message + prefix + "할인방법지정은 필수선택 항목입니다.<br>";
 		checkObject[checkObject.length] = "discountMethod";
 		validation = false;
 	}

 	if(discountMethod == "" || discountMethod == null ) {
        discountMethod = "RATE";
        discountFixedAmount = 0;
        discountRateValue = 0;
 	} else {
        if(discountMethod == "RATE"){
            if(discountRateValue == "" || discountRateValue == null){
                message = message + prefix + "할일값(%)은 필수선택 입력 항목입니다.<br>";
                checkObject[checkObject.length] = "discountRateValue";
                validation = false;
            }
        } else if (discountMethod = "FIXED") {
            if(discountFixedAmount == "" || discountFixedAmount == null){
                message = message + prefix + "할일값(금액)은 필수선택 입력 항목입니다.<br>";
                checkObject[checkObject.length] = "discountFixedAmount";
                validation = false;
            }
        } else {
            message = message + prefix + "할인방법지정은 필수선택 항목입니다.<br>";
            checkObject[checkObject.length] = "discountMethod";
            validation = false;
        }

    }

    var parsedOrderStartDt = kendo.parseDate(orderStartDt, "yyyy-MM-dd hh:mm tt");
    if (!parsedOrderStartDt) {
        message = message + prefix + "올바른 공동구매 주문시작일시 형식이 아닙니다. [YYYY-MM-DD hh:mm AM/PM] 형식으로 입력해주세요<br>";
        checkObject[checkObject.length] = "orderStartDt";
        validation = false;
    }

    var parsedOrderEndDt = kendo.parseDate(orderEndDt, "yyyy-MM-dd hh:mm tt");
    if (!parsedOrderEndDt) {
        message = message + prefix + "올바른 공동구매 주문종료일시 형식이 아닙니다. [YYYY-MM-DD hh:mm AM/PM] 형식으로 입력해주세요<br>";
        checkObject[checkObject.length] = "orderEndDt";
        validation = false;
    }

    if(stopSelling != "" && stopSelling == "N"){
        if(stopSellingReason == "" || stopSellingReason == null){
            message = message + prefix + "공동구매 정지일경우 공동구매 정지이유는 필수입력 항목입니다<br>";
            checkObject[checkObject.length] = "stopSellingReason";
            validation = false;
        }
    } else {
        stopSellingReason = "";
        stopSelling = "N";
    }

    if(marketAddressState == "") {
        message = message + prefix + "공동구매 장소의 State는 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "marketAddressState";
        validation = false;
    }
    if(marketAddressPostcode == "") {
        message = message + prefix + "공동구매 장소의 우편번호입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "marketAddressPostcode";
        validation = false;
    }
    if(marketAddressSuburb == "") {
        message = message + prefix + "공동구매 장소의 Suburb입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "marketAddressSuburb";
        validation = false;
    }
    if(marketAddressStreet == "") {
        message = message + prefix + "공동구매 장소의 세부주소 입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "marketAddressStreet";
        validation = false;
    }

    var parsedMarketOpenStartDt = kendo.parseDate(marketOpenStartDt, "yyyy-MM-dd hh:mm tt");
    if (!parsedMarketOpenStartDt) {
        message = message + prefix + "올바른 공동구매 시작일시 형식이 아닙니다. [YYYY-MM-DD hh:mm AM/PM] 형식으로 입력해주세요<br>";
        checkObject[checkObject.length] = "marketOpenStartDt";
        validation = false;
    }

    var parsedMarketOpenEndDt = kendo.parseDate(marketOpenEndDt, "yyyy-MM-dd hh:mm tt");
    if (!parsedMarketOpenEndDt) {
        message = message + prefix + "올바른 공동구매 종료일시 형식이 아닙니다. [YYYY-MM-DD hh:mm AM/PM] 형식으로 입력해주세요<br>";
        checkObject[checkObject.length] = "marketOpenEndDt";
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
    var groupPurchaseTitle = $('#groupPurchaseTitle').val();
    var groupPurchaseSubtitle = $('#groupPurchaseSubtitle').val();
    var purchaseOrganizer = $('#purchaseOrganizer').val();
    var minimumPurchaseAmount = $('#minimumPurchaseAmount').val();
    var discountMethod = $('#discountMethod').val();
    var discountRateValue = $('#discountRateValue').val();
    var discountFixedAmount = $('#discountFixedAmount').val();
    var orderStartDt = $('#orderStartDt').val();
    var orderEndDt = $('#orderEndDt').val();
    var stopSelling = $('#stopSelling').val();
    var stopSellingReason = $('#stopSellingReason').val();
    var marketAddressState = $('#marketAddressState').val();
    var marketAddressPostcode = $('#marketAddressPostcode').val();
    var marketAddressStreet = $('#marketAddressStreet').val();
    var marketAddressSuburb = $('#marketAddressSuburb').val();
    var marketAddressComment = $('#marketAddressComment').val();
    var marketOpenStartDt = $('#marketOpenStartDt').val();
    var marketOpenEndDt = $('#marketOpenEndDt').val();
    var groupPurchaseNotice = $('#groupPurchaseNotice').val();

    if(validateForm() == false) return;

    $.ajax({
           url  : "/admin/grouppurchase/saveGroupPurchase.yum",
           data : {
               groupPurchaseTitle : groupPurchaseTitle,
               groupPurchaseSubtitle : groupPurchaseSubtitle,
               purchaseOrganizer : purchaseOrganizer,
               minimumPurchaseAmount : minimumPurchaseAmount,
               discountMethod : discountMethod,
               discountRateValue : discountRateValue,
               discountFixedAmount : discountFixedAmount,
               orderStartDt : orderStartDt,
               orderEndDt : orderEndDt,
               stopSelling : stopSelling,
               stopSellingReason : stopSellingReason,
               marketAddressState : marketAddressState,
               marketAddressPostcode : marketAddressPostcode,
               marketAddressStreet : marketAddressStreet,
               marketAddressSuburb : marketAddressSuburb,
               marketAddressComment : marketAddressComment,
               marketOpenStartDt    : marketOpenStartDt,
               marketOpenEndDt    : marketOpenEndDt,
               groupPurchaseNotice : groupPurchaseNotice,
               groupPurchaseId : GROUP_PURCHASE_ID,
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
          parent.closeGroupPurchasePopup();
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
			           url  : "/admin/grouppurchase/deleteGroupPurchase.yum",
			           data      : {
			             userId : _USERID
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


function openRegistGroupPurchasePopup(){
    $("#groupPurchasePopup").kendoWindow({
        content: "/admin/grouppurchase/registGroupPurchaseForm.yum",
        actions: [ "Minimize", "Maximize","Close" ],
        title: "Regist Group Purchase",
        modal: true,
        iframe: true
    });

    var popup_dialog = $("#groupPurchasePopup").data("kendoWindow");
    popup_dialog.setOptions({
        width: 800,
        height: 760
    });
    popup_dialog.center();

    $("#groupPurchasePopup").data("kendoWindow").open();
}


function openUpdateGroupPurchasePopup(groupPurchaseId){
    $("#groupPurchasePopup").kendoWindow({
        content: "/admin/grouppurchase/updateGroupPurchaseForm.yum?groupPurchaseId=" + groupPurchaseId,
        actions: [ "Minimize", "Maximize","Close" ],
        title: "Regist Group Purchase",
        modal: true,
        iframe: true
    });

    var popup_dialog = $("#groupPurchasePopup").data("kendoWindow");
    popup_dialog.setOptions({
        width: 800,
        height: 760
    });
    popup_dialog.center();

    $("#groupPurchasePopup").data("kendoWindow").open();
}

function closeGroupPurchasePopup() {

    if(ACTION_MODE == 'ADD'){
        parent.search();
    } else if(ACTION_MODE == 'MODIFY') {
        parent.goDetailInfo(GROUP_PURCHASE_ID);
    }

    var win_dialog = $("#groupPurchasePopup").data("kendoWindow");
    win_dialog.close();
}
function closeGroupPurchasePopupWithoutRefresh() {
    var win_dialog = $("#groupPurchasePopup").data("kendoWindow");
    win_dialog.close();
}

function changeDiscountMethod(obj) {
    var discountMethod = obj.value;
    if(discountMethod == 'FIXED') {
        $('#spanDiscountFixedAmount').show();
        $('#spanDiscountRateValue').hide();
        $('#discountRateValue').val('0.00');
    } else if(discountMethod == 'RATE') {
        $('#spanDiscountRateValue').show();
        $('#spanDiscountFixedAmount').hide();
        $('#discountFixedAmount').val('0.00');

    } else {
        $('#spanDiscountFixedAmount').hide();
        $('#spanDiscountRateValue').hide();
        $('#discountFixedAmount').val('0.00');
        $('#discountRateValue').val('0.00');

    }
}

function addGroupPurchaseProduct(groupPurchaseId){
    $("#groupPurchasePopup").kendoWindow({
        content: "/admin/grouppurchase/product/searchProductForRegist.yum?groupPurchaseId=" + groupPurchaseId,
        actions: [ "Minimize", "Maximize","Close" ],
        title: "Regist Group Purchase",
        modal: true,
        iframe: true
    });

    var popup_dialog = $("#groupPurchasePopup").data("kendoWindow");
    popup_dialog.setOptions({
        width: 800,
        height: 500
    });
    popup_dialog.center();

    $("#groupPurchasePopup").data("kendoWindow").open();
}

function closeAddGroupPurchaseProduct() {
    var win_dialog = $("#groupPurchasePopup").data("kendoWindow");
    win_dialog.close();
}


