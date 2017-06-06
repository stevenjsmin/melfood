<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	이 파일의 레이아웃은, 판매자의 샵을 구성하는 페이지 레이아웃 프레임이다.
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
                 <!-- 상품판매자 정보 : 기본정보, 공지사항, 배송일정 -->
                 <div class="col-md-3"  style="background-color: #F6F6F6;padding: 0px 5px 20px 5px; ">
                 	<tiles:insertAttribute name="seller_notice_delivery" />
                 </div>
                 
                 <!-- 상품 판매자 제품 디스플래이 -->
                 <div class="col-md-9" style="padding: 0px 5px 20px 5px;">
                 	<table style="width: 100%;">
                 		<tr>
                 			<td>
                 				<tiles:insertAttribute name="seller_product_mainpage" />
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