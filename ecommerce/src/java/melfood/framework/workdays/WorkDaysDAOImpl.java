/** 
 * 2016 WorkDaysDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.workdays;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * Implementation for MenuDAO
 * 
 * @author steven.min
 *
 */
@Repository
public class WorkDaysDAOImpl extends BaseDAO implements WorkDaysDAO {

	@Override
	public List<WorkDays> getList(WorkDays workDays) throws Exception {
		return sqlSession.selectList("mySqlWorkDaysDao.getList", workDays);
	}

	@Override
	public Integer getTotalCntForList(WorkDays workDays) throws Exception {
		return sqlSession.selectOne("mySqlWorkDaysDao.getTotalCntForList", workDays);
	}

	@Override
	public Integer deleteWorkDays(WorkDays workDays) throws Exception {
		return sqlSession.delete("mySqlWorkDaysDao.deleteWorkDays", workDays);
	}

	@Override
	public Integer insertWorkDays(WorkDays workDays) throws Exception {
		return sqlSession.insert("mySqlWorkDaysDao.insertWorkDays", workDays);
	}

	@Override
	public Integer updateWorkDays(WorkDays workDays) throws Exception {
		return sqlSession.update("mySqlWorkDaysDao.updateWorkDays", workDays);
	}

}
