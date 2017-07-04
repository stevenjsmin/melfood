/** 
 * 2015 RoleController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import melfood.framework.role.Role;
import melfood.framework.system.BaseController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Role Management controller
 * 
 * @author Steven J.S Min
 * 
 */
@Controller
@RequestMapping("/admin/role")
public class RoleMgtController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleMgtController.class);

	@RequestMapping("/Main")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("tiles/framework/role/main");
		return mav;
	}

	@RequestMapping(value = "/RoleList", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getRoleList(HttpServletRequest request) throws Exception {

		HashMap<String, String> param = new HashMap<String, String>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<Role> list = null;

		String name = request.getParameter("name");
		if (!StringUtils.isEmpty(name)) {
			param.put("name", "%" + name + "%");
		}

		list = roleService.getRoleList(param);

		model.put("resultList", list);

		return model;
	}

	@RequestMapping(value = "/RoleRegist", produces = "application/json")
	@ResponseBody
	public Map<String, Object> roleRegist(HttpServletRequest request) throws Exception {

		HashMap<String, String> param = new HashMap<String, String>();
		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String useYn = request.getParameter("useYn");

		// param.put("roleId", new Integer(roleId).toString());
		param.put("name", name);
		param.put("description", description);
		param.put("useYn", useYn);

		updateCnt = roleService.insertRoleInfo(param);

		model.put("message", updateCnt);

		return model;
	}

	@RequestMapping(value = "/ExistRoleInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> existRoleInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String name = request.getParameter("name");

		model.put("message", roleService.existRole(name));

		return model;
	}

	@RequestMapping(value = "/RoleModify", produces = "application/json")
	@ResponseBody
	public Map<String, Object> modifyRoleInfo(HttpServletRequest request) throws Exception {
		HashMap<String, String> param = new HashMap<String, String>();
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String roleId = request.getParameter("roleId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String useYn = request.getParameter("useYn");

		param.put("roleId", roleId);
		param.put("name", name);
		param.put("description", description);
		param.put("useYn", useYn);

		updateCnt = roleService.modifyRoleInfo(param);

		model.put("message", updateCnt);

		return model;
	}

	@RequestMapping(value = "/RoleDelete", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteRoleInfo(HttpServletRequest request) throws Exception, NoSuchMethodException {
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String roleId = request.getParameter("roleId");

		updateCnt = roleService.deleteRoleInfo(roleId);

		model.put("message", updateCnt);

		return model;
	}

	@RequestMapping("/RoleServiceMapping")
	public ModelAndView roleServiceMapping(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("framework/role/serviceMapping");
		Role roleDto = new Role();

		String roleId = request.getParameter("roleId");
		roleDto = roleService.getRoleInfo(roleId);

		mav.addObject("roleDto", roleDto);

		return mav;

	}

	@RequestMapping(value = "/RoleServiceMappingApply", produces = "application/json")
	@ResponseBody
	public Map<String, Object> applyMapping(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String jsonParam = request.getParameter("jsonParam");
		logger.info(jsonParam);

		Map<String, Object> classMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(jsonParam);

		Map<String, Object> jsonModel = (Map<String, Object>) JSONObject.toBean(jsonObject, HashMap.class, classMap);
		ArrayList<Object> services = (ArrayList<Object>) jsonModel.get("list");

		updateCnt = roleService.applyMappingWithService(services);

		model.put("message", updateCnt);

		return model;
	}

	@RequestMapping(value = "/RoleServiceMappingRemove", produces = "application/json")
	@ResponseBody
	public Map<String, Object> applyMappingRemove(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String jsonParam = request.getParameter("jsonParam");
		logger.info(jsonParam);

		Map<String, Object> classMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(jsonParam);

		Map<String, Object> jsonModel = (Map<String, Object>) JSONObject.toBean(jsonObject, HashMap.class, classMap);
		ArrayList<Object> services = (ArrayList<Object>) jsonModel.get("list");
		String roleId = (String) jsonModel.get("roleId");

		updateCnt = roleService.removeRoleMappingWithService(roleId, services);

		model.put("message", updateCnt);

		return model;
	}

	@RequestMapping(value = "/ServiceListWithNotMapped", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getServiceListWithNotMapped(HttpServletRequest request) throws Exception {

		// TODO
		return null;
	}

	@RequestMapping(value = "/ServiceListWithMapped", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getServiceListWithMapped(HttpServletRequest request) throws Exception {

		// TODO
		return null;

	}

}
