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


    /**
     * * 주문 영수증을 데이터베이스에 기록한다.
     *
     * @param orderMasterId
     * @return
     * @throws Exception
     */
    public int transferPaymentReceiptToAttachementFileDb(int orderMasterId) throws Exception;

    /**
     * 첨부된 Payment 영수증 파일 정보를 삭제한다.
     *
     * @param orderMasterId
     * @return
     * @throws Exception
     */
    public Integer removePaymentReceiptFileInfo(int orderMasterId) throws Exception;

    /**
     * 배송상태 수정<br/>
     * orderMasterId와 statusDelivery를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public Integer modifyStatusDelivery(OrderMaster orderMaster) throws Exception;

    /**
     * 결재상태 수정<br/>
     * orderMasterId와 statusPayment를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public Integer modifyStatusPayment(OrderMaster orderMaster) throws Exception;

}
