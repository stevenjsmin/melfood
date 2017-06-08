/** 
 * 2015 AbstractDocumentStamper.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public abstract class AbstractDocumentStamper implements Document {

	private String fileName = "TEMP";

	protected DocumentTemplate documentDTO;

	@Override
	public void setDocumentTemplate(DocumentTemplate documentDto) {
		this.documentDTO = documentDto;

	}

	@Override
	public DocumentTemplate getDocumentTemplate() {
		return this.documentDTO;
	}

	@Override
	public String getDocumentTemplateType() {
		return documentDTO.getTemplateFileType();
	}

	/**
	 * Get document name that will be created<br>
	 * If null, then file name will be TEMP
	 * 
	 * @return
	 */
	protected String getFileName() {
		return fileName;
	}

	/**
	 * Set document name that will be created<br>
	 * If null, then file name will be set as TEMP
	 * 
	 * @param fileName
	 */
	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
