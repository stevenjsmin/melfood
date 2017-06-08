/** 
 * 2016 CheckBeforeBuyDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.checkbeforebuy;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * Implementation for CheckBeforeBuyDAO
 * 
 * @author steven.min
 *
 */
@Repository
public class CheckBeforeBuyDAOImpl extends BaseDAO implements CheckBeforeBuyDAO {

	@Override
	public List<CheckBeforeBuy> getCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return sqlSession.selectList("mySqlCheckBeforeBuyDao.getCheckBeforeBuyList", checkBeforeBuy);
	}

	@Override
	public Integer getTotalCntForCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return sqlSession.selectOne("mySqlCheckBeforeBuyDao.getTotalCntForCheckBeforeBuyList", checkBeforeBuy);
	}

	@Override
	public Integer modifyCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception {
		if (StringUtils.equalsIgnoreCase(checkBeforeBuy.getIsDefault(), "Y")) {
			this.setAsNotDefaultCheckBeforeBuy(checkBeforeBuy.getSellerId());
		}

		return sqlSession.update("mySqlCheckBeforeBuyDao.modifyCheckBeforeBuy", checkBeforeBuy);
	}

	@Override
	public Integer modifyCheckBeforeBuyForNotNull(CheckBeforeBuy checkBeforeBuy) throws Exception {
		if (StringUtils.equalsIgnoreCase(checkBeforeBuy.getIsDefault(), "Y")) {
			this.setAsNotDefaultCheckBeforeBuy(checkBeforeBuy.getSellerId());
		}

		return sqlSession.update("mySqlCheckBeforeBuyDao.modifyCheckBeforeBuyForNotNull", checkBeforeBuy);
	}

	@Override
	public Integer registCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception {
		if (StringUtils.equalsIgnoreCase(checkBeforeBuy.getIsDefault(), "Y")) {
			this.setAsNotDefaultCheckBeforeBuy(checkBeforeBuy.getSellerId());
		}

		return sqlSession.insert("mySqlCheckBeforeBuyDao.registCheckBeforeBuy", checkBeforeBuy);
	}

	@Override
	public Integer deleteCheckBeforeBuy(Integer id) throws Exception {
		return sqlSession.delete("mySqlCheckBeforeBuyDao.deleteCheckBeforeBuy", id);
	}

	@Override
	public Integer setAsNotDefaultCheckBeforeBuy(String sellerId) throws Exception {
		return sqlSession.update("mySqlCheckBeforeBuyDao.setAsNotDefaultCheckBeforeBuy", sellerId);
	}

}
