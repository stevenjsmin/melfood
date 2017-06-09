/** 
 * 2015 Document.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.io.File;

/**
 * This class defines about document's operation.<br>
 * Basically, this method have default value like following, otherwise it can be act with parameter for more detail operation. <br>
 * <b>Default values</b>
 * <ul>
 * <li>copyToServer : choose for saving on server or not
 * </ul>
 *
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface Document {

	/**
	 * Publish document with default format<br>
	 * This method will publish document corresponding template ID.<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public File publishDocument() throws Exception;

	/**
	 * Publish document with default format<br>
	 * This method will publish document corresponding template ID as a indicated file name.<br>
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public File publishDocument(String fileName) throws Exception;

	/**
	 * Set document service object
	 * 
	 * @param documentService
	 * @throws Exception
	 */
	public void setDocumentTemplate(DocumentTemplate documentDto);

	/**
	 * Set document service object
	 * 
	 * @return
	 */
	public DocumentTemplate getDocumentTemplate();

	/**
	 * Get document type
	 * 
	 * @return
	 */
	public String getDocumentTemplateType();

}
