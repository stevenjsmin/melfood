/** 
 * 2016 ContractInfoDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.contract;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;
import melfood.framework.user.User;

/**
 * Seller와의 계약 정보를 관리하는 DAO 인터페이스 구현체
 *
 * @author steven.min
 *
 */
@Repository
public class ContractInfoDAOImpl extends BaseDAO implements ContractInfoDAO {

	@Override
	public List<ContractInfo> getContractInfos(ContractInfo contractInfo) throws Exception {
		return sqlSession.selectList("mySqlContractInfoDao.getContractInfos", contractInfo);
	}

	@Override
	public Integer getTotalCntForContractInfos(ContractInfo contractInfo) {
		return sqlSession.selectOne("mySqlContractInfoDao.getTotalCntForContractInfos", contractInfo);
	}

	@Override
	public Integer deleteContractInfo(ContractInfo contractInfo) throws Exception {
		return sqlSession.delete("mySqlContractInfoDao.deleteContractInfo", contractInfo);
	}

	@Override
	public Integer insertContractInfo(ContractInfo contractInfo) throws Exception {
		return sqlSession.insert("mySqlContractInfoDao.insertContractInfo", contractInfo);
	}

	@Override
	public Integer modifyContractInfo(ContractInfo contractInfo) throws Exception {
		return sqlSession.update("mySqlContractInfoDao.modifyContractInfo", contractInfo);
	}

	@Override
	public Integer modifyContractInfoForNotNull(ContractInfo contractInfo) throws Exception {
		return sqlSession.update("mySqlContractInfoDao.modifyContractInfoForNotNull", contractInfo);
	}

	@Override
	public List<User> getSellers(User user) throws Exception {
		return sqlSession.selectList("mySqlContractInfoDao.getSellers", user);
	}

	@Override
	public Integer insertContractFile(List<ContractFile> contractFiles) throws Exception {
		return sqlSession.insert("mySqlContractInfoDao.insertContractFile", contractFiles);
	}

	@Override
	public List<ContractFile> getContractFiles(ContractFile contractFile) throws Exception {
		return sqlSession.selectList("mySqlContractInfoDao.getContractFiles", contractFile);
	}

	@Override
	public Integer deleteContractFile(ContractFile contractFile) throws Exception {
		return sqlSession.delete("mySqlContractInfoDao.deleteContractFile", contractFile);
	}

}
