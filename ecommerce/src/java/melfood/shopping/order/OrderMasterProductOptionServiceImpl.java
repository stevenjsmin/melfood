package melfood.shopping.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
@Service
public class OrderMasterProductOptionServiceImpl implements OrderMasterProductOptionService {

    @Autowired
    OrderMasterProductOptionDAO orderMasterProductOptionDAO;

    /**
     * 주문상품의 옵션목록을 가져온다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMasterProductOption> getOrderMasterProductOptions(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return null;
    }

    /**
     * 주문상품의 옵션(들)을 등록한다.
     *
     * @param orderMasterProductOptions
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProductOptions(List<OrderMasterProductOption> orderMasterProductOptions) throws Exception {
        return orderMasterProductOptionDAO.insertOrderMasterProductOptions(orderMasterProductOptions);
    }

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return null;
    }

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductOptionForNotNull(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return null;
    }

    /**
     * 주문상품의 옵션을 삭제한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return null;
    }
}
