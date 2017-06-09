/** 
 * 2016 ShoppingCartServiceImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.user.User;

/**
 * @author steven.min
 *
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartDAO shoppingCartDAO;

	@Override
	public ShoppingCart getShoppingCartProduct(ShoppingCart shoppingCart) throws Exception {
		List<ShoppingCart> shoppingCarts = this.getShoppingCartProducts(shoppingCart);
		if (shoppingCarts != null && shoppingCarts.size() > 0) {
			return shoppingCarts.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ShoppingCart> getShoppingCartProducts(ShoppingCart shoppingCart) throws Exception {
		return shoppingCartDAO.getShoppingCartProducts(shoppingCart);
	}

	@Override
	public Integer getTotalCntForShoppingCartProducts(ShoppingCart shoppingCart) {
		return shoppingCartDAO.getTotalCntForShoppingCartProducts(shoppingCart);
	}

	@Override
	public List<User> getProductSellers(String customerId) throws Exception {
		return shoppingCartDAO.getProductSellers(customerId);
	}

	/**
	 * 고객이 주문하려는 상품을 장바구니에 추가한다.<br>
	 * 상품을 장바구니에 추가한 후에는 추가된 레코드 ID를 반환한다.
	 * 
	 * @param shoppingCart
	 * @return 추가된 레코드 ID
	 * @throws Exception
	 */
	@Override
	public Integer addProductOnCart(ShoppingCart shoppingCart) throws Exception {
		return shoppingCartDAO.insertShoppingCart(shoppingCart);
	}

	@Override
	public Integer modifyShoppingCart(ShoppingCart shoppingCart) throws Exception {
		return shoppingCartDAO.modifyShoppingCart(shoppingCart);
	}

	@Override
	public Integer modifyShoppingCartForNotNull(ShoppingCart shoppingCart) throws Exception {
		return shoppingCartDAO.modifyShoppingCartForNotNull(shoppingCart);
	}

	@Override
	public Integer deleteShoppingCart(ShoppingCart shoppingCart) throws Exception {
		return shoppingCartDAO.deleteShoppingCart(shoppingCart);
	}

}
