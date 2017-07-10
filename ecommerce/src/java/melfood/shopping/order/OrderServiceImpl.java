package melfood.shopping.order;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steven on 10/7/17.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 세션에 저장된 사용자의 주문 내역을 가저온다. 주문내역이 없는 경우 빈 Map<String, OrderMaster>을 가저온다.
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, OrderMaster> getUserSessionOrderSet(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        SessionUserInfo sessionUser = null;
        String userId = null;

        Map<String, OrderMaster> sessionOrder = null;

        try {
            if (session != null) {
                sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
                sessionOrder = sessionUser.getSessionOrder();
            }

            if (sessionOrder == null) {
                sessionOrder = new HashMap<String, OrderMaster>();
                sessionUser.setSessionOrder(sessionOrder);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("세션으로부터 주문정보를 가저오는데 실패하였습니다.:" + e.getMessage());
        }

        return sessionOrder;
    }

    /**
     * 세션에 사용자의 새로운 주문울 추가한다.<br/>
     * 주문은 Map<String, OrderMaster> 구조에 저장되며 Map의 키는 "yyyy-MM-dd_HH:mm:ss" 형태로 저장된다.
     * <p>
     * 정상적으로 세션에 주문이 추가되면, 추가된 주문의 Key가 반환된다.
     *
     * @param request
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public String addUserSessionOrder(HttpServletRequest request, OrderMaster orderMaster) throws Exception {
        Map<String, OrderMaster> order = this.getUserSessionOrderSet(request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String orderKey = df.format(cal.getTime());
        order.put(orderKey, orderMaster);

        return orderKey;
    }

    /**
     * 세션에 저장된 사용자의 제거한다. 제거하려는 주문의 Key의 키는 "YYYY-MM-DD_HH:mm:ss" 형태로 전달되어야한다.
     *
     * @param request
     * @param sessOrderKey
     * @return
     * @throws Exception
     */
    @Override
    public String removeUserSessionOrder(HttpServletRequest request, String sessOrderKey) throws Exception {
        Map<String, OrderMaster> order = this.getUserSessionOrderSet(request);
        order.remove(sessOrderKey);

        return sessOrderKey;
    }

    /**
     * 세선에 저장된 주문중에 지정된 주문 키에 해당하는 주문정보를 가져온다.
     *
     * @param request
     * @param sessOrderKey
     * @return
     * @throws Exception
     */
    @Override
    public OrderMaster getUserSessionOrder(HttpServletRequest request, String sessOrderKey) throws Exception {
        Map<String, OrderMaster> order = this.getUserSessionOrderSet(request);

        return (OrderMaster) order.get(sessOrderKey);
    }
}
