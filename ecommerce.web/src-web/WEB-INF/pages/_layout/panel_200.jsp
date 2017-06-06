<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	화면 상.하로 Header와 Footer가 있고
	화면 중앙에는 왼쪽에는 메인메뉴 없이 내용만 있는 구조이다.
	
	로그인페이지나, 메인화면에 왼쪽 메뉴가 필요없을 경우 사용되는 레이아웃이다.
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
          <tiles:insertAttribute name="body" />
     </div>
     <div class="footer">
          <tiles:insertAttribute name="footer" />
     </div>
</body>
</html>