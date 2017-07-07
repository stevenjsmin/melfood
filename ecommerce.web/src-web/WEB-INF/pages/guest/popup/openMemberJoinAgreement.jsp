<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>

<div class="row">
    <div class="col-sm-8" style="padding-top: 10px;">

        <table style="color: #008600;">
            <tr>
                <td colspan="3" style="text-align: center;padding-bottom: 10px;"><img src="/resources/image/logo_melfood_2.png" style="width: 200px;"/></td>
            </tr>
            <tr style="height: 30px;">
                <td style="width: 40px;text-align: center;font-size: 15px;">하나</td>
                <td style="width: 10px;text-align: center;"></td>
                <td style="text-align: left;font-size: 15px;">가입하신 회원정보는 <b>멜푸드</b>이외 다른 목적으로 사용하지 않습니다.</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width: 40px;text-align: center;font-size: 15px;">둘</td>
                <td style="width: 10px;text-align: center;"></td>
                <td style="text-align: left;font-size: 15px;">가입하신 회원정보는 거래 당사자와 Mall관리자외 공유하지 않습니다.</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width: 40px;text-align: center;font-size: 15px;">셋</td>
                <td style="width: 10px;text-align: center;"></td>
                <td style="text-align: left;font-size: 15px;"><b>멜푸드</b>는 고객의 정보를 소중히 생각하겠습니다.</td>
            </tr>
        </table>
        <br/>
        <table class="action_button_table" width="100%">
            <tr>
                <td>
                    <a href="javascript:parent.closeMemberAgreementStmtWindow();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                </td>
            </tr>
        </table>
        <br/>

    </div>
</div>
</body>
</html>