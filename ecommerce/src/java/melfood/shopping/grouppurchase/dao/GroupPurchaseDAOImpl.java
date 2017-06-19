package melfood.shopping.grouppurchase.dao;

import melfood.framework.core.BaseDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
@Repository
public class GroupPurchaseDAOImpl extends BaseDAO implements GroupPurchaseDAO {

    @Override
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.selectList("mySqlGroupPurchaseDao.getGroupPurchase", groupPurchase);
    }

    @Override
    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase) {
        return sqlSession.selectOne("mySqlGroupPurchaseDao.getTotalCntGroupPurchase", groupPurchase);
    }

    @Override
    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.delete("mySqlGroupPurchaseDao.deleteGroupPurchase", groupPurchase);
    }

    @Override
    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.insert("mySqlGroupPurchaseDao.insertGroupPurchase", groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseDao.modifyGroupPurchase", groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseDao.modifyGroupPurchaseForNotNull", groupPurchase);
    }
}
