<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<head>
<script type="text/javascript">
	function goOrder(prodId){
	  document.location.href = "/shop/order/product.yum?prodId=" + prodId;
	}
</script>
</head>

<body>
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
		   	<table style="width: 100%;background-color: #64A41E;">
		   		<tr><td colspan="2" style="padding: 5px;text-align: center;color: #F7F7F7;"><b><span style="color: #000000;"><b>로즈 베이커리</b></span> 의 메인페이지</b></td></tr>
		   	</table>
		   	<br/>
        </div>
    </div>  
       	
   	ㅇ				            	
</body>
</html>