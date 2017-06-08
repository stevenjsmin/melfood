/** 
 * 2016 OrderProductOptionServiceImpl.java
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

/**
 * @author steven.min
 *
 */
@Service
public class OrderProductOptionServiceImpl implements OrderProductOptionService {

	@Autowired
	private OrderProductOptionDAO orderProductOptionDAO;

	@Override
	public OrderProductOption getOrderProductOption(OrderProductOption orderProductOption) throws Exception {
		List<OrderProductOption> orderProductOptions = this.getOrderProductOptions(orderProductOption);
		if (orderProductOptions != null && orderProductOptions.size() > 0) {
			return orderProductOptions.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<OrderProductOption> getOrderProductOptions(OrderProductOption orderProductOption) throws Exception {
		return orderProductOptionDAO.getOrderProductOptions(orderProductOption);
	}

	@Override
	public Integer getTotalCntOrderProductOptions(OrderProductOption orderProductOption) throws Exception {
		return orderProductOptionDAO.getTotalCntOrderProductOptions(orderProductOption);
	}

	@Override
	public Integer nextSeq(int orderItemId) throws Exception {
		return orderProductOptionDAO.nextSeq(orderItemId);
	}

	@Override
	public Integer insertOrderProductOption(List<OrderProductOption> orderProductOptions) throws Exception {
		return orderProductOptionDAO.insertOrderProductOption(orderProductOptions);
	}

	@Override
	public Integer modifyOrderProductOption(OrderProductOption orderProductOption) throws Exception {
		return orderProductOptionDAO.modifyOrderProductOption(orderProductOption);
	}

	@Override
	public Integer modifyOrderProductOptionForNotNull(OrderProductOption orderProductOption) throws Exception {
		return orderProductOptionDAO.modifyOrderProductOptionForNotNull(orderProductOption);
	}

	@Override
	public Integer deleteOrderProductOption(OrderProductOption orderProductOption) throws Exception {
		return orderProductOptionDAO.deleteOrderProductOption(orderProductOption);
	}

}
