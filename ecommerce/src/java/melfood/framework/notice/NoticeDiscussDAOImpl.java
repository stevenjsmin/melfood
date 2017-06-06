/** 
 * 2016 NoticeDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

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

}
