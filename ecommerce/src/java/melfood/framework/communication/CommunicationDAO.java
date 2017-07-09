package melfood.framework.communication;

import java.util.List;

/**
 * Created by Steven on 26/6/17.
 */
public interface CommunicationDAO {

    public List<Communication> getCommunicationList(Communication communication) throws Exception;

    public Integer getTotalCntForCommunicationList(Communication communication) throws Exception;

    public Integer deleteCommunication(Communication communication) throws Exception;

    public Integer registCommunication(Communication communication) throws Exception;

    public Integer modifyCommunication(Communication communication) throws Exception;

    public Integer modifyCommunicationForNotNull(Communication communication) throws Exception;

    public List<Communication> getMyCommunicationList(Communication communication) throws Exception;

    public Integer getTotalCntForGetMyCommunicationList(Communication communication) throws Exception;

}
