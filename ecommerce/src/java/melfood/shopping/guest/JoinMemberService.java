/** 
 * 2016 JoinMemberService.java
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
public interface JoinMemberService {

	/**
	 * 회원가입시 기본 사용자 이름을 가저온다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDefaultUserName() throws Exception;

	/**
	 * 회원가입후 회원에게 가입환영 이메일을 발송한다.
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendEmailForWelcomeMember(String userId) throws Exception;

	/**
	 * 비빌번호를 초기화 한후에 메일을 발송을 한다.
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void sendEmailForInitializePassword(String userId) throws Exception;

}
