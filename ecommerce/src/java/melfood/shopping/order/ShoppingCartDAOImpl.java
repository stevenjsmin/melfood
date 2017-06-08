/** 
 * 2016 ShoppingCartDAOImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;
import melfood.framework.user.User;

/**
 * @author steven.min
 *
 */
@Repository
public class ShoppingCartDAOImpl extends BaseDAO implements ShoppingCartDAO {

	@Override
	public List<ShoppingCart> getShoppingCartProducts(ShoppingCart shoppingCart) throws Exception {
		return sqlSession.selectList("mySqlShoppingCartDao.getShoppingCartProducts", shoppingCart);
	}

	@Override
	public Integer getTotalCntForShoppingCartProducts(ShoppingCart shoppingCart) {
		return sqlSession.selectOne("mySqlShoppingCartDao.getTotalCntForShoppingCartProducts", shoppingCart);
	}

	@Override
	public List<User> getProductSellers(String customerId) throws Exception {
		ShoppingCart temp = new ShoppingCart();
		temp.setCustomerId(customerId);
		return sqlSession.selectList("mySqlShoppingCartDao.getProductSellers", temp);
	}

	@Override
	public Integer insertShoppingCart(ShoppingCart shoppingCart) throws Exception {
		int updateCnt = sqlSession.insert("mySqlShoppingCartDao.insertShoppingCart", shoppingCart); 
		return shoppingCart.getId();
	}

	@Override
	public Integer modifyShoppingCart(ShoppingCart shoppingCart) throws Exception {
		return sqlSession.update("mySqlShoppingCartDao.modifyShoppingCart", shoppingCart);
	}

	@Override
	public Integer modifyShoppingCartForNotNull(ShoppingCart shoppingCart) throws Exception {
		return sqlSession.update("mySqlShoppingCartDao.modifyShoppingCartForNotNull", shoppingCart);
	}

	@Override
	public Integer deleteShoppingCart(ShoppingCart shoppingCart) throws Exception {
		return sqlSession.delete("mySqlShoppingCartDao.deleteShoppingCart", shoppingCart);
	}

}
