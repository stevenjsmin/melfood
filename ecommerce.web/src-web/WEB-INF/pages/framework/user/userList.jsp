<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/framework/usermanager.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
var KENDO_SELECTED_RECORD = null;
$(document).ready(function () {
    
    // DEFINE DATASOURCE
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
    var dataSource = new kendo.data.DataSource({
            pageSize: 10,
            serverPaging: true,
            serverFiltering: true,
            transport: {
                read: {
                    url: "/framework/usermanager/listUsers.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/framework/usermanager/enableAndDisable.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	useYn : $("#useYn").val(),
                        	applyStatus : $("#applyStatus").val(),
                            userType : $("#userType").val(),
                            sex : $("#sex").val(),
                            userName : $("#userName").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	userId : options.userId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "userId",
                    fields: {
                    	userId : { type: "string"}
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
		          { title : 'ID', field: 'userId', width: 100, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Name', field: 'userName', width: 200, attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : 'Role', field: 'roleName', width: 200},
		          { title : 'Is Active', template: kendo.template($("#useYn-template").html()), width: 100, attributes: {style: "text-align: center;" }},
		          { title : 'Address', field: 'address', width: 250, attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : 'Status', template: kendo.template($("#applyStatus-template").html()), width: 200},
		          { command: [ {text : "Enable/Disable", name: "destory", click: enableAndDisable}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var userId = dataItem.userId;
        goDetailInfo(userId);
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
    
    search();
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
	function enableAndDisable(e) {
		var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

	    $.ajax({
	         url  : "/framework/usermanager/enableAndDisable.yum",
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

<script id="useYn-template" type="text/x-kendo-template">
    #= (useYn == 'Y') ? '<font color="262626">Active</font>':'<font color="dfdfdf">Deactive</font>' #
</script>
<script id="applyStatus-template" type="text/x-kendo-template">
    # if (applyStatus === 'APPLY') { #
          <span style="color: 0080c0;"> 신청중 </span>
    # } else if (applyStatus === 'UNDER AUTHORIZATION') { # 
          <span style="color: 0fb1ff;"> 가입심사중 </span>
    # } else if (applyStatus === 'COMPLETE') { # 
          <span> 가입완료 </span>
    # } else if (applyStatus === 'REJECTED') { # 
          <span style="color: a60435;"> 가입거부 </span>
    # } else { #
          <span> n/a </span>
    # }#
</script>

</head>
<body>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Use Y/N :  </td>
    	 		<td class="value"><c:out value="${cbxUseYn}" escapeXml="false"/></td>
    	 		<td class="label" style="color: ">Apply status : </td>
    	 		<td class="value"><c:out value="${cbxApplyStatus}" escapeXml="false"/></td>
    	 		<td class="label" style="color: ">User type : </td>
    	 		<td class="value"><c:out value="${cbxUserType}" escapeXml="false"/></td>
    	 		<td class="label" style="color: ">Sex : </td>
    	 		<td class="value"><c:out value="${cbxSex}" escapeXml="false"/></td>
    	 		<td class="label">Name :  </td>
    	 		<td class="value_end"><input class="form-control" id="userName" name="userName"></input></td>
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
    <br/>
    <br/>
    <br/>

</body>
</html>