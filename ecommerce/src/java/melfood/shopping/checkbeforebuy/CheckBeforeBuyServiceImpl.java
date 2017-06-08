/** 
 * 2016 CheckBeforeBuyServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.checkbeforebuy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.code.Code;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * Notice Service Implementation
 *
 * @author steven.min
 *
 */
@Service
public class CheckBeforeBuyServiceImpl implements CheckBeforeBuyService {

	@Autowired
	private CheckBeforeBuyDAO checkBeforeBuyDAO;

	@Override
	public CheckBeforeBuy getCheckBeforeBuyInfo(int id) throws Exception {
		CheckBeforeBuy checkBeforeBuy = new CheckBeforeBuy(id);
		List<CheckBeforeBuy> list = getCheckBeforeBuyList(checkBeforeBuy);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<CheckBeforeBuy> getCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return checkBeforeBuyDAO.getCheckBeforeBuyList(checkBeforeBuy);
	}

	@Override
	public Integer getTotalCntForCheckBeforeBuyList(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return checkBeforeBuyDAO.getTotalCntForCheckBeforeBuyList(checkBeforeBuy);
	}

	@Override
	public Integer modifyCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return checkBeforeBuyDAO.modifyCheckBeforeBuy(checkBeforeBuy);
	}

	@Override
	public Integer modifyCheckBeforeBuyForNotNull(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return checkBeforeBuyDAO.modifyCheckBeforeBuyForNotNull(checkBeforeBuy);
	}

	@Override
	public Integer registCheckBeforeBuy(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return checkBeforeBuyDAO.registCheckBeforeBuy(checkBeforeBuy);
	}

	@Override
	public Integer deleteCheckBeforeBuy(Integer id) throws Exception {
		return checkBeforeBuyDAO.deleteCheckBeforeBuy(id);
	}

	@Override
	public CheckBeforeBuy getDefaultCheckBeforeBuy(String sellerId) throws Exception {
		CheckBeforeBuy temp = new CheckBeforeBuy();
		CheckBeforeBuy returnObj = null;
		temp.setSellerId(sellerId);
		temp.setIsDefault("Y");
		temp.setConfirmStatus("CONFIRMED");
		List<CheckBeforeBuy> list = this.getCheckBeforeBuyList(temp);
		if (list.size() > 0) {
			returnObj = list.get(0);
		}
		return returnObj;
	}

	@Override
	public List<Option> getCheckBeforeBuyForOption(CheckBeforeBuy checkBeforeBuy) throws Exception {
		return this.getCheckBeforeBuyForOption(checkBeforeBuy, 0);
	}

	@Override
	public List<Option> getCheckBeforeBuyForOption(CheckBeforeBuy checkBeforeBuy, int selectedId) throws Exception {
		CheckBeforeBuy temp = new CheckBeforeBuy();
		temp.setSellerId(checkBeforeBuy.getSellerId());
		temp.setConfirmStatus("CONFIRMED");

		List<CheckBeforeBuy> list = this.getCheckBeforeBuyList(temp);

		List<Option> options = new ArrayList<Option>();

		for (CheckBeforeBuy aObject : list) {
			if (aObject.getId() == selectedId && selectedId != 0) {
				options.add(new Option(new Integer(aObject.getId()).toString(), StringUtils.abbreviate(aObject.getSubject(), 15), true));
			} else {
				options.add(new Option(new Integer(aObject.getId()).toString(), StringUtils.abbreviate(aObject.getSubject(), 15), false));
			}
		}
		return options;
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property) {
		StringBuffer selectHtml = new StringBuffer("<select ");
		if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
		if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
		if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
		if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
		if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
		if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
		if (property.isMultipleSelect()) selectHtml.append("multiple='multiple' ");

		selectHtml.append("> \n");

		selectHtml.append("<option value=''> - SELECT - </option>\n");

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
