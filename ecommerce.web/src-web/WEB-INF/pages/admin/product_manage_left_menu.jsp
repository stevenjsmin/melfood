<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<head>

    <script type="text/javascript">
        function menuSelect() {
            var view = $("#treeview").data("kendoTreeView");
            var pathName = window.location.pathname;
            var barElement = null;

            if (pathName.startsWith("/admin/productmgt/overviewProductInfo")) {
                barElement = view.findByText("Overview");
            } else if (pathName.startsWith("/admin/productmgt/modifyProductMasterInfoForm")) {
                barElement = view.findByText("기본정보");
            } else if (pathName.startsWith("/admin/productmgt/option")) {
                barElement = view.findByText("옵션정보");
            } else if (pathName.startsWith("/admin/productmgt/image")) {
                barElement = view.findByText("이미지정보");
            } else if (pathName.startsWith("/admin/productmgt/alertDeleteAllProductInfo")) {
                barElement = view.findByText("삭제");
            }
            view.select(barElement);

            view.expand(".k-item");
        }
    </script>

    <script type="text/javascript">
        $(document).ready(function () {
            var items = [
                {
                    id: 100, text: "상품관리", image: "/resources/css/images/gic/ic_card_giftcard_black_18dp_1x.png",
                    items: [
                        {id: 110, text: "Overview", LinksTo: "/admin/productmgt/overviewProductInfo.yum?prodId=${product.prodId}", image: "/resources/css/images/gic/ic_dashboard_black_18dp_1x.png"},
                        {id: 120, text: "기본정보", LinksTo: "/admin/productmgt/modifyProductMasterInfoForm.yum?prodId=${product.prodId}", image: "/resources/css/images/gic/ic_info_outline_black_18dp_1x.png"},
                        {id: 120, text: "옵션정보", LinksTo: "/admin/productmgt/option/Main.yum?prodId=${product.prodId}", image: "/resources/css/images/gic/ic_playlist_add_check_black_18dp_1x.png"},
                        {id: 120, text: "이미지정보", LinksTo: "/admin/productmgt/image/Main.yum?prodId=${product.prodId}", image: "/resources/css/images/gic/ic_photo_black_18dp_1x.png"},
                        {id: 130, text: "삭제", LinksTo: "/admin/productmgt/alertDeleteAllProductInfo.yum?prodId=${product.prodId}", image: "/resources/css/images/gic/ic_delete_forever_black_18dp_1x.png"}
                    ]
                }
            ];

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
    <div class="well well-sm">
        <table style="width: 100%;">
            <tr><td style="text-align: left;color: #b3b3b3;">상품정보 ID : ${product.prodId}</td></tr>
            <tr><td style="text-align: center;"><span style="color: #3c763d;font-weight: bold;">${product.name}</span></td></tr>
        </table>
    </div>
    <div id="treeview"></div>
</body>
</html>