<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                    url: "/admin/productmgt/products.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/productmgt/deleteProduct.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	prodId : $("#prodId").val(),
                        	categoryId : $("#categoryId").val(),
                        	name : $("#name").val(),
                        	description : $("#description").val(),
                        	validateUntil : $("#validateUntil").val(),
                        	seller : $("#seller").val(),
                        	registerStatus : $("#registerStatus").val(),
                        	sellingStatus : $("#sellingStatus").val(),
                        	sellingCommissionType : $("#sellingCommissionType").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	prodId : options.prodId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "categoryId",
                    fields: {
                    	prodId : { type: "string"}
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
		          { title : 'ID', field: 'prodId', width: 40, attributes: {style: "color: bfbfbf; font-weight: bolder;" }},
		          { title : 'Name', field: 'name', width: 250, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Seller', field: 'seller', width: 100},
		          { title : 'Validity date', field: 'validateUntil', width: 70, attributes: {style: "text-align: center;" }},
		          { title : 'Register Status', template: kendo.template($("#registerStatus-template").html()), width: 100},
		          { title : 'Selling Status', template: kendo.template($("#sellingStatus-template").html()),  width: 100},
		          { title : 'Stock Cnt', field: 'inStockCnt', width: 50, attributes: {style: "text-align: right;" }},
		          { title : 'Unit Price ($)', field: 'unitPrice', width: 70, attributes: {style: "text-align: right;" }, format: "{0:c}"},
		          { title : 'Commission', field: 'sellingCommission', width: 70, attributes: {style: "text-align: right;" }}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var prodId = dataItem.prodId;
        goDetailInfo(prodId);
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

<script id="registerStatus-template" type="text/x-kendo-template">
    # if (registerStatus == 'COMPLETE') { #
		  #= '<span style="color: 3392DA;">' + registerStatusName + '</span>' #
    # } else if (registerStatus == 'REGISTRATION') { #
		  #= '<span style="color: 23B772;">' + registerStatusName + '</span>' #
    # } else if (registerStatus == 'REJECT') { #
		  #= '<span style="color: B74F40;">' + registerStatusName + '</span>' #
    # } #
</script>
<script id="sellingStatus-template" type="text/x-kendo-template">
    # if (sellingStatus == 'ON_SELLING') { #
		  #= '<span style="color: 3392DA;">' + sellingStatusName + '</span>' #
    # } else { #
		  #= '<span style="color: E79E8F;">' + sellingStatusName + '</span>' #
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
    	 		<td class="label">Seller : </td>
    	 		<td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
    	 		<td class="label">Category : </td>
    	 		<td class="value"><c:out value="${cbxCategory}" escapeXml="false"/></td>
    	 		<td class="label">Regi. : </td>
    	 		<td class="value"><c:out value="${cbxRegisterStatus}" escapeXml="false"/></td>
    	 		<td class="label">Selling : </td>
    	 		<td class="value"><c:out value="${cbxSellingStatus}" escapeXml="false"/></td>
    	 		<td class="label">Name : </td>
    	 		<td class="value_end"><input class="form-control" id="name" name="name"></td>
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