package melfood.framework.qna;

import java.util.List;

/**
 * Created by Steven on 26/6/17.
 */
public interface QnADAO {

    public List<QnA> getQnAList(QnA qnA) throws Exception;

    public Integer getTotalCntForQnAList(QnA qnA) throws Exception;

    public Integer deleteQnA(QnA qnA) throws Exception;

    public Integer registQnA(QnA qnA) throws Exception;

    public Integer modifyQnA(QnA qnA) throws Exception;

    public Integer modifyQnAForNotNull(QnA qnA) throws Exception;
}