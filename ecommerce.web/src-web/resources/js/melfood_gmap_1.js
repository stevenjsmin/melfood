/****************************************************************
 * This JS file is for marking google map with NMI information.
 * NMI information that display on the map should be co-operated with uss.rpc.model.NMI POJO model
 * Writen by Steven Min, 2015-12-09  
 */


/* DEFAULT */ var USSMAPSTYLE_UNSATURATED_BROWNS = [{"elementType":"geometry","stylers":[{"hue":"#ff4400"},{"saturation":-68},{"lightness":-4},{"gamma":0.72}]},{"featureType":"road","elementType":"labels.icon"},{"featureType":"landscape.man_made","elementType":"geometry","stylers":[{"hue":"#0077ff"},{"gamma":3.1}]},{"featureType":"water","stylers":[{"hue":"#00ccff"},{"gamma":0.44},{"saturation":-33}]},{"featureType":"poi.park","stylers":[{"hue":"#44ff00"},{"saturation":-23}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"hue":"#007fff"},{"gamma":0.77},{"saturation":65},{"lightness":99}]},{"featureType":"water","elementType":"labels.text.stroke","stylers":[{"gamma":0.11},{"weight":5.6},{"saturation":99},{"hue":"#0091ff"},{"lightness":-86}]},{"featureType":"transit.line","elementType":"geometry","stylers":[{"lightness":-48},{"hue":"#ff5e00"},{"gamma":1.2},{"saturation":-23}]},{"featureType":"transit","elementType":"labels.text.stroke","stylers":[{"saturation":-64},{"hue":"#ff9100"},{"lightness":16},{"gamma":0.47},{"weight":2.7}]}];
/* TYPE 1  */ var USSMAPSTYLE_NEUTRAL_BLUE = [{"featureType":"water","elementType":"geometry","stylers":[{"color":"#193341"}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"color":"#2c5a71"}]},{"featureType":"road","elementType":"geometry","stylers":[{"color":"#29768a"},{"lightness":-37}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#406d80"}]},{"featureType":"transit","elementType":"geometry","stylers":[{"color":"#406d80"}]},{"elementType":"labels.text.stroke","stylers":[{"visibility":"on"},{"color":"#3e606f"},{"weight":2},{"gamma":0.84}]},{"elementType":"labels.text.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"administrative","elementType":"geometry","stylers":[{"weight":0.6},{"color":"#1a3541"}]},{"elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#2c5a71"}]}];
/* TYPE 2  */ var USSMAPSTYLE_GOWALLA = [{"featureType":"administrative.land_parcel","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"landscape.man_made","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"labels","stylers":[{"visibility":"simplified"},{"lightness":20}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"hue":"#f49935"}]},{"featureType":"road.highway","elementType":"labels","stylers":[{"visibility":"simplified"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"hue":"#fad959"}]},{"featureType":"road.arterial","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.local","elementType":"labels","stylers":[{"visibility":"simplified"}]},{"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"all","stylers":[{"hue":"#a1cdfc"},{"saturation":30},{"lightness":49}]}];
/* TYPE 3  */ var USSMAPSTYLE_UNSATURATED_MAPBOX = [{"featureType":"water","stylers":[{"saturation":43},{"lightness":-11},{"hue":"#0088ff"}]},{"featureType":"road","elementType":"geometry.fill","stylers":[{"hue":"#ff0000"},{"saturation":-100},{"lightness":99}]},{"featureType":"road","elementType":"geometry.stroke","stylers":[{"color":"#808080"},{"lightness":54}]},{"featureType":"landscape.man_made","elementType":"geometry.fill","stylers":[{"color":"#ece2d9"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#ccdca1"}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"color":"#767676"}]},{"featureType":"road","elementType":"labels.text.stroke","stylers":[{"color":"#ffffff"}]},{"featureType":"poi","stylers":[{"visibility":"off"}]},{"featureType":"landscape.natural","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#b8cb93"}]},{"featureType":"poi.park","stylers":[{"visibility":"on"}]},{"featureType":"poi.sports_complex","stylers":[{"visibility":"on"}]},{"featureType":"poi.medical","stylers":[{"visibility":"on"}]},{"featureType":"poi.business","stylers":[{"visibility":"simplified"}]}];
/* TYPE 4  */ var USSMAPSTYLE_MUTED_BLUE = [{"featureType":"all","stylers":[{"saturation":0},{"hue":"#e7ecf0"}]},{"featureType":"road","stylers":[{"saturation":-70}]},{"featureType":"transit","stylers":[{"visibility":"off"}]},{"featureType":"poi","stylers":[{"visibility":"off"}]},{"featureType":"water","stylers":[{"visibility":"simplified"},{"saturation":-60}]}];
/* TYPE 5  */ var USSMAPSTYLE_LIGHT_GRAY = [{"featureType":"water","elementType":"geometry.fill","stylers":[{"color":"#d3d3d3"}]},{"featureType":"transit","stylers":[{"color":"#808080"},{"visibility":"off"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"visibility":"on"},{"color":"#b3b3b3"}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"road.local","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#ffffff"},{"weight":1.8}]},{"featureType":"road.local","elementType":"geometry.stroke","stylers":[{"color":"#d7d7d7"}]},{"featureType":"poi","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#ebebeb"}]},{"featureType":"administrative","elementType":"geometry","stylers":[{"color":"#a7a7a7"}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"landscape","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#efefef"}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"color":"#696969"}]},{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"visibility":"on"},{"color":"#737373"}]},{"featureType":"poi","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","elementType":"geometry.stroke","stylers":[{"color":"#d6d6d6"}]},{"featureType":"road","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{},{"featureType":"poi","elementType":"geometry.fill","stylers":[{"color":"#dadada"}]}];
/* TYPE 6  */ var USSMAPSTYLE_BLUE_GRAY = [{"featureType":"water","stylers":[{"visibility":"on"},{"color":"#b5cbe4"}]},{"featureType":"landscape","stylers":[{"color":"#efefef"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#83a5b0"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#bdcdd3"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#e3eed3"}]},{"featureType":"administrative","stylers":[{"visibility":"on"},{"lightness":33}]},{"featureType":"road"},{"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":20}]},{},{"featureType":"road","stylers":[{"lightness":20}]}];
/* TYPE 7  */ var USSMAPSTYLE_BRIGHT_BUBBLY = [{"featureType":"water","stylers":[{"color":"#19a0d8"}]},{"featureType":"administrative","elementType":"labels.text.stroke","stylers":[{"color":"#ffffff"},{"weight":6}]},{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"color":"#e85113"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#efe9e4"},{"lightness":-40}]},{"featureType":"road.arterial","elementType":"geometry.stroke","stylers":[{"color":"#efe9e4"},{"lightness":-20}]},{"featureType":"road","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"lightness":-100}]},{"featureType":"road.highway","elementType":"labels.icon"},{"featureType":"landscape","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"landscape","stylers":[{"lightness":20},{"color":"#efe9e4"}]},{"featureType":"landscape.man_made","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"lightness":-100}]},{"featureType":"poi","elementType":"labels.text.fill","stylers":[{"hue":"#11ff00"}]},{"featureType":"poi","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"poi","elementType":"labels.icon","stylers":[{"hue":"#4cff00"},{"saturation":58}]},{"featureType":"poi","elementType":"geometry","stylers":[{"visibility":"on"},{"color":"#f0e4d3"}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#efe9e4"},{"lightness":-25}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#efe9e4"},{"lightness":-10}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"simplified"}]}];
/* TYPE 8  */ var USSMAPSTYLE_BENTLEY = [{"featureType":"landscape","stylers":[{"hue":"#F1FF00"},{"saturation":-27.4},{"lightness":9.4},{"gamma":1}]},{"featureType":"road.highway","stylers":[{"hue":"#0099FF"},{"saturation":-20},{"lightness":36.4},{"gamma":1}]},{"featureType":"road.arterial","stylers":[{"hue":"#00FF4F"},{"saturation":0},{"lightness":0},{"gamma":1}]},{"featureType":"road.local","stylers":[{"hue":"#FFB300"},{"saturation":-38},{"lightness":11.2},{"gamma":1}]},{"featureType":"water","stylers":[{"hue":"#00B6FF"},{"saturation":4.2},{"lightness":-63.4},{"gamma":1}]},{"featureType":"poi","stylers":[{"hue":"#9FFF00"},{"saturation":0},{"lightness":0},{"gamma":1}]}];
/* TYPE 9  */ var USSMAPSTYLE_PASTEL_TONES = [{"featureType":"landscape","stylers":[{"saturation":-100},{"lightness":60}]},{"featureType":"road.local","stylers":[{"saturation":-100},{"lightness":40},{"visibility":"on"}]},{"featureType":"transit","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"administrative.province","stylers":[{"visibility":"off"}]},{"featureType":"water","stylers":[{"visibility":"on"},{"lightness":30}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ef8c25"},{"lightness":40}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#b6c54c"},{"lightness":40},{"saturation":-40}]},{}];
/* TYPE 10 */ var USSMAPSTYLE_BLUE_GRAY = [{"featureType":"water","stylers":[{"visibility":"on"},{"color":"#b5cbe4"}]},{"featureType":"landscape","stylers":[{"color":"#efefef"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#83a5b0"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#bdcdd3"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#e3eed3"}]},{"featureType":"administrative","stylers":[{"visibility":"on"},{"lightness":33}]},{"featureType":"road"},{"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":20}]},{},{"featureType":"road","stylers":[{"lightness":20}]}];
/* TYPE 11 */ var USSMAPSTYLE_SUBTLE_GRAYSCALE = [{"featureType":"landscape","stylers":[{"saturation":-100},{"lightness":65},{"visibility":"on"}]},{"featureType":"poi","stylers":[{"saturation":-100},{"lightness":51},{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"road.arterial","stylers":[{"saturation":-100},{"lightness":30},{"visibility":"on"}]},{"featureType":"road.local","stylers":[{"saturation":-100},{"lightness":40},{"visibility":"on"}]},{"featureType":"transit","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"administrative.province","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":-25},{"saturation":-100}]},{"featureType":"water","elementType":"geometry","stylers":[{"hue":"#ffff00"},{"lightness":-25},{"saturation":-97}]}];
/* TYPE 12 */ var USSMAPSTYLE_CLADME = [{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"color":"#444444"}]},{"featureType":"landscape","elementType":"all","stylers":[{"color":"#f2f2f2"}]},{"featureType":"poi","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"all","stylers":[{"saturation":-100},{"lightness":45}]},{"featureType":"road.highway","elementType":"all","stylers":[{"visibility":"simplified"}]},{"featureType":"road.arterial","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"all","stylers":[{"color":"#4f595d"},{"visibility":"on"}]}];

function getMapStyle(UssNmiGmap, mapType){
	var ussMapStyleNo = null;
	
	// Default MAP style select
	var ussMapStyle = USSMAPSTYLE_UNSATURATED_BROWNS;
	if(mapType == 'HEAT') ussMapStyle = USSMAPSTYLE_BENTLEY;
	
	if (typeof UssNmiGmap.mapStyle != 'undefined') ussMapStyleNo = UssNmiGmap.mapStyle;
	
	if(ussMapStyleNo == '1')  ussMapStyle  = USSMAPSTYLE_NEUTRAL_BLUE;
	if(ussMapStyleNo == '2')  ussMapStyle  = USSMAPSTYLE_GOWALLA;
	if(ussMapStyleNo == '3')  ussMapStyle  = USSMAPSTYLE_UNSATURATED_MAPBOX;
	if(ussMapStyleNo == '4')  ussMapStyle  = USSMAPSTYLE_MUTED_BLUE;
	if(ussMapStyleNo == '5')  ussMapStyle  = USSMAPSTYLE_LIGHT_GRAY;
	if(ussMapStyleNo == '6')  ussMapStyle  = USSMAPSTYLE_BLUE_GRAY;
	if(ussMapStyleNo == '7')  ussMapStyle  = USSMAPSTYLE_BRIGHT_BUBBLY;
	if(ussMapStyleNo == '8')  ussMapStyle  = USSMAPSTYLE_BENTLEY;
	if(ussMapStyleNo == '9')  ussMapStyle  = USSMAPSTYLE_PASTEL_TONES;
	if(ussMapStyleNo == '10') ussMapStyle  = USSMAPSTYLE_BLUE_GRAY;
	if(ussMapStyleNo == '11') ussMapStyle  = USSMAPSTYLE_SUBTLE_GRAYSCALE;
	if(ussMapStyleNo == '12') ussMapStyle  = USSMAPSTYLE_CLADME;
	
	return ussMapStyle;
}

var prev_nmi_gmap_infowindow = false;

/*
 * Mark Google map
 * 
 * 	@parm NMIs : Array of NMI
 * 	@parm UssNmiGmap.mapStyle : Google map style
 * 	@parm UssNmiGmap.mapName : Customized map name - It will display left top as string title
 * 	@parm UssNmiGmap.mapBindObjID : HTML element object id to binding google map
 * 	@parm UssNmiGmap.mapIcon : Image to be marked on google map
 * 	@parm UssNmiGmap.mapIconSize : Image size to be marked on google map
 * 	@parm UssNmiGmap.mapZoomLevel : Map zoom Level(Available only in case of number of ONE NMI ( Default: 10)
 * 	@parm UssNmiGmap.mapDetailPopup : Turn on or off for detail popup window on the map
 */
function markNMIOnGMap(NMIs, UssNmiGmap){
	
	var ussMapStyle = getMapStyle(UssNmiGmap,'NORMAL');
	var ussMapName = "USS Map";
	var ussBindObjID = "map_canvas";
	var ussMapMarkIcon = "/resources/image/map_marker03.yum";
	var ussMapIconXSize = 25;
	var ussMapIconYSize = 34;
	var ussMapZoomLevel = 10;
	var ussDetailPopup = true;
	
	var moreThan1NMI = true;
	if(NMIs == null || NMIs.length < 2) moreThan1NMI = false;
	
	if (typeof UssNmiGmap.mapName != 'undefined') ussMapName = UssNmiGmap.mapName;
	if (typeof UssNmiGmap.mapBindObjID != 'undefined') ussBindObjID = UssNmiGmap.mapBindObjID;
	if (typeof UssNmiGmap.mapZoomLevel != 'undefined') ussMapZoomLevel = UssNmiGmap.mapZoomLevel;
	if (typeof UssNmiGmap.mapDetailPopup != 'undefined') ussDetailPopup = UssNmiGmap.mapDetailPopup;
	
	if (typeof UssNmiGmap.mapIconSize != 'undefined') {
		var xCoordinate = UssNmiGmap.mapIconSize.substr(0, UssNmiGmap.mapIconSize.indexOf(',')); 
		var yCoordinate = UssNmiGmap.mapIconSize.substr(UssNmiGmap.mapIconSize.indexOf(','));
		ussMapIconXSize = xCoordinate;
		ussMapIconYSize = yCoordinate;
	}
	
	if (typeof UssNmiGmap.mapIcon != 'undefined') ussMapMarkIcon = UssNmiGmap.mapIcon;
	
	if(typeof uss_mapicon != 'undefined' && uss_mapicon != null && uss_mapicon != ""){
		ussMapMarkIcon = uss_mapicon;
	}

	// Create an array of styles.
	var styledMap = new google.maps.StyledMapType(ussMapStyle, {name: ussMapName});
	
    var geocoder = new google.maps.Geocoder();
  	var bounds = new google.maps.LatLngBounds();
 	var mapoptions = { zoom:1, mapTypeControlOptions: { mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style'] } };  
 	var map = new google.maps.Map(document.getElementById(ussBindObjID), mapoptions); 
 	//Associate the styled map with the MapTypeId and set it to display.
 	map.mapTypes.set('map_style', styledMap);
 	map.setMapTypeId('map_style');	 			 
 	map.markers = [];
	// var xy = [];
	var nmi = null;
		
	var mapMarker = {
			url: ussMapMarkIcon,
		    scaledSize: new google.maps.Size(ussMapIconXSize,ussMapIconYSize),
		    origin: new google.maps.Point(0,0),
		    anchor: new google.maps.Point(0,0)
		};		
	
	
	
	var marker = null;
	for(var i=0;i<NMIs.length; i++){
		nmi = NMIs[i];
		
		// alert(nmi.gmapLatitude + ",   " + nmi.gmapLongitude);
		
		if(nmi.gmapLatitude != null && nmi.gmapLongitude ) {
 			marker = new google.maps.Marker({
    				map : map,
    				position: {lat: parseFloat(nmi.gmapLatitude), lng: parseFloat(nmi.gmapLongitude)},
    			    title: nmi.nmi + ',' + nmi.gmapFormattedAddress,
    				icon: mapMarker
    		});
 			if(ussDetailPopup) openNMIInfoWindow(marker, map, nmi);
 			
    	    // Extends this bounds to contain the given point.
 			
 			if(moreThan1NMI) {
 				bounds.extend(marker.position);
 				map.markers.push(marker);
 				map.fitBounds(bounds); //Sets the viewport to contain the given bounds.
 			} else {
 				bounds.extend(marker.position);
 				map.markers.push(marker);
 			}
		} // End of IF
	} // End of FOR
	
	if(moreThan1NMI == false) {
		map.setZoom(parseInt(ussMapZoomLevel));
		map.setCenter(bounds.getCenter());
	}
}

function openNMIInfoWindow(marker, map, nmi) {
    google.maps.event.addListener(marker, 'click', function() {
    	
        var html = "<table>"
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;'><font color='#F07800'><b>NMI</b></font></td>" 
	        		    + "    <td width='10' style='text-align: center;'>:</td>" 
	        		    + "    <td>" + nmi.nmi + "</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;'><font color='#808040'>Address </font></td>" 
	        		    + "    <td width='10' style='text-align: center;'>:</td>" 
	        		    + "    <td>" + nmi.gmapFormattedAddress + "</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;'><font color='#808040'>Network code </font></td>" 
	        		    + "    <td width='10' style='text-align: center;'>:</td>" 
	        		    + "    <td>" + nmi.networkCode + "</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;'><font color='#808040'>Customer Type </font></td>" 
	        		    + "    <td width='10' style='text-align: center;'>:</td>" 
	        		    + "    <td>" + nmi.customerClassificationCode + "</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;'><font color='#808040'>Broker </font></td>" 
	        		    + "    <td width='10' style='text-align: center;'>:</td>" 
	        		    + "    <td>" + nmi.brokerName + "</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td colspan=3>&nbsp;</td>" 
	        		    + "</tr>" 
	        		    + "<tr>" 
	        		    + "    <td style='text-align: right;' colspan=3>" + "<a href='javascript:openNMIInfo(" + nmi.nmi + ")'>Detail info</a></td> " 
	        		    + "</tr>" 
        		    + "</table>"; 
        
        var infowindow = new google.maps.InfoWindow({ content : html, maxWidth : 450});
        
    	if( prev_nmi_gmap_infowindow ) {
    		prev_nmi_gmap_infowindow.close();
        }
    	prev_nmi_gmap_infowindow = infowindow;
        infowindow.open(map,marker);
    });
}


function openNMIInfo(nmi) {
    
    var UssNMI = new Object();
    UssNMI.nmi = nmi;
    UssNMI.wintitle = "NMI Info";

    // Open email popup
    commonNMIDetailInfo(UssNMI); 
}


function markNMIOnGHeatMap(NMIs, UssNmiGmap){
	var pointarray, heatmap;
	
	var ussMapStyle = getMapStyle(UssNmiGmap,'HEAT');
	
	var ussMapStyleNo = null;
	var ussMapName = "USS Map";
	var ussBindObjID = "map_canvas";
	var ussMapZoomLevel = 10;
	
	var moreThan1NMI = true;
	if(NMIs == null || NMIs.length < 2) moreThan1NMI = false;
	
	var ussHeatGradient = ['rgba(0, 255, 255, 0)', 'rgba(0, 255, 255, 1)','rgba(0, 191, 255, 1)','rgba(0, 127, 255, 1)','rgba(0, 63, 255, 1)','rgba(0, 0, 255, 1)','rgba(0, 0, 223, 1)','rgba(0, 0, 191, 1)','rgba(0, 0, 159, 1)','rgba(0, 0, 127, 1)','rgba(63, 0, 91, 1)','rgba(127, 0, 63, 1)','rgba(191, 0, 31, 1)','rgba(255, 0, 0, 1)'];
	var ussHeatOpacity = 1;
	var ussHeatRadius = 20;
	
	if (typeof UssNmiGmap.mapName != 'undefined') ussMapName = UssNmiGmap.mapName;
	if (typeof UssNmiGmap.mapBindObjID != 'undefined') ussBindObjID = UssNmiGmap.mapBindObjID;
	if (typeof UssNmiGmap.mapZoomLevel != 'undefined') ussMapZoomLevel = UssNmiGmap.mapZoomLevel;
	if (typeof UssNmiGmap.mapHeatRadius != 'undefined') ussHeatRadius = UssNmiGmap.mapHeatRadius;
	if (typeof UssNmiGmap.mapHeatOpacity != 'undefined') ussHeatOpacity = UssNmiGmap.mapHeatOpacity;
	
	// Create an array of styles.
	var styledMap = new google.maps.StyledMapType(ussMapStyle, {name: ussMapName});
	
	var bounds = new google.maps.LatLngBounds();
	
	// Create an array of styles.
 	var mapoptions = {
 	          zoom: ussMapZoomLevel,
 	          maxZoom: 12,
 	          //center: new google.maps.LatLng(39.193299893443, -76.834862),
 	          mapTypeId: google.maps.MapTypeId.ROADMAP,
 	          mapTypeControl: true
 	        }; 	
 	
 	var map = new google.maps.Map(document.getElementById(ussBindObjID), mapoptions); 
 	map.mapTypes.set('map_style', styledMap);
 	map.setMapTypeId('map_style');	 
 	
 	var taxiData = new Array();
 	var nmi = null;
 	for(var i=0;i<NMIs.length; i++){
 		nmi = NMIs[i];
 		taxiData.push(new google.maps.LatLng(parseFloat(nmi.gmapLatitude), parseFloat(nmi.gmapLongitude)));
 		bounds.extend(new google.maps.LatLng(parseFloat(nmi.gmapLatitude), parseFloat(nmi.gmapLongitude)));
 	}
 	
 	pointArray = new google.maps.MVCArray(taxiData);

    heatmap = new google.maps.visualization.HeatmapLayer({
      data: pointArray
    });
 	
	if(moreThan1NMI == false) {
		map.setZoom(parseInt(ussMapZoomLevel));
		map.setCenter(bounds.getCenter());
	} else {
		map.fitBounds(bounds); //Sets the viewport to contain the given bounds.
	}
    
    heatmap.setMap(map);
    heatmap.setOptions({radius: heatmap.get('radius') ? null : ussHeatRadius});
    heatmap.setOptions({opacity: heatmap.get('opacity') ? null : ussHeatOpacity});
    heatmap.setOptions({gradient: heatmap.get('gradient') ? null : ussHeatGradient});
}
