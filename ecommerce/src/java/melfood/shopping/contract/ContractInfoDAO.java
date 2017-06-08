/** 
 * 2016 ContractInfoDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.contract;

import java.util.List;

import melfood.framework.user.User;

/**
 * Seller와의 계약 정보를 관리하는 DAO 인터페이스 정의
 *
 * @author steven.min
 *
 */
public interface ContractInfoDAO {
	public List<ContractInfo> getContractInfos(ContractInfo contractInfo) throws Exception;
	public Integer getTotalCntForContractInfos(ContractInfo contractInfo);
	
	public List<User> getSellers(User user) throws Exception;
	
	public Integer deleteContractInfo(ContractInfo contractInfo) throws Exception;
	public Integer insertContractInfo(ContractInfo contractInfo) throws Exception;
	public Integer modifyContractInfo(ContractInfo contractInfo) throws Exception;
	public Integer modifyContractInfoForNotNull(ContractInfo contractInfo) throws Exception;
	
	public Integer deleteContractFile(ContractFile contractFile) throws Exception;
	
	public Integer insertContractFile(List<ContractFile> contractFiles) throws Exception;
	
	public List<ContractFile> getContractFiles(ContractFile contractFile) throws Exception;
	
}
