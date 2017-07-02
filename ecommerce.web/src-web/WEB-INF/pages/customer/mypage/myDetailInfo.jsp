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
</head>

<body>
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
                              <td class="label">닉네임(Nickname) </td>
                              <td class="value">${user.userName}</td>
                              <td class="label">실명 </td>
                              <td class="value">${user.userNameReal}</td>
                         </tr>
                         <tr>
                              <td class="label">DOB </td>
                              <td class="value">${user.dob}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         <tr>
                              <td class="label">Email </td>
                              <td class="value">${user.email}</td>
                              <td class="label">Tel</td>
                              <td class="value">${user.telephone}</td>
                         </tr>
                         <tr>
                              <td class="label">Address </td>
                              <td class="value" colspan="3">${user.addressStreet} ${user.addressSuburb} ${user.addressState} ${user.addressPostcode}</td>
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