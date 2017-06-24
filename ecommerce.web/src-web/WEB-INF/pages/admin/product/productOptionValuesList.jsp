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
                    url: "/admin/productmgt/option/optionValue/productOptionValues.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/productmgt/option/optionValue/deleteProductOptionValue.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	prodId : $("#prodId").val(),
                        	optionSeq : $("#optionSeq").val()
                        };
                    } else if (operation == "destroy") {
                        return {
                        	prodId : options.prodId,
                        	optionSeq : options.optionSeq,
                        	valueSeq : options.valueSeq
                        };
                    } else if (operation == "modify") {
                        return {
                        	prodId : options.prodId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "prodId",
                    fields: {
                    	prodId : { type: "string"},
                    	optionSeq : { type: "string"},
                    	valueSeq : { type: "string"}
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
		          { hidden : true, field: 'prodId'},
		          { hidden : true, field: 'optionSeq'},
		          { title : 'Seq', field: 'valueSeq', width: 50, filterable: false},
		          { title : 'Option Value', field: 'valueLabel', width: 250, filterable: false, attributes: {style: "color: FF9324; font-weight: bolder;" }},
		          { title : 'In stock', field: 'inStockCnt', width: 80, filterable: false, attributes: {style: "text-align: right;" }},
		          { title : 'Unit Price', field: 'unitPrice', width: 120, filterable: false, attributes: {style: "text-align: right;" }, format: "{0:c}"},
		          { title : 'Extra Charge', field: 'extraCharge', width: 120, filterable: false, attributes: {style: "text-align: right;" }, format: "{0:c}"},
		          { title : 'Is Active', template: kendo.template($("#useYn-template").html()), width: 100, attributes: {style: "text-align: center;" }},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	// var dataItem = KENDO_SELECTED_RECORD;
    	// Nothing to do.
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
     function addOptionValue() {
         $("#productOptionPopup").kendoWindow({
               content: "/admin/productmgt/option/optionValue/registForm.yum?prodId=" + '${productOption.prodId}' + "&optionSeq=" + '${productOption.optionSeq}' ,
               actions: [ "Minimize", "Maximize","Close" ],
               title: "Add Product option value",
               modal: true,
               iframe: true
         });
         
         var subNMIList_dialog = $("#productOptionPopup").data("kendoWindow");
         subNMIList_dialog.setOptions({
               width: 900,
               height: 400
             });
         subNMIList_dialog.center();
         
         $("#productOptionPopup").data("kendoWindow").open();
     }
	function closeOptionWindow() {
		var win_dialog = $("#productOptionPopup").data("kendoWindow");
		win_dialog.close();
	}     
</script>

<script id="useYn-template" type="text/x-kendo-template">
    #= (useYn == 'Y') ? '<font color="262626">In Use</font>':'<font color="dfdfdf">Unuse</font>' #
</script>

</head>
<body>

    <div id="productOptionPopup"></div>
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Product Name :</td>
    	 		<td class="value_end">${product.name}</td>
    	 	</tr>
    	 	<tr>
    	 		<td class="label">Option Item : </td>
    	 		<td class="value_end">${productOption.optionItem}</td>
    	 	</tr>
    	 	<tr>
    	 		<td class="label">Is Mandatory : </td>
    	 		<td class="value_end">
    	 			<c:choose>
    	 				<c:when test="${productOption.isMandatory == 'Y'}">필수선택</c:when>
    	 				<c:otherwise>필수선택아님</c:otherwise>
    	 			</c:choose>
    	 		</td>
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
             	<button type="button" class="btn btn-primary" onclick="addOptionValue();">Add Item</button>
             </td>
         </tr>
     </table>
     <input type="hidden" name="prodId"  id="prodId" value="${productOption.prodId}" />
     <input type="hidden" name="optionSeq"  id="optionSeq" value="${productOption.optionSeq}" />

     <script type="text/javascript">
          var _PRODID = "<c:out value="${productOption.prodId}"/>";
          var _OPTIONSEQ = "<c:out value="${productOption.optionSeq}"/>";
     </script>
     
</body>
</html>