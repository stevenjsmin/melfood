/** 
 * 2016 ProductImageMgtController.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

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
import melfood.framework.Ctx;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductImage;
import melfood.shopping.product.ProductImageService;
import melfood.shopping.product.ProductService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/productmgt/image")
public class ProductImageMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProductImageMgtController.class);

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/image/main");
		String prodId = request.getParameter("prodId");

		mav.addObject("product", productService.getProduct(new Product(prodId)));
		return mav;
	}

	@RequestMapping(value = "/productImages", produces = "application/json")
	@ResponseBody
	public Map<String, Object> productOptions(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String prodId = request.getParameter("prodId");

		ProductImage productImage = new ProductImage(prodId);

		// For Pagination
		productImage.setPagenationPage(0);
		productImage.setPagenationPageSize(99999);

		Integer totalCount = 0;
		List<ProductImage> list = productImageService.getProductImages(productImage);
		totalCount = productImageService.getTotalCntForProductImages(productImage);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/productImageInfo")
	@ResponseBody
	public Map<String, Object> productImageInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String prodId = request.getParameter("prodId");
		String imageSeq = request.getParameter("imageSeq");

		if (StringUtils.isBlank(prodId)) throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");

		ProductImage productImage = productImageService.getProductImage(new ProductImage(prodId, imageSeq));

		model.put("productImage", productImage);

		return model;
	}

	@RequestMapping(value = "/uploadFile", produces = "application/json")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		String subDirectory = Ctx.getVar("PRODUCTIMAGE.TEMP.UPLOAD.DIR");
		String prodId = request.getParameter("prodId");

		try {
			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 지워준다.
			attachmentFileService.uploadFile(request, subDirectory);

			List<ProductImage> productImages = new ArrayList<ProductImage>();
			productImages = productImageService.transferFileToAttachementFileDb(Integer.parseInt(prodId), sessionUser.getUser().getUserId());

			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 (다시한번)지워준다.

			model.put("productImages", productImages);
			model.put("resultCode", "0");
			model.put("message", "File uploaded successfully");

		} catch (Exception e) {
			e.printStackTrace();
			model.put("productImages", null);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}
		return model;

	}

	@RequestMapping(value = "/removeFile", produces = "application/json")
	@ResponseBody
	public Map<String, Object> removeFile(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String subDirectory = Ctx.getVar("PRODUCTIMAGE.TEMP.UPLOAD.DIR");

		try {
			attachmentFileService.removeFile(request, subDirectory);

			model.put("resultCode", "0");
			model.put("message", "File removed successfully");

		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/saveProductImageInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveProductImageInfo(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String prodId = request.getParameter("prodId");
		String imageSeq = request.getParameter("imageSeq");
		String displayOrder = request.getParameter("displayOrder");
		String imageDescription = request.getParameter("imageDescription");
		String width = request.getParameter("width");
		String height = request.getParameter("height");

		List<ProductImage> productImages = new ArrayList<ProductImage>();
		try {

			if (StringUtils.isBlank(prodId)) {
				throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			ProductImage productImage = new ProductImage(prodId);
			productImage.setCreator(sessionUser.getUser().getUserId());

			if (StringUtils.isNotBlank(prodId)) productImage.setProdId(Integer.parseInt(prodId));
			if (StringUtils.isNotBlank(imageSeq)) productImage.setImageSeq(Integer.parseInt(imageSeq));
			if (StringUtils.isNotBlank(displayOrder)) productImage.setDisplayOrder(Integer.parseInt(displayOrder));
			if (StringUtils.isNotBlank(width)) productImage.setWidth(width);
			if (StringUtils.isNotBlank(height)) productImage.setHeight(height);
			if (StringUtils.isNotBlank(imageDescription)) productImage.setImageDescription(imageDescription);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				// updateCnt = contractInfoService.insertContractInfo(contractInfo);

			} else {
				if (StringUtils.isBlank(prodId) || StringUtils.isBlank(imageSeq)) {
					throw new Exception("[제품ID | 이미지 SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				updateCnt = productImageService.modifyProductImageForNotNull(productImage);
			}

			model.put("resultCode", "0");
			model.put("productImages", productImages);
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("productImages", null);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/modifyProductImageForm")
	public ModelAndView modifyProductImageForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/image/modifyForm");
		String prodId = request.getParameter("prodId");
		String imageSeq = request.getParameter("imageSeq");

		if (StringUtils.isBlank(prodId) || StringUtils.isBlank(imageSeq)) {
			throw new Exception("[제품ID | 이미지 SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Product product = productService.getProduct(new Product(prodId));

		ProductImage productImage = new ProductImage(prodId, imageSeq);

		mav.addObject("product", product);
		mav.addObject("productImage", productImageService.getProductImage(productImage));

		return mav;
	}

	@RequestMapping(value = "/deleteProductImage", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteProductImage(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String prodId = request.getParameter("prodId");
		String imageSeq = request.getParameter("imageSeq");

		if (StringUtils.isBlank(prodId) || StringUtils.isBlank(imageSeq)) {
			throw new Exception("[제품ID | 이미지 SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		try {
			updateCnt = productImageService.deleteProductImage(Integer.parseInt(prodId), Integer.parseInt(imageSeq));

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/productImageViewer")
	public ModelAndView productImageViewer(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/image/imageViewer");
		String prodId = request.getParameter("prodId");

		if (StringUtils.isBlank(prodId)) {
			throw new Exception("[제품ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		Product product = productService.getProduct(new Product(prodId));

		ProductImage productImage = new ProductImage(prodId);

		// For Pagination
		productImage.setPagenationPage(0);
		productImage.setPagenationPageSize(99999);

		List<ProductImage> list = productImageService.getProductImages(productImage);

		mav.addObject("product", product);
		mav.addObject("imageList", list);

		return mav;
	}
}
