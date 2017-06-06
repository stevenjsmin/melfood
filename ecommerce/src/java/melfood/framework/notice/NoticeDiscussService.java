/** 
 * 2016 NoticeDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface NoticeDiscussService {
	public NoticeDiscuss getNoticeDiscussInfo(int id) throws Exception;
	public List<NoticeDiscuss> getNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception;
	public Integer getTotalCntForNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception;
	public Integer modifyNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception;
	public Integer modifyNoticeDiscussForNotNull(NoticeDiscuss noticeDiscuss) throws Exception;
	public Integer registNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception;
	public Integer deleteNoticeDiscuss(Integer id) throws Exception;
}
