/** 
 * 2015 CommonController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.Ctx;
import melfood.framework.abn.ABNDetails;
import melfood.framework.abn.ABNLookup;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.document.Document;
import melfood.framework.document.DocumentTemplate;
import melfood.framework.document.DocumentFactory;
import melfood.framework.email.EmailDTO;
import melfood.framework.email.EmailServices;
import melfood.framework.email.IEmailService;
import melfood.framework.gmap.RpcGoogleMapService;
import melfood.framework.system.BaseController;
import melfood.shopping.order.ShoppingCart;
import melfood.shopping.order.ShoppingCartService;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	IEmailService emailService;

	@Autowired
	RpcGoogleMapService rpcGoogleMapService;

	@Autowired
	private ShoppingCartService shoppingCartService;

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
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		try {
			if (StringUtils.isBlank(documentId)) throw new Exception("For document download, documentId is needed.");

			int BUFFER_SIZE = Ctx.xmlConfig.getInt("system-config/file-download/buffer-size");

			ServletContext context = request.getSession().getServletContext();

			Document document = DocumentFactory.getDoument(documentId);

			// Physical document(pdf,doc...) creation
			File docFile = document.publishDocument();

			DocumentTemplate documentDto = document.getDocumentTemplate();

			if (!docFile.exists() || document == null || documentDto == null || documentDto.getDocumentId() == null) {
				throw new Exception("File not exist for download");
			}
			// Making download file name
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd_hhmmss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String downloadFileName = documentDto.getDocumentName() + "_" + df.format(cal.getTime()) + "." + StringUtils.upperCase(documentDto.getTemplateFileType());

			// Pickup download file
			String fullPath = Ctx.xmlConfig.getVar("data-directory/document/temp-data/dir") + "TEMP." + StringUtils.upperCase(documentDto.getTemplateFileType());
			File downloadFile = new File(fullPath);

			FileInputStream inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			logger.info("Download mimeType :" + mimeType + ", Download File :" + downloadFileName + " by " + sessionUser.getUser().getUserName());

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFileName);
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Can not download document.Please report to " + Ctx.xmlConfig.getString("contact-info/level1/name") + "|" + Ctx.xmlConfig.getString("contact-info/level1/phone"));

			// StringBuffer message = new StringBuffer("<script type=\"text/javascript\">");
			// message.append("alert(' Can not download document.Please report to " + UssCtx.config.getString("contact-info/level1") + "[" + e.getMessage() + "]' );");
			// message.append("window.history.back();");
			// message.append("</script>");
			// response.getWriter().println(message.toString());
		}

	}

	@RequestMapping("/FileDown")
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String file = request.getParameter("file");
		String downloadFileName = StringUtils.substringAfterLast(file, File.separator);
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		try {
			int BUFFER_SIZE = Ctx.xmlConfig.getInt("system-config/file-download/buffer-size");

			ServletContext context = request.getSession().getServletContext();

			File downloadFile = new File(file);

			FileInputStream inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = context.getMimeType(file);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFileName);
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Can not download document.Please report to " + Ctx.xmlConfig.getString("contact-info/level1/name") + "|" + Ctx.xmlConfig.getString("contact-info/level1/phone"));
		}

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

	@RequestMapping(value = "/EmailSend", produces = "application/json")
	@ResponseBody
	public Map<String, Object> emailSend(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, String> param = new HashMap<String, String>();

		String emailId = request.getParameter("emailId");
		EmailDTO retEmailDTO = new EmailDTO();

		String subject = request.getParameter("subject");
		String sender = request.getParameter("sender");
		String reciver = request.getParameter("reciver");
		String cc = request.getParameter("cc");
		String bcc = request.getParameter("bcc");
		String contents = request.getParameter("contents");

		if (StringUtils.isBlank(sender)) {
			sender = sessionUser.getUser().getEmail();
			if (StringUtils.isBlank(sender)) {
				sender = Ctx.xmlConfig.getString("contact-info/customer-service/email");
			}
		}

		String documentIDs = request.getParameter("documentIDs");
		String IDs[] = {};
		if (StringUtils.isNotBlank(documentIDs)) IDs = StringUtils.split(documentIDs.toString(), ",");

		EmailDTO email = new EmailDTO();
		String emailCreator = "admin";

		try {
			email.setSubject(subject);
			email.setSender(sender);
			email.setReciver(reciver);
			email.setCc(cc);
			email.setBcc(bcc);
			email.setContents(contents);
			if (sessionUser != null) {
				email.setCreator(authService.getSessionUserInfo(request).getUser().getUserId());
			} else {
				email.setCreator(emailCreator);
			}

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			// If emailID is existed, then it will be insert belonging emailID with increase sequence of the emailID
			if (StringUtils.isNotBlank(emailId)) email.setEmailId(emailId);

			List<String> docFileList = new ArrayList<String>();
			DocumentTemplate docDTO = new DocumentTemplate();
			String documentFileName = "";

			String resultCode = "0";
			int docIndex = 1;
			for (String docId : IDs) {
				switch (docIndex) {
				case 1:
					email.setAttachedDocumentId1(docId);
					break;
				case 2:
					email.setAttachedDocumentId2(docId);
					break;
				case 3:
					email.setAttachedDocumentId3(docId);
					break;
				case 4:
					email.setAttachedDocumentId4(docId);
					break;
				case 5:
					email.setAttachedDocumentId5(docId);
					break;
				default:
					break;
				}

				// TODO
				// TODO
				// TODO
				// TODO
				// TODO
				// TODO
				// docDTO = documentService.getDocumentInfo(docId);
				documentFileName = docDTO.getDocumentName() + "_" + df.format(cal.getTime()) + "_" + docDTO.getDocumentId();

				Document document = DocumentFactory.getDoument(docId);
				DocumentTemplate documentDto = document.getDocumentTemplate();
				File docFile = document.publishDocument(documentFileName);
				if (!docFile.exists() || document == null || documentDto == null || documentDto.getDocumentId() == null) {
					throw new Exception("Cannot create document for Email");
				}
				String documentFileFullPath = Ctx.xmlConfig.getVar("data-directory/document/temp-data/dir") + documentFileName + "." + StringUtils.upperCase(docDTO.getTemplateFileType());

				docFileList.add(documentFileFullPath);

				docIndex++;
			}  // end of FOR loop for Document List

			StringBuffer resultMessage = new StringBuffer("");

			EmailServices emailManager = new EmailServices();

			try {
				emailManager.sendEmail(email.getSender(), email.getReciver(), email.getCc(), email.getBcc(), email.getSubject(), email.getContents(), docFileList);
				resultCode = "0";
				logger.info("Successfully send email");
			} catch (Exception me) {
				me.getStackTrace().toString();
				String stackTrace = ExceptionUtils.getStackTrace(me);
				resultMessage.append(StringUtils.abbreviate(StringUtils.replace(stackTrace, "\r\n", ""), 400));
				resultCode = "1";
				me.printStackTrace();
			}

			// Save result after send email
			retEmailDTO = emailService.saveSendEmailHistory(email);
			String tmpEmailId = retEmailDTO.getEmailId();
			String tmpEmailSeq = retEmailDTO.getSeq();
			retEmailDTO = email;
			retEmailDTO.setEmailId(tmpEmailId);
			retEmailDTO.setSeq(tmpEmailSeq);

			retEmailDTO.setResultCode(resultCode);
			retEmailDTO.setResultMessage((StringUtils.isNotBlank(resultMessage.toString()) ? ("\n" + resultMessage.toString()) : ""));
			retEmailDTO.setBelongingEmailCnt(emailService.getBelongingEmailCnt(retEmailDTO.getEmailId()));

		} catch (Exception e) {
			retEmailDTO.setResultCode("1");
			retEmailDTO.setResultMessage(e.getMessage());
			retEmailDTO.setBelongingEmailCnt("N/A");
			e.printStackTrace();
		} finally {
			if (StringUtils.isNotBlank(retEmailDTO.getEmailId()) && StringUtils.isNotBlank(retEmailDTO.getSeq())) {
				try {
					emailService.updateSendEmailResult(retEmailDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		model.put("email", retEmailDTO);

		return model;

	}

	@RequestMapping(value = "/getNumberOfCartItems", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getNumberOfCartItems(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int numberOfCartItems = 0;

		try {
			if (sessionUser != null) {
				ShoppingCart shoppingCart = new ShoppingCart();
				shoppingCart.setCustomerId(sessionUser.getUser().getUserId());
				numberOfCartItems = shoppingCartService.getTotalCntForShoppingCartProducts(shoppingCart);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		model.put("numberOfCartItems", numberOfCartItems);

		return model;
	}

}
