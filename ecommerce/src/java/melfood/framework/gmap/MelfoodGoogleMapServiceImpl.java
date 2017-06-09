/**
 * 2015 MelfoodGoogleMapServiceImpl.java
 * Created by steven.min
 *
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.gmap;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import melfood.framework.Ctx;

/**
 * @author steven.min
 *
 */
@Service
public class MelfoodGoogleMapServiceImpl implements MelfoodGoogleMapService {

	private static final Logger logger = LoggerFactory.getLogger(MelfoodGoogleMapServiceImpl.class);

	@Override
	public GeocodingResult lookupGMap(String address) throws Exception {
		String googleMapApiKey = Ctx.xmlConfig.getString("system-config/google-web-service/map-service/geo-api-key");
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

		String googleMapApiKey = Ctx.xmlConfig.getString("system-config/google-web-service/map-service/geo-api-key");

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

}
