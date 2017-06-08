package melfood.framework.postcode;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

@Repository
public class PostcodeDAOImpl extends BaseDAO implements PostcodeDAO {

	@Override
	public List<Postcode> getPostcodes(Postcode postcode) throws Exception {
		return sqlSession.selectList("mySqlPostcodeDao.getPostcodes", postcode);
	}

	@Override
	public Integer getTotalCntForPostcodes(Postcode postcode) throws Exception {
		return sqlSession.selectOne("mySqlPostcodeDao.getTotalCntForPostcodes", postcode);
	}

	@Override
	public Integer insertPostcode(Postcode postcode) throws Exception {
		postcode.setSuburb(WordUtils.capitalizeFully(postcode.getSuburb()));
		return sqlSession.insert("mySqlPostcodeDao.insertPostcode", postcode);

	}

	@Override
	public Integer deletePostcode(Postcode postcode) throws Exception {
		return sqlSession.delete("mySqlPostcodeDao.deletePostcode", postcode);
	}

	@Override
	public Integer updatePostcode(Postcode postcode) throws Exception {
		postcode.setSuburb(WordUtils.capitalizeFully(postcode.getSuburb()));
		return sqlSession.update("mySqlPostcodeDao.updatePostcode", postcode);
	}

	@Override
	public Integer updatePostcodeForNotNull(Postcode postcode) throws Exception {
		postcode.setSuburb(WordUtils.capitalizeFully(postcode.getSuburb()));
		return sqlSession.update("mySqlPostcodeDao.updatePostcodeForNotNull", postcode);
	}

}
