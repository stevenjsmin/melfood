/** 
 * 2015 SystemConfigController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.ArrayList;
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
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/framework/sysconfigmanager")
public class SystemConfigController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

	@Autowired
	private SystemConfigService systemConfigService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/sysconfigmanager/main");

		Properties htmlProperty = null;

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssStyle("width: 150px;");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> stageOptions = codeService.getValueCmbxOptions("SYS", "PROD_STAGE");
		htmlProperty = new Properties("stage_multiSelect");
		// htmlProperty.setCssClass("form-control");
		htmlProperty.setCssStyle("width: 350px;");
		htmlProperty.setMultipleSelect(true);
		mav.addObject("cbxStage", codeService.generateCmbx(stageOptions, htmlProperty, false));

		return mav;
	}

	@RequestMapping(value = "/systemConfigs", produces = "application/json")
	@ResponseBody
	public Map<String, Object> systemConfigs(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		SystemConfig sysConfig = new SystemConfig();

		// For Pagination
		sysConfig.setPagenationPage(getPage(request));
		sysConfig.setPagenationPageSize(getPageSize(request));

		String stage = request.getParameter("stage");
		String[] stageArray = StringUtils.split(stage, ",");
		List<String> stages = new ArrayList<String>();
		for (String aStage : stageArray) {
			stages.add(aStage);
		}
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		String description = request.getParameter("description");
		String useYn = request.getParameter("useYn");
		// if (StringUtils.isNotBlank(stage)) sysConfig.setStage(stage);
		if (stages.size() > 0) sysConfig.setStages(stages);
		if (StringUtils.isNotBlank(key)) sysConfig.setKey(key);
		if (StringUtils.isNotBlank(value)) sysConfig.setValue(value);
		if (StringUtils.isNotBlank(description)) sysConfig.setDescription(description);
		if (StringUtils.isNotBlank(useYn)) sysConfig.setUseYn(useYn);

		List<SystemConfig> list = systemConfigService.getSystemConfigs(sysConfig);

		Integer totalCount = 0;
		totalCount = systemConfigService.getTotalCntForSystemConfigs(sysConfig);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/detailSystemConfigForm")
	public ModelAndView detailSystemConfigForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/sysconfigmanager/detailInfo");

		String stage = request.getParameter("stage");
		String key = request.getParameter("key");

		if (StringUtils.isBlank(key)) {
			throw new Exception("[키값]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		SystemConfig sysConfig = systemConfigService.getSystemConfig(new SystemConfig(stage, key));
		mav.addObject("sysConfig", sysConfig);

		return mav;
	}

	@RequestMapping("/registSystemConfigForm")
	public ModelAndView registSystemConfigForm() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/sysconfigmanager/regist");

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		Properties htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setCssStyle("width: 150px;");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> stageOptions = codeService.getValueCmbxOptions("SYS", "PROD_STAGE");
		htmlProperty = new Properties("stage_multiSelect");
		// htmlProperty.setCssClass("form-control");
		mav.addObject("cbxStage", codeService.generateCmbx(stageOptions, htmlProperty, false));

		return mav;
	}

	@RequestMapping("/modifySystemConfigForm")
	public ModelAndView modifySystemConfigForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/sysconfigmanager/modify");

		String stage = request.getParameter("stage");
		String key = request.getParameter("key");

		if (StringUtils.isBlank(key)) {
			throw new Exception("[키값]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		SystemConfig sysConfig = systemConfigService.getSystemConfig(new SystemConfig(stage, key));

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN", sysConfig.getUseYn());
		Properties htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setCssStyle("width: 150px;");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> stageOptions = codeService.getValueCmbxOptions("SYS", "PROD_STAGE", sysConfig.getStage());
		htmlProperty = new Properties("stage");
		// htmlProperty.setCssClass("form-control");
		mav.addObject("cbxStage", codeService.generateCmbx(stageOptions, htmlProperty, false));

		mav.addObject("sysConfig", sysConfig);

		return mav;
	}

	@RequestMapping(value = "/saveSystemConfig", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveSystemConfig(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String paramStage = request.getParameter("stage");
		String stages[] = StringUtils.split(paramStage, ",");

		String key = request.getParameter("key");
		String value = request.getParameter("value");
		String description = request.getParameter("description");
		String useYn = request.getParameter("useYn");
		String creator = sessionUser.getUser().getUserId();

		try {

			for (String stage : stages) {

				if (StringUtils.isBlank(stage) || StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
					throw new Exception("[(Dev | Testing | Production) | 키 | 키 값]  이항목(들)은 빈 값이 될 수 없습니다.");
				}

				SystemConfig sysConfig = new SystemConfig(stage, key, value);
				if (StringUtils.isNotBlank(useYn)) sysConfig.setUseYn(useYn);
				if (StringUtils.isNotBlank(description)) sysConfig.setDescription(description);
				if (StringUtils.isNotBlank(creator)) sysConfig.setCreator(creator);

				if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
					if (systemConfigService.existSysConfig(stage, key)) throw new Exception("추가하려는 정보가 이미 존재합니다.");
					updateCnt = systemConfigService.insertSystemConfig(sysConfig);

				} else {
					if (!systemConfigService.existSysConfig(stage, key)) throw new Exception("수정하려는 시스템 환경 정보가 존재하지 않습니다.");
					updateCnt = systemConfigService.modifySystemConfig(sysConfig);
				}

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

	@RequestMapping(value = "/systemConfigDelete", produces = "application/json")
	@ResponseBody
	public Map<String, Object> systemConfigDelete(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String stage = request.getParameter("stage");
		String key = request.getParameter("key");

		try {

			if (StringUtils.isBlank(stage) || StringUtils.isBlank(key)) {
				throw new Exception("[ Stage | 키값]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			updateCnt = systemConfigService.deleteSystemConfig(new SystemConfig(stage, key));

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
