package melfood.shopping.order;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Steven on 10/7/17.
 */
public interface OrderService {

    static final String SESSION_ORDER_POSTFIX = ":USER_SESSION_ORDER";

    /**
     * 세션에 저장된 사용자의 주문 내역을 가저온다. 주문내역이 없는 경우 빈 Map을 가저온다.
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, OrderMaster> getUserSessionOrderSet(HttpServletRequest request) throws Exception;

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
    public String addUserSessionOrder(HttpServletRequest request, OrderMaster orderMaster) throws Exception;


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

    /**
     * 세션에 저장된 사용자의 제거한다. 제거하려는 주문의 Key의 키는 "yyyy-MM-dd_HH:mm:ss" 형태로 전달되어야한다.
     *
     * @param request
     * @param sessOrderKey
     * @return
     * @throws Exception
     */
    public String removeUserSessionOrder(HttpServletRequest request, String sessOrderKey) throws Exception;


    /**
     * 세선에 저장된 주문중에 지정된 주문 키에 해당하는 주문정보를 가져온다.
     *
     * @param request
     * @param sessOrderKey
     * @return
     * @throws Exception
     */
    public OrderMaster getUserSessionOrder(HttpServletRequest request, String sessOrderKey) throws Exception;

}
