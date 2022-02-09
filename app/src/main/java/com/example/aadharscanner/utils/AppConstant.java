package com.example.aadharscanner.utils;

import androidx.annotation.NonNull;

import com.example.aadharscanner.BuildConfig;

public class AppConstant {
    /*Constants added by Abdul*/

    /*Don't change any constant bcoz all are used in programing conditions*/

    public static final String language_english[] = {"English", "Hindi", "Bengali", "Urdu", "Gujarati", "Kannada", "Malayalam", "Odia", "Punjabi", "Assamese", "Mizo"};
    public static final String local_language[] = {"English", "हिन्दी", "বাঙালি", "اردو", "ગુજરાતી", "ಕನ್ನಡ", "മലയാളം", "ଓଡ଼ିଆ", "ਪੰਜਾਬੀ", " অসমীয়া", "Mizo"};
    public static final String language_id[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static final String language_code[] = {"en", "hi", "bn", "ur", "gu", "kn", "ml", "or", "pa", "as"};

    /**
     * for local server
     * <p>
     * upaganands,  village code->
     */
  /*
   public static final String SERVER_TYPE = "local";
  public static final String HTTP_TYPE = "http";
    public static final String IP_ADDRESS = "10.197.183.105:8080";
    public static final String NRLM_STATUS = "nrlmwebservice";*/

/*
    public static final String SERVER_TYPE = "local";
    public static final String HTTP_TYPE = "http";
    public static final String IP_ADDRESS = "10.197.183.251:8080";
    public static final String NRLM_STATUS = "extended";*/




    /* for live demo*/

    /*upagiisrar   nrlm123*/
    public static final String SERVER_TYPE = "demo";
    public static final String HTTP_TYPE = "https";
    public static final String IP_ADDRESS = "nrlm.gov.in";
    public static final String NRLM_STATUS = "nrlmwebservicedemo";

    /*for live
     * apanliveid nrlm123*/

   /* public static final String SERVER_TYPE = "live";
    public static final String HTTP_TYPE = "https";
    public static final String IP_ADDRESS = "nrlm.gov.in";
    public static final String NRLM_STATUS = "nrlmwebservice";*/

    /*Api Hits*/
    public static final String LOCATION_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/logins/login";
    public static final String BANK_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/bankbranch/data";
    public static final String MEMBER_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/members/data";
    public static final String SYNC_BANK_DETAIL = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/aadharaccount/datasync";
    public static final String SYNC_AADHAR_DETAIL = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/aadhar/datasync";
    public static final String SYNC_ADD_MEMBER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/shgmember/add";
    public static final String SYNC_MEMBER_STATUS = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/modify/shgmember";
    public static final String DUPLICATE_DATA_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/fiextendedcheck/existdata";
    public static final String FORGET_PASSWORD = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/fiforgotpassword/resetPassword";
    public static final String SENT_OTP = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/fiforgotpassword/message";
    public static final String VILLAGE_CHECK_REQUEST = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/aadharchk/assignuser";
    public static final String VILLAGE_DELETE_REQUEST = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/aadharupdate/assign";
    public static final String SYNC_KYC_DOC = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/kycshgmember/data";
    public static final String SYNC_SHG_INACTIVATION_DETAILS_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/modifyshg/data";
    public static final String SYNC_MEMBER_UPDATE_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/update/Member";

    /*NOTE: don't change below constants these are used in programming conditions. */

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    public static final int CAPTURE_IMAGE_ACTIVITY_SELFI_REQUEST_CODE = 1889;
    public static final int CAPTURE_FRONT_IMAGE_REQUEST_CODE = 1;
    public static final int CAPTURE_BACK_IMAGE_REQUEST_CODE = 0;
    public static final long BACKGROUND_TIME = 1000 * 60 * 10;
    public static final int MAX_LOGIN_ATTEMPTS = 6;
    public static final int IMAGE_COMPRESS_QLTY = 30;


    @NonNull
    public static String NOT_AVAILABLE = "NA";
    public static String APP_VERSION = BuildConfig.VERSION_NAME;
    public static final String KYC_DOC_TYPE = "1";
    public static final String AADHAR_DIGITS = "0123456789";
    public static final int FILE_SIZE = 120000;
    public static final String PAN_or_DL_DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @NonNull
    public static String MEMBER_ACTIVE_STATUS = "Inactive";
    @NonNull
    public static String NO_INTERNET = "No Internet!";


    @NonNull
    public static String MPIN_FILE_NAME = "mpin_" + AppConstant.APP_VERSION;
    @NonNull
    public static String AADHAR_FILE_NAME = "aadhar_" + AppConstant.APP_VERSION;
    @NonNull
    public static String BANK_ACCOUNT_FILE_NAME = "bankaccount_" + AppConstant.APP_VERSION;
    @NonNull
    public static String ADD_MEMBER_FILE_NAME = "addmember_" + AppConstant.APP_VERSION;
    @NonNull
    public static String KYC_DOC_FILE_NAME = "kycdoc_" + AppConstant.APP_VERSION;
    @NonNull
    public static String MEMBER_STATUS_FILE_NAME = "memberstatus_" + AppConstant.APP_VERSION;
    @NonNull
    public static String SHG_INACTIVATION_STATUS_FILE_NAME = "shginactivationstatus_" + AppConstant.APP_VERSION;
    @NonNull
    public static String MEMBER_UPDATE_STATUS_FILE_NAME = "memberupdatestatus_" + AppConstant.APP_VERSION;
    public static String UPDATED_DESIGNATION_FILE_NAME = "memberupdateddesignation_" + AppConstant.APP_VERSION;


    public static final String flag_AddMember = "add_member";
    public static final String flag_AadharData = "aadhaar_detail";
    public static final String flag_BankData = "bank_detail";
    public static final String flag_kyc_details = "kyc_detail";
    public static final String flag_shg_inactive_details = "shg_inactive";
    public static final String flag_member_inactive_details = "member_inactive";
    public static final String flag_update_member = "update_member";


    @NonNull
    public static String DUP_STATUS_AADHAR = "aadhardupstatus";
    @NonNull
    public static String DUP_STATUS_BANK = "bankdupstatus";
    @NonNull
    public static String DUP_STATUS_ADDMEMBER = "addmemberdupstatus";
    @NonNull
    public static String DUP_STATUS_KYC_DOC = "kycdocstatus";
    @NonNull
    public static String DUP_STATUS_SHG_INACTIVATION = "shginactivationcstatus";
    @NonNull
    public static String DUP_STATUS_MEMBER_INACTIVATION = "memberinactivationcstatus";
    @NonNull
    public static String DUP_STATUS_MEMBER_UPDATE = "memberupdatestatus";

    @NonNull
    public static String CALLING_API_AADHAR = "callingaadhar";
    @NonNull
    public static String CALLING_API_BANK = "callingbankaccount";
    @NonNull
    public static String CALLING_API_ADD_MEMBER = "callingaddmember";
    @NonNull
    public static String CALLING_API_KYC = "callingkycdoc";
    @NonNull
    public static String CALLING_API_SHG_INACTIVATION = "callingshginactivation";
    @NonNull
    public static String CALLING_API_MEMBER_INACTIVATION = "callingmemberinactivation";
    @NonNull
    public static String CALLING_API_MEMBER_UPDATE = "callingmemberupdate";

    @NonNull
    public static String DUPLICATE_API_VOLLEY_ERROR = "duplicateapivollyerror";
    @NonNull
    public static String AADHAR_API_VOLLEY_ERROR = "aadharapivollyerror";
    @NonNull
    public static String BANK_ACC_API_VOLLEY_ERROR = "bankaccapivollyerror";
    @NonNull
    public static String ADD_MEMBER_API_VOLLEY_ERROR = "addmemberapivollyerror";
    @NonNull
    public static String KYC_DOC_API_VOLLEY_ERROR = "kycdocapivollyerror";
    @NonNull
    public static String SHG_INACTIVATION_API_VOLLEY_ERROR = "shginactivationapivollyerror";
    @NonNull
    public static String MEMBER_INACTIVATION_API_VOLLEY_ERROR = "memberinactivationapivollyerror";
    @NonNull
    public static String MEMBER_UPDATE_API_VOLLEY_ERROR = "memberinactivationapivollyerror";

    /*NOTE: don't change above constants these are used in programming conditions. */

    public static final String[] textualParameters = {"name", "costumer", "account", "address", "bank", "ifsc", "micr", "branch", "nominee", "id"};


    /*Spring boot Apis*/


  /*  public static final String LOCATION_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/login";
    public static final String BANK_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/bankbranch/data";
    public static final String MEMBER_MASTER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/shgMemberDetail";
    public static final String SYNC_BANK_DETAIL = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/bankData";
    public static final String SYNC_AADHAR_DETAIL = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/aadhaarData";
    public static final String SYNC_ADD_MEMBER = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/memberData";
    public static final String SYNC_MEMBER_STATUS = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/services/modify/shgmember";
    public static final String DUPLICATE_DATA_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/checkDuplicay";
    public static final String FORGET_PASSWORD = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/resetPassword";
    public static final String SENT_OTP = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/forgotPassword";
    public static final String VILLAGE_CHECK_REQUEST = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/deleteVillageWeb";
    public static final String VILLAGE_DELETE_REQUEST = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/deleteVillage";
    public static final String SYNC_KYC_DOC = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/kycData";
    public static final String SYNC_SHG_INACTIVATION_DETAILS_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/shgInactivation";
    public static final String SYNC_MEMBER_UPDATE_API = HTTP_TYPE + "://" + IP_ADDRESS + "/" + NRLM_STATUS + "/memberInactivation";
*/


}
