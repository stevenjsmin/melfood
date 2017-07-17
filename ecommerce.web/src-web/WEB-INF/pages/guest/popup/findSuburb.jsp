<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="/resources/js/melfood/framework/postcodemanager.js?ver=<%=Ctx.releaseVersion%>"></script>

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
                        url: "/framework/postcodemanager/getPostcodes.yum",
                        dataType: "json",
                        type: "POST"
                    },
                    destroy: {
                        url: "/framework/postcodemanager/postcodeDelete.yum",
                        dataType: "jsonp",
                        type: "POST"
                    },
                    parameterMap: function (options, operation) {
                        if (operation == "read") {
                            return {
                                page: options.page,
                                pageSize: options.pageSize,
                                state: $("#addressState").val(),
                                suburb: $("#suburb").val(),
                                postcode: $("#postcode").val()
                            };
                        } else if (operation == "destroy") {
                            console.log(options);
                            return {
                                postcodeId: options.postcodeId
                            };
                        }
                    }
                },
                schema: {
                    model: {
                        id: "postcodeId",
                        fields: {postcodeId: {type: "string"}}
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
                    operators: {string: {contains: "Contains"}}
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
                    {hidden: true, field: "state"},
                    {hidden: true, field: "postcodeId"},
                    {hidden: true, field: "suburb"},
                    {title: 'State',field: 'stateLabel',width: 150,attributes: {style: "color: 606000; font-weight: bolder;"},filterable: false},
                    {title: 'Postcode',field: 'postcode',width: 150,attributes: {style: "color: 939300; font-weight: bolder;"}},
                    {title: 'Suburb',field: 'suburb',width: 200,attributes: {style: "color: e37200;font-weight: bolder;"}},
                    {command: [ {text : "선택", name: "destory", click: selectItem}]}
                ] // End of Columns
            }); // End of GRID

            $("#grid_panel_main").dblclick(function (e) {
                var dataItem = KENDO_SELECTED_RECORD;
                var postcodeId = dataItem.postcodeId;
            });

            function onChange(e) {
                var gridRecord = e.sender;
                KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
            }

            search();

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function selectItem(e) {
            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
            window.parent.document.getElementById('addressState').value = dataItem.state;
            window.parent.document.getElementById('addressSuburb').value = dataItem.suburb;
            window.parent.document.getElementById('addressPostcode').value = dataItem.postcode;

            parent.closeFindSububPopup();
        }
    </script>


</head>

<body>


<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Search -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div class="well">
    <table class="search_table">
        <tr>
            <td class="label">State</td>
            <td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
            <td class="label">Postcode</td>
            <td class="value"> <input class="form-control" type="text" id="postcode" name="postcode" value='' maxlength="4" /></td>
            <td class="label">Suburb</td>
            <td class="value_end"><input class="form-control" type="text" id="suburb" name="suburb" value=''/></td>
            <td class="find"> <button type="button" class="btn btn-info" onclick="search();">Search</button> </td>
        </tr>
    </table>
</div>

<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<!-- Table List -->
<!-- ++++++++++++++++++++++++++++++++++++++++++ -->
<div id="grid_panel_main"></div>

<table class="detail_table_c" style="margin-top: 10px;">
    <tr>
        <td colspan="4" style="text-align: right;padding-right: 0px;">
            <button type="button" class="btn btn-default btn-sm" onclick="parent.closeFindSububPopup();"><i class="fa fa-times" aria-hidden="true"></i> Close</button>
        </td>
    </tr>
</table>


<script type="text/javascript">
    var ACTION_MODE = "ADD";
</script>
</body>
</html>