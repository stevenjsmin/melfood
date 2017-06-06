<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<head>
<!-- <META http-equiv="refresh" content="1;URL=redirect.html"> -->
<!-- META http-equiv="refresh" content="1;URL=http://rpc.dev.utilitiessoftwareservices.com:8080/rpc/redirect.html"-->

<script type="text/javascript">
$(document).ready(function () {
  
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
function removeItem(itemId){
    $.ajax({
        url  : "/cart/removeItem.yum",
        data : {
        	itemId : itemId
        },
        success : callbackRemoveItem
   });  	
}

function callbackRemoveItem(data) {
    var message = data.message;
    var resultCode = data.resultCode;

    if (resultCode != "0") {
         warningPopup(data.message);
    } else {
		BootstrapDialog.alert({
            title: '알림  :: 호주가 즐거운 이유, 쿠빵!!',
            message: data.message,
            type: BootstrapDialog.TYPE_INFO, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
            btnOKLabel: 'OK', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function(result) {
                if(result) {
			    	location.reload();
                }
            }
        });    	
    	
    }
}


function openModifyDeliveryAddressPopup(){
    $("#modifyDeliveryAddressPopup").kendoWindow({
        content: "/cart/modifyDeliveryAddressForm.yum",
        actions: [ "Minimize", "Maximize","Close" ],
        title: "상품수령지 주소 변경",
        modal: true,
        iframe: true
		});
  
	var popup_dialog = $("#modifyDeliveryAddressPopup").data("kendoWindow");
	popup_dialog.setOptions({
     	  	pinned: true,
     	  	position: { top: "100", left: "20%" },
           	width: 700,height: 300
          });
	$("#modifyDeliveryAddressPopup").data("kendoWindow").open(); 
}

function closeModifyDeliveryAddressPopup() {
	var win_dialog = $("#modifyDeliveryAddressPopup").data("kendoWindow");
	win_dialog.close();
}

function openChooseDeliveryDatePopup(){
    $("#chooseDeliveryDatePopup").kendoWindow({
        content: "/cart/chooseDeliveryDateForm.yum?sellerId=" + '${selectedCart.seller.userId}',
        actions: [ "Minimize", "Maximize","Close" ],
        title: "상품수령지 주소 변경",
        modal: true,
        iframe: true
		});
  
	var popup_dialog = $("#chooseDeliveryDatePopup").data("kendoWindow");
	popup_dialog.setOptions({
     	  	pinned: true,
     	  	position: { top: "100", left: "20%" },
           	width: 950,height: 600
          });
	$("#chooseDeliveryDatePopup").data("kendoWindow").open(); 
}

function closeChooseDeliveryDatePopup() {
	var win_dialog = $("#chooseDeliveryDatePopup").data("kendoWindow");
	win_dialog.close();
}

function cancelChooseDeliveryDate(){
	$("#addressStreet").val('');
	$("#addressSuburb").val('');
	$("#addressState").val('');
	$("#addressPostcode").val('');
	$("#isPickup").val('');
	$("#yyyyMmDd").val('');
	$("#amPm").val('');
	
	$("#productReceiveAndPickupDate",top.document).html( '<span style="color: #AF0003;font-size: 15px;font-weight: bold;">상품수령일자 / 픽업일정이 선택되지 않았습니다.</span>' );
}
</script>


<script type="text/javascript">
	function showAdditionalInfoForPaymentMethod(obj){
		var selectedValue = obj.value;
		// TODO : Nothing
	}
</script>
<script type="text/javascript">
	function order(){
		
		if(validateForm() == false) return;
		
	    var totalPaymentPrice = '${selectedCart.totalPrice}';
	    var addressStreet = $("#addressStreet").val();
	    var addressSuburb = $("#addressSuburb").val();
	    var addressState = $("#addressState").val();
	    var addressPostcode = $("#addressPostcode").val();
	    var isPickup = $("#isPickup").val();
	    var yyyyMmDd = $("#yyyyMmDd").val();
	    var amPm = $("#amPm").val();
	    var sellerId = $("#sellerId").val();
	    
	    var sellerHaveMinimumPayment = '${selectedCart.seller.sellerHaveMinimumPayment}';
	    var sellerMinimumPaymentForPickup = '${selectedCart.seller.sellerMinimumPaymentForPickup}';
	    var sellerMinimumPaymentForDeliver = '${selectedCart.seller.sellerMinimumPaymentForDeliver}';
	    var sellerPaymentMethod = $("#sellerPaymentMethod").val();
	    
	    if(sellerHaveMinimumPayment == 'Y'){
	    	if(isPickup == 'N' ) {
	    		if(parseFloat(totalPaymentPrice) <  parseFloat(sellerMinimumPaymentForDeliver)) {
	    			warningPopup( '결재 금액이 최소주문 가능금액 보다 작습니다. 결재금액을 확인해주세요.<br><br>' + 
	  					      '<b>${selectedCart.seller.sellerBusinessName}(${selectedCart.seller.userName}) </b>매장에서 더 주문하러 가기 : ' +
	  					      '<a href="javascript:goSellerShop(\'${selectedCart.seller.userId}\');"> >> <span style="color: #0B45D3;">${selectedCart.seller.sellerBusinessName}</span> << </a>'  					      
	  						 );
	    			return;
	    		}	    		
	    	} else {
	    		if(parseFloat(totalPaymentPrice) < parseFloat(sellerMinimumPaymentForPickup)) {
	    			warningPopup( '결재 금액이 최소주문 가능금액 보다 작습니다. 결재금액을 확인해주세요.<br><br>' + 
  					      '<b>${selectedCart.seller.sellerBusinessName}(${selectedCart.seller.userName}) </b>매장에서 더 주문하러 가기 : ' +
  					      '<a href="javascript:goSellerShop(\'${selectedCart.seller.userId}\');"> >> <span style="color: #0B45D3;">${selectedCart.seller.sellerBusinessName}</span> << </a>'  					      
  						 );
	    			return;
	    		}
	    	}
	    }
	    
	    
	    
	    if(isPickup == '' || isPickup == null) {
			BootstrapDialog.confirm({
	            title: '알림  :: 호주가 즐거운 이유, 쿠빵!!',
	            message: '고객님께서 상품수령 또는 픽업일자를 선택하지 않으셨습니다.<br/> "상품수령 또는 픽업일자"를 선택하지 않는 경우 주문 완료 후 확인 전화드리도록 하겠습니다.<br/><br/> 계속하시겠습니까?',
	            type: BootstrapDialog.TYPE_SUCCESS, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            closable: true, // <-- Default value is false
	            draggable: true, // <-- Default value is false
	            btnCancelLabel: '취소', // <-- Default value is 'Cancel',
	            btnOKLabel: '네, 주문확인합니다.', // <-- Default value is 'OK',
	            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
	            callback: function(result) {
	                if(result) {
	                	doOrder();
	                }
	            }
	        }); 	    
	    } else {
	    	doOrder();
	    }
	    
	}
	
	function doOrder() {
		alert('Do process for next order');	
	}
	
	
	var checkObject = [];
	function validateForm(){
		
	    var count;
	    var elementObj = "";
	    var validation = true;
	    
	    // Initialize
	    for(count=0; count < checkObject.length; count++ ){
	        elementObj = "#" + checkObject[count];
	        $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
	    }
	    checkObject = [];
	    var prefix = "- &nbsp;&nbsp;";
	    var message = "";

	    var sellerPaymentMethod = $("#sellerPaymentMethod").val();
	 	if(sellerPaymentMethod == "") {
	 		message = message + prefix + "결재방법을 선택해 주세요.<br>";
	 		checkObject[checkObject.length] = "sellerPaymentMethod";
	        validation = false;
	 	}

	 	// 검증된 필드들을 마킹한다.
		for(count=0; count < checkObject.length; count++ ){
			elementObj = "#" + checkObject[count];
			$(elementObj).css({'background':'#fffacd','border-color':'#DF0000','border' : '1px solid #f00'});
		}
	 	if(validation == false){
	 		// 오류가 있는 경우 경고 창을 보여준다.
	 		warningPopup(message);
	 	}
	 	
	 	return validation;
	} 
	 	
</script>

</head>


<body>
	<div id="modifyDeliveryAddressPopup"></div>
	<div id="chooseDeliveryDatePopup"></div>
    <div class="row">
      <div class="col-md-12">
	   	<table style="width: 100%;">
	   		<tr>
	   			<td style="padding: 5px;background-color: #CA0000;">
	   				<span style="font-weight: bold;font-size: 15px;color: #C7C7C7;">상품 주문</span>
	   				<span style="font-weight: bold;font-size: 20px;color: #C7C7C7;"> &nbsp; > &nbsp;</span>
	   				<span style="font-weight: bold;font-size: 20px;color: #FFFFFF;">배송일지정/결재</span>
	   				<span style="font-weight: bold;font-size: 20px;color: #FFFFFF;"> &nbsp;&nbsp; > &nbsp;</span>
	   				<span style="font-weight: bold;font-size: 15px;color: #C7C7C7;">완료</span>
	   			</td>
	   		</tr>
	   	</table>
	   	<br/>
      </div>
    </div>  
    
    <div class="row">
    	<div class="col-md-9">
		   	<table style="width: 100%;">
		   		<tr>
		   			<td style="padding: 5px;text-align: right;">
		   				<table style="width: 100%;">
		   					<tr>
		   						<td style="text-align: right;">
					   				<table style="width: 300px;color: #A8A8A8" align="right"> 
					   					<tr><td colspan="2" style="text-align: right;"><span style="font-size: 10px;color: #2A2A2A;">결재 전에 문의 사항있으시면 언제든지 연락 주세요</span></td></tr>
					   					<tr>
					   						<td style="width: 150px; text-align: right;"><span style="color: #2A2A2A;">Seller&nbsp;:</span></td>
					   						<td style="width: 150px; text-align: left;">&nbsp;&nbsp;${selectedCart.seller.userName} &nbsp;&nbsp;<a href="#sellerInfo"><span style="color: #1B4E75;font-size: 11px;">판매자상세정보</span></a></td>
					   					</tr>
					   					<tr>
					   						<td style="text-align: right;"><span style="color: #2A2A2A;"><img src="/resources/css/images/gic/ic_phone_black_18dp_1x.png"/>&nbsp;:</span></td>
					   						<td style="text-align: left;">&nbsp;&nbsp;${selectedCart.seller.userId}</td>
					   					</tr>
					   				</table>		
		   						</td>
		   						<td style="width: 60px;text-align: center;"><img src="/resources/image/shopping_cart.png"></td>
		   						<td style="text-align: right;vertical-align: top; width: 50px;">
		   							<table style="width: 20px; text-align: center;background-color: #71B921;" align="right">
		   								<tr>
		   									<td>${selectedCart.cartNumber}</td>
		   								</tr>
		   							</table>
		   						</td>
		   					</tr>
		   				</table>
		   			</td>
		   		</tr>
		   	</table>
    	</div>
    </div>
    	
    <div class="row">
    	<div class="col-md-9">
		    <div class="row">
		    	<div class="col-md-10" style="padding-left: 0px;">
		    	
					<table style="width: 100%;padding: 0px 0px 0px 0px;" class="detail_table_c" >
	        				<colgroup>
	                              <col width="200px" />
	                              <col width="300px" />
	                              <col width="150px" />
	                              <col width="300px" />
	                        </colgroup> 					
					  		<tr>
						  		<td style="text-align: right;vertical-align: middle;">결재금액 :</td>
						  		<td class="value" style="vertical-align: middle;"><span style="font-size: 25px;font-weight: bold;">$ <fmt:formatNumber type="number" pattern="###.00" value="${selectedCart.totalPrice}" /></span></td>
						  		<td></td>
						  		<td></td>
					  		</tr>
					  		<tr style="height: 5px;"><td colspan="4" style="height: 5px;">&nbsp;</td></tr>
					  		<tr>
						  		<td style="text-align: right;vertical-align: top;">상품수령 또는 픽업일자 :</td>
						  		<td class="value" colspan="3" style="vertical-align: top;">
						  			<span id="productReceiveAndPickupDate"><span style="color: #AF0003;font-size: 15px;font-weight: bold;">상품수령일자 / 픽업일정이 선택되지 않았습니다.</span></span>
						  			&nbsp;&nbsp;
						  			[<a href="javascript:openChooseDeliveryDatePopup();"><span style="color: #3694DA;font-size: 11px;">상품수령 또는 픽업일자 선택</span></a>]
						  			&nbsp;
						  			[<a href="javascript:cancelChooseDeliveryDate();"><span style="color: #3694DA;font-size: 11px;">취소</span></a>]
						  		</td>
					  		</tr>
					  		<tr style="height: 5px;"><td colspan="4" style="height: 5px;">&nbsp;</td></tr>
					  		<tr>
						  		<td style="text-align: right;"><span class="required">* </span>결재방법선택 :</td>
						  		<td class="value" colspan="3"><c:out value="${cbxSellerPaymentMethod}" escapeXml="false"/></td>
						  	</tr>
						  	<tr>
						  		<td style="text-align: right;">상품수령지 주소 :</td>
						  		<td class="value" colspan="2">
						  			<span style="color: #7A0002;">${deliveryAddresss}</span>
						  			&nbsp;&nbsp;&nbsp;[<a href="javascript:openModifyDeliveryAddressPopup();"><span style="color: #3694DA;font-size: 11px;">상품수령지주소변경</span></a>]
						  		</td>
					  			<td></td>
						  	</tr>
						  	<tr>
						  		<td style="text-align: right;">판매자에게 메모남기기 :</td>
						  		<td class="value" colspan="2"><input class="form-control" type="text" id="name" name="name" value=''/></td>
					  			<td><span style="color: #8B8B8B;font-size: 11px;">예] 물건을 문앞에 놓고 가주세요.</span></td>
						  	</tr>
						  	<tr>
						  		<td></td>
						  		<td></td>
						  		<td style="text-align: right;" colspan="2"><button type="button" class="btn btn-danger" onclick="order()">주문완료</button></td>
						  	</tr>
						  	<c:choose>
						  		<c:when test="${selectedCart.seller.sellerHaveMinimumPayment == 'Y'}">
								  	<tr>
								  		<td style="text-align: right;" colspan="4">
								  			<div id="minimumPaymentMsgArea"> 현재 판매자의 최소 주문가능 주문액은 <span style="font-weight: bold;color: #BD0000;">$ <fmt:formatNumber type="number" pattern="###.00" value="${selectedCart.seller.sellerMinimumPaymentForPickup}" /></span> 입니다.</div>
								  		</td>
								  	</tr>
								  	<tr>
								  		<td style="text-align: right;" colspan="4">
								  			<b>${selectedCart.seller.sellerBusinessName}(${selectedCart.seller.userName}) </b>매장에서 더 주문하러 가기 : <a href="javascript:goSellerShop('${selectedCart.seller.userId}');"><span style="color: #0B45D3;">${selectedCart.seller.sellerBusinessName}</span> </a>
								  		</td>
								  	</tr>
						  		</c:when>
						  	</c:choose>
					  		<tr><td colspan="4" style="height: 20px;"></td></tr>
					</table>
					<input type="hidden" id="addressStreet" name="addressStreet" value="">
					<input type="hidden" id="addressSuburb" name="addressSuburb" value="">
					<input type="hidden" id="addressState" name="addressState" value="">
					<input type="hidden" id="addressPostcode" name="addressPostcode" value="">
					<input type="hidden" id="isPickup" name="isPickup" value="">
					<input type="hidden" id="yyyyMmDd" name="yyyyMmDd" value="">
					<input type="hidden" id="amPm" name="amPm" value="">
					<input type="hidden" id="sellerId" name="sellerId" value="${selectedCart.seller.userId}">
		    </div>
		</div>
	</div>




    <div class="row">
    	<div class="col-md-9">
<form action="/cart/paymentresult.yum" method="POST">
  <script
    src="https://checkout.stripe.com/checkout.js" class="stripe-button"
    data-key="pk_test_9vypCb6MRqSERMISU4gxHt5W"
    data-amount="999"
    data-name="Coupang.com.au"
    data-description="Hello"
    data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
    data-locale="auto"
    data-zip-code="true">
  </script>
</form>        	
    	</div>
    </div>	



    
    <div class="row">
      <div class="col-md-9">
      	<span class="subtitle"> 현재 내장바구니 상세정보</span>
		<hr class="subtitle"/>	
      		<c:forEach var="product" items="${selectedCart.products}" varStatus="count">
	            <table style="width: 100%; border: 1px;border-color: #EBEBEB;" border="1"><tr><td>
		    	<table class="detail_table_c" style="border-color: #DADADA;border: 1px;" border="1">
		         <colgroup>
		              <col width="100px" />
		              <col width="150px" />
		              <col width="100px" />
		              <col width="150px" />
		              <col width="*" />
		         </colgroup>
		         <tr>
		         	<td colspan="4"><span style="color: #450C0D;font-size: 15px;font-weight: bold;">${product.name}</span></td>
		         	<td></td>
		         </tr>
				
				<c:choose>
						<c:when test="${not empty product.productOrderedOptions}">	
					         <tr>
					         	<td>&nbsp;</td>
					         	<td colspan="3">
					         		<c:forEach var="productOption" items="${product.productOrderedOptions}" varStatus="cnt">
					         			${productOption.optionName} : ${productOption.optionValue} , 
					         		</c:forEach>
					         	</td>
				         	   <td></td>
					         </tr>						
						</c:when>
				</c:choose>
		         
		         <tr>
		         	<td class="label">주문수량 :</td><td class="value">${product.orderAmount}</td>
		         	<td class="label">단가 :</td><td class="value">$ ${product.unitPrice}</td>
				    <td></td>
		         </tr>
		         <tr>
		         	<td colspan="3"></td>
		         	<td class="value"><span style="font-weight: bold;color: #3694DA;">$  ${product.orderAmount * product.unitPrice}</span></td>
				    <td><button type="button" class="btn btn-success btn-xs" onclick="removeItem('${product.orderItemId}')">장바구니에서 삭제</button></td>
		         </tr>
		        </table>
      		</td></tr></table>
		    <br />       		
      		</c:forEach>
      </div>
    </div>
	
	
	
   	
</body>
</html>