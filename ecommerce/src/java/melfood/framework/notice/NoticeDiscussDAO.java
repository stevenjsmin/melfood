/**
 * 2016 NoticeDiscussDAO.java
 * Created by steven.min
 * <p>
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
public interface NoticeDiscussDAO {

    public List<NoticeDiscuss> getNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception;

    public Integer getTotalCntForNoticeDiscussList(NoticeDiscuss noticeDiscuss) throws Exception;

    public List<NoticeDiscuss> getConversationList(NoticeDiscuss noticeDiscuss) throws Exception;

    public Integer modifyNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception;

    public Integer modifyNoticeDiscussForNotNull(NoticeDiscuss noticeDiscuss) throws Exception;

    public Integer registNoticeDiscuss(NoticeDiscuss noticeDiscuss) throws Exception;

    public Integer deleteNoticeDiscuss(Integer id) throws Exception;

    /**
     * writeFrom 와 writeTo에는 조회하고자 하는 사람의 ID가 동일하게 지정되는 경우 나와 관련된 모든 대화내용을 가져온다<br>
     * writeFrom 와 writeTo에 각각 다른 사람이 지정되는 경우, 직접 작성자와 받을 사람을 지정하게하여 대화내용을 가져온다<br>
     * 이 메소드는 일반 사용자에게 필요한 대화사항을 가저온다
     *
     * @param noticeDiscuss
     * @return
     * @throws Exception
     */
    public List<NoticeDiscuss> getAllMyDiscussListForCustomer(NoticeDiscuss noticeDiscuss) throws Exception;

    /**
     * writeFrom 와 writeTo에는 조회하고자 하는 사람의 ID가 동일하게 지정되는 경우 나와 관련된 모든 공지내용을 가져온다<br>
     * writeFrom 와 writeTo에 각각 다른 사람이 지정되는 경우, 직접 작성자와 받을 사람을 지정하게하여 공지내용을 가져온다
     *
     * @param noticeDiscuss
     * @return
     * @throws Exception
     */
    public List<NoticeDiscuss> getAllMyNoticeListForSeller(NoticeDiscuss noticeDiscuss) throws Exception;

}
