/** 
 * 2016 ProductUtilCtrl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.abn.RpcCmmLookupCountService;
import melfood.framework.system.BaseController;
import melfood.framework.system.BeanHelper;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductService;

/**
 * @author steven.min
 *
 */
public class ProductUtilCtrl extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProductUtilCtrl.class);

	/**
	 * 판매자의 상품을 가저온다.<br>
	 * 이 메소드는 주로 컨트롤로에서 페이지단위로 상품을 가저오기위하여 사용되며, HttpServletRequest 에 설정되어 저야할 파라미터는 다음과 같다.
	 * <ul>
	 * <li>page : 가저오려는 페이지번호(기본값: 1)</li>
	 * <li>pageSize : 가저오려는 페이지에 보여질 항목 갯수(기본 10)</li>
	 * <li>frameSize : 페이지(하단)에 표시된 페이지 갯수</li>
	 * </ul>
	 * <br>
	 * 처리 결과는 ModelAndView객체에 설정되어 반환되며, ModelAndView에 설정되는 Key와 해당되는 내용은 다음과 같다.
	 * <ul>
	 * <li>totalCount : 전체 레코드수</li>
	 * <li>pagenationPageSize : 각 페이지마다 보여질 항목수</li>
	 * <li>numberOfPages : 총 페이지수</li>
	 * <li>page : 현재 요청된 페이지수</li>
	 * <li>pageFrameNumber : 현재 요청된 페이지의 페이지프레임 번호</li>
	 * <li>totalNumberOfFrames : 총 페이지 프레임 갯수</li>
	 * <li>displayStartPage : 현재 요청된 페이지의 페이지 프레임 시작번호</li>
	 * <li>displayEndPage : 현재 요청된 페이지의 페이지 프레임 종료번호</li>
	 * <li>sellerProducts : 현재 요청된 페이지에 표시될 판매자 상품</li>
	 * </ul>
	 * 
	 * @param mav
	 * @param request
	 * @param sellerId
	 * @return
	 */
	public ModelAndView getSellerProducts(ModelAndView mav, HttpServletRequest request, String sellerId) {

		int numberOfPages = 1;
		int frameSize = 5;
		int page = 1;
		int pagenationPageSize = getPageSize(request);

		if (!StringUtils.isBlank(request.getParameter("frameSize")) && StringUtils.isNumeric(request.getParameter("frameSize"))) {
			frameSize = Integer.parseInt(request.getParameter("frameSize"));
		}
		String tmpPage = request.getParameter("page");
		if (StringUtils.isEmpty(tmpPage) || StringUtils.equalsIgnoreCase(tmpPage, "null") || !StringUtils.isNumeric(tmpPage) || StringUtils.equals(tmpPage, "0")) {
			page = 1;
		} else {
			page = Integer.parseInt(tmpPage);
		}		

		String productName = request.getParameter("productName");
		
		Product sellerProduct = new Product();
		sellerProduct.setSeller(sellerId);
		sellerProduct.setRegisterStatus("COMPLETE");
		sellerProduct.setSellingStatus("ON_SELLING");
		sellerProduct.setPagenationPage(getPage(request));
		sellerProduct.setPagenationPageSize(getPageSize(request));
		if (!StringUtils.isBlank(productName)) sellerProduct.setName(productName);

		ProductService productService = (ProductService) BeanHelper.getBean("productService");

		Integer totalCount = productService.getTotalCntForProducts(sellerProduct);
		numberOfPages = totalCount / pagenationPageSize;
		if ((totalCount % pagenationPageSize) > 0) numberOfPages++;

		int totalNumberOfFrames = numberOfPages / frameSize;
		if ((numberOfPages % frameSize) > 0) totalNumberOfFrames++;

		// 현재 선택된 페이지가 몇번째 프레임인지 계산한다.
		int pageFrameNumber = 1;
		int temp = 0;
		for (int i = 1; i < numberOfPages; i++) {
			pageFrameNumber = i;
			temp = frameSize * i;
			if (temp >= page) break;
		}

		int displayStartPage = (pageFrameNumber * frameSize) - (frameSize - 1);
		int displayEndPage = displayStartPage + (frameSize - 1);
		if (displayEndPage > numberOfPages) displayEndPage = numberOfPages;

		// logger.info("총레코드수 : " + totalCount);
		// logger.info("레코드수/페이지: " + pagenationPageSize);
		// logger.info("페이지수 : " + numberOfPages);
		// logger.info("선택페이지 : " + page);
		// logger.info("프레임은 : " + pageFrameNumber + " 번째.");
		// logger.info("페이지 : " + displayStartPage + " ~ " + displayEndPage);
		mav.addObject("totalCount", totalCount);
		mav.addObject("pagenationPageSize", pagenationPageSize);
		mav.addObject("numberOfPages", numberOfPages);
		mav.addObject("page", page);
		mav.addObject("pageFrameNumber", pageFrameNumber);
		mav.addObject("totalNumberOfFrames", totalNumberOfFrames);
		mav.addObject("displayStartPage", displayStartPage);
		mav.addObject("displayEndPage", displayEndPage);

		try {
			List<Product> sellerProducts = productService.getProducts(sellerProduct);
			mav.addObject("sellerProducts", sellerProducts);
		} catch (Exception e) {
			mav.addObject("sellerProducts", null);
			e.printStackTrace();
		}

		return mav;
	}
}
