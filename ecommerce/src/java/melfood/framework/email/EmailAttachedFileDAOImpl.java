/** 
 * 2015 EmailAttachedFileDAOImpl.java
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

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Repository
public class EmailAttachedFileDAOImpl extends BaseDAO implements IEmailAttachedFileDAO {

	@Autowired
	private IEmailDAO emailDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#insertAttachedFile(uss.framework.email.EmailAttachedFileDTO)
	 */
	@Override
	public String insertAttachedFile(EmailAttachedFileDTO attachedFile) throws Exception {

		String fileId = Integer.toString(this.getNextAttachedFileId());
		attachedFile.setFileId(fileId);
		int resultCnt = sqlSession.insert("mySqlEmailDAO.insertEmailAttachedFile", attachedFile);

		if (resultCnt > 0) {
			return fileId;
		} else {
			return null;
		}
	}

	private Integer getNextAttachedFileId() throws Exception {
		Integer seq = (Integer) sqlSession.selectOne("mySqlEmailDAO.getNextEmailAttachedFileId");
		return seq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#getAttachedFile(java.lang.String)
	 */
	@Override
	public EmailAttachedFileDTO getAttachedFile(String fileId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("fileId", fileId);
		return this.getAttachedFileList(param).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#getAttachedFileList(java.util.Map)
	 */
	@Override
	public List<EmailAttachedFileDTO> getAttachedFileList(Map<String, String> param) throws Exception {
		return sqlSession.selectList("mySqlEmailDAO.getEmailAttachedFileList", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#getAttachedFileList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmailAttachedFileDTO> getAttachedFileList(String emailId, String seq) throws Exception {
		List<String> fileIds = new ArrayList<String>();
		EmailDTO emailDTO = emailDAO.getEmail(emailId, seq);
		if (emailDTO != null) {
			String file1 = emailDTO.getAttachedFileId1();
			String file2 = emailDTO.getAttachedFileId2();
			String file3 = emailDTO.getAttachedFileId3();
			String file4 = emailDTO.getAttachedFileId4();
			String file5 = emailDTO.getAttachedFileId5();

			if (StringUtils.isNotBlank(file1)) fileIds.add(file1);
			if (StringUtils.isNotBlank(file2)) fileIds.add(file2);
			if (StringUtils.isNotBlank(file3)) fileIds.add(file3);
			if (StringUtils.isNotBlank(file4)) fileIds.add(file4);
			if (StringUtils.isNotBlank(file5)) fileIds.add(file5);

			return sqlSession.selectList("mySqlEmailDAO.getEmailAttachedFileListByFileIDs", fileIds);

		} else {

			return null;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#existAttachedFile(java.lang.String)
	 */
	@Override
	public boolean existAttachedFile(String fileId) throws Exception {
		EmailAttachedFileDTO emailAttachedFileDTO = this.getAttachedFile(fileId);
		if (emailAttachedFileDTO != null) return true;
		else return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#deleteAttachedFile(java.lang.String)
	 */
	@Override
	public int deleteAttachedFile(String fileId) throws Exception {
		List<String> fileIds = new ArrayList<String>();
		fileIds.add(fileId);
		return this.deleteAttachedFile(fileIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#deleteAttachedFile(java.util.List)
	 */
	@Override
	public int deleteAttachedFile(List<String> fileIds) throws Exception {
		if (fileIds.size() < 1) return 0;
		return sqlSession.delete("mySqlEmailDAO.deleteEmailAttachedFile", fileIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uss.framework.email.IEmailAttachedFileDAO#deleteAttachedFile(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteAttachedFile(String emailId, String seq) throws Exception {
		List<String> fileIds = new ArrayList<String>();

		EmailDTO emailDTO = emailDAO.getEmail(emailId, seq);
		if (emailDTO != null) {
			String file1 = emailDTO.getAttachedFileId1();
			String file2 = emailDTO.getAttachedFileId2();
			String file3 = emailDTO.getAttachedFileId3();
			String file4 = emailDTO.getAttachedFileId4();
			String file5 = emailDTO.getAttachedFileId5();

			if (StringUtils.isNotBlank(file1)) fileIds.add(file1);
			if (StringUtils.isNotBlank(file2)) fileIds.add(file2);
			if (StringUtils.isNotBlank(file3)) fileIds.add(file3);
			if (StringUtils.isNotBlank(file4)) fileIds.add(file4);
			if (StringUtils.isNotBlank(file5)) fileIds.add(file5);

			return this.deleteAttachedFile(fileIds);

		} else {

			return 0;

		}

	}

}
