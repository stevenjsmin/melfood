/** 
 * 2016 SystemConfig.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.List;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class SystemConfig extends BaseDto {
	private String stage;
	private String key;
	private String value;
	private String description;
	
	private List<String> stages;

	public SystemConfig() {
	}

	public SystemConfig(String stage, String key) {
		this.stage = stage;
		this.key = key;
	}

	public SystemConfig(String stage, String key, String value) {
		this.stage = stage;
		this.key = key;
		this.value = value;
	}

	public SystemConfig(String stage, String key, String value, String description) {
		this.stage = stage;
		this.key = key;
		this.value = value;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return the stages
	 */
	public List<String> getStages() {
		return stages;
	}

	/**
	 * @param stages the stages to set
	 */
	public void setStages(List<String> stages) {
		this.stages = stages;
	}

	
}
