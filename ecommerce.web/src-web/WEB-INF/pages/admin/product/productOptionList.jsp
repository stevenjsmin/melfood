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
                    url: "/admin/productmgt/option/productOptions.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/productmgt/option/deleteProductOption.yum",
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
                        	optionSeq : options.optionSeq
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
		          { hidden : true, field: 'prodId'},
		          { title : 'Seq', field: 'optionSeq', width: 50, filterable: false},
		          { title : 'Option', field: 'optionItem', width: 250, attributes: {style: "color: 606000; font-weight: bolder;" }},
		          { title : 'Is Mandatory', template: kendo.template($("#useYn-template").html()), width: 150},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}, {text : "Modify", name: "modify", click: modifyItem} ], width: 150}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var prodId = dataItem.prodId;
        var optionSeq = dataItem.optionSeq;
        document.location.href = "/admin/productmgt/option/optionValue/Main.yum?prodId=" + prodId + "&optionSeq=" + optionSeq;
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
    
        
    function modifyItem(e) {
    	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
     	
        $("#productOptionPopup").kendoWindow({
                  content: "/admin/productmgt/option/modifyForm.yum?prodId=" + dataItem.prodId + "&optionSeq=" + dataItem.optionSeq,
                  actions: [ "Minimize", "Maximize","Close" ],
                  title: "Modify Product option item",
                  modal: true,
                  iframe: true
        });
            
        var subNMIList_dialog = $("#productOptionPopup").data("kendoWindow");
        subNMIList_dialog.setOptions({
                  width: 500,
                  height: 300
                });
        subNMIList_dialog.center();
            
        $("#productOptionPopup").data("kendoWindow").open();
	}
        
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
     function addOption() {
         $("#productOptionPopup").kendoWindow({
               content: "/admin/productmgt/option/registForm.yum?prodId=" + '${prodId}',
               actions: [ "Minimize", "Maximize","Close" ],
               title: "Add Product option item",
               modal: true,
               iframe: true
         });
         
         var subNMIList_dialog = $("#productOptionPopup").data("kendoWindow");
         subNMIList_dialog.setOptions({
               width: 500,
               height: 300
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
    #= (isMandatory == 'Y') ? '<font color="262626">필수선택</font>':'<font color="dfdfdf">필수선택아님</font>' #
</script>

</head>
<body>

    <div id="productOptionPopup"></div>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Table List -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div id="grid_panel_main" style="width: 700px;"></div>

    <br/>
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Extra buttons -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
     <table class="action_button_table">
         <tr>
             <td>
             	<button type="button" class="btn btn-primary" onclick="addOption();">Add Item</button>
             </td>
         </tr>
     </table>
     <input type="hidden" name="prodId"  id="prodId" value="${prodId}" />

     <script type="text/javascript">
          var _PRODID = "<c:out value="${prodId}"/>";
     </script>
     
</body>
</html>