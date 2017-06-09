/** 
 * 2016 ProductOptionServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * 제품 옵션 오퍼레이션 서비스 인터페이스 구현<br>
 *
 * @author steven.min
 *
 */
@Service
public class ProductOptionServiceImpl implements ProductOptionService {

	@Autowired
	ProductOptionDAO productOptionDAO;

	@Override
	public ProductOptionValue getProductOption(ProductOptionValue productOption) throws Exception {
		List<ProductOptionValue> productOptions = this.getProductOptions(productOption);
		if (productOptions != null && productOptions.size() > 0) {
			return productOptions.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ProductOptionValue> getProductOptions(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.getProductOptions(productOption);
	}

	@Override
	public Integer getTotalCntForProductOptions(ProductOptionValue productOption) {
		return productOptionDAO.getTotalCntForProductOptions(productOption);
	}

	@Override
	public Integer insertProductOption(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.insertProductOption(productOption);
	}

	@Override
	public Integer modifyProductOption(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.modifyProductOption(productOption);
	}

	@Override
	public Integer modifyProductOptionForNotNull(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.modifyProductOptionForNotNull(productOption);
	}

	@Override
	public Integer deleteProductOption(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.deleteProductOption(productOption);
	}

	@Override
	public boolean existProductOption(ProductOptionValue productOption) throws Exception {
		return (this.getProductOption(productOption) != null) ? true : false;
	}

	@Override
	public String generateCmbxForOption(Properties property, int prodId) throws Exception {
		return this.generateCmbxForOption(property, prodId, true);
	}

	@Override
	public String generateCmbxForOption(Properties property, int prodId, boolean placeholder) throws Exception {
		return this.generateCmbxForOption(property, prodId, 99999, placeholder);
	}

	@Override
	public String generateCmbxForOption(Properties property, int prodId, int defaultSelectedValue) throws Exception {
		return this.generateCmbxForOption(property, prodId, defaultSelectedValue, true);
	}

	@Override
	public String generateCmbxForOption(Properties property, int prodId, int defaultSelectedValue, boolean placeholder) throws Exception {
		ProductOptionValue productOption = new ProductOptionValue(prodId);
		productOption.setPagenationPage(0);
		productOption.setPagenationPageSize(99999);
		List<ProductOptionValue> productOptions = this.getProductOptions(productOption);

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

		if (productOptions != null) {

			for (ProductOptionValue aOption : productOptions) {
				if (aOption.getOptionSeq() == defaultSelectedValue) {
					selectHtml.append("<option value='" + aOption.getOptionSeq() + "' selected>" + aOption.getOptionItem() + "</option>\n");
				} else {
					selectHtml.append("<option value='" + aOption.getOptionSeq() + "'>" + aOption.getOptionItem() + "</option>\n");
				}
			}
		}

		return selectHtml.toString();
	}

	@Override
	public ProductOptionValue getProductOptionValue(ProductOptionValue productOption) throws Exception {
		List<ProductOptionValue> productOptionValues = this.getProductOptionValues(productOption);
		if (productOptionValues != null && productOptionValues.size() > 0) {
			return productOptionValues.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ProductOptionValue> getProductOptionValues(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.getProductOptionValues(productOption);
	}

	@Override
	public Integer getTotalCntForProductOptionValues(ProductOptionValue productOption) {
		return productOptionDAO.getTotalCntForProductOptionValues(productOption);
	}

	@Override
	public Integer insertProductOptionValue(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.insertProductOptionValue(productOption);
	}

	@Override
	public Integer modifyProductOptionValue(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.modifyProductOptionValue(productOption);
	}

	@Override
	public Integer modifyProductOptionValueForNotNull(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.modifyProductOptionValueForNotNull(productOption);
	}

	@Override
	public Integer deleteProductOptionValue(ProductOptionValue productOption) throws Exception {
		return productOptionDAO.deleteProductOptionValue(productOption);
	}

	@Override
	public boolean existProductOptionValue(ProductOptionValue productOption) throws Exception {
		return (this.getProductOptionValue(productOption) != null) ? true : false;
	}

	@Override
	public List<Option> getOptionsForOptionValue(int prodid, int optionSeq) throws Exception {
		return this.getOptionsForOptionValue(prodid, optionSeq, 99999);
	}

	@Override
	public List<Option> getOptionsForOptionValue(int prodid, int optionSeq, int defaultSelectedValue) throws Exception {
		ProductOptionValue productOption = new ProductOptionValue(prodid, optionSeq);
		productOption.setPagenationPage(0);
		productOption.setPagenationPageSize(99999);
		productOption.setUseYn("Y");
		List<ProductOptionValue> productOptions = this.getProductOptionValues(productOption);

		List<Option> optionValues = new ArrayList<Option>();

		if (productOptions != null) {

			for (ProductOptionValue aObject : productOptions) {
				if (aObject.getOptionSeq() == defaultSelectedValue) {
					optionValues.add(new Option(Integer.toString(aObject.getValueSeq()), aObject.getValueLabel(), true));
				} else {
					optionValues.add(new Option(Integer.toString(aObject.getValueSeq()), aObject.getValueLabel(), false));
				}
			}
		}

		return optionValues;
	}

	@Override
	public String generateCmbxForOptionValue(List<Option> optionValues, Properties property) throws Exception {
		return this.generateCmbxForOptionValue(optionValues, property, true);
	}

	@Override
	public String generateCmbxForOptionValue(List<Option> optionValues, Properties property, boolean placeholder) throws Exception {
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

		if (optionValues != null) {

			for (Option option : optionValues) {
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
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid) throws Exception {
		return this.generateCmbxForOptionAndValue(property, prodid, true);
	}

	@Override
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid, boolean placeholder) throws Exception {
		return this.generateCmbxForOptionAndValue(property, prodid, 0, placeholder);
	}

	@Override
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid, int optionSeq, boolean placeholder) throws Exception {

		List<ProductOptionGroup> optionGroupArray = new ArrayList<ProductOptionGroup>();

		StringBuffer htmlOption = new StringBuffer("");
		StringBuffer htmlOptionCmbx = new StringBuffer("");
		List<ProductOptionValue> optionItems = new ArrayList<ProductOptionValue>();
		List<Option> optionValueOptions = new ArrayList<Option>();

		// 01. 모든 옵션항목을 얻어온다.
		ProductOptionValue productOption = new ProductOptionValue(prodid);
		productOption.setPagenationPage(0);
		productOption.setPagenationPageSize(99999);

		if (optionSeq != 0) {
			productOption.setOptionSeq(optionSeq);
			optionItems = this.getProductOptions(productOption);
		} else {
			optionItems = this.getProductOptions(productOption);
		}

		for (ProductOptionValue optionItem : optionItems) {
			htmlOption = new StringBuffer("");
			if (StringUtils.equalsIgnoreCase(optionItem.getIsMandatory(), "Y")) {
				htmlOption.append("<span class=\"required\">* </span>");
			}
			htmlOption.append(optionItem.getOptionItem() + " : ");

			// 02. 옵션항목에 속한 모든 옵션 값을 가저온다.
			htmlOptionCmbx = new StringBuffer("");
			htmlOptionCmbx.append("<select ");
			htmlOptionCmbx.append("id='" + optionItem.getOptionItem() + "' ");
			htmlOptionCmbx.append("name='" + optionItem.getOptionItem() + "' ");
			if (StringUtils.isNotBlank(property.getCssClass())) htmlOptionCmbx.append("class='" + property.getCssClass() + "' ");
			if (StringUtils.isNotBlank(property.getCssStyle())) htmlOptionCmbx.append("style='" + property.getCssStyle() + "' ");
			if (StringUtils.equalsIgnoreCase(optionItem.getIsMandatory(), "Y")) {
				htmlOptionCmbx.append("isMandatory='Y' ");
			} else {
				htmlOptionCmbx.append("isMandatory='N' ");
			}
			htmlOptionCmbx.append("> \n");

			if (placeholder) htmlOptionCmbx.append("<option value=''> - SELECT - </option>\n");

			optionValueOptions = this.getOptionsForOptionValue(prodid, optionItem.getOptionSeq());
			for (Option optionValue : optionValueOptions) {
				htmlOptionCmbx.append("<option value='" + optionValue.getName() + "'>" + optionValue.getName() + "</option>\n");
			}
			htmlOptionCmbx.append("</select><br />");

			optionGroupArray.add(new ProductOptionGroup(htmlOption.toString(), htmlOptionCmbx.toString()));
		}

		return optionGroupArray;
	}

}
