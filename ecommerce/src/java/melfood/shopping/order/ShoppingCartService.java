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
public interface ShoppingCartService {

	/**
	 * 장바구니에 담겨져있는 주문 하나의 정보를 가저온다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public ShoppingCart getShoppingCartProduct(ShoppingCart shoppingCart) throws Exception;

	/**
	 * 장바구니에 담겨져있는 주문 목록(상품)을 가저온다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public List<ShoppingCart> getShoppingCartProducts(ShoppingCart shoppingCart) throws Exception;

	/**
	 * 장바구니에 담겨져있는 주문 목록(상품)을 가저온다.
	 * 
	 * @param shoppingCart
	 * @return
	 */
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
	 * 고객의 주문을 장바구니에 담는다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public Integer addProductOnCart(ShoppingCart shoppingCart) throws Exception;

	/**
	 * 장바구니 정보를 수정한다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public Integer modifyShoppingCart(ShoppingCart shoppingCart) throws Exception;

	/**
	 * 장바구니 정보를 수정한다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public Integer modifyShoppingCartForNotNull(ShoppingCart shoppingCart) throws Exception;

	/**
	 * 장바구니의 정보를 삭제한다.
	 * 
	 * @param shoppingCart
	 * @return
	 * @throws Exception
	 */
	public Integer deleteShoppingCart(ShoppingCart shoppingCart) throws Exception;
}
