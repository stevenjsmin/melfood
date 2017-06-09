<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="melfood.framework.Ctx" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/deliverycalendarmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                    url: "/shop/order/getDeliveryCalendars.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/admin/deliverycalendarmgt/deleteDeliveryCalendar.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                            sellerId : '${sellerId}',
                            addressSuburb : $("#addressSuburb").val(),
                            addressPostcode : $("#addressPostcode").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	sellerId : options.sellerId,
                        	yyyyMmDd : options.yyyyMmDd,
                        	deliverySeq : options.deliverySeq
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "sellerId",
                    fields: {
                    	yyyyMmDd : { type: "string"},
                    	amPm : { type: "string"},
                    	addressSuburb : { type: "string"},
                    	addressPostcode : { type: "string"}
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
                empty: "<span>조회된 배송일정이 없습니다.</span>",
            } 
        },         
		columns: [
				  { hidden : true, field: 'yyyyMmDd'},
				  { hidden : true, field: 'sellerId'},
				  { hidden : true, field: 'deliverySeq'},
				  { hidden : true, field: 'addressPostcode'},
				  { hidden : true, field: 'addressState'},
				  { hidden : true, field: 'addressSuburb'},
				  { hidden : true, field: 'addressStreet'},
		          { title : '픽업/배송 일짜', template: kendo.template($("#yyyyMmDd-template").html()), width: 120},
		          { title : '시간', template: kendo.template($("#time-template").html()), width: 150},
		          { title : '픽업/배송', template: kendo.template($("#deliverMethod-template").html()), width: 150},
		          { title : '우편번호',  template: kendo.template($("#postcode-template").html()), width: 100},
		          { title : '지역', template: kendo.template($("#suburb-address-template").html()), width: 150},
		          { title : '픽업주소(고객님이 픽업오시는 경우)', template: kendo.template($("#street-address-template").html())},
		          { title : '지도', template: kendo.template($("#address-map-template").html()),attributes: {style: "text-align: center;" }, width: 80}
		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
    	
		var yyyyMmDd = dataItem.yyyyMmDd;
		var sellerId = dataItem.sellerId;
		var deliverySeq = dataItem.deliverySeq;
		
        displayGMapPopup(yyyyMmDd, sellerId, deliverySeq)
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }
    
    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>


<script type="text/javascript">
	function displayGMapPopup(yyyyMmDd, sellerId, deliverySeq){
			
	    $("#displayGMapPopup").kendoWindow({
	        content: "/shop/sellerDeliveryAddressMap.yum?yyyyMmDd=" + yyyyMmDd + "&sellerId=" + sellerId + "&deliverySeq=" + deliverySeq,
	        actions: [ "Minimize", "Maximize","Close" ],
	        title: "배송/픽업 주소맵",
	        modal: true,
	        iframe: true
		});
	  
		var popup_dialog = $("#displayGMapPopup").data("kendoWindow");
		// popup_dialog.setOptions({width: "700",height: "700",position: { top: "100", left: "50" },});
		popup_dialog.setOptions({width: "850",height: "650"});
		popup_dialog.center();
		      
		$("#displayGMapPopup").data("kendoWindow").open(); 
	}
	function closeDisplayGMapPopup() {
		var win_dialog = $("#displayGMapPopup").data("kendoWindow");
		win_dialog.close();
	} 
</script>


<script id="time-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          <span style="color: E57014;">
    # } else if (isPickup == 'N') { # 
          <span style="color: 7768E5;">
    # } else { #
          <span>
    # } #

    # if (btwnFromHhmm != '' && btwnFromHhmm != null) { #
          #= btwnFromHhmm #
    # } else { #
    # } #
    ~
    # if (btwnToHhmm != '' && btwnToHhmm != null) { #
          #= btwnToHhmm #
    # } else { #
    # } #
    </span> 
</script>
<script id="deliverMethod-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          <span style="color: E57014;">고객님께서 픽업 </span>
    # } else { #
          <span style="color: 7768E5;">고객님께 배송 </span>
    # } #
</script>
<script id="yyyyMmDd-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          #= '<span style="color: E57014;font-weight: bolder;">' + yyyyMmDd + '</span>' #
    # } else { #
          #= '<span style="color: 606000;font-weight: bolder;">' + yyyyMmDd + '</span>' #
    # } #
</script>
<script id="postcode-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
          #= '<span style="color: E57014;">' + addressPostcode + '</span>' #
    # } else { # 
           #= addressPostcode  #
    # } #
</script>
<script id="suburb-address-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
    	  #= '<font color="dfdfdf">' + addressState + ' / </font>' + '<font color="E57014"> ' + addressSuburb + '</font>' #
    # } else { #
    	  #= '<font color="dfdfdf">' + addressState + ' / </font>' + '<font color="262626"> ' + addressSuburb + '</font>' #
    # } #

</script>
<script id="street-address-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
		  #= '<font color="E57014"> ' + addressStreet + '</font>' #
    # } else { #
    # } #
</script>
<script id="address-map-template" type="text/x-kendo-template">
    #= '<a href=javascript:displayGMapPopup("' + yyyyMmDd + '","' + sellerId + '","' + deliverySeq +'")><img src="/resources/image/gmap_icon.png" style="height: 17px;"></a>' #
</script>

</head>
<body>

	<div id="displayGMapPopup"></div>

    <div class="row">
        <div class="col-md-12">
		    <span class="subtitle"> 상품판매자 배송일정 및 상품 픽업가능일짜</span>
		    <hr class="subtitle"/>
				 <c:choose>
						<c:when test="${not empty seller}">
						    <span style="font-size: 12px;color: #A8A8A8;">
						    	** 고객님의 지역(Suburb)에 배송일정이 없으신경우 [주문량]과 [배송예정지역과 입접한 경우]에 배송서비스가 가능할 수 있으니 전화 주세요.<br/> 
						    	** [고객님께서 픽업] 하시는 경우 판매자가 지정한 [픽업주소]로 오셔서 픽업 하시면 됩니다.</span><br/> 
				    	 	<table class="search_table">
				    	 		<tr>
				    	 			<td class="label">우편번호 :  </td>
				    	 			<td class="value_end"><input class="form-control" id="addressPostcode" name="addressPostcode" maxlength="4" value="${defaultCustomerPostcode}"></input></td>
				    	 			<td class="label">Suburb :  </td>
				    	 			<td class="value_end"><input class="form-control" id="addressSuburb" name="addressSuburb" value="${defaultCustomerSuburb}"></td>    	 		
				                	<td class="find"><button type="button" class="btn btn-primary" onclick="search();">검색</button></td>
				    	 		</tr>
				    	 	</table>
						    
						    <table class="detail_table_c">
						         <colgroup>
						              <col width="200px" />
						              <col width="250px" />
						              <col width="200px" />
						              <col width="300px" />
						         </colgroup>
						         <tr>
						         	<td colspan="4">
									    <div id="grid_panel_main"></div>
						         	</td>
						         </tr>
						         <tr>
        							<td  colspan="4" style="text-align: right;padding-right: 10px;font-size: 10px;">지도 아이콘을 클릭하거나, 라인을 더블클릭하면 상세지도를 확인하실 수 있습니다.</td>
        						 </tr>
						    </table>
						</c:when>
						<c:otherwise>
							<span style="font-size: small;font-weight: bold;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;"><a href="javascript:openLoginPopup();">로그인</a> 하셔야 확인하실 수 있는 정보입니다.</span>
						</c:otherwise>
				 </c:choose>
				 
				 <c:if test="${isPopup == 'true' }">
					 <table class="action_button_table" width="100%" style="margin-top: 10px;" >
	        			<tr>
	            		 	<td style="padding-right: 10px;">
	                  			<a href="javascript:parent.closedeliverySchedulePopup();" class="btn btn-info">&nbsp;&nbsp; 닫기 &nbsp;&nbsp;</a>
	             			</td>
	        			</tr>
	    			</table>				    
				 </c:if>
		</div>
	</div>	

</body>
</html>