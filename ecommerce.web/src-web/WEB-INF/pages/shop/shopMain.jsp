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
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.1), rgba(0,0,0,0.5)),url(/img/?f=${shopMaster.shopGateImageFileId});
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 110px;
        }

        .product-photo {
            display: inline-block;
            width: 52px;
            height: 52px;
            border-radius: 50%;
            background-size: 52px 55px;
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
            padding-left: 20px;
            font-size: 15px;
        }
    </style>
    <style>
        /**
         * Bootstrap Customizing
         */
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
        }
        .pull-right {
            float: right !important;
        }
    </style>
    <style>
        /**
         * KENDO Customizing
         */
        #grid_panel_main {
            border: 0px;
        }
        .k-grid tbody tr {
            height: 80px;
            color: #5e5e5e;
        }
        .k-grid-content>table>tbody>tr>td {
            font-size: 13px;
            border-left: 0px;
        }
        .k-grid-pager {
            height: 35px;
            padding-top: 5px;
            border-top: solid 6px #1AAF54;
            background-color: transparent;
        }
        .k-grid-content {
            overflow: hidden;
        }
        .k-grid-header{
            padding-right: 0px;
            display: none;
        }
        .k-grid .k-state-selected  {
            background-color: #FFF2DC !important;
            color: #EF604C;
            font-weight: bold;
            border: solid 6px #F1FFB6;
        }
        .k-grid .k-alt.k-state-selected {
            background-color: #FFF2DC !important;
            color: #EF604C;
            font-weight: bold;
            border: solid 6px #F15F4C;
        }
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
                        url: "/shop/products.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                shopOwner: "${shopMaster.shopOwner}",
                                searchWord: $('#searchWord').val()
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
                sortable: false,
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
                    {title: '가격', field: 'unitPrice', format:"{0:c2}", width: 100},
                    {title: '', template: kendo.template($("#order-template").html()), attributes:{style:"text-align:center;"}, width: 130}
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

    <script id="order-template" type="text/x-kendo-template">
        #=  '<a href="javascript:doOrder();"><i class="fa fa-cart-plus fa-2x" aria-hidden="true" style="color: 3B3B3B;"></i></a>' #
    </script>

    <script type="text/javascript">
        function doOrder() {
            console.log('do Order.....');
        }
    </script>
</head>

<body>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 상단 [시작] :: 공동구매 개요 -->
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper" style="padding-left: 30px;">
            <table align="left"  style="color: #FFFFFF;height: 100%;" >
                <tr>
                    <td style="padding-right: 30px;">
                        <div style="font-weight: bold;font-size: 25px;">${shopMaster.shopName}</div>
                        <i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>&nbsp;&nbsp;
                        <span style="font-size: 13px;font-weight: bold;">
                            ${shopMaster.addressStreet} ${shopMaster.addressSuburb} ${shopMaster.addressState} ${shopMaster.addressPostcode}
                        </span><br/>

                        <div style="font-size: 13px;padding-top: 10px">ABN ${owner.abn}</div>

                    </td>
                    <td style="width: 2px;background-color: #FFFFFF;"></td>
                    <td style="padding-left: 30px;padding-right: 20px;">
                        <table>
                            <tr style="height: 25px;">
                                <td style="width: 20px;text-align: center;"><i class="fa fa-address-card-o" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                <td style="width: 10px;"></td>
                                <td><a href="javascript:groupPurchaseOrganizer()" style="color: #FFFFFF;">Seller 정보</a><br/></td>
                            </tr>
                            <tr style="height: 25px;">
                                <td style="width: 20px;text-align: center;"><i class="fa fa-comment" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                <td style="width: 10px;"></td>
                                <td><a href="javascript:askQuestion('${shopMaster.shopOwner}')" style="color: #FFFFFF;"> ?!! 물어보세요</a><br/></td>
                            </tr>
                        </table>
                    </td>

                    <td style="width: 2px;background-color: #FFFFFF;"></td>
                    <td style="padding-left: 30px;padding-right: 20px;">
                        <c:choose>
                            <c:when test="${shopMaster.deliveryService == 'Y'}">
                                <table style="width: 100%;">
                                    <tr style="height: 25px;">
                                        <td><i class="fa fa-truck" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                        <td style="width: 10px;"></td>
                                        <td style="color: #FFFFFF;">배송서비스</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td></td>
                                        <td style="width: 10px;"></td>
                                        <td style="color: #FFFFFF;">최소 주문금액 : <fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.minimumPurchaseAmount}" /> $</td>
                                    </tr>
                                </table>
                            </c:when>
                        </c:choose>
                    </td>

                </tr>

            </table>
        </div>
    </div>
</div>
<!-- 페이지 상단 [끝]:: 공동구매 개요 -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 중간 [시작] :: 오른쪽:아이템목록 왼쪽:주문내역 -->
<div class="row" style="padding-top: 10px;padding-bottom: 30px;">

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 왼쪽:아이템목록 -->
    <div class="col-sm-6">


        <div class="well well-lg" style="background-color: transparent;padding: 10px 10px 40px 10px;">

            <div class="row">

                <!-- 검색버튼 -->
                <div class="col-sm-12" style="padding-bottom: 10px;">
                    <div class="row">
                        <div class="col-sm-6 pull-right" style="padding-right: 40px;">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search for..." id="searchWord" style="height: 40px;font-size: 16px;">
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" style="height: 40px;font-size: 16px;" onclick="search()"><i class="fa fa-search" aria-hidden="true"></i> 찾기</button>
                                </span>
                            </div><!-- /input-group -->
                        </div>
                    </div>
                </div>

                <!-- 검색결과 -->
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-12">
                            <div id="grid_panel_main" style="margin-right: 20px;margin-left: 20px;"></div>
                        </div>
                    </div>
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