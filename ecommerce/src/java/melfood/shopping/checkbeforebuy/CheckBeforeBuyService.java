/** 
 * 2016 CheckBeforeBuyDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.checkbeforebuy;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface CheckBeforeBuyService {
	public CheckBeforeBuy getCheckBeforeBuyInfo(int id) throws Exception;
	public List<CheckBeforeBuy> getCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public Integer getTotalCntForCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public Integer modifyCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public Integer modifyCheckBeforeBuyForNotNull(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public Integer registCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public Integer deleteCheckBeforeBuy(Integer id) throws Exception;
	
	public CheckBeforeBuy getDefaultCheckBeforeBuy(String sellerId) throws Exception;
	public List<Option> getCheckBeforeBuyForOption(CheckBeforeBuy checkBeforeBuy) throws Exception;
	public List<Option> getCheckBeforeBuyForOption(CheckBeforeBuy checkBeforeBuy, int selectedId) throws Exception;
	public String generateCmbx(List<Option> options, Properties property);
	
}
