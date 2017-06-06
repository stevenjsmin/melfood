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
<script src="/resources/js/melfood/framework/sysconfigmanager.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                              <col width="200px" />
                              <col width="400px" />
                         </colgroup>     
                         <tr>
                              <td class="label">Stage</td>
                              <td class="value"><span style="color: #dc0a5e;font-weight: bold;">${sysConfig.stage}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Key</td>
                              <td class="value"><span style="color: #d5d500;font-weight: bold;">${sysConfig.key}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Value</td>
                              <td class="value"><span style="color: #0073aa;font-weight: bold;">${sysConfig.value}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Description</td>
                              <td class="value">${sysConfig.description}</td>
                         </tr>
                         <tr>
                              <td class="label">Use Y/N :</td>
                              <td class="value">${sysConfig.useYnLabel}</td>
                         </tr>
                         <tr><td class="metavalue" colspan="4">Creator : ${sysConfig.creator}, Create Time : ${sysConfig.createDatetime}, Modify Time : ${sysConfig.modifyDatetime}</td></tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:deleteInfo('${sysConfig.stage}', '${sysConfig.key}');" class="btn btn-danger">Delete</a>
                                   &nbsp;&nbsp;&nbsp;
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify();" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
     <script type="text/javascript">
     	var _STAGE = "<c:out value="${sysConfig.stage}"/>";
     	var _KEY = "<c:out value="${sysConfig.key}"/>";
     </script>    
</body>
</html>