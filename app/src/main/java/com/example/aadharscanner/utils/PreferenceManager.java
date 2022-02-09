package com.example.aadharscanner.utils;

import androidx.annotation.NonNull;

public class PreferenceManager {
   /*------- preference key created by lincon*-------------*/
    private static final String PREF_KEY_LOGIN_BLOCK_CODE = "loginBlockCode";
    private static final String PREF_KEY_VILLAGE_CODE = "villageCode";
    private static final String PREF_KEY_GP_CODE = "gpCode";
    private static final String PREF_KEY_SHG_CODE = "shgCode";
    private static final String PREF_KEY_LINK_STATUS= "linkStatus";
    private static final String PREF_KEY_AADHAR_LINK_STATUS= "aadharlinkStatus";
    private static final String PREF_KEY_BANK_ACCOUNT_LINK_STATUS= "bankaccountlinkStatus";
    private static final String PREF_KEY_SHG_MEMBER_CODE= "shgMemberCode";
    private static final String PREF_KEY_SHG_MEMBER_STATUS= "shgMembeStatus";
    private static final String PREF_KEY_OTP="otp";
    private static final String PREF_KEY_MOBILE_NUMBER="mobileNumber";
    private static final String PREF_KEY_STATE_SHORT_NAME="stateShortName";
    private static final String LOGIN_FAILD_COUNT="loginFaildCount";
    private static final String LAST_ATTEMT_TIME="lastattempttime";
    private static final String PREF_LANGUAGE_ID="langId";

    @NonNull
    public static String getPrefLanguageId() {
        return PREF_LANGUAGE_ID;
    }

    @NonNull
    public static String getLastAttemtTime() {
        return LAST_ATTEMT_TIME;
    }

    @NonNull
    public static String getLoginFaildCount() {
        return LOGIN_FAILD_COUNT;
    }

    @NonNull
    public static String getPrefKeyStateShortName() {
        return PREF_KEY_STATE_SHORT_NAME;
    }

    @NonNull
    public static String getPrefKeyMobileNumber() {
        return PREF_KEY_MOBILE_NUMBER;
    }

    @NonNull
    public static String getPrefKeyOtp() {
        return PREF_KEY_OTP;
    }


    @NonNull
    public static String getPrefKeyShgMemberStatus() {
        return PREF_KEY_SHG_MEMBER_STATUS;
    }
    @NonNull
    public static String getPrefKeyAadharLinkStatus() {
        return PREF_KEY_AADHAR_LINK_STATUS;
    }

    @NonNull
    public static String getPrefKeyBankAccountLinkStatus() {
        return PREF_KEY_BANK_ACCOUNT_LINK_STATUS;
    }

    @NonNull
    public static String getPrefKeyGpCode() {
        return PREF_KEY_GP_CODE;
    }

    @NonNull
    public static String getPrefKeyShgMemberCode() {
        return PREF_KEY_SHG_MEMBER_CODE;
    }

    @NonNull
    public static String getPrefKeyLinkStatus() {
        return PREF_KEY_LINK_STATUS;
    }

    @NonNull
    public static String getPrefKeyShgCode() {
        return PREF_KEY_SHG_CODE;
    }

    @NonNull
    public static String getPrefKeyVillageCode() {
        return PREF_KEY_VILLAGE_CODE;
    }

    @NonNull
    public static String getPrefKeyLoginBlockCode() {
        return PREF_KEY_LOGIN_BLOCK_CODE;
    }

    /*prefrence added by Abdul*/


    private static final String PREF_LOGIN_SESSION_KEY="loginsessionkey";
    private static final String PREF_LOGIN_ID="loginid";
    private static final String PREF_LOGIN_PASSWORD="loginpassword";
    private static final String PREF_BLOCK_CODE="blockcode";
    private static final String PREF_SHG_CODE="prefshgcode";
    private static final String PREF_GP_NAME="gpname";
    private static final String PREF_VILLAGE_NAME="villagename";
    private static final String PREF_SHG_NAME="prefshgname";
    private static final String CALLING_API="callingapi";
    private static final String AADHAR_API_RESPONSE="aadharapiresponse";
    private static final String BANK_ACCOUNT_API_RESPONSE="bankaccapiresponse";
    private static final String ADD_MEMBER_API_RESPONSE="addmemberapiresponse";

    private static final String DUPLICATE_API_VOLLEY_ERROR="duplicateapivolleyerror";

    private static final String AADHAR_API_VOLLEY_ERROR="aadharapivolleyerror";
    private static final String BANK_ACC_API_VOLLEY_ERROR="bankaccountapivolleyerror";
    private static final String ADD_MEMBER_API_VOLLEY_ERROR="addmemberapivolleyerror";
    private static final String KYC_DOC_API_VOLLEY_ERROR="kycdocapivolleyerror";
    private static final String SHG_INACTIVATION_API_VOLLEY_ERROR="shginactivationapivolleyerror";
    private static final String MEMBER_INACTIVATION_API_VOLLEY_ERROR="memberinactivationapivolleyerror";

    private static final String LOGOUT_DATE_TIME="LOGOUTDATETIME";
    private static final String PREF_BLOCK_NAME="blockname";

    private static final String IMEI="IMEI";
    private static final String DEVICE_INFO="DEVICEINFO";
    private static final String AADHAR_CURRENT_STATUS="AADHAR_CURRENT_STATUS";
    private static final String BANK_CURRENT_STATUS="BANK_CURRENT_STATUS";

    @NonNull
    public static String getBankCurrentStatus() {
        return BANK_CURRENT_STATUS;
    }

    @NonNull
    public static String getAadharCurrentStatus() {
        return AADHAR_CURRENT_STATUS;
    }

    @NonNull
    public static String getShgInactivationApiVolleyError() {
        return SHG_INACTIVATION_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getMemberInactivationApiVolleyError() {
        return MEMBER_INACTIVATION_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getKycDocApiVolleyError() {
        return KYC_DOC_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getIMEI() {
        return IMEI;
    }

    @NonNull
    public static String getDeviceInfo() {
        return DEVICE_INFO;
    }

    @NonNull
    public static String getPrefBlockName() {
        return PREF_BLOCK_NAME;
    }

    @NonNull
    public static String getDuplicateApiVolleyError() {
        return DUPLICATE_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getLogoutDateTime() {
        return LOGOUT_DATE_TIME;
    }

    @NonNull
    public static String getAadharApiVolleyError() {
        return AADHAR_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getBankAccApiVolleyError() {
        return BANK_ACC_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getAddMemberApiVolleyError() {
        return ADD_MEMBER_API_VOLLEY_ERROR;
    }

    @NonNull
    public static String getAadharApiResponse() {
        return AADHAR_API_RESPONSE;
    }

    @NonNull
    public static String getBankAccountApiResponse() {
        return BANK_ACCOUNT_API_RESPONSE;
    }

    @NonNull
    public static String getAddMemberApiResponse() {
        return ADD_MEMBER_API_RESPONSE;
    }

    @NonNull
    public static String getCallingApi() {
        return CALLING_API;
    }

    @NonNull
    public static String getPrefGpName() {
        return PREF_GP_NAME;
    }

    @NonNull
    public static String getPrefVillageName() {
        return PREF_VILLAGE_NAME;
    }

    @NonNull
    public static String getPrefShgName() {
        return PREF_SHG_NAME;
    }

    @NonNull
    public static String getPrefShgCode() {
        return PREF_SHG_CODE;
    }

    @NonNull
    public static String getPrefLoginId() {
        return PREF_LOGIN_ID;
    }

    @NonNull
    public static String getPrefLoginPassword() {
        return PREF_LOGIN_PASSWORD;
    }

    @NonNull
    public static String getPrefLoginSessionKey() {
        return PREF_LOGIN_SESSION_KEY;
    }

    @NonNull
    public static String getPrefBlockCode() {
        return PREF_BLOCK_CODE;
    }

    /*prefrence addedd by manish*/
    private static final String PREF_KEY_MPIN="prfmpin";
    private static final String PREF_LANGUAGE_CODE="languagecode";

    @NonNull
    public static String getPrefLanguageCode() {
        return PREF_LANGUAGE_CODE;
    }

    @NonNull
    public static String getPrefKeyMpin() {
        return PREF_KEY_MPIN;
    }





}
