/**
 * 2015 UserService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import melfood.framework.role.Role;
import melfood.framework.uitl.html.Option;

import java.util.List;

/**
 * 시스템 사용자를 위한 Operation을 정의한다.
 *
 * @author Steven J.S Min
 */
public interface UserService {

    public String changeUserStatus(String userId, String useYn) throws Exception;

    /**
     * 사용자 영구히 정보를 삭제한다.<br>
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer deleteUser(String userId) throws Exception;

    /**
     * 사용자 계정을 잠금/해제 한다.(토글)
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public void enableAndDisable(String userId) throws Exception;

    public boolean existUser(String userId) throws Exception;

    public String getProfilePhoto(String userId) throws Exception;

    public User getUserInfo(String userId) throws Exception;

    public List<User> getUsers(User user) throws Exception;

    public Integer getTotalCntForUsers(User user);

    /**
     * 사용자 추가
     *
     * @param user
     * @return
     * @throws Exception
     */
    public String addUser(User user) throws Exception;

    /**
     * 사용자 수정
     *
     * @param user
     * @return
     * @throws Exception
     */
    public String modifyUser(User user) throws Exception;

    /**
     * 사용자 수정<br>
     * 사용자 수정을 위해서 전달되는 User object에서 Null이 아닌 경우에만 수정하려는 경우 이 API를 사용한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    public String modifyUserForNotNull(User user) throws Exception;

    public Integer increaseLoginFailureCnt(String userId) throws Exception;

    public boolean validateUser(String userId, String password) throws Exception;

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

    public String getRamdom4DigitPassword() throws Exception;

    /**
     * 사용자 계정을 초기화 한다.
     *
     * @param userId
     * @return 최기화된 사용자ID
     * @throws Exception
     */
    public String initialUserAccount(String userId) throws Exception;

    /**
     * 사용자 계정을 초기화 한다.
     *
     * @param userId
     * @param includePassword
     * @return 최기화된 사용자ID
     * @throws Exception
     */
    public String initialUserAccount(String userId, boolean includePassword) throws Exception;

    /**
     * 로그인 시도가 실패했을 경우 실패된 카운트를 증가시킨다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    public boolean loginFailureIncrease(User user) throws Exception;

    /**
     * 사용자에게 사용자 계정 정보를 알려준다.
     *
     * @param userId
     * @param includePassword
     * @throws Exception
     */
    public void noticeUserAccount(String userId, boolean includePassword) throws Exception;

    /**
     * 지정된 사용자의 모든 Role정보를 가져온다.
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAllRoles(String userId) throws Exception;

    /**
     * 업로드된 상품 판매자의 프로필사진을 사용자 마스터 테이블에 기록하고, 저장 디렉토리에 옮긴후 임시파일을 삭제한다.
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public int transferSellerProfilePhotoToAttachementFileDb(String userId) throws Exception;

    /**
     * 업로드된 상품 판매자의 프로필사진을 사용자 마스터 테이블에 기록하고, 저장 디렉토리에 옮긴후 임시파일을 삭제한다.
     *
     * @param userId
     * @param creator
     * @return
     * @throws Exception
     */
    public int transferSellerProfilePhotoToAttachementFileDb(String userId, String creator) throws Exception;

    /**
     * 사용자 프로필 사진을 업데이트 한다.
     *
     * @param user
     * @return 업데이트된 사용자 ID
     * @throws Exception
     */
    public String updateUserProfilePhoto(User user) throws Exception;


    /**
     * 주어진 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRule
     * @return
     * @throws Exception
     */
    public List<Option> getUsersByRoleId(String userRule) throws Exception;

    /**
     *주어진 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRule
     * @param defaultSelectedValue
     * @return
     * @throws Exception
     */
    public List<Option> getUsersByRoleId(String userRule, String defaultSelectedValue) throws Exception;

    /**
     * 주어진 하나이상의 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRoles
     * @return
     * @throws Exception
     */
    public List<Option> getUsersByRoleIds(String userRoles[]) throws Exception;

    /**
     * 주어진 하나이상의 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRoles
     * @param defaultSelectedValue
     * @return
     * @throws Exception
     */
    public List<Option> getUsersByRoleIds(String userRoles[], String defaultSelectedValue) throws Exception;

    /**
     * 모바일 인증정보를 갱신한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int updateMobileValidCheckCode(User user) throws Exception;

}
