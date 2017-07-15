<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<!doctype html>
<head>
    <script src="/resources/js/melfood/framework/grouppurchasepayment.js?ver=<%=Ctx.releaseVersion%>"></script>

    <style>
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
        }
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.1), rgba(0,0,0,0.5)),url(/resources/image/top-bg-bakery_1.jpg);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 100px;
        }

        .product-photo {
            display: inline-block;
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background-size: 32px 35px;
            background-position: center center;
            vertical-align: middle;
            line-height: 32px;
            box-shadow: inset 0 0 1px #999, inset 0 0 10px rgba(0,0,0,.2);
            margin-left: 5px;
        }
        .product-name {
            display: inline-block;
            vertical-align: middle;
            line-height: 32px;
            padding-left: 3px;
        }
    </style>
    <script type="text/javascript">
        var KENDO_SELECTED_RECORD = null;
        $(document).ready(function () {

            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 5,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/shop/products.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                seller: "0412778646"
                            };
                        } else if (operation == "destroy") {
                            console.log(options);
                            return {
                                id: options.prodId
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "prodId",
                        fields: {
                            prodId: {type: "string"}
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
                    {hidden: true, field: 'prodId'},
                    {title: '상품명', template: kendo.template($("#name-template").html()), width: 300},
                    {title: '설명', field: 'description'},
                    {title: '가격', field: 'unitPrice', width: 100}
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
        function search() {
            $('#grid_panel_main').data('kendoGrid').dataSource.read();
            $('#grid_panel_main').data('kendoGrid').refresh();
        }
    </script>

    <script id="name-template" type="text/x-kendo-template">
        #=   "<div class='product-photo' style='background-image: url(/resources/image/sample/8.jpg);'></div><div class='product-name'>" + name + "</div>" #
    </script>

</head>

<body>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 상단 [시작] :: 공동구매 개요 -->
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper">
            <table align="center">
                <tr>
                    <td style="padding: 0px 30px 0px 0px;">
                        ...
                    </td>
                    <td>...</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<!-- 페이지 상단 [끝]:: 공동구매 개요 -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 중간 [시작] :: 오른쪽:아이템목록 왼쪽:주문내역 -->
<div class="row" style="padding-top: 10px;">

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 왼쪽:아이템목록 -->
    <div class="col-sm-6" style="padding-right: 5px;padding-left: 5px;">

        <div class="row">
            <div class="col-sm-12">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-gift fa-2x" aria-hidden="true" style="color:#6F0E06;"></i></td>
                                <td style="text-align: left;"><span style="font-size: 13px;font-weight: bold;">로즈 베이커리 상품</span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 20px;">
                        <p>어쩌고 저쩌고..</p>
                    </div>

                    <!-- Table -->
                    <div id="grid_panel_main"></div>
                </div>
            </div>
        </div>

    </div>
    <!-- 페이지 중간 [끝] :: 왼쪽:아이템목록 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 오른쪽:주문내역 -->
    <div class="col-sm-3"style="padding-right: 20px;padding-left: 20px;">

        <div class="panel panel-success">

            <div class="panel-heading" style="background-color: #C8E297;">
                <table style="width: 100%;">
                    <tr>
                        <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-shopping-cart fa-3x" aria-hidden="true"></i></td>
                        <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">내 주문</span></td>
                    </tr>
                </table>
            </div>
            <div class="panel-body" style="padding-left: 100px;padding-right: 10px;padding-bottom: 30px;padding-top: 10px;">

                <table border="0" style="width: 100%;">
                    <colgroup>
                        <col style="width: 60%;">
                        <col style="width: 10%;">
                        <col style="width: 30%;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <td style="text-align: right;">상품구매</td>
                            <td style="text-align: center;"><i class="fa fa-plus" aria-hidden="true"></i></td>
                            <td style="color: #797979; text-align: right;font-size: 15px;"><span id="payment_totalAmountForProduct" style="font-size: 15px;">0.00</span> $</td>
                        </tr>
                        <tr style="height: 15px;">
                            <td colspan="3">&nbsp;</td>
                        </tr>

                        <tr style="height: 15px;">
                            <td style="text-align: right;">배송서비스</td>
                            <td style="text-align: center;"><i class="fa fa-plus" aria-hidden="true"></i></td>
                            <td style="color: #797979; text-align: right;font-size: 15px;"><span id="payment_deliveryFee" style="font-size: 15px;">0.00</span> $</td>
                        </tr>

                        <tr style="height: 30px;">
                            <td style="text-align: right;color: #58A578;">할인</td>
                            <td style="text-align: center;"><i class="fa fa-arrow-down" aria-hidden="true" style="color: #58A578;"></i></td>
                            <td style="color: #58A578; text-align: right;font-size: 15px;">- <span id="payment_toBeDiscountAmount" style="font-size: 15px;color: #58A578;">0.00</span> $</td>
                        </tr>
                        <tr>
                            <td colspan="3" style="padding-left: 30%;">
                                <hr class="subtitle-gray"/>
                            </td>
                        </tr>
                        <tr style="height: 5px;"><td colspan="3">&nbsp;</td></tr>
                        <tr style="height: 15px;">
                            <td style="text-align: right;font-size: 20px;font-weight: bold;color: #797979;">총</td>
                            <td style="text-align: center;"></td>
                            <td style="color: #797979; text-align: right;font-size: 20px;font-weight: bold;color: #797979;">
                                <span id="payment_finalAmount" style="font-size: 20px;font-weight: bold;color: #797979;">0.00</span> $
                            </td>
                        </tr>
                        <tr style="height: 30px;padding-top: 50px;">
                            <td style="color: #C3C5C8; text-align: right;font-size: 13px;" colspan="3"><br/>결재방법</td>
                        </tr>
                        <tr style="height: 40px;">
                            <td style="color: #797979; text-align: right;font-size: 15px;" colspan="3">...콤보박스</td>
                        </tr>
                        <tr id="detail_info_back_account" style="height: 40px;display: none;">
                            <td colspan="3" style="padding-top: 0px;padding-bottom: 10px;">
                                <table width="100%;" style="font-size: 5px;">
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">은행명</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankName"></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">BSB - Account no</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #514747;" id="payment_bankBsbBankAccountNo"></td>
                                    </tr>
                                    <tr>
                                        <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Account Holder</td>
                                        <td style="width: 20px;text-align: center">:</td>
                                        <td style="color: #8D9999;" id="payment_bankAccountOwnerName"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>


                        <tr style="height: 30px;padding-top: 50px;">
                            <td style="color: #C3C5C8; text-align: right;font-size: 13px;" colspan="3"><br/>주문메모</td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <div class="form-group">
                                    <textarea class="form-control" rows="3" id="customerOrderNote" placeholder="주문시 메모하고 싶은 내용을 입력해주세요."></textarea>
                                </div>
                            </td>
                        </tr>


                        <tr style="height: 25px;">
                            <td colspan="3" style="background-color: #F15F4C;text-align: center;"><a href="javascript:paymentProcessConfirm();" style="color: #FFFFFF;font-weight: bold;font-size: 15px;"><i class="fa fa-credit-card-alt" aria-hidden="true"></i>&nbsp;&nbsp; 결재하기 </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div style="padding: 10px;display: none;" id="payment_bankTransfer_message">
               <div class="alert alert-warning" style="border-left: 6px solid #F15F4C;padding-left: 5px;">계좌이체를 선택하신경우 결재후 계좌송금 <span style="text-decoration: underline;">영수증 이미지를 업로드</span>해 주시면 빠르게 처리해 드리겠습니다.</div>
            </div>
        </div>


    </div>
    <!-- 페이지 중간 [끝] :: 오른쪽:주문내역 -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


</div>
<!-- 페이지 중간 [끝] :: :: 오른쪽:아이템목록 왼쪽:주문내역  -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

</body>
</html>