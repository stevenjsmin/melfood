/** 
 * 2016 ProductMgtController.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import java.math.BigDecimal;
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
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductOptionService;
import melfood.shopping.product.ProductOptionValue;
import melfood.shopping.product.ProductService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/productmgt/option")
public class ProductOptionMgtController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ProductOptionMgtController.class);

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/main");
		String prodId = request.getParameter("prodId");

		Properties htmlProperty = new Properties();

		htmlProperty = new Properties("optionSeq");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxOptionSeq", productOptionService.generateCmbxForOption(htmlProperty, Integer.parseInt(prodId)));

		mav.addObject("prodId", prodId);

		return mav;
	}

	@RequestMapping(value = "/productOptions", produces = "application/json")
	@ResponseBody
	public Map<String, Object> productOptions(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String prodId = request.getParameter("prodId");

		ProductOptionValue productOption = new ProductOptionValue(prodId);

		// For Pagination
		productOption.setPagenationPage(0);
		productOption.setPagenationPageSize(99999);

		Integer totalCount = 0;
		List<ProductOptionValue> list = productOptionService.getProductOptions(productOption);
		totalCount = productOptionService.getTotalCntForProductOptions(productOption);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/registForm")
	public ModelAndView optionItemRegistForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/registForm");
		String prodId = request.getParameter("prodId");
		Properties htmlProperty = new Properties();

		List<Option> isMandatoryOptions = codeService.getValueCmbxOptions("PROD_MGT", "OPTION_MANDATORY", "N");
		htmlProperty = new Properties("isMandatory");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsMandatory", codeService.generateCmbx(isMandatoryOptions, htmlProperty));

		mav.addObject("prodId", prodId);
		return mav;
	}

	@RequestMapping("/modifyForm")
	public ModelAndView optionItemModifyForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/modifyForm");

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		if (StringUtils.isBlank(prodId) || StringUtils.isBlank(optionSeq)) throw new Exception("[제품ID | 제품 Seq]  이항목(들)은 빈 값이 될 수 없습니다.");

		ProductOptionValue productOption = productOptionService.getProductOption(new ProductOptionValue(prodId, optionSeq));

		Properties htmlProperty = new Properties();
		List<Option> isMandatoryOptions = codeService.getValueCmbxOptions("PROD_MGT", "OPTION_MANDATORY", productOption.getIsMandatory());
		htmlProperty = new Properties("isMandatory");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsMandatory", codeService.generateCmbx(isMandatoryOptions, htmlProperty));

		mav.addObject("productOption", productOption);

		return mav;
	}

	@RequestMapping(value = "/saveProductOption", produces = "application/json")
	@ResponseBody
	public Map<String, Object> optionItemSaveProductOption(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		String optionItem = request.getParameter("optionItem");
		String isMandatory = request.getParameter("isMandatory");

		try {

			ProductOptionValue productOption = new ProductOptionValue(prodId);

			if (StringUtils.isNotBlank(optionSeq)) productOption.setOptionSeq(Integer.parseInt(optionSeq));
			if (StringUtils.isNotBlank(optionItem)) productOption.setOptionItem(optionItem);
			if (StringUtils.isNotBlank(isMandatory)) {
				productOption.setIsMandatory(isMandatory);
			} else {
				productOption.setIsMandatory("N");
			}

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				productOption.setCreator(sessionUser.getUser().getUserId());
				updateCnt = productOptionService.insertProductOption(productOption);

			} else {
				if (StringUtils.isBlank(optionSeq)) throw new Exception("[제품 Sequecence]  이항목(들)은 빈 값이 될 수 없습니다.");

				if (!productOptionService.existProductOption(new ProductOptionValue(prodId, optionSeq))) {
					throw new Exception("수정하려는 [옵션정보]가 존재 하지 않습니다.");
				}
				updateCnt = productOptionService.modifyProductOption(productOption);
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

	@RequestMapping(value = "/deleteProductOption", produces = "application/json")
	@ResponseBody
	public Map<String, Object> optionItemDeleteProductOption(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");

		try {
			if (StringUtils.isBlank(optionSeq)) {
				updateCnt = productOptionService.deleteProductOption(new ProductOptionValue(prodId));
			} else {
				updateCnt = productOptionService.deleteProductOption(new ProductOptionValue(prodId, optionSeq));
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

	@RequestMapping("/optionValue/Main")
	public ModelAndView optionValueMain(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/optionValue/main");
		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		ProductOptionValue productOption = productOptionService.getProductOption(new ProductOptionValue(prodId, optionSeq));

		mav.addObject("product", productService.getProduct(new Product(prodId)));
		mav.addObject("productOption", productOption);

		return mav;
	}

	@RequestMapping(value = "/optionValue/productOptionValues", produces = "application/json")
	@ResponseBody
	public Map<String, Object> productOptionValues(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		ProductOptionValue productOption = new ProductOptionValue();

		// For Pagination
		productOption.setPagenationPage(getPage(request));
		productOption.setPagenationPageSize(getPageSize(request));

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");

		if (StringUtils.isNotBlank(prodId)) productOption.setProdId(Integer.parseInt(prodId));
		if (StringUtils.isNotBlank(optionSeq)) productOption.setOptionSeq(Integer.parseInt(optionSeq));

		Integer totalCount = 0;
		List<ProductOptionValue> list = null;
		if (StringUtils.isNotBlank(prodId)) {
			list = productOptionService.getProductOptionValues(productOption);
			totalCount = productOptionService.getTotalCntForProductOptionValues(productOption);
		}

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/optionValue/registForm")
	public ModelAndView optionValueRegistForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/optionValue/registForm");
		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");

		Properties htmlProperty = new Properties();

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", "Y");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		mav.addObject("prodId", prodId);
		mav.addObject("optionSeq", optionSeq);

		ProductOptionValue productOption = productOptionService.getProductOption(new ProductOptionValue(prodId, optionSeq));

		mav.addObject("productOption", productOption);
		mav.addObject("product", productService.getProduct(new Product(prodId)));

		return mav;
	}

	@RequestMapping("/optionValue/modifyForm")
	public ModelAndView optionValueModifyForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/option/optionValue/modifyForm");

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		String optionValue = request.getParameter("optionValue");
		if (StringUtils.isBlank(prodId) || StringUtils.isBlank(optionSeq) || StringUtils.isBlank(optionValue)) throw new Exception("[제품ID | 제품 Seq | 옵션값]  이항목(들)은 빈 값이 될 수 없습니다.");

		ProductOptionValue productOption = productOptionService.getProductOptionValue(new ProductOptionValue(prodId, optionSeq, optionValue));

		Properties htmlProperty = new Properties();

		// List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE");
		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", productOption.getUseYn());
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		mav.addObject("productOption", productOption);

		mav.addObject("product", productService.getProduct(new Product(prodId)));

		return mav;
	}

	@RequestMapping(value = "/optionValue/saveProductOption", produces = "application/json")
	@ResponseBody
	public Map<String, Object> optionValueSaveProductOption(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		String valueSeq = request.getParameter("valueSeq");
		String valueLabel = request.getParameter("valueLabel");
		String useYn = request.getParameter("useYn");
		String inStockCnt = request.getParameter("inStockCnt");
		String unitPrice = request.getParameter("unitPrice");
		String extraCharge = request.getParameter("extraCharge");

		try {

			ProductOptionValue productOption = new ProductOptionValue(prodId, optionSeq);
			if (StringUtils.isNotBlank(valueSeq)) productOption.setValueSeq(Integer.parseInt(valueSeq));
			if (StringUtils.isNotBlank(valueLabel)) productOption.setValueLabel(valueLabel);
			if (StringUtils.isNotBlank(useYn)) productOption.setUseYn(useYn);
			if (StringUtils.isNotBlank(inStockCnt)) productOption.setInStockCnt(Integer.parseInt(inStockCnt));
			if (StringUtils.isNotBlank(unitPrice)) productOption.setUnitPrice(new BigDecimal(unitPrice));
			if (StringUtils.isNotBlank(extraCharge)) productOption.setExtraCharge(new BigDecimal(extraCharge));

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				productOption.setCreator(sessionUser.getUser().getUserId());
				updateCnt = productOptionService.insertProductOptionValue(productOption);

			} else {
				if (StringUtils.isBlank(optionSeq)) throw new Exception("[제품 Sequecence]  이항목(들)은 빈 값이 될 수 없습니다.");

				if (!productOptionService.existProductOptionValue(new ProductOptionValue(prodId, optionSeq, valueSeq))) {
					throw new Exception("수정하려는 [옵션정보]가 존재 하지 않습니다.");
				}
				updateCnt = productOptionService.modifyProductOptionValue(productOption);
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

	@RequestMapping(value = "/optionValue/deleteProductOptionValue", produces = "application/json")
	@ResponseBody
	public Map<String, Object> optionValueDeleteProductOption(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String prodId = request.getParameter("prodId");
		String optionSeq = request.getParameter("optionSeq");
		String valueSeq = request.getParameter("valueSeq");
		if (StringUtils.isBlank(prodId) || StringUtils.isBlank(optionSeq) || StringUtils.isBlank(valueSeq)) throw new Exception("[제품ID | 제품 Seq | 옵션값 순]  이항목(들)은 빈 값이 될 수 없습니다.");

		try {
			updateCnt = productOptionService.deleteProductOptionValue(new ProductOptionValue(prodId, optionSeq, valueSeq));

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

}
