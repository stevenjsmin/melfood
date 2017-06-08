/** 
 * 2016 CreditInfoDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.credit;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * TODO : 설명
 * 
 * @author steven.min
 *
 */
@Repository
public class CreditInfoDAOImpl extends BaseDAO implements CreditInfoDAO {

	@Override
	public List<CreditInfo> getCreditInfos(CreditInfo creditInfo) throws Exception {
		return sqlSession.selectList("mySqlCreditInfoDao.getCreditInfos", creditInfo);
	}

	@Override
	public Integer getTotalCntForCreditInfos(CreditInfo creditInfo) {
		return sqlSession.selectOne("mySqlCreditInfoDao.getTotalCntForCreditInfos", creditInfo);
	}

	@Override
	public Integer getTotalCreditAmount(CreditInfo creditInfo) {
		return sqlSession.selectOne("mySqlCreditInfoDao.getTotalCreditAmount", creditInfo);
	}

	@Override
	public Integer insertCreditInfo(CreditInfo creditInfo) throws Exception {
		return sqlSession.insert("mySqlCreditInfoDao.insertCreditInfo", creditInfo);
	}

	@Override
	public Integer deleteCreditInfo(CreditInfo creditInfo) throws Exception {
		return sqlSession.delete("mySqlCreditInfoDao.deleteCreditInfo", creditInfo);
	}

	@Override
	public Integer modifyCreditInfo(CreditInfo creditInfo) throws Exception {
		return sqlSession.update("mySqlCreditInfoDao.modifyCreditInfo", creditInfo);
	}

	@Override
	public Integer modifyCreditInfoForNotNull(CreditInfo creditInfo) throws Exception {
		return sqlSession.update("mySqlCreditInfoDao.modifyCreditInfoForNotNull", creditInfo);
	}

}
