<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
var KENDO_SELECTED_RECORD = null;
$(document).ready(function () {

    $("#searchDateFrom").kendoDatePicker({
        format: "yyyy-MM-dd",
        start: "year"
    });
    var datepicker1 = $("#searchDateFrom").data("kendoDatePicker");
    $("#searchDateFrom").click(function() {
        datepicker1.open();
    });

    $("#searchDateTo").kendoDatePicker({
        format: "yyyy-MM-dd",
        start: "year"
    });
    var datepicker2 = $("#searchDateTo").data("kendoDatePicker");
    $("#searchDateTo").click(function() {
        datepicker2.open();
    });

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
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,
                            category : "QNA",
                            progressStatus : $("#progressStatus").val(),
                            searchDateFrom : $("#searchDateFrom").val(),
                            searchDateTo : $("#searchDateTo").val()
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
		          { title : 'Mobile', field: 'writerMobile',attributes: {style: "color: e37200; font-weight: bolder;" },width: 100},
		          { title : 'Email', field: 'writerEmail'},
                  { title : 'Question', template: kendo.template($("#question-template").html()), filterable: false},
                  { title : 'Status', template: kendo.template($("#progressStatus-template").html()), filterable: false, width: 100},
                  { title : 'Date', template: "#= kendo.toString(kendo.parseDate(createDatetime), 'yyyy-MM-dd hh:mm') #", width: 120},
		          { command: [ {text : "Not open", click: doStatusNotOpen}],width: 100},
		          { command: [ {text : "Open", click: doStatusOpen}],width: 100},
		          { command: [ {text : "Complete", click: doStatusComplete}],width: 100},
		          { command: [ {text : "Delete", click: doDeleteItem}],width: 100}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
        var dataItem = KENDO_SELECTED_RECORD;
        var id = dataItem.id;
        qnADetailInfoPopup(id);
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }

    search();
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
<script type="text/javascript">
    function qnADetailInfoPopup(id){

        $("#QnAInfoPopup").kendoWindow({
            content: "/framework/communicationmanager/getCommunication.yum?id=" + id,
            actions: [ "Minimize", "Maximize","Close" ],
            title: "QnA 상세정보",
            modal: true,
            iframe: true
        });

        var popup_dialog = $("#QnAInfoPopup").data("kendoWindow");
        popup_dialog.setOptions({
            pinned: true,
            width: 650,height: 450,
            open: function (e) {
                this.wrapper.css({ top: 100 });
            }
        });
        popup_dialog.center().open();

    }
    function closeQnADetailInfoPopup() {
        var win_dialog = $("#QnAInfoPopup").data("kendoWindow");
        win_dialog.close();
    }
</script>
<script type="text/javascript">
    function doStatusNotOpen(e) {
        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

        $.ajax({
            url  : "/framework/communicationmanager/updateProgressStatus.yum",
            data      : {
                id : dataItem.id,
                progressStatus : 'NOT_OPEN'
            },
            success : callbackStatusUpdate
        });
    }
    function doStatusOpen(e) {
        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

        $.ajax({
            url  : "/framework/communicationmanager/updateProgressStatus.yum",
            data      : {
                id : dataItem.id,
                progressStatus : 'OPEN'
            },
            success : callbackStatusUpdate
        });
    }
    function doStatusComplete(e) {
        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

        $.ajax({
            url  : "/framework/communicationmanager/updateProgressStatus.yum",
            data      : {
                id : dataItem.id,
                progressStatus : 'COMPLETE'
            },
            success : callbackStatusUpdate
        });
    }

    function doDeleteItem(e) {
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
                    $.ajax({
                        url  : "/framework/communicationmanager/deleteCommunication.yum",
                        data      : {
                            id : dataItem.id
                        },
                        success : callbackStatusUpdate
                    });
                }
            }
        });
    }

    function callbackStatusUpdate(data) {
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
    function search(){
        $('#grid_panel_main').data('kendoGrid').dataSource.read();
        $('#grid_panel_main').data('kendoGrid').refresh();
    }
</script>

<script id="progressStatus-template" type="text/x-kendo-template">
    # if (progressStatus == 'NOT_OPEN') { #
        #=  '<span style="color: C14F51;">처리 전</span>' #
    # } else if (progressStatus == 'OPEN') { #
        #=  '<span style="color: 128D15;">처리 중</span>' #
    # } else if (progressStatus == 'COMPLETE') { #
        #=  '<span style="color: 5974AB;">처리 완료</span>' #
    # } else { #
        #=  '-' #
    # } #

</script>
<script id="question-template" type="text/x-kendo-template">
    # if (contents != '' && contents != null) { #
        #=  abbreviate(contents,20) #
    # } else { #
        #=  '-'#
    # }#
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
                <td class="label">상태 : </td>
                <td class="value"><c:out value="${cbxProgressStatus}" escapeXml="false"/></td>
                <td class="label">Start Date :  </td>
                <td class="value"><input id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}"></input></td>
                <td class="label">End Date :  </td>
                <td class="value_end"><input id="searchDateTo" name="searchDateTo" value="${searchDateTo}"></input></td>
                <td class="find"><button type="button" class="btn btn-info" onclick="search();">Search</button></td>
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