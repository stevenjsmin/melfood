package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
public interface OrderMasterProductOptionService {
    /**
     * 주문상품의 옵션목록을 가져온다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public List<OrderMasterProductOption> getOrderMasterProductOptions(OrderMasterProductOption orderMasterProductOption) throws Exception;


    /**
     * 주문상품의 옵션(들)을 등록한다.
     *
     * @param orderMasterProductOptions
     * @return
     * @throws Exception
     */
    public Integer insertOrderMasterProductOptions(List<OrderMasterProductOption> orderMasterProductOptions) throws Exception;

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception;

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProductOptionForNotNull(OrderMasterProductOption orderMasterProductOption) throws Exception;

    /**
     * 주문상품의 옵션을 삭제한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer deleteOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception;
}
