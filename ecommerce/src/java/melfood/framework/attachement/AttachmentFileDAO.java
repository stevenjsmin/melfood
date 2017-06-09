/** 
 * 2016 AttachmentFileDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.attachement;

import java.util.List;

/**
 * 첨부파일 관리를 위한 DAO 인터페이스 정의
 *
 * @author steven.min
 *
 */
public interface AttachmentFileDAO {
	public List<AttachmentFile> getAttachmentFiles(AttachmentFile attachmentFile) throws Exception;
	public Integer getTotalCntForAttachmentFiles(AttachmentFile attachmentFile);
	
	public Integer nextFileId() throws Exception;
	public Integer insertAttachmentFile(AttachmentFile attachmentFile) throws Exception;
	
	public Integer deleteAttachmentFile(AttachmentFile attachmentFile) throws Exception;
	public Integer modifyAttachmentFile(AttachmentFile attachmentFile) throws Exception;
	public Integer modifyAttachmentFileForNotNull(AttachmentFile attachmentFile) throws Exception;
}
