/** 
 * 2016 AttachmentFileService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.attachement;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 첨부파일 관리를 위한 서비스 인터페이스<br>
 * 어떤 모듈의 파일을 첨부하거나, 파일을 등록하는 경우 이 인터페이스를 활용하도록 한다.<br>
 * 모든 첨부파일의 I/O 행위는 모두 이 인터페이스의 API통해 이루어지도록 하여 일관성을 유지하도록 한다.
 *
 * @author steven.min
 *
 */
public interface AttachmentFileService {

	public AttachmentFile getAttachmentFile(AttachmentFile attachmentFile) throws Exception;

	public List<AttachmentFile> getAttachmentFiles(AttachmentFile attachmentFile) throws Exception;

	public Integer getTotalCntForAttachmentFiles(AttachmentFile attachmentFile);

	/**
	 * 첨부파일 추가
	 * 
	 * @param attachmentFile
	 * @return 추가된 FileId, 주의-Insert된 레코드 갯수가 아니다.
	 * @throws Exception
	 */
	public Integer insertAttachmentFile(AttachmentFile attachmentFile) throws Exception;

	public Integer deleteAttachmentFile(AttachmentFile attachmentFile) throws Exception;

	public Integer modifyAttachmentFile(AttachmentFile attachmentFile) throws Exception;

	public Integer modifyAttachmentFileForNotNull(AttachmentFile attachmentFile) throws Exception;

	/**
	 * 업로든된 파일은 임시 디렉토리에 저장된다. 이 저장된 파일을 영구적으로 저장하고 관리하기위하여 해당 데이블로 정보를 기록하고<br>
	 * 물리적 파일을 지정된 위치로 옮겨야 하는데 이때 이 API를 사용한다.
	 * 
	 * @param srcFile 옮겨질 대상이 되는 파일
	 * @param subDirTobeSave 옮겨지게될 곳. (Full Path가 아닌 시스템에서 지정된) 하휘 Sub디렉토리를 지정한다.
	 * @return 처리된 FileID
	 * @throws Exception
	 */
	public Integer transToAttachmentFileFrom(File srcFile, String subDirTobeSave) throws Exception;

	/**
	 * 업로든된 파일은 임시 디렉토리에 저장된다. 이 저장된 파일을 영구적으로 저장하고 관리하기위하여 해당 데이블로 정보를 기록하고<br>
	 * 물리적 파일을 지정된 위치로 옮겨야 하는데 이때 이 API를 사용한다.
	 * 
	 * @param srcFile 옮겨질 대상이 되는 파일
	 * @param subDirTobeSave 옮겨지게될 곳. (Full Path가 아닌 시스템에서 지정된) 하휘 Sub디렉토리를 지정한다.
	 * @param delete 옮기고 나서 srcFile의 파일을 지울것인가 결정한다.
	 * @return 처리된 FileID
	 * @throws Exception
	 */
	public Integer transToAttachmentFileFrom(File srcFile, String subDirTobeSave, boolean delete) throws Exception;

	/**
	 * 파일을 다운로드 한다.<br>
	 * 다운로드 하려는 파일을 이름은 HttpServletRequest에 <b>fileId</b>파라미터로 저정되어야 한다.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void downloadAttachmentFile(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 파일 업로드시 사용되었던 임시디렉토리의 모든 파일을 삭제한다.
	 * 
	 * @param subDirectory 삭제될 업로드 임시 서브디렉토리
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAllfilesForTempUploadDir(String subDirectory) throws Exception;

	/**
	 * 파일을 업로드한다.<br>
	 * 업로드 하려는 파일을 이름은 HttpServletRequest에 <b>fileId</b>파라미터로 저정되어야 한다.
	 * 
	 * @param request
	 * @param subDirectory 업로드 임시 서브디렉토리
	 * @throws Exception
	 */
	public void uploadFile(HttpServletRequest request, String subDirectory) throws Exception;

	/**
	 * 파일을 업로드한다.<br>
	 * 
	 * @param request
	 * @param subDirectory
	 * @param fileParameterName
	 * @throws Exception
	 */
	public void uploadFile(HttpServletRequest request, String subDirectory, String fileParameterName) throws Exception;

	/**
	 * 파일을 삭제 한다.<br>
	 * 삭제 하려는 파일을 이름은 HttpServletRequest에 <b>fileId</b>파라미터로 저정되어야 한다.
	 * 
	 * @param request
	 * @param subDirectory
	 * @param fileParameterName
	 * @throws Exception
	 */
	public void removeFile(HttpServletRequest request, String subDirectory) throws Exception;

	/**
	 * 파일을 삭제 한다.<br>
	 * 
	 * @param request
	 * @param subDirectory
	 * @param fileParameterName
	 * @throws Exception
	 */
	public void removeFile(HttpServletRequest request, String subDirectory, String fileParameterName) throws Exception;

}
