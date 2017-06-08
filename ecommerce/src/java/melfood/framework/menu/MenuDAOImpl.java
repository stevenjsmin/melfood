/** 
 * 2016 MenuDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * Implementation for MenuDAO
 * 
 * @author steven.min
 *
 */
@Repository
public class MenuDAOImpl extends BaseDAO implements MenuDAO {

	@Override
	public List<Menu> getMenuList(Menu menu) throws Exception {
		return sqlSession.selectList("mySqlMenuDao.getMenuList", menu);
	}

	@Override
	public Integer getTotalCntForMenuList(Menu menu) throws Exception {
		return sqlSession.selectOne("mySqlMenuDao.getTotalCntForMenuList", menu);
	}

	@Override
	public Integer modifyMenu(Menu menu) throws Exception {
		return sqlSession.update("mySqlMenuDao.modifyMenu", menu);
	}

	@Override
	public Integer registMenu(Menu menu) throws Exception {
		return sqlSession.insert("mySqlMenuDao.registMenu", menu);
	}

	@Override
	public Integer deleteMenu(Integer menuId) throws Exception {
		return sqlSession.delete("mySqlMenuDao.deleteMenu", menuId);
	}

}
