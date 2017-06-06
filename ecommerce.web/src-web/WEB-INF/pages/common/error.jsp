<%@page isErrorPage="true"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="java.io.*"%>

<div align="center">
    <table style="width: 50%;">
        <tr>
            <td style="vertical-align: middle;">
                <div class="alert alert-info" style="width: 100%;">
                    <font style="color: #004080; font-size: medium;"><b>Sorry ! Your request page can't be serviced</b></font><br><br>
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
	                                out.println("<br/>ErrorCode: <span id='errorCode'>" + ed.getStatusCode() + "</span>"); %>
	                                
	                                <br/>URL: <span id='errorUrl'><% out.print(ed.getRequestURI() + "?"); %><c:out value="${queryString}" /></span>
	                                <%
	                                out.println("<br>");
	                                out.println("<br>");
	                                if(exception != null){
	                                	
		                                out.println("<font style=\"font-size: small; color: #0000FF\"><span id='errorMessage'>" + exception.getMessage() +"</span></font>");
		                                out.println("<br>");
		                                out.println("<br>");
		                                
		                                out.println("<div style=\"height: 200px;overflow-y:scroll \">");
		                                out.println("<pre style=\"background-color: #D9EDF7;border-color: #D9EDF7; \"><font style=\"font-size: x-small;\"><span id='errorDetailMessage'>");
		                                StringWriter stringWriter = new StringWriter();
		                                PrintWriter printWriter = new PrintWriter(stringWriter);
		                                exception.printStackTrace(printWriter);
		                                out.println(stringWriter);
		                                printWriter.close();
		                                stringWriter.close();
		                                out.println("</span></font></pre>");
		                                out.println("</div>");
		                                out.println("<br>");
		                           } else {
		                               out.println("<br/>Error Message: <div id='errorMessage'>Unknown</div>");
		                               out.println("<br/>Error Detail Message: <div id='errorDetailMessage'>Unknown</div>");
		                           }
                                // Error handled successfully, set a flag
                                handled = true;
                            }
                        } else {
                            out.println("<br/>ErrorCode: <div id='errorCode'>Unknown</div>");
                            out.println("<br/>URL: <div id='errorMessage'>Unknown</div>");
                            out.println("<br/>Error Message: <div id='errorMessage'>Unknown</div>");
                            out.println("<br/>Error Detail Message: <div id='errorDetailMessage'>Unknown</div>");
                            out.println("<br>");
                            out.println("<br>");
                        }

                        // Check if the error was handled
                        if (!handled) {
                            out.println("<p>");
                            out.println("No information about this error was available.");
                        }
                    %>
                    </font>
                </div>
                <br>
            </td>
        </tr>
        <tr>
            <td><font color="#004080" style="font-size: 12px;"><b>Enquiry to </b> <c:out value="${in_charge_of_person}" /></font> <div style="font-size: small;text-align: right;color: #8D8D8D"> Please report by <span class="glyphicon glyphicon-envelope" onclick="reportError()" style="cursor: pointer;"></span></div>&nbsp;&nbsp;<br>
            <br><font style="font-size: 13px;"><a href="/" class="alert-link">Home</a> | <a href="javascript:history.back()">Back</a> | <a href="javascript:window.close()">Close</a></font></td>
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

