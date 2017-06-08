package melfood.framework.postcode;

import java.util.List;

public interface PostcodeDAO {
	public List<Postcode> getPostcodes(Postcode postcode) throws Exception;
	public Integer getTotalCntForPostcodes(Postcode postcode) throws Exception;
	public Integer insertPostcode(Postcode postcode) throws Exception;
	public Integer deletePostcode(Postcode postcode) throws Exception;
	public Integer updatePostcode(Postcode postcode) throws Exception;
	public Integer updatePostcodeForNotNull(Postcode postcode) throws Exception;

}
