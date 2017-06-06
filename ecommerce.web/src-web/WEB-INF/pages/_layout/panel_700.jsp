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
                 <div class="col-md-2"  style="background-color: #E5E5E5;padding: 0px 5px 20px 5px; ">
                 	<tiles:insertAttribute name="customer_carts" />
                 </div>
                 <div class="col-md-10" style="padding: 0px 5px 20px 5px;">
                 	<table style="width: 100%;">
                 		<tr>
                 			<td>
                 				<tiles:insertAttribute name="selected_cart" />
                 				<br/><br/><br/><br/>
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