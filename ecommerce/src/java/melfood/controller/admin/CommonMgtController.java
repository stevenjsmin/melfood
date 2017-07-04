/** 
 * 2015 CommonMgtController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import melfood.framework.Ctx;
import melfood.framework.abn.ABNDetails;
import melfood.framework.abn.ABNLookup;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.document.DocumentTemplate;
import melfood.framework.email.EmailDTO;
import melfood.framework.email.EmailServices;
import melfood.framework.email.IEmailService;
import melfood.framework.gmap.MelfoodGoogleMapService;
import melfood.framework.system.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommonMgtController.class);

	@Autowired
	IEmailService emailService;

	@Autowired
	AttachmentFileService attachmentFileService;

	@Autowired
	MelfoodGoogleMapService melfoodGoogleMapService;

	@RequestMapping(value = "/ABNLookup", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getABN(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		String abn = request.getParameter("abn");
		abn = StringUtils.replace(abn, " ", "");

		try {
			ABNLookup abnLookup = new ABNLookup();
			ABNDetails abnDetails = abnLookup.getEntityNamebyABN(abn);

			model.put("result", abnDetails);
			model.put("resultMessage", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("result", null);
			model.put("resultMessage", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/ACNLookup", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getACN(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		String acn = request.getParameter("acn");
		acn = StringUtils.replace(acn, " ", "");

		try {
			ABNLookup acnLookup = new ABNLookup();
			ABNDetails acnDetails = acnLookup.getEntityNamebyACN(acn);

			model.put("result", acnDetails);
			model.put("resultMessage", "OK");
		} catch (Exception e) {
			model.put("result", null);
			model.put("resultMessage", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/ACNLookupFromCreditWork", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getACNFromCreditWork(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		String acn = request.getParameter("acn");
		acn = StringUtils.replace(acn, " ", "");

		try {
			ABNLookup acnLookup = new ABNLookup();
			ABNDetails acnDetails = acnLookup.getACNByCreditworks(acn);

			model.put("result", acnDetails);
			model.put("resultMessage", "OK");
		} catch (Exception e) {
			model.put("result", null);
			model.put("resultMessage", e.getMessage());
		}
		return model;
	}

	@RequestMapping("/DocDownload")
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String documentId = request.getParameter("documentId");
		if (StringUtils.isBlank(documentId)) throw new Exception("For document download, documentId is needed.");
		documentService.downloadDocument(request, response);

	}

	@RequestMapping("/fileDown")
	public void fileDown(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileId = request.getParameter("fileId");
		if (StringUtils.isBlank(fileId)) throw new Exception("Please specify file id for downloading");

		attachmentFileService.downloadAttachmentFile(request, response);

	}

	@RequestMapping(value = "/ReportError", produces = "application/json")
	@ResponseBody
	public Map<String, Object> reportError(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		StringBuffer reportedBy = new StringBuffer(sessionUser.getUser().getUserName());
		if (!StringUtils.isBlank(sessionUser.getUser().getEmail())) reportedBy.append(" " + sessionUser.getUser().getEmail());
		// if (!StringUtils.isBlank(sessionUser.getUser().getTelephone())) reportedBy.append(" " + sessionUser.getUser().getTelephone());
		// if (!StringUtils.isBlank(sessionUser.getUser().getMobilePhone())) reportedBy.append(" " + sessionUser.getUser().getMobilePhone());

		String errorCode = request.getParameter("errorCode");
		String errorUrl = request.getParameter("errorUrl");
		String errorMessage = request.getParameter("errorMessage");
		String errorDetailMessage = request.getParameter("errorDetailMessage");
		String errorTime = request.getParameter("errorTime");

		try {
			StringBuffer message = new StringBuffer("");
			message.append("errorCode=" + errorCode + "^");
			message.append("errorUrl=" + errorUrl + "^");
			message.append("errorMessage=" + errorMessage + "^");
			message.append("errorDetailMessage=" + errorDetailMessage + "^");
			message.append("errorTime=" + errorTime + "^");
			message.append("reportedBy=" + reportedBy.toString() + "^");
			String recipient1 = Ctx.xmlConfig.getString("contact-info/level1/email");
			String recipient2 = Ctx.xmlConfig.getString("contact-info/application-developer/email");
			String recipient3 = Ctx.xmlConfig.getString("contact-info/system-administrator/email");
			// String recipient4 = UssCtx.config.getString("contact-info/level2/email");

			// Send email
			// "4" is template id of Contract mail message body
			EmailServices email = new EmailServices();
			if (!StringUtils.isBlank(recipient1)) email.sendEmailUsingHtmlTemplate(recipient1, "System Error Report", message.toString(), "7");
			if (!StringUtils.isBlank(recipient2)) email.sendEmailUsingHtmlTemplate(recipient2, "System Error Report", message.toString(), "7");
			if (!StringUtils.isBlank(recipient3)) email.sendEmailUsingHtmlTemplate(recipient3, "System Error Report", message.toString(), "7");
			// if(StringUtils.isBlank(recipient4)) email.sendEmailWithTemplate(recipient4, "System Error Report", message.toString(), "7");

			model.put("result", "OK");
			model.put("resultMessage", "OK");
		} catch (Exception e) {
			model.put("result", "Failure");
			model.put("resultMessage", e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping("/EmailSendFrom")
	public ModelAndView emailSendFrom(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/emailsendform");

		String emailType = request.getParameter("top_email_type");
		if (StringUtils.equalsIgnoreCase(emailType, "Inquiry")) {
			mav = new ModelAndView("tiles/common/emailInquiryForm");
		}

		String emailId = request.getParameter("emailId");
		mav.addObject("emailId", StringUtils.isNotBlank(emailId) ? emailId : "");

		String subject = request.getParameter("subject");
		String sender = request.getParameter("sender");
		String to = request.getParameter("to");
		String cc = request.getParameter("cc");
		String bcc = request.getParameter("bcc");
		String contentsTemplate = request.getParameter("contentsTemplate");
		String doc1 = request.getParameter("doc1");
		String doc2 = request.getParameter("doc2");
		String doc3 = request.getParameter("doc3");
		String doc4 = request.getParameter("doc4");
		String doc5 = request.getParameter("doc5");

		String callbackUrl = request.getParameter("callbackUrl");
		String callbackParam = request.getParameter("callbackParam");
		mav.addObject("callbackUrl", StringUtils.isNotBlank(callbackUrl) ? callbackUrl : "");

		if (StringUtils.isNotBlank(callbackParam)) {
			callbackParam = StringUtils.trim(StringUtils.substringBetween(callbackParam, "{", "}"));
		}

		StringBuffer params = new StringBuffer("");
		if (StringUtils.isNotBlank(callbackParam)) {
			String callParam[] = StringUtils.split(callbackParam, ",");
			for (String aParam : callParam) {
				String keyValue[] = StringUtils.split(aParam, ":");
				params.append(StringUtils.trim(keyValue[0]) + "=" + StringUtils.trim(keyValue[1]) + "&");
			}
			mav.addObject("callbackParam", params.toString());
		}
		logger.info("callbackUrl :" + callbackUrl);
		logger.info("callbackParam :" + callbackParam);

		mav.addObject("subject", StringUtils.isNotBlank(subject) ? subject : "");

		if (StringUtils.isBlank(sender)) {
			mav.addObject("sender", Ctx.xmlConfig.getString("contact-info/general/email"));
		} else {
			mav.addObject("sender", sender);
		}

		mav.addObject("to", StringUtils.isBlank(to) ? "" : to);
		mav.addObject("cc", StringUtils.isBlank(cc) ? "" : cc);
		mav.addObject("bcc", StringUtils.isBlank(bcc) ? "" : bcc);

		if (StringUtils.isNotBlank(contentsTemplate)) {
			contentsTemplate = documentService.getDocumentTemplateContents(contentsTemplate);
		} else {
			contentsTemplate = "";
		}
		mav.addObject("contentsTemplate", contentsTemplate);

		// Making download file name
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String sendDatetime = df.format(cal.getTime());
		mav.addObject("sendDatetime", sendDatetime);

		String defaultActivateTab = request.getParameter("defaultActivateTab");
		if (StringUtils.isBlank(defaultActivateTab)) defaultActivateTab = "write";
		mav.addObject("defaultActivateTab", defaultActivateTab);

		List<DocumentTemplate> docList = new ArrayList<DocumentTemplate>();
		// if (StringUtils.isNotBlank(doc1)) docList.add(documentService.getDocumentInfo(doc1));
		// if (StringUtils.isNotBlank(doc2)) docList.add(documentService.getDocumentInfo(doc2));
		// if (StringUtils.isNotBlank(doc3)) docList.add(documentService.getDocumentInfo(doc3));
		// if (StringUtils.isNotBlank(doc4)) docList.add(documentService.getDocumentInfo(doc4));
		// if (StringUtils.isNotBlank(doc5)) docList.add(documentService.getDocumentInfo(doc5));
		mav.addObject("docList", docList);

		if (StringUtils.isNotBlank(emailId) && !StringUtils.equalsIgnoreCase(emailId, "null")) {
			List<EmailDTO> emailHistoryList = emailService.getEmail(emailId);
			mav.addObject("emailHistoryList", emailHistoryList);
		}

		mav.addObject("contact_general_phone", Ctx.xmlConfig.getString("contact-info/general/phone"));
		mav.addObject("contact_general_fax", Ctx.xmlConfig.getString("contact-info/general/fax"));
		mav.addObject("contact_general_email", Ctx.xmlConfig.getString("contact-info/general/email"));
		mav.addObject("contact_general_address", Ctx.xmlConfig.getString("contact-info/general/address"));
		mav.addObject("contact_level1_name", Ctx.xmlConfig.getString("contact-info/level1/name"));
		mav.addObject("contact_level1_email", Ctx.xmlConfig.getString("contact-info/level1/email"));
		mav.addObject("contact_level1_phone", Ctx.xmlConfig.getString("contact-info/level1/phone"));
		mav.addObject("contact_customersvc_name", Ctx.xmlConfig.getString("contact-info/customer-service/name"));
		mav.addObject("contact_customersvc_email", Ctx.xmlConfig.getString("contact-info/customer-service/email"));
		mav.addObject("contact_customersvc-phone", Ctx.xmlConfig.getString("contact-info/customer-service/phone"));
		mav.addObject("contact_salesmgr_name", Ctx.xmlConfig.getString("contact-info/sales-service/name"));
		mav.addObject("contact_salesmgr_email", Ctx.xmlConfig.getString("contact-info/sales-service/email"));
		mav.addObject("contact_salesmgr_phone", Ctx.xmlConfig.getString("contact-info/sales-service/phone"));

		return mav;
	}

}
