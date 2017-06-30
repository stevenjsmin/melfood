<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<div align="center">
	<table style="width: 50%;">
		<tr>
			<td style="vertical-align: middle;word-break:break-all">
				<div class="alert alert-info" style="width: 100%;">
					<b><font style="font-size: medium; color: #004080;font-size: 10px;">Session Expired</font></b><br>
					<br>
					<span style="color: #2185E8;font-weight: bold;">현재 요청하신 서비스/페이지는 로그인이 필요한 서비스입니다. 계속하시기 위해서는 먼저 <span style="color: #5974AB;font-weight: bold;">로그인</span>을 하셔야합니다.</span>
					<br>
					<br>
					<font style="font-size: small; color: #004080;"><div id="timer_div">You will be redirected to home page in 30 sec.</div></font>
				</div>
			</td>
		</tr>
		<tr>
            <td><font style="font-size: 13px;"><a href="/" class="alert-link">Home</a></font></td>
        </tr>
	</table>
	<br><br>
</div>
<script type="text/javascript">
	function redirect()	{
        setTimeout(5000);
	    window.location="/common/auth/welcome.yum";
	}
    
    var seconds_left = 30;
    var interval = setInterval(function() {
        document.getElementById('timer_div').innerHTML = 'You will be redirected to home page in ' +  (--seconds_left )  + " sec.";
        
        if (seconds_left <= 0)
        {
            document.getElementById('timer_div').innerHTML = 'Redirecting....';
            clearInterval(interval);
            redirect();
        }
    }, 1000);
    
    
</script>

