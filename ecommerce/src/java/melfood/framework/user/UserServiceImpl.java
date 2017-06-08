/** 
 * 2015 UserServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.role.Role;

/**
 * @author Steven J.S Min
 * 
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AttachmentFileService attachmentFileService;

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

}
