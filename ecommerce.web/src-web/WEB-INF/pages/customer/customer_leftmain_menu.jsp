<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<head>
<!-- <META http-equiv="refresh" content="1;URL=redirect.html"> -->
<!-- META http-equiv="refresh" content="1;URL=http://melfood.com.au/redirect.html"-->

<script type="text/javascript">
function menuSelect() {
    var view = $("#treeview").data("kendoTreeView");
    var pathName = window.location.pathname;
    var barElement = null;
    
	if (pathName.startsWith("/customer/mypage/passwordChangeForm")) {
		barElement = view.findByText("비밀번호 변경");
	} else if(pathName.startsWith("/customer/mypage/myDetailInfo")){
		barElement = view.findByText("개인정보 변경");
	} else if(pathName.startsWith("/customer/mypage/modifyUserInfoForm")){
		barElement = view.findByText("개인정보 변경");
	} else if(pathName.startsWith("/customer/mypage/myOrderList")) {
		barElement = view.findByText("상품구매 목록조회");
	} else if(pathName.startsWith("/customer/mypage/myQnAs")) {
		barElement = view.findByText("My QnA");	
	} 
	view.select(barElement);
	
	view.expand(".k-item");     
}

</script>

<div id="treeview"></div>
<script>
     var items = [
       	{id: 100, text: "비밀번호 변경", LinksTo: "/customer/mypage/passwordChangeForm.yum", image: "/resources/css/images/gic/ic_security_black_18dp_1x.png" },
       	{id: 200, text: "개인정보 변경", LinksTo: "/customer/mypage/myDetailInfo.yum", image: "/resources/css/images/gic/ic_person_black_18dp_1x.png" },
       	{id: 300, text: "상품구매 목록조회", LinksTo: "/customer/mypage/myOrderList.yum", image: "/resources/css/images/gic/ic_history_black_18dp_1x.png" },
       	{id: 400, text: "My QnA", LinksTo: "/customer/mypage/myQnAs.yum", image: "/resources/css/images/gic/ic_question_answer_black_18dp_1x.png" }     ];
     
     var treeview = $("#treeview").kendoTreeView({
     	dataUrlField: "LinksTo",
     	dataImageUrlField: "image",
     	dataSource: items
     });
     menuSelect();
	
</script>

</head>

<body>
     <div id="treeview"></div>
</body>
</html>