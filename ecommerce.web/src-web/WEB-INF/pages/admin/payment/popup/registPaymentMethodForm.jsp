<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script src="/resources/js/melfood/admin/paymentmethodmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                              <col width="200px" />
                              <col width="250px" />
                              <col width="200px" />
                              <col width="250px" />
                         </colgroup>       
                         <tr>
                              <td class="label"><span class="required">* </span>Seller :</td>
                              <td class="value" colspan="2"><c:out value="${cbxSeller}" escapeXml="false"/></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Method :</td>
                              <td class="value" colspan="2"><c:out value="${cbxPaymentMethod}" escapeXml="false"/></td>
                              <td></td>
                         </tr>
                         
                         <tr style="height: 10px;"><td colspan="4"></td></tr>
                         
                         <tr id="bankDetailInfo">
	                       	 <td colspan="4">
		                         <table class="detail_table">
			                         <tr>
			                              <td class="label">Bank :</td>
			                              <td class="value" colspan="2"><c:out value="${cbxBankName}" escapeXml="false"/></td>
			                              <td></td>
			                         </tr>
			                         <tr>
			                              <td class="label">BSB :</td>
			                              <td class="value"><input class="form-control" type="text" id="bankBsb" name="bankBsb" value=''/></td>
			                              <td class="label">Account No. :</td>
			                              <td class="value"><input class="form-control" type="text" id="bankAccountNo" name="bankAccountNo" value=''/></td>
			                         </tr>
			                         <tr>
			                              <td class="label">Account holder :</td>
			                              <td class="value"><input class="form-control" type="text" id="bankAccountOwnerName" name="bankAccountOwnerName" value=''/></td>
			                              <td></td>
			                         </tr>
		                         </table>
	                       	 </td>
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
                                   <a href="javascript:parent.closePaymentMethodPopup();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Add</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
     </table>
     
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>