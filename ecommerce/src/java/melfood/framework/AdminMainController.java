/** 
 * 2016 AdminMainController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.system.BaseController;

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/framework/admin")
public class AdminMainController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminMainController.class);

	@RequestMapping("/Main")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("tiles/framework/admin/main");
		return mav;
	}

	@RequestMapping(value = "/CategoryCodeList", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCategoryCodeList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("message", null);
		return model;
	}

}
