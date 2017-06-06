<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    $("#imageDescription").kendoEditor({
  		encoded: true
	});
    
	$("#displayOrder").kendoNumericTextBox({
    	 max: 100,
    	 min: 0,
        format: "n0"
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
                              <col width="200px" />
                              <col width="200px" />
                              <col width="200px" />
                         </colgroup>       
                         <tr>
                              <td class="value" style="text-align: center;background-color: #D6D6D6;" colspan="4">
	                              <c:choose>
	                                  <c:when test="${productImage.width > productImage.height}">
	                                       <c:choose>
				                                  <c:when test="${productImage.width > 350}">
				                                  		<img src="/img/?f=${productImage.imageFileId}" style="width: 350px;"/>
				                                  </c:when>
				                                  <c:otherwise>
				                                  		<img src="/img/?f=${productImage.imageFileId}"/>
				                                  </c:otherwise>	                                       		
	                                       </c:choose>
	                                  </c:when>
	                                  <c:otherwise>
	                                       <c:choose>
				                                  <c:when test="${productImage.height > 200}">
				                                  		<img src="/img/?f=${productImage.imageFileId}" style="height: 200px;"/>
				                                  </c:when>
				                                  <c:otherwise>
				                                  		<img src="/img/?f=${productImage.imageFileId}"/>
				                                  </c:otherwise>	                                       		
	                                       </c:choose>
	                                  </c:otherwise>
	                              </c:choose>
                              	 
                              </td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Display order</td>
                              <td class="value"><input type="text" id="displayOrder" name="displayOrder" value='${productImage.displayOrder}'/></td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Image Size(W X H)</td>
                              <td class="value" colspan="2"> ${productImage.width} X ${productImage.height}
                              	<!--  
                              	<table>
                              		<td><input type="text" id="width" name="width" value='${productImage.width}'/></td>
                              		<td> X </td>
                              		<td><input type="text" id="height" name="height" value='${productImage.height}'/></td>
                              	</table>
                              	-->
                              </td>
                              <td></td>
                         </tr>
                         
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;"><span class="required">* </span>Description</td>
                              <td class="value" colspan="3"><textarea class="form-control" id="imageDescription" name="imageDescription" rows="3" cols="30" style="width:100%;height:250px;">${productImage.imageDescription}</textarea></td>
                         </tr>
                         <tr><td colspan="4"><hr/></td></tr>                         
                         <tr>
                              <td class="value" colspan="4" style="font-style: italic;color: #B8B8B8;">
                              	File ID : ${productImage.imageFileId}, File Name : ${productImage.attachFile.fileName}<br />
                              	Physical Location : ${productImage.attachFile.subDirectory}${productImage.attachFile.savedFileName} 
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
                                   <a href="javascript:parent.closeImageWindow();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
                                   <a href="javascript:saveProdImage();" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
     </table>
     <input type="hidden" name="prodId"  id="prodId" value="${productImage.prodId}" />
     <input type="hidden" name="imageSeq"  id="imageSeq" value="${productImage.imageSeq}" />
     
     <script type="text/javascript">
          var ACTION_MODE = "MODIFY";
     </script>     
</body>
</html>