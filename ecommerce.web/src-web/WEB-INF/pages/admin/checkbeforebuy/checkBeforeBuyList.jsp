<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/checkbeforebuymanager.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                    url: "/admin/checkbeforebuy/getCheckBeforeBuyList.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/checkbeforebuy/deleteCheckBeforeBuy.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                            sellerId : $("#sellerId").val(),
                        	subject : $("#subject").val(),
                        	contents : $("#contents").val(),
                        	isDefault : $("#isDefault").val(),
                        	confirmStatus : $("#confirmStatus").val()
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
                display: "{0} - {1} / {2}",
                empty: "<span>조회된 구매전확인 사항내용이 없습니다.</span>",
            } 
        },         
		columns: [
				  { hidden : true, field: 'id'},
		          { title : 'Subject', field: 'subject'},
		          { title : 'Seller', field: 'sellerName', attributes: {style: "color: e37200;" },width: 150, filterable: false},
		          { title : 'Is Default', width: 100, template: kendo.template($("#isDefault-template").html()), width: 150, filterable: false},
		          { title : 'Confirm Status', template: kendo.template($("#confirmStatus-template").html()), width: 130},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}], width: 120}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var id = dataItem.id;
        goDetailInfo(id);
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

<script id="isDefault-template" type="text/x-kendo-template">
    # if (isDefault == 'Y') { #
		  #= '<span style="color: 323132;">기본선택</span>' #
    # } else { #
    # } #
</script>
<script id="confirmStatus-template" type="text/x-kendo-template">
    # if (confirmStatus == 'ON_DRAFT') { #
		  #= '<span style="color: 323132;">작성중</span>' #
    # } else { #
		  #= '<span style="color: A8A8A8;">작성완료</span>' #
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
    	 		<td class="label" style="width: 120px;">상품판매자 : </td>
    	 		<td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
    	 		<td class="label" style="width: 120px;">기본선택 여부 : </td>
    	 		<td class="value"><c:out value="${cbxIsDefault}" escapeXml="false"/></td>
    	 		<td class="label" style="width: 120px;">작성완료 여부 :  </td>
    	 		<td class="value_end"><c:out value="${cbxConfirmStatus}" escapeXml="false"/></td>
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