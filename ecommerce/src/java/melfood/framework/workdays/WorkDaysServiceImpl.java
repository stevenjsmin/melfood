/** 
 * 2016 WorkDaysServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.workdays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Menu Service Implementation
 *
 * @author steven.min
 *
 */
@Service
public class WorkDaysServiceImpl implements WorkDaysService {

	// private static final Logger logger = LoggerFactory.getLogger(WorkDaysServiceImpl.class);

	@Autowired
	private WorkDaysDAO workDaysDAO;

	@Override
	public WorkDays getWorkDay(WorkDays workDays) throws Exception {
		if (StringUtils.isBlank(workDays.getCountry())) {
			workDays.setCountry("AU");
		}
		if (StringUtils.isBlank(workDays.getState())) {
			workDays.setState("VIC");
		}

		DateFormat dfYear = new SimpleDateFormat("yyyy");
		DateFormat dfMonth = new SimpleDateFormat("MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String currYear = dfYear.format(cal.getTime());
		String currMonth = dfMonth.format(cal.getTime());
		if (StringUtils.isBlank(workDays.getYear())) {
			workDays.setYear(currYear);
		}
		if (StringUtils.isBlank(workDays.getMonth())) {
			workDays.setMonth(currMonth);
		}

		List<WorkDays> list = getList(workDays);
		if (list != null && list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}
	}

	@Override
	public List<WorkDays> getList(WorkDays workDays) throws Exception {
		return workDaysDAO.getList(workDays);
	}

	@Override
	public Integer getTotalCntForList(WorkDays workDays) throws Exception {
		return workDaysDAO.getTotalCntForList(workDays);
	}

	@Override
	public Integer deleteWorkDays(WorkDays workDays) throws Exception {
		return workDaysDAO.deleteWorkDays(workDays);
	}

	@Override
	public Integer insertWorkDays(WorkDays workDays) throws Exception {
		return workDaysDAO.insertWorkDays(workDays);
	}

	@Override
	public Integer updateWorkDays(WorkDays workDays) throws Exception {
		return workDaysDAO.updateWorkDays(workDays);
	}

}
