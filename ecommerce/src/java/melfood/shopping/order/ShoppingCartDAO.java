/** 
 * 2016 ShoppingCartDAO.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import java.util.List;

import melfood.framework.user.User;

/**
 * @author steven.min
 *
 */
public interface ShoppingCartDAO {
	public List<ShoppingCart> getShoppingCartProducts(ShoppingCart shoppingCart) throws Exception;
	public Integer getTotalCntForShoppingCartProducts(ShoppingCart shoppingCart);

	/**
	 * 고객이 주문한 장바구니에 모든 상품판매자 목록을 가저온다.
	 * 
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public List<User> getProductSellers(String customerId) throws Exception;

	/**
	 * 고객이 주문하려는 상품을 장바구니에 추가한다.<br>
	 * 상품을 장바구니에 추가한 후에는 추가된 레코드 ID를 반환한다.
	 * 
	 * @param shoppingCart
	 * @return 추가된 레코드 ID
	 * @throws Exception
	 */
	public Integer insertShoppingCart(ShoppingCart shoppingCart) throws Exception;

	public Integer modifyShoppingCart(ShoppingCart shoppingCart) throws Exception;

	public Integer modifyShoppingCartForNotNull(ShoppingCart shoppingCart) throws Exception;

	public Integer deleteShoppingCart(ShoppingCart shoppingCart) throws Exception;
}
