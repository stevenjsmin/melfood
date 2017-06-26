package melfood.framework.qna;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 26/6/17.
 */
@Repository
public class QnADAOImpl extends BaseDAO implements QnADAO {
    @Override
    public List<QnA> getQnAList(QnA qnA) throws Exception {
        return sqlSession.selectList("mySqlQnADao.getQnAList", qnA);
    }

    @Override
    public Integer getTotalCntForQnAList(QnA qnA) throws Exception {
        return sqlSession.selectOne("mySqlQnADao.getTotalCntForQnAList", qnA);
    }

    @Override
    public Integer deleteQnA(QnA qnA) throws Exception {
        return sqlSession.delete("mySqlQnADao.deleteQnA", qnA.getId());
    }

    @Override
    public Integer registQnA(QnA qnA) throws Exception {
        return sqlSession.insert("mySqlQnADao.registQnA", qnA);
    }

    @Override
    public Integer modifyQnA(QnA qnA) throws Exception {
        return sqlSession.update("mySqlQnADao.modifyQnA", qnA);
    }

    @Override
    public Integer modifyQnAForNotNull(QnA qnA) throws Exception {
        return sqlSession.update("mySqlQnADao.modifyQnAForNotNull", qnA);
    }
}
