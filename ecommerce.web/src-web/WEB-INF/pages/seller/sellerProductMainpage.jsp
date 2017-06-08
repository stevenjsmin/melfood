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
<script type="text/javascript">
	function openDeliverySchedulePopup(){
	    $("#deliverySchedulePopup").kendoWindow({
	        content: "/shop/sellerDeliverySchedule.yum?isPopup=true&sellerId=" + '${seller.userId}',
	        actions: [ "Minimize", "Maximize","Close" ],
	        title: "배송/픽업 일정 조회",
	        modal: true,
	        iframe: true
		});
	  
		var popup_dialog = $("#deliverySchedulePopup").data("kendoWindow");
		popup_dialog.setOptions({
		            width: "80%",height: "700",
		   	  	    open: function (e) {
		                this.wrapper.css({ top: 50 });
		             }
		          });
		
		popup_dialog.center().open();
	}
	function closedeliverySchedulePopup() {
		var win_dialog = $("#deliverySchedulePopup").data("kendoWindow");
		win_dialog.close();
	} 
</script>

<script type="text/javascript">
	function openAddCartPopupWin(prodId){
		document.location.href = "#home";
		openAddCartPopup(prodId);
	}

	function openAddCartPopup(prodId){
	    $("#addCartPopup").kendoWindow({
	        content: "/shop/addcart.yum?prodId=" + prodId,
	        actions: [ "Minimize", "Maximize","Close" ],
	        title: "ADD CART",
	        modal: true,
	        iframe: true
		});
	  
		var popup_dialog = $("#addCartPopup").data("kendoWindow");
		popup_dialog.setOptions({
		            width: "900",
		            height: "700",
		            open: function (e) {
		                this.wrapper.css({ top: 50 });
		        }})
		
		popup_dialog.center().open();      
		popup_dialog.open(); 
	}
	function closeAddCartPopup() {
		var win_dialog = $("#addCartPopup").data("kendoWindow");
		win_dialog.close();
		window.location.reload(true);
	}
	
	function goPaymentFromSellerMain() {
		document.location.href = "/cart/mycart.yum?selectedSellerId=${seller.userId}";
	} 

</script>
</head>

<body>
	<div id="deliverySchedulePopup"></div>
	<div id="addCartPopup"></div>

    <div class="row">
        <div class="col-md-12">
        	<form id="nextPageForm" action="/shop/no.yum" method="GET">
        	<table border="0" style="width: 100%;">
        		<tr>
        			<td style="width: 255px;">
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
        			<td class="label" style="vertical-align: middle;width: 255px;"><input class="form-control" style="width: 250px;" id="productName" name="productName" placeholder="상품명으로 검색해주세요." value='${productName }'></td>
        			<td class="label" style="text-align: left;vertical-align: middle"><a href="javascript:goNextPage('1')"><img src="/resources/css/images/gic/ic_search_black_18dp_2x.png"></a></td>
        			<td style="text-align: right;padding: 0px;">
        			    
        			    
        				<table align="right" border="0">
        					<tr>
        						<td>
        							<c:choose>
		   								<c:when test="${sessionUser != null }">
			        							<table style="cursor: pointer;border: 1px solid #FFFFFF;" border="1" onmouseover="this.style.border = '1px solid #B7141C'" onmouseout="this.style.border = '1px solid #FFFFFF'" onclick="goPaymentFromSellerMain() "><tr><td>
				        							<table border="0">
				        								<tr><td style="text-align: center;width: 100px;"><img src="/resources/image/onlinePayment.png" style="height: 40px;"></td></tr>
				        								<tr>
				        									<td style="text-align: center;width: 100px;">
				        										<span style="font-size: 9px;color: #100100;">결재하기 <span id="markNumberOfProductOnCartFor"></span></span>
				        									</td></tr>
				        							</table>
			        							</tr></table>
		   								</c:when>
		   								<c:otherwise>
			        							<table style="cursor: pointer;border: 1px solid #FFFFFF;" border="1" onmouseover="this.style.border = '1px solid #B7141C'" onmouseout="this.style.border = '1px solid #FFFFFF'" onclick="openLoginPopup() "><tr><td>
				        							<table border="0">
				        								<tr><td style="text-align: center;width: 100px;"><img src="/resources/image/onlinePayment.png" style="height: 40px;"></td></tr>
				        								<tr><td style="text-align: center;width: 100px;"><span style="font-size: 9px;color: #100100;">결재하기</span></td></tr>
				        							</table>
			        							</tr></table>
		   								</c:otherwise>
		   							</c:choose>	
        						</td>
        						<td>
        							<table style="cursor: pointer;border: 1px solid #FFFFFF;" border="1" onmouseover="this.style.border = '1px solid #B7141C'" onmouseout="this.style.border = '1px solid #FFFFFF'" onclick="openDeliverySchedulePopup() "><tr><td>
        								<table border="0">
        									<tr><td style="text-align: center;width: 100px;">
        										<img src="/resources/image/delivery_schedule.png" style="height: 40px;">
        										<img src="/resources/image/gmap_icon2.png" style="height: 35px;padding-left: 5px;"></td></tr>
        									<tr><td style="text-align: center;width: 100px;"><span style="font-size: 9px;color: #100100;">배송/픽업 일정확인</span></td></tr>
        								</table>
        							</td></tr></table>	
        						</td>
        					</tr>
        				</table>	
        			    
        				
        			</td>
        		</tr>
        	</table>
        	<input type="hidden" name="page" id="page" value=""/>
        	<input type="hidden" name="seller" id="seller" value="${seller.userId}"/>
			</form>	
        </div>
    </div>  
    
    <div class="row">
	    	<c:choose>
	        	<c:when test="${not empty sellerProducts and fn:length(sellerProducts) > 0}">
				    <c:forEach var="entry" items="${sellerProducts}" varStatus="count">
				    		<div class="col-xs-3">
				    		<div class="table-responsive">
				    		    <table class="table table-bordered" style="width: 250px;height: 300px;" border="1">
				    		    	<tr>
				    		    		<td style="text-align: center;">
									    	<table style="width: 100%;height: 100%;cursor: pointer;border: 1px solid #FFFFFF;" align="center" onmouseover="this.style.border = '1px solid #B7141C'" onmouseout="this.style.border = '1px solid #FFFFFF'" onclick="openAddCartPopupWin('${entry.productImage.prodId}') ">
									    		<tr>
									    			<td colspan="2" style="text-align: center;padding: 5px;">
									    			
									    				<c:choose> 
									    					<c:when test="${entry.productImage.imageFileId == null or entry.productImage.imageFileId == 0}"><img src="/resources/image/default_goods2.png" style="width: 80px;height: 80px;"></c:when>
									    					<c:otherwise><img src="/img/?f=${entry.productImage.imageFileId}" style="width: 150px;height: 150px;"/></c:otherwise>
									    				</c:choose>
									    			</td>
									    		</tr>
									    		<tr><td colspan="2" style="padding: 5px;color: #450C0E;height: 20px;font-weight: bolder;">${entry.name}</td></tr>
									    		<tr>
									    			<td style="height: 20px;padding: 5px;font-size: 20px;text-align: left;" colspan="2"><span style="color: #D2141C;font-size: 15px;font-weight: bold;">쿠빵가격 :</span> <b>$ ${entry.unitPrice}</b></td>
									    		</tr>
									    		<tr>
									    			<td style="height: 20px;text-align: right;padding-right: 10px;" colspan="2"><a href="javascript:openAddCartPopupWin('${entry.productImage.prodId}'); "><span style="font-size: 10px;">장바구니 담기</span></a></td>
									    		</tr>
									    	</table>
									    	
									    	
				    		    		</td>
				    		    	</tr>
				    		    </table>
						    	</div>
						    	</div>
				    </c:forEach> 
	         	</c:when>
	        	<c:otherwise>동일한 판매자의 상품이 더이상 존재하지 않습니다.</c:otherwise>
	    	</c:choose>
        </div>
</body>
</html>