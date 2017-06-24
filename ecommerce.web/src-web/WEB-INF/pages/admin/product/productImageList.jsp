<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="/resources/owlcarousel/owl.carousel.min.css" rel="stylesheet" media="screen">
<link href="/resources/owlcarousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
<script src="/resources/js/melfood/admin/productmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<script src="/resources/owlcarousel/owl.carousel.js"></script>

<script type="text/javascript">
var KENDO_SELECTED_RECORD = null;
$(document).ready(function () {

    $("#fileUpload").kendoUpload({
        async: {
            saveUrl: "/admin/productmgt/image/uploadFile.yum?prodId=" + ${product.prodId},
            removeUrl: "/admin/productmgt/image/removeFile.yum",
            removeField: "removeFile",
            autoUpload: true,
            batch: true,
        },
        showFileList: false,
        localization: {
            statusFailed: "Failed to file upload. Please check it again and retry.",
            uploadSelectedFiles: "Upload file(s)"
        },
        complete: onComplete,
        error: onError
    });
     
    function onComplete(e) {
    	search();
    } 
    function onError(e) {
        var files = e.files;
        if (e.operation == "upload") {
            warningPopup("Failed to upload : " + files[0].name);
        }
    }      	
    
    // DEFINE DATASOURCE
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
    var dataSource = new kendo.data.DataSource({
            pageSize: 20,
            serverPaging: true,
            serverFiltering: true,
            transport: {
                read: {
                    url: "/admin/productmgt/image/productImages.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/productmgt/image/deleteProductImage.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	prodId : $("#prodId").val()
                        };
                    } else if (operation == "destroy") {
                        return {
                        	prodId : options.prodId,
                        	imageSeq : options.imageSeq
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "prodId",
                    fields: {
                    	prodId : { type: "string"},
                    	imageSeq : { type: "string"}
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
		          { hidden : true, field: 'imageSeq'},
		          { title : 'File Id', field: 'imageFileId', width: 30, filterable: false, attributes: {style: "color: D6D6D6;text-align: center;" }},
		          { title : 'Order', field: 'displayOrder', width: 30, filterable: false, attributes: {style: "color: 1A717B;text-align: center;" }},
		          { title : 'W * H', width: 50, template: kendo.template($("#size-template").html()), filterable: false, attributes: {style: "text-align: center;" }},
		          { title : 'Desciption', field: 'imageDescription', width: 250},
		          { command: [ {text : "Delete", name: "destory", click: deleteItem} ], width: 100}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var prodId = dataItem.prodId;
        var imageSeq = dataItem.imageSeq;
        
        $("#productImagePopup").kendoWindow({
            content: "/admin/productmgt/image/modifyProductImageForm.yum?prodId=" + prodId + "&imageSeq=" + imageSeq,
            actions: [ "Minimize", "Maximize","Close" ],
            title: "Modify Product image",
            modal: true,
            iframe: true
  		});
      
		var popup_dialog = $("#productImagePopup").data("kendoWindow");
		popup_dialog.setOptions({
		            width: 800,
		            height: 750
		          });
		popup_dialog.center();
		      
		$("#productImagePopup").data("kendoWindow").open();      
		        
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
	function closeImageWindow() {
		var win_dialog = $("#productImagePopup").data("kendoWindow");
		win_dialog.close();
	} 
</script>
<script type="text/javascript">
	function openImageViwer() {
        
        $("#productImagePopup").kendoWindow({
            content: "/admin/productmgt/image/productImageViewer.yum?prodId=" + "${product.prodId}",
            actions: [ "Minimize", "Maximize","Close" ],
            title: "Product images",
            modal: true,
            iframe: true
  		});
      
		var popup_dialog = $("#productImagePopup").data("kendoWindow");
		popup_dialog.setOptions({
		            width: 900,
		            height: 430
		          });
		popup_dialog.center();
		      
		$("#productImagePopup").data("kendoWindow").open();   		
	} 
</script>

<script id="size-template" type="text/x-kendo-template">
    #= ( (width != null && height != null) ? (width + ' X ' + height) : '-')  #
</script>


</head>
<body> 
	<div id="productImagePopup"></div>
    <span class="subtitle">Attachment Images</span>
    <hr class="subtitle"/>
    <table style="width: 850px;">
          <tr>
               <td>
                    <table class="detail_table">
                         <tr>
                              <td class="label" style="width: 250px;">File Upload</td>
                              <td class="value" style="width: 500px;"><input type="file" name="files" id="fileUpload"/></td>
                         </tr>
                    </table>
               </td>
          </tr>
    </table>
    
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Extra buttons -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <table class="action_button_table">
         <tr>
             <td>
             	<button type="button" class="btn btn-primary" onclick="openImageViwer();">Open image viwer </button>
             </td>
         </tr>
    </table>
    <br/>
    
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Table List -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div id="grid_panel_main"></div>

    <br/>

    <input type="hidden" name="prodId"  id="prodId" value="${product.prodId}" />

    <script type="text/javascript">
          var _PRODID = "<c:out value="${product.prodId}"/>";
          
          $('.owl-carousel').owlCarousel({
        	    loop:false,
        	    margin:10,
        	    nav:true,
        	    responsive:{
        	        0:{
        	            items:1
        	        },
        	        600:{
        	            items:3
        	        },
        	        1000:{
        	            items:5
        	        }
        	    }
        	})          
          $(".owl-carousel").owlCarousel();
     </script>
     
</body>
</html>