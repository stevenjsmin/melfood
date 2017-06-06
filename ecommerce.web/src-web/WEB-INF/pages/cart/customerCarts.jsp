<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<head>
<script type="text/javascript">
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip(); 
});

function goPayment(sellerId){
	document.location.href = "/cart/mycart.yum?selectedSellerId=" + sellerId;
}

function alertAurrentCart() {
	infoPopup("현재 메인 화면에서 보고계신 장바구니입니다.");
}
</script>
<style type="text/css">
	.subCarts tr:nth-child(odd){ 
		background: #F6F6F6;
	}
	.subCarts tr:nth-child(even){
		background: #FFFFFF;
	}
</style>
</head>

<body>
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
		   	<table style="width: 100%;background-color: #F17517;">
		   		<tr><td colspan="2" style="padding: 5px;text-align: center;color: #F7F7F7;"><b>고객님의 장바구니 : ${numberOfCart} 개</b></td></tr>
		   	</table>
		   	<br/>
        </div>
    </div>  
    
		    <c:forEach var="customerCart" items="${customerCarts}" varStatus="count">
		       
		       <c:choose>
		       		<c:when test="${customerCart.cartNumber == selectedCart.cartNumber }">
						    <table border="3" style="width: 100%;border-color: #3694DA; background-color: #FFFFFF;">
							    <tr><td>
								    <table border="0" style="width: 100%;">
								    	<tr>
								    		<td style="width: 50px;text-align: right;"> 판매자 : </td>
								    		<td colspan="2">
								    			<span style="font-size: 15px;font-weight: bold;">&nbsp; ${customerCart.seller.sellerBusinessName }</span>
								    			<span style="font-size: 12px;">&nbsp;(${customerCart.seller.userName })</span>
								    		</td>
								    		<td style="width: 20px; text-align: center;background-color: #71B921;">${customerCart.cartNumber}</td>
								    	</tr>
								    	<tr><td colspan="4"><hr class="subtitle"/></td></tr>
								    	<tr>
								    		<td colspan="4" style="text-align: right;">
								    			<table border="0" style="text-align: right;width: 95%;" class="subCarts">
									    			<c:forEach var="orderedProd" items="${customerCart.products}" varStatus="idx">
									    				<tr>
									    					<td>${orderedProd.name }</td>
									    					<td style="width: 40px;">${orderedProd.orderAmount } 개</td>
									    				</tr>
									    			</c:forEach>
								    			</table>
								    		</td>
								    	</tr>
								    	<tr><td colspan="4">&nbsp;</td></tr>
								    	<tr>
								    		<td colspan="4" style="padding: 2px 10px 2px 10px;text-align: right;">
								    			<button type="button" class="btn btn-default btn-xs" onclick="alertAurrentCart()">&nbsp;&nbsp;&nbsp;<span data-toggle="tooltip" title="현재선택된 장바구니입니다.">결 재 : $ <fmt:formatNumber type="number" pattern="###.00" value="${customerCart.totalPrice}" /> </span>&nbsp;&nbsp;&nbsp;</button></td>
								    	</tr>
								    </table>
							    </td>
							    </tr>
						    </table>
		       		</c:when>
		       		<c:otherwise>
						    <table border="1" style="width: 100%;border-color: #DADADA; background-color: #FFFFFF;">
							    <tr><td>
								    <table border="0" style="width: 100%;">
								    	<tr>
								    		<td style="width: 50px;text-align: right;color: #A8A8A8;"> 판매자 : </td>
								    		<td colspan="2">
								    			<span style="font-size: 15px;color: #A8A8A8;font-weight: bold;">&nbsp; ${customerCart.seller.sellerBusinessName }</span>
								    			<span style="font-size: 12px;color: #A8A8A8;">&nbsp;(${customerCart.seller.userName })</span>
								    		</td>
								    		<td style="width: 20px; text-align: center;background-color: #71B921;">${customerCart.cartNumber}</td>
								    	</tr>
								    	<tr><td colspan="4"><hr class="subtitle"/></td></tr>
								    	<tr>
								    		<td colspan="4" style="text-align: right;">
								    			<table border="0" style="text-align: right;width: 95%;color: #A8A8A8;" class="subCarts">
									    			<c:forEach var="orderedProd" items="${customerCart.products}" varStatus="idx">
									    				<tr>
									    					<td>${orderedProd.name }</td>
									    					<td style="width: 40px;">${orderedProd.orderAmount } 개</td>
									    				</tr>
									    			</c:forEach>
								    			</table>
								    		</td>
								    	</tr>
								    	<tr><td colspan="4">&nbsp;</td></tr>
								    	<tr>
								    		<td colspan="4" style="padding: 2px 10px 2px 10px;text-align: right;">
								    			<button type="button" class="btn btn-danger btn-xs" onclick="goPayment('${customerCart.seller.userId}');">&nbsp;&nbsp;&nbsp;결 재 : $ <fmt:formatNumber type="number" pattern="###.00" value="${customerCart.totalPrice}" /> &nbsp;&nbsp;&nbsp;</button>
								    		</td>
								    	</tr>
								    </table>
							    </td>
							    </tr>
						    </table>		       		
		       		</c:otherwise>
		       </c:choose>
			   <br/>
			   <br/>
		    </c:forEach>  
			            	
</body>
</html>