/** 
 * 2015 DocumentDAO.java
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
public interface DocumentDAO {

	/**
	 * Get document template information.
	 * 
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public DocumentTemplate getDocumentTemplate(String templateId) throws Exception;

}
