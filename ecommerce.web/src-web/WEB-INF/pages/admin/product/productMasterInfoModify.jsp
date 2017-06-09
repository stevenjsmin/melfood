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
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
	var commissionType = '${product.sellingCommissionType}';
    if(commissionType == 'FEE') {
		$('#spanCommissionFee').show();
		$('#spanCommissionRate').hide();
		$('#spanCommissionRate').val('');
	} else if(commissionType == 'RATE') {
		$('#spanCommissionRate').show();
		$('#spanCommissionFee').hide();
		$('#spanCommissionFee').val('');
	} else if(commissionType == 'NONE') {
		$('#spanCommissionRate').hide();
		$('#spanCommissionFee').hide();
		$('#spanCommissionFee').val('');
		$('#spanCommissionRate').val('');
	}
    
    $("#description").kendoEditor({
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
                              <col width="200px" />
                              <col width="250px" />
                              <col width="200px" />
                              <col width="300px" />
                         </colgroup>
                         <tr>
                              <td class="label">상품ID</td>
                              <td class="value">${product.prodId}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>상품명</td>
                              <td class="value" colspan="3"><input class="form-control" type="text" id="name" name="name" value='${product.name}'/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>판매자</td>
                              <td class="value"><c:out value="${cbxSeller}" escapeXml="false" /></td>
                              <td class="label"><span class="required">* </span>상품분류</td>
                              <td class="value"><c:out value="${cbxCategory}" escapeXml="false" /></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>등록상태</td>
                              <td class="value"><c:out value="${cbxRegisterStatus}" escapeXml="false" /></td>
                              <td class="label"><span class="required">* </span>등록만료일자</td>
                              <td class="value"><input type="text" id="validateUntil" name="validateUntil" value='${product.validateUntil}'/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>재고량</td>
                              <td class="value"><input type="text" id="inStockCnt" name="inStockCnt" value='${product.inStockCnt}'/></td>
                              <td class="label"><span class="required">* </span>단가</td>
                              <td class="value"><input type="text" id="unitPrice" name="unitPrice" value='${product.unitPrice}'/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>판매상태</td>
                              <td class="value"><c:out value="${cbxSellingStatus}" escapeXml="false" /></td>
                              <td class="value"></td>
                              <td class="value"></td>
                         </tr>
                                                  
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Tags</td>
                              <td class="value" colspan="3"><input class="form-control" type="text" id="tags" name="tags" value='${product.tags}'/></td>
                         </tr>                         
                         <tr>
                              <td></td>
                              <td class="value" colspan="3"><span style="color: #8B8B8B;">** 콤마(,)로 구분하여 각 단어를 입력해주세요.</span></td>
                         </tr>                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label"><span class="required">* </span>커미션 형태</td>
                              <td class="value"><c:out value="${cbxSellingCommissionType}" escapeXml="false" /></td>
                              <td class="label">커미션 [ Free | % | $ ]</td>
                              <td class="value">
                                   <span id="spanCommissionRate" style="display: none;"><input type="text" id="sellingCommissionRate" name="sellingCommissionRate" value='${product.sellingCommissionRate}'/></span>
                                   <span id="spanCommissionFee" style="display: none;"><input type="text" id="sellingCommissionFee" name="sellingCommissionFee" value='${product.sellingCommissionFee}'/></span>
                              </td>
                         </tr>                         
                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;"><span class="required">* </span>Description</td>
                              <td class="value" colspan="3"><textarea class="form-control" rows="3" id="description" name="description">${product.description}</textarea></td>
                              <td></td>
                         </tr>
                         <tr><td class="metavalue" colspan="3">Creator : ${product.creator}, Create Time : ${product.createDatetime}, Modify Time : ${product.modifyDatetime}</td></tr>
                    </table>
               </td>
          </tr>
          <tr><td colspan="3">&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Save</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          <input type="hidden" name="prodId"  id="prodId" value="${product.prodId}" />
     </table>
     
     <script type="text/javascript">
          var _PRODID = "<c:out value="${product.prodId}"/>";
          var ACTION_MODE = "MODIFY";
     </script>  
          
</body>
</html>