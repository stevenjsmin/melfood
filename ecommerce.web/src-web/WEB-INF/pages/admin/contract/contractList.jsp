<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/contractmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                    url: "/admin/contractmgt/contractInfos.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/contractmgt/deleteContractInfo.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	userId : $("#userId").val(),
                            contractStatus : $("#contractStatus").val(),
                            contractStartDate : $("#contractStartDate").val(),
                            contractEndDate : $("#contractEndDate").val()
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
                    id: "userId",
                    fields: {
                    	userId : { type: "string"},
                        contractSeq : { type: "string"}
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
		          { title : 'Seller', field: 'userName', width: 100, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Seq', field: 'contractSeq', width: 80},
		          { title : 'Contract Status', field: 'contractStatusLabel', width: 200, attributes: {style: "color: e37200;" }},
		          { title : 'Start Date', field: 'contractStartDate', width: 150,attributes: {style: "color: 00994d;font-weight: bolder;" }},
		          { title : 'End Date', field: 'contractEndDate', width: 150,attributes: {style: "color: 804000;font-weight: bolder;" }},
		          { title : 'Create Date', field: 'createDatetime'},
		          { hidden : true, field: 'userId'}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
    	
        var userId = dataItem.userId;
        var contractSeq = dataItem.contractSeq;
        
        goDetailInfo(userId, contractSeq);
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }
    
    search();
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script id="useYn-template" type="text/x-kendo-template">
    #= (useYn == 'Y') ? '<font color="262626">Active</font>':'<font color="dfdfdf">Deactive</font>' #
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
    	 		<td class="label">Contract Status : </td>
    	 		<td class="value"><div id="typeCbx"><c:out value="${cbxContractStatus}" escapeXml="false"/></div></td>
    	 		<td class="label">Start Date :  </td>
    	 		<td class="value"><input id="contractStartDate" name="contractStartDate" value="${contractStartDate}"></input></td>
    	 		<td class="label">End Date :  </td>
    	 		<td class="value_end"><input id="contractEndDate" name="contractEndDate" value=""></input></td>
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