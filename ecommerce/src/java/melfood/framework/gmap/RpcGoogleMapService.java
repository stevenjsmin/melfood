/** 
 * 2015 RpcGoogleMapService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.gmap;

import java.util.List;

import com.google.maps.model.GeocodingResult;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface RpcGoogleMapService {

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
}
