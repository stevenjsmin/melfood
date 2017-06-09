/** 
 * 2015 CodeService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.code;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * Framework code data access interface.
 * 
 * @author Steven J.S Min
 * 
 */
public interface CodeService {

	/**
	 * 코드정보를 가져온다
	 * 
	 * @param category 대분류
	 * @param type 중룬뷰
	 * @param value 코드값
	 * @return
	 * @throws Exception
	 */
	public Code getCode(String category, String type, String value) throws Exception;
	public Integer getTotalCntForCodes(Code code);
	
	/**
	 * Get code list
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Code> getCodes(Code code) throws Exception;

	/**
	 * 대분류 목록을 가져온다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Code> getCategories() throws Exception;

	/**
	 * 중분류 목록을 가져온다.
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<Code> getTypes(String category) throws Exception;

	/**
	 * 코드정보 삭제
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer deleteCode(Code code) throws Exception;

	/**
	 * 하나이상의 코드를 삭게한다.
	 * 
	 * @param codes
	 * @return
	 * @throws Exception
	 */
	public Integer deleteCodes(List<Code> codes) throws Exception;

	/**
	 * 코드정보 추가
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer insertCode(Code code) throws Exception;

	/**
	 * 코드정보 수정
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer modifyCode(Code code) throws Exception;

	/**
	 * 코드정보 수정<br>
	 * 수정하려는 코드정보의 값이 없는 경우 수정하지 않는다. 오직 수정하려는 값이 Code 객체에 설정되 경우만 해당 값을 수정한다.
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer modifyCodeForNotNull(Code code) throws Exception;

	/**
	 * 코드정보가 존재하는지 확인한다.
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean existCode(Code code) throws Exception;

	/**
	 * 대분류 정보를 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Option> getCategoryCmbxOptions() throws Exception;

	/**
	 * 대분류 정보를 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @param defaultSelectedValue
	 * @return
	 * @throws Exception
	 */
	public List<Option> getCategoryCmbxOptions(String defaultSelectedValue) throws Exception;

	/**
	 * 중분류 정보를 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<Option> getTypeCmbxOptions(String category) throws Exception;

	/**
	 * 중분류 정보를 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @param category
	 * @param defaultSelectedValue
	 * @return
	 * @throws Exception
	 */
	public List<Option> getTypeCmbxOptions(String category, String defaultSelectedValue) throws Exception;

	/**
	 * 코드목록을 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @param category
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<Option> getValueCmbxOptions(String category, String type) throws Exception;

	/**
	 * 코드목록을 Html 콤보박스를 위한 Option목록을 가져온다.
	 * 
	 * @param category
	 * @param type
	 * @param defaultSelectedValue
	 * @return
	 * @throws Exception
	 */
	public List<Option> getValueCmbxOptions(String category, String type, String defaultSelectedValue) throws Exception;

	/**
	 * List에의해서 Wrapping된 Option을 HTML 콤보박스 코드로 구성하여 반환한다.
	 * 
	 * @param property
	 * @param options
	 * @return
	 */
	public String generateCmbx(List<Option> options, Properties property);

	/**
	 * List에의해서 Wrapping된 Option을 HTML 콤보박스 코드로 구성하여 반환한다.
	 * 
	 * @param options
	 * @param property
	 * @param placeholder
	 * @return
	 */
	public String generateCmbx(List<Option> options, Properties property, boolean placeholder);

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.lang.String(기본값))
	 * 
	 * @param category
	 * @param type
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public String getLabel(String category, String type, String value) throws Exception;

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.lang.Integer)
	 * 
	 * @param category
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Integer getLabelByInt(String category, String type, String value) throws Exception;

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.lang.Boolean)
	 * 
	 * @param category
	 * @param type
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Boolean getLabelByBoolean(String category, String type, String value) throws Exception;

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.util.List<java.lang.String>)
	 * 
	 * @param category
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<String> getArrayLabelByString(String category, String type) throws Exception;

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.util.List<java.lang.Integer>)
	 * 
	 * @param category
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getArrayLabelByInt(String category, String type) throws Exception;

	/**
	 * 코드명(Label)을 가져온다.(리턴형:java.util.List<java.lang.Boolean>)
	 * 
	 * @param category
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<Boolean> getArrayLabelByBoolean(String category, String type) throws Exception;
}
