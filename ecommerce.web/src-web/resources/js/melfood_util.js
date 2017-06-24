Number.prototype.toMoney = function(decimals, decimal_sep, thousands_sep)
{ 
   var n = this,
   c = isNaN(decimals) ? 2 : Math.abs(decimals), //if decimal is zero we must take it, it means user does not want to show any decimal
   d = decimal_sep || '.', //if no decimal separator is passed we use the dot as default decimal separator (we MUST use a decimal separator)

   t = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep, //if you don't want to use a thousands separator you can pass empty string as thousands_sep value

   sign = (n < 0) ? '-' : '',

   //extracting the absolute value of the integer part of the number and converting to string
   i = parseInt(n = Math.abs(n).toFixed(c)) + '', 

   j = ((j = i.length) > 3) ? j % 3 : 0; 
   return sign + (j ? i.substr(0, j) + t : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : ''); 
};


function toCurrency(money){
	if(money == null || money == '') return "$ 0";
	return "$ " + parseFloat(money).toMoney(2);
}


// It is simiar with NVL function of Oracle
function nvl(parm, defalutValue) {
	var val = "";
	var returnVal = "";
	if (defalutValue != null)
		val = defalutValue;
	if (parm == null || parm == '')
		returnVal = val;
	return returnVal
}

//  It is simiar with DECODE function of Oracle 
function decode() {
	var returnVal = "";
	var argCnt = arguments.length;

	var isEven = true; 
	if ((((argCnt) - 1) % 2) != 0)
		isEven = false;
	var param = arguments[0];
	var lastIndex = argCnt - 1;

	if (argCnt == 1) {
		returnVal = arguments[0];
	} else if (argCnt == 2) {
		if (arguments[0] == null || arguments[0] == '') {
			returnVal = arguments[1];
		} else {
			returnVal = arguments[0];
		}
	} else if (argCnt > 2) {
		var isMached = false;
		for ( var i = 1; i < lastIndex && isMached == false; i = i + 2) {
			if (param == arguments[i]) {
				returnVal = arguments[i + 1];
				isMached = true;
				break;
			}
		}

		if (isMached == false) {
			if (isEven) {
				returnVal = param;
			} else {
				returnVal = arguments[lastIndex];
			}
		}
	}
	return returnVal;
}


// Centerize popup window
function openPopupForm(url, windowName, pwidth, pheight){
    var win = null;
    var winL = (screen.width-pwidth)/2;
    var winT = (screen.height-pheight)/2;
    var scrollYN = "yes";
    var resizeYN = "no";
    var spec = 'toolbar=no,'; 
    spec += 'status=no,'; 
    spec += 'location=no,';
    spec += 'height='+pheight+',';
    spec += 'width='+pwidth+','; 
    spec += 'top='+winT+','; 
    spec += 'left='+winL+','; 
    spec += 'scrollbars='+(scrollYN == undefined ? "no" : scrollYN)+','; 
    spec += 'resizable='+(resizeYN == undefined ? "no" : resizeYN); 

    win = window.open(url, windowName, spec);
    if(parseInt(navigator.appVersion) >= 4) {
    	win.window.focus();
    }
    return win;
}

/**
 * @type   : function
 * @access : public
 * @desc   :  Mandatory field check. If all fields are inputed then true or false. It is useful to use when save form<br>
 *
 * <pre>
 *     if (cfCheckMandatory() == false)
 *         return;
 * </pre>
 * @return : boolean 
 */
function checkMandatory(propertyName) {
	var $mandatoryObjs = $('*[' + propertyName + '=true]');
	var returnVal = true;
	$mandatoryObjs.each(function (i, elementObj) {
		 if(elementObj.value == '' && elementObj.tagName == 'INPUT' && elementObj.type == 'text'){
			 elementObj.focus();
			 elementObj.style.background = "#fffacd";
			 returnVal = false;
		 } else if (elementObj.value == '' && elementObj.tagName == 'INPUT' && elementObj.type == 'password'){
			 elementObj.focus();
			 elementObj.style.background = "#fffacd";
			 returnVal = false;
		 } else if (elementObj.value == '' && elementObj.tagName == 'SELECT') {
			 elementObj.focus();
			 elementObj.style.background = "#fffacd";
			 returnVal = false;
		 } else if (elementObj.tagName == 'INPUT' &&  elementObj.type == 'radio') {
			 var radioVal =  $('INPUT:RADIO[NAME=' + elementObj.name + ']:checked').val();
			 if(radioVal == null){
				 elementObj.focus();
				 elementObj.style.background = "#fffacd";
				 returnVal = false;
			 }
		 }
		 return returnVal;
	 });
	
	if(returnVal == false) {
		pageLog("Check input field(s)","warn");
	}
	
	return returnVal;
}


function setOpenerDataset(returnValue) {
	window.opener.setReturnValue(returnValue);
	window.close();
}


function abbreviate(orgStringValue,maxLength) {
    var ls_str = orgStringValue;     
    var li_str_len = ls_str.length;    

    var li_max = maxLength;         
    var i = 0;                     
    var li_byte = 0;                  
    var li_len = 0;                   
    var ls_one_char = "";           
    var ls_str2 = "";                 

    for(i=0; i< li_str_len; i++) {
    	ls_one_char = ls_str.charAt(i);

	    if (escape(ls_one_char).length > 4){
	    	   li_byte += 2;
	    } else {
	    	   li_byte++; 
	    }
	
	    if(li_byte <= li_max){
	    	   li_len = i + 1;
	    }
    }

    if(li_byte > li_max) {
    	ls_str2 = ls_str.substr(0, li_len) + '...';

    } else {
    	ls_str2 = ls_str;
    }
    return ls_str2;
}  

function isNumeric(s) {
	for (i=0; i<s.length; i++) {
		    c = s.substr(i, 1);
		    if (c < "0" || c > "9") {
		    	alert('Please input numeric charater');
		    	return false;
		    }
	}
	return true;
}

function digitchk(obj) {
	 e = window.event; 
	 //Numeric 0 ~ 9 : 48 ~ 57, Keypad 0 ~ 9 : 96 ~ 105 ,8 : backspace, 46 : delete , 190,110:. 
	 if((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105) || e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 190 || e.keyCode == 110) {
		 if(e.keyCode == 48 || e.keyCode == 96 || e.keyCode == 190) { 
			 	if(obj.value == "" ) {
			 		return;
			 		//event.returnValue=false; 
			 	} else{  
			 		return; 
			 	}	
		 } else { 
			 return; 
		 }	 
 	 } else {
 		 event.returnValue=false;
 	 }
}


//1. Don't need to check float point.
//2. Default 2 digit(for cent of dollar). if you want more digit you have to use additional parameter eg. addComma(this,3)
function addCommas(nStr)
{
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validateMobile(mobileNumber) {
	mobileNumber = mobileNumber.replace(/\s/g, '');
	var re = /04[\d]{8}$/g;
	return re.test(mobileNumber);
}

function validatePostcode(postcode) {
	postcodeNumber = postcode.replace(/\s/g, '');
	var re = /\d{4}$/g;
	return re.test(postcodeNumber);
}

function commonEmailSendForm(UssEmail){
    var top_email_emailId = "";

    var top_email_subject = "";
    var top_email_sender =  "";
    var top_email_to = "";
    var top_email_cc = "";
    var top_email_bcc = "";
    var top_email_contentsTemplate = "";
    var top_email_doc1 = "";
    var top_email_doc2 = "";
    var top_email_doc3 = "";
    var top_email_doc4 = "";
    var top_email_doc5 = "";
    var top_email_callbackUrl = "";
    var top_email_callbackParam = "";
    var top_email_defaultActivateTab = "write";

    if (typeof UssEmail.emailId != 'undefined') top_email_emailId = UssEmail.emailId;
    
    if (typeof UssEmail.subject != 'undefined') top_email_subject = UssEmail.subject;
    if (typeof UssEmail.sender != 'undefined') top_email_sender = UssEmail.sender;
    if (typeof UssEmail.to != 'undefined') top_email_to = UssEmail.to;
    if (typeof UssEmail.cc != 'undefined') top_email_cc = UssEmail.cc;
    if (typeof UssEmail.bcc != 'undefined') top_email_bcc = UssEmail.bcc;
    if (typeof UssEmail.contentsTemplate != 'undefined') top_email_contentsTemplate = UssEmail.contentsTemplate;
    if (typeof UssEmail.doc1 != 'undefined') top_email_doc1 = UssEmail.doc1;
    if (typeof UssEmail.doc2 != 'undefined') top_email_doc2 = UssEmail.doc2;
    if (typeof UssEmail.doc3 != 'undefined') top_email_doc3 = UssEmail.doc3;
    if (typeof UssEmail.doc4 != 'undefined') top_email_doc4 = UssEmail.doc4;
    if (typeof UssEmail.doc5 != 'undefined') top_email_doc5 = UssEmail.doc5;
    if (typeof UssEmail.callbackUrl != 'undefined') top_email_callbackUrl = UssEmail.callbackUrl;
    if (typeof UssEmail.callbackParam != 'undefined') top_email_callbackParam = UssEmail.callbackParam;
    if (typeof UssEmail.defaultActivateTab != 'undefined') top_email_defaultActivateTab = UssEmail.defaultActivateTab;
    
    var top_email_param =  "emailId=" + top_email_emailId + "&subject=" + top_email_subject + "&sender=" + top_email_sender  + "&to=" + top_email_to  + "&cc=" + top_email_cc  + "&bcc=" + top_email_bcc + "&contentsTemplate=" + top_email_contentsTemplate  + "&doc1=" + top_email_doc1  + "&doc2=" + top_email_doc2  + "&doc3=" + top_email_doc3 + "&doc4=" + top_email_doc4 + "&doc5=" + top_email_doc5 + "&callbackUrl=" + top_email_callbackUrl+ "&callbackParam=" + top_email_callbackParam + "&defaultActivateTab=" + top_email_defaultActivateTab;
    
    $("#common_email_send").kendoWindow({
          content: "/common/EmailSendFrom.yum?" + top_email_param,
          actions: [ "Minimize", "Maximize","Close" ],
          title: "Send Email",
          modal: true,
          iframe: true
    });
    
    var email_dialog = $("#common_email_send").data("kendoWindow");
    email_dialog.setOptions({
          width: 750,
          height: 700
        });
    email_dialog.center();
    
    $("#common_email_send").data("kendoWindow").open();
}   

function closeCommonEmailSend(){
    var email_dialog = $("#common_email_send").data("kendoWindow");
    email_dialog.close();
}


// KENDO FILTER
function getFilterValue(filter, name) {
	  //search field & value exists
	  if($("#"+name).length && $("#"+name).val()!=''){
		  return $("#"+name).val();
	  }	
	  if (isDefined(filter)) {
		  var filterdata = $.grep(filter.filters, function(value) {
			  return value.field === name; 
	   });
	   if (isDefined(filterdata[0])) {
	    return filterdata[0].value;
	   }
	  }
	  return '';
}

function isDefined(obj) {
	 return (typeof obj !== 'undefined' && obj != null);
}

function commonNMIDetailInfo(UssNMI){
    
	var top_nmi_no = UssNMI.nmi;
	var top_nmi_wintitle = "NMI Info";
	
	
	if (typeof UssNMI.wintitle != 'undefined') top_nmi_wintitle = UssNMI.wintitle;
	
    var detail_info_param =  "nmi=" + top_nmi_no + "&option=";
    
    $("#common_nmi_detail_info").kendoWindow({
          content: "/common/NMIDetailInfoFrom.yum?" + detail_info_param,
          actions: [ "Minimize", "Maximize","Close" ],
          title: top_nmi_wintitle,
          modal: true,
          iframe: true
    });
    
    var nmi_win_dialog = $("#common_nmi_detail_info").data("kendoWindow");
    nmi_win_dialog.setOptions({
          width: 700,
          height: 480
        });
    nmi_win_dialog.center();
    
    $("#common_nmi_detail_info").data("kendoWindow").open();
}

function closeCommonNMIDetailInfo(){
    var win = $("#common_nmi_detail_info").data("kendoWindow");
    win.close();
}

function underDevelopment(){
	infoPopup('This service under the development..');
}

function warningPopup(message){
	if(message != null && message != ''){
		$("#warningPopupMessage").html(message);
	}
	$("#warningPopupWindow").modal();
}
function infoPopup(message){
	if(message != null && message != ''){
		$("#infoPopupMessage").html(message);
	}
	$("#infoPopupWindow").modal()
}

function downloadFile(fileId){
    document.location.href = "/admin/common/fileDown.yum?fileId=" + fileId;
}

function doLoginPopup(){
	BootstrapDialog.confirm({
        title: '알림  :: 호주가 즐거운 이유, 멜푸드!!',
        message: '요청하신 서비스는 로그인이 필요한 서비스입니다. 로그인 하시겠습니까?',
        type: BootstrapDialog.TYPE_INFO, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
        closable: true, // Default value is false
        draggable: true, // Default value is false
        btnCancelLabel: '아니요', // Default value is 'Cancel',
        btnOKLabel: '로그인', // Default value is 'OK',
        btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
        callback: function(result) {
            if(result) {
            	openLoginPopup();
            } 
        }
    }); 
}

function openLoginPopup() {
	document.location.href = "#home";
	
    $("#loginiWindowPopup").kendoWindow({
          content: "/guest/login/openWin.yum",
          actions: [ "Minimize", "Maximize","Close" ],
          title: "Login",
          modal: true,
          iframe: true
    });
    
    var popupwid_dialog = $("#loginiWindowPopup").data("kendoWindow");
    popupwid_dialog.setOptions({
   	  		pinned: true,
   	  		width: 400,height: 200,
   	  	    open: function (e) {
               this.wrapper.css({ top: 50 });
            }
        });
    popupwid_dialog.center().open();      
}
function closeLoginPopup() {
	var win_dialog = $("#loginiWindowPopup").data("kendoWindow");
    console.log(win_dialog);
	win_dialog.close();
} 

function goSellerShop(sellerId){
    document.location.href = "/shop/no.yum?seller=" + sellerId;
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