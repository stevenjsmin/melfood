/** 
 * 2015 EmailServices.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;
import melfood.framework.document.Document;
import melfood.framework.document.DocumentTemplate;
import melfood.framework.document.DocumentFactory;

public class EmailServices {

	private static final Logger logger = LoggerFactory.getLogger(EmailServices.class);

	static String fromName = Ctx.xmlConfig.getString("contact-info/default-customer-service/name");
	static String fromEmail = Ctx.xmlConfig.getString("contact-info/default-customer-service/email");

	public void sendEmail(String recipient, String subject, String bodyMessage) throws Exception {
		this.sendEmail(recipient, subject, bodyMessage, null);
	}

	public void sendEmail(String recipient, String subject, String bodyMessage, String attachmentFilePath) throws Exception {
		List<String> documentFileFullPathList = new ArrayList<String>();
		documentFileFullPathList.add(attachmentFilePath);
		this.sendEmail(null, recipient, null, null, subject, bodyMessage, documentFileFullPathList);
	}

	public void sendEmail(String sender, String recipient, String cc, String subject, String bodyMessage, String attachmentFilePath) throws Exception {
		List<String> documentFileFullPathList = new ArrayList<String>();
		documentFileFullPathList.add(attachmentFilePath);
		this.sendEmail(sender, recipient, cc, null, subject, bodyMessage, documentFileFullPathList);
	}

	/**
	 * 첨부파일과 함께, 메일을 발송하다. 주의할 점은 발송하게될 임시파일(디렉토리포함)을 함께 넘겨줘야 메일을 보낸후에 삭제해준다.<br>
	 * 그렇지 않으면 임시 디렉토리에 계속 남아있게된다.
	 * 
	 * @param sender
	 * @param recipient
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param bodyMessage
	 * @param documentFileFullPathList
	 * @throws Exception
	 */
	public void sendEmail(String sender, String recipient, String cc, String bcc, String subject, String bodyMessage, List<String> documentFileFullPathList) throws Exception {

		String host = Ctx.getVar("EMAIL.SMTP.SVR.ADDR");
		String port = Ctx.getVar("EMAIL.SMTP.SVR.PORT");
		String smtpauth = Ctx.getVar("EMAIL.SMTP.AUTH");
		String protocol = Ctx.getVar("EMAIL.SMTP.PROTOCOL");
		String iamuser = Ctx.getVar("EMAIL.SMTP.IAM.USERNAME");
		String userName = Ctx.getVar("EMAIL.SMTP.USERNAME");
		String password = Ctx.getVar("EMAIL.SMTP.PASSWORD");

		try {
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.port", port);
			properties.setProperty("mail.smtp.auth", smtpauth);
			properties.setProperty("mail.smtp.user", userName);
			properties.setProperty("mail.smtp.password", password);
			properties.setProperty("mail.transport.protocol", protocol);
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.starttls.required", "true");

			Session session = Session.getDefaultInstance(properties, new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}

			});

			// creates a new e-mail message
			Message message = new MimeMessage(session);
			if (StringUtils.isNotBlank(sender)) {
				message.setFrom(new InternetAddress(sender, "Info"));
			} else {
				message.setFrom(new InternetAddress(fromEmail, fromName));
			}
			message.setSubject(subject);

			// For TO
			if (StringUtils.contains(recipient, ";")) {
				String recipients[] = StringUtils.split(recipient, ";");
				for (String reciver : recipients) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciver));
				}

			} else {
				InternetAddress[] toAddresses = { new InternetAddress(recipient) };
				message.setRecipients(Message.RecipientType.TO, toAddresses);
			}

			// For CC
			if (StringUtils.isNotBlank(cc)) {
				if (StringUtils.contains(cc, ";")) {
					String recipients[] = StringUtils.split(cc, ";");
					for (String reciver : recipients) {
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(reciver));
					}

				} else {
					InternetAddress[] ccAddresses = { new InternetAddress(cc) };
					message.setRecipients(Message.RecipientType.CC, ccAddresses);
				}
			}

			// For BCC
			if (StringUtils.isNotBlank(bcc)) {
				if (StringUtils.contains(bcc, ";")) {
					String recipients[] = StringUtils.split(bcc, ";");
					for (String reciver : recipients) {
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(reciver));
					}

				} else {
					InternetAddress[] bccAddresses = { new InternetAddress(bcc) };
					message.setRecipients(Message.RecipientType.BCC, bccAddresses);
				}
			}

			message.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(bodyMessage, "text/html; charset=utf-8");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// int tempDirLength = 0;
			String fileName = "";
			for (String aFileFullpath : documentFileFullPathList) {

				if (aFileFullpath != null) {

					MimeBodyPart attachPart = new MimeBodyPart();

					attachPart.attachFile(aFileFullpath);
					// tempDirLength = UssCtx.config.getEnvConfig("data-directory/document/temp-data/dir").length();
					// fileName = StringUtils.substring(aFileFullpath, tempDirLength);
					fileName = StringUtils.substringAfterLast(aFileFullpath, "/");
					attachPart.setFileName(fileName);
					multipart.addBodyPart(attachPart);
				}
			}

			// Put parts in message
			message.setContent(multipart);

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {

				File file = null;
				for (String aFileFullpath : documentFileFullPathList) {
					if (aFileFullpath != null) {

						file = new File(aFileFullpath);

						if (file.delete()) {
							logger.info(file.getName() + " is deleted!");
						} else {
							logger.info("Delete operation is failed.");
						}
					}
				}
				logger.info("sender=" + sender + ", recipient=" + recipient + ", cc=" + cc + ", subject=" + subject);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	}

	/**
	 * 메일을 발송하다.<br>
	 * 메일의 내용은 주어진 템플릿(templateId)에 데이터를 Publish하기위한 메시지(templateMessage)를 도큐먼트 객체에 설정하므로서 첨부파일을 생성한다.<br>
	 * 따라서 미리 해당 템플릿과 해당하는 데이터가 준비되어 있어야한다.<br>
	 * 이 메소드는 주로 템플릿이 HTML인경우 사용하는 경우 유용한다.
	 * 
	 * @param recipient
	 * @param subject
	 * @param templateMessage
	 * @param templateId
	 * @throws Exception
	 */
	public void sendEmailUsingHtmlTemplate(String recipient, String subject, String templateMessage, String templateId) throws Exception {
		Document documentFactory = DocumentFactory.getDoumentTemplate(templateId, templateMessage);
		File templateMessageFile = documentFactory.publishDocument();

		String message = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(templateMessageFile));

			String line = null;
			while ((line = in.readLine()) != null) {
				message += line;
			}

			in.close();
			char intxt[] = new char[message.length()];
			message.getChars(0, message.length(), intxt, 0);

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sendEmail(recipient, subject, message);
	}

	/**
	 * Send email message using mail template with attched document<br>
	 * By documentId that passed as a parameter, it will generate document and then it will be sent
	 * 
	 * @param recipient
	 * @param subject
	 * @param templateMessage
	 * @param templateId
	 * @param documentId
	 * @throws Exception
	 */
	public void sendEmailUsingTemplateWithAttchement(String recipient, String subject, String templateMessage, String templateId, String documentId) throws Exception {
		this.sendEmailUsingTemplateWithAttchement(recipient, subject, templateMessage, templateId, documentId, null);
	}

	/**
	 * Send email message using mail template with attched document<br>
	 * By documentId that passed as a parameter, it will generate document and then it will be sent
	 * 
	 * @param recipient
	 * @param subject
	 * @param templateMessage
	 * @param messageTemplateId
	 * @param documentId
	 * @param attachedFileName Give file name for attched file
	 * @throws Exception
	 */
	public void sendEmailUsingTemplateWithAttchement(String recipient, String subject, String templateMessage, String messageTemplateId, String documentId, String attachedFileName) throws Exception {
		this.sendEmailUsingTemplateWithAttchement(null, recipient, null, subject, templateMessage, messageTemplateId, documentId, attachedFileName);
	}

	/**
	 * Send email message using mail template with attched document<br>
	 * By documentId that passed as a parameter, it will generate document and then it will be sent
	 * 
	 * @param sender
	 * @param recipient
	 * @param cc
	 * @param subject
	 * @param templateMessage
	 * @param messageTemplateId
	 * @param documentId
	 * @param attachedFileName Give file name for attched file
	 * @throws Exception
	 */
	public void sendEmailUsingTemplateWithAttchement(String sender, String recipient, String cc, String subject, String templateMessage, String messageTemplateId, String documentId, String attachedFileName) throws Exception {

		// For message contents
		Document documentFactory = DocumentFactory.getDoumentTemplate(messageTemplateId, templateMessage);
		File templateMessageFile = documentFactory.publishDocument();

		// For attachment document file
		Document document = DocumentFactory.getDoument(documentId);
		DocumentTemplate documentDto = document.getDocumentTemplate();
		File docFile = null;
		if (StringUtils.isBlank(attachedFileName)) {
			docFile = document.publishDocument();
		} else {
			// Publish with given file name(attachedFileName). In other words, create physical file info UssCtx.config.getEnvConfig("data-directory/document/temp-data/dir") as given fileName or default filename.
			docFile = document.publishDocument(attachedFileName);
		}
		if (!docFile.exists() || document == null || documentDto == null || documentDto.getDocumentId() == null) {
			throw new Exception("Cannot create document for Email");
		}
		String attachmentFilePath = Ctx.xmlConfig.getVar("data-directory/document/temp-data/dir") + (StringUtils.isBlank(attachedFileName) ? "TEMP." : (attachedFileName + ".")) + StringUtils.upperCase(documentDto.getTemplateFileType());

		String bodyMessage = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(templateMessageFile));

			String line = null;
			while ((line = in.readLine()) != null) {
				bodyMessage += line;
			}

			in.close();
			char intxt[] = new char[bodyMessage.length()];
			bodyMessage.getChars(0, bodyMessage.length(), intxt, 0);

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sendEmail(sender, recipient, cc, subject, bodyMessage, attachmentFilePath);
	}

}