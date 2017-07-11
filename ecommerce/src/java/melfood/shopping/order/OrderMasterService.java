package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
public interface OrderMasterService {
    /**
     * 주문마스터상세 정보를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public OrderMaster getOrderMaster(OrderMaster orderMaster) throws Exception;


    /**
     * 주문마스터 정보 목록을 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public List<OrderMaster> getOrderMasters(OrderMaster orderMaster) throws Exception;



    /**
     * 주문마스터 정보 목록의 레코드 갯수를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public Integer getTotalCntForGetOrderMasters(OrderMaster orderMaster) throws Exception;

    /**
     * 주문마스터 정보를 등록한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public Integer insertOrderMaster(OrderMaster orderMaster) throws Exception;


    /**
     * 주문마스터 정보를 삭제한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public Integer deleteOrderMaster(OrderMaster orderMaster) throws Exception;

}
