<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<!doctype html>
<head>
    <script src="/resources/js/melfood/framework/shopmain.js?ver=<%=Ctx.releaseVersion%>"></script>

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
        .openProductOrderPopup {
            color: 3B3B3B;
        }
        .openProductOrderPopup:hover {
            color: #F15F4C;
            cursor: hand;
        }
        #grid_panel_main > div.k-grid-content > table > tbody > tr:hover {
            color: #F15F4C;
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
            border-top: solid 6px #BE0712;
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
            border: solid 6px #BE0712;
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

            checkDeliverable();

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function search() {
            $('#grid_panel_main').data('kendoGrid').dataSource.read();
            $('#grid_panel_main').data('kendoGrid').refresh();
        }
    </script>

    <script id="name-template" type="text/x-kendo-template">
        # if (productImage.imageFileId != 0) { #
        #=   "<div class='product-photo' style='background-image: url(/img/?f=" + productImage.imageFileId + ");'></div><div class='product-name'>" + name + "</div>" #
        # } else { #
        #=   "<div class='product-photo' style='background-image: url(/resources/image/default_goods4.png);'></div><div class='product-name'>" + name + "</div>" #
        # } #
    </script>

    <script id="order-template" type="text/x-kendo-template">
        #=  "<a href='javascript:openProductOrderPopup(\"" + prodId + "\");'><i class='fa fa-cart-plus fa-2x openProductOrderPopup' aria-hidden='true'></i></a>" #
    </script>

    <script type="text/javascript">
        function checkDeliverable() {
            $("#UNKNOWN_ERROR").hide();

            if('${shopMaster.deliveryService}' == 'Y'){

                progressWithId(true, 'dilivery_service_row');
                $.ajax({
                    url: "/shop/checkDeliverable.yum",
                    data: {
                        shopId: ${shopMaster.shopId}
                    },
                    success: callbackCheckDeliverable
                });
            }
        }
        function callbackCheckDeliverable(data) {

            var resultCode = data.resultCode;
            var mapResultCode = data.mapResultCode;
            var mapResultMessage = data.mapResultMessage;

            var cutomerAddress = data.cutomerAddress;
            var deliveryFee = data.deliveryFee;

            var distance = data.distance;
            var duration = data.duration;
            var deliveryLimitKm = 0;
            if(data.shopMaster != null){
                deliveryLimitKm = data.shopMaster.deliveryLimitKm;
            }

            progressWithId(false, 'dilivery_service_row');

            if(resultCode == "OK"){

                if(deliveryLimitKm != 0 && deliveryLimitKm < distance) {
                    // 배달할수 없는 거리인경우

                    $("#DELIVERY_SERVICE_DETAIL").show();
                    $("#cutomerAddress").html(cutomerAddress);
                    $("#distance").html(distance);

                    $("#delivery_distance").val(distance);

                    $("#useDeliveryService").prop("disabled", true);
                    $("#deliveryFee").html("<span style='color: #EF604C;'>죄송합니다. " + deliveryLimitKm + "Km 이상되는 거리는 배송해 드릴 수 없습니다</span></span>");

                    $("#deliveryServiceFee").val(0);

                } else {
                    $("#DELIVERY_SERVICE_DETAIL").show();
                    $("#cutomerAddress").html(cutomerAddress);
                    $("#distance").html(distance);

                    $("#delivery_distance").val(distance);

                    if(deliveryFee == 0) {
                        $("#deliveryFee").html("<span style='color: #EF604C;font-weight: bold;'>없음 - 무료</span></span>");
                    } else {
                        $("#deliveryFee").html("$ <span style='color: #EF604C;'>" + deliveryFee + "</span><span style='font-size: 10px;'> (${shopMaster.deliveryBaseCharge} $ 기본서비스 + ${shopMaster.deliveryFeePerKm} $ 서비스비/Km)</span>");
                    }
                    $("#deliveryServiceFee").val(deliveryFee);
                }


            } else if(resultCode == "CUSTOMER_ADDR_INVALID"){
                $("#CUSTOMER_ADDR_INVALID").show();
                $("#customerAddrInvalidddress").html(cutomerAddress);

            } else  if(resultCode == "NO_DELIVERABLE_SERVICE_AREA") {
                $("#NO_DELIVERABLE_SERVICE_AREA").show();
                $("#noDeliverableServiceAreaMessage").html(mapResultMessage);
            } else {
                $("#UNKNOWN_ERROR").show();
                $("#unknownErrorCutomerAddress").html(cutomerAddress);
                $("#unknownErrorMessage").html(mapResultMessage);
            }

        }

    </script>
</head>

<body>
<div id="productOrderPopup"></div>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 상단 [시작] :: 샵설명 개요 -->
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
                                <td><a href="javascript:groupPurchaseOrganizer()" style="color: #FFFFFF;">판매자 정보</a><br/></td>
                            </tr>
                            <tr style="height: 25px;">
                                <td style="width: 20px;text-align: center;"><i class="fa fa-comment" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                <td style="width: 10px;"></td>
                                <td><a href="javascript:askQuestion('${shopMaster.shopOwner}')" style="color: #FFFFFF;"> ?!! 물어보세요</a><br/></td>
                            </tr>
                        </table>
                    </td>

                    <c:choose>
                        <c:when test="${shopMaster.deliveryService == 'Y'}">
                            <td style="padding-left: 30px;padding-right: 20px;">
                                <table style="width: 100%;">
                                    <tr style="height: 25px;">
                                        <td><i class="fa fa-truck" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                        <td style="width: 10px;"></td>
                                        <td style="color: #FFFFFF;">배송서비스</td>
                                    </tr>
                                    <tr style="height: 25px;">
                                        <td><i class="fa fa-check-square-o" aria-hidden="true" style="color: #FFFFFF;"></i></td>
                                        <td style="width: 10px;"></td>
                                        <td style="color: #FFFFFF;">최소 주문금액 : <fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.minimumPurchaseAmount}" /> $</td>
                                    </tr>
                                </table>
                            </td>
                        </c:when>
                    </c:choose>

                </tr>

            </table>
        </div>
    </div>
</div>
<!-- 페이지 상단 [끝]:: 샵설명 개요 -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- 페이지 중간 [시작] :: 왼쪽:아이템목록 오른쪽:주문내역 -->
<div class="row" style="padding-top: 10px;padding-bottom: 30px;">

    <!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- 페이지 중간 [시작] :: 왼쪽:아이템목록 -->
    <div class="col-sm-6">

        <c:choose>
            <c:when test="${shopMaster.notice != null && shopMaster.notice != '' }">
                <div class="alert alert-warning" style="color: #0052A4;margin-bottom: 20px;padding: 10px;border-left: solid 6px #E37000;">${shopMaster.notice}</div>
            </c:when>
        </c:choose>

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


        <!-- 배송서비스 -->
        <c:choose>
            <c:when test="${shopMaster.deliveryService == 'Y'}">
                <div class="row">
                    <div class="col-sm-12" style="padding: 10px 0px 10px 0px;">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                <table style="width: 100%;">
                                    <tr>
                                        <td style="width: 80px;padding-left: 20px;text-align: left;"><i class="fa fa-truck fa-3x" aria-hidden="true" style="color:#514747;"></i></td>
                                        <td style="text-align: left;"><span style="font-size: 15px;font-weight: bold;">배송 서비스</span></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="panel-body" style="padding-left: 20px;padding-bottom: 20px;padding-top: 20px;padding-right: 100px;" id="dilivery_service_row">

                                <div id="DELIVERY_SERVICE_DETAIL"  style="display: none;">
                                    <div style="text-align: right;font-size: 10px;color: #58A578;" align="right"> 배송비는 '<b>${shopMaster.forDeliverCalcAddressSuburb} ${shopMaster.forDeliverCalcAddressPostcode}</b>'를 기준으로 Drive거리(Km)에 따라 계산됩니다.(Avoid toll road)  </div>
                                    <table class="table table-striped">
                                        <colgroup>
                                            <col style="width: 150px;">
                                            <col style="width: 10px;">
                                            <col style="width: *;">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">고객님주소</td>
                                            <td>:</td>
                                            <td><span id="cutomerAddress">${customerUser.addressHomeGmapFormattedAddress}</span> &nbsp;&nbsp;<a href="/customer/mypage/myDetailInfo.yum" style="font-size: 10px;">주소변경</a></td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">고객님댁 까지의 거리</td>
                                            <td>:</td>
                                            <td>
                                                <span id="distance">0</span> Km
                                                <input type="hidden" name="delivery_distance" id="delivery_distance" value="0" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="color: #797979; text-align: right;">배송비</td>
                                            <td>:</td>
                                            <td><span id="deliveryFee"> - </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" style="text-align: right; padding-right: 20px;color: #505050;font-weight: bold;">
                                                <label style="color: #F15F4C;">배송서비스를 이용하시겠습니까 ? &nbsp;&nbsp;&nbsp;<input type="checkbox" id="useDeliveryService" value="" style="transform: scale(1.5);" onchange="parseProductOrderValue()"></label>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>

                                <div id="CUSTOMER_ADDR_INVALID" class="alert alert-warning"  style="display: none;">
                                    <table style="width: 100%;">
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td style="color: #900C3E;">현재 고객님의 주소가 유효하지 않습니다.</td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>
                                        <tr style="height: 30px;">
                                            <td></td>
                                            <td><span style="color: #A2A4A4;">현재 고객님의 주소 :</span> <span id="customerAddrInvalidddress" style="color: #505050;"></span> </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td style="text-align: right;"> <a href="/customer/mypage/myDetailInfo.yum">My 푸드 > 개인정보 변경</a> 에서 주소를 수정하실수 있습니다.</td>
                                        </tr>
                                    </table>
                                </div>

                                <div id="NO_DELIVERABLE_SERVICE_AREA" class="alert alert-warning"  style="display: none;">
                                    <table style="width: 100%;">
                                        <colgroup>
                                            <col style="width: 80px;">
                                            <col style="width: 10px;">
                                            <col>
                                        </colgroup>
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info fa-2x" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td></td>
                                            <td style="color: #F15F4C;"><span id="noDeliverableServiceAreaMessage"></span></td>
                                        </tr>
                                        <tr style="height: 30px;">
                                            <td style="text-align: right" colspan="3"><a href="javascript:deliverySchedulePopup();">배송가능 지역보기</a></td>
                                        </tr>
                                    </table>
                                </div>

                                <div id="UNKNOWN_ERROR" class="alert alert-warning" style="display: none;">
                                    <table style="width: 100%;">
                                        <tr>
                                            <td style="width: 40px; text-align: center;"><i class="fa fa-info" aria-hidden="true" style="color: #900C3E;"></i></td>
                                            <td style="color: #900C3E;">죄송합니다. 배송비를 계산할 수가 없어 배송서비스를 이용하실수 없습니다.</td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>
                                        <tr style="height: 30px;">
                                            <td></td>
                                            <td><span style="color: #A2A4A4;" id="unknownErrorMessage"></span></td>
                                        </tr>
                                        <tr><td colspan="2"></td></tr>

                                    </table>
                                    <input type="hidden" id="deliveryServiceFee" value="${deliveryFee}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>


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
<!-- 페이지 중간 [끝] :: :: 왼쪽:아이템목록 오른쪽:주문내역  -->
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<script type="text/javascript">
    var SHOP_ID = "${shopMaster.shopId}";
</script>
</body>
</html>