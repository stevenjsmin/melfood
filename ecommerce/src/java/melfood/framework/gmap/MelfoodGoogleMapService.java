/**
 * 2015 MelfoodGoogleMapService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.gmap;

import com.google.maps.model.GeocodingResult;

import java.util.List;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 */
public interface MelfoodGoogleMapService {

    /**
     * Return lookup a result from google web services coresponding given addresse
     *
     * @param address
     * @return
     * @throws Exception
     */
    public GeocodingResult lookupGMap(String address) throws Exception;

    /**
     * Return lookup results from google web services coresponding given addresses
     *
     * @param addresses
     * @return
     * @throws Exception
     */
    public List<GeocodingResult> lookupGMap(List<String> addresses) throws Exception;


    /**
     * 두 지점사이의 거리를 얻어온다
     *
     * @param originAddress
     * @param destinationAddress
     * @return
     * @throws Exception
     */
    public int getLookupGmapDistance(String originAddress, String destinationAddress) throws Exception;

    /**
     * 두 지점사이의 거리를 얻어온다
     *
     * @param originAddress
     * @param destinationAddress
     * @param includeToll
     * @return
     * @throws Exception
     */
    public int getLookupGmapDistance(String originAddress, String destinationAddress, boolean includeToll) throws Exception;


}
