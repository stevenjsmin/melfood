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
<script src="/resources/js/melfood/framework/noticedisscussmanager.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<body>
<div id="errorWindow"></div>
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
                              <td class="label">Subject :</td>
                              <td class="value" colspan="3"><span style="color: #dc0a5e;font-weight: bold;">${noticeDiscuss.subject}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">From :</td>
                              <td class="value">${noticeDiscuss.writeFrom}</td>
                              <td class="label">To :</td>
                              <td class="value">${noticeDiscuss.writeTo}</td>
                         </tr>
                         <tr><td colspan="4">&nbsp;</td></tr>
                         <tr>
                              <td class="label">For Seller :</td>
                              <td class="value">${noticeDiscuss.isForAllSeller}</td>
                              <td class="label">For Customer :</td>
                              <td class="value">${noticeDiscuss.isForAllCustomer}</td>
                         </tr>
                         <tr>
                              <td class="label">Is Notice :</td>
                              <td class="value">${noticeDiscuss.isForNotice}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr><td colspan="4">&nbsp;</td></tr>
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;">Contents</td>
                              <td class="value" colspan="3">${noticeDiscuss.contents}</td>
                              <td></td>
                         </tr>

                         <tr><td class="metavalue" colspan="4">Creator : ${noticeDiscuss.creator}, Create Time : ${noticeDiscuss.createDatetime}, Modify Time : ${noticeDiscuss.modifyDatetime}</td></tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:deleteInfo('${noticeDiscuss.id}');" class="btn btn-danger">Delete</a>
                                   &nbsp;&nbsp;&nbsp;
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify('${noticeDiscuss.id}');" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>

     <script type="text/javascript">
         var ID = "<c:out value="${noticeDiscuss.id}"/>";
     </script>

</body>
</html>