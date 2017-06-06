/** 
 * 2016 OrderProductOptionService.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import java.util.List;

/**
 * 장바구니/주문 상품의 옵션 항목을 관리하는 Service이다.
 * 
 * @author steven.min
 *
 */
public interface OrderProductOptionService {

	/**
	 * 장바구니/주문 상품의 옵션을 가저온다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public OrderProductOption getOrderProductOption(OrderProductOption orderProductOption) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션 목록을 가저온다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public List<OrderProductOption> getOrderProductOptions(OrderProductOption orderProductOption) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션 목록 갯수를 가저온다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCntOrderProductOptions(OrderProductOption orderProductOption) throws Exception;

	/**
	 * 장바구니/주문 상품의 다음 옵션 순번을 가저온다.
	 * 
	 * @param orderItemId
	 * @return
	 * @throws Exception
	 */
	public Integer nextSeq(int orderItemId) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션(들)을 등록한다.
	 * 
	 * @param orderProductOptions
	 * @return
	 * @throws Exception
	 */
	public Integer insertOrderProductOption(List<OrderProductOption> orderProductOptions) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션을 수정한다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public Integer modifyOrderProductOption(OrderProductOption orderProductOption) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션을 수정한다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public Integer modifyOrderProductOptionForNotNull(OrderProductOption orderProductOption) throws Exception;

	/**
	 * 장바구니/주문 상품의 옵션을 삭제한다.
	 * 
	 * @param orderProductOption
	 * @return
	 * @throws Exception
	 */
	public Integer deleteOrderProductOption(OrderProductOption orderProductOption) throws Exception;
}
