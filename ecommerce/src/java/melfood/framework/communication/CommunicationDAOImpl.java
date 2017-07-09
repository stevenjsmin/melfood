package melfood.framework.communication;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 8/7/17.
 */
@Repository
public class CommunicationDAOImpl extends BaseDAO implements CommunicationDAO {
    @Override
    public List<Communication> getCommunicationList(Communication communication) throws Exception {
        return sqlSession.selectList("mySqlCommunicationDao.getCommunicationList", communication);
    }

    @Override
    public Integer getTotalCntForCommunicationList(Communication communication) throws Exception {
        return sqlSession.selectOne("mySqlCommunicationDao.getTotalCntForCommunicationList", communication);
    }

    @Override
    public Integer deleteCommunication(Communication communication) throws Exception {
        return sqlSession.delete("mySqlCommunicationDao.deleteCommunication", communication.getId());
    }

    @Override
    public Integer registCommunication(Communication communication) throws Exception {
        return sqlSession.update("mySqlCommunicationDao.registCommunication", communication);
    }

    @Override
    public Integer modifyCommunication(Communication communication) throws Exception {
        return sqlSession.update("mySqlCommunicationDao.modifyCommunication", communication);
    }

    @Override
    public Integer modifyCommunicationForNotNull(Communication communication) throws Exception {
        return sqlSession.update("mySqlCommunicationDao.modifyCommunicationForNotNull", communication);
    }

    @Override
    public List<Communication> getMyCommunicationList(Communication communication) throws Exception {
        return sqlSession.selectList("mySqlCommunicationDao.getMyCommunicationList", communication);
    }

    @Override
    public Integer getTotalCntForGetMyCommunicationList(Communication communication) throws Exception {
        return sqlSession.selectOne("mySqlCommunicationDao.getTotalCntForGetMyCommunicationList", communication);
    }

    @Override
    public List<Communication> getMyCommunicationListWithPerson(Communication communication) throws Exception {
        return sqlSession.selectList("mySqlCommunicationDao.getMyCommunicationListWithPerson", communication);
    }
}
