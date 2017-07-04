package melfood.shopping.order;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
@Repository
public class OrderMasterProductDAOImpl extends BaseDAO implements OrderMasterProductDAO {
    /**
     * 주문상품목록을 가져온다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMasterProduct> getOrderMasterProducts(OrderMasterProduct orderMasterProductOption) throws Exception {
        return sqlSession.selectList("mySqlOrderMasterProductDao.getOrderMasterProducts", orderMasterProductOption);
    }

    /**
     * 주문상품(들)을 등록한다.
     *
     * @param orderMasterProductOptions
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProducts(List<OrderMasterProduct> orderMasterProductOptions) throws Exception {
        return sqlSession.insert("mySqlOrderMasterProductDao.insertOrderMasterProducts", orderMasterProductOptions);
    }

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProduct(OrderMasterProduct orderMasterProductOption) throws Exception {
        return sqlSession.update("mySqlOrderMasterProductDao.modifyOrderMasterProduct", orderMasterProductOption);
    }

    /**
     * 주문상품을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductForNotNull(OrderMasterProduct orderMasterProductOption) throws Exception {
        return sqlSession.update("mySqlOrderMasterProductDao.modifyOrderMasterProductForNotNull", orderMasterProductOption);
    }

    /**
     * 주문상품을 삭제한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMasterProduct(OrderMasterProduct orderMasterProductOption) throws Exception {
        return sqlSession.delete("mySqlOrderMasterProductDao.deleteOrderMasterProduct", orderMasterProductOption);
    }
}
