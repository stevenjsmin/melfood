<%@page isErrorPage="true"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="icon" type="image/png" href="/resources/image/favicon.png" />
<%
    ErrorData ed = null;
    try {
        ed = pageContext.getErrorData();
    } catch (NullPointerException ne) {
    }
%>

<table style="color: #3c3c3c;font-size: 15px;">
    <tr>
        <td style="vertical-align: middle;">죄송합니다. 요청하신 서비스를 처리할 수 없습니다</td>
        <td style="width: 20px;text-align: center;">|</td>
        <td style="vertical-align: middle;"><%=ed.getStatusCode()%></td>
        <td style="width: 20px;text-align: center;">|</td>
        <td style="vertical-align: middle;"><a href="/" style="color: #3c3c3c;"><img src="/resources/css/images/gic/ic_home_black_18dp_1x.png"></a></td>
    </tr>
</table>