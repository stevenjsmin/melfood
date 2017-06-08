/** 
 * 2016 JoinMemberDAOImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.guest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author steven.min
 *
 */
@Repository
public class JoinMemberDAOImpl extends BaseDAO implements JoinMemberDAO {

	@Override
	public String getDefaultUserName() throws Exception {
		Integer currentSeq = 1;
		Integer nextSeq = 1;
		Integer maxSeq = sqlSession.selectOne("mySqlJoinMemberDao.getMaxSeq");
		String defaultName = null;

		// 현재의 위치를 얻는다.
		currentSeq = sqlSession.selectOne("mySqlJoinMemberDao.getCurrentSeq");
		if (currentSeq == null || currentSeq == 0) {
			currentSeq = 1;
			sqlSession.update("mySqlJoinMemberDao.initializeSequenceToY", 1);
			sqlSession.update("mySqlJoinMemberDao.initializeSequenceToN", 1);
		}

		nextSeq = currentSeq + 1;
		if (nextSeq >= maxSeq ) nextSeq = 1;
		while (StringUtils.isBlank(defaultName)) {

			defaultName = sqlSession.selectOne("mySqlJoinMemberDao.getDefaultName", nextSeq);
			if (StringUtils.isBlank(defaultName)) nextSeq++;
			if (nextSeq == maxSeq) nextSeq = 1;
		}

		sqlSession.update("mySqlJoinMemberDao.initializeSequenceToY", nextSeq);
		sqlSession.update("mySqlJoinMemberDao.initializeSequenceToN", nextSeq);		

		return defaultName;
	}

}
