package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
public interface OrderMasterDAO {

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
     * 공동구매의 주문마스터 정보 목록을 가져온다.(Distincted by groupPurchaseId, groupPurchaseTitle)
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    public List<OrderMaster> getOrderMastersForGroupPurchaseCbx(OrderMaster orderMaster) throws Exception;


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
     * Payment 영수증 파일 정보를 갱신한다.
     *
     * @param orderMaster : orderMasterId 와 paymentAccTransferReceipt이 설정되어져야 한다.
     * @return
     * @throws Exception
     */
    public Integer modifyPaymentReceiptFileInfo(OrderMaster orderMaster) throws Exception;

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
