/** 
 * 2015 ErrorController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.common;

import melfood.framework.Ctx;
import melfood.framework.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Role Management controller
 * 
 * @author Steven J.S Min
 * 
 */
@Controller
@RequestMapping("/framework/errormanager")
public class ErrorController extends BaseController {

	@RequestMapping("/error")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("tiles/error");
		
	    String queryString = request.getQueryString();
	    
	    if (queryString != null) {
	    	queryString =  queryString + "?";
	    } else {
	    }
	    mav.addObject("queryString",queryString);
	    

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		mav.addObject("errorTime",dt.format(new Date()).toString());

		mav.addObject("in_charge_of_person",
				Ctx.xmlConfig.getString("contact-info/default-customer-service/name")
						+ " | " + Ctx.xmlConfig.getString("contact-info/default-customer-service/phone")
						+ " | " + Ctx.xmlConfig.getString("contact-info/default-customer-service/email"));
		return mav;
	}

}
