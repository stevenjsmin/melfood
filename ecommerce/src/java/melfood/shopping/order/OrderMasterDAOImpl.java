package melfood.shopping.order;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
@Repository
public class OrderMasterDAOImpl extends BaseDAO implements OrderMasterDAO {
    /**
     * 주문마스터 정보 목록을 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMaster> getOrderMasters(OrderMaster orderMaster) throws Exception {
        // TOTO : 옵션테이블에서  Sum-up해야 할 것이 있으면 처리한다
        return sqlSession.selectList("mySqlOrderMasterDao.getOrderMasters", orderMaster);
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
        return sqlSession.selectOne("mySqlOrderMasterDao.getTotalCntForGetOrderMasters", orderMaster);
    }

    /**
     * 공동구매의 주문마스터 정보 목록을 가져온다.(Distincted by groupPurchaseId, groupPurchaseTitle)
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMaster> getOrderMastersForGroupPurchaseCbx(OrderMaster orderMaster) throws Exception {
        return sqlSession.selectList("mySqlOrderMasterDao.getOrderMastersForGroupPurchaseCbx", orderMaster);
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
        return sqlSession.insert("mySqlOrderMasterDao.insertOrderMaster", orderMaster);
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
        return sqlSession.delete("mySqlOrderMasterDao.deleteOrderMaster", orderMaster);
    }

    /**
     * Payment 영수증 파일 정보를 갱신한다.
     *
     * @param orderMaster : orderMasterId 와 paymentAccTransferReceipt이 설정되어져야 한다.
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyPaymentReceiptFileInfo(OrderMaster orderMaster) throws Exception {
        return sqlSession.update("mySqlOrderMasterDao.modifyPaymentReceiptFileInfo", orderMaster);
    }

    /**
     * 배송상태 수정<br/>
     * orderMasterId와 statusDelivery를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyStatusDelivery(OrderMaster orderMaster) throws Exception {
        return sqlSession.update("mySqlOrderMasterDao.modifyStatusDelivery", orderMaster);
    }

    /**
     * 결재상태 수정<br/>
     * orderMasterId와 statusPayment를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyStatusPayment(OrderMaster orderMaster) throws Exception {
        return sqlSession.update("mySqlOrderMasterDao.modifyStatusPayment", orderMaster);
    }
}
