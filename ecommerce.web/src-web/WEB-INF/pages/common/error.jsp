<%@page isErrorPage="true"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<script type="text/javascript">
    var status = "less";

    function toggleText()
    {
        if (status == "less") {
            $("#textArea").show();
            document.getElementById("toggleButton").innerText = "오류내용 상세히보기 열기..";
            status = "more";
        } else if (status == "more") {
            $("#textArea").hide();
            document.getElementById("toggleButton").innerText = "오류내용 상세히보기 닫기..";
            status = "less"
        }
    }
</script>


<div align="center">
    <table style="width: 50%;">
        <tr>
            <td style="vertical-align: middle;">
                <div class="alert alert-info" style="width: 100%;">
                    <br/>
                    <br/>
                    <font style="color: #004080; font-size: small;">
                            <b>죄송합니다. 요청하신 서비스를 수행하는 도중 문제가 발생하였습니다.</b>
                    </font><br><br>
                    <font style="font-size: x-small; color: #004080;">
                    <%
                        boolean handled = false; // Set to true after handling the error

                        // Get the PageContext
                        if (pageContext != null) {

                            // Get the ErrorData
                            ErrorData ed = null;
                            try {
                                ed = pageContext.getErrorData();
                            } catch (NullPointerException ne) {
                                // If the error page was accessed directly, a NullPointerException
                                // is thrown at (PageContext.java:514).
                                // Catch and ignore it... it effectively means we can't use the ErrorData
                            }

                            // Display error details for the user
                            if (ed != null) {
	                                // Output details about the HTTP error
	                                // (this should show error code 404, and the name of the missing page)
	                                out.println("<br/>오류코드: <span id='errorCode' style='color: #2185E8;'>" + ed.getStatusCode() + "</span>"); %>
	                                
	                                <br/>URL: <span id='errorUrl' style='color: #2185E8;'><% out.print(ed.getRequestURI() + "?"); %><c:out value="${queryString}" /></span>
	                                <%
	                                out.println("<br>");
	                                out.println("<br>");
	                                out.println("<br>");
	                                out.println("<a id='toggleButton' onclick='toggleText();' href='javascript:void(0);'>오류내용 상세히보기 닫기..</a>");
	                                out.println("<div id='textArea' style='display: none;width: 500px;'>");
	                                if(exception != null){
	                                	
		                                out.println("<font style=\"font-size: small; color: #0000FF\"><span id='errorMessage'>" + StringUtils.abbreviate(exception.getMessage().toLowerCase().replaceAll("(\r\n|\n)", "<br />"),150) +"</span></font>");
                                        /**
		                                out.println("<div style='height: 200px;overflow-y:scroll;width:160%'>");
		                                out.println("<font style='font-size: x-small;'><span id='errorDetailMessage'>");
		                                StringWriter stringWriter = new StringWriter();
		                                PrintWriter printWriter = new PrintWriter(stringWriter);
		                                exception.printStackTrace(printWriter);
		                                out.println(stringWriter);
		                                printWriter.close();
		                                stringWriter.close();
		                                out.println("</span></font>");
		                                out.println("</div>");
		                                out.println("<br>");
                                         */
		                           } else {
		                               out.println("<br/>오류 메시지: <span id='errorMessage'>Unknown</span>");
		                               out.println("<br/>상세 메시지: <span id='errorDetailMessage'>Unknown</span>");
		                           }
                                   out.println("</div>");
                                   out.println("<br/>");
                                   out.println("<br/>");

                                // Error handled successfully, set a flag
                                handled = true;
                            }
                        } else {
                            out.println("<br/>오류코드: <span id='errorCode' style='color: #2185E8;font-weight: bold;'>Unknown</span>");
                            out.println("<br/>URL: <span id='errorMessage'>Unknown</span>");
                            out.println("<br/>오류 메시지: <span id='errorMessage'>Unknown</span>");
                            out.println("<br/>상세 메시지: <span id='errorDetailMessage'>Unknown</span>");
                            out.println("<br>");
                            out.println("<br>");
                        }

                        // Check if the error was handled
                        if (!handled) {
                            out.println("<p>");
                            out.println("발생한 오류에대해서 더이상 제공할 오류 메시지가 존재하지 않습니다.");
                        }
                    %>
                    </font>
                </div>
                <br>
            </td>
        </tr>
        <tr>
            <td>
                <font color="#004080" style="font-size: 12px;">
                    <b>문의 </b> <c:out value="${in_charge_of_person}" />
                </font>
                <br/><br/>
                <div style="font-size: small;text-align: right;color: #8D8D8D"> 다음 이메일 아이콘을 선택하여 오류내용을 보고해주세요.
                    <span class="glyphicon glyphicon-envelope" onclick="reportError()" style="cursor: pointer;"></span>
                </div>&nbsp;&nbsp;<br/>

                <font style="font-size: 13px;"><a href="/" class="alert-link">홈</a> | <a href="javascript:history.back()">뒤로가기</a> | <a href="javascript:window.close()">닫기</a></font>
            </td>
        </tr>
    </table>
    <br> <br>
</div>

<script type="text/javascript">
   function reportError(){
	   
       var errorCode = "";
       var errorUrl = "";
       var errorMessage = "";
       var errorDetailMessage = "";
       var errorTime = '<c:out value="${errorTime}"/>';

       if($('#errorCode') != null) errorCode = $('#errorCode').get(0).innerText;
       if($('#errorUrl') != null) errorUrl = $('#errorUrl').get(0).innerText;
       if($('#errorMessage') != null)  errorMessage = $('#errorMessage').get(0).innerText;
       if($('#errorDetailMessage') != null) errorDetailMessage = $('#errorDetailMessage').get(0).innerText;
        
        $.ajax({
            url     : "/common/ReportError.yum",
            data    : {
            	errorCode : errorCode,
            	errorUrl : errorUrl,
            	errorMessage : errorMessage,
            	errorDetailMessage : errorDetailMessage,
            	errorTime : errorTime
            },
            success : callbackReportError
        }); 
    }
   function callbackReportError(data) {
       var message = data.result;
       
       if(message == "OK"){
    	    alert('Successfully reported. Thanks so much');
       } else {
            alert('Successfully reported.');
       }
   }   
</script>

