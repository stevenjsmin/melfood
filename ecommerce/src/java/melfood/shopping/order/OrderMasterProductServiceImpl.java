package melfood.shopping.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
@Service
public class OrderMasterProductServiceImpl implements OrderMasterProductService {

    @Autowired
    private OrderMasterProductDAO orderMasterProductDAO;

    @Autowired
    private OrderMasterProductOptionService orderMasterProductOptionService;

    /**
     * 주문상품목록을 가져온다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMasterProduct> getOrderMasterProducts(OrderMasterProduct orderMasterProduct) throws Exception {

        List<OrderMasterProductOption> orderMasterProductOptions = null;
        OrderMasterProductOption orderProductOption = null;
        List<OrderMasterProduct> orderMasterProducts = orderMasterProductDAO.getOrderMasterProducts(orderMasterProduct);
        for (int i = 0; i < orderMasterProducts.size(); i++) {
            orderProductOption = new OrderMasterProductOption();
            orderProductOption.setOrderMasterProductId(orderMasterProducts.get(i).getOrderMasterProductId());

            orderMasterProductOptions = orderMasterProductOptionService.getOrderMasterProductOptions(orderProductOption);
            orderMasterProducts.get(i).setOrderMasterProductOptionList(orderMasterProductOptions);
        }

        return orderMasterProducts;
    }

    /**
     * 주문상품(들)을 등록한다.<br/>
     * 상품을 등록하고 등록된 ID를 반환한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception {
        return orderMasterProductDAO.insertOrderMasterProduct(orderMasterProduct);
    }

    /**
     * 주문상품(들)을 등록한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProducts(List<OrderMasterProduct> orderMasterProduct) throws Exception {

        int updateCnt = 0;
        int insertedId = 0;

        try {
            List<OrderMasterProductOption> orderMasterProductOptionList = null;

            for (OrderMasterProduct masterProduct : orderMasterProduct) {
                insertedId = this.insertOrderMasterProduct(masterProduct);

                // Product 옵션에 Product ID를 설정한다.
                // Product 옵션을 저장한다.
                orderMasterProductOptionList = masterProduct.getOrderMasterProductOptionList();
                for (int i = 0; i < orderMasterProductOptionList.size(); i++) {
                    orderMasterProductOptionList.get(i).setOrderMasterProductId(insertedId);
                }
                if (orderMasterProductOptionList.size() > 0) orderMasterProductOptionService.insertOrderMasterProductOptions(orderMasterProductOptionList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return updateCnt;
    }

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception {
        return null;
    }

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductForNotNull(OrderMasterProduct orderMasterProduct) throws Exception {
        return null;
    }

    /**
     * 주문상품을 삭제한다.
     *
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception {
        return null;
    }
}
