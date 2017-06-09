/** 
 * 2015 CodeServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * @author Steven J.S Min
 * 
 */

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeDAO codeDAO;

	@Override
	public Code getCode(String category, String type, String value) throws Exception {
		List<Code> codes = this.getCodes(new Code(category, type, value));
		if (codes != null && codes.size() > 0) {
			return codes.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Code> getCodes(Code code) throws Exception {
		return codeDAO.getCodes(code);
	}

	@Override
	public Integer getTotalCntForCodes(Code code) {
		return codeDAO.getTotalCntForCodes(code);
	}

	@Override
	public List<Code> getCategories() throws Exception {
		return codeDAO.getCategories();
	}

	@Override
	public List<Code> getTypes(String category) throws Exception {
		return codeDAO.getTypes(category);
	}

	@Override
	public Integer deleteCode(Code code) throws Exception {
		return codeDAO.deleteCode(code);
	}

	@Override
	public Integer deleteCodes(List<Code> codes) throws Exception {
		return codeDAO.deleteCodes(codes);
	}

	@Override
	public Integer insertCode(Code code) throws Exception {
		return codeDAO.insertCode(code);
	}

	@Override
	public Integer modifyCode(Code code) throws Exception {
		return codeDAO.modifyCode(code);
	}

	@Override
	public Integer modifyCodeForNotNull(Code code) throws Exception {
		return codeDAO.modifyCodeForNotNull(code);
	}

	@Override
	public boolean existCode(Code code) throws Exception {
		return (this.getCode(code.getCategory(), code.getType(), code.getValue()) != null) ? true : false;
	}

	@Override
	public List<Option> getCategoryCmbxOptions() throws Exception {
		return this.getCategoryCmbxOptions(null);
	}

	@Override
	public List<Option> getCategoryCmbxOptions(String defaultSelectedValue) throws Exception {
		List<Code> codes = this.getCategories();

		List<Option> options = new ArrayList<Option>();

		for (Code code : codes) {
			if (StringUtils.equalsIgnoreCase(code.getCategory(), defaultSelectedValue)) {
				options.add(new Option(code.getCategory(), code.getCategory(), true));
			} else {
				options.add(new Option(code.getCategory(), code.getCategory(), false));
			}
		}
		return options;
	}

	@Override
	public List<Option> getTypeCmbxOptions(String category) throws Exception {
		return this.getTypeCmbxOptions(category, null);
	}

	@Override
	public List<Option> getTypeCmbxOptions(String category, String defaultSelectedValue) throws Exception {
		List<Code> codes = this.getTypes(category);
		if (codes == null) return null;

		List<Option> options = new ArrayList<Option>();

		for (Code code : codes) {
			if (StringUtils.equalsIgnoreCase(code.getType(), defaultSelectedValue)) {
				options.add(new Option(code.getType(), code.getType(), true));
			} else {
				options.add(new Option(code.getType(), code.getType(), false));
			}
		}
		return options;
	}

	@Override
	public List<Option> getValueCmbxOptions(String category, String type) throws Exception {
		return this.getValueCmbxOptions(category, type, null);
	}

	@Override
	public List<Option> getValueCmbxOptions(String category, String type, String defaultSelectedValue) throws Exception {
		Code aCode = new Code(category, type);
		aCode.setUseYn("Y");
		List<Code> codes = this.getCodes(aCode);

		List<Option> options = new ArrayList<Option>();

		for (Code code : codes) {
			if (StringUtils.equalsIgnoreCase(code.getValue(), defaultSelectedValue)) {
				options.add(new Option(code.getValue(), code.getLabel(), true));
			} else {
				options.add(new Option(code.getValue(), code.getLabel(), false));
			}
		}
		return options;
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property) {
		return this.generateCmbx(options, property, true);
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property, boolean placeholder) {
		StringBuffer selectHtml = new StringBuffer("<select ");
		if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
		if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
		if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
		if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
		if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
		if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
		if (property.isMultipleSelect()) selectHtml.append("multiple='multiple' ");

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

	@Override
	public String getLabel(String category, String type, String name) throws Exception {
		Code code = this.getCode(category, type, name);
		String value = "";
		if (code != null) {
			value = code.getValue();
			return value;

		} else {
			return null;
		}
	}

	@Override
	public Integer getLabelByInt(String category, String type, String name) throws Exception {
		Code code = this.getCode(category, type, name);
		String value = "";
		Integer retValue = 0;

		if (code != null) {
			value = code.getValue();

			try {
				retValue = Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
				retValue = 0;
			}
		}
		return retValue;
	}

	@Override
	public Boolean getLabelByBoolean(String category, String type, String name) throws Exception {
		Code code = this.getCode(category, type, name);
		String value = "";
		Boolean retValue = false;

		if (code != null) {
			value = code.getValue();

			try {
				if (StringUtils.equalsIgnoreCase(value, "Y") || StringUtils.equalsIgnoreCase(value, "YES") || StringUtils.equalsIgnoreCase(value, "T") || StringUtils.equalsIgnoreCase(value, "TRUE")
						|| StringUtils.equalsIgnoreCase(value, "1") || StringUtils.equalsIgnoreCase(value, "OK") || StringUtils.equalsIgnoreCase(value, "SUCESS")) {
					retValue = true;
				} else {
					retValue = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retValue;
	}

	@Override
	public List<String> getArrayLabelByString(String category, String type) throws Exception {
		return null;
	}

	@Override
	public List<Integer> getArrayLabelByInt(String category, String type) throws Exception {
		return null;
	}

	@Override
	public List<Boolean> getArrayLabelByBoolean(String category, String type) throws Exception {
		return null;
	}

}
