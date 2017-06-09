/** 
 * 2015 IEmailAttachedFileDAO.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.util.List;
import java.util.Map;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface IEmailAttachedFileDAO {

	/**
	 * Insert attached file
	 * 
	 * @param attachedFile
	 * @return Inserted file id
	 * @throws Exception
	 */
	public String insertAttachedFile(EmailAttachedFileDTO attachedFile) throws Exception;

	/**
	 * Get attached file information
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public EmailAttachedFileDTO getAttachedFile(String fileId) throws Exception;

	/**
	 * Get attached file list corresponding search condition
	 * 
	 * @param param This map should be mached between it's key and search keys
	 * @return
	 * @throws Exception
	 */
	public List<EmailAttachedFileDTO> getAttachedFileList(Map<String, String> param) throws Exception;

	/**
	 * Get attached file list corresponding primary key of email master table
	 * 
	 * @param emailId
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	public List<EmailAttachedFileDTO> getAttachedFileList(String emailId, String seq) throws Exception;

	/**
	 * Check if file corresponding fileId exist or not
	 * 
	 * @param fileId
	 * @return If exist, it will be true. Otherwise it will be return false
	 * @throws Exception
	 */
	public boolean existAttachedFile(String fileId) throws Exception;

	/**
	 * Delete attached file corresponding fileId
	 * 
	 * @param fileId
	 * @return A number of deleted attached files
	 * @throws Exception
	 */
	public int deleteAttachedFile(String fileId) throws Exception;

	/**
	 * Delete attached files corresponding List of attaced file IDs
	 * 
	 * @param fileIds
	 * @return
	 * @throws Exception
	 */
	public int deleteAttachedFile(List<String> fileIds) throws Exception;

	/**
	 * Delete attached file corresponding primary key of email master table
	 * 
	 * @param emailId
	 * @param seq
	 * @return A number of deleted attached files
	 * @throws Exception
	 */
	public int deleteAttachedFile(String emailId, String seq) throws Exception;

}
