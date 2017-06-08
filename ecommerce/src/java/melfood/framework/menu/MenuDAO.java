/** 
 * 2016 MenuDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface MenuDAO {
	
	public List<Menu> getMenuList(Menu Menu) throws Exception;

	public Integer getTotalCntForMenuList(Menu menu) throws Exception;

	public Integer modifyMenu(Menu menu) throws Exception;

	public Integer registMenu(Menu menu) throws Exception;

	public Integer deleteMenu(Integer menuId) throws Exception;
}
