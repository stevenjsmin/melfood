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
<script src="/resources/js/melfood/customer/customer.mypage.myinfo.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
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
                              <td class="label"><span class="required">* </span>닉네임(Nickname) </td>
                              <td class="value"><input class="form-control" type="text" id="userName" name="userName" value='${user.userName}'/></td>
                              <td class="label">실명 </td>
                              <td class="value"><input class="form-control" type="text" id="userNameReal" name="userNameReal" value='${user.userNameReal}'/></td>
                         </tr>
                         <tr>
                              <td class="label">DOB </td>
                              <td class="value"><input type="text" id="dob" name="dob" value='${user.dob}'/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                                                  
                         <tr>
                              <td class="label"><span class="required">* </span>Email </td>
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
                              <td class="label">Messenger </td>
                              <td class="value"><c:out value="${cbxUseSocialMessenger}" escapeXml="false"/></td>
                              <td class="label">Messenger ID</td>
                              <td class="value"><input class="form-control" type="text" id="useSocialMessengerId" name="useSocialMessengerId" value='${user.useSocialMessengerId}'/></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         <tr>
                              <td class="label"><span class="required">* </span>State</td>
                              <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Postcode</td>
                              <td class="value" style="padding-left: 3px;">
                                   <table><tr><td><input class="form-control" type="text" id="addressPostcode" name="addressPostcode" value='${user.addressPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('addressPostcode', 'addressSuburb')"></td></tr></table>
                              </td>
                              <td class="label"><span class="required">* </span>Suburb</td>
                              <td class="value"><div id="cbx_addressSuburb"><input class="form-control" type="text" id="addressSuburb" name="addressSuburb" value='${user.addressSuburb}'/></div></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="addressStreet" name="addressStreet" value='${user.addressStreet}'/></td>
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
                                   <a href="javascript:goDetailInfo('${user.userId}');" class="btn btn-default">&nbsp;&nbsp; 취소 &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">개인정보변경</a>
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
