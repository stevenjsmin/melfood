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
    $(function() {
         $('#userId').focusout(function() {
             var checkUserId = $('#userId').val();
             if(checkUserId != "" && checkUserId != null){
                $.ajax({
                     url  : "/framework/usermanager/existUser.yum",
                     data      : {
                       userId : checkUserId
                     },
                     success : callbackCheckUserId
                });              	 
             }
         });
    });    
    function callbackCheckUserId(data) {
           var message = data.message;
           if (message == 'true') {
                warningPopup("해당 ID는 이미 사용하고 있는 ID 입니다.");
           }
    }
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<body>
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
                              <td class="label"><span class="required">* </span>ID </td>
                              <td class="value"><input class="form-control" type="text" id="userId" name="userId" value=''/></td>
                              <td class="label">Sex</td>
                              <td class="value"><c:out value="${cbxSex}" escapeXml="false"/></td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>Nickname </td>
                              <td class="value"><input class="form-control" type="text" id="userName" name="userName" value=''/></td>
                              <td class="label">Real Name </td>
                              <td class="value"><input class="form-control" type="text" id="userNameReal" name="userNameReal" value=''/></td>
                         </tr>
                         <tr>
                              <td class="label">DOB </td>
                              <td class="value"><input type="text" id="dob" name="dob" value=''/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         <tr>
                              <td class="label">Email </td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="email" name="email" value=''/></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Mobile </td>
                              <td class="value"><input class="form-control" type="text" id="mobile" name="mobile" value=''/></td>
                              <td class="label">Tel</td>
                              <td class="value"><input class="form-control" type="text" id="telephone" name="telephone" value=''/></td>
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
                              <td class="value"><input class="form-control" type="text" id="useSocialMessengerId" name="useSocialMessengerId" value=''/></td>
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
                                   <table><tr><td><input class="form-control" type="text" id="addressPostcode" name="addressPostcode" value='' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx()"></td></tr></table>
                              </td>
                              <td class="label">Suburb</td>
                              <td class="value"><div id="cbxAddressSuburb"><input class="form-control" type="text" id="addressSuburb" name="addressSuburb" value=''/></div></td>
                         </tr>
                         <tr>
                              <td class="label">Street</td>
                              <td class="value" colspan="2"><input class="form-control" type="text" id="addressStreet" name="addressStreet" value=''/></td>
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
                              <td class="value"><c:out value="${cbxUserType}" escapeXml="false"/></td>
                         </tr>
                         
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Save</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
     <script type="text/javascript">
     	var ACTION_MODE = "ADD";
     </script>    
</body>
</html>
