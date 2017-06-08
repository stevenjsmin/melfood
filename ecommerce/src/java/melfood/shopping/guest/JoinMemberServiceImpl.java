/** 
 * 2016 JoinMemberServiceImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author steven.min
 *
 */
@Service
public class JoinMemberServiceImpl implements JoinMemberService {

	@Autowired
	private JoinMemberDAO joinMemberDAO;

	@Override
	public String getDefaultUserName() throws Exception {
		return joinMemberDAO.getDefaultUserName();
	}

	@Override
	public void sendEmailForWelcomeMember(String userId) throws Exception {
	}

	@Override
	public void sendEmailForInitializePassword(String userId) throws Exception {
	}

}
