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
<script type="text/javascript">
    $(document).ready(function () {
        $("#searchDateFrom").kendoDatePicker({
            format: "yyyy-MM-dd",
            start: "year"
        });
        var datepicker1 = $("#searchDateFrom").data("kendoDatePicker");
        $("#searchDateFrom").click(function() {
            datepicker1.open();
        });

        $("#searchDateTo").kendoDatePicker({
            format: "yyyy-MM-dd",
            start: "year"
        });
        var datepicker2 = $("#searchDateTo").data("kendoDatePicker");
        $("#searchDateTo").click(function() {
            datepicker2.open();
        });
    }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

</script>

<script type="text/javascript">
    function goList(){
        var searchDateFrom = $("#searchDateFrom").val();
        var searchDateTo = $("#searchDateTo").val();
        document.location.href = "/customer/mypage/myCommunication.yum?searchDateFrom=" + searchDateFrom + "&searchDateTo=" + searchDateTo;
    }
</script>

<style>
     .img-circle {
          border-radius: 50%;
     }
     .rouned-table {
          margin: 0px;
          border-collapse: separate;
          border-spacing: 0px;

     }
     .rouned-td {
          border-radius: 5px;
          background-color: #369;
          color: white;
          border: 5px solid #C8C8C8;
     }​
</style>
</head>




<body>
     <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
     <!-- Search -->
     <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
     <div class="well">
          <table class="search_table">
               <tr>
                    <td class="label">Start Date :  </td>
                    <td class="value"><input id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}"></input></td>
                    <td class="label">End Date :  </td>
                    <td class="value_end"><input id="searchDateTo" name="searchDateTo" value="${searchDateTo}"></input></td>
                    <td class="find"><button type="button" class="btn btn-info" onclick="goList();">Search</button></td>
               </tr>
          </table>
     </div>


     <div class="row">
          <div class="col-md-12" style="padding: 0px;">
               <!-- 대화내용 -->
               <c:forEach var="entry" items="${communicationList}" varStatus="count" begin="0">
                    <c:choose>
                         <c:when test="${sessionUserId == entry.writeFrom}">
                              <table style="width: 600px;">
                                   <tr>
                                        <td>
                                             <div class="chat-dialogbox">
                                                  <div class="chat-body" style="max-width: 500px;">
                                                       <span class="chat-tip chat-tip-right"></span>
                                                       <div class="chat-message">
                                                            <table style="width: 100%;">
                                                                 <tr>
                                                                      <td style="font-size: 13px;color: #1E1E1E;">
                                                                           <c:choose>
                                                                                <c:when test="${entry.category == 'QNA'}">[QnA]</c:when>
                                                                           </c:choose>
                                                                           ${entry.contents}
                                                                      </td>
                                                                 </tr>
                                                                 <tr>
                                                                      <td style="text-align: left;font-size: 10px;padding-top: 5px;">To :
                                                                           <c:choose>
                                                                                <c:when test="${entry.writeToName == '' || entry.writeToName == null}">_(삭제된사용자)</c:when>
                                                                                <c:otherwise>${entry.writeToName}</c:otherwise>
                                                                           </c:choose>
                                                                      </td>
                                                                 </tr>
                                                                 <tr><td style="text-align: left;"><span style="color: #AEACAF; font-size: 10px;">${entry.createDatetime}</span></td></tr>
                                                            </table>
                                                       </div>
                                                  </div>
                                             </div>
                                        </td>
                                        <td style="width: 150px;">&nbsp;</td>
                                   </tr>
                              </table>
                         </c:when>

                         <c:otherwise>
                              <table style="width: 600px;">
                                   <tr>
                                        <td style="width: 150px;">&nbsp;</td>
                                        <td>
                                             <div class="chat-dialogbox">
                                                  <div class="chat-body" style="background-color: #F5F5F5;max-width: 500px;">
                                                       <span class="chat-tip chat-tip-left"></span>
                                                       <div class="chat-message">
                                                            <table style="width: 100%;">
                                                                 <tr><td style="font-size: 13px;color: #727272;">${entry.contents}</td></tr>
                                                                 <tr>
                                                                      <td style="text-align: right;font-size: 10px;">From :
                                                                           <c:choose>
                                                                                <c:when test="${entry.writeFromName == '' || entry.writeFromName == null}">_(삭제된사용자)</c:when>
                                                                                <c:otherwise>${entry.writeFromName}</c:otherwise>
                                                                           </c:choose>
                                                                      </td>
                                                                 </tr>
                                                                 <tr><td style="text-align: right;"><span style="color: #AEACAF; font-size: 10px;"><fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" />${entry.createDatetime}</span></td></tr>
                                                            </table>
                                                       </div>
                                                  </div>
                                             </div>
                                        </td>
                                   </tr>
                              </table>



                         </c:otherwise>
                    </c:choose>
               </c:forEach>
          </div>
     </div>







     <br />
     <br />
     <br />
     
     <script type="text/javascript">
     </script>
</body>
</html>