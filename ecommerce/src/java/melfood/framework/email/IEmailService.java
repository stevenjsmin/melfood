/** 
 * 2015 IEmailService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.util.List;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface IEmailService {

	/**
	 * Save send email record<br>
	 * 
	 * @param email
	 * @return 0: Successs, otherwise Failure
	 * @throws Exception
	 */
	public EmailDTO saveSendEmailHistory(EmailDTO email) throws Exception;

	/**
	 * Get belonging email count of given email ID
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public String getBelongingEmailCnt(String emailId) throws Exception;

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
	 * Get email list corresponding emailId.<br>
	 * It will be return all email of sequence that included it's emailId.
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public List<EmailDTO> getEmail(String emailId) throws Exception;

}
