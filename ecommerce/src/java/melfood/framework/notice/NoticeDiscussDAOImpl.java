/** 
 * 2016 NoticeDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for NoticeDiscussDAO
 * 
 * @author steven.min
 *
 */
@Repository
public class NoticeDiscussDAOImpl extends BaseDAO implements NoticeDiscussDAO {

	@Override
	public List<NoticeDiscuss> getNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.selectList("mySqlNoticeDiscussDao.getNoticeDiscussList", noticeDiscuss);
	}

	@Override
	public Integer getTotalCntForNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.selectOne("mySqlNoticeDiscussDao.getTotalCntForNoticeDiscussList", noticeDiscuss);
	}

	@Override
	public List<NoticeDiscuss> getConversationList(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.selectList("mySqlNoticeDiscussDao.getConversationList", noticeDiscuss);
	}
	
	@Override
	public Integer modifyNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.update("mySqlNoticeDiscussDao.modifyNoticeDiscuss", noticeDiscuss);
	}

	@Override
	public Integer modifyNoticeDiscussForNotNull(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.update("mySqlNoticeDiscussDao.modifyNoticeDiscussForNotNull", noticeDiscuss);
	}

	@Override
	public Integer registNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.insert("mySqlNoticeDiscussDao.registNoticeDiscuss", noticeDiscuss);
	}

	@Override
	public Integer deleteNoticeDiscuss(Integer id) throws Exception {
		return sqlSession.delete("mySqlNoticeDiscussDao.deleteNoticeDiscuss", id);
	}

	/**
	 * writeFrom 와 writeTo에는 조회하고자 하는 사람의 ID가 동일하게 지정되는 경우 나와 관련된 모든 대화내용을 가져온다<br>
	 * writeFrom 와 writeTo에 각각 다른 사람이 지정되는 경우, 직접 작성자와 받을 사람을 지정하게하여 대화내용을 가져온다
	 *
	 * @param noticeDiscuss
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<NoticeDiscuss> getAllMyDiscussListForCustomer(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.selectList("mySqlNoticeDiscussDao.getAllMyDiscussListForCustomer", noticeDiscuss);
	}

	/**
	 * writeFrom 와 writeTo에는 조회하고자 하는 사람의 ID가 동일하게 지정되는 경우 나와 관련된 모든 공지내용을 가져온다<br>
	 * writeFrom 와 writeTo에 각각 다른 사람이 지정되는 경우, 직접 작성자와 받을 사람을 지정하게하여 공지내용을 가져온다<br>
	 * 이 메소드는 판매자(공동구매자)에게 필요한 공지사항을 가저온다(일반 공지사항 포함)
	 *
	 * @param noticeDiscuss
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<NoticeDiscuss> getAllMyNoticeListForSeller(NoticeDiscuss noticeDiscuss) throws Exception {
		return sqlSession.selectList("mySqlNoticeDiscussDao.getAllMyNoticeListForSeller", noticeDiscuss);
	}
}
