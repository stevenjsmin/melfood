<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!-- 
	화면 상.하로 Header와 Footer가 있고
	화면 중앙에는 왼쪽에는 메인메뉴, 오른쪽에는 메인화면이 있는 구조이다.
	
	추가로, 이곳 레이아웃에서는 상품관리를 위하여 화면 메인컨텐츠의 상단에 상품관리와 관련된 탭메뉴가있는 관리자 페이지레이아웃 정의
 -->
<html lang="en">
<head>
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="resource" />
<tiles:insertAttribute name="commonhtml" />
<style>
     .navbar {
          margin-bottom: 0px;
          margin-top: 5px;
     }
</style>
<script type="text/javascript">
$(document).ready(function () {
     var pathName = window.location.pathname;
     var barElement = null;
     $("#prodProcessBar li").removeClass("active");
     
     if (pathName.startsWith("/admin/productmgt/overviewProductInfo.yum")) {
    	$("#prodOverview").addClass("active")
    	
     } else if(pathName.startsWith("/admin/productmgt/registProductMasterInfoForm.yum")) {
    	$("#prodMasterInfo").addClass("active")
    	
     } else if(pathName.startsWith("/admin/productmgt/modifyProductMasterInfoForm.yum")) {
    	$("#prodMasterInfo").addClass("active")
    	
     } else if(pathName.startsWith("/admin/productmgt/option/")) {
     	$("#prodOptionInfo").addClass("active")   	
    	
     } else if(pathName.startsWith("/admin/productmgt/image/")) {
    	$("#prodImageInfo").addClass("active")
    	
     } else if(pathName.startsWith("/admin/productmgt/alertDeleteAllProductInfo.yum")) {
    	$("#prodInfoDelete").addClass("active")
    	
     }
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>
<body>
     <div class="header">
          <tiles:insertAttribute name="header" />
     </div>
     <div class="content" id="contentsDiv">
          <div class="row">
                 <div class="col-md-2"  style="padding-left: 0px;">
                    <table style="width: 100%;vertical-align: top; background-color: #f9f9f9;">
                         <tr><td style="vertical-align: top; text-align: right; padding: 5px 5px 5px 0px;height: 20px;"><span style="color: #929292;font-weight: bold;">[ ADMIN ]</span></td></tr>
                         <tr><td style="vertical-align: top;"><tiles:insertAttribute name="left-menu" /></td></tr>
                         <tr><td>&nbsp;</td></tr>
                    </table>
                 </div>
                 <div class="col-md-10" style="padding-left: 0px;">
                    <table style="width: 100%;">
                         <tr>
                              <td style="font-size: 15px;font-weight: bold;color: #f89e1f;"><tiles:getAsString name="title" /></td>
                              <td style="color: #9c9c9c;text-align: right;"><tiles:getAsString name="navigation" /></td>
                         </tr>
                         <tr>
                              <td colspan="2">
                                   <nav class="navbar navbar-inverse">
                                     <div class="container-fluid">
                                       <ul id="prodProcessBar" class="nav navbar-nav">
                                         <li id="prodOverview" class="active"><a href="javascript:goProdManagement('/admin/productmgt/overviewProductInfo.yum');"><b>Overview</b></a></li>
                                         <li id="prodMasterInfo" class="active"><a href="javascript:goProdManagement('/admin/productmgt/modifyProductMasterInfoForm.yum');"><span class="glyphicon glyphicon-tag"></span> 기본정보</a></li>
                                         <li id="prodOptionInfo" class="active"><a href="javascript:goProdManagement('/admin/productmgt/option/Main.yum');"><span class="glyphicon glyphicon-check"></span> 옵션정보</a></li>
                                         <li id="prodImageInfo" class="active"><a href="javascript:goProdManagement('/admin/productmgt/image/Main.yum');"><span class="glyphicon glyphicon-picture"></span> 이미지정보</a></li>
                                         <li id="prodInfoDelete" class="active"><a href="javascript:goProdManagement('/admin/productmgt/alertDeleteAllProductInfo.yum');"><span class="glyphicon glyphicon-trash"></span> 삭제</a></li>
                                       </ul>
                                     </div>
                                   </nav>                              
                              </td>
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