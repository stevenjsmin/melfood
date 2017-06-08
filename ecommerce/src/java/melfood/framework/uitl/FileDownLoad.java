/** 
 * 2015 FileDownLoad.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.uitl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class FileDownLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FileDownLoad.class);

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		ServletContext sc = getServletContext();
		// String filename = req.getParameter("file");
		String filename = Ctx.xmlConfig.getVar("data-directory/document/temp-data/dir") + "temp.pdf";

		String mimeType = sc.getMimeType(StringUtils.lowerCase(filename));
		if (mimeType == null) {
			sc.log("Could not get MIME type of " + filename);
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		res.setContentType(mimeType);

		File file = new File(filename);
		res.setContentLength((int) file.length());

		FileInputStream in = new FileInputStream(file);
		OutputStream out = res.getOutputStream();

		// logger.debug("File Name: " + filename);
		// logger.debug("MIME Type: " + mimeType);
		// logger.debug("File Length: " + (int) file.length());

		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			// System.out.print(".");
			out.write(buf, 0, count);
		}

		in.close();
		out.close();
	}
}
