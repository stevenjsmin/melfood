/** 
 * 2015 MSWordDocument.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import java.io.File;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class MSWordDocument extends AbstractDocumentStamper {

	public MSWordDocument(DocumentTemplate documentDto) {
		super.setDocumentTemplate(documentDto);
	}

	@Override
	public File publishDocument() throws Exception {
		return null;
	}

	@Override
	public File publishDocument(String fileName) throws Exception {
		setFileName(fileName);
		return publishDocument();
	}
}
