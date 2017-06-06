<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<head>
<!-- <META http-equiv="refresh" content="1;URL=redirect.html"> -->
<!-- META http-equiv="refresh" content="1;URL=http://rpc.dev.utilitiessoftwareservices.com:8080/rpc/redirect.html"-->

<script type="text/javascript">
$(document).ready(function () {
		$("#amount").kendoNumericTextBox({
	    	 max: 99999,
	    	 min: 1,
	         format: "n0",
	         spin: calculateTotalPrice,
	         change: calculateTotalPrice,
	         
	   });
		
		var slideCount = $('#slider ul li').length;
		var slideWidth = $('#slider ul li').width();
		var slideHeight = $('#slider ul li').height();
		var sliderUlWidth = slideCount * slideWidth;
		
		$('#slider').css({ width: slideWidth, height: slideHeight });
		
		$('#slider ul').css({ width: sliderUlWidth, marginLeft: - slideWidth });
		
	    $('#slider ul li:last-child').prependTo('#slider ul');

	    function moveLeft() {
	        $('#slider ul').animate({
	            left: + slideWidth
	        }, 200, function () {
	            $('#slider ul li:last-child').prependTo('#slider ul');
	            $('#slider ul').css('left', '');
	        });
	    };

	    function moveRight() {
	        $('#slider ul').animate({
	            left: - slideWidth
	        }, 200, function () {
	            $('#slider ul li:first-child').appendTo('#slider ul');
	            $('#slider ul').css('left', '');
	        });
	    };

	    $('a.control_prev').click(function () {
	        moveLeft();
	    });

	    $('a.control_next').click(function () {
	        moveRight();
	    });    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	function goPayment(){
		var seller = '${product.seller}';
		document.location.href = "/cart/mycart.yum?selectedSellerId=" + seller;
	}
	function calculateTotalPrice(e){
		var amount = $('#amount').val();
		var unitPrice = '${product.unitPrice}';
		var totalPrice = (unitPrice * amount);
		
		$('#totalPrice').html(toCurrency(totalPrice));
	}
</script>

<script type="text/javascript">
	function goAddCart(){
		if (orderValidate() == false) return;
		
		var prodId = '${product.prodId}';
		var unitPrice = '${product.unitPrice}';
	    var amount = $('#amount').val();
		var productOptions = '';
	    $('#productOptions select').each(function() {
	        var select = $(this);
	        var isMandatory = select.attr('ismandatory');
	        var selectId = select.attr('id');
	        var selectName = select.attr('name');
	        var value = select.val();
	        
	        // 상품의 옵션선택이 있는 경우 각 항목을 '항목명=선택값'형태로 ^로 구분하여 가저온다.
	        if(value != '') {
	        	productOptions = productOptions + selectName + "=" + value + "^";
	        }
	    });
	    
	    $.ajax({
	         url  : "/cart/addProductOnCart.yum",
	         data      : {
	        	 prodId : prodId,
	        	 amount : amount,
	        	 productOptions : productOptions
	         },
	         success : callbackAddCart
	    }); 
	    // infoPopup('prodId=' + prodId + ", unitPrice=" + unitPrice + ", amount=" + amount + ", productOptions=[" +  productOptions + "]");
	}

	function callbackAddCart(data) {
	    var message = data.message;
	    var resultCode = data.resultCode;
	
	    if (resultCode != "0") {
	         warningPopup(data.message);
	    } else {
	    	 infoPopup(data.message); 
	    }
	}
	
	
	
	var checkObject = [];
	function orderValidate() {
		var count;
		var elementObj = "";
		var validation = true;

		// 값검증 결과 초기화
		for (count = 0; count < checkObject.length; count++) {
			elementObj = "#" + checkObject[count];
			$(elementObj).css({
				'background' : '#ECF5FF',
				'border-color' : '#DFDFDF'
			});
		}
		checkObject = [];
		var prefix = "- &nbsp;&nbsp;";
		var message = "";

		// 필수옵션항목을 체크한다.
	    $('#productOptions select').each(function() {
	        var select = $(this);
	        var isMandatory = select.attr('ismandatory');
	        var selectId = select.attr('id');
	        var selectName = select.attr('name');
	        var value = select.val();
	        
	        if(isMandatory == 'Y' && value == '') {
				message = message + prefix + selectName + " 항목선택은 필수입니다.<br>";
				checkObject[checkObject.length] = selectId;
				validation = false;
	        }
	    });

		
		// 검증된 필드들을 마킹한다.
		for (count = 0; count < checkObject.length; count++) {
			elementObj = "#" + checkObject[count];
			$(elementObj).css({
				'background' : '#fffacd',
				'border-color' : '#DF0000',
				'border' : '1px solid #f00'
			});
		}
		if (validation == false) {
			// 오류가 있는 경우 경고 창을 보여준다.
			warningPopup(message);
		}
		return validation;
	}
</script>



<style>
	#slider {
	  position: relative;
	  overflow: hidden;
	  margin: 0px auto 0 auto;
	  border-radius: 4px;
	}
	
	#slider ul {
	  position: relative;
	  margin: 0;
	  padding: 0;
	  height: 300px;
	  list-style: none;
	}
	
	#slider ul li {
	  position: relative;
	  display: block;
	  float: left;
	  margin: 0;
	  padding: 0;
	  width: 350px;
	  height: 250px;
	  background: #ccc;
	  text-align: center;
	  line-height: 300px;
	}
	
	a.control_prev, a.control_next {
	  position: absolute;
	  top: 40%;
	  z-index: 999;
	  display: block;
	  padding: 4% 3%;
	  width: auto;
	  height: auto;
	  background: #2a2a2a;
	  color: #fff;
	  text-decoration: none;
	  font-weight: 600;
	  font-size: 18px;
	  opacity: 0.8;
	  cursor: pointer;
	}
	
	a.control_prev:hover, a.control_next:hover {
	  opacity: 1;
	  -webkit-transition: all 0.2s ease;
	}
	
	a.control_prev {
	  border-radius: 0 2px 2px 0;
	}
	
	a.control_next {
	  right: 0;
	  border-radius: 2px 0 0 2px;
	}
	
	.slider_option {
	  position: relative;
	  margin: 10px auto;
	  width: 160px;
	  font-size: 18px;
	}
	
</style>
</head>


<body>


    <div class="row">
      <div class="col-md-12">
	   	<table style="width: 100%;border-collapse:separate;border-radius:20px;-moz-border-radius:20px;">
	   		<tr>
	   			<td style="padding: 5px;background-color: #3794DA;">
	   				<span style="font-weight: bold;font-size: 20px;color: #FFFFFF;">상품 주문</span>
	   				<span style="font-weight: bold;font-size: 20px;color: #FFFFFF;"> &nbsp; > &nbsp;</span>
	   				<span style="font-weight: bold;font-size: 15px;color: #C7C7C7;">배송일짜/결재</span>
	   				<span style="font-weight: bold;font-size: 20px;color: #ACACAC;"> &nbsp;&nbsp; > &nbsp;</span>
	   				<span style="font-weight: bold;font-size: 15px;color: #C7C7C7;">완료</span>
	   			</td>
	   		</tr>
	   	</table>
	   	<br/>
      </div>
    </div> 


    <div class="row">
        <div class="col-md-10" style="padding-left: 50px;">
        	<table style="width: 100%;" border="0">
        		<tr>
        			<td style="width: 355px;vertical-align: top;text-align: left;">
						<!-- 상품 이미지구성 -->
						 <c:choose>
								<c:when test="${not empty productImages}">
									<div id="slider">
									  <a href="#" class="control_next">>></a>
									  <a href="#" class="control_prev"><<</a>
									  <ul>					
									    <c:forEach var="entry" items="${productImages}" varStatus="count" begin="0">
											<li style="background: #FFFFFF;"><img src="/img/?f=${entry.imageFileId}" style="width: 200px;height: 200px;"/></li>
									    </c:forEach>
									  </ul>  
									</div>					      				
								</c:when>
								<c:otherwise>
									<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;color: #EFEFEF;">등록된 이미지가 없습니다.</span>
								</c:otherwise>
						</c:choose>           			
        			</td>
        			<td style="vertical-align: top;text-align: left;padding-left: 20px;"">
	        			<span style="font-size: 20px;font-weight: bold; color: #3794DA;">${product.name}</span>
				    	<c:choose>
					         <c:when test="${not empty productOptions}">
				        			<br/>
				        			<br/>
								    <span class="subtitle"> 상품옵션</span>
								    <hr class="subtitle"/>					         
									<table class="detail_table_c" id="productOptions">	                                  
									    <c:forEach var="entry" items="${productOptions}" varStatus="count" begin="0">
									    	<tr>
									    		<td class="label">${entry.optionLabel}</td>
									    		<td class="value"><c:out value="${entry.optionCmbx}" escapeXml="false"/></td>
									    		<td></td>
									    		<td></td>
									    	</tr>
									    </c:forEach>
								    </table>    
					         </c:when>
				    	</c:choose>
				    	
				    	<br/>
				    	<br/>
				    	<table>
				    		<tr>
				    			<td><span style="font-size: 15px;font-weight: bold;color: #000000;">단가 :</span> <span style="font-size: 15px;font-weight: bold;color: #CE1521;" id="totalPrice">$ ${product.unitPrice}</span></td>
				    		</tr>
				    	</table>
				    	
				    	
				    	<br/>
				    	<table style="width: 100%;" border="0">
				    		<tr>
				    			<td>
					    			<span class="subtitle"> 주문수량</span>
						    		<hr class="subtitle"/>   
						    		<input type="text" id="amount" name="amount" value='1'/>	
				    			</td>
				    			<td>&nbsp;&nbsp;</td>
				    			<td>
				    				<c:choose>
				    					<c:when test="${sessionUser != null}"><a href="javascript:goAddCart();"><img src="/resources/image/add_cart1.png" style="width: 60px;"></a></c:when>
				    					<c:otherwise><a href="javascript:doLoginPopup();"><img src="/resources/image/add_cart1.png" style="width: 60px;"></a></c:otherwise>
				    				</c:choose>				    			
				    			</td>
				    		</tr>
				    		<tr><td colspan="3">&nbsp;</td></tr>
				    		<tr>
				    			<td colspan="3">
				    				<c:choose>
				    					<c:when test="${sessionUser != null}"><a href="javascript:goPayment();"><img src="/resources/image/do_payment2.png" style="height: 40px;"></a></c:when>
				    					<c:otherwise><a href="javascript:doLoginPopup();"><img src="/resources/image/do_payment2.png" style="height: 40px;"></a></c:otherwise>
				    				</c:choose>
				    			</td>
				    		</tr>
				    		<tr><td colspan="3" style="height: 10px;">&nbsp;</td></tr>
				    		<tr><td colspan="3"><span style="font-size: 10px;color: #A8A8A8;">주문전에 문의 사항있으시면 언제든지 전화/메신저/메일 주세요 : <a href="#sellerInfo">판매자정보 바로가기</a></span></td></tr>
				    	</table>
        			</td>
        		</tr>
        	</table>
		</div>
	</div>	

    <br/> 
    <br/> 
    <br/> 
    <br/> 
    <div class="row">
        <div class="col-md-9">
		    <span class="subtitle"> 상품설명</span>
		    <hr class="subtitle"/>
			<table class="detail_table_c">	                                  
		    	<tr>
		    		<td class="value">${product.description}</td>
		    	</tr>
		    </table>                          	
        </div>
    </div>    
		
   	
</body>
</html>