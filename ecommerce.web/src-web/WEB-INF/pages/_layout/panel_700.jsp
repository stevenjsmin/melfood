<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	화면 좌측에는 장바구니 목록이 보여지고(20% 너비),
	화면 우측에는 선택한 장바구니에대해서 결재전의 화면(카트내용확인, 배송일정, 결재방법선택)이 나온다.
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
          		<!-- 고객의 장바구니들 -->
                 <div class="col-md-3"  style="background-color: #E5E5E5;padding: 0px 5px 20px 5px; ">
                 	<tiles:insertAttribute name="customer_carts" />
                 </div>
                 
                 <!-- 선택된 장바구니정보 -->
                 <div class="col-md-9" style="padding: 0px 5px 20px 5px;">
                 	<div class="row">
                 		<div class="col-md-12" style="padding: 0px;">
                 			<tiles:insertAttribute name="selected_cart" />                 
               			</div>
               		</div>	  
                 	<div class="row">
                 		<div class="col-md-12" style="padding: 0px;">
                 			<tiles:insertAttribute name="check_before_buy" />                 
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