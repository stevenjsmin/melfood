/** 
 * 2015 CodeController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Controller for code management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/framework/codemanager")
public class CodeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/codemanager/main");

		Properties htmlProperty = new Properties();

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> categoryOptions = codeService.getCategoryCmbxOptions();
		htmlProperty = new Properties("category");
		htmlProperty.setOnchange("changeType(this.value);");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxCategory", codeService.generateCmbx(categoryOptions, htmlProperty));

		List<Option> typeOptions = codeService.getTypeCmbxOptions(null);
		htmlProperty = new Properties("type");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxTypes", codeService.generateCmbx(typeOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/categories", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCategoryCodeList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		List<Option> categoryOptions = codeService.getCategoryCmbxOptions();
		Properties htmlProperty = new Properties("category");
		htmlProperty.setCssClass("form-control");

		model.put("cbxCategory", codeService.generateCmbx(categoryOptions, htmlProperty));
		return model;
	}

	@RequestMapping(value = "/types", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getTypeCodeList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String category = request.getParameter("category");

		List<Option> typeOptions = codeService.getTypeCmbxOptions(category);
		Properties htmlProperty = new Properties("type");
		htmlProperty.setCssClass("form-control");

		model.put("cbxTypes", codeService.generateCmbx(typeOptions, htmlProperty));

		return model;
	}

	@RequestMapping(value = "/codes", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCodeList(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Code code = new Code();

		// For Pagination
		code.setPagenationPage(getPage(request));
		code.setPagenationPageSize(getPageSize(request));

		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String useYn = request.getParameter("useYn");
		String label = request.getParameter("label");
		String description = request.getParameter("description");
		if (StringUtils.isNotBlank(category)) code.setCategory(category);
		if (StringUtils.isNotBlank(type)) code.setType(type);
		if (StringUtils.isNotBlank(label)) code.setLabel(label);
		if (StringUtils.isNotBlank(useYn)) code.setUseYn(useYn);
		if (StringUtils.isNotBlank(description)) code.setDescription(description);

		List<Code> list = codeService.getCodes(code);

		Integer totalCount = 0;
		totalCount = codeService.getTotalCntForCodes(code);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/detailCodeForm")
	public ModelAndView detailCodeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/codemanager/detailInfo");

		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String value = request.getParameter("value");

		if (StringUtils.isBlank(category) || StringUtils.isBlank(type) || StringUtils.isBlank(value)) {
			throw new Exception("[대분류 | 중분류 | 코드값]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Code code = codeService.getCode(category, type, value);
		mav.addObject("useYn", codeService.getCode("COMM", "USE_YN", code.getUseYn()).getLabel());
		mav.addObject("code", code);

		return mav;
	}

	@RequestMapping("/registCodeForm")
	public ModelAndView registCodeForm() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/codemanager/regist");

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		Properties htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");

		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/modifyCodeForm")
	public ModelAndView modifyCodeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/codemanager/modify");

		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String value = request.getParameter("value");

		if (StringUtils.isBlank(category) || StringUtils.isBlank(type) || StringUtils.isBlank(value)) {
			throw new Exception("[대분류 | 중분류 | 코드값]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Code code = codeService.getCode(category, type, value);

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN", code.getUseYn());
		Properties htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");

		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));
		mav.addObject("code", code);

		return mav;
	}

	@RequestMapping(value = "/saveCode", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveCode(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		String label = request.getParameter("label");
		String description = request.getParameter("description");
		String useYn = request.getParameter("useYn");
		String displayOrder = request.getParameter("displayOrder");
		String creator = sessionUser.getUser().getUserId();

		try {

			if (StringUtils.isBlank(category) || StringUtils.isBlank(type) || StringUtils.isBlank(value) || StringUtils.isBlank(value) || StringUtils.isBlank(label)) {
				throw new Exception("[대분류 | 중분류 | 코드 | 코드값 | 코드표시]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			Code code = new Code(category, type, value, label);
			if (StringUtils.isNotBlank(useYn)) code.setUseYn(useYn);
			if (StringUtils.isNotBlank(description)) code.setDescription(description);
			if (StringUtils.isNotBlank(displayOrder)) code.setDisplayOrder(Integer.parseInt(displayOrder));
			if (StringUtils.isNotBlank(creator)) code.setCreator(creator);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				if (codeService.existCode(new Code(category, type, value))) throw new Exception("이미 기존 코드가 존재 합니다.");
				updateCnt = codeService.insertCode(code);

			} else {
				if (!codeService.existCode(new Code(category, type, value))) throw new Exception("수정하려는 코드정보가 존재하지 않습니다.");
				updateCnt = codeService.modifyCode(code);
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

	@RequestMapping(value = "/codeDelete", produces = "application/json")
	@ResponseBody
	public Map<String, Object> codeDelete(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String value = request.getParameter("value");

		try {

			if (StringUtils.isBlank(category) || StringUtils.isBlank(type) || StringUtils.isBlank(value)) {
				throw new Exception("[대분류 | 중분류 | 코드값]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			updateCnt = codeService.deleteCode(new Code(category, type, value));

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

}
