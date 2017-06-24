/**
 * 2015 User.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.user;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.role.Role;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Persistence model for User
 *
 * @author Steven J.S Min
 *
 */
public class User extends BaseDto {

    private String userId;
    private String password;
    private String userName;
    private String userNameReal;
    private String sex;
    private String photoFile;
    private String passwordUpdateDate;
    private int passwordFailureCnt;
    private String email;
    private String mobile;
    private String mobileAuthFinished;
    private String telephone;
    private String useSocialMessenger;
    private String useSocialMessengerId;
    private String dob;
    private String applyStatus;
    private String addressStreet;
    private String addressSuburb;
    private String addressState;
    private String addressPostcode;
    private String addressBusinessSuburb;
    private String addressBusinessStreet;
    private String addressBusinessState;
    private String addressBusinessSamewithHome;
    private String addressBusinessPostcode;
    private String acn;
    private String abn;
    private String sellerDeliveryAddressStreet;
    private String sellerDeliveryAddressSuburb;
    private String sellerDeliveryAddressState;
    private String sellerDeliveryAddressPostcode;
    private String sellerPickupAddressStreet;
    private String sellerPickupAddressSuburb;
    private String sellerPickupAddressState;
    private String sellerPickupAddressPostcode;
    private String sellerIntroduction;
    private String sellerBusinessName;
    private String sellerHaveMinimumPayment;
    private String sellerHaveMinimumPaymentLabel;
    private Float sellerMinimumPaymentForPickup;
    private Float sellerMinimumPaymentForDeliver;
    private int profilePhotoId;
    private String useSocialMessengerLabel;
    private String sexLabel;
    private String applyStatusLabel;
    private String address;
    private String roleId;
    private String roleName;

    private List<Role> roles;
    private boolean isPasswordChangePeriod;
    private int passwordChangePeriodDays;

    private String sellerIsMandatoryChooseDeliveryPickupDate;
    private String sellerIsMandatoryChooseDeliveryPickupDateLabel;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }

    public String getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public void setPasswordUpdateDate(String passwordUpdateDate) {
        this.passwordUpdateDate = passwordUpdateDate;
    }

    public int getPasswordFailureCnt() {
        return passwordFailureCnt;
    }

    public void setPasswordFailureCnt(int passwordFailureCnt) {
        this.passwordFailureCnt = passwordFailureCnt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getAddressSuburb() {
        return addressSuburb;
    }

    public void setAddressSuburb(String addressSuburb) {
        this.addressSuburb = addressSuburb;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getAddressBusinessSuburb() {
        return addressBusinessSuburb;
    }

    public void setAddressBusinessSuburb(String addressBusinessSuburb) {
        this.addressBusinessSuburb = addressBusinessSuburb;
    }

    public String getAddressBusinessStreet() {
        return addressBusinessStreet;
    }

    public void setAddressBusinessStreet(String addressBusinessStreet) {
        this.addressBusinessStreet = addressBusinessStreet;
    }

    public String getAddressBusinessState() {
        return addressBusinessState;
    }

    public void setAddressBusinessState(String addressBusinessState) {
        this.addressBusinessState = addressBusinessState;
    }

    public String getAddressBusinessSamewithHome() {
        return addressBusinessSamewithHome;
    }

    public void setAddressBusinessSamewithHome(String addressBusinessSamewithHome) {
        this.addressBusinessSamewithHome = addressBusinessSamewithHome;
    }

    public String getAddressBusinessPostcode() {
        return addressBusinessPostcode;
    }

    public void setAddressBusinessPostcode(String addressBusinessPostcode) {
        this.addressBusinessPostcode = addressBusinessPostcode;
    }

    public String getAcn() {
        return acn;
    }

    public void setAcn(String acn) {
        this.acn = acn;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public boolean isPasswordChangePeriod() {
        return isPasswordChangePeriod;
    }

    public void setPasswordChangePeriod(boolean isPasswordChangePeriod) {
        this.isPasswordChangePeriod = isPasswordChangePeriod;
    }

    public int getPasswordChangePeriodDays() {
        if (passwordChangePeriodDays == 0) {
            return 365;
        } else {
            return passwordChangePeriodDays;
        }
    }

    public void setPasswordChangePeriodDays(int passwordChangePeriodDays) {
        this.passwordChangePeriodDays = passwordChangePeriodDays;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String roleId) {
        boolean retVal = false;
        List<Role> roleList = this.getRoles();
        for (Role role : roleList) {
            if (StringUtils.equalsIgnoreCase(role.getRoleId(), roleId)) {
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUseSocialMessenger() {
        return useSocialMessenger;
    }

    public void setUseSocialMessenger(String useSocialMessenger) {
        this.useSocialMessenger = useSocialMessenger;
    }

    public String getUseSocialMessengerId() {
        return useSocialMessengerId;
    }

    public void setUseSocialMessengerId(String useSocialMessengerId) {
        this.useSocialMessengerId = useSocialMessengerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUseSocialMessengerLabel() {
        return useSocialMessengerLabel;
    }

    public void setUseSocialMessengerLabel(String useSocialMessengerLabel) {
        this.useSocialMessengerLabel = useSocialMessengerLabel;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    public String getApplyStatusLabel() {
        return applyStatusLabel;
    }

    public void setApplyStatusLabel(String applyStatusLabel) {
        this.applyStatusLabel = applyStatusLabel;
    }

    public String getSellerDeliveryAddressStreet() {
        return sellerDeliveryAddressStreet;
    }

    public void setSellerDeliveryAddressStreet(String sellerDeliveryAddressStreet) {
        this.sellerDeliveryAddressStreet = sellerDeliveryAddressStreet;
    }

    public String getSellerDeliveryAddressSuburb() {
        return sellerDeliveryAddressSuburb;
    }

    public void setSellerDeliveryAddressSuburb(String sellerDeliveryAddressSuburb) {
        this.sellerDeliveryAddressSuburb = sellerDeliveryAddressSuburb;
    }

    public String getSellerDeliveryAddressState() {
        return sellerDeliveryAddressState;
    }

    public void setSellerDeliveryAddressState(String sellerDeliveryAddressState) {
        this.sellerDeliveryAddressState = sellerDeliveryAddressState;
    }

    public String getSellerDeliveryAddressPostcode() {
        return sellerDeliveryAddressPostcode;
    }

    public void setSellerDeliveryAddressPostcode(String sellerDeliveryAddressPostcode) {
        this.sellerDeliveryAddressPostcode = sellerDeliveryAddressPostcode;
    }

    public String getSellerPickupAddressStreet() {
        return sellerPickupAddressStreet;
    }

    public void setSellerPickupAddressStreet(String sellerPickupAddressStreet) {
        this.sellerPickupAddressStreet = sellerPickupAddressStreet;
    }

    public String getSellerPickupAddressSuburb() {
        return sellerPickupAddressSuburb;
    }

    public void setSellerPickupAddressSuburb(String sellerPickupAddressSuburb) {
        this.sellerPickupAddressSuburb = sellerPickupAddressSuburb;
    }

    public String getSellerPickupAddressState() {
        return sellerPickupAddressState;
    }

    public void setSellerPickupAddressState(String sellerPickupAddressState) {
        this.sellerPickupAddressState = sellerPickupAddressState;
    }

    public String getSellerPickupAddressPostcode() {
        return sellerPickupAddressPostcode;
    }

    public void setSellerPickupAddressPostcode(String sellerPickupAddressPostcode) {
        this.sellerPickupAddressPostcode = sellerPickupAddressPostcode;
    }

    public String getSellerIntroduction() {
        return sellerIntroduction;
    }

    public void setSellerIntroduction(String sellerIntroduction) {
        this.sellerIntroduction = sellerIntroduction;
    }

    public int getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(int profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public String getSellerBusinessName() {
        return sellerBusinessName;
    }

    public void setSellerBusinessName(String sellerBusinessName) {
        this.sellerBusinessName = sellerBusinessName;
    }

    public String getSellerHaveMinimumPayment() {
        return sellerHaveMinimumPayment;
    }

    public void setSellerHaveMinimumPayment(String sellerHaveMinimumPayment) {
        this.sellerHaveMinimumPayment = sellerHaveMinimumPayment;
    }

    public Float getSellerMinimumPaymentForPickup() {
        return sellerMinimumPaymentForPickup;
    }

    public void setSellerMinimumPaymentForPickup(Float sellerMinimumPaymentForPickup) {
        this.sellerMinimumPaymentForPickup = sellerMinimumPaymentForPickup;
    }

    public Float getSellerMinimumPaymentForDeliver() {
        return sellerMinimumPaymentForDeliver;
    }

    public void setSellerMinimumPaymentForDeliver(Float sellerMinimumPaymentForDeliver) {
        this.sellerMinimumPaymentForDeliver = sellerMinimumPaymentForDeliver;
    }

    public String getSellerHaveMinimumPaymentLabel() {
        return sellerHaveMinimumPaymentLabel;
    }

    public void setSellerHaveMinimumPaymentLabel(String sellerHaveMinimumPaymentLabel) {
        this.sellerHaveMinimumPaymentLabel = sellerHaveMinimumPaymentLabel;
    }

    public String getSellerIsMandatoryChooseDeliveryPickupDate() {
        return sellerIsMandatoryChooseDeliveryPickupDate;
    }

    public void setSellerIsMandatoryChooseDeliveryPickupDate(String sellerIsMandatoryChooseDeliveryPickupDate) {
        this.sellerIsMandatoryChooseDeliveryPickupDate = sellerIsMandatoryChooseDeliveryPickupDate;
    }

    public String getSellerIsMandatoryChooseDeliveryPickupDateLabel() {
        return sellerIsMandatoryChooseDeliveryPickupDateLabel;
    }

    public void setSellerIsMandatoryChooseDeliveryPickupDateLabel(String sellerIsMandatoryChooseDeliveryPickupDateLabel) {
        this.sellerIsMandatoryChooseDeliveryPickupDateLabel = sellerIsMandatoryChooseDeliveryPickupDateLabel;
    }

    public String getUserNameReal() {
        return userNameReal;
    }

    public void setUserNameReal(String userNameReal) {
        this.userNameReal = userNameReal;
    }

    public String getMobileAuthFinished() {
        return mobileAuthFinished;
    }

    public void setMobileAuthFinished(String mobileAuthFinished) {
        this.mobileAuthFinished = mobileAuthFinished;
    }
}
