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
     * @param orderMasterProduct
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMasterProduct> getOrderMasterProducts(OrderMasterProduct orderMasterProduct) throws Exception {
        return sqlSession.selectList("mySqlOrderMasterProductDao.getOrderMasterProducts", orderMasterProduct);
    }


    /**
     * 주문상품(들)을 등록한다.<br/>
     * 상품을 등록하고 등록된 ID를 반환한다.
     *
     * @param orderMasterProduct
     * @return 등록된ID
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProduct(OrderMasterProduct orderMasterProduct) throws Exception {
        int updateCnt  = sqlSession.insert("mySqlOrderMasterProductDao.insertOrderMasterProduct", orderMasterProduct);
        return orderMasterProduct.getOrderMasterProductId();
    }


    /**
     * 주문상품(들)을 등록한다.
     *
     *
     * @param orderMasterProducts
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProduct(List<OrderMasterProduct> orderMasterProducts) throws Exception {
        return sqlSession.insert("mySqlOrderMasterProductDao.insertOrderMasterProduct", orderMasterProducts);
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
        return sqlSession.update("mySqlOrderMasterProductDao.modifyOrderMasterProduct", orderMasterProduct);
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
        return sqlSession.update("mySqlOrderMasterProductDao.modifyOrderMasterProductForNotNull", orderMasterProduct);
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
        return sqlSession.delete("mySqlOrderMasterProductDao.deleteOrderMasterProduct", orderMasterProduct);
    }
}
