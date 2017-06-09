/** 
 * 2015 CodeDao.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.code;

import java.util.List;

/**
 * Framwork code data access interface.
 * 
 * @author Steven J.S Min
 * 
 */
public interface CodeDAO {

	/**
	 * Get code list
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Code> getCodes(Code code) throws Exception;

	/**
	 * Get total count for codes
	 * 
	 * @param code
	 * @return
	 */
	public Integer getTotalCntForCodes(Code code);

	/**
	 * Get system Category list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Code> getCategories() throws Exception;

	/**
	 * Get category code list that corresponding Category code.
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<Code> getTypes(String category) throws Exception;

	/**
	 * Delete code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer deleteCode(Code code) throws Exception;

	/**
	 * Delete codes<br>
	 * To delete more than one, you have to set code information that will be deleted into List that wrap Code<br>
	 * 
	 * @param keyList
	 * @return
	 * @throws Exception
	 */
	public Integer deleteCodes(List<Code> codes) throws Exception;

	/**
	 * Insert code<br>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer insertCode(Code code) throws Exception;

	/**
	 * Modify code<br>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer modifyCode(Code code) throws Exception;

	/**
	 * Modify code<br>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer modifyCodeForNotNull(Code code) throws Exception;

}
