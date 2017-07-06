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

<span style="color: #004080; font-size: small;font-weight: bold;">죄송합니다. 요청하신 서비스를 처리할 수 없습니다 - <%=ed.getStatusCode()%> </span>