/** 
 * 2016 Option.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.uitl.html;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class Option {
	private String value;
	private String name;
	private boolean isSelected = false;

	public Option() {
	}

	public Option(String value) {
		this.value = value;
		this.name = value;
	}

	public Option(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public Option(String value, String name, boolean isSelected) {
		this.value = value;
		this.name = name;
		this.isSelected = isSelected;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
