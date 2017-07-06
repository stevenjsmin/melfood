<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>
<%@ page import="melfood.framework.auth.SessionUserInfo" %>
<%@ page import="melfood.framework.system.BeanHelper" %>
<%@ page import="melfood.framework.role.Role"%>
<%@ page import="melfood.framework.user.User"%>
<%@ page import="melfood.framework.auth.AuthService"%>
<%@ page import="melfood.framework.auth.AuthServiceImpl"%>

<%
     AuthService userService = new AuthServiceImpl();
	 SessionUserInfo sessionUser = BeanHelper.getSessionUser();
     // Role userRole = BeanHelper.getSessionUser().getSessionRole();
     // User user = BeanHelper.getUserFromSession();
     // User user = sessionUser.getUser();
     pageContext.setAttribute("sessionUser", sessionUser);
%>
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		url : "/common/getNumberOfCartItems.yum",
		success : callbackSetNumberOfCartItems
	});
	
    function callbackSetNumberOfCartItems(data) {
    	var cnt = data.numberOfCartItems;
    	if(cnt != null && cnt != '' && cnt > 0){
	    	$("#markNumberOfProductOnCartForHeader").html('<span style="font-size: 9px;color: #B7141C;font-weight: bold;">(+)</span>');
	    	$("#markNumberOfProductOnCartFor").html('<span style="font-size: 9px;color: #B7141C;font-weight: bold;">( ' + cnt + ' )</span>');
    	}
   }	
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
     function pressEnter() {
          if (event.keyCode == 13) {
               login();
          }
     }
 	function login() {
		var userId = $('#userId').val();
		var password = $('#password').val();

		if (userId.length == 0 || password.length == 0) {
			warningPopup("사용자 ID와 비밀번호를 확인해 주세요");
			return false;
		} else {
			$.ajax({
				url : "/common/auth/login.yum",
				data : {
					userId : userId,
					password : password
				},
				success : callbackLogin
			});
		}
	}

	function callbackLogin(data) {
		if (data.status == 'SUCCESS') {
			document.location.href = "/common/auth/welcome.yum";
		} else if (data.status == 'CHANGE_PWD') {
			document.location.href = "/common/mypage/passwordChange.yum";
		} else {
			warningPopup(data.message);
		}
	}
	function logout() {
		document.location.href = "/common/auth/logout.yum";
	}    
	function goHome() {
		document.location.href = "/";
	}    
	function joinMember() {
		document.location.href = "/guest/joinmember/join.yum";
	}    
	function findPassword() {
		document.location.href = "/guest/joinmember/findPassword.yum";
	}    
	function goMyPage(div) {
		if(div == 'ADMIN') {
			// document.location.href = "/admin/mypage/Main.yum";
			document.location.href = "/customer/mypage/Main.yum";
		} else if(div == 'SELLER') {
			// document.location.href = "/seller/mypage/Main.yum";
			document.location.href = "/customer/mypage/Main.yum";
		} else if(div == 'CUSTOMER') {
			document.location.href = "/customer/mypage/Main.yum";
		}
	}
	function goPayment(){
		document.location.href = "/cart/mycart.yum";
	}	
</script>

<div class="row" id="home">
  <div class="col-md-2"></div>
  <div class="col-md-3"><a href="javascript:goHome();"><img src="/resources/image/logo_melfood.png" style="height: 40px;"></a></div>
  <div class="col-md-7" style="vertical-align: middle;">

     <div style="height: 50px;padding-right: 0px;">
       <% if(sessionUser != null) {  %>
               <table align="right" style="height: 100%;">
                    <tr>
                         <td><span style="color: #007900;font-weight: bold;"><%=sessionUser.getUser().getUserName() %> </span><span style="color: #bfbfbf;">/ <%=sessionUser.getSessionRole().getRoleName() %></span> 님 안녕하세요? &nbsp;&nbsp;&nbsp;</td>
                         <td>
                              <a href="javascript:logout();">로그아웃</a>  &nbsp;|&nbsp;
                              <a href="javascript:goPayment();">결재 <span id="markNumberOfProductOnCartForHeader"></span></a>  &nbsp;|&nbsp;
                              <a href="javascript:goMyPage('${sessionUser.sessionRole.roleId}');">My 푸드</a>
                         </td>
                    </tr>
               </table>
       <%  } else { %>
               <table align="right" style="height: 100%;">
                    <tr>
                         <td><input type="text" class="form-control" id="userId" name="userId" placeholder="ID (Mobile 번호)" onkeydown="pressEnter();"></td>
                         <td style="padding-left: 5px;"><input type="password" class="form-control" id="password" name="password" placeholder="Password" onkeydown="pressEnter();"></td>
                         <td>&nbsp;<a href="javascript:login();"><img src="/resources/image/login.jpg" style="height: 45px;"></a></td>
                         <td style="width: 15px;">&nbsp;</td>
                         <td><a href="javascript:joinMember();">회원가입</a> | <a href="javascript:findPassword();">비밀번호찾기</a></td>
                    </tr>
               </table>
       <%  } %>




     </div>
  </div>
</div>