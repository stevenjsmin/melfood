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
<script type="text/javascript">
$(document).ready(function() {
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<body>

     <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="250px" />
                              <col width="250px" />
                              <col width="250px" />
                              <col width="250px" />
                         </colgroup>

                         <tr>
                              <td class="label">Status :</td>
                              <td class="value" colspan="3">
                                   <span style="font-weight: bold;color: #e37200;">
                                     <c:choose>
                                          <c:when test="${qnA.qnaStatus == 'NOT_OPEN'}">처리 전</c:when>
                                          <c:when test="${qnA.qnaStatus == 'OPEN'}">처리 중</c:when>
                                          <c:when test="${qnA.qnaStatus == 'COMPLETE'}">처리 완료</c:when>
                                          <c:otherwise>-</c:otherwise>
                                     </c:choose>
                                   </span>
                              </td>
                         </tr>
                         <tr>
                              <td class="label">Mobile :</td>
                              <td class="value">${qnA.customerMobile}</td>
                              <td class="label">Email :</td>
                              <td class="value">${qnA.customerEmail}</td>
                         </tr>
                         <tr>
                              <td class="label">내용 :</td>
                              <td class="value" colspan="3">${qnA.customerQuestion}</td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr>
                              <td class="label">작성일 :</td>
                              <td class="value" colspan="3"><span style="color: #A2A4A4;">${qnA.createDatetime}</span></td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>



                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:parent.closeQnADetailInfoPopup();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
</body>
</html>