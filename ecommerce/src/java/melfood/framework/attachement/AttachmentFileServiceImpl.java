/** 
 * 2016 AttachmentFileServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.attachement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import melfood.framework.Ctx;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentFileServiceImpl.class);

	@Autowired
	private AttachmentFileDAO attachmentFileDAO;

	@Override
	public AttachmentFile getAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		List<AttachmentFile> files = this.getAttachmentFiles(attachmentFile);
		if (files != null && files.size() > 0) {
			return files.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<AttachmentFile> getAttachmentFiles(AttachmentFile attachmentFile) throws Exception {
		return attachmentFileDAO.getAttachmentFiles(attachmentFile);
	}

	@Override
	public Integer getTotalCntForAttachmentFiles(AttachmentFile attachmentFile) {
		return attachmentFileDAO.getTotalCntForAttachmentFiles(attachmentFile);
	}

	@Override
	public Integer insertAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		return attachmentFileDAO.insertAttachmentFile(attachmentFile);
	}

	@Override
	public Integer deleteAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		int fileId = attachmentFile.getFileId();
		AttachmentFile attachedFile = this.getAttachmentFile(new AttachmentFile(fileId));

		String subDirectory = "";
		String fileName = "";
		String savedFileName = "";
		StringBuffer fileFullPath = new StringBuffer("");

		try {
			subDirectory = attachedFile.getSubDirectory();
			fileName = attachedFile.getFileName();
			savedFileName = attachedFile.getSavedFileName();

			fileFullPath.append(Ctx.APP_DATA_DIR);
			fileFullPath.append(Ctx.getVar("ATTACHMENT.FILE.SUBDIR"));
			fileFullPath.append(subDirectory + savedFileName);
			File physicalFile = new File(fileFullPath.toString());

			if (physicalFile.isFile() && physicalFile.exists()) {
				FileUtils.deleteQuietly(physicalFile);
				logger.info("File deleted :" + fileName + "::" + savedFileName);
			} else {
				logger.info("File not exists to be deleted :" + fileName + "::" + savedFileName);
			}

		} catch (Exception e) {
			logger.info("Failure while deleting file :" + fileName + "::" + savedFileName + "::" + e.getMessage());
		}

		return attachmentFileDAO.deleteAttachmentFile(new AttachmentFile(fileId));
	}

	@Override
	public Integer modifyAttachmentFile(AttachmentFile attachmentFile) throws Exception {
		return attachmentFileDAO.modifyAttachmentFile(attachmentFile);
	}

	@Override
	public Integer modifyAttachmentFileForNotNull(AttachmentFile attachmentFile) throws Exception {
		return attachmentFileDAO.modifyAttachmentFileForNotNull(attachmentFile);
	}

	@Override
	public Integer transToAttachmentFileFrom(File srcFile, String subDirTobeSave) throws Exception {
		return this.transToAttachmentFileFrom(srcFile, subDirTobeSave, true);
	}

	@Override
	public Integer transToAttachmentFileFrom(File srcFile, String subDirTobeSave, boolean delete) throws Exception {
		String fileName = "";
		String savedFileName = "";
		String fileType = "";
		long fileSize = 0;
		Integer insertedFileId = 0;

		AttachmentFile attachmentFile = new AttachmentFile();

		if (srcFile.isFile()) {

			fileName = srcFile.getName();
			fileType = StringUtils.upperCase(StringUtils.substringAfterLast(fileName, "."));
			savedFileName = this.getUniqueFileName() + "." + fileType;
			fileSize = srcFile.length();

			attachmentFile.setSubDirectory(subDirTobeSave);
			attachmentFile.setFileName(fileName);
			attachmentFile.setSavedFileName(savedFileName);
			attachmentFile.setFileType(fileType);
			attachmentFile.setFileSize(Math.toIntExact(fileSize));

			insertedFileId = this.insertAttachmentFile(attachmentFile);

			String destPath = Ctx.APP_DATA_DIR + Ctx.getVar("ATTACHMENT.FILE.SUBDIR") + subDirTobeSave;
			File destDir = new File(destPath);

			if (destDir.exists() == false) FileUtils.forceMkdir(destDir);

			FileUtils.copyFile(srcFile, new File(destPath + savedFileName));

			if (delete) {
				FileUtils.deleteQuietly(srcFile);
			}

		}
		return insertedFileId;
	}

	private String getUniqueFileName() {
		String fileName = new SimpleDateFormat("yyyyMMddhhmmssSS").format(new Date());
		return fileName;
	}

	@Override
	public void downloadAttachmentFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileId = request.getParameter("fileId");
		if (StringUtils.isBlank(fileId)) throw new Exception("Please specify file id for downloading");
		AttachmentFile attachmentFile = this.getAttachmentFile(new AttachmentFile(Integer.parseInt(fileId)));

		try {
			String subDirectory = attachmentFile.getSubDirectory();
			String fileName = attachmentFile.getFileName();
			String savedFileName = attachmentFile.getSavedFileName();
			StringBuffer fileFullPath = new StringBuffer("");
			fileFullPath.append(Ctx.APP_DATA_DIR);
			fileFullPath.append(Ctx.getVar("ATTACHMENT.FILE.SUBDIR"));
			fileFullPath.append(subDirectory + savedFileName);
			File physicalFile = new File(fileFullPath.toString());

			int BUFFER_SIZE = Ctx.getVarAsInt("FILE-DOWNLOAD.BUFFER-SIZE");
			// File downloadFile = new File(file);
			FileInputStream inputStream = new FileInputStream(physicalFile);

			// get MIME type of the file
			ServletContext context = request.getSession().getServletContext();
			String mimeType = context.getMimeType(fileFullPath.toString());
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) physicalFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Can not download document.Please report to " + Ctx.xmlConfig.getString("contact-info/level1/name") + "|" + Ctx.xmlConfig.getString("contact-info/level1/phone"));
		}
	}

	@Override
	public boolean deleteAllfilesForTempUploadDir(String subDirectory) throws Exception {
		String uploadFileDir = Ctx.APP_DATA_DIR + subDirectory;

		try {
			logger.info("Delete all files under '" + uploadFileDir + "' directory");
			File dir = new File(uploadFileDir);
			FileUtils.cleanDirectory(dir);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void uploadFile(HttpServletRequest request, String subDirectory) throws Exception {
		this.uploadFile(request, subDirectory, "files");
	}

	@Override
	public void uploadFile(HttpServletRequest request, String subDirectory, String fileParameterName) throws Exception {
		String targetDir = Ctx.APP_DATA_DIR + subDirectory;
	
		MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartReq.getFiles(fileParameterName);

		byte[] bfile = null;
		String origFileName = null;
		File file = null;

		try {
			
			// 생성해야할 디렉토리가 존재하지 않는다면 해당 디릭토리를 생성해준다.
			File destDir = new File(targetDir);
			if(!destDir.exists() && !destDir.isDirectory()) {
			    logger.info("임시로 업로드되어져야할 디렉토리 생성 :" + destDir.getAbsolutePath());
				destDir.mkdirs();
			}
			
			
			for (MultipartFile mFile : files) {
				bfile = mFile.getBytes();
				origFileName = mFile.getOriginalFilename();

				file = new File(targetDir + origFileName);

				OutputStream out = new FileOutputStream(file);
				out.write(bfile);
				out.flush();
				out.close();

				logger.info("File upload : " + file.getAbsolutePath());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void removeFile(HttpServletRequest request, String subDirectory) throws Exception {
		this.removeFile(request, subDirectory, "removeFile");
	}

	@Override
	public void removeFile(HttpServletRequest request, String subDirectory, String fileParameterName) throws Exception {
		String removeFile = request.getParameter(fileParameterName);
		String uploadFileDir = Ctx.APP_DATA_DIR + subDirectory;

		try {
			logger.info("File delete : " + removeFile);
			File file = new File(uploadFileDir + removeFile);

			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
