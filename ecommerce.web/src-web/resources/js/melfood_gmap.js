/****************************************************************
 * This JS file is for marking google map with address information.
 * Writen by Steven Min, 2015-12-09  
 */

/* DEFAULT */ var MAPSTYLE_UNSATURATED_BROWNS = [{"elementType":"geometry","stylers":[{"hue":"#ff4400"},{"saturation":-68},{"lightness":-4},{"gamma":0.72}]},{"featureType":"road","elementType":"labels.icon"},{"featureType":"landscape.man_made","elementType":"geometry","stylers":[{"hue":"#0077ff"},{"gamma":3.1}]},{"featureType":"water","stylers":[{"hue":"#00ccff"},{"gamma":0.44},{"saturation":-33}]},{"featureType":"poi.park","stylers":[{"hue":"#44ff00"},{"saturation":-23}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"hue":"#007fff"},{"gamma":0.77},{"saturation":65},{"lightness":99}]},{"featureType":"water","elementType":"labels.text.stroke","stylers":[{"gamma":0.11},{"weight":5.6},{"saturation":99},{"hue":"#0091ff"},{"lightness":-86}]},{"featureType":"transit.line","elementType":"geometry","stylers":[{"lightness":-48},{"hue":"#ff5e00"},{"gamma":1.2},{"saturation":-23}]},{"featureType":"transit","elementType":"labels.text.stroke","stylers":[{"saturation":-64},{"hue":"#ff9100"},{"lightness":16},{"gamma":0.47},{"weight":2.7}]}];
/* TYPE 1  */ var MAPSTYLE_NEUTRAL_BLUE = [{"featureType":"water","elementType":"geometry","stylers":[{"color":"#193341"}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"color":"#2c5a71"}]},{"featureType":"road","elementType":"geometry","stylers":[{"color":"#29768a"},{"lightness":-37}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#406d80"}]},{"featureType":"transit","elementType":"geometry","stylers":[{"color":"#406d80"}]},{"elementType":"labels.text.stroke","stylers":[{"visibility":"on"},{"color":"#3e606f"},{"weight":2},{"gamma":0.84}]},{"elementType":"labels.text.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"administrative","elementType":"geometry","stylers":[{"weight":0.6},{"color":"#1a3541"}]},{"elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#2c5a71"}]}];
/* TYPE 2  */ var MAPSTYLE_GOWALLA = [{"featureType":"administrative.land_parcel","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"landscape.man_made","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"labels","stylers":[{"visibility":"simplified"},{"lightness":20}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"hue":"#f49935"}]},{"featureType":"road.highway","elementType":"labels","stylers":[{"visibility":"simplified"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"hue":"#fad959"}]},{"featureType":"road.arterial","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"visibility":"simplified"}]},{"featureType":"road.local","elementType":"labels","stylers":[{"visibility":"simplified"}]},{"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"all","stylers":[{"hue":"#a1cdfc"},{"saturation":30},{"lightness":49}]}];
/* TYPE 3  */ var MAPSTYLE_UNSATURATED_MAPBOX = [{"featureType":"water","stylers":[{"saturation":43},{"lightness":-11},{"hue":"#0088ff"}]},{"featureType":"road","elementType":"geometry.fill","stylers":[{"hue":"#ff0000"},{"saturation":-100},{"lightness":99}]},{"featureType":"road","elementType":"geometry.stroke","stylers":[{"color":"#808080"},{"lightness":54}]},{"featureType":"landscape.man_made","elementType":"geometry.fill","stylers":[{"color":"#ece2d9"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#ccdca1"}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"color":"#767676"}]},{"featureType":"road","elementType":"labels.text.stroke","stylers":[{"color":"#ffffff"}]},{"featureType":"poi","stylers":[{"visibility":"off"}]},{"featureType":"landscape.natural","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#b8cb93"}]},{"featureType":"poi.park","stylers":[{"visibility":"on"}]},{"featureType":"poi.sports_complex","stylers":[{"visibility":"on"}]},{"featureType":"poi.medical","stylers":[{"visibility":"on"}]},{"featureType":"poi.business","stylers":[{"visibility":"simplified"}]}];
/* TYPE 4  */ var MAPSTYLE_MUTED_BLUE = [{"featureType":"all","stylers":[{"saturation":0},{"hue":"#e7ecf0"}]},{"featureType":"road","stylers":[{"saturation":-70}]},{"featureType":"transit","stylers":[{"visibility":"off"}]},{"featureType":"poi","stylers":[{"visibility":"off"}]},{"featureType":"water","stylers":[{"visibility":"simplified"},{"saturation":-60}]}];
/* TYPE 5  */ var MAPSTYLE_LIGHT_GRAY = [{"featureType":"water","elementType":"geometry.fill","stylers":[{"color":"#d3d3d3"}]},{"featureType":"transit","stylers":[{"color":"#808080"},{"visibility":"off"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"visibility":"on"},{"color":"#b3b3b3"}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"road.local","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#ffffff"},{"weight":1.8}]},{"featureType":"road.local","elementType":"geometry.stroke","stylers":[{"color":"#d7d7d7"}]},{"featureType":"poi","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#ebebeb"}]},{"featureType":"administrative","elementType":"geometry","stylers":[{"color":"#a7a7a7"}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"landscape","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#efefef"}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"color":"#696969"}]},{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"visibility":"on"},{"color":"#737373"}]},{"featureType":"poi","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","elementType":"geometry.stroke","stylers":[{"color":"#d6d6d6"}]},{"featureType":"road","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{},{"featureType":"poi","elementType":"geometry.fill","stylers":[{"color":"#dadada"}]}];
/* TYPE 6  */ var MAPSTYLE_BLUE_GRAY = [{"featureType":"water","stylers":[{"visibility":"on"},{"color":"#b5cbe4"}]},{"featureType":"landscape","stylers":[{"color":"#efefef"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#83a5b0"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#bdcdd3"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#e3eed3"}]},{"featureType":"administrative","stylers":[{"visibility":"on"},{"lightness":33}]},{"featureType":"road"},{"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":20}]},{},{"featureType":"road","stylers":[{"lightness":20}]}];
/* TYPE 7  */ var MAPSTYLE_BRIGHT_BUBBLY = [{"featureType":"water","stylers":[{"color":"#19a0d8"}]},{"featureType":"administrative","elementType":"labels.text.stroke","stylers":[{"color":"#ffffff"},{"weight":6}]},{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"color":"#e85113"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#efe9e4"},{"lightness":-40}]},{"featureType":"road.arterial","elementType":"geometry.stroke","stylers":[{"color":"#efe9e4"},{"lightness":-20}]},{"featureType":"road","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"lightness":-100}]},{"featureType":"road.highway","elementType":"labels.icon"},{"featureType":"landscape","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"landscape","stylers":[{"lightness":20},{"color":"#efe9e4"}]},{"featureType":"landscape.man_made","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"lightness":-100}]},{"featureType":"poi","elementType":"labels.text.fill","stylers":[{"hue":"#11ff00"}]},{"featureType":"poi","elementType":"labels.text.stroke","stylers":[{"lightness":100}]},{"featureType":"poi","elementType":"labels.icon","stylers":[{"hue":"#4cff00"},{"saturation":58}]},{"featureType":"poi","elementType":"geometry","stylers":[{"visibility":"on"},{"color":"#f0e4d3"}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#efe9e4"},{"lightness":-25}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#efe9e4"},{"lightness":-10}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"simplified"}]}];
/* TYPE 8  */ var MAPSTYLE_BENTLEY = [{"featureType":"landscape","stylers":[{"hue":"#F1FF00"},{"saturation":-27.4},{"lightness":9.4},{"gamma":1}]},{"featureType":"road.highway","stylers":[{"hue":"#0099FF"},{"saturation":-20},{"lightness":36.4},{"gamma":1}]},{"featureType":"road.arterial","stylers":[{"hue":"#00FF4F"},{"saturation":0},{"lightness":0},{"gamma":1}]},{"featureType":"road.local","stylers":[{"hue":"#FFB300"},{"saturation":-38},{"lightness":11.2},{"gamma":1}]},{"featureType":"water","stylers":[{"hue":"#00B6FF"},{"saturation":4.2},{"lightness":-63.4},{"gamma":1}]},{"featureType":"poi","stylers":[{"hue":"#9FFF00"},{"saturation":0},{"lightness":0},{"gamma":1}]}];
/* TYPE 9  */ var MAPSTYLE_PASTEL_TONES = [{"featureType":"landscape","stylers":[{"saturation":-100},{"lightness":60}]},{"featureType":"road.local","stylers":[{"saturation":-100},{"lightness":40},{"visibility":"on"}]},{"featureType":"transit","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"administrative.province","stylers":[{"visibility":"off"}]},{"featureType":"water","stylers":[{"visibility":"on"},{"lightness":30}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ef8c25"},{"lightness":40}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#b6c54c"},{"lightness":40},{"saturation":-40}]},{}];
/* TYPE 10 */ var MAPSTYLE_BLUE_GRAY = [{"featureType":"water","stylers":[{"visibility":"on"},{"color":"#b5cbe4"}]},{"featureType":"landscape","stylers":[{"color":"#efefef"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#83a5b0"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#bdcdd3"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#e3eed3"}]},{"featureType":"administrative","stylers":[{"visibility":"on"},{"lightness":33}]},{"featureType":"road"},{"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":20}]},{},{"featureType":"road","stylers":[{"lightness":20}]}];
/* TYPE 11 */ var MAPSTYLE_SUBTLE_GRAYSCALE = [{"featureType":"landscape","stylers":[{"saturation":-100},{"lightness":65},{"visibility":"on"}]},{"featureType":"poi","stylers":[{"saturation":-100},{"lightness":51},{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"road.arterial","stylers":[{"saturation":-100},{"lightness":30},{"visibility":"on"}]},{"featureType":"road.local","stylers":[{"saturation":-100},{"lightness":40},{"visibility":"on"}]},{"featureType":"transit","stylers":[{"saturation":-100},{"visibility":"simplified"}]},{"featureType":"administrative.province","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":-25},{"saturation":-100}]},{"featureType":"water","elementType":"geometry","stylers":[{"hue":"#ffff00"},{"lightness":-25},{"saturation":-97}]}];
/* TYPE 12 */ var MAPSTYLE_CLADME = [{"featureType":"administrative","elementType":"labels.text.fill","stylers":[{"color":"#444444"}]},{"featureType":"landscape","elementType":"all","stylers":[{"color":"#f2f2f2"}]},{"featureType":"poi","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"all","stylers":[{"saturation":-100},{"lightness":45}]},{"featureType":"road.highway","elementType":"all","stylers":[{"visibility":"simplified"}]},{"featureType":"road.arterial","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"transit","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"all","stylers":[{"color":"#4f595d"},{"visibility":"on"}]}];

var geocoder = new google.maps.Geocoder();
var prev_gmap_infowindow = false;
var map;	// Google map object
var bounds = new google.maps.LatLngBounds();
var prev_gmap_infowindow = false;


function getMapStyle(melGmap, mapType){
	var mapStyleNo = null;
	
	if(mapType == 'HEAT') mapStyle = MAPSTYLE_BENTLEY;


	if (typeof melGmap.mapStyleNo == 'undefined') {
        mapStyleNo = 7; // 기본 스타일을 MAPSTYLE_BRIGHT_BUBBLY 로 설정한다.
    } else {
        mapStyleNo = melGmap.mapStyleNo;
    }

	if(mapStyleNo == '1')  mapStyle  = MAPSTYLE_NEUTRAL_BLUE;
	if(mapStyleNo == '2')  mapStyle  = MAPSTYLE_GOWALLA;
	if(mapStyleNo == '3')  mapStyle  = MAPSTYLE_UNSATURATED_MAPBOX;
	if(mapStyleNo == '4')  mapStyle  = MAPSTYLE_MUTED_BLUE;
	if(mapStyleNo == '5')  mapStyle  = MAPSTYLE_LIGHT_GRAY;
	if(mapStyleNo == '6')  mapStyle  = MAPSTYLE_BLUE_GRAY;
	if(mapStyleNo == '7')  mapStyle  = MAPSTYLE_BRIGHT_BUBBLY;
	if(mapStyleNo == '8')  mapStyle  = MAPSTYLE_BENTLEY;
	if(mapStyleNo == '9')  mapStyle  = MAPSTYLE_PASTEL_TONES;
	if(mapStyleNo == '10') mapStyle  = MAPSTYLE_BLUE_GRAY;
	if(mapStyleNo == '11') mapStyle  = MAPSTYLE_SUBTLE_GRAYSCALE;
	if(mapStyleNo == '12') mapStyle  = MAPSTYLE_CLADME;
	
	return mapStyle;
}


function markAddressOnGMap(melGmap){
	
	var mapStyleNo = null;
	var mapName = "멜푸드";
	var mapBindObjID = "map-canvas";
	var mapZoomLevel = 11;
	var mapAddress = "Melbourne, VIC";
	var message = "";
	var mapIsMultipleMark = "no";
    var mapTypeId = "map_style"; // [roadmap | satellite | hybrid | terrain | map_style]

	if (typeof melGmap.mapName != 'undefined') mapName = melGmap.mapName;
	if (typeof melGmap.mapBindObjID != 'undefined') mapBindObjID = melGmap.mapBindObjID;
	if (typeof melGmap.mapZoomLevel != 'undefined') mapZoomLevel = parseInt(melGmap.mapZoomLevel);
	if (typeof melGmap.mapAddress != 'undefined') mapAddress = melGmap.mapAddress;
	if (typeof melGmap.message != 'undefined') message = melGmap.message;
	console.log("mapName:" + mapName);

	var styledMap = new google.maps.StyledMapType(getMapStyle(melGmap, 'SIMPLE'), {name: mapName});
	var latlng = new google.maps.LatLng( -37.818449, 145.124889 );	// Box Hill VIC
	
	// Map options for how to display the Google map
	var mapOptions = {
       zoom: mapZoomLevel,
       maxZoom: 20,
       center: latlng,
       //mapTypeId: google.maps.MapTypeId.ROADMAP,
       mapTypeControl: true,
	   scrollwheel: false,
        mapTypeControl: false,
     }; 	
	
	// Show the Google map in the div with the attribute id 'map-canvas'.
	map = new google.maps.Map(document.getElementById(mapBindObjID), mapOptions);

	markingByCoordinate(melGmap);

 	map.mapTypes.set('map_style', styledMap);
 	map.setMapTypeId(mapTypeId);



 	if(melGmap.mapLegend != null && melGmap.mapLegend != '' && (typeof melGmap.mapLegend != 'undefined')) {
 	    $('#legend').show();
        var legend = document.getElementById('legend');
        var legendIcons = melGmap.mapLegend;
        for (var key in legendIcons) {
            var type = legendIcons[key];
            var name = type.name;
            var icon = type.icon;
            var div = document.createElement('div');
            div.style.padding='3px';
            div.innerHTML = '<img src="' + icon + '" style="height:15px; width:15px;"> <span style="font-size:9px;color:#929497;">' + name + '</span>';
            legend.appendChild(div);
        }

        map.controls[google.maps.ControlPosition.LEFT_TOP].push(legend);
    }

}



function markingByCoordinate(melGmap) {
    var icon = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
    var defaultIco = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";

    var list    = melGmap.mapMultiplePoints;

    //console.log("JS melGmap.mapMultiplePoints :" + melGmap.mapMultiplePoints)
    //console.log("JS melGmap.mapZoomLevel :" + melGmap.mapZoomLevel)

    if(list != null && list.length > 0) {
        while((a=list.pop()) != null){

            // console.log("JS a.latitude :" + a.latitude)
            // console.log("JS a.longitude :" + a.longitude)

            if(a.iconUrl == '' || a.iconUrl == null  || a.iconUrl == undefined) {
                icon = defaultIco;
            } else {
                icon = a.iconUrl;
            }

            var position = new google.maps.LatLng( a.latitude, a.longitude );

            var marker = new google.maps.Marker({
                animation: google.maps.Animation.DROP
                , icon: icon
                , map: map
                , position: position
                , title: location[ 0 ]
            });

            infoWindow(marker, map, a.message);
            bounds.extend(marker.getPosition());
            map.fitBounds(bounds);

            if(a.clickEvent == true){
                google.maps.event.trigger(marker, "click", {});
            }
        }

        if(melGmap.mapZoomLevel != 'undefined' && melGmap.mapZoomLevel != '' ) {
            var zoomLevel = map.getZoom();
            var listener = google.maps.event.addListener(map, "idle", function() {
                map.setZoom(zoomLevel);
                google.maps.event.removeListener(listener);
            });
        }

    }

}

/**
 * Should be careful to call, it require to call Google webservice and could be over maximum limitation.
 *
 * @param melGmap
 */
function markingByAddress(melGmap) {
    var icon = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
    var defaultIco = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";

    var list    = melGmap.mapMultiplePoints;

    if(list != null && list.length > 0) {
        while((a=list.pop()) != null){

            if(a.iconUrl == '' || a.iconUrl == null  || a.iconUrl == undefined) {
                icon = defaultIco;
            } else {
                icon = a.iconUrl;
            }

            geocoder.geocode({
                    'address': a.address
                },
                function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        var marker = new google.maps.Marker({
                            icon: icon,
                            map: map,
                            position: results[0].geometry.location,
                            // title: title,
                            // animation: google.maps.Animation.DROP,
                            address: a.address
                        });
                        infoWindow(marker, map, a.message);
                        bounds.extend(marker.getPosition());
                        map.fitBounds(bounds);

                        if(clickEvent == true){
                            google.maps.event.trigger(marker, "click", {});
                        }
                    } else {
                        console.log("geocode of " + address + " failed:" + status);
                    }
                });


        } // End of While

    }
}





function markStreeViewByCoordinate(melGmap){

    var mapStyleNo = null;
    var mapName = "Coupang Map";
    var mapBindObjID = "map-street-canvas";
    var mapZoomLevel = 11;
    var mapAddress = "Melbourne, VIC";
    var mapStyleNo = getMapStyle(melGmap,'HEAT');
    var mapIsMultipleMark = "no";


    if (typeof melGmap.mapName != 'undefined') mapName = melGmap.mapName;
    if (typeof melGmap.mapBindObjID != 'undefined') mapBindObjID = melGmap.mapBindObjID;
    if (typeof melGmap.mapZoomLevel != 'undefined') mapZoomLevel = parseInt(melGmap.mapZoomLevel);

    var list    = melGmap.mapMultiplePoints;
    var point = list.pop();

    var mapOptions = {
        zoom: mapZoomLevel,
        maxZoom: 20,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: true
    };

    // var latlng = new google.maps.LatLng( point.latitude, point.longitude );	// Melbourne, VIC
    var latlng = new google.maps.LatLng( melGmap.mapLatitude, melGmap.mapLongitude );	// Melbourne, VIC

    var panorama = new google.maps.StreetViewPanorama(
        document.getElementById(mapBindObjID), {
            position: latlng,
            pov: {
                heading: 50,
                pitch: 10
            }
        });
    map.setStreetView(panorama);

}


/**
 * Should be careful to call, it require to call Google webservice and could be over maximum limitation.
 *
 * @param melGmap
 */
function markStreeViewByAddress(melGmap){
    var latitude = "-37.813556";
    var longitude = "144.963050";

    var mapStyleNo = null;
    var mapName = "Coupang Map";
    var mapBindObjID = "map-street-canvas";
    var mapZoomLevel = 11;
    var mapAddress = "Melbourne, VIC";
    var mapStyleNo = getMapStyle(melGmap,'HEAT');
    var message = "";
    var mapIsMultipleMark = "no";


    if (typeof melGmap.mapName != 'undefined') mapName = melGmap.mapName;
    if (typeof melGmap.mapBindObjID != 'undefined') mapBindObjID = melGmap.mapBindObjID;
    if (typeof melGmap.mapZoomLevel != 'undefined') mapZoomLevel = parseInt(melGmap.mapZoomLevel);
    if (typeof melGmap.mapAddress != 'undefined') mapAddress = melGmap.mapAddress;
    if (typeof melGmap.message != 'undefined') message = melGmap.message;
    if (typeof melGmap.mapIsMultipleMark != 'undefined') mapIsMultipleMark = melGmap.mapIsMultipleMark;

    geocoder.geocode( { 'address': mapAddress}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            latitude = results[0].geometry.location.lat();
            longitude = results[0].geometry.location.lng();

            var mapOptions = {
                zoom: mapZoomLevel,
                maxZoom: 20,
                center: latlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                mapTypeControl: true
            };

            var latlng = new google.maps.LatLng( latitude, longitude );	// Melbourne, VIC

            var panorama = new google.maps.StreetViewPanorama(
                document.getElementById(mapBindObjID), {
                    position: latlng,
                    pov: {
                        heading: 34,
                        pitch: 10
                    }
                });
            map.setStreetView(panorama);

        } else {
            alert(2);
        }
    });

}




function infoWindow(marker, map, message) {
    google.maps.event.addListener(marker, 'click', function () {
        var html = message;
        iw = new google.maps.InfoWindow({
            content: html,
            maxWidth: 350
        });

        if( prev_gmap_infowindow ) {
            prev_gmap_infowindow.close();
        }
        prev_gmap_infowindow = iw;
        iw.open(map, marker);
    });
}



function markPointsOnGHeatMap(mapPoints, melGmap){
    var pointarray, heatmap;

    var melFoodMapType = 'HEAT'; // SIMPLE | HEAT
    var melFoodMapStyle = MAPSTYLE_UNSATURATED_BROWNS;
    var melFoodMapStyleNo = null;
    var melFoodMapName = "Mel푸드 Map";
    var ussBindObjID = "map_canvas";
    var melFoodMapZoomLevel = 10;

    var moreThan1Point = true;
    if(mapPoints == null || mapPoints.length < 2) moreThan1Point = false;

    var ussHeatGradient = ['rgba(0, 255, 255, 0)', 'rgba(0, 255, 255, 1)','rgba(0, 191, 255, 1)','rgba(0, 127, 255, 1)','rgba(0, 63, 255, 1)','rgba(0, 0, 255, 1)','rgba(0, 0, 223, 1)','rgba(0, 0, 191, 1)','rgba(0, 0, 159, 1)','rgba(0, 0, 127, 1)','rgba(63, 0, 91, 1)','rgba(127, 0, 63, 1)','rgba(191, 0, 31, 1)','rgba(255, 0, 0, 1)'];
    var ussHeatOpacity = 1;
    var ussHeatRadius = 20;

    if (typeof melGmap.mapName != 'undefined') melFoodMapName = melGmap.mapName;
    if (typeof melGmap.mapBindObjID != 'undefined') ussBindObjID = melGmap.mapBindObjID;
    if (typeof melGmap.mapZoomLevel != 'undefined') melFoodMapZoomLevel = melGmap.mapZoomLevel;
    if (typeof melGmap.mapHeatRadius != 'undefined') ussHeatRadius = melGmap.mapHeatRadius;
    if (typeof melGmap.mapHeatOpacity != 'undefined') ussHeatOpacity = melGmap.mapHeatOpacity;

    // Create an array of styles.
    var styledMap = new google.maps.StyledMapType(melFoodMapStyle, {name: melFoodMapName});

    var bounds = new google.maps.LatLngBounds();

    // Create an array of styles.
    var mapoptions = {
        zoom: melFoodMapZoomLevel,
        maxZoom: 12,
        //center: new google.maps.LatLng(39.193299893443, -76.834862),
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: true
    };

    var map = new google.maps.Map(document.getElementById(ussBindObjID), mapoptions);
    map.mapTypes.set('map_style', styledMap);
    map.setMapTypeId('map_style');

    var taxiData = new Array();
    var mapPoint = null;
    for(var i=0;i<mapPoints.length; i++){
        mapPoint = mapPoints[i];
        taxiData.push(new google.maps.LatLng(parseFloat(mapPoint.marketGmapLatitude), parseFloat(mapPoint.marketGmapLongitude)));
        bounds.extend(new google.maps.LatLng(parseFloat(mapPoint.marketGmapLatitude), parseFloat(mapPoint.marketGmapLongitude)));
    }

    pointArray = new google.maps.MVCArray(taxiData);

    heatmap = new google.maps.visualization.HeatmapLayer({
        data: pointArray
    });

    if(moreThan1Point == false) {
        map.setZoom(parseInt(melFoodMapZoomLevel));
        map.setCenter(bounds.getCenter());
    } else {
        map.fitBounds(bounds); //Sets the viewport to contain the given bounds.
    }

    heatmap.setMap(map);
    heatmap.setOptions({radius: heatmap.get('radius') ? null : ussHeatRadius});
    heatmap.setOptions({opacity: heatmap.get('opacity') ? null : ussHeatOpacity});
    heatmap.setOptions({gradient: heatmap.get('gradient') ? null : ussHeatGradient});
}