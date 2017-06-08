package melfood.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import melfood.framework.postcode.PostcodeService;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller("commonPostCodeController")
@RequestMapping("/common/postcode")
public class PostCodeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PostCodeController.class);

	@Autowired
	private PostcodeService postcodeService;

	@RequestMapping(value = "/stateCmbx", produces = "application/json")
	@ResponseBody
	public Map<String, Object> stateCmbx(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String state = request.getParameter("state");

		Properties htmlProperty = new Properties();

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", state);
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		model.put("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		return model;
	}

	@RequestMapping(value = "/suburbCmbx", produces = "application/json")
	@ResponseBody
	public Map<String, Object> suburbCmbx(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String zipCode = request.getParameter("postcode");
		String objName = request.getParameter("objName");
		String retValue = "";

		List<Option> postcodes = postcodeService.getSuburbCmbxOptions(zipCode);
		if (postcodes != null && postcodes.size() > 0) {
			Properties htmlProperty = new Properties();
			htmlProperty = new Properties(objName);
			htmlProperty.setCssClass("form-control");
			retValue = postcodeService.generateCmbx(postcodes, htmlProperty, false);
		}
		model.put("objName", objName);
		model.put("objValue", retValue);

		return model;
	}

}
