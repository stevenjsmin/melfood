/** 
 * 2016 PaymentMethodMgtController.java
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
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.contract.ContractInfoService;
import melfood.shopping.payment.PaymentMethod;
import melfood.shopping.payment.PaymentMethodService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/paymentmethodmgt")
public class PaymentMethodMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryMgtController.class);

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private ContractInfoService contractInfoService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/paymentmgt/main");

		Properties htmlProperty = new Properties();

		// List<Option> contractorOptions = contractInfoService.getAllSellers();
		List<Option> contractorOptions = contractInfoService.getSellers(new User());
		htmlProperty = new Properties("userId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> paymentMethodOptions = codeService.getValueCmbxOptions("COMM", "PAYMENT_METHOD");
		htmlProperty = new Properties("paymentMethod");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxPaymentMethod", codeService.generateCmbx(paymentMethodOptions, htmlProperty));

		List<Option> bankNameOptions = codeService.getValueCmbxOptions("COMM", "BANK");
		htmlProperty = new Properties("bankName");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxBankName", codeService.generateCmbx(bankNameOptions, htmlProperty));

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/getPaymentMethods", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getPaymentMethods(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		PaymentMethod paymentMethod = new PaymentMethod();

		// For Pagination
		paymentMethod.setPagenationPage(getPage(request));
		paymentMethod.setPagenationPageSize(getPageSize(request));

		String userId = request.getParameter("userId");
		String paymentMethod1 = request.getParameter("paymentMethod");
		String bankName = request.getParameter("bankName");
		String bankBsb = request.getParameter("bankBsb");
		String bankAccountNo = request.getParameter("bankAccountNo");
		String bankAccountOwnerName = request.getParameter("bankAccountOwnerName");
		String useYn = request.getParameter("useYn");

		if (StringUtils.isNotBlank(userId)) paymentMethod.setUserId(userId);
		if (StringUtils.isNotBlank(paymentMethod1)) paymentMethod.setPaymentMethod(paymentMethod1);
		if (StringUtils.isNotBlank(bankName)) paymentMethod.setBankName(bankName);
		if (StringUtils.isNotBlank(bankBsb)) paymentMethod.setBankBsb(bankBsb);
		if (StringUtils.isNotBlank(bankAccountNo)) paymentMethod.setBankAccountNo(bankAccountNo);
		if (StringUtils.isNotBlank(bankAccountOwnerName)) paymentMethod.setBankAccountOwnerName(bankAccountOwnerName);
		if (StringUtils.isNotBlank(useYn)) paymentMethod.setUseYn(useYn);

		Integer totalCount = 0;
		List<PaymentMethod> list = paymentMethodService.getPaymentMethods(paymentMethod);
		totalCount = paymentMethodService.getTotalCntForPaymentMethods(paymentMethod);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/registPaymentMethodForm")
	public ModelAndView registPaymentMethodForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/paymentmgt/registPaymentMethodForm");

		Properties htmlProperty = new Properties();

		// List<Option> contractorOptions = contractInfoService.getAllSellers();
		List<Option> contractorOptions = contractInfoService.getSellers(new User());
		htmlProperty = new Properties("userId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> paymentMethodOptions = codeService.getValueCmbxOptions("COMM", "PAYMENT_METHOD");
		htmlProperty = new Properties("paymentMethod");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setOnchange("disableOrEnableBankdetail(this)");
		mav.addObject("cbxPaymentMethod", codeService.generateCmbx(paymentMethodOptions, htmlProperty));

		List<Option> bankNameOptions = codeService.getValueCmbxOptions("COMM", "BANK");
		htmlProperty = new Properties("bankName");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxBankName", codeService.generateCmbx(bankNameOptions, htmlProperty));

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/modifyPaymentMethodForm")
	public ModelAndView modifyPaymentMethodForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/paymentmgt/modifyPaymentMethodForm");

		String userId = request.getParameter("userId");
		String methodSeq = request.getParameter("methodSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(methodSeq)) {
			throw new Exception("[USER_ID | METHOD_SEQ] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(new PaymentMethod(userId, methodSeq));
		mav.addObject("paymentMethod", paymentMethod);

		Properties htmlProperty = new Properties();

		mav.addObject("userName", paymentMethod.getSeller().getUserName());
		mav.addObject("methodSeq", methodSeq);
		mav.addObject("userId", userId);

		List<Option> paymentMethodOptions = codeService.getValueCmbxOptions("COMM", "PAYMENT_METHOD", paymentMethod.getPaymentMethod());
		htmlProperty = new Properties("paymentMethod");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setOnchange("disableOrEnableBankdetail(this)");
		mav.addObject("cbxPaymentMethod", codeService.generateCmbx(paymentMethodOptions, htmlProperty));

		List<Option> bankNameOptions = codeService.getValueCmbxOptions("COMM", "BANK", paymentMethod.getBankName());
		htmlProperty = new Properties("bankName");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxBankName", codeService.generateCmbx(bankNameOptions, htmlProperty));

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "USE_YN", paymentMethod.getUseYn());
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/detailPaymentMethod")
	public ModelAndView detailCategoryForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/product/categorymgt/detailInfo");

		String userId = request.getParameter("userId");
		String methodSeq = request.getParameter("methodSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(methodSeq)) {
			throw new Exception("[USER_ID | METHOD_SEQ] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(new PaymentMethod(userId, methodSeq));

		mav.addObject("paymentMethod", paymentMethod);

		return mav;
	}

	@RequestMapping(value = "/savePaymentMethod", produces = "application/json")
	@ResponseBody
	public Map<String, Object> savePaymentMethod(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String userId = request.getParameter("userId");
		String methodSeq = request.getParameter("methodSeq");
		String paymentMethod1 = request.getParameter("paymentMethod");
		String bankName = request.getParameter("bankName");
		String bankBsb = request.getParameter("bankBsb");
		String bankAccountNo = request.getParameter("bankAccountNo");
		String bankAccountOwnerName = request.getParameter("bankAccountOwnerName");
		String useYn = request.getParameter("useYn");

		if (StringUtils.isBlank(userId)) {
			throw new Exception("[USER_ID] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		try {

			PaymentMethod paymentMethod = new PaymentMethod(userId);

			if (StringUtils.isNotBlank(userId)) paymentMethod.setUserId(userId);
			if (StringUtils.isNotBlank(paymentMethod1)) paymentMethod.setPaymentMethod(paymentMethod1);
			if (StringUtils.isNotBlank(useYn)) {
				paymentMethod.setUseYn(useYn);
			} else {
				paymentMethod.setUseYn("Y");
			}

			if (StringUtils.equalsIgnoreCase(paymentMethod1, "ACCOUNT_TRANSFER")) {
				if (StringUtils.isNotBlank(bankName)) paymentMethod.setBankName(bankName);
				if (StringUtils.isNotBlank(bankBsb)) paymentMethod.setBankBsb(bankBsb);
				if (StringUtils.isNotBlank(bankAccountNo)) paymentMethod.setBankAccountNo(bankAccountNo);
				if (StringUtils.isNotBlank(bankAccountOwnerName)) paymentMethod.setBankAccountOwnerName(bankAccountOwnerName);
			}

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				// 추가
				paymentMethod.setCreator(sessionUser.getUser().getUserId());
				updateCnt = paymentMethodService.insertPaymentMethod(paymentMethod);

			} else {
				// 수정
				if (StringUtils.isBlank(methodSeq)) throw new Exception("[METHOD_SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
				paymentMethod.setMethodSeq(Integer.parseInt(methodSeq));

				if (!paymentMethodService.existPaymentMethod(new PaymentMethod(userId, methodSeq))) {
					throw new Exception("수정하려는 카테고리 정보가 존재하지 않습니다.");
				}
				updateCnt = paymentMethodService.modifyPaymentMethod(paymentMethod);
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

	@RequestMapping(value = "/deletePaymentMethod", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deletePaymentMethod(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String methodSeq = request.getParameter("methodSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(methodSeq)) {
			throw new Exception("[USER_ID | METHOD_SEQ] 이항목(들)은 빈 값이 될 수 없습니다.");
		}

		try {
			updateCnt = paymentMethodService.deletePaymentMethod(new PaymentMethod(userId, methodSeq));

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/enableAndDisablePaymentMethod", produces = "application/json")
	@ResponseBody
	public Map<String, Object> enableAndDisablePaymentMethod(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String methodSeq = request.getParameter("methodSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(methodSeq)) throw new Exception("[USER_ID | METHOD_SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
		PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(new PaymentMethod(userId, methodSeq));

		PaymentMethod paymentMethod1 = new PaymentMethod(userId, methodSeq);
		if (StringUtils.equalsIgnoreCase(paymentMethod.getUseYn(), "Y")) {
			paymentMethod1.setUseYn("N");
		} else {
			paymentMethod1.setUseYn("Y");
		}

		try {

			updateCnt = paymentMethodService.modifyPaymentMethodForNotNull(paymentMethod1);

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
