<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<script type="text/javascript">
		// document.location.href = "/common/auth/welcome.yum";
	</script>
</head>

<body>

    <div class="row" id="sellerInfo">
        <div class="col-md-9">
		    <span class="subtitle"> 구매전 확인사항</span>
		    <hr class="subtitle"/>
		    <table class="detail_table_c">
				 <c:choose>
						<c:when test="${not empty checkBeforeBuy}">
						<tr>
							<td class="value">${checkBeforeBuy.contents}</td>
						</tr>
		  				</c:when>
						<c:otherwise>
							<tr>
								<td class="value" style="text-align: center;">
		     						<span style="font-size: small;padding-top: 50px;padding-bottom: 50px;color: #D6D6D6;">등록된 주문전 확인 사항이 없습니다.</span>
								</td>		         
					  		</tr>
					    </c:otherwise>
			     </c:choose>
			</table>                          	
	     
        </div>
    </div> 
    <br/>
    <br/>
    <br/>


    
</body>
</html>