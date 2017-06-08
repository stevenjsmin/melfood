/** 
 * 2015 EmailDAOImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;
import melfood.framework.document.DocumentDAO;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Repository
public class EmailDAOImpl extends BaseDAO implements IEmailDAO {

	@Autowired
	private IEmailAttachedFileDAO emailAttachedFileDAO;

	@Autowired
	private DocumentDAO documentDAO;

	@Override
	public EmailDTO insertEmail(EmailDTO email) throws Exception {
		EmailDTO retEmailDTO = new EmailDTO();

		String nextEmailId = email.getEmailId();
		String nextSeq = "1";
		if (StringUtils.isBlank(nextEmailId) || StringUtils.equalsIgnoreCase(nextEmailId, "null")) {
			nextEmailId = this.getNextEmailId();

			email.setEmailId(nextEmailId);
			email.setSeq(nextSeq);

			retEmailDTO.setEmailId(nextEmailId);
			retEmailDTO.setSeq(nextSeq);

		} else {
			nextSeq = this.getNextEmailSequence(nextEmailId);
			email.setSeq(nextSeq);

			retEmailDTO.setEmailId(nextEmailId);
			retEmailDTO.setSeq(nextSeq);
		}

		int resultCnt = sqlSession.insert("mySqlEmailDAO.insertEmail", email);

		return retEmailDTO;
	}

	private String getNextEmailId() throws Exception {
		Integer nextEmailId = (Integer) sqlSession.selectOne("mySqlEmailDAO.getNextEmailId");
		return Integer.toString(nextEmailId);
	}

	private String getNextEmailSequence(String emailId) throws Exception {
		Integer seq = (Integer) sqlSession.selectOne("mySqlEmailDAO.getNextEmailSequence", emailId);
		return Integer.toString(seq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#updateSendEmailResult(uss.framework.email.EmailDTO)
	 */
	@Override
	public Integer updateSendEmailResult(EmailDTO email) throws Exception {
		return (Integer) sqlSession.selectOne("mySqlEmailDAO.updateSendEmailResult", email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#getBelongingEmailCnt(java.lang.String)
	 */
	@Override
	public String getBelongingEmailCnt(String emailId) throws Exception {
		Integer belongingEmailCnt = (Integer) sqlSession.selectOne("mySqlEmailDAO.getBelongingEmailCnt", emailId);
		return Integer.toString(belongingEmailCnt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#getEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public EmailDTO getEmail(String emailId, String seq) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("emailId", emailId);
		param.put("seq", seq);
		return this.getEmail(param).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#getEmail(java.lang.String)
	 */
	@Override
	public List<EmailDTO> getEmail(String emailId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("emailId", emailId);
		return this.getEmail(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#getEmail(java.util.Map)
	 */
	@Override
	public List<EmailDTO> getEmail(Map<String, String> param) throws Exception {

		List<EmailDTO> retEmailDTOs = sqlSession.selectList("mySqlEmailDAO.getEmailList", param);

		List<String> documentIds = new ArrayList<String>();
		String emailId = "";
		String seq = "";
		/*
		 // Because performance problem, following block is commented
		for (EmailDTO emailDTO : retEmailDTOs) {

			if (StringUtils.isNotBlank(emailDTO.getAttachedDocumentId1())) documentIds.add(emailDTO.getAttachedDocumentId1());
			if (StringUtils.isNotBlank(emailDTO.getAttachedDocumentId2())) documentIds.add(emailDTO.getAttachedDocumentId2());
			if (StringUtils.isNotBlank(emailDTO.getAttachedDocumentId3())) documentIds.add(emailDTO.getAttachedDocumentId3());
			if (StringUtils.isNotBlank(emailDTO.getAttachedDocumentId4())) documentIds.add(emailDTO.getAttachedDocumentId4());
			if (StringUtils.isNotBlank(emailDTO.getAttachedDocumentId5())) documentIds.add(emailDTO.getAttachedDocumentId5());
			if (documentIds.size() > 0) {
				emailDTO.setDocumentDTOList(documentDAO.getDocumentList(documentIds));
			}

			emailId = emailDTO.getEmailId();
			seq = emailDTO.getSeq();
			emailDTO.setEmailAttachedFileDTOList(emailAttachedFileDAO.getAttachedFileList(emailId, seq));
		}
		*/

		return retEmailDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#deleteEmail(java.lang.String)
	 */
	@Override
	public int deleteEmail(String emailId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("emailId", emailId);
		return this.deleteEmail(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailDAO#deleteEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteEmail(String emailId, String seq) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("emailId", emailId);
		param.put("seq", seq);
		return this.deleteEmail(param);
	}

	private int deleteEmail(Map<String, String> param) throws Exception {
		List<EmailDTO> retEmailDTOs = this.getEmail(param);
		for (EmailDTO emailDTO : retEmailDTOs) {
			emailAttachedFileDAO.deleteAttachedFile(emailDTO.getEmailId(), emailDTO.getSeq());
		}

		return sqlSession.delete("mySqlEmailDAO.deleteEmail", param);
	}

}
