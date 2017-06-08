/** 
 * 2016 MenuDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import java.util.List;

import melfood.framework.auth.SessionUserInfo;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface MenuService {
	public List<Menu> getMenuAuthorizedList(SessionUserInfo sessionUser) throws Exception;
	public Menu getMenuInfo(int menuId) throws Exception;
	public List<Menu> getMenuList(Menu menu) throws Exception;
	public Integer getTotalCntForMenuList(Menu menu) throws Exception;
	public Integer modifyMenu(Menu menu) throws Exception;
	public Integer registMenu(Menu menu) throws Exception;
	public Integer deleteMenu(Integer menuId) throws Exception;
}
