package melfood.framework.postcode;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

public interface PostcodeService {

	public Postcode getPostcode(Postcode postcode) throws Exception;

	public List<Postcode> getPostcodes(Postcode postcode) throws Exception;

	public Integer getTotalCntForPostcodes(Postcode postcode) throws Exception;

	public Integer insertPostcode(Postcode postcode) throws Exception;

	public Integer deletePostcode(Postcode postcode) throws Exception;

	public Integer updatePostcode(Postcode postcode) throws Exception;

	public Integer updatePostcodeForNotNull(Postcode postcode) throws Exception;

	public boolean existPostcode(Postcode postcode) throws Exception;

	public String generateCmbx(String postcode, Properties property) throws Exception;

	public String generateCmbx(String postcode, String defaultSelectedValue, Properties property) throws Exception;

	public List<Option> getSuburbCmbxOptions(String postcode) throws Exception;

	public List<Option> getSuburbCmbxOptions(String postcode, String defaultSelectedValue) throws Exception;

	public String generateCmbx(List<Option> options, Properties property) throws Exception;

	public String generateCmbx(List<Option> options, Properties property, boolean placeholder) throws Exception;

}
