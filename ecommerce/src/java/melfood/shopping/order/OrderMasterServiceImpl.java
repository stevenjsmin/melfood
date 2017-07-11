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
     * 주문마스터 정보 목록을 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMaster> getOrderMasters(OrderMaster orderMaster) throws Exception {
        return null;
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
        return null;
    }

    /**
     * 주문마스터 정보를 등록한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMaster(OrderMaster orderMaster) throws Exception {
        int updateCnt = orderMasterDAO.insertOrderMaster(orderMaster);
        int orderMasterId = orderMaster.getOrderMasterId();

        List<OrderMasterProduct> orderMasterProduct = orderMaster.getOrderMasterProduct();
        for (int i = 0; i < orderMasterProduct.size(); i++) {
            orderMasterProduct.get(i).setOrderMasterId(orderMasterId);
        }
        orderMasterProductService.insertOrderMasterProducts(orderMasterProduct);

        return updateCnt;
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
