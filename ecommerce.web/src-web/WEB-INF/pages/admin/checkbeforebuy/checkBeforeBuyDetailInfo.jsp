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
<script src="/resources/js/melfood/admin/checkbeforebuymanager.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                              <td class="label"><span class="required">* </span>상품판매자</td>
                              <td class="value"><c:out value="${checkBeforeBuy.sellerName}"/></td>
                              <td></td>
                              <td></td>
                         </tr>                            
                         <tr>
                              <td class="label">Subject :</td>
                              <td class="value" colspan="3"><span style="font-weight: bold;color: #e37200;">${checkBeforeBuy.subject}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">기본 구매전확인사항</td>
                              <td class="value">
                              	<c:choose>
                              		<c:when test="${checkBeforeBuy.isDefault == 'Y' }">기본값</c:when>
                              		<c:otherwise>-</c:otherwise>
                              	</c:choose>
                              </td>
                              <td class="label">승인상태</td>
                              <td class="value">
                              	<c:choose>
                              		<c:when test="${checkBeforeBuy.confirmStatus == 'ON_DRAFT' }">작성중</c:when>
                              		<c:when test="${checkBeforeBuy.confirmStatus == 'CONFIRMED' }">승인완료</c:when>
                              		<c:otherwise>-</c:otherwise>
                              	</c:choose>
                              </td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;">Contents</td>
                              <td class="value" colspan="3"><c:out value="${checkBeforeBuy.contents}" escapeXml="false"/></td>
                              <td></td>
                         </tr>                         
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td style="padding-bottom: 20px;">
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify('${checkBeforeBuy.id}');" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
     <script type="text/javascript">
          var ACTION_MODE = "MODIFY";
     </script>     
</body>
</html>