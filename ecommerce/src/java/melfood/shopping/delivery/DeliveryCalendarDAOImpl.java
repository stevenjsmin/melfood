/** 
 * 2016 DeliveryCalendarDAOImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.delivery;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author steven.min
 *
 */
@Repository
public class DeliveryCalendarDAOImpl extends BaseDAO implements DeliveryCalendarDAO {

	@Override
	public List<DeliveryCalendar> getDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception {
		return sqlSession.selectList("mySqlDeliveryCalendarDao.getDeliveryCalendars", deliveryCalendar);
	}

	@Override
	public Integer getTotalCntForDeliveryCalendars(DeliveryCalendar deliveryCalendar) {
		return sqlSession.selectOne("mySqlDeliveryCalendarDao.getTotalCntForDeliveryCalendars", deliveryCalendar);
	}

	@Override
	public Integer nextDeliverySeq(DeliveryCalendar deliveryCalendar) {
		return sqlSession.selectOne("mySqlDeliveryCalendarDao.nextDeliverySeq", deliveryCalendar);
	}

	@Override
	public Integer insertDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		if (StringUtils.isBlank(deliveryCalendar.getSellerId()) || StringUtils.isBlank(deliveryCalendar.getYyyyMmDd())) {
			throw new Exception("SELLER_ID or YYYY_MM_DD are mandatory fields for insert");
		}

		int nextSeq = sqlSession.selectOne("mySqlDeliveryCalendarDao.nextDeliverySeq", deliveryCalendar);
		deliveryCalendar.setDeliverySeq(nextSeq);
		return sqlSession.insert("mySqlDeliveryCalendarDao.insertDeliveryCalendar", deliveryCalendar);
	}

	@Override
	public Integer modifyDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		return sqlSession.update("mySqlDeliveryCalendarDao.modifyDeliveryCalendar", deliveryCalendar);
	}

	@Override
	public Integer modifyDeliveryCalendarForNotNull(DeliveryCalendar deliveryCalendar) throws Exception {
		return sqlSession.update("mySqlDeliveryCalendarDao.modifyDeliveryCalendarForNotNull", deliveryCalendar);
	}

	@Override
	public Integer deleteDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		return sqlSession.delete("mySqlDeliveryCalendarDao.deleteDeliveryCalendar", deliveryCalendar);
	}

	@Override
	public List<DeliveryCalendar> getDeliveryCalendarsForGuestOrder(DeliveryCalendar deliveryCalendar) throws Exception {
		return sqlSession.selectList("mySqlDeliveryCalendarDao.getDeliveryCalendarsForGuestOrder", deliveryCalendar);
	}

	@Override
	public Integer getTotalCntForDeliveryCalendarsForGuestOrder(DeliveryCalendar deliveryCalendar) {
		return sqlSession.selectOne("mySqlDeliveryCalendarDao.getTotalCntForDeliveryCalendarsForGuestOrder", deliveryCalendar);
	}
	
	

}
