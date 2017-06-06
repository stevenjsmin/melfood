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
		   				<b><span style="color: #7F7F7F;"><b>${seller.sellerBusinessName}</b></span></b>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td colspan="2" style="padding: 5px;">
		   				<table style="width: 100%;">
		   					<tr>
		   						<td><img src="/img/?f=31" style="width: 90px;" class="img-circle"></td>
		   						<td style="vertical-align: middle;padding-left: 10px;">
		   							<br>안녕하세요. <b>${seller.sellerBusinessName}</b>을 찾아주셔서 감사합니다.<br><br>
		   							<span style="color: #8B8B8B;">
		   							제품 구입에관한 문의 사항이 있으시면 언제든지 전화/메일/메신저를 통해 연락 주시면 빠른시간내에 답변드리도록 하겠습니다.<br>
		   							<ul>
		   								<li>전화 : ${seller.mobile }</li>
		   								<li>메일 : ${seller.email }</li>
		   							</ul>
		   							<br>
		   							 고맙습니다.
		   							</span>
		   						</td>
		   					</tr>
		   				</table>
		   			</td>
		   		</tr>
		   	</table>
		   	<br/>
        </div>
    </div>  
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
        	<!--  판매자 공지사항 -->
			<div class="chat-dialogbox">
			    <div class="chat-body">
			      <span class="chat-tip chat-tip-up"></span>
			      <div class="chat-message">
			        <span>주문하시기전에 거주하시는 지역에 배송일정을 확인해주세요.</span>
			      </div>
			    </div>
			</div>
			  
			<div class="chat-dialogbox">
			    <div class="chat-body">
			      <span class="chat-tip chat-tip-up"></span>
			      <div class="chat-message">
			        <span>이번에 새로운 찹쌀 팥빵을 런칭하게되었습니다. 보다 든든한 간식이 될수 있을겁니다. 맛있게드세요.</span>
			      </div>
			    </div>
			</div>
			<div class="row">
        		<div class="col-md-12" style="text-align: right;padding-right: 10px;">
        			<a href="javascript:allNotice();"><span style="font-size: 9px; color: #DAC20D;">모든공지사항보기</span></a>
        			<span style="font-weight: bold;color: #BCBCBC;">|</span>
        			<a href="javascript:allNotice();"><span style="font-size: 9px; color: #DAC20D;">문의하기</span></a>
        		</div>
        	</div>
		    
		</div>
	</div>	
       	
					            	
</body>
</html>