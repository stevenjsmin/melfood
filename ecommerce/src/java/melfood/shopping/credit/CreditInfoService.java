/** 
 * 2016 CreditInfoService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.shopping.credit;

import java.util.List;

/**
 * TODO : 설명
 * 
 * @author steven.min
 *
 */
public interface CreditInfoService {

	public CreditInfo getCreditInfo(CreditInfo creditInfo) throws Exception;

	public List<CreditInfo> getCreditInfos(CreditInfo creditInfo) throws Exception;

	public Integer getTotalCntForCreditInfos(CreditInfo creditInfo);

	public Integer getTotalCreditAmount(CreditInfo creditInfo);

	public Integer addCreditInfo(CreditInfo creditInfo) throws Exception;

	public Integer deleteCreditInfo(CreditInfo creditInfo) throws Exception;

	public Integer modifyCreditInfo(CreditInfo creditInfo) throws Exception;

	public Integer modifyCreditInfoForNotNull(CreditInfo creditInfo) throws Exception;
}
