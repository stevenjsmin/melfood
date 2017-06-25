package melfood.framework.system;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Steven on 25/6/17.
 */
public class AwsSNSUtils {

    public static String sendMessage(String message, String mobileNumber) {
        AmazonSNSClient snsClient = AwsClients.getSNSClient();

        String phoneNumber = "+61" + StringUtils.substringAfter(mobileNumber, "0");
        // Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
        // <set SMS attributes>

        PublishResult result = snsClient.publish(
                new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber));

        return result.toString();
    }

}
