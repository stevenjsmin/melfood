/** 
 * 2016 CreditInfoDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.shopping.credit;

import java.util.List;

/**
 * 사용자 신용정보 관리
 *
 * @author steven.min
 *
 */
public interface CreditInfoDAO {
	public List<CreditInfo> getCreditInfos(CreditInfo creditInfo) throws Exception;
	public Integer getTotalCntForCreditInfos(CreditInfo creditInfo);
	
	public Integer getTotalCreditAmount(CreditInfo creditInfo);
	
	public Integer insertCreditInfo(CreditInfo creditInfo) throws Exception;
	public Integer deleteCreditInfo(CreditInfo creditInfo) throws Exception;
	public Integer modifyCreditInfo(CreditInfo creditInfo) throws Exception;
	public Integer modifyCreditInfoForNotNull(CreditInfo creditInfo) throws Exception;
}
