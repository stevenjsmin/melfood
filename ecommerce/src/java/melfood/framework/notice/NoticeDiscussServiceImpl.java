/** 
 * 2016 NoticeServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
