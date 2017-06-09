/** 
 * 2015 EmailServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.document.DocumentService;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private IEmailDAO emailDAO;

	@Autowired
	private IEmailAttachedFileDAO emailAttachedFileDAO;

	@Autowired
	private DocumentService documentService;

	@Override
	public EmailDTO saveSendEmailHistory(EmailDTO email) throws Exception {
		EmailDTO retEmailDTO = emailDAO.insertEmail(email);
		return retEmailDTO;
	}

	@Override
	public String getBelongingEmailCnt(String emailId) throws Exception {
		return emailDAO.getBelongingEmailCnt(emailId);
	}

	@Override
	public Integer updateSendEmailResult(EmailDTO email) throws Exception {
		return emailDAO.updateSendEmailResult(email);
	}

	@Override
	public List<EmailDTO> getEmail(String emailId) throws Exception {
		return emailDAO.getEmail(emailId);
	}

}
