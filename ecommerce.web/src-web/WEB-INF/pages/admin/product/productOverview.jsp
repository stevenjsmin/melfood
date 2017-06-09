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
<!-- http://baijs.com/tinycarousel/ -->
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<style>
#imageTable tbody tr:nth-child(odd) {
   background-color: #F9F7FC;
}
</style>
</head>

<body>

    <span class="subtitle"> 상품 기본정보</span>
    <hr class="subtitle"/>  
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
                              <td class="label">상품명</td>
                              <td class="value" colspan="3">${product.name}</td>
                         </tr>
                         <tr>
                              <td class="label">판매자</td>
                              <td class="value">${product.sellerName}</td>
                              <td class="label">상품분류</td>
                              <td class="value">${product.categoryName}</td>
                         </tr>
                         <tr>
                              <td class="label">등록상태</td>
                              <td class="value">${product.registerStatusName}</td>
                              <td class="label">등록만료일자</td>
                              <td class="value">${product.validateUntil}</td>
                         </tr>
                         <tr>
                              <td class="label">재고량</td>
                              <td class="value">${product.inStockCnt}</td>
                              <td class="label">단가</td>
                              <td class="value">$ ${product.unitPrice}</td>
                         </tr>
                         <tr>
                              <td class="label">판매상태</td>
                              <td class="value">${product.sellingStatusName}</td>
                              <td class="value"></td>
                              <td class="value"></td>
                         </tr>                         
                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label">커미션 형태</td>
                              <td class="value">${product.sellingCommissionTypeName}</td>
                              <td class="label">커미션</td>
                              <td class="value">
                              		<c:choose>
                              			<c:when test="${product.sellingCommissionType == 'RATE' }"> 
                              				<fmt:formatNumber type="percent" maxIntegerDigits="3" value="${product.sellingCommissionRate}" />
                              			</c:when>
                              			<c:when test="${product.sellingCommissionType == 'FEE' }">$ ${product.sellingCommissionFee}</c:when>
                              		</c:choose>
                              </td>
                         </tr>                         
                         <tr><td colspan="4"><hr/></td></tr>
                         <tr>
                              <td class="label">Tags</td>
                              <td class="value" colspan="3" style="vertical-align: top;text-align: left;">${product.tags}</td>
                         </tr>                           
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                              <td class="label" style="vertical-align: top;padding-top: 5px;">Description</td>
                              <td class="value" colspan="3">${product.description}</td>
                              <td></td>
                         </tr>
                    </table>
               </td>
          </tr>

     </table>
     
    <br/> 
    <br/> 
    <span class="subtitle"> 상품 옵션정보</span>
    <hr class="subtitle"/>  
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
					    	<c:choose>
						         <c:when test="${not empty productOptions}">
						                                  
									    <c:forEach var="entry" items="${productOptions}" varStatus="count" begin="0">
									    	<tr>
									    		<td class="label">${entry.optionLabel}</td>
									    		<td class="value"><c:out value="${entry.optionCmbx}" escapeXml="false"/></td>
									    		<td></td>
									    		<td></td>
									    	</tr>
									    </c:forEach>    
						         </c:when>
						         <c:otherwise>
										<tr>
											<td colspan="4">
						         				<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;">등록된 상품옵션이 없습니다.</span>
											</td>		         
									    </tr>
						         </c:otherwise>
					    	</c:choose>
                     </table>    
                  </td>       
              </tr>
     </table>                    
                         
                         

    <br/> 
    <br/> 
    <span class="subtitle"> 상품 이미정보</span>
    <hr class="subtitle"/>  
    
    
    <table class="detail_table" id="imageTable">
		<tr>
			<td class="label" style="text-align: center; width: 60px;">SEQ</td>
			<td class="label" style="text-align: center; width: 200px;">IMGAGE</td>
			<td class="label" style="text-align: center; width: 200px;">DESCRIPTION</td>
			<td class="label" style="text-align: center;">IMG META INFO</td>
	    </tr>
	    	<c:choose>
		         <c:when test="${not empty productImages}">
					<c:forEach var="entry" items="${productImages}" varStatus="count" begin="0">
						<tr>
							<td class="value" style="text-align: center;vertical-align: top;padding-top: 5px;background-color: #7E7C7F;color: #FFFFFF;">${entry.displayOrder}</td>
							<td class="value" style="text-align: left;vertical-align: top;padding-top: 5px;">
								<a href="javascript:openImageInNewTab('${entry.imageFileId}');" >
									<c:choose>
							            <c:when test="${entry.height > 200}">
							                <c:choose>
							                	<c:when test="${entry.width > 200}">
							                		<img src="/img/?f=${entry.imageFileId}" style="height: 200px;width: 200px;"/>
							                	</c:when> 
							                	<c:otherwise>
							                		<img src="/img/?f=${entry.imageFileId}" style="height: 200px;width: ${entry.width}px;"/>
							                	</c:otherwise>
							                </c:choose>
							            </c:when>
							            
							            <c:otherwise>
							                <c:choose>
							                	<c:when test="${entry.width > 200}">
							                		<img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: 200px;"/>
							                	</c:when>
							                	<c:otherwise>
							                		<img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: ${entry.width}px;"/>
							                	</c:otherwise>
							                </c:choose>
							            </c:otherwise>
									</c:choose>
								</a>				
							</td>
							<td class="value" style="text-align: left;vertical-align: top;padding-top: 5px;">${entry.imageDescription}</td>
							<td class="value" style="text-align: left;vertical-align: top;padding-top: 5px;">
								<font style="color: #E1DEE3;">Size : </font>${entry.width} X ${entry.height} <br />
								<font style="color: #E1DEE3;">File id : </font>${entry.imageFileId}<br />
								<font style="color: #E1DEE3;">File name : </font>${entry.attachFile.fileName}<br />
								<font style="color: #E1DEE3;">Saved file name :</font> ${entry.attachFile.subDirectory}${entry.attachFile.savedFileName}<br />
							</td>
					    </tr>
				    </c:forEach>
		         </c:when>
		         <c:otherwise>
						<tr>
							<td colspan="4">
		         				<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;">등록된 이미지가 없습니다.</span>
							</td>		         
					    </tr>
		         </c:otherwise>
		    </c:choose>			                    
    </table>     
    <br/> 
    <br/> 
    <br/> 
    <br/>
    
     
    <input type="hidden" name="prodId"  id="prodId" value="${product.categoryId}" />
     
    <script type="text/javascript">
          var _PRODID = "<c:out value="${product.prodId}"/>";
    </script>  
          
</body>
</html>