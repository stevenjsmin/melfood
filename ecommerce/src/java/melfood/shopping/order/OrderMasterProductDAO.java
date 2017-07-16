package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
public interface OrderMasterProductDAO {
    /**
     * 주문상품목록을 가져온다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public List<OrderMasterProduct> getOrderMasterProducts(OrderMasterProduct orderMasterProductOption) throws Exception;

    /**
     * 주문상품을 등록한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer insertOrderMasterProduct(OrderMasterProduct orderMasterProductOption) throws Exception;

    /**
     * 주문상품(들)을 등록한다.
     *
     * @param orderMasterProductOptions
     * @return
     * @throws Exception
     */
    public Integer insertOrderMasterProduct(List<OrderMasterProduct> orderMasterProductOptions) throws Exception;

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProduct(OrderMasterProduct orderMasterProductOption) throws Exception;

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProductForNotNull(OrderMasterProduct orderMasterProductOption) throws Exception;

    /**
     * 주문상품을 삭제한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    public Integer deleteOrderMasterProduct(OrderMasterProduct orderMasterProductOption) throws Exception;
}
