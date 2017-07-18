<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        #grid_panel_main {
            border: 0px;
        }
        .k-grid tbody tr {
            height: 35px;
        }
        .k-grid-content>table>tbody>tr>td {
            font-size: 13px;
            border-left: 0px;
        }
        .k-grid-pager {
            height: 30px;
            padding-top: 5px;
        }
    </style>


    <script type="text/javascript">
        var KENDO_SELECTED_RECORD = null;
        $(document).ready(function () {

            $("#searchDateFrom").kendoDatePicker({
                format: "yyyy-MM-dd",
                start: "year"
            });
            var datepicker1 = $("#searchDateFrom").data("kendoDatePicker");
            $("#searchDateFrom").click(function () {
                datepicker1.open();
            });

            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 5,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/customer/mypage/myorder/myorders.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                category: "QNA",
                                searchDateFrom: $("#searchDateFrom").val(),
                            };
                        } else if (operation == "destroy") {
                            console.log(options);
                            return {
                                id: options.id
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "orderMasterId",
                        fields: {
                            orderMasterId: {type: "string"}
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
                    pageSizes: [5, 10, 20, 30],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    {hidden: true, field: 'id'},
                    {title: '장본일자', field: 'createDatetime', attributes: {style: "color: e37200;"}},
                    {title: '판매자', field: 'sellerName', width: 100},
                    {title: '공구/일반', template: kendo.template($("#normalOrGroupOrder-template").html()), filterable: false, width: 100},
                    {title: '결재방법', template: kendo.template($("#paymentMethod-template").html()), width: 100},
                    {title: '결재상태', template: kendo.template($("#statusPayment-template").html()), filterable: false, width: 100}
                ] // End of Columns
            }); // End of GRID

            $("#grid_panel_main").dblclick(function (e) {
                var dataItem = KENDO_SELECTED_RECORD;
                var orderMasterId = dataItem.orderMasterId;
                myOrderDetailInfo(orderMasterId);
            });

            function onChange(e) {
                var gridRecord = e.sender;
                KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
            }

            search();
        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <script type="text/javascript">
        function myOrderDetailInfo(orderMasterId) {
            document.location.href = "/customer/mypage/myorder/myorderdetail.yum?thanks=" + orderMasterId;
        }
    </script>


    <script type="text/javascript">
        function search() {
            $('#grid_panel_main').data('kendoGrid').dataSource.read();
            $('#grid_panel_main').data('kendoGrid').refresh();
        }
    </script>

    <script id="normalOrGroupOrder-template" type="text/x-kendo-template">
        # if (normalOrGroupOrder == 'G') { #
        #=  '<span style="color: C14F51;">공.구</span>' #
        # } else { #
        #=  '-' #
        # } #

    </script>
    <script id="statusPayment-template" type="text/x-kendo-template">
        # if (statusPayment == 'BEFORE_PAYMENT') { #
        #=  '<span style="color: F15F4C;">결재전</span>' #
        # } else if (statusPayment == 'ON_PROCESSING') { #
        #=  '<span style="color: 337AB7;">결재처리중</span>' #
        # } else if (statusPayment == 'COMPLETED') { #
        #=  '<span style="color: 8B8A8A;">완료</span>' #
        # } else { #
        #=  '-' #
        # } #

    </script>

    <script id="paymentMethod-template" type="text/x-kendo-template">
        # if (paymentMethod == 'ACCOUNT_TRANSFER') { #
        #=  '<span style="color: 337AB7;">계좌이체</span>' #
        # } else if (paymentMethod == 'CASH') { #
        #=  '<span style="color: 006F3C;">만나서결재</span>' #
        # } else if (statusPayment == 'CREDIT_CARD') { #
        #=  '<span style="color: 3E3637;">신용카드결재</span>' #
        # } else if (statusPayment == 'PAYPAL') { #
        #=  'PAYPAL' #
        # } else { #
        #=  paymentMethod #
        # } #

    </script>


</head>
<body>
<div id="QnAInfoPopup"></div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div class="well">
    <table class="search_table">
        <tr>
            <td class="label">조회 시작일 :</td>
            <td class="value_end"><input id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}"></input></td>
            <td class="find">
                <button type="button" class="btn btn-info btn-sm" onclick="search();"><i class="fa fa-search" aria-hidden="true"></i> Search</button>
            </td>
        </tr>
    </table>
</div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Table List -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div id="grid_panel_main"></div>

<br/>

</body>
</html>