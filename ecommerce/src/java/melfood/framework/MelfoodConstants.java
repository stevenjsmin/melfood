/** 
 * 2015 UssConstants.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework;

/**
 * @author Steven
 *
 */
public interface MelfoodConstants {
	static final String ACTION_MODE_MODIFY = "MODIFY";
	static final String ACTION_MODE_ADD = "ADD";

	// System user session key
	static final String LOGIN_USER_SESSION_ATTR = "system_user_login_session";

	static final Integer VALIDATE_RESULT_FAILURE = -1;
	static final Integer VALIDATE_RESULT_SUCCESS = 0;

	static final String VALIDATE_RESULT_SUCCESS_MSG = "SUCCESS";

	static final String DOCUMENT_TYPE_PDF = "PDF";
	static final String DOCUMENT_TYPE_MSWORD = "MSWORD";
	static final String DOCUMENT_TYPE_HTML = "HTML";
	static final String DOCUMENT_TYPE_JPG = "JPG";
	static final String DOCUMENT_TYPE_XML = "XML";
	static final String DOCUMENT_TYPE_EXCEL = "EXCEL";

	static final String USER_TYPE_ADMIN = "ADMIN";
	static final String USER_TYPE_SELLER = "SELLER";
	static final String USER_TYPE_CUSTOMER = "CUSTOMER";

	static final String LOGIN_AUTH_STATUS_FAILURE = "FAILURE";
	static final String LOGIN_AUTH_STATUS_SUCCESS = "SUCCESS";
	static final String LOGIN_AUTH_STATUS_CHANGE_PWD = "CHANGE_PWD";

}
