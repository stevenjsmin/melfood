/** 
 * 2015 HTMLDocument.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import melfood.framework.MelfoodConstants;
import melfood.framework.Ctx;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class HTMLDocument extends AbstractDocumentStamper {

	public HTMLDocument(DocumentTemplate documentDto) {
		super.setDocumentTemplate(documentDto);
	}

	@Override
	public File publishDocument() throws Exception {
		DocumentTemplate documentDto = this.getDocumentTemplate();
		String tempName = Ctx.APP_DATA_DIR + Ctx.getVar("DOCUMENT.TEMP.TEMPLATE.DIR") + getFileName() + "." + MelfoodConstants.DOCUMENT_TYPE_HTML;
		String template = Ctx.APP_DATA_DIR + Ctx.getVar("DOCUMENT.TEMPLATE.DIR") + documentDto.getTemplateFileName();

		try {
			BufferedReader in = new BufferedReader(new FileReader(template));

			String str = new String("");
			String line = null;
			while ((line = in.readLine()) != null) {
				str = str + line + "\r\n";
			}
			in.close();

			String data = documentDto.getData();
			String dataArray[] = StringUtils.split(data, "^");
			for (String item : dataArray) {
				String key = StringUtils.substringBefore(item, "=");
				String value = StringUtils.substringAfter(item, "=");
				str = StringUtils.replace(str, "#{" + key + "}#", value);
			}
			char intxt[] = new char[str.length()];

			str.getChars(0, str.length(), intxt, 0);

			FileWriter fw = new FileWriter(tempName);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(intxt);
			bw.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new File(tempName);
	}

	@Override
	public File publishDocument(String fileName) throws Exception {
		setFileName(fileName);
		return publishDocument();
	}

}
