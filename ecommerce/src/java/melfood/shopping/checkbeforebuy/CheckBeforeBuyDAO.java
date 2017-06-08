/** 
 * 2016 CheckBeforeBuyDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.checkbeforebuy;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface CheckBeforeBuyDAO {

	public List<CheckBeforeBuy> getCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception;

	public Integer getTotalCntForCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception;
	

	public Integer modifyCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception;

	public Integer modifyCheckBeforeBuyForNotNull(CheckBeforeBuy checkBeforeBuy) throws Exception;

	public Integer registCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception;

	public Integer deleteCheckBeforeBuy(Integer id) throws Exception;
	
	public Integer setAsNotDefaultCheckBeforeBuy(String sellerId) throws Exception;
}
