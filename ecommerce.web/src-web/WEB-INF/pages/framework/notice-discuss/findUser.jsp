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
                    url: "/framework/noticedisscussmanager/findUser.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/framework/noticedisscussmanager/findUser.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                            userType : $("#userType").val(),
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
            pageSizes: [5, 10],
            messages: {
                itemsPerPage: "",
                display: "{0} - {1} / {2}"
            } 
        },         
		columns: [
		          { title : 'ID', field: 'userId', width: 100, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Name', field: 'userName', width: 200, attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : 'Role', field: 'roleName', width: 200},
		          { command: [ {text : "Select", name: "destory", click: selectUser}]}

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
    
    search();
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">
	function selectUser(e) {
		var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
		var userId = dataItem.userId;
		var userName = dataItem.userName;
		
		if(userId != null && userId !== '' && userName != null && userName !== '' ) {
			$(parent.document).find("#" + '${objectName}' + 'Label').val(userName) ;
			$(parent.document).find("#" + '${objectName}').val(userId) ;
			
			if( '${objectName}' == 'writeTo'){
				$(parent.document).find("#isForAllSeller").val("N") ;
				$(parent.document).find("#isForAllCustomer").val("N") ;
			}
		}
	}
	   
</script>

</head>

<body>

    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Search -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div class="well">
    	 <table class="search_table">
    	 	<tr>
    	 		<td class="label">Name :  </td>
    	 		<td class="value"><input class="form-control" id="userName" name="userName"></input></td>
    	 		<td class="label" style="color: ">User type : </td>
    	 		<td class="value_end"><c:out value="${cbxUserType}" escapeXml="false"/></td>
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
             	<button type="button" class="btn btn-primary" onclick="parent.closeFindUserWindow();">Close</button>
             </td>
         </tr>
     </table>
    <br/>
</body>
</html>