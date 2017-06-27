/** 
 * 2016 ProductOrderController.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import melfood.framework.system.BaseController;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/mallfront")
public class MallFrontController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MallFrontController.class);

	@Autowired
	GroupPurchaseService groupPurchaseService;

	@RequestMapping(value = "/getGroupPurchases", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getGroupPurchases(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		GroupPurchase groupPurchase = new GroupPurchase();

		// For Pagination
		groupPurchase.setPagenationPage(getPage(request));
		groupPurchase.setPagenationPageSize(getPageSize(request));

		String searchDateFrom = request.getParameter("searchDateFrom");
		if (StringUtils.isBlank(searchDateFrom)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			searchDateFrom = df.format(cal.getTime());
		}
		groupPurchase.setSearchDateFrom(searchDateFrom);

		Integer totalCount = 0;
		List<GroupPurchase> list = null;
		list = groupPurchaseService.getGroupPurchaseForMallFront(groupPurchase);
		totalCount = groupPurchaseService.getTotalCntGroupPurchaseForMallFront(groupPurchase);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

}
