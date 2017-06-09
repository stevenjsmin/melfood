/** 
 * 2016 OrderProductOptionDAOImpl.java
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

/**
 * @author steven.min
 *
 */
@Repository
public class OrderProductOptionDAOImpl extends BaseDAO implements OrderProductOptionDAO {

	@Override
	public List<OrderProductOption> getOrderProductOptions(OrderProductOption orderProductOption) throws Exception {
		return sqlSession.selectList("mySqlOrderProductOptionDao.getOrderProductOptions", orderProductOption);

	}

	@Override
	public Integer getTotalCntOrderProductOptions(OrderProductOption orderProductOption) throws Exception {
		return sqlSession.selectOne("mySqlOrderProductOptionDao.getTotalCntOrderProductOptions", orderProductOption);
	}

	@Override
	public Integer nextSeq(int orderItemId) throws Exception {
		return sqlSession.selectOne("mySqlOrderProductOptionDao.nextSeq", orderItemId);
	}

	@Override
	public Integer insertOrderProductOption(List<OrderProductOption> orderProductOptions) throws Exception {
		return sqlSession.insert("mySqlOrderProductOptionDao.insertOrderProductOptions", orderProductOptions);
	}

	@Override
	public Integer modifyOrderProductOption(OrderProductOption orderProductOption) throws Exception {
		return sqlSession.update("mySqlOrderProductOptionDao.modifyOrderProductOption", orderProductOption);
	}

	@Override
	public Integer modifyOrderProductOptionForNotNull(OrderProductOption orderProductOption) throws Exception {
		return sqlSession.update("mySqlOrderProductOptionDao.modifyOrderProductOptionForNotNull", orderProductOption);
	}

	@Override
	public Integer deleteOrderProductOption(OrderProductOption orderProductOption) throws Exception {
		return sqlSession.delete("mySqlOrderProductOptionDao.deleteOrderProductOption", orderProductOption);
	}

}
