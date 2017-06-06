<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

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
	                         <col width="200px" />
	                         <col width="250px" />
	                         <col width="200px" />
	                         <col width="250px" />
	                    </colgroup>       
	                    <tr>
	                         <td class="label">Product Name > Option Item</td>
	                         <td class="value" colspan="2">${product.name} > ${productOption.optionItem}</td>
	                         <td></td>
	                    </tr>
	                    <tr>
	                         <td class="label"><span class="required">* </span>상품 옵션 값</td>
	                         <td class="value" colspan="2"><input class="form-control" type="text" id="valueLabel" name="valueLabel" value=''/></td>
	                         <td></td>
	                    </tr>
                        <tr>
                             <td class="label"><span class="required">* </span>In Stock</td>
                             <td class="value"><input type="text" id="inStockCnt" name="inStockCnt" value='0'/></td>
                             <td class="label"><span class="required">* </span>Unit Price</td>
                             <td class="value"><input type="text" id="unitPrice" name="unitPrice" value='0'/></td>
                        </tr>
                        <tr>
                             <td class="label"><span class="required">* </span>Extra charge</td>
                             <td class="value"><input type="text" id="extraCharge" name="extraCharge" value='0'/></td>
                             <td></td>
                             <td></td>
                        </tr>
                        <tr>
                             <td class="label"><span class="required">* </span>Use Y/N</td>
                             <td class="value"><c:out value="${cbxUseYn}" escapeXml="false" /></td>
                             <td></td>
                             <td></td>
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
	                              <a href="javascript:saveProdOptionValue();" class="btn btn-primary">Save</a>
	                         </td>
	                    </tr>
	               </table>
	          </td>
	     </tr>
	     
	</table>
     
     <input type="hidden" name="prodId"  id="prodId" value="${prodId}" />
     <input type="hidden" name="optionSeq"  id="optionSeq" value="${optionSeq}" />
     
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>