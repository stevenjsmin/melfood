/** 
 * 2016 DeliveryCalendarDAO.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.delivery;

import java.util.List;

/**
 * 각 판매자마다 상품 배달을 위한 일정을 관리하는 DAO 인터페이스이다.
 * 
 * @author steven.min
 *
 */
public interface DeliveryCalendarDAO {
	public List<DeliveryCalendar> getDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer getTotalCntForDeliveryCalendars(DeliveryCalendar deliveryCalendar);
	
	public List<DeliveryCalendar> getDeliveryCalendarsForGuestOrder(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer getTotalCntForDeliveryCalendarsForGuestOrder(DeliveryCalendar deliveryCalendar);
	
	public Integer nextDeliverySeq(DeliveryCalendar deliveryCalendar);
	
	public Integer insertDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer modifyDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer modifyDeliveryCalendarForNotNull(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer deleteDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
}
