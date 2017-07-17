/**
 * 2015 UserServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import com.google.maps.model.GeocodingResult;
import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.gmap.MelfoodGoogleMapService;
import melfood.framework.role.Role;
import melfood.framework.system.AwsSNSUtils;
import melfood.framework.uitl.html.Option;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Steven J.S Min
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AttachmentFileService attachmentFileService;

    @Autowired
    private MelfoodGoogleMapService melfoodGoogleMapService;

    @Override
    public String changeUserStatus(String userId, String useYn) throws Exception {
        User user = new User(userId);
        user.setUseYn(StringUtils.upperCase(useYn));
        return userDAO.updateUserForNotNull(user);
    }

    @Override
    public Integer deleteUser(String userId) throws Exception {
        User user = new User(userId);
        return userDAO.deleteUser(user);
    }

    @Override
    public void enableAndDisable(String userId) throws Exception {
        User user = new User(userId);
        if (StringUtils.equalsIgnoreCase(this.getUserInfo(userId).getUseYn(), "Y")) {
            user.setUseYn("N");
        } else {
            user.setUseYn("Y");
        }
        this.modifyUserForNotNull(user);
    }

    @Override
    public boolean existUser(String userId) throws Exception {
        List<User> users = userDAO.getUsers(new User(userId));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getProfilePhoto(String userId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getUserInfo(String userId) throws Exception {
        List<User> users = userDAO.getUsers(new User(userId));
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUsers(User user) throws Exception {
        return userDAO.getUsers(user);
    }

    @Override
    public Integer getTotalCntForUsers(User user) {
        return userDAO.getTotalCntForUsers(user);
    }

    @Override
    public String addUser(User user) throws Exception {
        return userDAO.insertUser(user);
    }

    @Override
    public String modifyUser(User user) throws Exception {
        return userDAO.updateUser(user);
    }

    @Override
    public String modifyUserForNotNull(User user) throws Exception {
        return userDAO.updateUserForNotNull(user);
    }

    @Override
    public Integer increaseLoginFailureCnt(String userId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateUser(String userId, String password) throws Exception {
        List<User> users = userDAO.getUsers(new User(userId, password));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Role> getUserRoles(String userId) throws Exception {
        return userDAO.getUserRoles(userId);
    }

    @Override
    public List<Role> getUserRolesNotMaped(String userId) throws Exception {
        return userDAO.getUserRolesNotMaped(userId);
    }

    @Override
    public Integer applyMapping(String userId, List<Role> roles) throws Exception {
        return userDAO.applyMapping(userId, roles);
    }

    @Override
    public Integer removeMapping(String userId, List<Role> roles) throws Exception {
        return userDAO.removeMapping(userId, roles);
    }

    @Override
    public String passwordChange(String userId, String password) throws Exception {
        User user = new User(userId, password);
        return userDAO.updateUserForNotNull(user);
    }

    @Override
    public String initialUserAccount(String userId) throws Exception {
        return this.initialUserAccount(userId, false);
    }

    @Override
    public String initialUserAccount(String userId, boolean includePassword) throws Exception {
        User user = null;
        if (includePassword) {
            user = new User(userId, this.getRamdom4DigitPassword());
        } else {
            user = new User(userId);
        }

        return userDAO.initialUserAccount(user, includePassword);
    }

    @Override
    public boolean loginFailureIncrease(User user) throws Exception {

        boolean isLocked = false;

        int maxAllowedCnt = Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt");

        User newUser = new User(user.getUserId());

        int loginFailureCnt = user.getPasswordFailureCnt();
        if (loginFailureCnt < maxAllowedCnt) {
            loginFailureCnt++;
            newUser.setPasswordFailureCnt(loginFailureCnt);

        } else {
            newUser.setPasswordFailureCnt(maxAllowedCnt);
            newUser.setUseYn("N");
            isLocked = true;
        }

        userDAO.updateUserForNotNull(newUser);

        return isLocked;
    }

    @Override
    public String getRamdom4DigitPassword() throws Exception {
        Random r = new Random();
        Set<Integer> s = new HashSet<Integer>();
        while (s.size() < 4) {
            s.add(r.nextInt(9));
        }

        StringBuffer password = new StringBuffer("");
        for (Integer aInt : s) {
            password.append(Integer.toString(aInt.intValue()));
        }

        return password.toString();
    }

    @Override
    public void noticeUserAccount(String userId, boolean includePassword) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public String getAllRoles(String userId) throws Exception {
        return userDAO.getAllRoles(userId);
    }

    @Override
    public int transferSellerProfilePhotoToAttachementFileDb(String userId) throws Exception {
        return this.transferSellerProfilePhotoToAttachementFileDb(userId, null);
    }

    @Override
    public int transferSellerProfilePhotoToAttachementFileDb(String userId, String creator) throws Exception {
        String uploadTempSubdir = Ctx.getVar("USERPROFILEIMAGE.TEMP.UPLOAD.DIR");
        String uploadTempFileDir = Ctx.APP_DATA_DIR + uploadTempSubdir;
        File tempUploadDir = new File(uploadTempFileDir);

        File[] files = tempUploadDir.listFiles();
        File userProfilePhoto = null;

        if (files.length != 1) {
            throw new Exception("프로파일 이미지 등록은 한개의 파일만 허용합니다.");
        } else {
            userProfilePhoto = files[0];
        }

        Integer insertedFileId = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String subDirectory = Ctx.getVar("USERPROFILEIMAGE.ATTACHMENT.FILE") + df.format(cal.getTime()) + "/";

        BufferedImage bimg = null;
        try {

            if (userProfilePhoto != null && isAllowedType(userProfilePhoto)) {
                // File userProfilePhoto = files[0];
                bimg = ImageIO.read(userProfilePhoto);
                int width = bimg.getWidth();
                int height = bimg.getHeight();

                if (width > 500 || height > 500) {
                    throw new Exception("이미지의 크기가 세로 및 가로크기가 500 픽셀을 넘을 수 없습니다.");
                }

                insertedFileId = attachmentFileService.transToAttachmentFileFrom(userProfilePhoto, subDirectory, true);

                User user = new User(userId);
                user.setProfilePhotoId(insertedFileId);
                user.setCreator(creator);
                userDAO.updateUserProfilePhoto(user);
            } else {
                throw new Exception("업로드된 프로필 사진에 문제가 있습니다. 파일 형식이나 파일에 문제가 있는지 확인해주세요.");
            }
        } catch (Exception e) {
            attachmentFileService.deleteAttachmentFile(new AttachmentFile(insertedFileId));
            attachmentFileService.deleteAllfilesForTempUploadDir(uploadTempSubdir); // 업로드된 임시 파일들을 삭제해준다.
            throw e;
        }

        return insertedFileId;
    }

    /**
     * 허용된 이미지파일 형식인지 체크한다.
     *
     * @param f
     * @return
     */
    private boolean isAllowedType(File f) {
        boolean retValue = false;
        String allowedImageTypes[] = Ctx.getVarAsArray("ALLOWED.PRODUCT.IMG.TYPES");
        String fileType = StringUtils.substringAfterLast(f.getName(), ".");

        for (String type : allowedImageTypes) {
            if (StringUtils.equalsIgnoreCase(fileType, type)) {
                retValue = true;
                break;
            }
        }
        return retValue;
    }

    @Override
    public String updateUserProfilePhoto(User user) throws Exception {
        return userDAO.updateUserProfilePhoto(user);
    }

    /**
     * 주어진 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRule
     * @return
     * @throws Exception
     */
    @Override
    public List<Option> getUsersByRoleId(String userRule) throws Exception {
        String[] userRoles = {userRule};

        return this.getUsersByRoleIds(userRoles);
    }

    /**
     * 주어진 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRule
     * @param defaultSelectedValue
     * @return
     * @throws Exception
     */
    @Override
    public List<Option> getUsersByRoleId(String userRule, String defaultSelectedValue) throws Exception {
        String[] userRoles = {userRule};

        return this.getUsersByRoleIds(userRoles, defaultSelectedValue);
    }

    /**
     * 주어진 하나이상의 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRoles
     * @return
     * @throws Exception
     */
    @Override
    public List<Option> getUsersByRoleIds(String[] userRoles) throws Exception {
        return this.getUsersByRoleIds(userRoles, null);
    }

    /**
     * 주어진 하나이상의 Role에 해당하는 사용자정보를 Html 콤보박스에서 이용할수 있도록 Option 객체목록으로 받아온다
     *
     * @param userRoles
     * @param defaultSelectedValue
     * @return
     * @throws Exception
     */
    @Override
    public List<Option> getUsersByRoleIds(String[] userRoles, String defaultSelectedValue) throws Exception {

        List<User> users = userDAO.getUsersByRoleIds(userRoles);

        List<Option> options = new ArrayList<Option>();

        for (User user : users) {
            if (StringUtils.equalsIgnoreCase(user.getUserId(), defaultSelectedValue)) {
                options.add(new Option(user.getUserId(), user.getUserName(), true));
            } else {
                options.add(new Option(user.getUserId(), user.getUserName(), false));
            }
        }
        return options;
    }

    /**
     * 모바일 인증정보를 갱신한다.<br>
     * 인증코드를 SMS로 발송하고, 인증코드를 사용자테이블에 저장한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updateMobileValidCheckCode(User user) throws Exception {

        // 인증코드를 위한 4자리 Random코드 생성
        // SMS로 인증코드를 보낸다.
        String randomNumber = this.randomNumber(4);
        String smsResult = AwsSNSUtils.sendMessage("Melfood 회원가입인증코드 : " + randomNumber, user.getUserId());
        logger.info("SMS Send result :" + smsResult);

        // 인증코드를 사용자테이블에 업데이트 한다.
        User newUser = new User(user.getUserId());
        newUser.setMobileValidCheckCode(randomNumber);

        return userDAO.updateMobileValidCheckCode(newUser);
    }

    private String randomNumber(int len) {

        String AB = "0123456789";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /**
     * 모바일 번호가 인증된경우 인증된 정보를 갱신한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int validateMobileCheck(User user) throws Exception {
        return userDAO.updateMobileValidCheckCode(user);
    }

    /**
     * 사용자의 홈주소 구글 좌표주소를 갱신한다.<br/>
     * user객체에 사용자ID, addressHomeGmapLatitude, addressHomeGmapLongitude, addressHomeGmapFormattedAddress를 설정해줘야 한다.
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updateHomeAddressGmapCoordinate(User user) throws Exception {

        String addressState = user.getAddressState();
        String addressPostcode = user.getAddressPostcode();
        String addressSuburb = user.getAddressSuburb();
        String addressStreet = user.getAddressStreet();

        GeocodingResult geoResult = null;
        String gmapLatitude = null;
        String gmapLongitude = null;

        int updateCnt = 0;

        // 사용자의 주소가 존재한다면 사용자 주소의 좌표를 읽어서 저장한다.
        if (StringUtils.isNotBlank(user.getUserId())
                && StringUtils.isNotBlank(addressSuburb)
                && StringUtils.isNotBlank(addressPostcode)
                && StringUtils.isNotBlank(addressState)) {

            try {
                geoResult = melfoodGoogleMapService.lookupGMap(addressStreet + " " + addressSuburb + " " + addressState + " " + addressPostcode);
                if (geoResult != null) {
                    gmapLatitude = Double.toString(geoResult.geometry.location.lat);
                    gmapLongitude = Double.toString(geoResult.geometry.location.lng);

                    user.setAddressHomeGmapLatitude(gmapLatitude);
                    user.setAddressHomeGmapLongitude(gmapLongitude);
                    user.setAddressHomeGmapFormattedAddress(geoResult.formattedAddress);

                    updateCnt = userDAO.updateHomeAddressGmapCoordinate(user);

                } else {
                    // 상세주소를 제외한 주소로 다시한번 시도해본다.
                    geoResult = melfoodGoogleMapService.lookupGMap(addressSuburb + " " + addressState + " " + addressPostcode);

                    gmapLatitude = Double.toString(geoResult.geometry.location.lat);
                    gmapLongitude = Double.toString(geoResult.geometry.location.lng);

                    user.setAddressHomeGmapLatitude(gmapLatitude);
                    user.setAddressHomeGmapLongitude(gmapLongitude);
                    user.setAddressHomeGmapFormattedAddress(geoResult.formattedAddress);

                    updateCnt = userDAO.updateHomeAddressGmapCoordinate(user);

                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.info("[" + addressStreet + " " + addressSuburb + " " + addressState + " " + addressPostcode + "] 에대한 죄표를 구하는데 실패하였습니다. :" + e.getMessage());
            }
        } else {
            logger.info("[" + addressStreet + " " + addressSuburb + " " + addressState + " " + addressPostcode + "] 에대한 죄표를 계산 할 수 없습니다.");
        }

        return updateCnt;
    }
}
