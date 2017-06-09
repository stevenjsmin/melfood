/** 
 * 2016 Menu.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.menu;

import melfood.framework.common.dto.BaseDto;

/**
 * DTO class for Menu
 *
 * @author steven.min
 *
 */
public class Menu extends BaseDto {
	private int menuId;
	private int seq;
	private String type;
	private String name;
	private int parentMenuId;
	private String allowedRoles;
	private String allowedUsers;
	private String htmlObjId;
	private String htmlHref;
	private String htmlOnclick;
	private String htmlClass;
	private String htmlCss;
	private String useYn;
	private String comment;
	private String creator;
	private String createDatetime;
	private String modifyDatetime;
	
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(String allowedRoles) {
		this.allowedRoles = allowedRoles;
	}

	public String getAllowedUsers() {
		return allowedUsers;
	}

	public void setAllowedUsers(String allowedUsers) {
		this.allowedUsers = allowedUsers;
	}

	public String getHtmlObjId() {
		return htmlObjId;
	}

	public void setHtmlObjId(String htmlObjId) {
		this.htmlObjId = htmlObjId;
	}

	public String getHtmlHref() {
		return htmlHref;
	}

	public void setHtmlHref(String htmlHref) {
		this.htmlHref = htmlHref;
	}

	public String getHtmlOnclick() {
		return htmlOnclick;
	}

	public void setHtmlOnclick(String htmlOnclick) {
		this.htmlOnclick = htmlOnclick;
	}

	public String getHtmlClass() {
		return htmlClass;
	}

	public void setHtmlClass(String htmlClass) {
		this.htmlClass = htmlClass;
	}

	public String getHtmlCss() {
		return htmlCss;
	}

	public void setHtmlCss(String htmlCss) {
		this.htmlCss = htmlCss;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}


}
