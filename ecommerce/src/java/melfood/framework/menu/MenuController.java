/** 
 * 2016 RpcSalesPartnerController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/common/menumanager")
public class MenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/menu/admin/main");
		return mav;
	}

	@RequestMapping(value = "/MenuList", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMenuList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Menu menu = new Menu();

		// For Pagination
		menu.setPagenationPage(getPage(request));
		menu.setPagenationPageSize(getPageSize(request));

		String useYn = request.getParameter("useYn");
		String name = request.getParameter("name");

		if (StringUtils.isNotBlank(useYn)) menu.setUseYn(useYn);
		if (StringUtils.isNotBlank(name)) menu.setName(name);

		List<Menu> menuList = menuService.getMenuList(menu);

		Integer totalCount = 0;
		totalCount = menuService.getTotalCntForMenuList(menu);
		
		model.put("totalCount", totalCount);
		model.put("list", menuList);

		return model;
	}

	@RequestMapping("/MenuDetailInfo")
	public ModelAndView menuDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/menu/admin/detailInfo");

		String menuId = request.getParameter("menuId");
		if (StringUtils.isBlank(menuId)) throw new Exception("Can not be null : menuId");

		Menu menu = menuService.getMenuInfo(Integer.parseInt(menuId));
		String allowedRoles = menu.getAllowedRoles();
		List<String> allowedOptions = new ArrayList<String>();
		if (StringUtils.isNotBlank(allowedRoles)) {
			String temp[] = StringUtils.split(allowedRoles, ",");
			for (String var : temp) {
				allowedOptions.add("'" + var + "'");
			}
		}
		mav.addObject("allowedOptions", allowedOptions);
		mav.addObject("menu", menu);

		return mav;
	}

	@RequestMapping("/menuRegistForm")
	public ModelAndView salsesPartnerRegistForm() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/menu/admin/registForm");
		return mav;
	}

	@RequestMapping(value = "/MenuRegist", produces = "application/json")
	@ResponseBody
	public Map<String, Object> menuRegist(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		Menu menu = new Menu();

		String seq = request.getParameter("seq");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String allowedRoles = request.getParameter("allowedRoles");
		String allowedUsers = request.getParameter("allowedUsers");
		String htmlObjId = request.getParameter("htmlObjId");
		String htmlHref = request.getParameter("htmlHref");
		String htmlOnclick = request.getParameter("htmlOnclick");
		String htmlClass = request.getParameter("htmlClass");
		String htmlCss = request.getParameter("htmlCss");
		String useYn = request.getParameter("useYn");
		String comment = request.getParameter("comment");
		String creator = sessionUser.getUser().getUserId();

		if (StringUtils.isNotBlank(seq)) {
			menu.setSeq(Integer.parseInt(seq));
		} else {
			menu.setSeq(0);
		}
		if (StringUtils.isNotBlank(name)) {
			menu.setName(name);
		}
		if (StringUtils.isNotBlank(type)) {
			menu.setType(type);
		} else {
			menu.setType("ONCLICK");
		}
		if (StringUtils.isNotBlank(allowedRoles)) {
			menu.setAllowedRoles(allowedRoles);
		}
		if (StringUtils.isNotBlank(allowedUsers)) {
			menu.setAllowedUsers(allowedUsers);
		}
		if (StringUtils.isNotBlank(htmlObjId)) {
			menu.setHtmlObjId(htmlObjId);
		}
		if (StringUtils.isNotBlank(htmlHref)) {
			menu.setHtmlHref(htmlHref);
		}
		if (StringUtils.isNotBlank(htmlOnclick)) {
			menu.setHtmlOnclick(htmlOnclick);
		}
		if (StringUtils.isNotBlank(htmlClass)) {
			menu.setHtmlClass(htmlClass);
		}
		if (StringUtils.isNotBlank(htmlCss)) {
			menu.setHtmlCss(htmlCss);
		}
		if (StringUtils.isNotBlank(useYn)) {
			menu.setUseYn(useYn);
		}
		if (StringUtils.isNotBlank(comment)) {
			menu.setComment(comment);
		}
		if (StringUtils.isNotBlank(creator)) {
			menu.setCreator(creator);
		}

		if (StringUtils.isBlank(name) || StringUtils.isBlank(type)) {
			throw new Exception("Plese check again mandatory fields.");
		}

		try {
			int updateCnt = 0;
			updateCnt = menuService.registMenu(menu);

			model.put("resultCode", "0");
			model.put("message", updateCnt + "record inserted.");
		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/MenuUpdate", produces = "application/json")
	@ResponseBody
	public Map<String, Object> menuUpdate(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Menu menu = new Menu();

		String menuId = request.getParameter("menuId");
		if (StringUtils.isBlank(menuId)) {
			throw new Exception("Can not be null for update : menuId");
		}

		String seq = request.getParameter("seq");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String allowedRoles = request.getParameter("allowedRoles");
		String allowedUsers = request.getParameter("allowedUsers");
		String htmlObjId = request.getParameter("htmlObjId");
		String htmlHref = request.getParameter("htmlHref");
		String htmlOnclick = request.getParameter("htmlOnclick");
		String htmlClass = request.getParameter("htmlClass");
		String htmlCss = request.getParameter("htmlCss");
		String useYn = request.getParameter("useYn");
		String comment = request.getParameter("comment");

		menu.setMenuId(Integer.parseInt(menuId));
		if (StringUtils.isNotBlank(seq)) {
			menu.setSeq(Integer.parseInt(seq));
		} else {
			menu.setSeq(0);
		}
		if (StringUtils.isNotBlank(name)) {
			menu.setName(name);
		}
		if (StringUtils.isNotBlank(type)) {
			menu.setType(type);
		} else {
			menu.setType("ONCLICK");
		}
		if (StringUtils.isNotBlank(allowedRoles)) {
			menu.setAllowedRoles(allowedRoles);
		}
		if (StringUtils.isNotBlank(allowedUsers)) {
			menu.setAllowedUsers(allowedUsers);
		}
		if (StringUtils.isNotBlank(htmlObjId)) {
			menu.setHtmlObjId(htmlObjId);
		}
		if (StringUtils.isNotBlank(htmlHref)) {
			menu.setHtmlHref(htmlHref);
		}
		if (StringUtils.isNotBlank(htmlOnclick)) {
			menu.setHtmlOnclick(htmlOnclick);
		}
		if (StringUtils.isNotBlank(htmlClass)) {
			menu.setHtmlClass(htmlClass);
		}
		if (StringUtils.isNotBlank(htmlCss)) {
			menu.setHtmlCss(htmlCss);
		}
		if (StringUtils.isNotBlank(useYn)) {
			menu.setUseYn(useYn);
		}
		if (StringUtils.isNotBlank(comment)) {
			menu.setComment(comment);
		}

		if (StringUtils.isBlank(name) || StringUtils.isBlank(type)) {
			throw new Exception("Plese check again mandatory fields.");
		}

		try {
			int updateCnt = 0;
			updateCnt = menuService.modifyMenu(menu);

			model.put("resultCode", "0");
			model.put("message", updateCnt + " record updated.");
		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/DeleteMenu", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteMenu(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String menuId = request.getParameter("menuId");
		if (StringUtils.isBlank(menuId)) {
			throw new Exception("Can not be null for delete : menuId");
		}

		try {
			int updateCnt = 0;
			updateCnt = menuService.deleteMenu(Integer.parseInt(menuId));

			model.put("resultCode", "0");
			model.put("message", updateCnt + " record deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/AuthorizedMenuList", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMenuAuthorizedList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		List<Menu> retMenuList = menuService.getMenuAuthorizedList(sessionUser);

		model.put("list", retMenuList);

		return model;
	}

}
