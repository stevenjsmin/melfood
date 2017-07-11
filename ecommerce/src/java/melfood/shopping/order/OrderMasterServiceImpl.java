package melfood.shopping.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Autowired
    private OrderMasterProductDAO orderMasterProductDAO;

    @Autowired
    private OrderMasterProductService orderMasterProductService;

    @Autowired
    private OrderMasterProductOptionDAO orderMasterProductOptionDAO;


    /**
     * 주문마스터상세 정보를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public OrderMaster getOrderMaster(OrderMaster orderMaster) throws Exception {
        List<OrderMaster> list = this.getOrderMasters(orderMaster);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 주문마스터 정보 목록을 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMaster> getOrderMasters(OrderMaster orderMaster) throws Exception {

        OrderMaster aOrder = null;
        OrderMasterProduct orderMasterProduct = null;
        List<OrderMasterProduct> orderMasterProducts = null;
        List<OrderMaster> orderMasters = orderMasterDAO.getOrderMasters(orderMaster);

        if (!orderMaster.isLazyLoad()) {

            for (int i = 0; i < orderMasters.size(); i++) {
                // 소속된 모든 하위 상품 목록을 가저온다.
                orderMasterProduct = new OrderMasterProduct();
                orderMasterProduct.setOrderMasterId(orderMasters.get(i).getOrderMasterId());
                orderMasterProducts = orderMasterProductService.getOrderMasterProducts(orderMasterProduct);

                orderMasters.get(i).setOrderMasterProduct(orderMasterProducts);
            }
        }

        return orderMasters;
    }

    /**
     * 주문마스터 정보 목록의 레코드 갯수를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer getTotalCntForGetOrderMasters(OrderMaster orderMaster) throws Exception {
        return orderMasterDAO.getTotalCntForGetOrderMasters(orderMaster);
    }

    /**
     * 주문마스터 정보를 등록한다.<br/>
     * 등록된후 등록된 주문 번호를 반환한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMaster(OrderMaster orderMaster) throws Exception {
        int updateCnt = orderMasterDAO.insertOrderMaster(orderMaster);
        int insertedId = orderMaster.getOrderMasterId();

        List<OrderMasterProduct> orderMasterProduct = orderMaster.getOrderMasterProduct();
        for (int i = 0; i < orderMasterProduct.size(); i++) {
            orderMasterProduct.get(i).setOrderMasterId(insertedId);
        }
        orderMasterProductService.insertOrderMasterProducts(orderMasterProduct);

        return insertedId;
    }

    /**
     * 주문마스터 정보를 삭제한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMaster(OrderMaster orderMaster) throws Exception {
        return null;
    }
}
