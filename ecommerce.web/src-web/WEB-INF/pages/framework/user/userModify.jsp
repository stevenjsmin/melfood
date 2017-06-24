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
<script src="/resources/js/melfood/framework/usermanager.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
    var allowedRoles = $("#userRoles").data("kendoMultiSelect");
    allowedRoles.value(<c:out value="${allRoles}" escapeXml="false"/>);		
    
    $("#sellerIntroduction").kendoEditor({
        tools :["bold","italic","underline","justifyLeft","justifyCenter","justifyRight","insertUnorderedList","insertOrderedList","createLink","unlink","insertImage","createTable","formatting","fontSize","foreColor"],
        messages: {fontSizeInherit: "Default"},
        encoded: false
	});
    
	$("#sellerMinimumPaymentForDeliver").kendoNumericTextBox({
		   max: 99999,
		   min: 0.00,
		   format: "c2"
	    });
	
	$("#sellerMinimumPaymentForPickup").kendoNumericTextBox({
		   max: 99999,
		   min: 0.00,
		   format: "c2"
	    });
	
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
                              <td class="label">ID </td>
                              <td class="value"><span style="color: #dc0a5e;font-weight: bold;font-size: 15px;">${user.userId}</span></td>
                              <td class="label">Sex</td>
                              <td class="value"><c:out value="${cbxSex}" escapeXml="false"/></td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>Nickname </td>
                              <td class="value"><input class="form-control" type="text" id="userName" name="userName" value='${user.userName}'/></td>
                              <td class="label">Real Name </td>
                              <td class="value"><input class="form-control" type="text" id="userNameReal" name="userNameReal" value='${user.userNameReal}'/></td>
                         </tr>
                         <tr>
                              <td class="label">DOB </td>
                              <td class="value"><input type="text" id="dob" name="dob" value='${user.dob}'/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Password </td>
                              <td class="value"><span style="color: #ebebeb;">${user.password}</span></td>
                              <td></td>
                              <td></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                                                  
                         <tr>
                              <td class="label">Email </td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="email" name="email" value='${user.email}'/></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Mobile </td>
                              <td class="value"><input class="form-control" type="text" id="mobile" name="mobile" value='${user.mobile}'/></td>
                              <td class="label">Tel</td>
                              <td class="value"><input class="form-control" type="text" id="telephone" name="telephone" value='${user.telephone}'/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Mobile authentication </td>
                              <td class="value"><c:out value="${cbxMobileAuthFinished}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Messenger </td>
                              <td class="value"><c:out value="${cbxUseSocialMessenger}" escapeXml="false"/></td>
                              <td class="label">Messenger ID</td>
                              <td class="value"><input class="form-control" type="text" id="useSocialMessengerId" name="useSocialMessengerId" value='${user.useSocialMessengerId}'/></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         <tr>
                              <td class="label">State</td>
                              <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Postcode</td>
                              <td class="value" style="padding-left: 3px;">
                                   <table><tr><td><input class="form-control" type="text" id="addressPostcode" name="addressPostcode" value='${user.addressPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td></tr></table>
                              </td>
                              <td class="label">Suburb</td>
                              <td class="value"><div id="cbx_addressSuburb"><input class="form-control" type="text" id="addressSuburb" name="addressSuburb" value='${user.addressSuburb}'/></div></td>
                         </tr>
                         <tr>
                              <td class="label">Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="addressStreet" name="addressStreet" value='${user.addressStreet}'/></td>
                              <td></td>
                         </tr>
                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label"><span class="required">* </span>Use Y/N </td>
                              <td class="value"><c:out value="${cbxUseYn}" escapeXml="false"/></td>
                              <td class="label"><span class="required">* </span>가입상태</td>
                              <td class="value"><c:out value="${cbxApplyStatus}" escapeXml="false"/></td>
                         </tr>
                         <tr>
                              <td class="label">Password Failure Cnt </td>
                              <td class="value"><input type="text" id="passwordFailureCnt" name="passwordFailureCnt" value='${user.passwordFailureCnt}'/></td>
                              <td class="label">Role</td>
                              <td class="value"><c:out value="${cbxUserRoles}" escapeXml="false"/></td>
                         </tr>
                         
                         <tr>
                         	<td colspan="4">
                         	    <br />
                         	    <br />
                         	    <span class="subtitle"> 상품판매자 추가 정보 (사용자가 판매자인경우에만 유효 합니다.)</span>
    							<hr class="subtitle"/>  
                         	</td>
                         </tr>
                         <tr>
                              <td class="label">사업장 이름(Trading Name) </td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="sellerBusinessName" name="sellerBusinessName" value='${user.sellerBusinessName}'/></td>
                              <td></td>
                         </tr>                          
                         <tr>
                              <td class="label">ABN </td>
                              <td class="value"><input class="form-control" type="text" id="abn" name="abn" value='${user.abn}'/></td>
                              <td class="label">ACN</td>
                              <td class="value"><input class="form-control" type="text" id="acn" name="acn" value='${user.acn}'/></td>
                         </tr> 
                         <tr>
                              <td class="label">Photo </td>
                              <td class="value" style="vertical-align: top;">
                              	<table>
                              		<tr>
                              			<td>
                              				<c:choose>
                              					<c:when test="${user.profilePhotoId != 0 and user.profilePhotoId != null}"><img id="profilePhotoId" src="/img/?f=${user.profilePhotoId}" style="height: 70px;"></c:when>
                              					<c:otherwise><img id="profilePhotoId" src="/resources/image/profile_photo.png" style="width: 70px;"></c:otherwise>
                              				</c:choose>
                              			</td>
                              			<td style="vertical-align: bottom;">&nbsp;&nbsp; 
                              				<a href="javascript:deleteProfilePhoto('${user.userId}');">Delete</a> | 
                              				<a href="javascript:openPopupForRegisterProfilePhoto('${user.userId}');">Upload</a>
                              			</td>
                              		</tr>
                              	</table>
                              </td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Introduction </td>
                              <td class="value" colspan="3"><textarea class="form-control" rows="3" id="sellerIntroduction" name="sellerIntroduction">${user.sellerIntroduction}</textarea></td>
                         </tr>                          
                         <tr style="height: 10px;"><td colspan="4"></td></tr>


                         <tr>
                              <td>최소주문 적용여부</td>
                              <td></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">적용여부</td>
                              <td class="value"><c:out value="${cbxSellerHaveMinimumPayment}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">배송시 최소주문 금액</td>
                              <td class="value"><input type="text" id="sellerMinimumPaymentForDeliver" name="sellerMinimumPaymentForDeliver" value='${user.sellerMinimumPaymentForDeliver}'/></td>
                              <td class="label">고객픽업시 최소주문 금액</td>
                              <td class="value"><input type="text" id="sellerMinimumPaymentForPickup" name="sellerMinimumPaymentForPickup" value='${user.sellerMinimumPaymentForPickup}'/></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         <tr>
                              <td>배송/픽업 일자선택 필수여부</td>
                              <td></td>
                              <td></td>
                              <td></td>
                         </tr>
                   		<tr>
                              <td class="label">배송/픽업 일자선택 필수여부</td>
                              <td class="value"><c:out value="${cbxSellerIsMandatoryChooseDeliveryPickupDate}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>                         
                                                                                                    
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         <tr>
                              <td>배송지 정보</td>
                              <td></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">State</td>
                              <td class="value"><c:out value="${cbxSellerDeliveryAddressState}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Postcode</td>
                              <td class="value" style="padding-left: 3px;">
                                   <table><tr><td><input class="form-control" type="text" id="sellerDeliveryAddressPostcode" name="sellerDeliveryAddressPostcode" value='${user.sellerDeliveryAddressPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('sellerDeliveryAddressPostcode', 'sellerDeliveryAddressSuburb')"></td></tr></table>
                              </td>
                              <td class="label">Suburb</td>
                              <td class="value"><div id="cbx_sellerDeliveryAddressSuburb"><input class="form-control" type="text" id="sellerDeliveryAddressSuburb" name="sellerDeliveryAddressSuburb" value='${user.sellerDeliveryAddressSuburb}'/></div></td>
                         </tr>
                         <tr>
                              <td class="label">Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="sellerDeliveryAddressStreet" name="sellerDeliveryAddressStreet" value='${user.sellerDeliveryAddressStreet}'/></td>
                              <td></td>
                         </tr>              
                         
                         <tr>
                              <td>상품픽업 주소 정보</td>
                              <td></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">State</td>
                              <td class="value"><c:out value="${cbxSellerPickupAddressState}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Postcode</td>
                              <td class="value" style="padding-left: 3px;">
                                   <table><tr><td><input class="form-control" type="text" id="sellerPickupAddressPostcode" name="sellerPickupAddressPostcode" value='${user.sellerPickupAddressPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('sellerPickupAddressPostcode', 'sellerPickupAddressSuburb')"></td></tr></table>
                              </td>
                              <td class="label">Suburb</td>
                              <td class="value"><div id="cbx_sellerPickupAddressSuburb"><input class="form-control" type="text" id="sellerPickupAddressSuburb" name="sellerPickupAddressSuburb" value='${user.sellerPickupAddressSuburb}'/></div></td>
                         </tr>
                         <tr>
                              <td class="label">Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="sellerPickupAddressStreet" name="sellerPickupAddressStreet" value='${user.sellerPickupAddressStreet}'/></td>
                              <td></td>
                         </tr>                            
                         
                                                             
                         
                         <tr><td class="metavalue" colspan="4">Creator : ${user.creator}, Create Time : ${user.createDatetime}, Modify Time : ${user.modifyDatetime}</td></tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goDetailInfo('${user.userId}');" class="btn btn-default">&nbsp;&nbsp; Cancel &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     <input type="hidden" name="userId" id="userId" value="${user.userId}"/>
     
     <br />
     <br />
     <br />
     
     <script type="text/javascript">
     	var ACTION_MODE = "MODIFY";
     </script>    
</body>
</html>
