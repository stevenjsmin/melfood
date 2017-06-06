/** 
 * 2015 DocumentDAOImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.document;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Repository
public class DocumentDAOImpl extends BaseDAO implements DocumentDAO {

	@Override
	public DocumentTemplate getDocumentTemplate(String templateId) throws Exception {
		return (DocumentTemplate) sqlSession.selectOne("mySqlDocumentDao.getDocumentTemplate", templateId);
	}

}
