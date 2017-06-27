<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
var KENDO_SELECTED_RECORD_1 = null;
$(document).ready(function () {

    // DEFINE DATASOURCE
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
    var dataSource = new kendo.data.DataSource({
            pageSize: 20,
            serverPaging: true,
            serverFiltering: true,
            transport: {
                read: {
                    url: "/admin/grouppurchase/getGroupPurchases.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/grouppurchase/deleteGroupPurchase.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,
                            purchaseOrganizer : $("#purchaseOrganizer").val(),
                            stopSelling : $("#stopSelling").val(),
                            searchDateFrom : $("#searchDateFrom").val(),
                            searchDateTo : $("#searchDateTo").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                            groupPurchaseId : options.groupPurchaseId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "groupPurchaseId",
                    fields: {
                        groupPurchaseId : { type: "string" }
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
    $("#grid_panel_main_1").kendoGrid({
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
                  { hidden : true, field: 'groupPurchaseId'},
		          { title : '공구 게시자', template: kendo.template($("#purchaseOrganizer-template").html()), width: 200, attributes: {style: "color: 606000; font-weight: bolder;" }},
		          { title : 'Title', field: 'groupPurchaseTitle', attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : '주문 시작일시', field: 'orderStartDt', width: 150, attributes: {style: "color: 128D15;text-align: left;" }},
		          { title : '주문 종료일시', field: 'orderEndDt', width: 150, attributes: {style: "color: 128D15;text-align: left;" }},
		          { title : '마켓오픈 일시', field: 'marketOpenStartDt', width: 150, attributes: {style: "color: e37200;font-weight: bolder;text-align: left;" }},
		          { title : '마켓상태', template: kendo.template($("#stopSelling-template").html()), width: 200},
		          { title : '마켓오픈 주소', template: kendo.template($("#marketAddress-template").html()), attributes: {style: "text-align: left;" }},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}], width: 140}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main_1").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD_1;
        var groupPurchaseId = dataItem.groupPurchaseId;

        goDetailInfo(groupPurchaseId);
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
        KENDO_SELECTED_RECORD_1 = gridRecord.dataItem(gridRecord.select());
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
                	var grid = $("#grid_panel_main_1").data("kendoGrid");
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

<script id="marketAddress-template" type="text/x-kendo-template">
    #= marketAddressState + ' / ' + marketAddressSuburb #
</script>
<script id="purchaseOrganizer-template" type="text/x-kendo-template">
    #= purchaseOrganizer + ' / ' + purchaseOrganizerName #
</script>
<script id="stopSelling-template" type="text/x-kendo-template">
    # if (stopSelling == 'Y') { #
        <span style="color: d58512">판매정지</span>
    # } else if (stopSelling == 'N') { #
        판매중
    # } else { #
          -
    # } #
</script>
</head>
<body>

    <div id="groupPurchasePopup"></div>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Organizer : </td>
                <td class="value"><c:out value="${cbxPurchaseOrganizer}" escapeXml="false"/></td>
                <td class="label">마켓상태 : </td>
                <td class="value"><c:out value="${cbxStopSelling}" escapeXml="false"/></td>
                <td class="label">주문 시작일시 :  </td>
                <td class="value_end"><input id="searchDateFrom" name="searchDateFrom" value="${orderStartDt}"></input></td>
                <td class="label"> ~ </td>
                <td class="value_end"><input id="searchDateTo" name="searchDateTo" value=""></input></td>
                <td class="find"><button type="button" class="btn btn-info" onclick="search();">Search</button></td>

    	 	</tr>
    	 </table>
    </div>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Table List -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div id="grid_panel_main_1"></div>

    <br/>
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Extra buttons -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
     <table class="action_button_table">
         <tr>
             <td>
             	<button type="button" class="btn btn-primary" onclick="openRegistGroupPurchasePopup();">Add Item</button>
             </td>
         </tr>
     </table>

    <script type="text/javascript">
        var GROUP_PURCHASE_ID = "";
        var ACTION_MODE = "ADD";
    </script>
</body>
</html>