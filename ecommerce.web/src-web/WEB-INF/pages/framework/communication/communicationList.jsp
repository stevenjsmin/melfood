<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="/resources/js/melfood/framework/noticediscussmanager.js?ver=<%=Ctx.releaseVersion%>"></script>
    <script type="text/javascript">
        var KENDO_SELECTED_RECORD = null;
        $(document).ready(function () {

            // DEFINE DATASOURCE
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            var dataSource = new kendo.data.DataSource({
                pageSize: 20,
                serverPaging: true,
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/framework/communicationmanager/getCommunications.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    destroy: {
                        url: "/framework/communicationmanager/deleteCommunication.yum",
                        dataType: "jsonp",
                        type: "POST"
                    },
                    parameterMap: function(options, operation) {
                        if (operation == "read") {
                            return {
                                page : options.page,
                                pageSize : options.pageSize,
                                category : $("#category").val(),
                                contents : $("#contents").val(),
                                writeFrom : $("#writeFrom").val(),
                                writeTo : $("#writeTo").val(),
                                isForAllSeller : $("#isForAllSeller").val(),
                                isForAllSeller : $("#isForAllSeller").val(),
                                isForNotice : $("#isForNotice").val()
                            };
                        } else if (operation == "destroy") {
                            console.log(options);
                            return {
                                id : options.id
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id : { type: "string"}
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
            $("#grid_panel_main").kendoGrid({
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
                    pageSizes: [10, 20, 30],
                    messages: {
                        itemsPerPage: "",
                        display: "{0} - {1} / {2}"
                    }
                },
                columns: [
                    { hidden : true, field: 'id'},
                    { title : 'Subject', field: 'subject',attributes: {style: "color: e37200; font-weight: bolder;" }},
                    { title : 'Category', template: kendo.template($("#category-template").html()), width: 120},
                    { title : 'Date', width: 130, template: "#= kendo.toString(kendo.parseDate(createDatetime), 'yyyy-MM-dd hh:mm') #"},
                    { title : 'From', width: 200, template: kendo.template($("#writeFrom-template").html()), filterable: false},
                    { title : 'To', width: 200, template: kendo.template($("#writeTo-template").html()), filterable: false},
                    { title : 'For All Seller', template: kendo.template($("#forSeller-template").html()), width: 130},
                    { title : 'For All Customer', template: kendo.template($("#forCustomer-template").html()), width: 130},
                    { command: [ {text : "Delete", name: "destory", click: deleteItem}], width: 120}

                ] // End of Columns
            }); // End of GRID

            $("#grid_panel_main").dblclick(function(e) {
                var dataItem = KENDO_SELECTED_RECORD;
                var id = dataItem.id;
                goDetailInfo(id);
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

    <script id="writeFrom-template" type="text/x-kendo-template">
        # if (writeFrom != null && writeFrom != '') { #
        #= '<span style="color: 323132;">' + writeFrom #
        # } else { #
        # } #
    </script>
    <script id="writeTo-template" type="text/x-kendo-template">
        # if (writeTo != null && writeTo != '') { #
        #= '<span style="color: 323132;">' + writeTo #
        # } else { #
        # } #
    </script>
    <script id="forSeller-template" type="text/x-kendo-template">
        #= (isForAllSeller == 'Y') ? '<font color="262626">Yes</font>':'<font color="dfdfdf">No</font>' #
    </script>
    <script id="forCustomer-template" type="text/x-kendo-template">
        #= (isForAllCustomer == 'Y') ? '<font color="262626">Yes</font>':'<font color="dfdfdf">No</font>' #
    </script>
    <script id="category-template" type="text/x-kendo-template">
        # if (category == 'NOTICE') { #
        #= '<span style="color: 006F3C;">공지</span>' #
        # } else if (category == 'CHAT') { #
        #= '<span style="color: ACADAF;">대화</span>' #
        # } else { #
        #= '' #
        # } #
    </script>
</head>
<body>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div class="well">
    <table class="search_table">
        <tr>
            <td class="label">Contents : </td>
            <td class="value"><input class="form-control" id="contents" name="contents"></input></td>
            <td class="label">From(ID) :  </td>
            <td class="value"><input class="form-control" id="writeFrom" name="writeFrom"></input></td>
            <td class="label">To(ID) :  </td>
            <td class="value"><input class="form-control" id="writeTo" name="writeTo"></input></td>
            <td class="label">Category :  </td>
            <td class="value_end"><c:out value="${cbxCategory}" escapeXml="false"/></td>
            <td class="find"><button type="button" class="btn btn-info" onclick="search();">Search</button></td>
        </tr>
    </table>
</div>

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
            <button type="button" class="btn btn-primary" onclick="add();">Add Item</button>
        </td>
    </tr>
</table>

</body>
</html>