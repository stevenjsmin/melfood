package melfood.shopping.survey;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by Steven on 14/7/17.
 */
@Repository
public class PreferGrpPurchaseMarketDayDAOImpl extends BaseDAO implements PreferGrpPurchaseMarketDayDAO {

    @Override
    public Integer insertPreferGrpPurchaseMarketDay(PreferGrpPurchaseMarketDay preferGrpPurchaseMarkeDay) throws Exception {
        return sqlSession.insert("mySqlPreferGrpPurchaseMarketDayDao.insertPreferGrpPurchaseMarketDay", preferGrpPurchaseMarkeDay);
    }

}
