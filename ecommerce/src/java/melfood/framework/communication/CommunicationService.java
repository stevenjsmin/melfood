package melfood.framework.communication;

import java.util.List;

/**
 * Created by Steven on 8/7/17.
 */
public interface CommunicationService {

    public List<Communication> getCommunicationList(Communication communication) throws Exception;

    public Integer getTotalCntForCommunicationList(Communication communication) throws Exception;

    public Integer deleteCommunication(Communication communication) throws Exception;

    /**
     * Notice, Discussion 또는 QnA글을 등록한다<br/>
     * SMS에 통지를 하기위해서는 notifySmsNo를 설정해준다.(한개이상의 SMS로 보내기위해서는 콤마(,)로 구분하여 설정한다.) <br/>
     * Email로 통지하기 위해서는 notifyEmail를 설정해준다.(한개이상의 Email로 보내기위해서는 콤마(,)로 구분하여 설정한다.)
     *
     * @param communication
     * @return
     * @throws Exception
     */
    public Integer registCommunication(Communication communication) throws Exception;

    public Integer modifyCommunication(Communication communication) throws Exception;

    public Integer modifyCommunicationForNotNull(Communication communication) throws Exception;

    public Communication getCommunication(String id) throws Exception;

    public Communication getCommunication(int id) throws Exception;

    public List<Communication> getMyCommunicationList(Communication communication) throws Exception;

    public Integer getTotalCntForGetMyCommunicationList(Communication communication) throws Exception;

}
