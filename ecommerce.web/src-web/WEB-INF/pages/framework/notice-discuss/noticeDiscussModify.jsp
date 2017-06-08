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
             $("#contents").kendoEditor({
                 encoded: false
             });
         }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
                         <td class="value" colspan="3"><input class="form-control" type="text" id="subject" name="subject" value='${noticeDiscuss.subject}'/></td>
                    </tr>
                    <tr>
                         <td class="label"><span class="required">* </span>From :</td>
                         <td class="value">
                              <table style="width: 100%;">
                                   <tr>
                                        <td><input class="form-control" type="text" id="writeFromLabel" name="writeFromLabel" value='${writeFromLabel}' style="width: 200px;background-color: #F1F1F1;" maxlength="30" /></td>
                                        <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="findUserPopup('writeFrom', 'writeFromLabel', 'yes')"></td>
                                   </tr>
                              </table>
                              <input type="hidden" id="writeFrom" name="writeFrom" value='${noticeDiscuss.writeFrom}'/>
                         </td>
                         <td class="label"><span class="required">* </span>To :</td>
                         <td class="value">
                              <table>
                                   <tr>
                                        <td><input class="form-control" type="text" id="writeToLabel" name="writeFromLabel" value='${writeToLabel}' style="width: 200px;background-color: #F1F1F1;" maxlength="30" readonly/></td>
                                        <td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="findUserPopup('writeTo', 'writeToLabel', 'yes')"></td>
                                   </tr>
                              </table>
                              <input type="hidden" id="writeTo" name="writeTo" value='${noticeDiscuss.writeTo}'/>
                         </td>
                    </tr>
                    <tr><td colspan="4">&nbsp;</td></tr>
                    <tr>
                         <td class="label"><span class="required">* </span>For All Seller :</td>
                         <td class="value">${cbxIsForAllSeller}</td>
                         <td class="label"><span class="required">* </span>For All Customer :</td>
                         <td class="value">${cbxIsForAllCustomer}</td>
                    </tr>
                    <tr>
                         <td class="label"><span class="required">* </span>Is Notice :</td>
                         <td class="value">${cbxIsForNotice}</td>
                         <td></td>
                         <td></td>
                    </tr>
                    <tr><td colspan="4">&nbsp;</td></tr>
                    <tr>
                         <td class="label" style="vertical-align: top;padding-top: 5px;"><span class="required">* </span>Contents</td>
                         <td class="value" colspan="3"><textarea class="form-control" rows="3" id="contents" name="contents">${noticeDiscuss.contents}</textarea></td>
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
                              <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                              <a href="javascript:save();" class="btn btn-primary">Modify</a>
                         </td>
                    </tr>
               </table>
          </td>
     </tr>

</table>

<script type="text/javascript">
    var ID = "<c:out value="${noticeDiscuss.id}"/>";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>