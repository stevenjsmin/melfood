/** 
 * 2016 ContractMgtController.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import melfood.shopping.contract.ContractFile;
import melfood.shopping.contract.ContractInfo;
import melfood.shopping.contract.ContractInfoService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/contractmgt")
public class ContractMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ContractMgtController.class);

	@Autowired
	private ContractInfoService contractInfoService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {

		ModelAndView mav = new ModelAndView("tiles/admin/contractmgt/main");

		User seller = new User();
		seller.setUseYn("Y");

		Properties htmlProperty = new Properties();

		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> contractStatusOptions = codeService.getValueCmbxOptions("COMM", "CONTRACT_STATUS");
		htmlProperty = new Properties("contractStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxContractStatus", codeService.generateCmbx(contractStatusOptions, htmlProperty));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String to_yyyymmdd = df.format(cal.getTime());
		cal.add(Calendar.YEAR, -1);
		String from_yyyymmdd = df.format(cal.getTime());
		mav.addObject("contractStartDate", from_yyyymmdd);
		mav.addObject("contractEndDate", to_yyyymmdd);

		return mav;
	}

	@RequestMapping(value = "/contractInfos", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getContractInfos(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		ContractInfo contractInfo = new ContractInfo();

		// For Pagination
		contractInfo.setPagenationPage(getPage(request));
		contractInfo.setPagenationPageSize(getPageSize(request));

		String userId = request.getParameter("userId");
		String contractStatus = request.getParameter("contractStatus");
		String contractStartDate = request.getParameter("contractStartDate");
		String contractEndDate = request.getParameter("contractEndDate");
		String contractDescription = request.getParameter("contractDescription");

		if (StringUtils.isNotBlank(userId)) contractInfo.setUserId(userId);
		if (StringUtils.isNotBlank(contractStatus)) contractInfo.setContractStatus(contractStatus);
		if (StringUtils.isNotBlank(contractStartDate)) contractInfo.setContractStartDate(contractStartDate);
		if (StringUtils.isNotBlank(contractEndDate)) contractInfo.setContractEndDate(contractEndDate);
		if (StringUtils.isNotBlank(contractDescription)) contractInfo.setContractDescription(contractDescription);

		Integer totalCount = 0;
		List<ContractInfo> list = contractInfoService.getContractInfos(contractInfo);
		totalCount = contractInfoService.getTotalCntForContractInfos(contractInfo);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping("/registContractInfoForm")
	public ModelAndView registContractInfoForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/contractmgt/regist");

		User seller = new User();
		seller.setUseYn("Y");

		Properties htmlProperty = new Properties();

		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> contractStatusOptions = codeService.getValueCmbxOptions("COMM", "CONTRACT_STATUS");
		htmlProperty = new Properties("contractStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxContractStatus", codeService.generateCmbx(contractStatusOptions, htmlProperty, false));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String from_yyyymmdd = df.format(cal.getTime());
		cal.add(Calendar.YEAR, 1);
		String to_yyyymmdd = df.format(cal.getTime());

		mav.addObject("contractStartDate", from_yyyymmdd);
		mav.addObject("contractEndDate", to_yyyymmdd);

		return mav;
	}

	@RequestMapping("/modifyContractInfoForm")
	public ModelAndView modifyContractInfoForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/contractmgt/modify");

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");

		Properties htmlProperty = new Properties();

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(contractSeq)) {
			throw new Exception("[사용자ID | 계약순번]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		ContractInfo contractInfo = contractInfoService.getContractInfo(new ContractInfo(userId, contractSeq));

		List<Option> contractStatusOptions = codeService.getValueCmbxOptions("COMM", "CONTRACT_STATUS", contractInfo.getContractStatus());
		htmlProperty = new Properties("contractStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxContractStatus", codeService.generateCmbx(contractStatusOptions, htmlProperty));

		mav.addObject("seller", contractInfo);

		return mav;
	}

	@RequestMapping("/detailContractForm")
	public ModelAndView detailContractForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/contractmgt/detailInfo");

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(contractSeq)) {
			throw new Exception("[사용자ID | 계약순번]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		ContractInfo contractInfo = contractInfoService.getContractInfo(new ContractInfo(userId, contractSeq));
		mav.addObject("seller", contractInfo);

		return mav;
	}

	@RequestMapping(value = "/saveContractInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveContractInfo(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");
		String contractStatus = request.getParameter("contractStatus");
		String contractStartDate = request.getParameter("contractStartDate");
		String contractEndDate = request.getParameter("contractEndDate");
		String contractDescription = request.getParameter("contractDescription");
		String attachementFile = request.getParameter("attachementFile");

		List<ContractFile> contractFiles = new ArrayList<ContractFile>();
		try {

			if (StringUtils.isBlank(userId)) {
				throw new Exception("[사용자ID]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			ContractInfo contractInfo = new ContractInfo(userId);
			contractInfo.setCreator(sessionUser.getUser().getUserId());

			if (StringUtils.isNotBlank(contractSeq)) contractInfo.setContractSeq(Integer.parseInt(contractSeq));
			if (StringUtils.isNotBlank(contractStatus)) contractInfo.setContractStatus(contractStatus);
			if (StringUtils.isNotBlank(contractStartDate)) contractInfo.setContractStartDate(contractStartDate);
			if (StringUtils.isNotBlank(contractEndDate)) contractInfo.setContractEndDate(contractEndDate);
			if (StringUtils.isNotBlank(contractStartDate)) contractInfo.setContractDescription(contractDescription);
			if (StringUtils.isNotBlank(attachementFile)) contractInfo.setAttachementFile(attachementFile);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				updateCnt = contractInfoService.insertContractInfo(contractInfo);

			} else {
				if (StringUtils.isBlank(userId) || StringUtils.isBlank(contractSeq)) {
					throw new Exception("[사용자ID | 계약 순번]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				updateCnt = contractInfoService.modifyContractInfo(contractInfo);
				// contractFiles = contractInfoService.transferFileToAttachementFileDb(userId, Integer.parseInt(contractSeq));
			}

			model.put("resultCode", "0");
			model.put("contractFiles", contractFiles);
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("contractFiles", null);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/transferFileToAttachement", produces = "application/json")
	@ResponseBody
	public Map<String, Object> transferFileToAttachement(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");

		List<ContractFile> contractFiles = new ArrayList<ContractFile>();
		try {

			if (StringUtils.isBlank(userId) || StringUtils.isBlank(contractSeq)) {
				throw new Exception("[사용자ID | 계약순번]  이항목(들)은 빈 값이 될 수 없습니다.");
			}

			contractFiles = contractInfoService.transferFileToAttachementFileDb(userId, Integer.parseInt(contractSeq));

			model.put("resultCode", "0");
			model.put("contractFiles", contractFiles);
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("contractFiles", null);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/uploadFile", produces = "application/json")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String subDirectory = Ctx.getVar("CONTRACTFILE.TEMP.UPLOAD.DIR");

		try {
			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);
			attachmentFileService.uploadFile(request, subDirectory);

			model.put("resultCode", "0");
			model.put("message", "File uploaded successfully");

		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}
		return model;

	}

	@RequestMapping(value = "/removeFile", produces = "application/json")
	@ResponseBody
	public Map<String, Object> removeFile(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String subDirectory = Ctx.getVar("CONTRACTFILE.TEMP.UPLOAD.DIR");

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

	@RequestMapping(value = "/getContractFiles", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getContractFiles(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		List<ContractFile> contractFiles = new ArrayList<ContractFile>();

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");
		String fileId = request.getParameter("fileId");
		if (StringUtils.isBlank(contractSeq)) contractSeq = "0";
		if (StringUtils.isBlank(fileId)) fileId = "0";

		try {
			contractFiles = contractInfoService.getContractFiles(new ContractFile(userId, Integer.parseInt(contractSeq), Integer.parseInt(fileId)));

			model.put("totalCount", contractFiles.size());
			model.put("list", contractFiles);
			model.put("resultCode", "0");
			model.put("message", "OK");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("totalCount", contractFiles.size());
			model.put("list", contractFiles);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/deleteContractFiles", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteContractFiles(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");
		String fileId = request.getParameter("fileId");
		if (StringUtils.isBlank(contractSeq)) contractSeq = "0";
		if (StringUtils.isBlank(fileId)) fileId = "0";

		try {
			updateCnt = contractInfoService.deleteContractAttachedFile(userId, Integer.parseInt(contractSeq), Integer.parseInt(fileId));

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	/**
	 *  마스터 계약정보와, 첨부파일등 모든 자료들을 삭제한다.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteContractInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteContractInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String contractSeq = request.getParameter("contractSeq");

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(contractSeq)) {
			throw new Exception("[사용자ID | 계약순번]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		try {
			contractInfoService.deleteContractAttachedFile(userId, Integer.parseInt(contractSeq));
			updateCnt = contractInfoService.deleteContractInfo(new ContractInfo(userId, Integer.parseInt(contractSeq)));

			model.put("resultCode", "0");
			model.put("message", updateCnt + "  의 정보가 변경/저장 되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

}
