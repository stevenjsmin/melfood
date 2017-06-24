/** 
 * 2016 NoticeServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Notice Service Implementation
 *
 * @author steven.min
 *
 */
@Service
public class NoticeDiscussServiceImpl implements NoticeDiscussService {

	// private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);

	@Autowired
	private NoticeDiscussDAO noticeDAO;

	@Override
	public NoticeDiscuss getNoticeDiscussInfo(int id) throws Exception {
		NoticeDiscuss noticeDiscuss = new NoticeDiscuss();
		noticeDiscuss.setId(id);
		List<NoticeDiscuss> list = getNoticeDiscussList(noticeDiscuss);
		if (list != null && list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}
	}

	@Override
	public List<NoticeDiscuss> getNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.getNoticeDiscussList(noticeDiscuss);
	}

	@Override
	public Integer getTotalCntForNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.getTotalCntForNoticeDiscussList(noticeDiscuss);
	}

	@Override
	public List<NoticeDiscuss> getConversationList(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.getConversationList(noticeDiscuss);
	}

	@Override
	public Integer modifyNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.modifyNoticeDiscuss(noticeDiscuss);
	}

	@Override
	public Integer modifyNoticeDiscussForNotNull(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.modifyNoticeDiscussForNotNull(noticeDiscuss);
	}

	@Override
	public Integer registNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.registNoticeDiscuss(noticeDiscuss);
	}

	@Override
	public Integer deleteNoticeDiscuss(Integer id) throws Exception {
		return noticeDAO.deleteNoticeDiscuss(id);
	}

	/**
	 * writeFrom 와 writeTo에는 조회하고자 하는 사람의 ID가 동일하게 지정되는 경우 나와 관련된 모든 대화내용을 가져온다<br>
	 * writeFrom 와 writeTo에 각각 다른 사람이 지정되는 경우, 직접 작성자와 받을 사람을 지정하게하여 대화내용을 가져온다<br>
	 * 이 메소드는 일반 사용자에게 필요한 대화사항을 가저온다
	 *
	 * @param noticeDiscuss
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<NoticeDiscuss> getAllMyDiscussListForCustomer(NoticeDiscuss noticeDiscuss) throws Exception {
		return noticeDAO.getAllMyDiscussListForCustomer(noticeDiscuss);
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
		return noticeDAO.getAllMyDiscussListForCustomer(noticeDiscuss);
	}
}
