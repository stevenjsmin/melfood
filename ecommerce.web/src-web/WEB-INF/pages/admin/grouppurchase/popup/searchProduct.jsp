<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>
    <script type="text/javascript">
        var KENDO_SELECTED_RECORD_1 = null;
        $(document).ready(function () {

            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 10,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/admin/grouppurchase/product/searchProduct.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    parameterMap: function(options, operation) {
                        if (operation == "read") {
                            return {
                                page : options.page,
                                pageSize : options.pageSize,
                                seller : $("#seller").val(),
                                name : $("#name").val()
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "seller",
                        fields: {
                            seller : { type: "string" }
                        }
                    },
                    data: function(response) {
                        return response.list;
                    },
                    total: function (response) {
                        return response.totalCount;
                    }
                }
            }); // End of DATASOURCE

            // DEFINE GRID TABLE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            $("#grid_panel_main_1").kendoGrid({
                dataSource: dataSource,
                selectable: true,
                sortable: true,
                editable: false,
                change: onChange,
                filterable : {
                    extra:false,
                    operators: {
                        string:{ contains: "Contains"}
                    }
                },
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5,
                    page: 1,
                    pageSizes: [5,10],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    { hidden : true, field: 'prodId'},
                    { title : 'Product Name', field: 'name', attributes: {style: "text-align: left;color: 606000; font-weight: bolder;" }},
                    { title : 'Product Owner', template: kendo.template($("#seller-template").html()), attributes: {style: "text-align: left;" }},
                    { title : 'Price($)', field: 'unitPrice', width: 150, attributes: {style: "text-align: right;" }, format: "{0:c}"},
                    { command: [ {text : "Add", click: addGroupPurchaseProduct}], width: 90}

                ] // End of Columns
            }); // End of GRID

            $("#grid_panel_main_1").dblclick(function(e) {
                // var dataItem = KENDO_SELECTED_RECORD_1;
                // var groupPurchaseId = dataItem.groupPurchaseId;

                // goDetailInfo(groupPurchaseId);
            });

            function onChange(e) {
                var gridRecord = e.sender;
                KENDO_SELECTED_RECORD_1 = gridRecord.dataItem(gridRecord.select());
            }

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function changeType(category) {
            $.ajax({
                url : "/framework/codemanager/types.yum",
                data : "category=" + category,
                success : callbackChangeType,
            });
        }
        function callbackChangeType(data) {
            $('#typeCbx').empty();
            $('#typeCbx').html(data.cbxTypes);
        }
    </script>

    <script id="seller-template" type="text/x-kendo-template">
        #= seller + ' / ' + sellerName #
    </script>

    <script type="text/javascript">
            function addGroupPurchaseProduct(e) {
                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

                var productId = dataItem.prodId;

                $.ajax({
                    url: "/admin/grouppurchase/product/addProduct.yum",
                    data: {
                        productId: productId,
                        groupPurchaseId: ${groupPurchaseId}
                    },
                    success: callbackAddGroupPurchaseProduct
                });

            }

            function callbackAddGroupPurchaseProduct(data) {
                var message = data.message;
                var resultCode = data.resultCode;

                if (resultCode != "0") {
                    warningPopup(data.message);
                } else {
                    parent.refreshForProductList();
                }
            }
    </script>
</head>
<body>

<div id="groupPurchasePopup"></div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div class="well">
    <table class="search_table">
        <tr>
            <td class="label">Product Owner : </td>
            <td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
            <td class="label">Product Name :  </td>
            <td class="value_end"><input class="form-control" id="name" name="name"></input></td>
            <td class="find"><button type="button" class="btn btn-info" onclick="search();">Search</button></td>

        </tr>
    </table>
</div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Table List -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div id="grid_panel_main_1"></div>

<br/>
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Extra buttons -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<table class="action_button_table">
    <tr>
        <td>
            <button type="button" class="btn btn-primary" onclick="parent.closeSearchProductPopup();">Close</button>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var GROUP_PURCHASE_ID = "";
    var ACTION_MODE = "ADD";
</script>
</body>
</html>