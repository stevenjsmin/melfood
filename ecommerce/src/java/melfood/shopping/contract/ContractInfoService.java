/** 
 * 2016 ContractInfoService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.contract;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;

/**
 * Seller와의 계약 정보를 관리하는 서비스 인터페이스 정의
 *
 * @author steven.min
 *
 */
public interface ContractInfoService {

	public ContractInfo getContractInfo(ContractInfo contractInfo) throws Exception;

	public List<ContractInfo> getContractInfos(ContractInfo contractInfo) throws Exception;

	public Integer getTotalCntForContractInfos(ContractInfo contractInfo) throws Exception;

	public Integer deleteContractInfo(ContractInfo contractInfo) throws Exception;

	public Integer insertContractInfo(ContractInfo contractInfo) throws Exception;

	public Integer modifyContractInfo(ContractInfo contractInfo) throws Exception;

	public Integer modifyContractInfoForNotNull(ContractInfo contractInfo) throws Exception;

	public List<Option> getSellers(User user) throws Exception;
	public List<Option> getSellers(User user, String selectedUser) throws Exception;
	public List<Option> getAllSellers() throws Exception; 
	public List<Option> getAllSellers(String selectedUser) throws Exception; 

	public String generateCmbx(List<Option> options, Properties property, boolean placeholder) throws Exception;

	// public String getUploadTempDir() throws Exception;

	public Integer deleteContractAttachedFile(String userId) throws Exception;

	public Integer deleteContractAttachedFile(String userId, int contractSeq) throws Exception;

	public Integer deleteContractAttachedFile(String userId, int contractSeq, int fileId) throws Exception;


	public List<ContractFile> transferFileToAttachementFileDb(String userId, int contractSeq) throws Exception;

	public List<ContractFile> getContractFiles(ContractFile contractFile) throws Exception;
}
