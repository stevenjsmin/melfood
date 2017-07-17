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

            $("#searchDateTo").kendoDatePicker({
                format: "yyyy-MM-dd",
                start: "year"
            });
            var datepicker2 = $("#searchDateTo").data("kendoDatePicker");
            $("#searchDateTo").click(function () {
                datepicker2.open();
            });


            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 10,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/admin/ordermgt/orders.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                sellerName: $("#sellerName").val(),
                                invoiceIssue: $("#invoiceIssue").val(),
                                normalOrGroupOrder: $("#normalOrGroupOrder").val(),
                                isPickupOrDelivery: $("#isPickupOrDelivery").val(),
                                isRefund: $("#isRefund").val(),
                                statusDelivery: $("#statusDelivery").val(),
                                statusPayment: $("#statusPayment").val(),
                                paymentMethod: $("#paymentMethod").val(),
                                paymentBankName: $("#paymentBankName").val(),
                                searchDateFrom: $("#searchDateFrom").val(),
                                searchDateTo: $("#searchDateTo").val()
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
                excel:{
                    fileName:"all_orders.xlsx",
                    allPages:true,
                    filterable:true
                },
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
                    pageSizes: [5, 10, 20, 30, 40, 50, 100],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    {hidden: true, field: 'orderMasterId'},
                    {title: '장본일자', field: 'createDatetime', attributes: {style: "color: e37200;"}},
                    {title: '금액', field: 'amountTotal', width: 100, format: "{0:n} $", type: "number", attributes:{style:"text-align:right;"}},
                    {title: '판매자', field: 'sellerName', width: 100},
                    {title: '구매자', field: 'customerName', width: 150},
                    {title: '픽업/배달', field: 'isPickupOrDelivery', template: kendo.template($("#isPickupOrDelivery-template").html()), filterable: false, width: 80},
                    {title: '환불', field: 'isRefund', template: kendo.template($("#isRefund-template").html()), filterable: false, width: 80},
                    {title: '공구/일반', field: 'normalOrGroupOrder', template: kendo.template($("#normalOrGroupOrder-template").html()), filterable: false, width: 100},
                    {title: '결재방법', field: 'paymentMethod', template: kendo.template($("#paymentMethod-template").html()), width: 100},
                    {title: '배송상태', field: 'statusDelivery', template: kendo.template($("#statusDelivery-template").html()), width: 100},
                    {title: '결재상태', field: 'statusPayment',template: kendo.template($("#statusPayment-template").html()), filterable: false, width: 100},
                    {title: '송금영수증', attributes: {style: "text-align: center;" }, template: kendo.template($("#paymentAccTransferReceipt-template").html()), width: 80},
                    {title: '배송상태', template: kendo.template($("#command-statusDelivery-template").html()),width: 120, attributes:{style:"text-align:right;"}},
                    {title: '결재상태', template: kendo.template($("#command-statusPayment-template").html()),width: 130, attributes:{style:"text-align:right;"}}
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
        function deliveryStatusToggle(orderMasterId) {
            $.ajax({
                url  : "/admin/ordermgt/deliveryStatusToggle.yum",
                data      : {
                    orderMasterId : orderMasterId
                },
                success : callbackDeliveryStatusToggle
            });
        }

        function callbackDeliveryStatusToggle(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                search();
            }
        }
        function paymentStatusToggle(orderMasterId) {
            $.ajax({
                url  : "/admin/ordermgt/paymentStatusToggle.yum",
                data      : {
                    orderMasterId : orderMasterId
                },
                success : callbackPaymentStatusToggle
            });
        }

        function callbackPaymentStatusToggle(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                search();
            }
        }
    </script>


    <script type="text/javascript">
        function myOrderDetailInfo(orderMasterId) {
            document.location.href = "/admin/ordermgt/orderdetail.yum?pageType=all&orderMasterId=" + orderMasterId;
        }
    </script>


    <script type="text/javascript">
        function search() {
            $('#grid_panel_main').data('kendoGrid').dataSource.read();
            $('#grid_panel_main').data('kendoGrid').refresh();
        }
    </script>

    <script id="paymentAccTransferReceipt-template" type="text/x-kendo-template">
        #= (paymentAccTransferReceipt != null) ? '<a href=javascript:downloadFile(' + (paymentAccTransferReceipt) + ');><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"></a>':'' #
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
    <script id="command-statusPayment-template" type="text/x-kendo-template">
        # if (statusPayment == 'BEFORE_PAYMENT') { #
                <a class="k-button" onclick="paymentStatusToggle('#=orderMasterId#')" style="color: F15F4C;width: 100px;">결재처리중 처리</a>
        # } else if (statusPayment == 'ON_PROCESSING') { #
                <a class="k-button" onclick="paymentStatusToggle('#=orderMasterId#')" style="color: 337AB7;width: 100px;">완료 처리</a>
        # } else if (statusPayment == 'COMPLETED') { #
                <a class="k-button" onclick="paymentStatusToggle('#=orderMasterId#')" style="color: B8A8A;width: 100px;">결재전 처리</a>
        # } else { #
                #=statusPayment #
        # } #
    </script>

    <script id="statusDelivery-template" type="text/x-kendo-template">
        # if (statusDelivery == 'BEFORE') { #
        #=  '<span style="color: C2002F;">배송전</span>' #
        # } else if (statusDelivery == 'COMPLETE') { #
        #=  '<span style="color: 8B8A8A;">배송완료</span>' #
        # } else { #
        #=  '<span style="color: C2002F;">배송전</span>' #
        # } #
    </script>
    <script id="command-statusDelivery-template" type="text/x-kendo-template">
        # if (statusDelivery == 'BEFORE') { #
        <a class="k-button" onclick="deliveryStatusToggle('#=orderMasterId#')" style="color: C2002F;width: 100px;">배송.완료 처리</a>
        # } else if (statusDelivery == 'COMPLETE') { #
        <a class="k-button" onclick="deliveryStatusToggle('#=orderMasterId#')" style="color: 737273;width: 100px;">배송.전 처리</a>
        # } else { #
        <a class="k-button" onclick="deliveryStatusToggle('#=orderMasterId#')" style="color: C2002F;width: 100px;">배송.완료 처리</a>
        # } #
    </script>

    <script id="isRefund-template" type="text/x-kendo-template">
        # if (isRefund == 'Y') { #
        #=  '<span style="color: 337AB7;">환불</span>' #
        # } else { #
        #=  '-' #
        # } #

    </script>

    <script id="isPickupOrDelivery-template" type="text/x-kendo-template">
        # if (isPickupOrDelivery == 'P') { #
        #=  '픽업' #
        # } else if (isPickupOrDelivery == 'D') { #
        #=  '<span style="color: 737273;">배달</span>' #
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

    <script type="text/javascript">
        function downlaodexcel() {
            $("#grid_panel_main").getKendoGrid().saveAsExcel();
        }
    </script>

</head>
<body>
<div id="QnAInfoPopup"></div>
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div class="well">
    <table class="search_table">
        <tr>
            <td class="label">조회 시작일 :</td>
            <td class="value"><input id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}"></input></td>

            <td class="label">조회 종료 :</td>
            <td class="value"><input id="searchDateTo" name="searchDateTo" value=""></input></td>

            <td class="label">인보이스발생 :  </td>
            <td class="value"><c:out value="${cbxInvoiceIssue}" escapeXml="false"/></td>

            <td class="label">일반/공.구 :  </td>
            <td class="value"><c:out value="${cbxNormalOrGroupOrder}" escapeXml="false"/></td>

            <td class="label">결재방법 :  </td>
            <td class="value_end"><c:out value="${cbxPaymentMethod}" escapeXml="false"/></td>

            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td class="label">픽업/배달 :  </td>
            <td class="value"><c:out value="${cbxIsPickupOrDelivery}" escapeXml="false"/></td>

            <td class="label">환불여부 :  </td>
            <td class="value"><c:out value="${cbxIsRefund}" escapeXml="false"/></td>

            <td class="label">제품배달여부 :  </td>
            <td class="value"><c:out value="${cbxStatusDelivery}" escapeXml="false"/></td>

            <td class="label">결재상태 :  </td>
            <td class="value"><c:out value="${cbxStatusPayment}" escapeXml="false"/></td>

            <td class="label">구매자 :  </td>
            <td class="value_end"><input class="form-control" id="customerName" name="customerName"></input></td>

            <td class="label">판매자 :  </td>
            <td class="value_end"><input class="form-control" id="sellerName" name="sellerName"></input></td>

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
<table class="action_button_table">
    <tr>
        <td align="right">
            <button type="button" onclick="downlaodexcel()" class="btn btn-info btn-sm"><i class="fa fa-file-excel-o" aria-hidden="true"></i> Excel export</button>
        </td>
    </tr>
</table>
</body>
</html>