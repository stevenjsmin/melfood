<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/resources/owlcarousel/owl.carousel.min.css" rel="stylesheet" media="screen">
<link href="/resources/owlcarousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<script src="/resources/owlcarousel/owl.carousel.js"></script>

<script type="text/javascript">
$(document).ready(function() {  
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<body>

	<c:choose>
	         <c:when test="${not empty imageList}">
					<div class="owl-carousel" style="width: 800px; height: 300px;">
						<c:forEach var="entry" items="${imageList}" varStatus="count" begin="0">
							<div class="item" style="text-align: center;height: 250px;">
								<a href="javascript:openImageInNewTab('${entry.imageFileId}');" > 
									<c:choose>
							            <c:when test="${entry.height > 200}">
							                <c:choose>
							                	<c:when test="${entry.width > 200}">
							                		<img src="/img/?f=${entry.imageFileId}" style="height: 200px;width: 200px;"/>
							                	</c:when>
							                	<c:otherwise>
							                		<img src="/img/?f=${entry.imageFileId}" style="height: 200px;width: ${entry.width}px;"/>
							                	</c:otherwise>
							                </c:choose>
							            </c:when>
							            
							            <c:otherwise>
							                <c:choose>
							                	<c:when test="${entry.width > 200}">
							                		<img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: 200px;"/>
							                	</c:when>
							                	<c:otherwise>
							                		<img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: ${entry.width}px;"/>
							                	</c:otherwise>
							                </c:choose>
							            </c:otherwise>
									</c:choose>
								</a>
								<div class="panel panel-success">
								  <div class="panel-body">
								    <span style="font-style: italic;font-size: x-small;color: #808080;">
							                <c:choose>
							                	<c:when test="${entry.imageDescription == '' || entry.imageDescription == null}">
							                		{이미지 설명 없음}
							                	</c:when>
							                	<c:otherwise>
							                		${entry.imageDescription}
							                	</c:otherwise>
							                </c:choose>
								   	</span>
								  </div>
								</div>					
							</div>
						
						</c:forEach>
					</div>
	         </c:when>
	         <c:otherwise>
	         		<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;">등록된 이미지가 없습니다.</span>
	         </c:otherwise>
	</c:choose>     
	
		
    <table class="action_button_table" width="100%">
        <tr>
             <td>
                  <a href="javascript:parent.closeImageWindow();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
             </td>
        </tr>
    </table>
          
<script type="text/javascript">
     $('.owl-carousel').owlCarousel({
 	    loop:false,
 	    margin:10,
 	    nav:true,
 	    responsive:{
 	        0:{
 	            items:1
 	        },
 	        600:{
 	            items:3
 	        },
 	        1000:{
 	            items:5
 	        }
 	    }
 	})          
   $(".owl-carousel").owlCarousel();
     
</script>     
</body>
</html>