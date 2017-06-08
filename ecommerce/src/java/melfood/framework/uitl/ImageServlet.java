/** 
 * 2016 ImageServlet.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.uitl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.system.BeanHelper;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServletContext cntx = getServletContext();

		String fileId = request.getParameter("f");

		AttachmentFile aFile = null;
		AttachmentFileService fileService = null;
		try {
			fileService = BeanHelper.getBean("attachmentFileService", AttachmentFileService.class);
			aFile = fileService.getAttachmentFile(new AttachmentFile(fileId));

			String imageFile = Ctx.APP_DATA_DIR + Ctx.getVar("ATTACHMENT.FILE.SUBDIR") + aFile.getSubDirectory() + aFile.getSavedFileName();
			// logger.info(imageFile);

			String fileType = aFile.getFileType().toLowerCase();

			String mime = cntx.getMimeType(imageFile);
			response.setContentType(mime);

			File f = new File(imageFile);
			BufferedImage bi = ImageIO.read(f);
			OutputStream out = response.getOutputStream();
			ImageIO.write(bi, fileType, out);
			out.close();
			
		} catch (Exception e) {
			// e.printStackTrace();
			logger.warn("이미지 파일을 읽는도중에 문제가 발생하였습니다. [File ID : " + fileId + "] - " + e.getMessage() );
		}

	}
}
