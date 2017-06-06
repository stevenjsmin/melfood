<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
     .k-grid-header {
          display: none;
     }
     .k-grid {
          border: 0px;
     }
     .k-grid-pager {
          border: 0px;
     }
</style>

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
                    url: "/admin/contractmgt/getContractFiles.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/contractmgt/deleteContractFiles.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	userId : $("#sellerId").val(),
                            contractSeq : $("#contractSeq").val(),
                            fileId : $("#fileId").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	userId : options.userId,
                            contractSeq : options.contractSeq,
                            fileId : options.fileId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "userId",
                    fields: {
                    	userId : { type: "string"},
                        fileId : { type: "string"},
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
		          { title : 'FileID', field: 'fileId', width: 60},
		          { title : 'File Name', field: 'fileName', attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Saved File Name', field: 'savedFileName', attributes: {style: "color: e5e5e5;" }},
		          { title : 'Create time', field: 'createDatetime', width: 100},
		          { hidden : true, field: 'userId'},
		          { hidden : true, field: 'contractSeq'},
		          { hidden : true, field: 'fileId'},
		          { title : 'Down', template: kendo.template($("#download-template").html()), width: 50, attributes: {style: "text-align: center;" }},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
    	
        var userId = dataItem.userId;
        var contractSeq = dataItem.contractSeq;
        var fileId = dataItem.fileId;
        downloadFile(fileId);
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

<script id="download-template" type="text/x-kendo-template">
    #= '<a href="javascript:downloadFile(' + fileId + ');"><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"/></a>' #
</script>
</head>

<body>
<div id="errorWindow"></div>
     <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="250px" />
                              <col width="250px" />
                              <col width="20px" />
                              <col width="250px" />
                         </colgroup>     
                         <tr>
                              <td class="label">Seller</td>
                              <td class="value"><span style="font-weight: bold;">${seller.userName}</span> / ${seller.userId} </td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Contract Status</td>
                              <td class="value" style="color: #e37200;font-weight: bolder;">${seller.contractStatusLabel}</td>
                              <td></td>
                              <td></td>
                         </tr>
                         <tr>
                              <td class="label">Contract period</td>
                              <td class="value" colspan="3">
                                   <table>
                                        <tr>
                                             <td style="color: #00994d;font-weight: bolder;">${seller.contractStartDate}</span></td>
                                             <td>~</td>
                                             <td style="color: #804000;font-weight: bolder;"><span>${seller.contractEndDate}</span></td>
                                        </tr>
                                   </table> 
                              </td>
                         </tr>
                         <tr>
                              <td class="label">Description</td>
                              <td class="value" colspan="2">${seller.contractDescription}</td>
                              <td></td>
                         </tr>
                         <tr><td class="metavalue" colspan="4">Creator : ${seller.creator}, Create Time : ${seller.createDatetime}, Modify Time : ${seller.modifyDatetime}</td></tr>
                    </table>
               </td>
          </tr>
          <tr><td>&nbsp;</td></tr>          
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:deleteInfo('${seller.userId}', '${seller.contractSeq}');" class="btn btn-danger">Delete</a>
                                   &nbsp;&nbsp;&nbsp;
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:goModify('${seller.userId}', '${seller.contractSeq}');" class="btn btn-primary">Modify</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>          
          <tr><td colspan="4">&nbsp;</td></tr>
     </table>
     
     
     <span class="subtitle">Attachment Files</span>
     <hr class="subtitle"/>
     <table style="width: 950px;">
          <tr>
               <td>
                    <table class="detail_table">
                         <tr>
                              <td class="label" style="width: 250px;">File Upload</td>
                              <td class="value" style="width: 500px;"><input type="file" name="files" id="fileUpload"/></td>
                         </tr>
                         <tr>
                              <td class="value" colspan="2" style="padding-left: 0px;"><div id="grid_panel_main"></div></td>
                         </tr>
                    </table>
               </td>
          </tr>
     </table>
     <br/>
     <br/>
     
     <input type="hidden" id="sellerId" name="sellerId" value="${seller.userId}" />
     <input type="hidden" id="contractSeq" name="contractSeq" value="${seller.contractSeq}" />
     
     
</body>
</html>