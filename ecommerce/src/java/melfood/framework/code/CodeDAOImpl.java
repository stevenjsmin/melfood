/** 
 * 2015 CodeDAOImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.code;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author Steven J.S Min
 * 
 */

@Repository
public class CodeDAOImpl extends BaseDAO implements CodeDAO {

	@Override
	public List<Code> getCodes(Code code) throws Exception {
		return sqlSession.selectList("mySqlCodeDao.getCodes", code);
	}

	@Override
	public Integer getTotalCntForCodes(Code code) {
		return sqlSession.selectOne("mySqlCodeDao.getTotalCntForCodes", code);
	}

	@Override
	public List<Code> getCategories() throws Exception {
		return sqlSession.selectList("mySqlCodeDao.getCategories");
	}

	@Override
	public List<Code> getTypes(String category) throws Exception {
		if(StringUtils.isBlank(category)) return null;
		return sqlSession.selectList("mySqlCodeDao.getTypes", category);
	}

	@Override
	public Integer deleteCode(Code code) throws Exception {
		return sqlSession.delete("mySqlCodeDao.deleteCode", code);
	}

	@Override
	public Integer deleteCodes(List<Code> codes) throws Exception {
		return null;
	}

	@Override
	public Integer insertCode(Code code) throws Exception {
		return sqlSession.insert("mySqlCodeDao.insertCode", code);
	}

	@Override
	public Integer modifyCode(Code code) throws Exception {
		return sqlSession.update("mySqlCodeDao.modifyCode", code);
	}

	@Override
	public Integer modifyCodeForNotNull(Code code) throws Exception {
		return sqlSession.update("mySqlCodeDao.modifyCodeForNotNull", code);
	}

}
