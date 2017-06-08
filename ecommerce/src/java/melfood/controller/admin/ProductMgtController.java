/** 
 * 2016 ProductMgtController.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import melfood.framework.Ctx;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.contract.ContractInfoService;
import melfood.shopping.product.CategoryService;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductImage;
import melfood.shopping.product.ProductImageService;
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
@RequestMapping("/admin/productmgt")
public class ProductMgtController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ProductMgtController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ContractInfoService contractInfoService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/main");

		Properties htmlProperty = new Properties();

		List<Option> contractorOptions = contractInfoService.getAllSellers();
		htmlProperty = new Properties("seller");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		htmlProperty = new Properties("categoryId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxCategory", categoryService.generateCmbx(htmlProperty));

		List<Option> registerStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "REGISTER_STATUS");
		htmlProperty = new Properties("registerStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxRegisterStatus", codeService.generateCmbx(registerStatusOptions, htmlProperty));

		List<Option> sellingStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "SELLING_STATUS");
		htmlProperty = new Properties("sellingStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellingStatus", codeService.generateCmbx(sellingStatusOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/products", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCategories(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Product product = new Product();

		// For Pagination
		product.setPagenationPage(getPage(request));
		product.setPagenationPageSize(getPageSize(request));

		String prodId = request.getParameter("prodId");
		String categoryId = request.getParameter("categoryId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String validateUntil = request.getParameter("validateUntil");
		String seller = request.getParameter("seller");
		String parentProdId = request.getParameter("parentProdId");
		String inStockCnt = request.getParameter("inStockCnt");
		String unitPrice = request.getParameter("unitPrice");
		String registerStatus = request.getParameter("registerStatus");
		String sellingStatus = request.getParameter("sellingStatus");
		String sellingCommissionType = request.getParameter("sellingCommissionType");
		String sellingCommissionRate = request.getParameter("sellingCommissionRate");
		String sellingCommissionFee = request.getParameter("sellingCommissionFee");

		if (StringUtils.isNotBlank(prodId)) product.setProdId(Integer.parseInt(prodId));
		if (StringUtils.isNotBlank(categoryId)) product.setCategoryId(Integer.parseInt(categoryId));
		if (StringUtils.isNotBlank(name)) product.setName(name);
		if (StringUtils.isNotBlank(description)) product.setDescription(description);
		if (StringUtils.isNotBlank(validateUntil)) product.setValidateUntil(validateUntil);
		if (StringUtils.isNotBlank(seller)) product.setSeller(seller);
		if (StringUtils.isNotBlank(inStockCnt)) product.setInStockCnt(Integer.parseInt(inStockCnt));
		if (StringUtils.isNotBlank(unitPrice)) product.setUnitPrice(BigDecimal.valueOf(Long.parseLong(unitPrice)));
		if (StringUtils.isNotBlank(registerStatus)) product.setRegisterStatus(registerStatus);
		if (StringUtils.isNotBlank(sellingStatus)) product.setSellingStatus(sellingStatus);
		if (StringUtils.isNotBlank(sellingCommissionType)) product.setSellingCommissionType(sellingCommissionType);
		if (StringUtils.isNotBlank(sellingCommissionRate)) product.setSellingCommissionRate(Float.parseFloat(sellingCommissionRate));
		if (StringUtils.isNotBlank(sellingCommissionFee)) product.setSellingCommissionFee(BigDecimal.valueOf(Long.parseLong(sellingCommissionFee)));

		Integer totalCount = 0;
		List<Product> list = productService.getProducts(product);
		totalCount = productService.getTotalCntForProducts(product);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/overviewProductInfo")
	public ModelAndView overviewProductInfo(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/overviewProductInfo");

		String prodId = request.getParameter("prodId");

		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");

		Product product = productService.getProduct(new Product(prodId));

		Properties htmlProperty = new Properties();
		mav.addObject("productOptions", productOptionService.generateCmbxForOptionAndValue(htmlProperty, Integer.parseInt(prodId)));
		mav.addObject("product", product);

		ProductImage productImage = new ProductImage(prodId);
		productImage.setPagenationPage(0);
		productImage.setPagenationPageSize(99999);
		List<ProductImage> productImages = productImageService.getProductImages(productImage);
		mav.addObject("productImages", productImages);

		return mav;
	}

	@RequestMapping("/registProductMasterInfoForm")
	public ModelAndView registProductMasterInfoForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/registProductMasterInfo");

		Properties htmlProperty = new Properties();

		htmlProperty = new Properties("categoryId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxCategory", categoryService.generateCmbx(htmlProperty));

		List<Option> contractorOptions = contractInfoService.getSellers(new User());
		htmlProperty = new Properties("seller");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> registerStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "REGISTER_STATUS", "REGISTRATION");
		htmlProperty = new Properties("registerStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxRegisterStatus", codeService.generateCmbx(registerStatusOptions, htmlProperty));

		List<Option> sellingStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "SELLING_STATUS");
		htmlProperty = new Properties("sellingStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellingStatus", codeService.generateCmbx(sellingStatusOptions, htmlProperty));

		List<Option> sellingCommissionTypeOptions = codeService.getValueCmbxOptions("PROD_MGT", "SELLING_COMMISSION_TYPE", "NONE");
		htmlProperty = new Properties("sellingCommissionType");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setOnchange("changeCommissionType(this)");
		mav.addObject("cbxSellingCommissionType", codeService.generateCmbx(sellingCommissionTypeOptions, htmlProperty));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, Ctx.getVarAsInt("DEFAULT.PRODUCT.REGIST.VALIDUNTIL.DATES"));
		String defaultValidateUntil = df.format(cal.getTime());
		mav.addObject("defaultValidateUntil", defaultValidateUntil);

		return mav;
	}

	@RequestMapping("/modifyProductMasterInfoForm")
	public ModelAndView modifyProductMasterInfoForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/modifyProductMasterInfo");

		Properties htmlProperty = new Properties();

		String prodId = request.getParameter("prodId");

		if (StringUtils.isBlank(prodId)) {
			throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Product product = productService.getProduct(new Product(prodId));

		htmlProperty = new Properties("categoryId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxCategory", categoryService.generateCmbx(htmlProperty, product.getCategoryId()));

		List<Option> contractorOptions = contractInfoService.getSellers(new User(), product.getSeller());
		htmlProperty = new Properties("seller");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> registerStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "REGISTER_STATUS", product.getRegisterStatus());
		htmlProperty = new Properties("registerStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxRegisterStatus", codeService.generateCmbx(registerStatusOptions, htmlProperty));

		List<Option> sellingStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "SELLING_STATUS", product.getSellingStatus());
		htmlProperty = new Properties("sellingStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellingStatus", codeService.generateCmbx(sellingStatusOptions, htmlProperty));

		List<Option> sellingCommissionTypeOptions = codeService.getValueCmbxOptions("PROD_MGT", "SELLING_COMMISSION_TYPE", product.getSellingCommissionType());
		htmlProperty = new Properties("sellingCommissionType");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setOnchange("changeCommissionType(this)");
		mav.addObject("cbxSellingCommissionType", codeService.generateCmbx(sellingCommissionTypeOptions, htmlProperty));

		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping(value = "/saveProductMasterInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveProduct(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		Product product = new Product();

		String prodId = request.getParameter("prodId");
		String categoryId = request.getParameter("categoryId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String validateUntil = request.getParameter("validateUntil");
		String seller = request.getParameter("seller");
		String parentProdId = request.getParameter("parentProdId");
		String inStockCnt = request.getParameter("inStockCnt");
		String unitPrice = request.getParameter("unitPrice");
		String registerStatus = request.getParameter("registerStatus");
		String sellingStatus = request.getParameter("sellingStatus");
		String sellingCommissionType = request.getParameter("sellingCommissionType");
		String sellingCommissionRate = request.getParameter("sellingCommissionRate");
		String sellingCommissionFee = request.getParameter("sellingCommissionFee");
		String tags = request.getParameter("tags");

		if (StringUtils.equalsIgnoreCase(sellingCommissionType, "RATE")) {
			sellingCommissionFee = null;
		} else if (StringUtils.equalsIgnoreCase(sellingCommissionType, "FEE")) {
			sellingCommissionRate = null;
		} else if (StringUtils.equalsIgnoreCase(sellingCommissionType, "NONE")) {
			sellingCommissionFee = null;
			sellingCommissionRate = null;
		} else {
			sellingCommissionType = null;
			sellingCommissionFee = null;
			sellingCommissionRate = null;
		}

		try {

			if (StringUtils.isNotBlank(prodId)) product.setProdId(Integer.parseInt(prodId));
			if (StringUtils.isNotBlank(categoryId)) product.setCategoryId(Integer.parseInt(categoryId));
			if (StringUtils.isNotBlank(name)) product.setName(name);
			if (StringUtils.isNotBlank(description)) product.setDescription(description);
			if (StringUtils.isNotBlank(validateUntil)) product.setValidateUntil(validateUntil);
			if (StringUtils.isNotBlank(seller)) product.setSeller(seller);
			if (StringUtils.isNotBlank(inStockCnt)) product.setInStockCnt(Integer.parseInt(inStockCnt));
			if (StringUtils.isNotBlank(unitPrice)) product.setUnitPrice(new BigDecimal(unitPrice));
			if (StringUtils.isNotBlank(registerStatus)) product.setRegisterStatus(registerStatus);
			if (StringUtils.isNotBlank(sellingStatus)) product.setSellingStatus(sellingStatus);
			if (StringUtils.isNotBlank(sellingCommissionType)) product.setSellingCommissionType(sellingCommissionType);
			if (StringUtils.isNotBlank(sellingCommissionRate)) product.setSellingCommissionRate(new Float(sellingCommissionRate));
			if (StringUtils.isNotBlank(sellingCommissionFee)) product.setSellingCommissionFee(new BigDecimal(sellingCommissionFee));
			if (StringUtils.isNotBlank(tags)) product.setTags(tags);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				updateCnt = productService.insertProduct(product);

			} else {
				if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");
				product.setProdId(Integer.parseInt(prodId));

				if (!productService.existProduct(new Product(prodId))) {
					throw new Exception("수정하려는 제품의 정보가 존재하지 않습니다. 제품ID 를 다시 확인해주세요");
				}
				updateCnt = productService.modifyProduct(product);
			}

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/modifyProductDeliveryForm")
	public ModelAndView modifyProductDeliveryForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/modifyProductDelivery");
		String prodId = request.getParameter("prodId");
		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		Product product = productService.getProduct(new Product(prodId));

		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping("/alertDeleteAllProductInfo")
	public ModelAndView alertDeleteAllProductInfo(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/alertDeleteAllProductInfo");
		String prodId = request.getParameter("prodId");
		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");

		Product product = productService.getProduct(new Product(prodId));
		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping(value = "/deleteProduct", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteProduct(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String prodId = request.getParameter("prodId");
		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");

		try {
			// 등록된 모든 이미지를 삭제한다.
			productImageService.deleteAllProductImages(Integer.parseInt(prodId));

			// 등록된 모든 옵션정보를 삭제한다.
			productOptionService.deleteProductOption(new ProductOptionValue(prodId));

			// TODO: 등록된 디스플레이 Area 삭제

			// 등록된 마스터 정보를 삭제한다.
			productService.deleteProduct(new Product(prodId));

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
