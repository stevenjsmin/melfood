<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<head>
<!-- <META http-equiv="refresh" content="1;URL=redirect.html"> -->
<!-- META http-equiv="refresh" content="1;URL=http://rpc.dev.utilitiessoftwareservices.com:8080/rpc/redirect.html"-->

<script type="text/javascript">
function menuSelect() {
    var view = $("#treeview").data("kendoTreeView");
    var pathName = window.location.pathname;
    var barElement = null;
    
	if (pathName.startsWith("/admin/categorymgt")) {
		barElement = view.findByText("카테고리 관리");
	} else if(pathName.startsWith("/admin/productmgt")){
		barElement = view.findByText("상품 관리");
	} else if(pathName.startsWith("/framework/codemanager")){
		barElement = view.findByText("코드 관리");
	} else if(pathName.startsWith("/admin/contractmgt")) {
		barElement = view.findByText("계약 관리");
	} else if(pathName.startsWith("/admin/deliverycalendarmgt")) {
		barElement = view.findByText("배송일정 관리");	
	} else if(pathName.startsWith("/admin/paymentmethodmgt")) {
		barElement = view.findByText("결재수단 관리");
	} else if(pathName.startsWith("/framework/noticedisscussmanager")) {
		barElement = view.findByText("알림글 관리");
	} else if (pathName.startsWith("/framework/usermanager")) {
		barElement = view.findByText("사용자 관리");
	} else if (pathName.startsWith("/framework/postcodemanager")) {
		barElement = view.findByText("우편번호 관리");
	} else if (pathName.startsWith("/framework/sysconfigmanager")) {
		barElement = view.findByText("시스템 환경변수 관리");		
	} else {
		barElement = view.findByText("상품관리");
	}
	view.select(barElement);
	
	view.expand(".k-item");     
}

</script>

<div id="treeview"></div>
<script>
     var items = [
       	{id: 100, text: "상품관리", image: "/resources/css/images/gic/ic_card_giftcard_black_18dp_1x.png", items: [
           {id: 110, text: "카테고리 관리", LinksTo: "/admin/categorymgt/Main.yum", image: "/resources/css/images/gic/ic_bookmark_border_black_18dp_1x.png"  },
           {id: 120,  text: "상품 관리", LinksTo: "/admin/productmgt/Main.yum", image: "/resources/css/images/gic/ic_bookmark_border_black_18dp_1x.png" }
       	] },
       	{id: 200, text: "판매자 관리", image: "/resources/css/images/gic/ic_library_books_black_18dp_1x.png" ,items: [
            {id: 210, text: "계약 관리", LinksTo: "/admin/contractmgt/Main.yum", image: "/resources/css/images/gic/ic_library_books_black_18dp_1x.png" } ,
            {id: 220, text: "배송일정 관리", LinksTo: "/admin/deliverycalendarmgt/Main.yum", image: "/resources/css/images/gic/ic_send_black_18dp_1x.png" },
            {id: 230, text: "결재수단 관리", LinksTo: "/admin/paymentmethodmgt/Main.yum", image: "/resources/css/images/gic/ic_payment_black_18dp_1x.png" }
       	]},
       	{id: 300, text: "S.Window 관리", image: "/resources/css/images/gic/ic_dashboard_black_18dp_1x.png" },
       	{id: 310, text: "알림글 관리", LinksTo: "/framework/noticedisscussmanager/Main.yum", image: "/resources/css/images/gic/ic_chat_black_18dp_1x.png" },
        {id: 400, text: "시스템관리", image: "/resources/css/images/gic/ic_settings_black_18dp_1x.png", items: [
           {id: 410, text: "코드 관리", LinksTo: "/framework/codemanager/Main.yum", image: "/resources/css/images/gic/ic_developer_board_black_18dp_1x.png"  },
           {id: 420, text: "사용자 관리", LinksTo: "/framework/usermanager/Main.yum", image: "/resources/css/images/gic/ic_account_box_black_18dp_1x.png"  },
           {id: 430, text: "우편번호 관리", LinksTo: "/framework/postcodemanager/Main.yum", image: "/resources/css/images/gic/ic_local_post_office_black_18dp_1x.png"  },
           {id: 440, text: "시스템 환경변수 관리", LinksTo: "/framework/sysconfigmanager/Main.yum", image: "/resources/css/images/gic/ic_list_black_18dp_1x.png" }
        ] },       	
     ];
     
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