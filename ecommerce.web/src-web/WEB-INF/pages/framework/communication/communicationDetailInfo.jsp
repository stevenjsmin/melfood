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
<script src="/resources/js/melfood/framework/communicationmanager.js?ver=<%=Ctx.releaseVersion%>"></script>
     <script type="text/javascript">
         $(document).ready(function () {
         }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     </script>

     <script type="text/javascript">
         function sendMessagePopup(receiverUserId){

             $("#sendMessagePopup").kendoWindow({
                 content: "/framework/communicationmanager/sendMessageForm.yum?receiverUserId=" + receiverUserId,
                 actions: [ "Minimize", "Maximize","Close" ],
                 title: "SEND MESSAGE",
                 modal: true,
                 iframe: true
             });

             var popup_dialog = $("#sendMessagePopup").data("kendoWindow");
             popup_dialog.setOptions({
                 pinned: true,
                 width: 650,height: 350,
                 open: function (e) {
                     this.wrapper.css({ top: 100 });
                 }
             });
             popup_dialog.center().open();

         }
         function closesendMessagePopup() {
             var win_dialog = $("#sendMessagePopup").data("kendoWindow");
             win_dialog.close();
         }
     </script>
</head>

<body>
	<div id="sendMessagePopup"></div>
		
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
                              <td class="value" colspan="3"><span style="font-weight: bold;color: #e37200;">${communication.subject}</span></td>
                         </tr>
                         <tr>
                              <td class="label">Category :</td>
                              <td class="value" colspan="3">
                                   <span style="font-weight: bold;color: #006F3C;">
                                        <c:choose>
                                             <c:when test="${communication.category == 'CHAT'}">대화</c:when>
                                             <c:when test="${communication.category == 'NOTICE'}">공지사항</c:when>
                                             <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                   </span>
                              </td>
                         </tr>

                         <tr>
                              <td class="label">From</td>
                              <td class="value">
                                   <c:choose>
                                        <c:when test="${communication.writeFrom != null}">
                                             ${communication.writeFrom} / ${communication.writeFromName}
                                             <a href="javascript:sendMessagePopup('${communication.writeFrom}');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                        </c:when>
                                        <c:otherwise>${communication.writeFrom} / ${communication.writeFromName}</c:otherwise>
                                   </c:choose>
                              </td>
                              <td class="label">To</td>
                              <td class="value">
                                   <c:choose>
                                        <c:when test="${communication.writeTo != null}">
                                             ${communication.writeTo} / ${communication.writeToName}
                                             <a href="javascript:sendMessagePopup('${communication.writeTo}');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                        </c:when>
                                        <c:otherwise>${communication.writeTo} / ${communication.writeToName}</c:otherwise>
                                   </c:choose>
                              </td>
                         </tr>
                         <tr>
                              <td class="label">For all seller</td>
                              <td class="value">
                              	<c:choose>
                              		<c:when test="${communication.isForAllSeller == 'Y'}">네</c:when>
                              		<c:otherwise>아니오</c:otherwise>
                              	</c:choose>
                              </td>
                              <td class="label">For all customer</td>
                              <td class="value">
                              	<c:choose>
                              		<c:when test="${communication.isForAllCustomer == 'Y'}">네</c:when>
                              		<c:otherwise>아니오</c:otherwise>
                              	</c:choose>
                              
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;">Contents</td>
                              <td class="value" colspan="3"><c:out value="${communication.contents}" escapeXml="false"/></td>
                              <td></td>
                         </tr>                         
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goList('${communication.category}');" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify('${communication.id}');" class="btn btn-primary">Modify</a>
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