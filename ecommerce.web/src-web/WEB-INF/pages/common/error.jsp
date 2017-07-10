<%@page isErrorPage="true"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<style>
    /* centered columns styles */
    .col-centered {
        display:inline-block;
        float:none;
    }
</style>


<%
    ErrorData ed = null;
    try {
        ed = pageContext.getErrorData();
    } catch (NullPointerException ne) {
        // If the error page was accessed directly, a NullPointerException
        // is thrown at (PageContext.java:514).
        // Catch and ignore it... it effectively means we can't use the ErrorData
    }
%>


<div class="row" style="margin-top: 30px;text-align: center;">
    <div class="col-sm-6 col-centered">

        <div class="panel panel-info">
            <div class="panel-heading" style="padding-top: 10px;padding-bottom: 10px;">
                <span style="color: #004080; font-size: small;font-weight: bold;">Ooops.... The service you requested can not be processed properly</span>
            </div>
            <div class="panel-body" style="padding-top: 20px; padding-bottom: 30px;background-color: #F4F5F5;padding-left: 20px;padding-right: 20px;">
                <table style="width: 100%;color: #514747;" align="center">
                    <tr style="height: 30px;vertical-align: middle;border-bottom: solid 1px #C3C5C8;">
                        <td style="width: 90px;text-align: right;">CODE</td>
                        <td style="width: 30px;text-align: center;font-weight: bold;">:</td>
                        <td style="font-weight: bold;color: #0080C5;"><%=ed.getStatusCode()%></td>
                    </tr>
                    <tr style="height: 30px;">
                        <td style="text-align: right;">REQUEST URL</td>
                        <td style="width: 30px;text-align: center;font-weight: bold;">:</td>
                        <td style="color: #0080C5;"><%=ed.getRequestURI() %>?${queryString}</td>
                    </tr>
                    <!--
                    <tr>
                        <td style="width: 100px;text-align: right;vertical-align: top;padding-top: 5px;">오류내용</td>
                        <td style="width: 30px;text-align: center;vertical-align: top;padding-top: 5px;font-weight: bold;">:</td>
                        <td style="vertical-align: top;padding-top: 5px;color: #0080C5;"><%=exception.getMessage()%></td>
                    </tr> -->
                </table>
            </div>
        </div>

    </div>
</div>


<div class="row" style="margin-top: 5px;margin-bottom: 30px;text-align: center;">
    <div class="col-sm-5 col-centered">
        <table style="width: 100%;">
            <tr style="height: 40px;"><td style="color: #464646;"><b>INQUIRY :: </b> <c:out value="${inChargeOfPerson}" /></td></tr>
            <!--
            <tr>
                <td style="font-size: small;text-align: right;">
                    <a href="javascript:reportError();" style="color: #C3C5C8;">오류내용을 보고해 주세요</a>
                </td>
            </tr> -->
            <tr>
                <td style="text-align: right;"><font style="font-size: 13px;">
                    <a href="/" class="alert-link"><i class="fa fa-home fa-lg" aria-hidden="true" style="color: #465876;"></i></a> &nbsp;&nbsp;
                    <a href="javascript:history.back()"><i class="fa fa-step-backward" aria-hidden="true" style="color: #465876;"></i></a> &nbsp;&nbsp;
                    <a href="javascript:window.close()"><i class="fa fa-times fa-lg" aria-hidden="true" style="color: #465876;"></i></a></font></td>
            </tr>
        </table>
    </div>
</div>




<script type="text/javascript">
   function reportError(){
	   
       var errorCode = "<%=ed.getStatusCode()%>";
       var errorUrl = "<%=ed.getRequestURI() %>?${queryString}";
       var errorMessage = "<%=exception.getMessage()%>";
       var errorTime = '<c:out value="${errorTime}"/>';

       progress(true);
        $.ajax({
            url     : "/common/ReportError.yum",
            data    : {
            	errorCode : errorCode,
            	errorUrl : errorUrl,
            	errorMessage : errorMessage,
            	errorTime : errorTime
            },
            success : callbackReportError
        }); 
    }
   function callbackReportError(data) {
       var message = data.result;

       progress(false);

       if(message == "OK"){
           infoPopup('Successfully reported. Thanks so much');
       } else {
           warningPopup(data.resultMessage);
       }
   }   
</script>

