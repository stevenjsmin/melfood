/** 
 * 2016 MenuServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.auth.SessionUserInfo;

/**
 * Menu Service Implementation
 *
 * @author steven.min
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	// private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDAO menuDAO;

	@Override
	public List<Menu> getMenuAuthorizedList(SessionUserInfo sessionUser) throws Exception {
		String userId = sessionUser.getUser().getUserId();
		// String userRoleId = sessionUser.getUser().getPrimaryRoleId();
		// String userRoleName = sessionUser.getUser().getPrimaryRoleName();
		String userRoleName = null;

		Menu menu = new Menu();
		menu.setUseYn("Y");
		menu.setPagenationPageSize(9999);
		List<Menu> menuList = getMenuList(menu); // All activated menu list
		List<Menu> retMenuList = new ArrayList<Menu>();

		String allowedRoles;
		String allowedUsers;
		boolean allowedRole = false;
		boolean allowedUser = false;
		String[] temp = null;

		for (Menu aMenu : menuList) {
			allowedRole = false;
			allowedUser = false;

			if (!StringUtils.equalsIgnoreCase(aMenu.getName(), "SPACE")) {

				if (StringUtils.isBlank(aMenu.getAllowedRoles()) && StringUtils.isBlank(aMenu.getAllowedUsers())) {
					allowedRole = true;
					allowedUser = true;

				} else {
					allowedRoles = aMenu.getAllowedRoles();
					if (!StringUtils.isBlank(allowedRoles)) {
						temp = StringUtils.split(allowedRoles, ",");
						for (String var : temp) {
							if (StringUtils.equalsIgnoreCase(var, userRoleName)) {
								allowedRole = true;
							}
						}
					}

					allowedUsers = aMenu.getAllowedUsers();
					if (!StringUtils.isBlank(allowedUsers)) {
						temp = StringUtils.split(allowedUsers, ",");
						for (String var : temp) {
							if (StringUtils.equalsIgnoreCase(var, userId)) {
								allowedUser = true;
							}
						}
					}
				}

			} else {
				allowedRole = true;
				allowedUser = true;
			}

			if (allowedRole || allowedUser) {
				retMenuList.add(aMenu);
			}
		}

		return retMenuList;
	}

	@Override
	public Menu getMenuInfo(int menuId) throws Exception {
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		List<Menu> list = getMenuList(menu);
		if (list != null && list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}
	}

	@Override
	public List<Menu> getMenuList(Menu menu) throws Exception {
		return menuDAO.getMenuList(menu);
	}

	@Override
	public Integer getTotalCntForMenuList(Menu menu) throws Exception {
		return menuDAO.getTotalCntForMenuList(menu);
	}

	@Override
	public Integer modifyMenu(Menu menu) throws Exception {
		return menuDAO.modifyMenu(menu);
	}

	@Override
	public Integer registMenu(Menu menu) throws Exception {
		return menuDAO.registMenu(menu);
	}

	@Override
	public Integer deleteMenu(Integer menuId) throws Exception {
		return menuDAO.deleteMenu(menuId);
	}

}
