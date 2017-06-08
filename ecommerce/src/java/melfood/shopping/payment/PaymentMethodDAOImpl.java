/** 
 * 2016 PaymentMethodDAOImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.payment;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author steven.min
 *
 */
@Repository
public class PaymentMethodDAOImpl extends BaseDAO implements PaymentMethodDAO {

	@Override
	public List<PaymentMethod> getPaymentMethods(PaymentMethod paymentMethod) throws Exception {
		return sqlSession.selectList("mySqlPaymentMethodDao.getPaymentMethods", paymentMethod);
	}

	@Override
	public Integer getTotalCntForPaymentMethods(PaymentMethod paymentMethod) {
		return sqlSession.selectOne("mySqlPaymentMethodDao.getTotalCntForPaymentMethods", paymentMethod);
	}

	@Override
	public Integer getNextPaymentMethodSeq(PaymentMethod paymentMethod) throws Exception {
		return sqlSession.selectOne("mySqlPaymentMethodDao.getNextPaymentMethodSeq", paymentMethod);
	}

	@Override
	public Integer insertPaymentMethod(PaymentMethod paymentMethod) throws Exception {
		int nextSeq = this.getNextPaymentMethodSeq(paymentMethod);
		paymentMethod.setMethodSeq(nextSeq);
		return sqlSession.insert("mySqlPaymentMethodDao.insertPaymentMethod", paymentMethod);
	}

	@Override
	public Integer modifyPaymentMethod(PaymentMethod paymentMethod) throws Exception {
		return sqlSession.update("mySqlPaymentMethodDao.modifyPaymentMethod", paymentMethod);
	}

	@Override
	public Integer modifyPaymentMethodForNotNull(PaymentMethod paymentMethod) throws Exception {
		return sqlSession.update("mySqlPaymentMethodDao.modifyPaymentMethodForNotNull", paymentMethod);
	}

	@Override
	public Integer deletePaymentMethod(PaymentMethod paymentMethod) throws Exception {
		return sqlSession.delete("mySqlPaymentMethodDao.deletePaymentMethod", paymentMethod);
	}

}
