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
                    url: "/cart/getDeliveryCalendars.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/cart/deleteDeliveryCalendar.yum",
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
            refresh: false,
            pageSizes: true,
            previousNext: true,
            numeric: false,
            buttonCount: 5,
            page: 1,
            pageSizes: [5,10],
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
				  { hidden : true, field: 'isPickup'},
				  { hidden : true, field: 'amPm'},
				  { hidden : true, field: 'addressPostcode'},
				  { hidden : true, field: 'addressState'},
				  { hidden : true, field: 'addressSuburb'},
				  { hidden : true, field: 'addressStreet'},
		          { title : '일자', template: kendo.template($("#yyyyMmDd-template").html()), width: 100, sort: true},
		          { title : '오전/오후', template: kendo.template($("#amPm-template").html()), width: 150},
		          { title : '픽업/배송', template: kendo.template($("#deliverMethod-template").html()), width: 150},
		          { title : '지역/배송가능지역', template: kendo.template($("#suburb-address-template").html()), width: 200},
		          { title : '픽업오시는 주소', template: kendo.template($("#street-address-template").html()), width: 200},
		          { command: [ {text : "선택", name: "select", click: selectItem}]}
		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var prodId = dataItem.prodId;
        // Nothing to do
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }
    
    function selectItem(e) {
     	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
     	
     	var htmlStr = "";
     	htmlStr = htmlStr + '<span style="color: C90000;">' + dataItem.yyyyMmDd + '</span>일 ';
     	if(dataItem.amPm == 'AM') {
     		htmlStr = htmlStr + '<span style="color: C90000;"> 오전</span>에 ';
     	} else if(dataItem.amPm == 'PM') {
     		htmlStr = htmlStr + '<span style="color: C90000;"> 오후</span>에 ';
     	} else {
     		htmlStr = htmlStr + ' <span style="color: C90000;">아침7시 ~ 저녁 6시</span> 사이에 ';     		
     	}
     	
     	if (dataItem.isPickup == 'Y') {
     		htmlStr = htmlStr + '<br>';     		
     		htmlStr = htmlStr + '고객님께서 <span style="color: E57014;">"' + dataItem.addressStreet + ' ' + dataItem.addressSuburb + ' ' + dataItem.addressState + ' ' + dataItem.addressPostcode + '"</span>';     		
     		htmlStr = htmlStr + ' 에 <b>방문</b>하셔서 <b>상품 픽업</b>.';     		
     	} else {
     		htmlStr = htmlStr + '<span style="color: E57014;">고객님께 배달.</span> ';     		     		
     	}
     	
     	$("#productReceiveAndPickupDate",top.document).html( htmlStr );
     	
     	$("#addressStreet",top.document).val(dataItem.addressStreet);
     	$("#addressSuburb",top.document).val(dataItem.addressSuburb);
     	$("#addressState",top.document).val(dataItem.addressState);
     	$("#addressPostcode",top.document).val(dataItem.addressPostcode);
     	$("#yyyyMmDd",top.document).val(dataItem.yyyyMmDd);
     	$("#amPm",top.document).val(dataItem.amPm);
     	$("#isPickup",top.document).val(dataItem.isPickup);
     	
     	if('${seller.sellerHaveMinimumPayment}' == 'Y') {
     		if(dataItem.isPickup == 'Y') {
     			// 고객이 제품을 찾으러 오는 경우.
     			var htmlMsg = "";
     			htmlMsg = '현재 판매자의 최소 주문가능 주문액은 <b>고객님</b>께서 <span style="font-weight: bold;color: #318DD3;">픽업</span>오시는 경우 <span style="font-weight: bold;color: #BD0000;">$' + '<fmt:formatNumber type="number" pattern="###.00" value="${seller.sellerMinimumPaymentForPickup}" />' + '</span> 입니다.';
     			$("#minimumPaymentMsgArea",top.document).html( htmlMsg );
     			
     		} else {
     			var htmlMsg = "";
     			htmlMsg = '현재 판매자의 최소 주문가능 주문액은 <b>고객님</b>께 <span style="font-weight: bold;color: #318DD3;">배달</span>하는 경우 <span style="font-weight: bold;color: #BD0000;">$' +  '<fmt:formatNumber type="number" pattern="###.00" value="${seller.sellerMinimumPaymentForDeliver}" />' + '</span> 입니다.';
     			$("#minimumPaymentMsgArea",top.document).html( htmlMsg );
     		}
     	}
     	
     	parent.closeChooseDeliveryDatePopup();
    }    
    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script id="amPm-template" type="text/x-kendo-template">
    # if (amPm == 'AM') { #
          <span style="color: 72BA22;">오전 </span>
    # } else if (amPm == 'PM') { # 
          <span style="color: 7768E5;">오후 </span>
    # } else { #
          <span style="color: 0080c0;">아침7시 ~ 저녁 6시</span>
    # } #
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
<script id="suburb-address-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
    	  #= '<font color="dfdfdf">' + addressPostcode + ' ' + addressState + ' / </font>' + '<font color="E57014"> ' + addressSuburb + '</font>' #
    # } else { #
    	  #= '<font color="dfdfdf">' + addressPostcode + ' ' + addressState + ' / </font>' + '<font color="262626"> ' + addressSuburb + '</font>' #
    # } #

</script>
<script id="street-address-template" type="text/x-kendo-template">
    # if (isPickup == 'Y') { #
		  #= '<font color="E57014"> ' + addressStreet + '</font>' #
    # } else { #
    # } #

</script>

</head>
<body>


    <div class="row">
        <div class="col-md-11">
		    <span class="subtitle"> <span style="color: #66A71F;">${seller.sellerBusinessName} (${seller.userName} )</span> 상품판매자의 배송일정 및 상품 픽업가능일짜</span>
		    <hr class="subtitle"/>
		    <c:choose>
		    	<c:when test="${totalCountForDelivery < 1 and totalCountForPickup > 0}">
					<div class="alert alert-danger">
					  <span style="color: #BD0000;"><strong>주의!</strong> 현재 고객님의 지역에 배송일정이 잡혀있지 않습니다. 대신 상품판매자의 매장(또는 지정된 장소)에 방문하여 상품을 찾아가실 수 있습니다.</span>
					</div>		 
					<div class="alert alert-info">
					  <span style="color: #0E0E0E;">현재 고객님의 지역에 배송일정이 잡혀있지 않더라도 배송을 원하시는 경우,<br/> "상품수령 또는 픽업일자"를 지정하시마시고 그냥 주문하시고 상품판매자에게 연락주시기를 바랍니다.</span>
					</div>		 
		    	</c:when>
		    </c:choose>
		    
		    <div id="grid_panel_main"></div>
		</div>
	</div>
	
    <br/>
    <div class="row">
        <div class="col-md-11">
		    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
		    <!-- Extra buttons -->
		    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
		     <table class="action_button_table">
		         <tr>
		             <td>
		             	<button type="button" class="btn btn-primary" onclick="parent.closeChooseDeliveryDatePopup();">닫기</button>
		             </td>
		         </tr>
		     </table>		          
        </div>
    </div>        

</body>
</html>