package melfood.framework.postcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/framework/postcodemanager")
public class PostCodeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PostCodeController.class);

	@Autowired
	private PostcodeService postcodeService;

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/postcodemanager/main");

		Properties htmlProperty = new Properties();

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE");
		htmlProperty = new Properties("state");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/getPostcodes")
	@ResponseBody
	public Map<String, Object> getPostcodes(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Postcode postcode = new Postcode();

		// For Pagination
		postcode.setPagenationPage(getPage(request));
		postcode.setPagenationPageSize(getPageSize(request));

		String state = request.getParameter("state");
		String suburb = request.getParameter("suburb");
		String zipCode = request.getParameter("postcode");

		if (StringUtils.isNotBlank(state)) postcode.setState(state);
		if (StringUtils.isNotBlank(suburb)) postcode.setSuburb(suburb);
		if (StringUtils.isNotBlank(zipCode)) postcode.setPostcode(zipCode);

		Integer totalCount = 0;

		List<Postcode> postCodes = postcodeService.getPostcodes(postcode);
		totalCount = postcodeService.getTotalCntForPostcodes(postcode);

		model.put("totalCount", totalCount);
		model.put("list", postCodes);

		return model;
	}

	@RequestMapping("/registPostcodeForm")
	public ModelAndView registPostcodeForm() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/postcodemanager/regist");

		Properties htmlProperty = new Properties();

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE");
		htmlProperty = new Properties("state");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/modifyPostcodeForm")
	public ModelAndView modifyPostcodeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/postcodemanager/modify");

		String postcodeId = request.getParameter("postcodeId");
		if (StringUtils.isBlank(postcodeId)) {
			throw new Exception("[POSTCODE_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Postcode postcode = postcodeService.getPostcode(new Postcode(postcodeId));
		mav.addObject("postcode", postcode);

		Properties htmlProperty = new Properties();

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", postcode.getState());
		htmlProperty = new Properties("state");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/detailPostcodeForm")
	public ModelAndView detailPostcodeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/postcodemanager/detailInfo");

		String postcodeId = request.getParameter("postcodeId");

		if (StringUtils.isBlank(postcodeId)) {
			throw new Exception("[POSTCODE_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Postcode postcode = postcodeService.getPostcode(new Postcode(postcodeId));
		mav.addObject("postcode", postcode);

		return mav;
	}

	@RequestMapping(value = "/savePostcode", produces = "application/json")
	@ResponseBody
	public Map<String, Object> savePostcode(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String postcodeId = request.getParameter("postcodeId");
		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		Postcode postcode = new Postcode();
		postcode.setType("Delivery Area");
		postcode.setCreator(sessionUser.getUser().getUserId());

		String state = request.getParameter("state");
		String suburb = request.getParameter("suburb");
		String zipCode = request.getParameter("postcode");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		try {
			if (StringUtils.isNotBlank(state)) postcode.setState(state);
			if (StringUtils.isNotBlank(zipCode)) postcode.setPostcode(zipCode);
			if (StringUtils.isNotBlank(longitude)) postcode.setLongitude(Double.parseDouble(longitude));
			if (StringUtils.isNotBlank(latitude)) postcode.setLatitude(Double.parseDouble(latitude));

			if (StringUtils.isBlank(zipCode) || StringUtils.isBlank(suburb)) {
				throw new Exception("[우편번호 | Suburb]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				if (postcodeService.existPostcode(new Postcode(zipCode, suburb))) throw new Exception("추가하려는 우편번호/Suburb가 이미 존재합니다.");
				postcode.setSuburb(suburb);
				updateCnt = postcodeService.insertPostcode(postcode);

			} else {

				if (StringUtils.isBlank(postcodeId)) {
					throw new Exception("[POSTCODE_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				postcode.setPostcodeId(Integer.parseInt(postcodeId));
				if (!postcodeService.existPostcode(new Postcode(postcodeId))) throw new Exception("수정하려는 우편번호/Suburb가 존재하지 않습니다.");
				postcode.setSuburb(suburb);
				updateCnt = postcodeService.updatePostcode(postcode);
			}

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/postcodeDelete", produces = "application/json")
	@ResponseBody
	public Map<String, Object> postcodeDelete(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String postcodeId = request.getParameter("postcodeId");

		try {

			if (StringUtils.isBlank(postcodeId)) {
				throw new Exception("[POSTCODE_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			updateCnt = postcodeService.deletePostcode(new Postcode(postcodeId));

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");
		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

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

		Properties htmlProperty = new Properties();

		htmlProperty = new Properties("addressSuburb");
		htmlProperty.setCssClass("form-control");
		model.put("cbxAddressSuburb", postcodeService.generateCmbx(zipCode, htmlProperty));

		return model;
	}

}
