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
<script src="/resources/js/melfood/framework/noticediscussmanager.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("#contents").kendoEditor({
        tools :["bold","italic","underline","justifyLeft","justifyCenter","justifyRight","insertUnorderedList","insertOrderedList","createLink","unlink","insertImage","createTable","formatting","fontSize","foreColor"],
        messages: {fontSizeInherit: "Default"},
        encoded: false
	});
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
     function findUser(objectName) {
         $("#findUserPopup").kendoWindow({
               content: "/framework/noticedisscussmanager/findUserForm.yum?objectName=" + objectName,
               actions: [ "Minimize", "Maximize","Close" ],
               title: "Find User",
               modal: true,
               iframe: true
         });
         
         var popupwid_dialog = $("#findUserPopup").data("kendoWindow");
         popupwid_dialog.setOptions({
               width: 650,
               height: 550
             });
         popupwid_dialog.center();
         
         $("#findUserPopup").data("kendoWindow").open();
     }
	function closeFindUserWindow() {
		var win_dialog = $("#findUserPopup").data("kendoWindow");
		win_dialog.close();
	}     
</script>

<script type="text/javascript">
function clearWriteWhom(objName){
	if(objName == 'writeFrom'){
		$("#writeFromLabel").val('');
		$("#writeFrom").val('');
	} else if(objName == 'writeTo') {
		$("#writeToLabel").val('');
		$("#writeTo").val('');		
	}
}
</script>

</head>

<body>
	<div id="findUserPopup"></div>
		
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
                              <td class="label"><span class="required">* </span>Subject :</td>
                              <td class="value" colspan="3"><input class="form-control" type="text" id="subject" name="subject" value='${noticeDiscuss.subject}' placeholder="Subject" maxlength="30"/></td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>From</td>
                              <td class="value" style="padding-left: 3px;" colspan="2">
                                   <table style="width: 100%;">
                                   		<tr>
                                   			<td><input class="form-control" type="text" id="writeFromLabel" name="writeFromLabel" value='${noticeDiscuss.writeFromName}' disabled/></td>
                                   			<td>
                                   				<img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="findUser('writeFrom')">
                                   				<img src="/resources/image/delete.png" style="cursor: pointer;" onclick="clearWriteWhom('writeFrom')">
                                   			</td>
                                   		</tr>
                                   	</table>
                              </td>
                              <td><input type="hidden" id="writeFrom" name="writeFrom" value='${noticeDiscuss.writeFrom}'></input></td>
                         </tr>
                         <tr>
                              <td class="label">To</td>
                              <td class="value" style="padding-left: 3px;" colspan="2">
                                   <table style="width: 100%;">
                                   		<tr>
                                   			<td><input class="form-control" type="text" id="writeToLabel" name="writeToLabel" value='${noticeDiscuss.writeToName}' disabled/></td>
                                   			<td>
                                   				<img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="findUser('writeTo')">
                                   				<img src="/resources/image/delete.png" style="cursor: pointer;" onclick="clearWriteWhom('writeTo')">
                                   			</td>
                                   		</tr>
                                   	</table>
                              </td>
                              <td><input type="hidden" id="writeTo" name="writeTo" value='${noticeDiscuss.writeTo}'></input></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>For all seller</td>
                              <td class="value"><c:out value="${cbxIsForAllSeller}" escapeXml="false"/></td>
                              <td class="label"><span class="required">* </span>For all customer</td>
                              <td class="value"><c:out value="${cbxIsForAllCustomer}" escapeXml="false"/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Is for notice</td>
                              <td class="value"><c:out value="${cbxIsForNotice}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;"><span class="required">* </span>Contents</td>
                              <td class="value" colspan="3"><textarea class="form-control" rows="3" id="contents" name="contents">${noticeDiscuss.contents}</textarea></td>
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
                                   <a href="javascript:goDetailInfo('${noticeDiscuss.id}');" class="btn btn-info">&nbsp;&nbsp; Cancel &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Save</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
     <script type="text/javascript">
          var ACTION_MODE = "MODIFY";
          var ID = "${noticeDiscuss.id}";
     </script>     
</body>
</html>