<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        var KENDO_SELECTED_RECORD_1 = null;
        var KENDO_SELECTED_RECORD_2 = null;
        $(document).ready(function () {
            $.ajaxSetup({cache: false});

            $("#fileUpload").kendoUpload({
                async: {
                    saveUrl: "/admin/grouppurchase/image/uploadFile.yum?groupPurchaseId=${groupPurchase.groupPurchaseId}",
                    removeUrl: "/admin/grouppurchase/image/removeFile.yum",
                    removeField: "removeFile",
                    autoUpload: true,
                    batch: true,
                },
                showFileList: false,
                localization: {
                    statusFailed: "Failed to file upload. Please check it again and retry.",
                    uploadSelectedFiles: "Upload file(s)"
                },
                complete: onComplete,
                error: onError
            });

            function onComplete(e) {
                search();
            }

            function onError(e) {
                var files = e.files;
                if (e.operation == "upload") {
                    warningPopup("Failed to upload : " + files[0].name);
                }
            }


            // Define datasource for IMAGE list
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource_1 = new kendo.data.DataSource({
                pageSize: 5,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/admin/grouppurchase/image/productImages.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    destroy: {
                        url: "/admin/grouppurchase/image/deleteProductImage.yum",
                        dataType: "jsonp",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                groupPurchaseId: ${groupPurchase.groupPurchaseId}
                            };
                        } else if (operation == "destroy") {
                            return {
                                groupPurchaseId: options.prodId,
                                imageSeq: options.imageSeq
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "prodId",
                        fields: {
                            prodId: {type: "string"},
                            imageSeq: {type: "string"}
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
            $("#grid_panel_main_1").kendoGrid({
                dataSource: dataSource_1,
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
                    pageSizes: [5, 10],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    {hidden: true, field: 'prodId'},
                    {hidden: true, field: 'imageSeq'},
                    {title: 'W * H', width: 150, template: kendo.template($("#size-template").html()), filterable: true, attributes: {style: "text-align: center;"}},
                    {title: 'File Name', field: 'fileName'},
                    {command: [{text: "Delete", name: "destory", click: deleteItem}], width: 100}

                ] // End of Columns
            }); // End of GRID


            // Define datasource for PRODUCT list
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource_2 = new kendo.data.DataSource({
                pageSize: 10,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/admin/grouppurchase/product/getProducts.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    destroy: {
                        url: "/admin/grouppurchase/product/deleteProduct.yum",
                        dataType: "jsonp",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                groupPurchaseId: ${groupPurchase.groupPurchaseId}
                            };
                        } else if (operation == "destroy") {
                            return {
                                groupPurchaseId: options.groupPurchaseId,
                                productId: options.productId
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "groupPurchaseId",
                        fields: {
                            groupPurchaseId: {type: "string"},
                            productId: {type: "string"}
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
            $("#grid_panel_main_2").kendoGrid({
                dataSource: dataSource_2,
                selectable: true,
                sortable: true,
                editable: false,
                change: onChangeProduct,
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
                    pageSizes: [10, 20, 30],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    {hidden: true, field: 'groupPurchaseId'},
                    {hidden: true, field: 'productId'},
                    {title: 'Name', width: 200, field: 'productName', filterable: true,  attributes: {style: "text-align: left;color: 606000; font-weight: bolder;" }},
                    {title: 'Owner', field: 'productOwner', filterable: true},
                    {title: 'Price($)', field: 'unitPrice', filterable: true, width: 100},
                    { title : 'Status', template: kendo.template($("#stopSelling-template").html()), width: 200},
                    {command: [{text: "Stop", click: stopSelling}], width: 100},
                    {command: [{text: "Info", click: detailInfoProduct}], width: 100},
                    {command: [{text: "Delete", name: "destory", click: deleteItemProduct}], width: 100}

                ] // End of Columns
            }); // End of GRID


        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        function refreshForProductList(){
            $('#grid_panel_main_2').data('kendoGrid').dataSource.read();
            $('#grid_panel_main_2').data('kendoGrid').refresh();
        }

        function onChange(e) {
            var gridRecord = e.sender;
            KENDO_SELECTED_RECORD_1 = gridRecord.dataItem(gridRecord.select());
        }

        function onChangeProduct(e) {
            var gridRecord = e.sender;
            KENDO_SELECTED_RECORD_2 = gridRecord.dataItem(gridRecord.select());
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
                callback: function (result) {
                    if (result) {
                        var grid = $("#grid_panel_main_1").data("kendoGrid");
                        grid.dataSource.remove(dataItem);
                        grid.dataSource.sync();
                        grid.refresh();
                    }
                }
            });
        }

        function deleteItemProduct(e) {
            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
            var grid = $("#grid_panel_main_2").data("kendoGrid");
            grid.dataSource.remove(dataItem);
            grid.dataSource.sync();
            grid.refresh();
        }

    </script>

    <script type="text/javascript">
        function openImageViwer() {

            $("#productImagePopup").kendoWindow({
                content: "/admin/grouppurchase/image/productImageViewer.yum?groupPurchaseId=" + "${groupPurchase.groupPurchaseId}",
                actions: ["Minimize", "Maximize", "Close"],
                title: "Product images",
                modal: true,
                iframe: true,
                position:{ top:"200", left:"25%"}
            });

            var popup_dialog = $("#productImagePopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 900,
                height: 430
            });
            // popup_dialog.center();

            $("#productImagePopup").data("kendoWindow").open();
        }
        function closeImageWindow() {
            var win_dialog = $("#productImagePopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>

    <script type="text/javascript">
        function detailInfoProduct(e) {
            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

            $("#productOverviewPopup").kendoWindow({
                content: "/admin/grouppurchase/product/overviewProductInfo.yum?productId=" + dataItem.productId,
                actions: ["Minimize", "Maximize", "Close"],
                title: "Product detail",
                modal: true,
                iframe: true,
                position:{ top:"200", left:"25%"}
            });

            var popup_dialog = $("#productOverviewPopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 900,
                height: 500
            });
            // popup_dialog.center();

            $("#productOverviewPopup").data("kendoWindow").open();
        }
        function closeDetailInfoProduct() {
            var win_dialog = $("#productOverviewPopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>


    <script type="text/javascript">
        function stopSelling(e) {
            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

            $("#productStopSellingPopup").kendoWindow({
                content: "/admin/grouppurchase/product/stopSellingForm.yum?groupPurchaseId=" + dataItem.groupPurchaseId + "&productId=" + dataItem.productId,
                actions: ["Minimize", "Maximize", "Close"],
                title: "Stop selling",
                modal: true,
                iframe: true,
                position:{ top:"200", left:"25%"}
            });

            var popup_dialog = $("#productStopSellingPopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 700,
                height: 300
            });
            //popup_dialog.center();

            $("#productStopSellingPopup").data("kendoWindow").open();
        }
        function closeStopSelling() {
            var win_dialog = $("#productStopSellingPopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>


    <script id="size-template" type="text/x-kendo-template">
        #= ( (width != null && height != null) ? (width + ' X ' + height) : '-')  #
    </script>
    <script id="stopSelling-template" type="text/x-kendo-template">
        # if (stopSelling === 'Y') { #
            #= '<span style="color: F9B583;">판매정지 :' + abbreviate(stopSellingReason,10) + '</span>' #
        # } else { #
            판매중
        # }#
    </script>
</head>


<body>
<div id="productImagePopup"></div>
<div id="groupPurchasePopup"></div>
<div id="productOverviewPopup"></div>
<div id="productStopSellingPopup"></div>
<table>
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="200px"/>
                    <col width="300px"/>
                    <col width="200px"/>
                    <col width="300px"/>
                </colgroup>


                <tr>
                    <td colspan="4">
                        <span class="subtitle"> 공동구매 상품이미지</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table style="width: 850px;">
                            <tr>
                                <td>
                                    <table class="detail_table">
                                        <tr>
                                            <td class="label" style="width: 250px;">File Upload</td>
                                            <td class="value" style="width: 500px;"><input type="file" name="files" id="fileUpload"/></td>
                                            <td class="value" style="width: 70px;"><img onclick="openImageViwer();" src="/resources/image/imgviewer.png" style="height: 30px; width: 30px;cursor: hand;"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>

                        <div id="grid_panel_main_1"></div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle"> 공동구매 기본정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>


                <tr>
                    <td class="label">Title :</td>
                    <td class="value" colspan="3">${groupPurchase.groupPurchaseTitle}</td>
                </tr>
                <tr>
                    <td class="label">Subtitle :</td>
                    <td class="value" colspan="2">${groupPurchase.groupPurchaseSubtitle}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">공동구매 주관자 :</td>
                    <td class="value">${groupPurchase.purchaseOrganizerName} (ID : ${groupPurchase.purchaseOrganizer})</td>
                    <td class="label">최소 참여금액 :</td>
                    <td class="value">$ <fmt:formatNumber type="number" minFractionDigits="2" value="${groupPurchase.minimumPurchaseAmount}"/></td>
                </tr>
                <tr>
                    <td class="label">주문받는 기간 :</td>
                    <td class="value">${groupPurchase.orderStartDt} ~ ${groupPurchase.orderEndDt}</td>
                    <td class="label">할인 비율/금액 :</td>
                    <td class="value">
                        <c:choose>
                            <c:when test="${groupPurchase.discountMethod == 'RATE'}">
                                <fmt:formatNumber type="percent" minFractionDigits="0" value="${groupPurchase.discountRateValue}"/>
                            </c:when>
                            <c:when test="${groupPurchase.discountMethod == 'FIXED'}">
                                $ <fmt:formatNumber type="number" minFractionDigits="2" value="${groupPurchase.discountFixedAmount}"/>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="label">배달가능여부 :</td>
                    <td class="value">
                            <c:choose>
                                <c:when test="${groupPurchase.deliverable == 'Y'}">
                                    배달가능
                                </c:when>
                                <c:when test="${groupPurchase.deliverable == 'N'}">
                                    배달 서비스없음
                                </c:when>
                            </c:choose>
                    </td>
                    <td class="label">배달비 / Km :</td>
                    <td class="value">
                        $ <fmt:formatNumber type="number" minFractionDigits="2" value="${groupPurchase.deliveryFeePerKm}"/>
                    </td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">상태 :</td>
                    <td class="value">
                        <c:choose>
                            <c:when test="${groupPurchase.stopSelling == 'Y'}">
                                판매 정지
                            </c:when>
                            <c:when test="${groupPurchase.stopSelling == 'N'}">
                                판매 중
                            </c:when>
                        </c:choose>

                    </td>
                </tr>
                <c:choose>
                    <c:when test="${groupPurchase.stopSelling == 'Y'}">
                        <tr>
                            <td class="label">공동구매 정지이유 :</td>
                            <td class="value" colspan="2">${groupPurchase.stopSellingReason}</td>
                            <td></td>
                        </tr>
                    </c:when>
                </c:choose>

                <tr>
                    <td colspan="4">
                        <br/>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td class="label">마켓오픈 시간 :</td>
                    <td class="value">${groupPurchase.marketOpenStartDt} ~ ${groupPurchase.marketOpenEndDt}</td>
                </tr>
                <tr>
                    <td class="label">장소</td>
                    <td class="value" colspan="3">${groupPurchase.marketAddressStreet}, ${groupPurchase.marketAddressSuburb}</td>
                </tr>
                <tr>
                    <td class="label">비고</td>
                    <td class="value" colspan="3">${groupPurchase.marketAddressComment}</td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="value" colspan="4">${groupPurchase.groupPurchaseNotice}</td>
                </tr>


                <tr>
                    <td colspan="4">
                        <table class="action_button_table" width="100%">
                            <tr>
                                <td>
                                    <a href="javascript:openUpdateGroupPurchasePopup('${groupPurchase.groupPurchaseId}');" class="btn btn-default btn-sm">기본정보수정</a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle"> 공동구매 상품목록</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div id="grid_panel_main_2"></div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <table class="action_button_table" width="100%">
                            <tr>
                                <td>
                                    <a href="javascript:searchProductPopup('${groupPurchase.groupPurchaseId}');" class="btn btn-default btn-sm">공동구매 상품등록</a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>


                <tr>
                    <td colspan="4">&nbsp;</td>
                </tr>

            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var GROUP_PURCHASE_ID = "${groupPurchase.groupPurchaseId}";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>