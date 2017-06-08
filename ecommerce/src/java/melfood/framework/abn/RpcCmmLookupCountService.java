/** 
 * 2015 RpcCmmLookupCountService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.abn;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public interface RpcCmmLookupCountService {

	/**
	 * Increase lookup count corresponding lookup type. Looup type can be followed by following values<br>
	 * <b> Lookup type </b>
	 * <ul>
	 * <li>ABN
	 * <li>ACN
	 * <li>NMI
	 * </ul>
	 * 
	 * @param lookupType
	 * @param lookupData
	 * @throws Exception
	 */
	public void increaseLookupCount(String lookupType, String lookupData) throws Exception;
}
