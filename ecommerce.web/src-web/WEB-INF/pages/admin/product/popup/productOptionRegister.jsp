<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                         </colgroup>       
                         <tr>
                              <td class="label"><span class="required">* </span>Option Item</td>
                              <td class="value"><input class="form-control" type="text" id="optionItem" name="optionItem" value=''/></td>
                         </tr>
                         <tr>
                              <td class="label">Is Mandatory Option</td>
                              <td class="value"><c:out value="${cbxIsMandatory}" escapeXml="false"/></td>
                         </tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="3">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:parent.closeOptionWindow();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                                   <a href="javascript:saveProdOption();" class="btn btn-primary">Add</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
     </table>
     <input type="hidden" name="prodId"  id="prodId" value="${prodId}" />
     
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>