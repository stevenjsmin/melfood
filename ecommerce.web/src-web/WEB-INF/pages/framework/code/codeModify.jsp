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
<script src="/resources/js/melfood/framework/codemanager.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                              <td class="label">Code :</td>
                              <td class="value" colspan="3"><span style="color: #804040;font-weight: bold;">${code.category}</span> | &nbsp;&nbsp;<span style="color: #c75c54;font-weight: bold;">${code.type}</span> |&nbsp;&nbsp; ${code.value}</td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>Label</td>
                              <td class="value"><input class="form-control" type="text" id="label" name="label" value='${code.label}'/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr><td colspan="4">&nbsp;</td></tr>
                         <tr>
                              <td class="label">Display order :</td>
                              <td class="value"><input type="text" id="displayOrder" name="displayOrder" value='${code.displayOrder}'/></td>
                              <td class="label">Use Y/N :</td>
                              <td class="value"><c:out value="${cbxUseYn}" escapeXml="false"/></td>
                         </tr>
                         <tr>
                              <td class="label">Description :</td>                             
                              <td class="value" colspan="3"><input class="form-control" type="text" id="description" name="description" value='${code.description}'/></td>
                         </tr>
                         <tr><td class="metavalue" colspan="4">Creator : ${code.creator}, Create Time : ${code.createDatetime}, Modify Time : ${code.modifyDatetime}</td></tr>
                    </table>
                    <input type="hidden" name="category" id="category" value="${code.category}"/>
                    <input type="hidden" name="type" id="type" value="${code.type}"/>
                    <input type="hidden" name="value" id="value" value="${code.value}"/>
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
          var CATEGORY = "<c:out value="${code.category}"/>";
          var TYPE = "<c:out value="${code.type}"/>";
          var VALUE = "<c:out value="${code.value}"/>";
          var ACTION_MODE = "MODIFY";
     </script>      
</body>
</html>