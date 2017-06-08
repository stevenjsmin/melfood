/** 
 * 2015 DocumentServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.Ctx;
import melfood.framework.auth.AuthService;
import melfood.framework.auth.SessionUserInfo;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Service
public class DocumentServiceImpl implements DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	@Autowired
	protected AuthService authService;

	@Autowired
	private DocumentDAO documentDAO;



	@Override
	public DocumentTemplate getDocumentTemplate(String templateId) throws Exception {
		return documentDAO.getDocumentTemplate(templateId);
	}

	@Override
	public String getDocumentTemplateContents(String templateId) throws Exception {
		DocumentTemplate template = this.getDocumentTemplate(templateId);
		String templateFile = getClass().getClassLoader().getResource("/templates/" + template.getTemplateFileName()).getFile();

		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(templateFile));

			String line = null;
			while ((line = in.readLine()) != null) {
				contentBuilder.append(line + "\r\n");
			}
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}

	@Override
	public void downloadDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		}
	}

}
