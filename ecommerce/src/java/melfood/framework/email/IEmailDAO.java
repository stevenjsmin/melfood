/** 
 * 2015 IEmailDAO.java
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
public interface IEmailDAO {

	/**
	 * Insert a email information<br>
	 * Note : Return value should be set email ID and sequence number
	 * 
	 * @param email
	 * @return Inserted email Id
	 * @throws Exception
	 */
	public EmailDTO insertEmail(EmailDTO email) throws Exception;

	/**
	 * Update send email result information<br>
	 * Email ID and sequence number of the id is mandatory
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Integer updateSendEmailResult(EmailDTO email) throws Exception;

	/**
	 * Get belonging email count of given email ID
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public String getBelongingEmailCnt(String emailId) throws Exception;

	/**
	 * Get a email information corresponding emailId and it's sequence number
	 * 
	 * @param emailId
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	public EmailDTO getEmail(String emailId, String seq) throws Exception;

	/**
	 * Get email list corresponding emailId.<br>
	 * It will be return all email of sequence that included it's emailId.
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public List<EmailDTO> getEmail(String emailId) throws Exception;

	/**
	 * Get email list corresponding search condition
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<EmailDTO> getEmail(Map<String, String> param) throws Exception;

	/**
	 * Delete email list corresponding emailId.
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public int deleteEmail(String emailId) throws Exception;

	/**
	 * Delete a email list corresponding emailId and seq.
	 * 
	 * @param emailId
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	public int deleteEmail(String emailId, String seq) throws Exception;

}
