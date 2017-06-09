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
<script src="/resources/js/melfood/framework/postcodemanager.js?ver=<%=Ctx.releaseVersion%>"></script>

<script type="text/javascript">
var KENDO_SELECTED_RECORD = null;
$(document).ready(function () {
	$("#yyyyMmDd").kendoDatePicker({
     	format: "yyyy-MM-dd",
     	start: "year"
	});
	var datepicker1 = $("#yyyyMmDd").data("kendoDatePicker");
	$("#yyyyMmDd").click(function() {
	     datepicker1.open();
	})
	
	
	//	Time Reage setting : Start
	function startChange() {
	    var startTime = btwnFromHhmm.value();
	
	    if (startTime) {
	        startTime = new Date(startTime);
	
	        btwnToHhmm.max(startTime);
	
	        startTime.setMinutes(startTime.getMinutes() + this.options.interval);
	
	        btwnToHhmm.min(startTime);
	        btwnToHhmm.value(startTime);
	    }
	} 
	
    //init start timepicker
    var btwnFromHhmm = $("#btwnFromHhmm").kendoTimePicker({
        change: startChange,
        format: "HH:mm tt"
    }).data("kendoTimePicker");

    //init end timepicker
    var btwnToHhmm = $("#btwnToHhmm").kendoTimePicker({
        format: "HH:mm tt"
    }).data("kendoTimePicker");    

    //define min/max range
    btwnFromHhmm.min("6:00 AM");
    btwnFromHhmm.max("6:00 AM");

    //define min/max range
    btwnToHhmm.min("8:00 PM");
    btwnToHhmm.max("8:00 PM");
	//	Time Reage setting : End
	
	
	
    // DEFINE DATASOURCE
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
    var dataSource = new kendo.data.DataSource({
            pageSize: 10,
            serverPaging: true,
            serverFiltering: true,
            transport: {
                read: {
                    url: "/framework/postcodemanager/getPostcodes.yum",
                    dataType: "json",
                    type: "POST"
                },
                destroy: {
                    url: "/framework/postcodemanager/postcodeDelete.yum",
                    dataType: "jsonp",
                    type: "POST"
                }, 
                parameterMap: function(options, operation) {
                    if (operation == "read") {
                        return {
                            page : options.page,
                            pageSize : options.pageSize,                          	
                        	state : $("#addressState").val(),
                            suburb : $("#suburb").val(),
                            postcode : $("#postcode").val()
                        };
                    } else if (operation == "destroy") {
                        console.log(options);
                        return {
                        	postcodeId : options.postcodeId
                        };
                    }
                }
            }, 
            schema: {
                model: {
                    id: "postcodeId",
                    fields: {
                    	postcodeId : { type: "string" }
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
		          { hidden: true, field: "state" },
		          { hidden: true, field: "postcodeId" },
		          { hidden: true, field: "state" },
		          { title : 'State', field: 'stateLabel', width: 150, attributes: {style: "color: 606000; font-weight: bolder;" }, filterable: false},
		          { title : 'Postcode', field: 'postcode', width: 100, attributes: {style: "color: 939300; font-weight: bolder;" }},
		          { title : 'Suburb', field: 'suburb', width: 200, attributes: {style: "color: e37200;font-weight: bolder;" }},
		          { command: [ {text : "Add", name: "destory", click: addDeliveryCalendarFromRow}]}

		 ] // End of Columns
    }); // End of GRID
    
    $("#grid_panel_main").dblclick(function(e) {
    	var dataItem = KENDO_SELECTED_RECORD;
        var postcodeId = dataItem.postcodeId;
        // goDetailInfo(postcodeId);
    });
    
    function onChange(e) {
         var gridRecord = e.sender;
         KENDO_SELECTED_RECORD = gridRecord.dataItem(gridRecord.select());
    }
    
    search();
    
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script type="text/javascript">

</script>

<script type="text/javascript">
	function addDeliveryCalendarFromRow(e) {
	 	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
	 	
	 	if(validateForm() == false) return;
	 	
	    var sellerId = $('#sellerId').val();
	    var isPickup = $('#isPickup').val();
	    var yyyyMmDd = $('#yyyyMmDd').val();
	    var btwnFromHhmm = $('#btwnFromHhmm').val();
	    var btwnToHhmm = $('#btwnToHhmm').val();	    
	    
	    var postcode = '';
	    var suburb = '';
	    var state = '';
	    var street = '';
	    
	    if(isPickup == 'Y') {
	    } else  if (isPickup == 'N') {
		    postcode = dataItem.postcode;
		    suburb = dataItem.suburb;
		    state = dataItem.state;
	    }
	    
	    $.ajax({
	         url  : "/admin/deliverycalendarmgt/addCalendar.yum",
	         data      : {
	           sellerId : sellerId,
	           isPickup : isPickup,
	           yyyyMmDd : yyyyMmDd,
	           btwnFromHhmm : btwnFromHhmm,
	           btwnToHhmm : btwnToHhmm,
	           addressPostcode : postcode,
	           addressState : state,
	           addressSuburb : suburb,
	           addressStreet : street
	         },
	         success : callbackAddDeliveryCalendar
	    }); 
	 	
	}
	
	function addDeliveryCalendarFromScreen() {
	 	
	 	if(validateForm() == false) return;
	 	
	    var sellerId = $('#sellerId').val();
	    var isPickup = $('#isPickup').val();
	    var yyyyMmDd = $('#yyyyMmDd').val();
	    var btwnFromHhmm = $('#btwnFromHhmm').val();
	    var btwnToHhmm = $('#btwnToHhmm').val();	    
	    
	    var postcode = '';
	    var suburb = '';
	    var state = '';
	    var street = '';
	    var addressNote = '';
	    
	    if(isPickup == 'Y') {
		    postcode = $('#sellerPickupAddressPostcode').val();
		    suburb = $('#sellerPickupAddressSuburb').val();
		    state = $('#sellerPickupAddressState').val();
		    street = $('#sellerPickupAddressStreet').val();
		    addressNote = $('#addressNote').val();
		    
	    } else {
	    }
	    
	    $.ajax({
	         url  : "/admin/deliverycalendarmgt/addCalendar.yum",
	         data      : {
	           sellerId : sellerId,
	           isPickup : isPickup,
	           yyyyMmDd : yyyyMmDd,
	           btwnFromHhmm : btwnFromHhmm,
	           btwnToHhmm : btwnToHhmm,
	           addressPostcode : postcode,
	           addressState : state,
	           addressSuburb : suburb,
	           addressStreet : street,
	           addressNote : addressNote
	         },
	         success : callbackAddDeliveryCalendar
	    }); 
	 	
	}	
	
	function callbackAddDeliveryCalendar(data) {
	      var message = data.message;
	      var resultCode = data.resultCode;

	      if (resultCode != "0") {
	           warningPopup(data.message);
	      } else {
	    	  parent.search();
	      }
	}	
</script>

<script type="text/javascript">
	var checkObject = [];
	function validateForm(){
		var count;
	    var elementObj = "";
	    var validation = true;
	    
	    // 값검증 결과 초기화
	    for(count=0; count < checkObject.length; count++ ){
	         elementObj = "#" + checkObject[count];
	         $(elementObj).css({'background':'#ECF5FF','border-color':'#DFDFDF'});
	    }
	    checkObject = [];
	    var prefix = "- &nbsp;&nbsp;";
	    var message = "";
	
	    var sellerId = $('#sellerId').val();
	    var isPickup = $('#isPickup').val();
	    var yyyyMmDd = $('#yyyyMmDd').val();
	    var btwnFromHhmm = $('#btwnFromHhmm').val();
	    var btwnToHhmm = $('#btwnToHhmm').val();
	    
	    var postcode = '';
	    var suburb = '';
	    var state = '';
	    var street = '';
	    
	    if(isPickup == 'Y') {
		    postcode = $('#sellerPickupAddressPostcode').val();
		    suburb = $('#sellerPickupAddressSuburb').val();
		    state = $('#sellerPickupAddressState').val();
		    street = $('#sellerPickupAddressStreet').val();
	    }
	    
	 	if(sellerId == "") {
	 		message = message + prefix + "Selller ID 값은 필수값입니다.<br>";
	 		checkObject[checkObject.length] = "sellerId";
	        validation = false;
	 	}
	 	if(isPickup == "") {
	 		message = message + prefix + "고객님께서 픽업할 것인지, 고객님께 배달해드릴것인지 설정해주세요.<br>";
	 		checkObject[checkObject.length] = "isPickup";
	        validation = false;
	 	}
	 	
	 	if(isPickup == 'Y') {
		 	if(state == "") {
		 		message = message + prefix + "고객님께서 픽업할 State를 입력해세요.<br>";
		 		checkObject[checkObject.length] = "sellerPickupAddressState";
		        validation = false;
		 	}
		 	if(postcode == "") {
		 		message = message + prefix + "고객님께서 픽업할 Postcode를 입력해세요.<br>";
		 		checkObject[checkObject.length] = "sellerPickupAddressPostcode";
		        validation = false;
		 	}
		 	if(suburb == "") {
		 		message = message + prefix + "고객님께서 픽업할 Suburb를 입력해세요.<br>";
		 		checkObject[checkObject.length] = "sellerPickupAddressSuburb";
		        validation = false;
		 	}
		 	if(street == "") {
		 		message = message + prefix + "고객님께서 픽업할 Street를 입력해세요.<br>";
		 		checkObject[checkObject.length] = "sellerPickupAddressStreet";
		        validation = false;
		 	}
	 	}
	 	
	 	
	 	if(yyyyMmDd == "") {
	 		message = message + prefix + "배송일 지정은 필수입니다.<br>";
	 		checkObject[checkObject.length] = "yyyyMmDd";
	 		validation = false;
	 	} else {
	        var parsedDate = kendo.parseDate(yyyyMmDd, "yyyy-mm-dd");
	        if (!parsedDate) {
	            message = message + prefix + "올바른 배송일 형식이 아닙니다. [YYYY-MM-DD] 형식으로 입력해주세요<br>";
	            checkObject[checkObject.length] = "yyyyMmDd";
	            validation = false;
	        }
	 	}
	 	if(btwnFromHhmm == "") {
	 		message = message + prefix + "시작시간 지정은 필수입니다.<br>";
	 		checkObject[checkObject.length] = "btwnFromHhmm";
	 		validation = false;
	 	} else {
	        var parsedTime = kendo.parseDate(btwnFromHhmm, "HH:mm tt");
	        if (!parsedTime) {
	            message = message + prefix + "올바른 시작시간 형식이 아닙니다. [HH:MM AM/PM] 형식으로 입력해주세요<br>";
	            checkObject[checkObject.length] = "btwnFromHhmm";
	            validation = false;
	        }
	 	}	
	 	if(btwnToHhmm == "") {
	 		message = message + prefix + "종료시간 지정은 필수입니다.<br>";
	 		checkObject[checkObject.length] = "btwnToHhmm";
	 		validation = false;
	 	} else {
	        var parsedTime = kendo.parseDate(btwnToHhmm, "HH:mm tt");
	        if (!parsedTime) {
	            message = message + prefix + "올바른 종료시간 형식이 아닙니다. [HH:MM AM/PM] 형식으로 입력해주세요<br>";
	            checkObject[checkObject.length] = "btwnToHhmm";
	            validation = false;
	        }
	 	}	
	 	
	 	
	 	
	 	// 검증된 필드들을 마킹한다.
		for(count=0; count < checkObject.length; count++ ){
			elementObj = "#" + checkObject[count];
			$(elementObj).css({'background':'#fffacd','border-color':'#DF0000','border' : '1px solid #f00'});
		}
	 	if(validation == false){
	 		// 오류가 있는 경우 경고 창을 보여준다.
	 		warningPopup(message);
	 	}
	 	return validation;
	}
	
	function infoForPickupservice(obj){
		var isPickup = obj.value;
		if(isPickup == 'Y'){
			// infoPopup("<span style='color: #A8154A;'><br>고객님이 상품을 픽업하는 경우 판매자의 Suburb를 지정하여 등록해 주시기 바랍니다.<br></span>");
			$("#searchForFullAddr").show();
			$("#searchForSimpleAddr").hide();
		} else {
			$("#searchForFullAddr").hide();
			$("#searchForSimpleAddr").show();
		}
	}
</script>


</head>

<body>
     <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="100px" />
                              <col width="200px" />
                              <col width="100px" />
                              <col width="200px" />
                         </colgroup>       
                         <tr>
                              <td class="label"><span class="required">* </span>Seller</td>
                              <td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
                              <td class="label"><span class="required">* </span>Method</td>
                              <td class="value"><c:out value="${cbxIsPickup}" escapeXml="false"/></td>
                         </tr>
                         <tr>
                              <td class="label"><span class="required">* </span>Date</td>
                              <td class="value"><input id="yyyyMmDd" name="yyyyMmDd" value="" /></td>
                              <td class="value" colspan="2">
                              	<table style="width: 100%;">
                              		<tr>
                              			<td><input id="btwnFromHhmm" value="6:00 AM"/></td>
                              			<td> ~ </td>
                              			<td><input id="btwnToHhmm" value="8:00 PM"/></td>
                              		</tr>
                              	</table>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          <tr style="height: 10px;"><td colspan="4"></td></tr>
     </table>
    
    <div id="searchForFullAddr" style="display: none; ">
           <table class="detail_table_c">
                 <colgroup>
                      <col width="80px" />
                      <col width="150px" />
                      <col width="80px" />
                      <col width="150px" />
                 </colgroup>            
                 <tr>
                  	<td colspan="4">
                  	    <br />
                  	    <br />
                  	    <span class="subtitle"> 고객이 상품을 찾아갈 주소를 입력해주세요.</span>
						<hr class="subtitle"/>  
                  	</td>
                 </tr>         
                 <tr>
                      <td class="label"><span class="required">* </span>State</td>
                      <td class="value"><c:out value="${cbxDefaultPickupState}" escapeXml="false"/></td>
                      <td></td>
                      <td></td>
                 </tr>
                 <tr>
                      <td class="label"><span class="required">* </span>Postcode</td>
                      <td class="value" style="padding-left: 3px;">
                           <table><tr><td><input class="form-control" type="text" id="sellerPickupAddressPostcode" name="sellerPickupAddressPostcode" value='${defaultPickupPostcode}' style="width: 80px;" maxlength="4"/></td><td><img src="/resources/image/lookup.png" style="cursor: pointer;" onclick="setSuburbCbx('sellerPickupAddressPostcode', 'sellerPickupAddressSuburb')"></td></tr></table>
                      </td>
                      <td class="label"><span class="required">* </span>Suburb</td>
                      <td class="value"><div id="cbx_sellerPickupAddressSuburb"><input class="form-control" type="text" id="sellerPickupAddressSuburb" name="sellerPickupAddressSuburb" value='${defaultPickupSurbub}'/></div></td>
                 </tr>
                 <tr>
                      <td class="label"><span class="required">* </span>Street</td>
                      <td class="value" colspan="2"><input class="form-control" type="text" id="sellerPickupAddressStreet" name="sellerPickupAddressStreet" value='${defaultPickupStreet}'/></td>
                      <td></td>
                 </tr>  
                 <tr>
                      <td class="label">Comment</td>
                      <td class="value" colspan="3"><input class="form-control" type="text" id="addressNote" name="addressNote" value='' maxlength="230"/></td>
                 </tr>
                 <tr><td colspan="4" style="height: 20px;">&nbsp;</td></tr>  
                 <tr>
                      <td colspan="4" style="text-align: right;">
                      	<button type="button" class="btn btn-default btn-sm" onclick="parent.closeOptionWindow();">Close</button>&nbsp;&nbsp;&nbsp;
                      	<button type="button" class="btn btn-primary btn-sm" onclick="addDeliveryCalendarFromScreen();">Add</button> &nbsp;&nbsp;
                      </td>
                 </tr>  
			</table>                           
    </div>
    <div id="searchForSimpleAddr" style="display: block; ">
    
					    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
					    <!-- Search -->
					    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
					    <div class="well">
					    	 <table class="search_table">
					    	 	<tr>
					    	 		<td class="label">State </td>
					    	 		<td class="value"><c:out value="${cbxAddressState}" escapeXml="false"/></td>
					    	 		<td class="label">Postcode </td>
					    	 		<td class="value"><input class="form-control" type="text" id="postcode" name="postcode" value='' maxlength="4"/></td>
					    	 		<td class="label">Suburb</td>
					    	 		<td class="value_end"><input class="form-control" type="text" id="suburb" name="suburb" value=''/></td>
					                <td class="find"><button type="button" class="btn btn-info" onclick="search();">Search</button></td>
					    	 	</tr>
					    	 </table>
					    </div>     
					     
					    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
					    <!-- Table List -->
					    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
					    <div id="grid_panel_main"></div>     
					    <br/> 
           <table class="detail_table_c">
                 <tr>
                      <td colspan="4" style="text-align: right;">
                      	<button type="button" class="btn btn-default btn-sm" onclick="parent.closeOptionWindow();">Close</button>
                  	  </td>
                 </tr> 
          </table>       					    
    </div> 
     
               
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>