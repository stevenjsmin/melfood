<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
        var MelfoodGmap = new Object();
        MelfoodGmap.mapName = '멜푸드지도';
        MelfoodGmap.mapStyleNo = 6;
        MelfoodGmap.mapIsMultipleMark = '${mapIsMultipleMark}';
        MelfoodGmap.mapZoomLevel = 13;
        MelfoodGmap.mapAddress = '${mapFullAddress}';
        MelfoodGmap.mapMessage = "${mapMessage}";
        
        if('${mapIsMultipleMark}' == 'Y'){
	        
        	MelfoodGmap.mapStyleNo = 4;
        	
        	var schedule = [];
	        var address = "";
	        var message = "";
	        
	        
	        <c:forEach var="entry" items="${deliverySchedules}" varStatus="count" begin="0">
	            if('${entry.isPickup}' == 'Y' || '${entry.isPickup}' == 'y') {
	            	address = '${entry.addressSuburb}' + ' ' + '${entry.addressState}' + ' ' + '${entry.addressPostcode}';
	            } else {
	            	address = '${entry.addressStreet}' + ' ' + '${entry.addressSuburb}' + ' ' + '${entry.addressState}' + ' ' +  '${entry.addressPostcode}';
	            }
	            message = getDeliveryAreaMessage(address, '${entry.yyyyMmDd}', '${entry.btwnFromHhmm}', '${entry.btwnToHhmm}', '${entry.addressNote}');
	            
	            if('${deliveryCalendar.yyyyMmDd}' == '${entry.yyyyMmDd}' && '${deliveryCalendar.addressPostcode}' == '${entry.addressPostcode}' && '${deliveryCalendar.addressSuburb}' == '${entry.addressSuburb}'){
		        	schedule.push({address:address, message: message, clickEvent: true});
	            } else {
		        	schedule.push({address:address, message: message, clickEvent: false});
	            }
	        </c:forEach>
	        
	        MelfoodGmap.mapDeliverySchedules = schedule;
	        // MelfoodGmap.mapZoomLevel = 11;
        }
        
        if('${mapIsMultipleMark}' == 'Y'){
			markAddressOnGMap(MelfoodGmap);
			$("#streetViewMap").hide();
			$("#map-street-canvas").hide();
        } else {
			markAddressOnGMap(MelfoodGmap);
			markStreeViewOnGMap(MelfoodGmap);
			$("#streetViewMap").show();
			$("#map-street-canvas").show();
        }
		
	}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>

<script>
	function getDeliveryAreaMessage(deliveryAddress, deliveryYyyyMmDd, fromTime, toTime, note) {
		var html = "<table>"
		       		    + "<tr>" 
		       		    + "    <td style='text-align: right;'><font color='#561314'><b>" + deliveryYyyyMmDd + "</b></font><font color='#F07800'> 일 <b>배송지역</b></font></td>" 
		       		    + "    <td width='10' style='text-align: center;'>:</td>" 
		       		    + "    <td>" + deliveryAddress + "</td>" 
		       		    + "</tr>" 
		       		    + "<tr>" 
		       		    + "    <td style='text-align: right;'><font color='#808040'>배송예상시간 </font></td>" 
		       		    + "    <td width='10' style='text-align: center;'>:</td>" 
		       		    + "    <td>" + fromTime + ' ~ ' + toTime + "</td>" 
		       		    + "</tr>" 
		       		    + "<tr>" 
		       		    + "    <td colspan='3'>" + note + "</td>" 
		       		    + "</tr>" 
		      		    + "</table>"; 
		return html;
	}
</script>


</head>
<body>

    <div class="row">
        <div class="col-md-12">
            <c:if test="${mapIsMultipleMark == 'N' }">
            <!-- 픽업하는 경우 -->
	        	<table class="detail_table" style="width: 100%;border-color: #D6D6D6;" border="1">
	        		<tr>
	        			<td style="width: 150px;text-align: right;"><b>${yyyyMmDd }</b>일 픽업장소 :</td>
	        			<td class="value"><span style="color: #C7001C;">${mapFullAddress}</span></td>
	        		</tr>
	        		<tr>
	        			<td style="width: 150px;text-align: right;">픽업시간 : <span style="color: #C7001C;"></td>
	        			<td class="value"><span style="color: #C7001C;">${deliveryCalendar.btwnFromHhmm} ~ ${deliveryCalendar.btwnToHhmm}</span></td>
	        		</tr>
	        	</table>
            </c:if>
            <c:if test="${mapIsMultipleMark == 'Y' }">
            <!-- 배송하는 경우 : 한개 이상의 Suburb 표시 -->
	        	<table style="width: 100%;border-color: #D6D6D6;" border="0">
	        		<tr>
	        			<td style="padding-bottom: 5px;"><b>${yyyyMmDd }</b>일 배송지역 : <br/> 
	        				<c:forEach var="entry" items="${deliverySchedules}" varStatus="count" begin="0">
	        				   [ <span style="color: #C7001C;">${entry.addressSuburb}&nbsp;${entry.addressPostcode} </span>] &nbsp; 
	        				</c:forEach>
	        			</td>
	        		</tr>
	        	</table>
            </c:if>
            
            
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
        	<table style="width: 760px;border-color: #D6D6D6;" border="1">
        		<tr>
        			<td>
						<div id='map-canvas' style="width: 750px; height: 450px;"></div>
						
						<div id="streetViewMap" style="padding-top: 20px;">
						    <span class="subtitle"> Street view</span>
						    <hr class="subtitle"/>						
							<div id='map-street-canvas' style="width: 750px; height: 450px;"></div>
						</div>
        			</td>
        		</tr>
        	</table>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
			 <table class="action_button_table" width="100%" style="margin-top: 10px;" >
		     			<tr>
		         		 	<td style="padding-right: 10px;">
		               			<a href="javascript:parent.closeDisplayGMapPopup();" class="btn btn-info">&nbsp;&nbsp; 닫기 &nbsp;&nbsp;</a>
		          			</td>
		     			</tr>
		 	 </table>	
        </div>
    </div>
	
			    
</body>
</html>