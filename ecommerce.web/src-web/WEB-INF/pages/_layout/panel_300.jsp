<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	화면 상.하로 Header와 Footer가 있고
	화면 중앙에는 왼쪽에는 메인메뉴, 오른쪽에는 메인화면이 있는 구조이다.
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

     <div class="mainMenu" id="mainMenu">
         <tiles:insertAttribute name="main-menu" />
     </div>


     <div class="content" id="contentsDiv">
          <div class="row">
                 <div class="col-md-12" style="padding-left: 0px;">
                    <table style="width: 100%;">
                         <tr>
                              <td style="font-size: 15px;font-weight: bold;color: #f89e1f;"><tiles:getAsString name="title" /></td>
                              <td style="color: #9c9c9c;text-align: right;"><tiles:getAsString name="navigation" /></td>
                         </tr>
                         <tr>
                              <td style="padding-top: 10px;" colspan="2"><tiles:insertAttribute name="body" /></td>
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