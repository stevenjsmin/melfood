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
                        	sellerId : options.sellerId,
                        	yyyyMmDd : options.yyyyMmDd,
                        	deliverySeq : options.deliverySeq
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "sellerId",
                    fields: {
                    	sellerId : { type: "string"},
                    	yyyyMmDd : { type: "string"},
                    	amPm : { type: "string"},
                    	postcodeId : { type: "string"}
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
				  { hidden : true, field: 'sellerId'},
				  { hidden : true, field: 'yyyyMmDd'},
				  { hidden : true, field: 'deliverySeq'},
		          { title : 'Seller', field: 'seller.userName',width: 120},
		          { title : 'Date', field: 'yyyyMmDd', attributes: {style: "color: 606000; font-weight: bolder;" },width: 120},
		          { title : 'Time', template: kendo.template($("#time-template").html()), width: 150},
		          { title : 'Pickup/Deliver', template: kendo.template($("#deliverMethod-template").html()), width: 200},
		          { title : 'Postcode', template: kendo.template($("#addressPostcode-template").html()), width: 100},
		          { title : 'Suburb', template: kendo.template($("#suburb-address-template").html())},
		          { title : 'Street', template: kendo.template($("#street-address-template").html())},
		          { title : 'Enabled', field: 'useYn', template: kendo.template($("#useYn-template").html()), width: 100, attributes: {style: "text-align: center;" }},
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
            title: 'WARNING  :: 호주가 즐거운 이유, 쿠빵!!',
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
	           sellerId : dataItem.sellerId,
	           yyyyMmDd : dataItem.yyyyMmDd,
	           deliverySeq : dataItem.deliverySeq
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
               height: 600
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
<script id="time-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          <span style="color: E57014;">
    # } else if (isPickup == 'N') { # 
          <span style="color: 7768E5;">
    # } else { #
          <span>
    # } #

    # if (btwnFromHhmm != '' && btwnFromHhmm != null) { #
          #= btwnFromHhmm #
    # } else { #
    # } #
    ~
    # if (btwnToHhmm != '' && btwnToHhmm != null) { #
          #= btwnToHhmm #
    # } else { #
    # } #
    </span> 
</script>
<script id="deliverMethod-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          <span style="color: E57014;">고객님께서 픽업 </span>
    # } else if (isPickup == 'N') { # 
          <span style="color: 7768E5;">고객님께 배달 </span>
    # } else { #
    # } #
</script>
<script id="addressPostcode-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          #= '<span style="color: E57014;">' + addressPostcode + '</span>' #
    # } else if (isPickup == 'N') { # 
          #= '<span style="color: 7768E5;">' + addressPostcode + '</span>' #
    # } else { #
          #= addressPostcode #
    # } #
</script>
<script id="suburb-address-template" type="text/x-kendo-template">
    #= '<font color="dfdfdf">' + addressState + ' / </font>' #

    # if (isPickup == 'Y') { #
          #= '<span style="color: E57014;">' + addressSuburb + '</span>' #
    # } else if (isPickup == 'N') { # 
          #= '<span style="color: 7768E5;">' + addressSuburb + '</span>' #
    # } else { #
          #= addressSuburb #
    # } #

</script>
<script id="street-address-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
		  #= '<font color="E57014"> ' + addressStreet + '</font>' #
    # } #
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