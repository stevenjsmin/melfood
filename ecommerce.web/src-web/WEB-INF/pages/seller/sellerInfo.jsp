<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<head>
<!-- <META http-equiv="refresh" content="1;URL=redirect.html"> -->
<!-- META http-equiv="refresh" content="1;URL=http://rpc.dev.utilitiessoftwareservices.com:8080/rpc/redirect.html"-->

<script type="text/javascript">
	// document.location.href = "/common/auth/welcome.yum";
</script>

</head>

<body>

    <div class="row" id="sellerInfo">
        <div class="col-md-9">
		    <span class="subtitle"> 상품판매자 정보</span>
		    <hr class="subtitle"/>
		    <table class="detail_table_c">
		         <colgroup>
		              <col width="200px" />
		              <col width="250px" />
		              <col width="200px" />
		              <col width="300px" />
		         </colgroup>
				 <c:choose>
						<c:when test="${not empty seller}">
							<tr>
								<td class="label">판매자이름</td>
								<td class="value">${seller.userName}</td>
								<td class="label">연락처</td>
								<td class="value">${seller.userId}</td>
							</tr>
							<tr>
								<td class="label">이메일</td>
								<td class="value">${seller.email}</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="label">ABN</td>
								<td class="value">${seller.abn}</td>
								<td class="label">ACN</td>
								<td class="value">${seller.acn}</td>
							</tr>
							<tr>
								<td class="value" style="padding-top: 10px;text-align: center;">
									<c:choose>
										<c:when test="${seller.profilePhotoId != null and seller.profilePhotoId != 0}"><img src="/img/?f=${seller.profilePhotoId}" style="width: 90px;"/></c:when>
										<c:otherwise><img id="profilePhotoId" src="/resources/image/profile_photo.png" style="width: 40px;"></c:otherwise>
									</c:choose>
								</td>
								<td class="value" colspan="3"><br/>
									${seller.sellerIntroduction}
								</td>
							</tr>
		  				</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4">
		     						<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;"><a href="javascript:openLoginPopup();">로그인</a> 하셔야 확인하실 수 있는 정보입니다.</span>
								</td>		         
					  		</tr>
					    </c:otherwise>
			     </c:choose>
			</table>                          	
	     
        </div>
    </div> 
    <br/>
    <br/>

	 <c:if test="${isPopup == 'y' }">
		 <table class="action_button_table" width="100%" style="margin-top: 10px;" >
      			<tr>
          		 	<td style="padding-right: 10px;">
                			<a href="javascript:parent.closeShowSellerInfoPopup();" class="btn btn-info">&nbsp;&nbsp; 닫기 &nbsp;&nbsp;</a>
           			</td>
      			</tr>
  			</table>				    
	 </c:if>

    
</body>
</html>