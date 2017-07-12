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
		barElement = view.findByText("내정보 변경");
	} else if(pathName.startsWith("/customer/mypage/modifyUserInfoForm")){
		barElement = view.findByText("내정보 변경");
	} else if(pathName.startsWith("/customer/mypage/myorder")) {
		barElement = view.findByText("구매기록 조회");
	} else if(pathName.startsWith("/customer/mypage/myQnAs")) {
		barElement = view.findByText("My Communication");
	} 
	view.select(barElement);
	
	view.expand(".k-item");     
}

</script>

<script type="text/javascript">
    $(document).ready(function () {
        var items = [
            {id: 100, text: "비밀번호 변경", LinksTo: "/customer/mypage/passwordChangeForm.yum", image: "/resources/css/images/gic/ic_security_black_18dp_1x.png" },
            {id: 200, text: "내정보 변경", LinksTo: "/customer/mypage/myDetailInfo.yum", image: "/resources/css/images/gic/ic_person_black_18dp_1x.png" },
            {id: 300, text: "구매기록 조회", LinksTo: "/customer/mypage/myorder/Main.yum", image: "/resources/css/images/gic/ic_history_black_18dp_1x.png" },
            {id: 400, text: "My Communication", LinksTo: "/customer/mypage/myCommunication.yum", image: "/resources/css/images/gic/ic_question_answer_black_18dp_1x.png" }     ];

        var treeview = $("#treeview").kendoTreeView({
            dataUrlField: "LinksTo",
            dataImageUrlField: "image",
            dataSource: items
        });
        menuSelect();
    }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

</head>

<body>
<div class="well well-sm" style="background-color: #E7F4E3;">
     <table style="width: 100%;">
          <tr><td style="text-align: center;"><span style="color: #4C792D;font-weight: bold;">My푸드</span></td></tr>
     </table>
</div>
<div id="treeview"></div>
</body>
</html>