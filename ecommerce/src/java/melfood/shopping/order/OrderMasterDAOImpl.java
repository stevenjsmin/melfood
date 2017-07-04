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
}