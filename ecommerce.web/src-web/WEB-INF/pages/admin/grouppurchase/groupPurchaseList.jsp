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
                    url: "/amdin/grouppurchase/getGroupPurchases.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/framework/grouppurchase/deleteGroupPurchase.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	category : $("#category").val(),
                            type : $("#type").val(),
                            label : $("#label").val(),
                            useYn : $("#useYn").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	category : options.category,
                            type : options.type,
                            value : options.value
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "category",
                    fields: {
                    	category : { type: "string"},
                        type : { type: "string"},
                        value : { type: "string" }
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
		          { title : 'Organizer', field: 'purchaseOrganizer', width: 100, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Title', field: 'groupPurchaseTitle', width: 200, attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : 'Start', field: 'orderingStartDt', width: 200, attributes: {style: "color: e37200;font-weight: bolder;" }},
		          { title : 'End', field: 'orderingEndDt', width: 90, attributes: {style: "text-align: center;" }},
		          { title : 'Market Address', template: kendo.template($("#marketAddress-template").html()), attributes: {style: "text-align: center;" }},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
    	
        var category = dataItem.category;
        var type = dataItem.type;
        var value = dataItem.value;
        
        goDetailInfo(category, type, value);
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }
    
    function deleteItem(e) {
     	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
      
		BootstrapDialog.confirm({
            title: 'WARNING  :: 호주가 즐거운 이유, 쿠빵!!',
            message: '정말 삭제하시겠습니까?',
            type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
            btnOKLabel: 'OK', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
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

<script id="marketAddress-template" type="text/x-kendo-template">
    #= marketAddressState + '/' + marketAddressSuburb #
</script>

</head>
<body>

    <div id="registGroupPurchasePopup"></div>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Organizer : </td>
    	 		<td class="value"><c:out value="${cbxPurchaseOrganizer}" escapeXml="false"/></td>
                <td class="label">상태 :</td>
                <td class="value"><c:out value="${cbxStopSelling}" escapeXml="false"/></td>
                <td class="label">공동구매 시작일시 :  </td>
                <td class="value"><input id="orderingStartDt" name="orderingStartDt" value="${orderingStartDt}"></input></td>
                <td class="label">공동구매 종료일시 :  </td>
                <td class="value_end"><input id="orderingEndDt" name="orderingEndDt" value=""></input></td>
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
             	<button type="button" class="btn btn-primary" onclick="openRegistGroupPurchasePopup();">Add Item</button>
             </td>
         </tr>
     </table>

</body>
</html>