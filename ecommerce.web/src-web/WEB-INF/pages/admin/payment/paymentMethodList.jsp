<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script src="/resources/js/melfood/admin/paymentmethodmgt.js?ver=<%=melfood.framework.Ctx.releaseVersion%>"></script>
<script type="text/javascript">
var KENDO_SELECTED_RECORD = null;
$(document).ready(function () {
	 $.ajaxSetup({ cache: false });
	 
    // DEFINE DATASOURCE
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
    var dataSource = new kendo.data.DataSource({
    		pageSize: 20,
            serverPaging: true,
            serverFiltering: true,
            transport: {
                read: {
                    url: "/admin/paymentmethodmgt/getPaymentMethods.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/paymentmethodmgt/deletePaymentMethod.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                            userId : $("#userId").val(),
                            paymentMethod : $("#paymentMethod").val(),
                            bankName : $("#bankName").val(),
                            useYn : $("#useYn").val()
                        };
                    } else if (operation == "destroy") {
                        return {
                        	userId : options.userId,
                        	methodSeq : options.methodSeq
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "userId",
                    fields: {
                    	userId : { type: "string"},
                    	methodSeq : { type: "string"}
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
		          { hidden : true, field: 'userId'},
		          { hidden : true, field: 'methodSeq'},
		          { title : 'Seller', field: 'seller.userName', width: 100, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Method', template: kendo.template($("#paymentMethod-template").html())},
		          { title : 'Bank', field: 'bankNameCodeName'},
		          { title : 'BSB.Account No / Owner', template: kendo.template($("#bankDetail-template").html())},
		          { title : 'Use Y/N', template: kendo.template($("#useYn-template").html())},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}, {text : "Enable/Disable", name: "modify", click: enableAndDisable}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var userId = dataItem.userId;
        var methodSeq = dataItem.methodSeq;
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
	         url  : "/admin/paymentmethodmgt/enableAndDisablePaymentMethod.yum",
	         data      : {
	           userId : dataItem.userId,
	           methodSeq : dataItem.methodSeq
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

<script id="paymentMethod-template" type="text/x-kendo-template">
    # if (paymentMethod == 'CASH') { #
		  #= '<span style="color: CA0000;">' + paymentMethodCodeName + '</span>' #
    # } else if (paymentMethod == 'ACCOUNT_TRANSFER') { #
		  #= '<span style="color: 3794DA;">' + paymentMethodCodeName + '</span>' #
    # } else if (paymentMethod == 'PAYPAL') { #
		  #= '<span style="color: B4C23D;">' + paymentMethodCodeName + '</span>' #
    # } else if (paymentMethod == 'CREDIT_CARD') { #
		  #= '<span style="color: 7768E5;">' + paymentMethodCodeName + '</span>' #
    # } #
</script>
<script id="bankDetail-template" type="text/x-kendo-template">
    # if (paymentMethod == 'ACCOUNT_TRANSFER') { #
          #= bankBsb  + ' ' + bankAccountNo + ' | ' + bankAccountOwnerName #
    # } else { #
    # } #
</script>

<script id="useYn-template" type="text/x-kendo-template">
    # if (useYn == 'Y') { #
          #= '<font color="262626">사용</font>' #
    # } else if (useYn == 'N') { #
          #= '<font color="dfdfdf">미사용</font>' #
    # } else { #
          #= '<font color="dfdfdf">n/a</font>' #
    # } #
</script>

</head>
<body>

	<div id="paymentMethodPopup"></div>
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Seller : </td>
    	 		<td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
    	 		<td class="label">Method : </td>
    	 		<td class="value"><c:out value="${cbxPaymentMethod}" escapeXml="false"/></td>
    	 		<td class="label">Bank : </td>
    	 		<td class="value"><c:out value="${cbxBankName}" escapeXml="false"/></td>
    	 		<td class="label">Use Y/N : </td>
    	 		<td class="value_end"><c:out value="${cbxUseYn}" escapeXml="false"/></td>
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
             	<button type="button" class="btn btn-primary" onclick="openPaymentMethodPopup();">Add Item</button>
             </td>
         </tr>
     </table>

</body>
</html>