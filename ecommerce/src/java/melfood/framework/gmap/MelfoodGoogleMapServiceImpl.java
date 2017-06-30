/**
 * 2015 MelfoodGoogleMapServiceImpl.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.gmap;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import melfood.framework.Ctx;
import melfood.framework.gmap.gson.dto.GMapResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author steven.min
 */
@Service
public class MelfoodGoogleMapServiceImpl implements MelfoodGoogleMapService {

    private static final Logger logger = LoggerFactory.getLogger(MelfoodGoogleMapServiceImpl.class);
    private static final String googleMapApiKey = Ctx.getVar("GOOGLE.GEO.API.KEY");
    private static final String googleapisUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?";

    @Override
    public GeocodingResult lookupGMap(String address) throws Exception {

        GeoApiContext context = new GeoApiContext().setApiKey(googleMapApiKey);
        GeocodingResult[] results = null;

        logger.info("Lookup ADDR : " + address);

        results = GeocodingApi.geocode(context, address).await();

        if (results != null && results.length > 0) {
            return results[0];
        } else {
            return null;
        }

    }

    @Override
    public List<GeocodingResult> lookupGMap(List<String> addresses) throws Exception {

        GeoApiContext context = new GeoApiContext().setApiKey(googleMapApiKey);
        GeocodingResult[] results = null;

        List<GeocodingResult> list = new ArrayList<GeocodingResult>();

        for (String address : addresses) {
            logger.info("Lookup ADDR : " + address);
            results = GeocodingApi.geocode(context, address).await();

            if (results != null && results.length > 0) {
                list.add(results[0]);
            }
        }

        return list;
    }

    /**
     * 두 지점사이의 거리를 얻어온다
     *
     * @param originAddress
     * @param destinationAddress
     * @return
     * @throws Exception
     */
    @Override
    public String getLookupGmapDistance(String originAddress, String destinationAddress) throws Exception {
        return this.getLookupGmapDistance(originAddress, destinationAddress, false);
    }

    /**
     * 두 지점사이의 거리를 얻어온다
     *
     * @param originAddress
     * @param destinationAddress
     * @param includeToll
     * @return
     * @throws Exception
     */
    @Override
    public String getLookupGmapDistance(String originAddress, String destinationAddress, boolean includeToll) throws Exception {

        // Reference - https://developers.google.com/maps/documentation/distancematrix/

        StringBuffer options = new StringBuffer();

        String orgAddr[] = StringUtils.split(originAddress);
        options.append("origins=");
        for (int i = 0; i < orgAddr.length; i++) {
            if (i < (orgAddr.length - 1)) {
                options.append(orgAddr[i] + "+");
            } else {
                options.append(orgAddr[i]);
            }
        }

        String destAddr[] = StringUtils.split(destinationAddress);
        options.append("&destinations=");
        for (int i = 0; i < destAddr.length; i++) {
            if (i < (destAddr.length - 1)) {
                options.append(destAddr[i] + "+");
            } else {
                options.append(destAddr[i]);
            }
        }

        if (!includeToll) options.append("&avoid=tolls");

        URL url = new URL(googleapisUrl + options.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }

        GMapResult mapLookupResult = new Gson().fromJson(outputString, GMapResult.class);

        // Ref: https://developers.google.com/maps/documentation/distance-matrix/
        //Top-level Status Codes : ["NOT_FOUND" | "OK" | "INVALID_REQUEST" | "MAX_ELEMENTS_EXCEEDED" | "OVER_QUERY_LIMIT" | "REQUEST_DENIED" | "UNKNOWN_ERROR" ]
        //Element level status code : ["NOT_FOUND" | "OK" | "ZERO_RESULTS" | "MAX_ROUTE_LENGTH_EXCEEDED" ]
        logger.info("Element Status : " + mapLookupResult.getRows()[0].getElements()[0].getStatus());


        logger.info("Distance : " + mapLookupResult.getRows()[0].getElements()[0].getDistance().getText());
        logger.info("Duration : " + mapLookupResult.getRows()[0].getElements()[0].getDuration().getText());


        // float distance = Float.parseFloat(mapLookupResult.getRows()[0].getElements()[0].getDistance().getValue()) / 1000;
        String distance = mapLookupResult.getRows()[0].getElements()[0].getDistance().getText();

        return distance;
    }

}
