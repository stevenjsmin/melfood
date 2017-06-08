<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	이 파일의 레이아웃은, 판매자의 상품을 주문하는 페이지이다.
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
          		 <!-- 판매자의 다른상품 -->
                 <div class="col-md-3"  style="background-color: #F6F6F6;padding: 0px 5px 20px 5px; ">
                 	<tiles:insertAttribute name="seller_products" />
                 </div>
                 
                 <!-- 상품주문 -->
                 <div class="col-md-9" style="padding: 0px 5px 20px 5px;">
                 	<div class="row">
                 		<div class="col-md-12">
                			<tiles:insertAttribute name="order_product" />
                			<br/>
                			<br/>                			
                			<br/>                			
                 		</div>
                 	</div>
                 	
                 </div>
          </div>
     </div>
     <div class="footer">
          <tiles:insertAttribute name="footer" />
     </div>
</body>
</html>