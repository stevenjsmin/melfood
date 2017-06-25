/**
 * 2015 UserDAO.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import melfood.framework.role.Role;

import java.util.List;

/**
 * Framwork User data access interface.
 *
 * @author Steven J.S Min
 *
 */
public interface UserDAO {

    public List<User> getUsers(User user) throws Exception;

    public Integer getTotalCntForUsers(User user);

    public Integer deleteUser(User user) throws Exception;

    public String insertUser(User user) throws Exception;

    public String updateUser(User user) throws Exception;

    public String updateUserForNotNull(User user) throws Exception;

    public List<Role> getUserRoles(String userId) throws Exception;

    public List<Role> getUserRolesNotMaped(String userId) throws Exception;

    public Integer applyMapping(String userId, List<Role> roles) throws Exception;

    public Integer removeMapping(String userId, List<Role> roles) throws Exception;

    /**
     * 사용자 비밀번호를 변경한다. 변경 후 변경된 사용자 ID를 반환하며, 변경된 비빌번호가 없는 경우 NULL을 반환한다.
     *
     * @param userId
     * @param password
     * @return 변경된 사용자 ID, 변경된 ID가 없는 경우 NULL을 반환
     * @throws Exception
     */
    public String passwordChange(String userId, String password) throws Exception;

    public String initialUserAccount(User user, boolean includePassword) throws Exception;

    public String getAllRoles(String userId) throws Exception;

    /**
     * 사용자 프로필 사진을 업데이트 한다.
     *
     * @param user
     * @return 업데이트된 사용자 ID
     * @throws Exception
     */
    public String updateUserProfilePhoto(User user) throws Exception;

    public List<User> getUsersByRoleIds(String[] userRoles) throws Exception;


    /**
     * 모바일 인증정보를 갱신한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int updateMobileValidCheckCode(User user) throws Exception;



}