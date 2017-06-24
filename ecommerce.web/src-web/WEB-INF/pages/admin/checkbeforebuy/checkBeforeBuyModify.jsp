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
    $("#contents").kendoEditor({
        tools :["bold","italic","underline","justifyLeft","justifyCenter","justifyRight","insertUnorderedList","insertOrderedList","createLink","unlink","insertImage","createTable","formatting","fontSize","foreColor"],
        messages: {fontSizeInherit: "Default"},
  		encoded: false
	});
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
                              <td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr> 
                         <tr>
                              <td class="label"><span class="required">* </span>Subject :</td>
                              <td class="value" colspan="3"><input class="form-control" type="text" id="subject" name="subject" value='${checkBeforeBuy.subject }' placeholder="Subject" maxlength="30"/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>기본 구매전확인사항</td>
                              <td class="value"><c:out value="${cbxIsDefault}" escapeXml="false"/></td>
                              <td class="label"><span class="required">* </span>승인상태</td>
                              <td class="value"><c:out value="${cbxConfirmStatus}" escapeXml="false"/></td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>                                                                           
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;"><span class="required">* </span>Contents</td>
                              <td class="value" colspan="3"><textarea class="form-control" rows="3" id="contents" name="contents">${checkBeforeBuy.contents }</textarea></td>
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
          var ID = "${checkBeforeBuy.id}";
     </script>     
</body>
</html>