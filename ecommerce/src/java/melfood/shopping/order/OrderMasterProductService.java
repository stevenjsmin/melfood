package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
public interface OrderMasterProductService {
    /**
     * 주문상품목록을 가져온다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    public List<OrderMasterProduct> getOrderMasterProducts(OrderMasterProduct orderMasterProduct) throws Exception;

    /**
     * 주문상품을 등록한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    public Integer insertOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception;

    /**
     * 주문상품(들)을 등록한다.
     *
     * @param orderMasterProducts
     * @return
     * @throws Exception
     */
    public Integer insertOrderMasterProducts(List<OrderMasterProduct> orderMasterProducts) throws Exception;

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception;

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    public Integer modifyOrderMasterProductForNotNull(OrderMasterProduct orderMasterProduct) throws Exception;

    /**
     * 주문상품을 삭제한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    public Integer deleteOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception;
}
