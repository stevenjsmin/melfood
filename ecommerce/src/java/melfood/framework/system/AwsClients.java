package melfood.framework.system;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import melfood.framework.Ctx;

/**
 * AWS에서 필요로하는 여러가지 Client 오브젝트를 반환한다.
 * <p>
 * Created by Steven on 25/6/17.
 */
public class AwsClients {

    public static AmazonSNSClient getSNSClient() {
        String accessKey = Ctx.getVar("SNS.SMS.ACCESS_KEY");
        String secretKey = Ctx.getVar("SNS.SMS.SECRET_KEY");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonSNSClient snsClient = new AmazonSNSClient(credentials);
        snsClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));

        return snsClient;
    }

}
