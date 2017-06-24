<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<head>
	<script type="text/javascript">
		function goOrder(prodId){
		  document.location.href = "/shop/order/product.yum?prodId=" + prodId + "&page=" + ${page};
		}
		
		function goNextPage(page){
			$("#page").val(page);
			$("#nextPageForm").submit();
		}
		function goNextFrame(page){
			$("#page").val(((+page) + (+1)));
			$("#nextPageForm").submit();
		}
		function goPreviousFrame(page){
			$("#page").val(((+page) - (+1)));
			$("#nextPageForm").submit();
		}		
	</script>
	<style>
	    .img-circle {
	    	border-radius: 50%;
		}
		.rouned-table {
		    margin: 0px;
		    border-collapse: separate;
		    border-spacing: 0px;
		    
		}
		.rouned-td {
		    border-radius: 5px;
		    background-color: #369;
		    color: white;
		    border: 5px solid #C8C8C8;
		}​	
	</style>
</head>

<body>
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
		   	<table style="width: 100%;" class="rouned-table">
		   		<tr>
		   			<td colspan="2" style="padding: 5px;text-align: center;background-color: #D7D7D7;" class="rouned-td">
		   				<b><a href="javascript:goSellerShop('${sellerId }');"><span style="color: #7F7F7F;"><b>${sellerBusinessName}</b></span></a></b>
		   			</td>
		   		</tr>			   		
			</table>   		        
        </div>
    </div>
    
    <div class="row" style="padding-top: 10px;">
        <div class="col-md-12" style="padding-left: 0px;">
        	<form id="nextPageForm" action="/shop/order/product.yum" method="GET">
        	<table style="width: 100%;" border="0">
        		<tr>
        			<td>
						<nav>
						  <ul class="pagination" style="margin: 0px;">
							<c:choose>
							  	<c:when test="${pageFrameNumber ne  1}"><li><a href="javascript:goPreviousFrame('${displayStartPage}');" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li></c:when>
							</c:choose>
						    <c:forEach begin="${displayStartPage}" end="${displayEndPage}" varStatus="loop">
							    	<c:choose>
							    		<c:when test="${loop.index eq page}"><li class="active"><a>${loop.index}</a></li></c:when>
							    		<c:otherwise><li><a href="javascript:goNextPage('${loop.index}')">${loop.index}</a></li></c:otherwise>
							    	</c:choose>
							</c:forEach>

							<c:choose>
							  	<c:when test="${totalNumberOfFrames ne  pageFrameNumber}"><li><a href="javascript:goNextFrame('${displayEndPage}');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li></c:when>
							</c:choose>
						    
						  </ul>
						</nav>
        			</td>
        			<td style="vertical-align: bottom;width: 100px;text-align: right;"><a href="javascript:goSellerShop('${sellerId }');"><span  style="font-size: 9px;">판매자 <span  style="color: #561314;font-size: 9px;">Shop</span>으로가기</span></a></td>
        		</tr>
        	</table>
        	<input type="hidden" name="page" id="page" value=""/>
        	<input type="hidden" name="prodId" id="prodId" value="${product.prodId }"/>
			</form>	
        </div>
    </div>  
    <br />  
       	
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">       	
		   	<c:choose>
		         <c:when test="${not empty sellerProducts and fn:length(sellerProducts) > 1}">					    
		         <c:forEach var="entry" items="${sellerProducts}" varStatus="count">
					    	<c:choose>
					    		<c:when test="${entry.productImage.prodId != product.prodId}">
					    		
							    	<!--  -->
							    	<table style="width: 95%;border-color:#808080;margin-bottom: 10px;height: 150px;background-color: #D6D6D6;" align="center" border="0" onmouseover="this.style.border = '2px solid #86BF44'" onmouseout="this.style.border = '1px solid #FFFFFF'">
							    		<tr><td style="padding: 1px;">
									    	<table style="width: 100%;height: 100%;background-color: #FFFFFF;">
									    		<tr>
									    			<td style="text-align: center;padding: 5px;width: 200px;">
									    				<c:choose>
									    					<c:when test="${entry.productImage.imageFileId == null or entry.productImage.imageFileId == 0}"><img src="/resources/image/default_goods2.png" style="width: 80px;height: 80px;"></c:when>
									    					<c:otherwise><img src="/img/?f=${entry.productImage.imageFileId}" style="width: 130px;height: 130px;"/></c:otherwise>
									    				</c:choose>
									    			</td>
									    			<td>
									    				<table style="width: 100%;height: 100%;" border="0">
									    					<tr><td><span style="color: #450C0E;">${entry.name}</span></td></tr>
									    					<tr><td style="padding-top: 10px;padding-bottom: 10px;"><span style="color: #D2141C;font-size: 20px;">멜푸드가격 :</span> <span style="font-size: 20px;font-weight: bold;">$ ${entry.unitPrice}</span></td></tr>
									    					<tr>
									    						<td style="padding-right: 5px;">
									    							<table align="right">
										    							<tr><td style="background-color: #4DA8DD;padding: 5px;"><a href="javascript:goOrder('${entry.productImage.prodId}')"><span style="color: #FFFFFF;font-size: 9px;">주문하기</span></a></td></tr>
										    						</table>
									    						</td>
									    					</tr>
									    				</table>
									    			</td>
									    		</tr>
									    	</table>	
							    		</td></tr>
							    	</table>
					    		</c:when>
					    	</c:choose>
					    </c:forEach> 
		         </c:when>
		         <c:otherwise>동일한 판매자의 상품이 더이상 존재하지 않습니다.</c:otherwise>
		    </c:choose>
        </div>
    </div>
    

    <div class="row" style="padding-top: 10px;">
        <div class="col-md-12" style="padding-left: 0px;">
        	<table style="width: 100%;">
        		<tr>
        			<td>
						<nav>
						  <ul class="pagination" style="margin: 0px;">
							<c:choose>
							  	<c:when test="${pageFrameNumber ne  1}"><li><a href="javascript:goPreviousFrame('${displayStartPage}');" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li></c:when>
							</c:choose>
						    <c:forEach begin="${displayStartPage}" end="${displayEndPage}" varStatus="loop">
							    	<c:choose>
							    		<c:when test="${loop.index eq page}"><li class="active"><a>${loop.index}</a></li></c:when>
							    		<c:otherwise><li><a href="javascript:goNextPage('${loop.index}')">${loop.index}</a></li></c:otherwise>
							    	</c:choose>
							</c:forEach>

							<c:choose>
							  	<c:when test="${totalNumberOfFrames ne  pageFrameNumber}"><li><a href="javascript:goNextFrame('${displayEndPage}');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li></c:when>
							</c:choose>
						    
						  </ul>
						</nav>
        			</td>
        			<td style="vertical-align: bottom;width: 100px;text-align: right;"><a href="javascript:goSellerShop('${sellerId }');"><span  style="font-size: 9px;">판매자 <span  style="color: #561314;font-size: 9px;">Shop</span>으로가기</span></a></td>
        		</tr>
        	</table>
        </div>
    </div> 
    
        					            	
</body>
</html>