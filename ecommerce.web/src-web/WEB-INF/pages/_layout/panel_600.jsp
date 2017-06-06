<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	화면 좌측에는 판매자가 판매하는 상품목록이 보여지고(20% 너비),
	화면 우측에서 선택한 상품의 주문화면이 나온다.(80% 너비)
	    - 화면 중앙에는 주문화면(이미지, 주문버틈, 설명)과 판매자 정보가 보여지게된다.
 -->
 
<html lang="en">
<head>
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="resource" />
<tiles:insertAttribute name="commonhtml" />
</head>
<body>
     <div class="header">
          <tiles:insertAttribute name="header" />
     </div>
     <div class="content" id="contentsDiv">
          <div class="row">
                 <div class="col-md-2"  style="background-color: #E5E5E5;padding: 0px 5px 20px 5px; ">
                 	<tiles:insertAttribute name="seller_products" />
                 </div>
                 <div class="col-md-10" style="padding: 0px 5px 20px 5px;">
                 	<table style="width: 100%;">
                 		<tr>
                 			<td>
                 				<tiles:insertAttribute name="order_product" />
                 				<br/><br/><br/><br/>
                 			</td>
                 		</tr>
                 		<tr>
                 			<td>
                 				<tiles:insertAttribute name="seller_delivery_schedule" />
                 				<br/>
                 				<br/>
                 				<br/>
                 				<br/>
                 			</td>
                 		</tr>
                 		<tr>
                 			<td>
                 				<tiles:insertAttribute name="seller_info" />
                 				<br/>
                 				<br/>
                 				<br/>
                 				<br/>
                 			</td>
                 		</tr>
                 	</table>
                 </div>
          </div>
     </div>
     <div class="footer">
          <tiles:insertAttribute name="footer" />
     </div>
</body>
</html>