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
<!-- http://baijs.com/tinycarousel/ -->
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<style>
	#imageTable tbody tr:nth-child(odd) {
	   background-color: #F9F7FC;
	}
</style>

<script type="text/javascript">
	function deleteAllProductInfo(){
		
		BootstrapDialog.confirm({
	        title: 'WARNING  :: 호주가 즐거운 이유, 쿠빵!!',
	        message: '정말 모든 상품정보를 영구히 삭제하시겠습니까?',
	        type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
	        closable: true, // <-- Default value is false
	        draggable: true, // <-- Default value is false
	        btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
	        btnOKLabel: 'OK', // <-- Default value is 'OK',
	        btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
	        callback: function(result) {
	            if(result) {
				      $.ajax({
				           url  : "/admin/productmgt/deleteProduct.yum",
				           data      : {
				        	   prodId : "${product.prodId}"
				           },
				           success : callbackDeleteAllProductInfo
				      });            	
	            }
	        }
	    });   	
	}
	     
	function callbackDeleteAllProductInfo(data) {
	      var message = data.message;
	      var resultCode = data.resultCode;

	      if (resultCode != "0") {
	           warningPopup(data.message);
	      } else {
	    	  goList();
	      }
	}
	
</script>

</head>

<body>

    <span class="subtitle"> 상품 기본정보</span>
    <hr class="subtitle"/>  
    <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="200px" />
                              <col width="250px" />
                              <col width="200px" />
                              <col width="300px" />
                         </colgroup>
                         <tr>
                              <td class="label">상품ID</td>
                              <td class="value">${product.prodId}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">상품명</td>
                              <td class="value" colspan="3">${product.name}</td>
                         </tr>
                         <tr>
                              <td class="label">판매자</td>
                              <td class="value">${product.sellerName}</td>
                              <td class="label">상품분류</td>
                              <td class="value">${product.categoryName}</td>
                         </tr>
                         <tr>
                              <td class="label">등록상태</td>
                              <td class="value">${product.registerStatusName}</td>
                              <td class="label">등록만료일자</td>
                              <td class="value">${product.validateUntil}</td>
                         </tr>
                         <tr>
                              <td class="label">재고량</td>
                              <td class="value">${product.inStockCnt}</td>
                              <td class="label">단가</td>
                              <td class="value">$ ${product.unitPrice}</td>
                         </tr>
                         <tr>
                              <td class="label">판매상태</td>
                              <td class="value">${product.sellingStatusName}</td>
                              <td></td>
                              <td></td>
                         </tr>                         
                         
                         <tr>
                              <td class="label">커미션 형태</td>
                              <td class="value">${product.sellingCommissionTypeName}</td>
                              <td class="label">커미션</td>
                              <td class="value">
                              		<c:choose>
                              			<c:when test="${product.sellingCommissionType == 'RATE' }"> 
                              				<fmt:formatNumber type="percent" maxIntegerDigits="3" value="${product.sellingCommissionRate}" />
                              			</c:when>
                              			<c:when test="${product.sellingCommissionType == 'FEE' }">$ ${product.sellingCommissionFee}</c:when>
                              		</c:choose>
                              </td>
                         </tr>
                         
                         <tr><td colspan="4"><hr/></td></tr>
                         
                         <tr>
                         	  <td colspan="4">
								<div class="panel panel-danger">
								  <div class="panel-heading"><b>삭제시 유의사항</b></div>
								  <div class="panel-body">
								  		상품정보 삭제시 다음의 정보가 함께 영구 삭제되니 주의하여 주시기 바랍니다.
								  		<ul>
								  			<li>상품에 관련된 마스터 정보</li>
								  			<li>상품에 관련된 이미지 정보 (첨부된파일포함)</li>
								  			<li>상품에 관련된 옵션 정보</li>
								  		</ul>
								  		또한 이미 고객으로부터 주문이된 이력이있는 상품에대해서 삭제하시는 경우 차후 삭제된 상품정보를 조회하고자 하는 경우 조회가 불가합니다.<br/>
								  		따라서 삭제가 꼭 필요하지 않는 경우라면 상품의 <span style="color: #5B95B0;">판매상태</span>를 "<span style="color: #5D5DB0;">판매정지(품절), 판매정지(상품준비중)</span> 또는 <span style="color: #5D5DB0;">판매정지(임시판매정지)</span>"등의 상태로 변경하여 운영하시기 바랍니다.
								  </div>
								</div>
                         	  </td>
                         </tr>
                         
				          <tr>
				               <td colspan="4">
				                    <table class="action_button_table" width="100%">
				                         <tr>
				                              <td>
				                                   <a href="javascript:deleteAllProductInfo();" class="btn btn-danger">Delete</a>
				                              </td>
				                         </tr>
				                    </table>
				               </td>
				          </tr>                                                  
                         
                    </table>
               </td>
          </tr>

     </table>
     
    <br/> 
    <br/> 
    <br/> 
    <br/>
    
     
    <input type="hidden" name="prodId"  id="prodId" value="${product.categoryId}" />
     
    <script type="text/javascript">
          var _PRODID = "<c:out value="${product.prodId}"/>";
    </script>  
          
</body>
</html>