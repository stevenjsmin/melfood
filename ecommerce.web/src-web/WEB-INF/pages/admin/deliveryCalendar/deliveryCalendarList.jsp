<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/deliverycalendarmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                    url: "/admin/deliverycalendarmgt/getDeliveryCalendars.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/deliverycalendarmgt/deleteDeliveryCalendar.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                            sellerId : $("#sellerId").val(),
                            searchDateFrom : $("#searchDateFrom").val(),
                            searchDateTo : $("#searchDateTo").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                            deliveryCalendarId : options.deliveryCalendarId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "deliveryCalendarId",
                    fields: {
                        deliveryCalendarId : { type: "string"}
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
				  { hidden : true, field: 'deliveryCalendarId'},
				  { hidden : true, field: 'sellerId'},
				  { hidden : true, field: 'yyyyMmDd'},
		          { title : 'Seller', field: 'seller.userName',width: 120},
		          { title : 'Date', field: 'yyyyMmDd', attributes: {style: "color: 606000; font-weight: bolder;" },width: 120},
		          { title : '배송지역이름', field: 'deliveryBaseAddressNote', attributes: {style: "color: 606000;" },width: 150},
		          { title : 'AM/PM', template: kendo.template($("#ampm-template").html()), width: 100},
		          { title : 'Base Address', field: 'deliveryBaseAddressGmapFormattedAddress', width: 400},
		          { title : '배송반경 Km', template: kendo.template($("#deliveryLimitKm-template").html()), width: 100, attributes: {style: "text-align: right;" }},
		          { title : '기본 배송비', template: kendo.template($("#deliveryBasicFee-template").html()), width: 100, attributes: {style: "text-align: right;" }},
		          { title : '배송비/Km', template: kendo.template($("#deliveryFeePerKm-template").html()), width: 100, attributes: {style: "text-align: right;" }},
		          { title : '활성/비활성', template: kendo.template($("#useYn-template").html()), width: 100, attributes: {style: "text-align: right;" }},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}, {text : "Enable/Disable", name: "modify", click: enableAndDisable}]}
		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var prodId = dataItem.prodId;
        // Nothing to do
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
    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
	function enableAndDisable(e) {
		var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

	    $.ajax({
	         url  : "/admin/deliverycalendarmgt/enableAndDisableDiliveryCalendar.yum",
	         data      : {
                 deliveryCalendarId : dataItem.deliveryCalendarId
	         },
	         success : callbackEnableAndDisable
	    });  
	}
	   
	function callbackEnableAndDisable(data) {
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
     function addDeliveryCalendar() {
         $("#deliveryCalendarPopup").kendoWindow({
               content: "/admin/deliverycalendarmgt/addCalendarForm.yum",
               actions: [ "Minimize", "Maximize","Close" ],
               title: "Add Delivery Calendar",
               modal: true,
               iframe: true
         });
         
         var popupwid_dialog = $("#deliveryCalendarPopup").data("kendoWindow");
         popupwid_dialog.setOptions({
               width: 700,
               height: 450
             });
         popupwid_dialog.center();
         
         $("#deliveryCalendarPopup").data("kendoWindow").open();
     }
	function closeOptionWindow() {
		var win_dialog = $("#deliveryCalendarPopup").data("kendoWindow");
		win_dialog.close();
	}     
</script>

<script id="useYn-template" type="text/x-kendo-template">
    #= (useYn == 'Y') ? '<font color="262626">예</font>':'<font color="dfdfdf">아니오</font>' #
</script>
<script id="ampm-template" type="text/x-kendo-template">
    # if (amPm == 'AM') { #
            <span style="color: E57014;">AM</span>
    # } else if (amPm == 'PM') { #
            <span style="color: 7768E5;">PM</span>
    # } else if (amPm == 'ALL') { #
            <span style="color: 738182;">상관없음</span>
    # } else { #
    # } #
</script>
<script id="deliveryLimitKm-template" type="text/x-kendo-template">
    #= deliveryLimitKm + ' Km' #
</script>
<script id="deliveryBasicFee-template" type="text/x-kendo-template">
    #= '$ ' + deliveryBasicFee  #
</script>
<script id="deliveryFeePerKm-template" type="text/x-kendo-template">
    #= '$ ' + deliveryFeePerKm  #
</script>
</head>
<body>

	<div id="deliveryCalendarPopup"></div>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Seller : </td>
    	 		<td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
    	 		<td class="label">Start Date :  </td>
    	 		<td class="value"><input id="searchDateFrom" name="searchDateFrom" value="${defaultSearchDateFrom}"></input></td>
    	 		<td class="label">End Date :  </td>
    	 		<td class="value_end"><input id="searchDateTo" name="searchDateTo" value=""></input></td>    	 		
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
             	<button type="button" class="btn btn-primary" onclick="addDeliveryCalendar();">Add Item</button>
             </td>
         </tr>
     </table>

</body>
</html>