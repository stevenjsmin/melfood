/** 
 * 2015 DocumentFactory.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import melfood.framework.MelfoodConstants;
import melfood.framework.system.BeanHelper;

/**
 * Get instance of document type
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class DocumentFactory {

	/**
	 * Get default document type by <b>documentId</b><br>
	 * documentId is created document instance in document table.
	 * 
	 * @return
	 */
	public static Document getDoument(String documentId) throws Exception {

		Document document = null;
		DocumentService documentService = (DocumentService) BeanHelper.getBean("documentService");

		// DocumentTemplate docDto = documentService.getDocumentInfo(documentId);
		DocumentTemplate docDto = documentService.getDocumentTemplate(documentId);

		String docType = docDto.getTemplateFileType();

		if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_PDF)) {
			document = new PDFDocument(docDto);
		} else if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_MSWORD)) {
			document = new MSWordDocument(docDto);
		} else if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_HTML)) {
			document = new HTMLDocument(docDto);
		}

		return document;
	}

	public static Document getDoumentTemplate(String templateId) throws Exception {
		return getDoumentTemplate(templateId, null);
	}

	public static Document getDoumentTemplate(String templateId, String data) throws Exception {

		Document document = null;
		DocumentService documentService = (DocumentService) BeanHelper.getBean("documentService");

		DocumentTemplate docDto = documentService.getDocumentTemplate(templateId);
		String docType = docDto.getTemplateFileType();

		if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_PDF)) {
			document = new PDFDocument(docDto);
		} else if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_MSWORD)) {
			document = new MSWordDocument(docDto);
		} else if (StringUtils.equalsIgnoreCase(docType, MelfoodConstants.DOCUMENT_TYPE_HTML)) {
			document = new HTMLDocument(docDto);
		}

		document.getDocumentTemplate().setData(data);

		return document;
	}

}
