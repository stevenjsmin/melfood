/** 
 * 2016 PaymentMethodDAO.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.payment;

import java.util.List;

/**
 * 상품 판매자별로 결재 가능한 수단을 관리해야하는데, 이 클래스에서는 이런한 정보를 저장관리하는 메소드들을 정의한다.
 * 
 * @author steven.min
 *
 */
public interface PaymentMethodDAO {
	public List<PaymentMethod> getPaymentMethods(PaymentMethod paymentMethod) throws Exception;
	public Integer getTotalCntForPaymentMethods(PaymentMethod paymentMethod);

	/**
	 * 상품판매자 별로 유일한 결재방법을 등록해야하는데, 이때 유니크한 순번을 얻기위해 이 API를 사용한다.<br>
	 * 이때 반드시 PaymentMethod에 설정해서 넘겨야할 항목은 판매ID(User ID)이다.
	 * 
	 * @param paymentMethod
	 * @return
	 * @throws Exception
	 */
	public Integer getNextPaymentMethodSeq(PaymentMethod paymentMethod) throws Exception;
	
	public Integer insertPaymentMethod(PaymentMethod paymentMethod) throws Exception;
	
	public Integer modifyPaymentMethod(PaymentMethod paymentMethod) throws Exception;
	public Integer modifyPaymentMethodForNotNull(PaymentMethod paymentMethod) throws Exception;
	
	public Integer deletePaymentMethod(PaymentMethod paymentMethod) throws Exception;
}
