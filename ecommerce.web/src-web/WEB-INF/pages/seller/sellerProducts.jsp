<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<head>
<script type="text/javascript">
	function goOrder(prodId){
	  document.location.href = "/shop/order/product.yum?prodId=" + prodId;
	}
</script>
</head>

<body>
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
		   	<table style="width: 100%;background-color: #64A41E;">
		   		<tr><td colspan="2" style="padding: 5px;text-align: center;color: #F7F7F7;"><b><a href="javascript:goSellerShop('${sellerId }');"><span style="color: #000000;"><b>${sellerBusinessName}</b></span></a> 의 다른 상품</b></td></tr>
		   	</table>
		   	<br/>
        </div>
    </div>  
       	
   	<c:choose>
         <c:when test="${not empty sellerProducts and fn:length(sellerProducts) > 1}">
			    <c:forEach var="entry" items="${sellerProducts}" varStatus="count">
			    	<c:choose>
			    		<c:when test="${entry.productImage.prodId != product.prodId}">
			    		
					    	<table style="width: 100%;background-color: #FFFFFF;border-color: #FF0000;">
					    		<tr>
					    			<td colspan="2" style="text-align: center;padding: 5px;">
					    				<c:choose>
					    					<c:when test="${entry.productImage.imageFileId == null or entry.productImage.imageFileId == 0}"><img src="/resources/image/default_goods2.png" style="width: 80px;height: 80px;"></c:when>
					    					<c:otherwise><img src="/img/?f=${entry.productImage.imageFileId}" style="width: 100px;height: 100px;"/></c:otherwise>
					    				</c:choose>
					    			</td>
					    		</tr>
					    		<tr><td colspan="2" style="padding: 5px;color: #450C0E;">${entry.name} </td></tr>
					    		<tr>
					    			<td style="padding: 5px;"><span style="color: #FF1172;">쿠빵가격 :</span> <b>$ ${entry.unitPrice}</b></td>
					    			<td style="padding: 5px; text-align: right;">
						    			<table>
						    				<tr><td style="background-color: #000000;padding: 5px;"><a href="javascript:goOrder('${entry.productImage.prodId}')"><span style="color: #FFFFFF;font-size: 9px;">주문하기</span></a></td></tr>
						    			</table>
					    			</td>
					    		</tr>
					    	</table>
					    	<table><tr><td></td>&nbsp;</tr></table>    		
			    		
			    		</c:when>
			    	</c:choose>
			    </c:forEach> 
         </c:when>
         <c:otherwise>동일한 판매자의 상품이 더이상 존재하지 않습니다.</c:otherwise>
    </c:choose>
					            	
</body>
</html>