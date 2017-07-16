<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="/resources/js/melfood/admin/shopmaster.js?ver=<%=Ctx.releaseVersion%>"></script>

    <style type="text/css">
    </style>


    <script type="text/javascript">
        var KENDO_SELECTED_RECORD = null;
        $(document).ready(function () {

            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 10,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/admin/shopmastermgt/shopMasters.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    destroy: {
                        url: "/admin/shopmastermgt/deleteShopMaster.yum",
                        dataType: "jsonp",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize
                            };
                        } else if (operation == "destroy") {
                            return {
                                shopId: options.shopId
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "shopId",
                        fields: {
                            shopId: {type: "string"}
                        }
                    },
                    data: function (response) {
                        return response.list;
                    },
                    total: function (response) {
                        return response.totalCount;
                    }
                }
            }); // End of DATASOURCE

            // DEFINE GRID TABLE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            $("#grid_panel_main").kendoGrid({
                dataSource: dataSource,
                selectable: true,
                sortable: true,
                editable: false,
                change: onChange,
                filterable: {
                    extra: false,
                    operators: {
                        string: {contains: "Contains"}
                    }
                },
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5,
                    page: 1,
                    pageSizes: [5, 10, 20],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    {hidden: true, field: 'shopId'},
                    {title: '샵이름', field: 'shopName', attributes: {style: "color: e37200;"}},
                    {title: '소유자', template: kendo.template("#= shopOwner + ' / ' + shopOwnerName #")},
                    {title: '최소주문금액', field: 'minimumPurchaseAmount', format:'{0:0.00} $', attributes: {style: "text-align: right;"}, width: 120},
                    {title: '주소', template: kendo.template($("#addressStreet-template").html()), filterable: false},
                    {title: '배송', template: kendo.template($("#deliveryService-template").html()), width: 120},
                    {title: '기본배송비', field: 'deliveryBaseCharge', format:'{0:0.00} $', attributes: {style: "text-align: right;"}, width: 120},
                    {title: '배송비/Km', field: 'deliveryFeePerKm', format:'{0:0.00} $', attributes: {style: "text-align: right;"}, width: 120},
                    {title: '배송기준주소', template: kendo.template($("#forDeliverAddressStreet-template").html()), filterable: false},
                    { command: [ {text : "Delete", name: "destory", click: deleteItem}], width: 140}
                ] // End of Columns
            }); // End of GRID

            $("#grid_panel_main").dblclick(function (e) {
                var dataItem = KENDO_SELECTED_RECORD;
                var shopId = dataItem.shopId;
                goDetailInfo(shopId);
            });

            function onChange(e) {
                var gridRecord = e.sender;
                KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
            }

            function deleteItem(e) {
                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

                BootstrapDialog.confirm({
                    title: 'WARNING  :: 호주가 즐거운 이유, 멜푸드!!',
                    message: '정말 삭제하시겠습니까?',
                    type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
                    closable: true, // Default value is false
                    draggable: true, // Default value is false
                    btnCancelLabel: 'Cancel', // Default value is 'Cancel',
                    btnOKLabel: 'OK', // Default value is 'OK',
                    btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
                    callback: function(result) {
                        if(result) {
                            var grid = $("#grid_panel_main").data("kendoGrid");
                            grid.dataSource.remove(dataItem);
                            grid.dataSource.sync();
                            grid.refresh();
                        }
                    }
                });
            }

            search();
        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script id="addressStreet-template" type="text/x-kendo-template">
        #=  addressStreet + ', ' + addressSuburb + ' ' + addressState + ' ' + addressPostcode #
    </script>

    <script id="forDeliverAddressStreet-template" type="text/x-kendo-template">
        #=  forDeliverCalcAddressStreet + ', ' + forDeliverCalcAddressSuburb + ' ' + forDeliverCalcAddressPostcode #
    </script>

    <script id="deliveryService-template" type="text/x-kendo-template">
        # if (deliveryService == 'Y') { #
        #=  '<span style="color: F15F4C;">배송가능</span>' #
        # } else if (deliveryService == 'N') { #
        #=  '<span style="color: 337AB7;">배송서비스 없음</span>' #
        # } else { #
        #=  '-' #
        # } #

    </script>


</head>
<body>
<div id="shopMasterPopup"></div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Table List -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div id="grid_panel_main"></div>

<br/>
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Extra buttons -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<table class="action_button_table">
    <tr>
        <td>
            <button type="button" class="btn btn-primary" onclick="registShopMaster();">Add Item</button>
        </td>
    </tr>
</table>

</body>
</html>