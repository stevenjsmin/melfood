/** 
 * 2016 AttachmentFileDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.attachement;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * 첨부파일 관리를 위한 DAO 인터페이스 구현체
 *
 * @author steven.min
 */
@Repository
public class AttachmentFileDAOImpl extends BaseDAO implements AttachmentFileDAO {

	@Override
	public List<AttachmentFile> getAttachmentFiles(AttachmentFile attachmentFile) throws Exception {
		return sqlSession.selectList("mySqlAttachmentFileDao.getAttachmentFiles", attachmentFile);
	}

	@Override
	public Integer getTotalCntForAttachmentFiles(AttachmentFile attachmentFile) {
		return sqlSession.selectOne("mySqlAttachmentFileDao.getTotalCntForAttachmentFiles", attachmentFile);
	}

	@Override
	public Integer nextFileId() throws Exception {
		return sqlSession.selectOne("mySqlAttachmentFileDao.nextFileId");
	}

	@Override
	public Integer insertAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		int nextFileId = 0;
		try {
			nextFileId = this.nextFileId();
			attachmentFile.setFileId(nextFileId);
			sqlSession.insert("mySqlAttachmentFileDao.insertAttachmentFile", attachmentFile);
		} catch (Exception e) {
			throw e;
		}
		return nextFileId;
	}

	@Override
	public Integer deleteAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		return sqlSession.delete("mySqlAttachmentFileDao.deleteAttachmentFile", attachmentFile);
	}

	@Override
	public Integer modifyAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		return sqlSession.update("mySqlAttachmentFileDao.modifyAttachmentFile", attachmentFile);
	}

	@Override
	public Integer modifyAttachmentFileForNotNull(AttachmentFile attachmentFile) throws Exception {
		return sqlSession.update("mySqlAttachmentFileDao.modifyAttachmentFileForNotNull", attachmentFile);
	}

}
