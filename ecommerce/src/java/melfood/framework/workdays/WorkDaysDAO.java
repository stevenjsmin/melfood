/** 
 * 2016 WorkDaysDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.workdays;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface WorkDaysDAO {

	public List<WorkDays> getList(WorkDays workDays) throws Exception;

	public Integer getTotalCntForList(WorkDays workDays) throws Exception;

	public Integer deleteWorkDays(WorkDays workDays) throws Exception;

	public Integer insertWorkDays(WorkDays workDays) throws Exception;

	public Integer updateWorkDays(WorkDays workDays) throws Exception;
}
