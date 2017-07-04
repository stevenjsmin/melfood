package melfood.shopping.order;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
@Repository
public class OrderMasterProductOptionDAOImpl extends BaseDAO implements OrderMasterProductOptionDAO {

    /**
     * 주문상품의 옵션목록을 가져온다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMasterProductOption> getOrderMasterProductOptions(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return sqlSession.selectList("mySqlOrderMasterProductOptionDao.getOrderMasterProductOptions", orderMasterProductOption);
    }

    /**
     * 주문상품의 옵션(들)을 등록한다.
     *
     * @param orderMasterProductOptions
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMasterProductOptions(List<OrderMasterProductOption> orderMasterProductOptions) throws Exception {
        return sqlSession.insert("mySqlOrderMasterProductOptionDao.insertOrderMasterProductOptions", orderMasterProductOptions);
    }

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return sqlSession.update("modifyOrderMasterProductOption.insertOrderMasterProductOptions", orderMasterProductOption);
    }

    /**
     * 주문상품의 옵션을 수정한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyOrderMasterProductOptionForNotNull(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return sqlSession.update("mySqlOrderMasterProductOptionDao.modifyOrderMasterProductOptionForNotNull", orderMasterProductOption);
    }

    /**
     * 주문상품의 옵션을 삭제한다.
     *
     * @param orderMasterProductOption
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMasterProductOption(OrderMasterProductOption orderMasterProductOption) throws Exception {
        return sqlSession.delete("mySqlOrderMasterProductOptionDao.deleteOrderMasterProductOption", orderMasterProductOption);
    }
}
