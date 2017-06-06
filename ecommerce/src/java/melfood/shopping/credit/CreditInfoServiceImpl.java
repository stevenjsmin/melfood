/** 
 * 2016 CreditInfoServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.credit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO : 설명
 *
 * @author steven.min
 *
 */
@Service
public class CreditInfoServiceImpl implements CreditInfoService {

	@Autowired
	private CreditInfoDAO creditInfoDAO;

	@Override
	public CreditInfo getCreditInfo(CreditInfo creditInfo) throws Exception {
		List<CreditInfo> creditInfos = this.getCreditInfos(creditInfo);
		if (creditInfos != null && creditInfos.size() > 0) {
			return creditInfos.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<CreditInfo> getCreditInfos(CreditInfo creditInfo) throws Exception {
		return creditInfoDAO.getCreditInfos(creditInfo);
	}

	@Override
	public Integer getTotalCntForCreditInfos(CreditInfo creditInfo) {
		return creditInfoDAO.getTotalCntForCreditInfos(creditInfo);
	}

	@Override
	public Integer getTotalCreditAmount(CreditInfo creditInfo) {
		return creditInfoDAO.getTotalCreditAmount(creditInfo);
	}

	@Override
	public Integer addCreditInfo(CreditInfo creditInfo) throws Exception {
		return creditInfoDAO.insertCreditInfo(creditInfo);
	}

	@Override
	public Integer deleteCreditInfo(CreditInfo creditInfo) throws Exception {
		return creditInfoDAO.deleteCreditInfo(creditInfo);
	}

	@Override
	public Integer modifyCreditInfo(CreditInfo creditInfo) throws Exception {
		return creditInfoDAO.modifyCreditInfo(creditInfo);
	}

	@Override
	public Integer modifyCreditInfoForNotNull(CreditInfo creditInfo) throws Exception {
		return creditInfoDAO.modifyCreditInfoForNotNull(creditInfo);
	}

}
