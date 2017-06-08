/** 
 * 2016 PaymentMethodService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.payment;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.shopping.product.Category;

/**
 * 상품 판매자별로 결재 가능한 수단을 관리해야하는데, 이 클래스에서는 이런한 정보를 저장관리하는 메소드들을 정의한다.
 *
 * @author steven.min
 *
 */
public interface PaymentMethodService {
	
	public PaymentMethod getPaymentMethod(PaymentMethod paymentMethod) throws Exception;
	
	public List<PaymentMethod> getPaymentMethods(PaymentMethod paymentMethod) throws Exception;
	public Integer getTotalCntForPaymentMethods(PaymentMethod paymentMethod);

	public Integer deletePaymentMethod(PaymentMethod paymentMethod) throws Exception;
	
	public Integer insertPaymentMethod(PaymentMethod paymentMethod) throws Exception;
	
	public Integer modifyPaymentMethod(PaymentMethod paymentMethod) throws Exception;
	public Integer modifyPaymentMethodForNotNull(PaymentMethod paymentMethod) throws Exception;
	
	/**
	 * 상품판매자가 제공하는 모든 결재수단을 <code>Opiton</code>객체로 각각구성하여 List객체로 Wrapping하여 가저온다.<br>
	 * 현재 활성화 되어있는 판매자의 결재수단만을 조회한다.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Option> getCmbxOptions(String userId) throws Exception;
	
	/**
	 * 상품판매자가 제공하는 모든 결재수단을 <code>Opiton</code>객체로 각각구성하여 List객체로 Wrapping하여 가저온다.
	 *  
	 * @param userId
	 * @param useYn 'Y' 인경우 현재 활성화 되어있는 판매자의 결재수단만을 조회한다.
	 * @return
	 * @throws Exception
	 */
	public List<Option> getCmbxOptions(String userId, boolean useYn) throws Exception;
	
	/**
	 * 상품판매자가 제공하는 모든 결재수단을 <code>Opiton</code>객체로 각각구성하여 List객체로 Wrapping하여 가저온다.
	 *  
	 * @param userId
	 * @param useYn 'Y' 인경우 현재 활성화 되어있는 판매자의 결재수단만을 조회한다.
	 * @param defaultSelectedValue 기본값으로 선택되어질 항목 
	 * @return
	 * @throws Exception
	 */
	public List<Option> getCmbxOptions(String userId, boolean useYn, String defaultSelectedValue) throws Exception;
	
	/**
	 * 상품판매자가 제공하는 모든 결재수단을 HTML 콤보박스를 구성하는 HTML코드를 구성하여 가저온다.
	 * 
	 * @param options
	 * @param property
	 * @return
	 */
	public String generateCmbx(List<Option> options, Properties property);
	
	/**
	 * 상품판매자가 제공하는 모든 결재수단을 HTML 콤보박스를 구성하는 HTML코드를 구성하여 가저온다.
	 *  
	 * @param options
	 * @param property
	 * @param placeholder 콤보박스에 첫번째 항목으로 "--select --" 표시를 나오게 할것인지 말것인지 결정
	 * @return
	 */
	public String generateCmbx(List<Option> options, Properties property, boolean placeholder);

	/**
	 * 조건에 해당하는 결재수단이 존재하는지 체크한다.
	 * 
	 * @param paymentMethod
	 * @return
	 * @throws Exception
	 */
	public boolean existPaymentMethod(PaymentMethod paymentMethod) throws Exception;
}
