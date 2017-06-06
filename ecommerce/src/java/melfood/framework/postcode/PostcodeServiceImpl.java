package melfood.framework.postcode;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

@Service
public class PostcodeServiceImpl implements PostcodeService {

	@Autowired
	private PostcodeDAO postcodeDAO;

	@Override
	public Postcode getPostcode(Postcode postcode) throws Exception {
		List<Postcode> postcodes = this.getPostcodes(postcode);
		if (postcodes != null && postcodes.size() > 0) {
			return postcodes.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Postcode> getPostcodes(Postcode postcode) throws Exception {
		return postcodeDAO.getPostcodes(postcode);
	}

	@Override
	public Integer getTotalCntForPostcodes(Postcode postcode) throws Exception {
		return postcodeDAO.getTotalCntForPostcodes(postcode);
	}

	@Override
	public Integer insertPostcode(Postcode postcode) throws Exception {
		return postcodeDAO.insertPostcode(postcode);
	}

	@Override
	public Integer deletePostcode(Postcode postcode) throws Exception {
		return postcodeDAO.deletePostcode(postcode);
	}

	@Override
	public Integer updatePostcode(Postcode postcode) throws Exception {
		return postcodeDAO.updatePostcode(postcode);
	}

	@Override
	public Integer updatePostcodeForNotNull(Postcode postcode) throws Exception {
		return postcodeDAO.updatePostcodeForNotNull(postcode);

	}

	@Override
	public boolean existPostcode(Postcode postcode) throws Exception {
		return (this.getPostcode(postcode) != null) ? true : false;
	}

	@Override
	public String generateCmbx(String postcode, Properties property) throws Exception {
		List<Option> options = this.getSuburbCmbxOptions(postcode);

		return this.generateCmbx(options, property);
	}

	@Override
	public String generateCmbx(String postcode, String defaultSelectedValue, Properties property) throws Exception {
		List<Option> options = this.getSuburbCmbxOptions(postcode, defaultSelectedValue);

		return this.generateCmbx(options, property);
	}

	@Override
	public List<Option> getSuburbCmbxOptions(String postcode) throws Exception {
		return this.getSuburbCmbxOptions(postcode, null);
	}

	@Override
	public List<Option> getSuburbCmbxOptions(String postcode, String defaultSelectedValue) throws Exception {
		// Postcode temp = new Postcode(postcode);
		Postcode temp = new Postcode();
		temp.setPostcode(postcode);
		temp.setPagenationPageSize(100);

		List<Postcode> list = postcodeDAO.getPostcodes(temp);
		List<Option> options = new ArrayList<Option>();
		for (Postcode aPostcode : list) {

			if (StringUtils.equalsIgnoreCase(aPostcode.getSuburb(), defaultSelectedValue)) {
				options.add(new Option(aPostcode.getSuburb(), aPostcode.getSuburb(), true));
			} else {
				options.add(new Option(aPostcode.getSuburb(), aPostcode.getSuburb(), false));
			}
		}

		return options;
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property) throws Exception {
		return this.generateCmbx(options, property, true);
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property, boolean placeholder) throws Exception {
		StringBuffer selectHtml = new StringBuffer("<select ");
		if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
		if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
		if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
		if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
		if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
		if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
		selectHtml.append("> \n");

		if (placeholder) selectHtml.append("<option value=''> - SELECT - </option>\n");

		if (options != null) {

			for (Option option : options) {
				if (option.isSelected()) {
					selectHtml.append("<option value='" + option.getValue() + "' selected>" + option.getName() + "</option>\n");
				} else {
					selectHtml.append("<option value='" + option.getValue() + "'>" + option.getName() + "</option>\n");
				}
			}
		}
		selectHtml.append("</select>");

		return selectHtml.toString();
	}

}
