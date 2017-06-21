<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
$(document).ready(function() {
    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
<script type="text/javascript">
function modifyDeliveryAddress(){
	var addressPostcode = $('#addressPostcode').val();
	var addressState = $('#addressState').val();
	var addressSuburb = $('#addressSuburb').val();
	var addressStreet = $('#addressStreet').val();
	
	if(validateForm() == false) return;
	
    $.ajax({
        url  : "/cart/modifyDeliveryAddress.yum",
        data : {
        	addressPostcode : addressPostcode,
        	addressState : addressState,
        	addressSuburb : addressSuburb,
        	addressStreet : addressStreet
        },
        success : callbackModifyDeliveryAddress
   });  	
}

function callbackModifyDeliveryAddress(data) {
    var message = data.message;
    var resultCode = data.resultCode;

    if (resultCode != "0") {
         warningPopup(data.message);
    } else {
		BootstrapDialog.alert({
            title: '알림  :: 호주가 즐거운 이유, 쿠빵!!',
            message: data.message,
            type: BootstrapDialog.TYPE_INFO, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
            closable: true, // Default value is false
            draggable: true, // Default value is false
            btnCancelLabel: 'Cancel', // Default value is 'Cancel',
            btnOKLabel: 'OK', // Default value is 'OK',
            btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
            callback: function(result) {
                if(result) {
			    	parent.location.reload();
			    	parent.closeModifyDeliveryAddressPopup();
                }
            }
        });    	
    	
    }
}

var checkObject = [];
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

	var addressPostcode = $('#addressPostcode').val();
	var addressState = $('#addressState').val();
	var addressSuburb = $('#addressSuburb').val();
	var addressStreet = $('#addressStreet').val();
	
 	if(addressPostcode == "") {
 		message = message + prefix + "우편번호 입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "addressPostcode";
 		validation = false;
 	} else {
 	 	if(!validatePostcode(addressPostcode)) {
 	 		message = message + prefix + "우편번호가 올바른 형식이 아닙니다.<br>";
 	 		checkObject[checkObject.length] = "addressPostcode";
 	 		validation = false;
 	 	}	
 	}
 	
 	
 	if(addressState == "") {
 		message = message + prefix + "주소의 State 선택은 입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "addressState";
 		validation = false;
 	}
 	if(addressSuburb == "") {
 		message = message + prefix + "주소의 Suburb 입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "addressSuburb";
 		validation = false;
 	}
 	if(addressStreet == "") {
 		message = message + prefix + "주소의 상세 Street 주소입력은 필수 항목입니다.<br>";
 		checkObject[checkObject.length] = "addressStreet";
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

</script>

</head>

<body>
<div id="userProfilePhotoPopup"></div>
<div id="errorWindow"></div>
     <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="200px" />
                              <col width="300px" />
                              <col width="200px" />
                              <col width="300px" />
                         </colgroup>     
                         <tr>
                              <td class="label">State</td>
                              <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Postcode</td>
                              <td class="value" style="padding-left: 3px;">
                                   <table><tr><td><input class="form-control" type="text" id="addressPostcode" name="addressPostcode" value='${customer.addressPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td></tr></table>
                              </td>
                              <td class="label">Suburb</td>
                              <td class="value"><div id="cbx_addressSuburb"><input class="form-control" type="text" id="addressSuburb" name="addressSuburb" value='${customer.addressSuburb}'/></div></td>
                         </tr>
                         <tr>
                              <td class="label">Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="addressStreet" name="addressStreet" value='${customer.addressStreet}'/></td>
                              <td></td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr><td colspan="4"><div class="well">** 상품수령 주소 변경시 고객님의 기본 주소정보도 함께 변경됩니다.</div></td></tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:parent.closeModifyDeliveryAddressPopup();" class="btn btn-default">취소</a>
                                   <a href="javascript:modifyDeliveryAddress();" class="btn btn-primary">주소 변경</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
</body>
</html>
