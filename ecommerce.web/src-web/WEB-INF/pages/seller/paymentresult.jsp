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
		    <span class="subtitle"> 결재처리결과.</span>
		    <hr class="subtitle"/>
		   ${paymentresult }
        </div>
    </div> 
    <br/>
    <br/>
    <br/>


    
</body>
</html>