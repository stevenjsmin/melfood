/** 
 * 2015 DocumentService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface DocumentService {

	/**
	 * Get document template information.
	 * 
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public DocumentTemplate getDocumentTemplate(String templateId) throws Exception;

	/**
	 * Get document template contents.
	 * 
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public String getDocumentTemplateContents(String templateId) throws Exception;

	/**
	 * 문서를 다운로드한다.<br>
	 * 다운로드 하려는 문서의 저정은 HttpServletRequest의 <b>documentId</b>파라미터 변수로 지정되어져야한다.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void downloadDocument(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
