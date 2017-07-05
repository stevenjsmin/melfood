<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
%>

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
		if (data.status == 'SUCCESS' || data.status == 'CHANGE_PWD') {
			parent.closeLoginPopup();
			parent.location.reload();
		} else {
			warningPopup(data.message);
		}
	}
	
	function joinMember() {
		parent.closeLoginPopup();
		parent.document.location.href = "/guest/joinmember/join.yum";
	}    
	function findPassword() {
		parent.closeLoginPopup();
		parent.document.location.href = "/guest/joinmember/findPassword.yum";
	}  	
</script>

<div class="row">
  <div class="col-md-2"></div>
  <div class="col-md-4"><a href="javascript:goHome();"><img src="/resources/image/logo_melfood_2.png" style="height: 40px;"></a></div>
  <div class="col-md-6" style="vertical-align: middle;">
     <div style="height: 50px;">
       <% if(sessionUser != null) {  %>
               <table align="right" style="height: 100%;">
                    <tr>
                         <td style="font-weight: bold;color: #E26E15;">이미 로그인 되어있습니다.</td>
                    </tr>
               </table>
       <%  } else { %>
               <table align="right" style="height: 100%;">
                    <tr>
                         <td><input type="text" class="form-control" id="userId" name="userId" placeholder="ID (Mobile 번호)" onkeydown="pressEnter();"></td>
                         <td style="padding-left: 5px;"><input type="password" class="form-control" id="password" name="password" placeholder="Password" onkeydown="pressEnter();"></td>
                         <td>&nbsp;<a href="javascript:login();"><img src="/resources/image/login.jpg" style="height: 45px;"></a></td>
                    </tr>
                    <tr><td colspan="3">&nbsp;</td></tr>
                    <tr><td colspan="3" style="text-align: left;"><a href="javascript:joinMember();">회원가입</a> | <a href="javascript:findPassword();">비밀번호찾기</a></td></tr>
               </table>
       <%  } %>
     </div>
  </div>
</div>

<!-- 
<br/>
	<table class="action_button_table" width="100%">
	      <tr>
	           <td>
	                <a href="javascript:parent.closeLoginPopup();" class="btn btn-info">&nbsp;&nbsp; 닫기 &nbsp;&nbsp;</a>
	           </td>
	      </tr>
	 </table>
<br/>
-->