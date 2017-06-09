<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<script src="/resources/jssor/js/jssor.slider-21.1.6.mini.js" type="text/javascript"></script>
    
    
<script type="text/javascript">
$(document).ready(function () {
		$("#amount").kendoNumericTextBox({
	    	 max: 99999,
	    	 min: 1,
	         format: "n0",
	         spin: calculateTotalPrice,
	         change: calculateTotalPrice,
	         
	    });
		
        var jssor_1_SlideoTransitions = [
                      [{b:0,d:600,y:-290,e:{y:27}}],
                      [{b:0,d:1000,y:185},{b:1000,d:500,o:-1},{b:1500,d:500,o:1},{b:2000,d:1500,r:360},{b:3500,d:1000,rX:30},{b:4500,d:500,rX:-30},{b:5000,d:1000,rY:30},{b:6000,d:500,rY:-30},{b:6500,d:500,sX:1},{b:7000,d:500,sX:-1},{b:7500,d:500,sY:1},{b:8000,d:500,sY:-1},{b:8500,d:500,kX:30},{b:9000,d:500,kX:-30},{b:9500,d:500,kY:30},{b:10000,d:500,kY:-30},{b:10500,d:500,c:{x:87.50,t:-87.50}},{b:11000,d:500,c:{x:-87.50,t:87.50}}],
                      [{b:0,d:600,x:410,e:{x:27}}],
                      [{b:-1,d:1,o:-1},{b:0,d:600,o:1,e:{o:5}}],
                      [{b:-1,d:1,c:{x:175.0,t:-175.0}},{b:0,d:800,c:{x:-175.0,t:175.0},e:{c:{x:7,t:7}}}],
                      [{b:-1,d:1,o:-1},{b:0,d:600,x:-570,o:1,e:{x:6}}],
                      [{b:-1,d:1,o:-1,r:-180},{b:0,d:800,o:1,r:180,e:{r:7}}],
                      [{b:0,d:1000,y:80,e:{y:24}},{b:1000,d:1100,x:570,y:170,o:-1,r:30,sX:9,sY:9,e:{x:2,y:6,r:1,sX:5,sY:5}}],
                      [{b:2000,d:600,rY:30}],
                      [{b:0,d:500,x:-105},{b:500,d:500,x:230},{b:1000,d:500,y:-120},{b:1500,d:500,x:-70,y:120},{b:2600,d:500,y:-80},{b:3100,d:900,y:160,e:{y:24}}],
                      [{b:0,d:1000,o:-0.4,rX:2,rY:1},{b:1000,d:1000,rY:1},{b:2000,d:1000,rX:-1},{b:3000,d:1000,rY:-1},{b:4000,d:1000,o:0.4,rX:-1,rY:-1}]
                  ];

        var jssor_1_options = {
        	$AutoPlay: true,
         	$Idle: 2000,
         	$CaptionSliderOptions: {
         			$Class: $JssorCaptionSlideo$,
           			$Transitions: jssor_1_SlideoTransitions,
           			$Breaks: [ [{d:2000,b:1000}]]
         	},
        	$ArrowNavigatorOptions: { $Class: $JssorArrowNavigator$ },
        	$BulletNavigatorOptions: {  $Class: $JssorBulletNavigator$ }
        };

        var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

        /*responsive code begin*/
        /*you can remove responsive code if you don't want the slider scales while window resizing*/
        function ScaleSlider() {
           var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
           if (refSize) {
               refSize = Math.min(refSize, 300);
               jssor_1_slider.$ScaleWidth(refSize);
           } else {
               window.setTimeout(ScaleSlider, 30);
           }
        }
        
       	ScaleSlider();
       	$(window).bind("load", ScaleSlider);
       	$(window).bind("resize", ScaleSlider);
       	$(window).bind("orientationchange", ScaleSlider);
        /*responsive code end*/		
		
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
	    	 // infoPopup(data.message);
			BootstrapDialog.alert({
	            title: 'INFO  :: 호주가 즐거운 이유, 쿠빵!!',
	            message: data.message,
	            type: BootstrapDialog.TYPE_PRIMARY, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	            closable: true, // <-- Default value is false
	            draggable: true, // <-- Default value is false
	            buttonLabel: '닫기', // <-- Default value is 'OK',
	            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
	            callback: function(result) {
	                if(result) {
	                	parent.closeAddCartPopup();
	                }
	            }
	        }); 	    	 
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
        /* jssor slider bullet navigator skin 01 css */
        /*
        .jssorb01 div           (normal)
        .jssorb01 div:hover     (normal mouseover)
        .jssorb01 .av           (active)
        .jssorb01 .av:hover     (active mouseover)
        .jssorb01 .dn           (mousedown)
        */
        .jssorb01 {
            position: absolute;
        }
        .jssorb01 div, .jssorb01 div:hover, .jssorb01 .av {
            position: absolute;
            /* size of bullet elment */
            width: 12px;
            height: 12px;
            filter: alpha(opacity=70);
            opacity: .7;
            overflow: hidden;
            cursor: pointer;
            border: #000 1px solid;
        }
        .jssorb01 div { background-color: gray; }
        .jssorb01 div:hover, .jssorb01 .av:hover { background-color: #d3d3d3; }
        .jssorb01 .av { background-color: #fff; }
        .jssorb01 .dn, .jssorb01 .dn:hover { background-color: #555555; }

        /* jssor slider arrow navigator skin 02 css */
        /*
        .jssora02l                  (normal)
        .jssora02r                  (normal)
        .jssora02l:hover            (normal mouseover)
        .jssora02r:hover            (normal mouseover)
        .jssora02l.jssora02ldn      (mousedown)
        .jssora02r.jssora02rdn      (mousedown)
        .jssora02l.jssora02lds      (disabled)
        .jssora02r.jssora02rds      (disabled)
        */
        .jssora02l, .jssora02r {
            display: block;
            position: absolute;
            /* size of arrow element */
            width: 55px;
            height: 55px;
            cursor: pointer;
            background: url('/resources/jssor/img/a02.png') no-repeat;
            overflow: hidden;
        }
        .jssora02l { background-position: -3px -33px; }
        .jssora02r { background-position: -63px -33px; }
        .jssora02l:hover { background-position: -123px -33px; }
        .jssora02r:hover { background-position: -183px -33px; }
        .jssora02l.jssora02ldn { background-position: -3px -33px; }
        .jssora02r.jssora02rdn { background-position: -63px -33px; }
        .jssora02l.jssora02lds { background-position: -3px -33px; opacity: .3; pointer-events: none; }
        .jssora02r.jssora02rds { background-position: -63px -33px; opacity: .3; pointer-events: none; }
    </style>
</head>


<body>

    <div class="row">
        <div class="col-md-10">
        	<table style="width: 100%;" border="0">
        		<tr>
        			<td style="width: 355px;vertical-align: top;text-align: left;border: 1px solid #EADEAD;vertical-align: middle;">
						<!-- 상품 이미지구성 -->
						 <c:choose>
								<c:when test="${not empty productImages}">

									<!-- 이미지 슬라이드 구성 : 시작 -->
								    <div id="jssor_1" style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 300px; height: 300px; overflow: hidden; visibility: hidden;">
								        <!-- Loading Screen -->
								        <div data-u="loading" style="position: absolute; top: 0px; left: 0px;">
								            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
								            <div style="position:absolute;display:block;background:url('/resources/jssor/img/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
								        </div>
								        <div data-u="slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 300px; height: 300px; overflow: hidden;">
								            <c:forEach var="entry" items="${productImages}" varStatus="count" begin="0">
									            <div data-p="112.50">
									                <img data-u="image" src="/img/?f=${entry.imageFileId}" />
									            </div>								            
								            </c:forEach>
								        </div>
								        <!-- Bullet Navigator -->
								        <div data-u="navigator" class="jssorb01" style="bottom:16px;right:16px;">
								            <div data-u="prototype" style="width:12px;height:12px;"></div>
								        </div>
								        <!-- Arrow Navigator -->
								        <span data-u="arrowleft" class="jssora02l" style="top:0px;left:8px;width:55px;height:55px;" data-autocenter="2"></span>
								        <span data-u="arrowright" class="jssora02r" style="top:0px;right:8px;width:55px;height:55px;" data-autocenter="2"></span>
								    </div>
									<!-- 이미지 슬라이드 구성 : 종료 -->
					      				
								</c:when>
								<c:otherwise>
									<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;color: #D6D6D6;">등록된 이미지가 없습니다.</span>
								</c:otherwise>
						</c:choose>           			
        			</td>
        			<td style="vertical-align: top;text-align: left;padding-left: 20px;"">
        			
        			
	        			<span style="font-size: 20px;font-weight: bold; color: #0A5499;">${product.name}</span>
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
				    	
				    	<table>
				    		<tr>
				    			<td style="padding-top: 20px;"><span style="font-size: 15px;font-weight: bold;color: #000000;">단가 :</span> <span style="font-size: 15px;font-weight: bold;color: #CE1521;" id="totalPrice">$ ${product.unitPrice}</span></td>
				    		</tr>
				    	</table>
				    	
				    	
				    	<br/>
				    	<span class="subtitle"> 주문수량</span>
						<hr class="subtitle"/> 
				    	<table style="width: 100%;">
				    		<tr>
				    			<td style="vertical-align: top;width: 200px;">
						    		<input type="text" id="amount" name="amount" value='1'/>	
				    			</td>
				    			<td style="text-align: right;">
				    				<c:choose>
				    					<c:when test="${sessionUser != null}">
				    							<button type="button" class="btn btn-danger btn-md" onclick="goAddCart()"> <span class="glyphicon glyphicon-shopping-cart" style="font-size: 12px;font-weight: bold;"> 장바구니담기</span> </button>
					    						<button type="button" class="btn btn-success btn-md" onclick="parent.closeAddCartPopup()"><span class="glyphicon glyphicon-home" style="font-size: 12px;font-weight: bold;"> 쇼핑계속하기</span></button>
				    					</c:when>
				    					<c:otherwise>
				    						<button type="button" class="btn btn-danger btn-md" onclick="doLoginPopup()"><span class="glyphicon glyphicon-shopping-cart" style="font-size: 12px;font-weight: bold;"> 장바구니담기</span></button>
				    						<button type="button" class="btn btn-success btn-md" onclick="parent.closeAddCartPopup()"><span class="glyphicon glyphicon-home" style="font-size: 12px;font-weight: bold;"> 쇼핑계속하기</span></button>
				    					</c:otherwise>
				    				</c:choose>
				    			</td>
				    		</tr>

				    	</table>
				    	
				    	<table align="right">
				    		<tr>
				    			<td style="padding-top: 5px;text-align: right;">
				    				<c:choose>
				    					<c:when test="${sessionUser != null}">
				    							<a href="javascript:parent.goPaymentFromSellerMain();"><span style="color: #808080;font-size: 11px;">[ 결재하러가기 ]</span></a>
				    					</c:when>
				    					<c:otherwise>
				    							<a href="javascript:doLoginPopup();"><span style="color: #808080;font-size: 11px;">[ 결재하러가기 ]</span></a>
				    					</c:otherwise>
				    				</c:choose>				    			
				    			</td>
				    		</tr>
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
			<table class="detail_table_c">	                                  
		    	<tr>
		    		<td class="value">${product.description}</td>
		    	</tr>
		    </table>                          	
        </div>
    </div>    
		
   	
</body>
</html>