/** 
 * 2016 CategoryMgtController.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

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
import melfood.shopping.product.Category;
import melfood.shopping.product.CategoryService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/categorymgt")
public class CategoryMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryMgtController.class);

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/categorymgt/main");
		return mav;
	}

	@RequestMapping(value = "/categories", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCategories(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Category category = new Category();

		// For Pagination
		category.setPagenationPage(getPage(request));
		category.setPagenationPageSize(getPageSize(request));

		String categoryName = request.getParameter("categoryName");
		String categoryDescription = request.getParameter("categoryDescription");

		if (StringUtils.isNotBlank(categoryName)) category.setCategoryName(categoryName);
		if (StringUtils.isNotBlank(categoryDescription)) category.setCategoryDescription(categoryDescription);

		Integer totalCount = 0;
		List<Category> list = categoryService.getCategories(category);
		totalCount = categoryService.getTotalCntForCategories(category);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/registCategoryForm")
	public ModelAndView registCategoryForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/categorymgt/regist");
		return mav;
	}

	@RequestMapping("/modifyCategoryForm")
	public ModelAndView modifyCategoryForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/categorymgt/modify");

		String categoryId = request.getParameter("categoryId");

		if (StringUtils.isBlank(categoryId)) {
			throw new Exception("[카테고리ID] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Category category = categoryService.getCategory(new Category(categoryId));

		mav.addObject("category", category);

		return mav;
	}

	@RequestMapping("/detailCategoryForm")
	public ModelAndView detailCategoryForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/categorymgt/detailInfo");

		String categoryId = request.getParameter("categoryId");

		if (StringUtils.isBlank(categoryId)) {
			throw new Exception("[카테고리ID] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Category category = categoryService.getCategory(new Category(categoryId));
		
		mav.addObject("category", category);

		return mav;
	}
	@RequestMapping(value = "/saveCategory", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveContractInfo(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String categoryId = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");
		String categoryDescription = request.getParameter("categoryDescription");

		try {

			Category category = new Category();

			if (StringUtils.isNotBlank(categoryName)) category.setCategoryName(categoryName);
			if (StringUtils.isNotBlank(categoryDescription)) category.setCategoryDescription(categoryDescription);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				// 추가
				category.setCreator(sessionUser.getUser().getUserId());
				updateCnt = categoryService.insertCategory(category);

			} else {
				// 수정
				if (StringUtils.isBlank(categoryId)) throw new Exception("[카테고리ID]  이항목(들)은 빈 값이 될 수 없습니다.");
				category.setCategoryId(Integer.parseInt(categoryId));
				
				if (!categoryService.existCategory(new Category(Integer.parseInt(categoryId)))) {
					throw new Exception("수정하려는 카테고리 정보가 존재하지 않습니다.");
				}
				updateCnt = categoryService.modifyCategory(category);
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

	@RequestMapping(value = "/deleteCategory", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteContractInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String categoryId = request.getParameter("categoryId");
		if (StringUtils.isBlank(categoryId)) throw new Exception("[카테고리ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		Category category = new Category(Integer.parseInt(categoryId));

		try {
			updateCnt = categoryService.deleteCategory(category);

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
