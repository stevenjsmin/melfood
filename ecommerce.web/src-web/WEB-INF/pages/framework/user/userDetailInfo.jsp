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
                              <td class="value">${user.sexLabel}</td>
                         </tr>                         
                         <tr>
                              <td class="label">Nickname </td>
                              <td class="value">${user.userName}</td>
                              <td class="label">Real Name </td>
                              <td class="value">${user.userNameReal}</td>
                         </tr>
                         <tr>
                              <td class="label">DOB </td>
                              <td class="value">${user.dob}</td>
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
                              <td class="value">${user.email}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Mobile / Authentication</td>
                              <td class="value">
                                   ${user.userId} /
                                        <c:choose>
                                             <c:when test="${user.mobileAuthFinished == 'Y'}">
                                                  인증 완료
                                             </c:when>
                                             <c:otherwise>
                                                  인증 미완료
                                             </c:otherwise>
                                        </c:choose>
                              </td>
                              <td class="label">Tel</td>
                              <td class="value">${user.telephone}</td>
                         </tr>
                         <tr>
                              <td class="label">Address </td>
                              <td class="value" colspan="3">${user.address}</td>
                         </tr>
                         <tr>
                              <td class="label">Messenger </td>
                              <td class="value">${user.useSocialMessengerLabel}</td>
                              <td class="label">Messenger ID</td>
                              <td class="value">${user.useSocialMessengerId}</td>
                         </tr>
                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label">Use Y/N </td>
                              <td class="value">${user.useYnLabel}</td>
                              <td class="label">가입상태</td>
                              <td class="value">${user.applyStatusLabel}</td>
                         </tr>
                         <tr>
                              <td class="label">Password Failure Cnt </td>
                              <td class="value">${user.passwordFailureCnt}</td>
                              <td class="label">Role</td>
                              <td class="value">${user.roleName}</td>
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
                              <td class="value" colspan="3"><span style="font-weight: bold;color: #E57014;">${user.sellerBusinessName}</span></td>
                         </tr> 
                        <tr>
                              <td class="label">ABN </td>
                              <td class="value">${user.abn}</td>
                              <td class="label">ACN</td>
                              <td class="value">${user.acn}</td>
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
                              <td class="label" style="padding-top: 5px;vertical-align: top;">Introduction </td>
                              <td class="value" colspan="3" style="padding-top: 10px;padding-bottom: 10px;">${user.sellerIntroduction}</td>
                         </tr>                          
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         
                         <tr>
                              <td class="label">주문 최소금액 적용</td>
                              <td class="value" colspan="3">${user.sellerHaveMinimumPaymentLabel}</td>
                         </tr>
                         <c:choose>
                         	<c:when test="${user.sellerHaveMinimumPayment == 'Y'}">
		                         <tr>
		                              <td class="label">상품배달시 주문최소 금액 </td>
		                              <td class="value"><fmt:formatNumber value="${user.sellerMinimumPaymentForDeliver}" type="currency"/></td>
		                              <td class="label">상품픽업시 주문최소 금액</td>
		                              <td class="value"><fmt:formatNumber value="${user.sellerMinimumPaymentForPickup}" type="currency"/></td>
		                         </tr>                         
                         	</c:when>
                         </c:choose>
                         
                         <tr>
                              <td class="label">배송/픽업 일자선택 필수여부</td>
                              <td class="value" colspan="3">${user.sellerIsMandatoryChooseDeliveryPickupDateLabel}</td>
                         </tr>
                         <tr>
                              <td class="label">배송지 주소</td>
                              <td class="value" colspan="3">${user.sellerDeliveryAddressStreet} ${user.sellerDeliveryAddressSuburb} ${user.sellerDeliveryAddressState} ${user.sellerDeliveryAddressPostcode}</td>
                         </tr>
                         <tr>
                              <td class="label">픽업 주소</td>
                              <td class="value" colspan="3">${user.sellerPickupAddressStreet} ${user.sellerPickupAddressSuburb} ${user.sellerPickupAddressState} ${user.sellerPickupAddressPostcode}</td>
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
                              <td align="left">
                              </td>
                              <td>
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify();" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     <br />
     <br />
     <br />
     
     <script type="text/javascript">
     	var _USERID = "<c:out value="${user.userId}"/>";
     </script>    
</body>
</html>