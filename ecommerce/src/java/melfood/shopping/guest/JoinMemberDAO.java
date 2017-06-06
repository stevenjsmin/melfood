/** 
 * 2016 JoinMemberDAO.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.guest;

/**
 * 회원가입에관한 API를 정의한다.
 * 
 * @author steven.min
 *
 */
public interface JoinMemberDAO {

	/**
	 * 회원가입시 기본 사용자 이름을 가저온다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDefaultUserName() throws Exception;

}
