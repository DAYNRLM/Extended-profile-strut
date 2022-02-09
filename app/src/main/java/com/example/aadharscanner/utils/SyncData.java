package com.example.aadharscanner.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.AadharAccountActivity;
import com.example.aadharscanner.activites.AddShgMemberActivity;
import com.example.aadharscanner.activites.LoginActivity;
import com.example.aadharscanner.activites.SplashScreen;

import com.example.aadharscanner.adapters.ShgMemberListAdapter;

import com.example.aadharscanner.database.AadharDetailSyncData;
import com.example.aadharscanner.database.AadharDetailSyncDataDao;
import com.example.aadharscanner.database.AccountDetailSyncData;
import com.example.aadharscanner.database.AccountDetailSyncDataDao;
import com.example.aadharscanner.database.BlockDaoData;
import com.example.aadharscanner.database.ChangeDesignationSyncData;
import com.example.aadharscanner.database.ChangeDesignationSyncDataDao;
import com.example.aadharscanner.database.KycDocSyncData;
import com.example.aadharscanner.database.KycDocSyncDataDao;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.database.ShgDetailData;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.database.ShgInActivSyncData;
import com.example.aadharscanner.database.ShgInActivSyncDataDao;
import com.example.aadharscanner.database.ShgInactivationSyncData;
import com.example.aadharscanner.database.ShgInactivationSyncDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncData;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncDataDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* Created by Samad Khan*/

public class SyncData {

    private String loginId;
    private String imei;
    private String deviceInfo;
    private String shortName;
    public static SyncData syncData;
    private Context context;
    private List<ShgMemberRegistrationSyncData> shgMemberRegistrationSyncDataItem;
    private List<AccountDetailSyncData> accountDetailSyncDataList;
    private List<AadharDetailSyncData> aadharDetailSyncData;
    @NonNull
    private android.os.Handler handler = new android.os.Handler();
    private List<ShgInActivSyncData> shgInActivSyncData;
    private List<ShgInactivationSyncData> shgInactivationSyncDataList;
    private List<KycDocSyncData> kycDocSyncDataList;
    private List<ChangeDesignationSyncData> changeDesignationSyncDataList;


    public synchronized static SyncData getInstance() {
        if (syncData == null) {
            syncData = new SyncData();
        }
        return syncData;
    }

    public void getLogInfo(@NonNull Context context) {
        imei = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getIMEI(), context);
        deviceInfo = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getDeviceInfo(), context);
        AppUtils.getInstance().showLog("deviceInfofromSyncdata" + deviceInfo, SyncData.class);

        List<LoginInfoData> loginInfoDataList = new AadharAccountActivity().getLoginInfo();
        if (loginInfoDataList.size() == 0) {
            loginId = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), context);
        } else {
            for (LoginInfoData loginInfoData : loginInfoDataList) {
                loginId = loginInfoData.getLoginId();
                shortName = loginInfoData.getState_short_name();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void syncData(@NonNull Context context) {
        this.context = context;

        getLogInfo(context);

        String callingApi = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
        switch (callingApi) {
            case "callingaadhar":
                /*calling api for Aadhar data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_AADHAR, context);
                break;
            case "callingbankaccount":
                /*calling api for Bank Account data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_BANK, context);
                break;

            case "callingaddmember":
                /*calling api for Added Member data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_ADDMEMBER, context);
                break;

            case "callingkycdoc":
                /*calling api for kyc doc data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_KYC_DOC, context);
                break;

            case "callingshginactivation":
                /*calling api for shg inactivation data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_SHG_INACTIVATION, context);
                break;

            case "callingmemberinactivation":
                /*calling api for member inactivation  data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_MEMBER_INACTIVATION, context);
                break;

            case "callingmemberupdate":
                /*calling api for member inactivation  data*/
                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getCallingApi(), context);
                callDuplicateApi(AppConstant.DUP_STATUS_MEMBER_UPDATE, context);
                break;


            default:
                /*calling api for Aadhar data*/
                callDuplicateApi(AppConstant.DUP_STATUS_AADHAR, context);

                /*calling api for Bank Account data*/
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callDuplicateApi(AppConstant.DUP_STATUS_BANK, context);
                    }
                }, 6000);

                /*calling api for Added Member data*/
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        callDuplicateApi(AppConstant.DUP_STATUS_ADDMEMBER, context);
                    }
                }, 12000);

                /*calling api for Kyc data*/
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        //callKycDocSyncDataApi(context);
                        callDuplicateApi(AppConstant.DUP_STATUS_KYC_DOC, context);
                    }
                }, 18000);

                /*calling api for Shg inactivation data*/
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        //callShgInactivationApi();
                        callDuplicateApi(AppConstant.DUP_STATUS_SHG_INACTIVATION, context);
                    }
                }, 24000);

                /*calling api for member inactivation data*/
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        /*callMemberInactivationApi*/
                        callDuplicateApi(AppConstant.DUP_STATUS_MEMBER_INACTIVATION, context);
                    }
                }, 30000);

                /*calling member update api*/
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        /*callingmemberupdate*/
                        callDuplicateApi(AppConstant.DUP_STATUS_MEMBER_UPDATE, context);
                    }
                }, 30000);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callDuplicateApi(@NonNull String dupStatus, @NonNull Context context) {
        JSONObject dataToCheckDup = getDataTocheckDup(dupStatus);
        try {
            if (dataToCheckDup.has("state_short_name") && dataToCheckDup.has("login_id")
                    && dataToCheckDup.getString("member_data") != "") {
                AppUtils.getInstance().showLog("dataToCheckDup" + dataToCheckDup, SyncData.class);

                dataToCheckDup = AppUtils.getInstance().wantToEncrypt(dataToCheckDup);
                AppUtils.getInstance().showLog("dataToCheckDupEncypted" + dataToCheckDup, SyncData.class);

                AppUtils.getInstance().showLog("duplicateApi" + AppConstant.DUPLICATE_DATA_API, SyncData.class);


                JsonObjectRequest checkToDuplicateRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.DUPLICATE_DATA_API
                        , dataToCheckDup, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.getInstance().showLog("duplicateResponse" + "=" + dupStatus + response, SyncData.class);
                        response = AppUtils.getInstance().wantToDecrypt(response);
                        AppUtils.getInstance().showLog("dupApiResponseDecrypt" + response, SyncData.class);
                        String status = "";
                        try {
                            if (response != null) {
                                status = response.getString("member_code");

                                if (status.equalsIgnoreCase("0")) {
                                    /**call api for sync data **/
                                    if (dupStatus.contentEquals(AppConstant.DUP_STATUS_AADHAR)) {
                                        callAadharSyncDataApi(context);
                                    } else if (dupStatus.contentEquals(AppConstant.DUP_STATUS_BANK)) {
                                        callBankAccountSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_ADDMEMBER)) {
                                        callAddMemberSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_KYC_DOC)) {
                                        callKycDocSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_SHG_INACTIVATION)) {
                                        callShgInactivationApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_MEMBER_UPDATE)) {
                                        callMemberUpdateApi(context);
                                    }else {
                                        callMemberInactivationApi(context);
                                    }

                                } else if (response.toString().contentEquals("{}")) {
                                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getDuplicateApiVolleyError(), AppConstant.DUPLICATE_API_VOLLEY_ERROR, context);
                                    AppUtils.getInstance().showLog("checkToDuplicateRequestResponseError" + "Server side exception", SyncData.class);
                                } else {
                                    /**delet all duplicate data from local and sync data again **/
                                    String[] duplicateObjects = status.split(",");

                                    if (dupStatus.contentEquals(AppConstant.DUP_STATUS_AADHAR)) {
                                        deleteAadharDupData(duplicateObjects);
                                        callAadharSyncDataApi(context);
                                    } else if (dupStatus.contentEquals(AppConstant.DUP_STATUS_BANK)) {
                                        deleteBankAccountDupData(duplicateObjects);
                                        callBankAccountSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_ADDMEMBER)) {
                                        deleteAddedMemberDupData(duplicateObjects);
                                        callAddMemberSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_KYC_DOC)) {
                                        deleteKycDocDupData(duplicateObjects);
                                        callKycDocSyncDataApi(context);
                                    } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_SHG_INACTIVATION)) {
                                        deleteShgInactivationDupData(duplicateObjects);
                                        callShgInactivationApi(context);
                                    }else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_MEMBER_UPDATE)) {
                                        deleteMemberUpdateDupData(duplicateObjects);
                                        callMemberUpdateApi(context);
                                    } else {
                                        deleteMemberInactivationDupData(duplicateObjects);
                                        callMemberInactivationApi(context);
                                    }
                                }
                            } else {
                                AppUtils.getInstance().showLog("serversideresponse{}", SyncData.class);
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getDuplicateApiVolleyError(), AppConstant.DUPLICATE_API_VOLLEY_ERROR, context);
                        AppUtils.getInstance().showLog("checkToDuplicateRequestVolleyerror" + error.toString(), SyncData.class);
                    }
                });
                checkToDuplicateRequest.setRetryPolicy(new DefaultRetryPolicy(4000
                        , 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

                SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(checkToDuplicateRequest);
            } else
                AppUtils.getInstance().showLog("Not hit duplicate api ", SyncData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteMemberUpdateDupData(String[] duplicateObjects) {

        List<ChangeDesignationSyncData> c=SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().queryBuilder()
                .where(ChangeDesignationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ChangeDesignationSyncData cd:c){
            for (int i=0;i<duplicateObjects.length;i++){
                if (cd.getMemberCode().equalsIgnoreCase(duplicateObjects[i])){
                    SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().delete(cd);
                }
            }
        }
    }

    private void callMemberUpdateApi(Context context) {
      JSONObject updateMemberObject=getUpdateMemberObject();
      if (changeDesignationSyncDataList!=null && changeDesignationSyncDataList.size()>0){

          AppUtils.getInstance().showLog("updateMemberObject" + updateMemberObject, SyncData.class);
          updateMemberObject = AppUtils.getInstance().wantToEncrypt(updateMemberObject);
          AppUtils.getInstance().showLog("updateMemberObjectEncrpted" + updateMemberObject, SyncData.class);
          AppUtils.getInstance().showLog("SyncUpdatedMemberApi" + AppConstant.SYNC_MEMBER_UPDATE_API, SyncData.class);

          JsonObjectRequest updateMemberRequest=new JsonObjectRequest(Request.Method.POST, AppConstant.SYNC_MEMBER_UPDATE_API, updateMemberObject, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                  AppUtils.getInstance().showLog("syncMemberUpdateDataResponse" + response, SyncData.class);
                  response = AppUtils.getInstance().wantToDecrypt(response);
                  AppUtils.getInstance().showLog("syncMemberUpdateResponseDecrypt" + response, SyncData.class);
                  try {
                      String status = response.getString("status");
                      if (status.contentEquals("Success")) {

                          updateMemberUpdateSyncData();

                          updateMberUpdateBackupFile(context);

                      } else {
                          PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAadharApiVolleyError(), AppConstant.MEMBER_UPDATE_API_VOLLEY_ERROR, context);
                          AppUtils.getInstance().showLog("SyncMemberUpdateResponseError", SyncData.class);
                      }
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }catch (NoSuchPaddingException e) {
                      e.printStackTrace();
                  } catch (NoSuchAlgorithmException e) {
                      e.printStackTrace();
                  } catch (IllegalBlockSizeException e) {
                      e.printStackTrace();
                  } catch (BadPaddingException e) {
                      e.printStackTrace();
                  } catch (InvalidAlgorithmParameterException e) {
                      e.printStackTrace();
                  } catch (InvalidKeyException e) {
                      e.printStackTrace();
                  } catch (UnsupportedEncodingException e) {
                      e.printStackTrace();
                  }

              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAadharApiVolleyError(), AppConstant.MEMBER_UPDATE_API_VOLLEY_ERROR, context);
                  AppUtils.getInstance().showLog("SyncMemberUpdateResponseError"+error, SyncData.class);
              }
          });

          updateMemberRequest.setRetryPolicy(new DefaultRetryPolicy(
                  10000,
                  0,
                  0f));
          SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

          SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(updateMemberRequest);
      }
    }

    public void updateMberUpdateBackupFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException{


        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.MEMBER_UPDATE_STATUS_FILE_NAME, new Cryptography().encrypt(getUpdateMemberObject().toString()));
    }

    private void updateMemberUpdateSyncData() {

        for (ChangeDesignationSyncData c:changeDesignationSyncDataList){
                c.setSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().update(c);
        }
    }

    public JSONObject getUpdateMemberObject() {
        JSONObject updateMemberObject = new JSONObject();
        try {
            updateMemberObject.accumulate("login_id", getEncryptedData(loginId));
            updateMemberObject.accumulate("state_short_name", getEncryptedData(shortName));
            updateMemberObject.accumulate("imei_no", getEncryptedData(imei));
            updateMemberObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            updateMemberObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));
            updateMemberObject.accumulate("member_data", getEncryptedData(getUpdatedMemberDetails().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtils.getInstance().showLog("statusChangObject" + updateMemberObject, SyncData.class);
        return updateMemberObject;
    }

    private JSONArray getUpdatedMemberDetails(){
        JSONArray updatedMemberDetailsArray=new JSONArray();
         changeDesignationSyncDataList=SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao()
                .queryBuilder().where(ChangeDesignationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ChangeDesignationSyncData c :changeDesignationSyncDataList){
            JSONObject memberDetails=new JSONObject();

            try {
                memberDetails.accumulate("shg_code",getEncryptedData(c.getShgCode()));
                memberDetails.accumulate("enity_code",getEncryptedData(c.getEnityCode()));
                memberDetails.accumulate("member_code",getEncryptedData(c.getMemberCode()));
                memberDetails.accumulate("updated_deg_code",getEncryptedData(c.getUpdatedDesignation()));
                memberDetails.accumulate("sync_status",getEncryptedData(c.getSyncStatus()));

                updatedMemberDetailsArray.put(memberDetails);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return updatedMemberDetailsArray;
    }

    private void deleteMemberInactivationDupData(@NonNull String[] duplicateObjects) {
        List<ShgInActivSyncData> shgInActivSyncData = SplashScreen.getInstance().getDaoSession()
                .getShgInActivSyncDataDao().queryBuilder().where(ShgInActivSyncDataDao.Properties.SyncStatus
                        .eq("0")).build().list();
        for (String string : duplicateObjects) {
            for (ShgInActivSyncData sdd : shgInActivSyncData) {
                if (string.equalsIgnoreCase(sdd.getShgMemberCode())) {
                    AppUtils.getInstance().showLog("DeletedMemberInactivation" + string, SyncData.class);
                    SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao().delete(sdd);

                    List<ShgMemberDetailsData> shgMemberDetailsDataList = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                            .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(sdd.getShgMemberCode())).build().list();
                    for (ShgMemberDetailsData shgMemberDetailsData : shgMemberDetailsDataList) {
                        shgMemberDetailsData.setMemberCurrentStatus("R");
                        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);
                    }
                }
            }
        }
    }

    private void deleteShgInactivationDupData(@NonNull String[] duplicateObjects) {

        List<ShgInactivationSyncData> shgInactivationSyncDataList = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao()
                .queryBuilder().where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (String string : duplicateObjects) {
            for (ShgInactivationSyncData shgInactivationSyncData : shgInactivationSyncDataList) {
                if (string.contentEquals(shgInactivationSyncData.getShgCode())) {
                    AppUtils.getInstance().showLog("DeletedShgInactivation" + string, SyncData.class);
                    SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().delete(shgInactivationSyncData);

                    List<ShgDetailData> shgDetailDataList = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao()
                            .queryBuilder().where(ShgDetailDataDao.Properties.ShgCode.eq(shgInactivationSyncData.getShgCode())).build().list();

                    for (ShgDetailData shgDetailData : shgDetailDataList) {
                        shgDetailData.setShgCurrentStatus("R");
                        SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().update(shgDetailData);
                    }
                }
            }
        }
    }

    private void deleteKycDocDupData(@NonNull String[] duplicateObjects) {

        List<KycDocSyncData> kycDocSyncDataList = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (String string : duplicateObjects) {
            for (KycDocSyncData kycDocSyncData : kycDocSyncDataList) {
                if (string.trim().equalsIgnoreCase(kycDocSyncData.getMemberCode().trim())) {
                    AppUtils.getInstance().showLog("DeletedKycDocs" + string, SyncData.class);
                    SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().delete(kycDocSyncData);

                    List<ShgMemberDetailsData> shgMemberDetailsDataList = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder()
                            .where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(kycDocSyncData.getMemberCode().trim())).build().list();

                    for (ShgMemberDetailsData shgMemberDetailsData : shgMemberDetailsDataList) {
                        shgMemberDetailsData.setKycStatus("1");
                        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);
                    }
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void callAadharSyncDataApi(@NonNull Context context) {
        JSONObject aaDharSyncJsonObject = aaDharSyncJsonObject(context);
        if (aadharDetailSyncData.size() > 0) {
            AppUtils.getInstance().showLog("aaDharSyncJsonObject" + aaDharSyncJsonObject, SyncData.class);

            aaDharSyncJsonObject = AppUtils.getInstance().wantToEncrypt(aaDharSyncJsonObject);
            AppUtils.getInstance().showLog("aaDharSyncJsonObjectEncrpted" + aaDharSyncJsonObject, SyncData.class);

            AppUtils.getInstance().showLog("SyncAadharApi" + AppConstant.SYNC_AADHAR_DETAIL, SyncData.class);

            JsonObjectRequest syncAadharDataRequest = new JsonObjectRequest(Request.Method.POST,
                    AppConstant.SYNC_AADHAR_DETAIL
                    , aaDharSyncJsonObject, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(JSONObject response) {
                    /*{"status":"Success"}*/
                    AppUtils.getInstance().showLog("syncAadharDataResponse" + response, SyncData.class);
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("syncAadharDataResponseDecrypt" + response, SyncData.class);
                    try {
                        String status = response.getString("status");
                        if (status.contentEquals("Success")) {
                            updateAadharSyncData();
                            try {
                                updateAadharBackupFile(context);
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAadharApiVolleyError(), AppConstant.AADHAR_API_VOLLEY_ERROR, context);
                            AppUtils.getInstance().showLog("SyncAadharDataResponseError", SyncData.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAadharApiVolleyError(), AppConstant.AADHAR_API_VOLLEY_ERROR, context);
                    AppUtils.getInstance().showLog("SyncAadharDataResponseError" + error.toString(), SyncData.class);
                }
            });
            syncAadharDataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    0,
                    0f));
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();


            SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(syncAadharDataRequest);
        }
    }

    public void updateAadharSyncData() {
        List<AadharDetailSyncData> aadharDetailSyncDataList = SplashScreen.getInstance().getDaoSession()
                .getAadharDetailSyncDataDao().queryBuilder().where(AadharDetailSyncDataDao.Properties
                        .AadharSyncStatus.eq("0")).build().list();
        for (int i = 0; i < aadharDetailSyncDataList.size(); i++) {
            AadharDetailSyncData aadharDetailSyncData = aadharDetailSyncDataList.get(i);
            aadharDetailSyncData.setAadharSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao().update(aadharDetailSyncData);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateAadharBackupFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.AADHAR_FILE_NAME, new Cryptography().encrypt(aaDharSyncJsonObject(context).toString()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callBankAccountSyncDataApi(@NonNull Context appContext) {
        JSONObject accountSyncJsonObject = accountSyncJsonObject(appContext);

        if (accountDetailSyncDataList.size() > 0) {
            AppUtils.getInstance().showLog("accountSyncJsonObject" + accountSyncJsonObject, SyncData.class);

            accountSyncJsonObject = AppUtils.getInstance().wantToEncrypt(accountSyncJsonObject);
            AppUtils.getInstance().showLog("accountSyncJsonObjectEncrypted" + accountSyncJsonObject, SyncData.class);

            AppUtils.getInstance().showLog("SyncBankApi" + AppConstant.SYNC_BANK_DETAIL, SyncData.class);


            AppUtils.getInstance().showLog("accountSyncJsonObject" + accountSyncJsonObject, SyncData.class);
            JsonObjectRequest syncBankDataRequest = new JsonObjectRequest(Request.Method.POST,
                    AppConstant.SYNC_BANK_DETAIL
                    , accountSyncJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    /*{"status":"Success"}*/
                    AppUtils.getInstance().showLog("syncBankDataResponse" + response, SyncData.class);
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("syncBankDataResponseDecrypt" + response, SyncData.class);
                    try {
                        String status = response.getString("status");
                        if (status.contentEquals("Success")) {
                            updateBankAccountSyncTable();
                            try {
                                updateBankAccountBackupFile(appContext);
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getBankAccApiVolleyError(), AppConstant.BANK_ACC_API_VOLLEY_ERROR, appContext);
                            // PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getBankAccountApiResponse(), status, appContext);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getBankAccApiVolleyError(), AppConstant.BANK_ACC_API_VOLLEY_ERROR, appContext);
                    AppUtils.getInstance().showLog("BankAccountSyncVolleyError" + error, SyncData.class);
                }
            });

            syncBankDataRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    0,
                    0f));
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(appContext).addToRequestQueue(syncBankDataRequest);
        }
    }


    public void updateBankAccountSyncTable() {

        List<AccountDetailSyncData> accountDetailSyncDataList = SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao()
                .queryBuilder().where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (int i = 0; i < accountDetailSyncDataList.size(); i++) {
            AccountDetailSyncData accountDetailSyncData = accountDetailSyncDataList.get(i);
            accountDetailSyncData.setSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao().update(accountDetailSyncData);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateBankAccountBackupFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.BANK_ACCOUNT_FILE_NAME, new Cryptography().encrypt(accountSyncJsonObject(context).toString()));

    }


    public void deleteAddedMemberDupData(@NonNull String[] duplicateObjects) {
        List<ShgMemberRegistrationSyncData> shgMemberRegistrationSyncDataItem = SplashScreen.getInstance().getDaoSession()
                .getShgMemberRegistrationSyncDataDao().queryBuilder()
                .where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (String str : duplicateObjects) {
            for (ShgMemberRegistrationSyncData s : shgMemberRegistrationSyncDataItem) {
                if (str.equalsIgnoreCase(s.getShgMemberuniqueCode())) {
                    AppUtils.getInstance().showLog("DeletedAddMembers" + str, SyncData.class);
                    SplashScreen.getInstance().getDaoSession()
                            .getShgMemberRegistrationSyncDataDao().delete(s);
                }
            }
        }
        AppUtils.getInstance().showLog("lengthOfList" + shgMemberRegistrationSyncDataItem.size(), SyncData.class);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callAddMemberSyncDataApi(@NonNull Context appContext) {

        JSONObject getShgMemberJsonData = getShgMemberJsonData(appContext);
        if (shgMemberRegistrationSyncDataItem.size() > 0) {
            AppUtils.getInstance().showLog("getShgMemberJsonData" + getShgMemberJsonData, SyncData.class);

            getShgMemberJsonData = AppUtils.getInstance().wantToEncrypt(getShgMemberJsonData);
            AppUtils.getInstance().showLog("getShgMemberJsonDataEncrpted" + getShgMemberJsonData, SyncData.class);

            AppUtils.getInstance().showLog("SyncAddMemberApi" + AppConstant.SYNC_ADD_MEMBER, SyncData.class);

            AppUtils.getInstance().showLog("getShgMemberJsonData" + getShgMemberJsonData, SyncData.class);
            JsonObjectRequest addMemberSyncRequest = new JsonObjectRequest(Request.Method.POST,
                    AppConstant.SYNC_ADD_MEMBER,
                    getShgMemberJsonData
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AppUtils.getInstance().showLog("addMemberSyncResponse" + response, SyncData.class);
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("addMemberSyncResponseDecrypt" + response, SyncData.class);
                    try {
                        String status = response.getString("status");
                        if (status.equalsIgnoreCase("Success")) {
                            updateAddMemberSyncStatus();
                            updateAddMemberBackUpFile(appContext);
                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAddMemberApiVolleyError(), AppConstant.ADD_MEMBER_API_VOLLEY_ERROR, appContext);
                            AppUtils.getInstance().showLog("addMemberSyncStatus" + status, AddShgMemberActivity.class);
                        }
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("exception_in_Add_member_response" + e, AddShgMemberActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getAddMemberApiVolleyError(), AppConstant.ADD_MEMBER_API_VOLLEY_ERROR, appContext);
                    AppUtils.getInstance().showLog("addMemberSyncError" + error, AddShgMemberActivity.class);
                }
            });

            addMemberSyncRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    0,
                    0f));
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(appContext.getApplicationContext()).addToRequestQueue(addMemberSyncRequest);
        }
    }

    public void updateAddMemberSyncStatus() {
        /**update sync status
         * 0 to 1 after get sucess
         * response from server**/
        List<ShgMemberRegistrationSyncData> shgMemberRegistrationSyncDataList = SplashScreen.getInstance()
                .getDaoSession().getShgMemberRegistrationSyncDataDao().queryBuilder()
                .where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (ShgMemberRegistrationSyncData smrsd : shgMemberRegistrationSyncDataList) {
            smrsd.setSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao().update(smrsd);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateAddMemberBackUpFile(Context context) {
        try {
            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.ADD_MEMBER_FILE_NAME, new Cryptography().encrypt(getShgMemberJsonData(context).toString()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }


    /****************************getting duplicate data to check duplicacy at server side *********************  */
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getDataTocheckDup(@NonNull String dupStatus) {
        JSONObject dataToCheckDup = new JSONObject();

        if (dupStatus.contentEquals(AppConstant.DUP_STATUS_AADHAR)) {
            dataToCheckDup = getUnSyncAadharData(AppConstant.DUP_STATUS_AADHAR);
        } else if (dupStatus.contentEquals(AppConstant.DUP_STATUS_BANK)) {
            dataToCheckDup = getUnSyncAadharData(AppConstant.DUP_STATUS_BANK);
        } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_ADDMEMBER)) {
            dataToCheckDup = getUnSyncAddMemberData();
        } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_KYC_DOC)) {
            dataToCheckDup = getUnSyncKycDocData();
        } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_SHG_INACTIVATION)) {
            dataToCheckDup = getUnSyncShgInactiveData();
        } else if (dupStatus.equalsIgnoreCase(AppConstant.DUP_STATUS_MEMBER_UPDATE)) {
            dataToCheckDup = getUnSyncMemberUpdateData();
        }else {
            dataToCheckDup = getUnSyncMemberInactiveData();
        }
        return dataToCheckDup;
    }

    private JSONObject getUnSyncMemberUpdateData() {
        JSONObject jsonObject = new JSONObject();
        try {
            //isDupAddMemberData.accumulate("login_id", "HPBINARENDRA");
            jsonObject.accumulate("login_id", loginId);
            jsonObject.accumulate("state_short_name", shortName);
            jsonObject.accumulate("flag", AppConstant.flag_update_member);
            jsonObject.accumulate("shg_code", "001122");
            jsonObject.accumulate("member_data", getMemberCodeForUpdation());
            jsonObject.accumulate("imei_no", imei);
            jsonObject.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            jsonObject.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String getMemberCodeForUpdation() {
        String memberCodes = "";
        List<ChangeDesignationSyncData> changeDesignationSyncDataList=SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao()
                .queryBuilder().where(ChangeDesignationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ChangeDesignationSyncData cdsd : changeDesignationSyncDataList) {
            memberCodes += cdsd.getMemberCode() + ",";
        }
        return AppUtils.getInstance().removeCommaFromLast(memberCodes);
    }

    @NonNull
    private JSONObject getUnSyncMemberInactiveData() {
        JSONObject jsonObject = new JSONObject();
        try {
            //isDupAddMemberData.accumulate("login_id", "HPBINARENDRA");
            jsonObject.accumulate("login_id", loginId);
            jsonObject.accumulate("state_short_name", shortName);
            jsonObject.accumulate("flag", AppConstant.flag_member_inactive_details);
            jsonObject.accumulate("shg_code", "001122");
            jsonObject.accumulate("member_data", getMemberCodeForInactivation());
            jsonObject.accumulate("imei_no", imei);
            jsonObject.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            jsonObject.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Nullable
    private String getMemberCodeForInactivation() {
        String memberCodes = "";
        List<ShgInActivSyncData> shgInActivSyncData = SplashScreen.getInstance().getDaoSession()
                .getShgInActivSyncDataDao().queryBuilder().where(ShgInActivSyncDataDao.Properties.SyncStatus
                        .eq("0")).build().list();
        for (ShgInActivSyncData sdd : shgInActivSyncData) {
            memberCodes += sdd.getShgMemberCode() + ",";
        }
        return AppUtils.getInstance().removeCommaFromLast(memberCodes);
    }

    @NonNull
    private JSONObject getUnSyncShgInactiveData() {
        JSONObject jsonObject = new JSONObject();
        /*flag_shg_inactive_details*/
        try {
            //isDupAddMemberData.accumulate("login_id", "HPBINARENDRA");
            jsonObject.accumulate("login_id", loginId);
            jsonObject.accumulate("state_short_name", shortName);
            jsonObject.accumulate("flag", AppConstant.flag_shg_inactive_details);
            jsonObject.accumulate("shg_code", "001122");
            jsonObject.accumulate("member_data", getShgCodeForInactivation());
            jsonObject.accumulate("imei_no", imei);
            jsonObject.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            jsonObject.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Nullable
    private String getShgCodeForInactivation() {
        String shgCodes = "";
        List<ShgInactivationSyncData> shgInactivationSyncDataList = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao()
                .queryBuilder().where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ShgInactivationSyncData shgInactivationSyncData : shgInactivationSyncDataList) {
            shgCodes += shgInactivationSyncData.getShgCode() + ",";
        }
        return AppUtils.getInstance().removeCommaFromLast(shgCodes);
    }

    @NonNull
    private JSONObject getUnSyncKycDocData() {

        JSONObject isDupKycDocData = new JSONObject();

        try {
            //isDupAddMemberData.accumulate("login_id", "HPBINARENDRA");
            isDupKycDocData.accumulate("login_id", loginId);
            isDupKycDocData.accumulate("state_short_name", shortName);
            isDupKycDocData.accumulate("flag", AppConstant.flag_kyc_details);
            isDupKycDocData.accumulate("shg_code", "001122");// not required
            isDupKycDocData.accumulate("member_data", getMemberCodeForKycDoc());
            isDupKycDocData.accumulate("imei_no", imei);
            isDupKycDocData.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            isDupKycDocData.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isDupKycDocData;
    }

    @Nullable
    private String getMemberCodeForKycDoc() {
        String memberCodes = "";
        List<KycDocSyncData> kycDocSyncDataList = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (KycDocSyncData kycDocSyncData : kycDocSyncDataList) {
            memberCodes += kycDocSyncData.getMemberCode() + ",";
        }
        return AppUtils.getInstance().removeCommaFromLast(memberCodes);
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getUnSyncAadharData(@NonNull String status) {
        JSONObject isDupAadharData = new JSONObject();
        String flag = "";
        String memberCodes = "";

        if (status.contentEquals(AppConstant.DUP_STATUS_AADHAR)) {
            flag = AppConstant.flag_AadharData;
            memberCodes = getMemberCodeForAadhar();
        } else if (status.contentEquals(AppConstant.DUP_STATUS_BANK)) {
            flag = AppConstant.flag_BankData;
            memberCodes = getMemberCodeForBank();
        }

        try {
            // isDupAadharData.accumulate("login_id", "HPBINARENDRA");
            isDupAadharData.accumulate("login_id", loginId);
            isDupAadharData.accumulate("state_short_name", shortName);
            isDupAadharData.accumulate("flag", flag);
            isDupAadharData.accumulate("member_data", memberCodes);
            isDupAadharData.accumulate("shg_code", "001122");
            isDupAadharData.accumulate("imei_no", imei);
            isDupAadharData.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            isDupAadharData.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isDupAadharData;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getUnSyncAddMemberData() {
        JSONObject isDupAddMemberData = new JSONObject();

        try {
            //isDupAddMemberData.accumulate("login_id", "HPBINARENDRA");
            isDupAddMemberData.accumulate("login_id", loginId);
            isDupAddMemberData.accumulate("state_short_name", shortName);
            isDupAddMemberData.accumulate("flag", AppConstant.flag_AddMember);
            isDupAddMemberData.accumulate("shg_code", "001122");
            isDupAddMemberData.accumulate("member_data", getAddedMemAadhar());
            isDupAddMemberData.accumulate("imei_no", imei);
            isDupAddMemberData.accumulate("device_name", deviceInfo);
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            isDupAddMemberData.accumulate("location_coordinate", location[0] + "," + location[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isDupAddMemberData;
    }

    @Nullable
    public static String getAddedMemAadhar() {
        String addedMemAadhar = "";
        List<ShgMemberRegistrationSyncData> addedMemberAadharList = SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao()
                .queryBuilder().where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ShgMemberRegistrationSyncData shgMemberRegistrationSyncData : addedMemberAadharList) {
            addedMemAadhar += shgMemberRegistrationSyncData.getShgMemberuniqueCode() + ",";
        }
        return AppUtils.getInstance().removeCommaFromLast(addedMemAadhar);
    }


    /**************************************************Linon Bhalla**************************************************************/
    /**********************for Added Member sync Data**********************************/
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getShgMemberJsonData(Context context) {
        JSONObject addMemberRequestObject = new JSONObject();
        JSONArray jsonMemberArray = new JSONArray();
        shgMemberRegistrationSyncDataItem = SplashScreen.getInstance().getDaoSession()
                .getShgMemberRegistrationSyncDataDao().queryBuilder()
                .where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (ShgMemberRegistrationSyncData shgMemberRegistrationSyncData : shgMemberRegistrationSyncDataItem) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("village_code", getEncryptedData(shgMemberRegistrationSyncData.getVillagecode()));
                jsonObject.accumulate("shg_code", getEncryptedData(shgMemberRegistrationSyncData.getShgcode()));
                jsonObject.accumulate("member_android_code", getEncryptedData(shgMemberRegistrationSyncData.getShgMemberuniqueCode()));
                jsonObject.accumulate("aadhaar_no", getEncryptedData(shgMemberRegistrationSyncData.getAadharNumber()));
                jsonObject.accumulate("member_name", getEncryptedData(shgMemberRegistrationSyncData.getMemberName().toUpperCase()));
                jsonObject.accumulate("dob", getEncryptedData(shgMemberRegistrationSyncData.getMemberYOB()));
                jsonObject.accumulate("belonging_name", getEncryptedData(shgMemberRegistrationSyncData.getMemberFathername().toUpperCase()));
                jsonObject.accumulate("gender", getEncryptedData(shgMemberRegistrationSyncData.getMemberGender()));
                jsonObject.accumulate("address", getEncryptedData(shgMemberRegistrationSyncData.getMemberAddress()));
                jsonObject.accumulate("mobile_number", getEncryptedData(shgMemberRegistrationSyncData.getMembermobileNumber()));
                jsonObject.accumulate("social_catagory", getEncryptedData(shgMemberRegistrationSyncData.getSocialCatagory()));
                jsonObject.accumulate("disability", getEncryptedData(shgMemberRegistrationSyncData.getDisablity()));
                jsonObject.accumulate("religion", getEncryptedData(shgMemberRegistrationSyncData.getReligion()));
                jsonObject.accumulate("pip_category", getEncryptedData(shgMemberRegistrationSyncData.getPipCatagory()));
                jsonObject.accumulate("leader", getEncryptedData(shgMemberRegistrationSyncData.getLeader()));
                jsonObject.accumulate("education", getEncryptedData(shgMemberRegistrationSyncData.getEducationStandard()));
                jsonObject.accumulate("shg_joining_date", getEncryptedData(shgMemberRegistrationSyncData.getMamberDateOfJoining()));
                jsonObject.accumulate("bank_code", getEncryptedData(shgMemberRegistrationSyncData.getBankNameCode()));
                jsonObject.accumulate("branch_code", getEncryptedData(shgMemberRegistrationSyncData.getBankBranchCode()));
                jsonObject.accumulate("account_number", getEncryptedData(shgMemberRegistrationSyncData.getAccountNumber()));
                jsonObject.accumulate("aadhar_seeded_sb_ac", getEncryptedData(shgMemberRegistrationSyncData.getAadharSeededAccount()));
                jsonObject.accumulate("enroll_in_pmjy", getEncryptedData(shgMemberRegistrationSyncData.getPmjjy()));
                jsonObject.accumulate("enroll_in_pmsby", getEncryptedData(shgMemberRegistrationSyncData.getPmsby()));
                jsonObject.accumulate("enroll_in_lic", getEncryptedData(shgMemberRegistrationSyncData.getLifeInsurance()));
                jsonObject.accumulate("enroll_in_hic", getEncryptedData(shgMemberRegistrationSyncData.getPensionScheme()));
                jsonObject.accumulate("secc_no", getEncryptedData(shgMemberRegistrationSyncData.getSeccNo()));
                jsonObject.accumulate("book_keeper_member", getEncryptedData(shgMemberRegistrationSyncData.getIsShopKeeper()));
                jsonObject.accumulate("other_education", getEncryptedData(shgMemberRegistrationSyncData.getOtherEducation()));
                jsonObject.accumulate("pvgt_status", getEncryptedData(shgMemberRegistrationSyncData.getPvgtStatus()));
                jsonObject.accumulate("qr_reader", getEncryptedData(shgMemberRegistrationSyncData.getScanStatus()));
                jsonObject.accumulate("member_image", Base64.encodeToString(shgMemberRegistrationSyncData.getMemberPhoto(), Base64.DEFAULT));
                jsonObject.accumulate("passbook_image", Base64.encodeToString(shgMemberRegistrationSyncData.getPassbookPhoto(), Base64.DEFAULT));
                jsonObject.accumulate("consent_pdf", Base64.encodeToString(shgMemberRegistrationSyncData.getConcentForm(), Base64.DEFAULT));
                jsonMemberArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {


            addMemberRequestObject.accumulate("login_id", getEncryptedData(loginId));
            addMemberRequestObject.accumulate("state_short_name", getEncryptedData(shortName));//get from database
            addMemberRequestObject.accumulate("imei_no", getEncryptedData(imei));
            addMemberRequestObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            addMemberRequestObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));
            addMemberRequestObject.accumulate("member_data", getEncryptedData(jsonMemberArray.toString()));

        } catch (JSONException e) {
            AppUtils.getInstance().showLog("addMemberRequestObjectExp" + addMemberRequestObject, SyncData.class);
        }
        AppUtils.getInstance().showLog("addMemberRequestObject" + addMemberRequestObject, SyncData.class);
        return addMemberRequestObject;
    }

    public String getEncryptedData(String data) {
        Cryptography cryptography = null;
        String encrypt = "";
        try {
            cryptography = new Cryptography();
            encrypt = cryptography.encrypt(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt;
    }

    /**********************************************for send aadhar data**********************************************************************/

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject aaDharSyncJsonObject(Context context) {
        JSONObject accountJsonObject = new JSONObject();
        try {
            accountJsonObject.accumulate("login_id", getEncryptedData(loginId));
            accountJsonObject.accumulate("block_code", getEncryptedData(getBlockCode()));
            accountJsonObject.accumulate("state_short_name", getEncryptedData(shortName));
            accountJsonObject.accumulate("account_data", getEncryptedData(getAadhaarJsonArray().toString()));
            accountJsonObject.accumulate("imei_no", getEncryptedData(imei));
            accountJsonObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            accountJsonObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // AppUtils.getInstance().showLog("accountJsonObject" + accountJsonObject, LoginActivity.class);
        return accountJsonObject;

    }

    @NonNull
    public Object getAadhaarJsonArray() {
        JSONArray jsonAadhaarArray = new JSONArray();
        SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao().detachAll();
        aadharDetailSyncData = SplashScreen.getInstance().getDaoSession()
                .getAadharDetailSyncDataDao().queryBuilder().where(AadharDetailSyncDataDao.Properties
                        .AadharSyncStatus.eq("0")).build().list();
        for (AadharDetailSyncData aadharDetailSyncData1 : aadharDetailSyncData) {
            JSONObject jsonObject = new JSONObject();
            try {
                Cryptography cryptography = new Cryptography();
                String village_code = cryptography.encrypt(aadharDetailSyncData1.getVillageCode());
                String shg_code = cryptography.encrypt(aadharDetailSyncData1.getShgCode());
                String shg_member_code = cryptography.encrypt(aadharDetailSyncData1.getShgMemberCode());
                String aadhaar_uid = cryptography.encrypt(aadharDetailSyncData1.getAadharNumber());
                String aadhaar_name = cryptography.encrypt(aadharDetailSyncData1.getAadharName().toUpperCase());
                String aadhaar_yob = cryptography.encrypt(aadharDetailSyncData1.getAadharYOB());
                String aadhaar_father_name = cryptography.encrypt(aadharDetailSyncData1.getAadharFatherName().toUpperCase());
                String aadhaar_gender = cryptography.encrypt(aadharDetailSyncData1.getAadharGender());
                String aadhaar_address = cryptography.encrypt(aadharDetailSyncData1.getAadharAddress());
                String aadhaar_MobileNumber = cryptography.encrypt(aadharDetailSyncData1.getAadhaarMobileNumber());
                String scanStatus = cryptography.encrypt(aadharDetailSyncData1.getScanStatus());
                String gps_cordinates = cryptography.encrypt(aadharDetailSyncData1.getLatLong());
                // String postalCode = aadharDetailSyncData1.getPostalCode();
                /* if (postalCode != null || !postalCode.equalsIgnoreCase("")) {
                 *//*aadhaar_address*//*
                }*/
               /* String postalCode = aadharDetailSyncData1.getPostalCode();
                if (postalCode != null || !postalCode.equalsIgnoreCase("")) {
                    *//*aadhaar_address*//*
                    jsonObject.accumulate("postal_code", postalCode);
                }*/
                jsonObject.accumulate("village_code", village_code);
                jsonObject.accumulate("shg_code", shg_code);
                jsonObject.accumulate("shg_member_code", shg_member_code);
                jsonObject.accumulate("aadhaar_no", aadhaar_uid);
                jsonObject.accumulate("member_name", aadhaar_name);
                /* ask for confirmation */
                jsonObject.accumulate("dob", aadhaar_yob);
                jsonObject.accumulate("belonging_name", aadhaar_father_name);
                jsonObject.accumulate("gender", aadhaar_gender);
                jsonObject.accumulate("address", aadhaar_address);
                jsonObject.accumulate("mobile_no", aadhaar_MobileNumber);
                jsonObject.accumulate("qr_reader", scanStatus);
                //lat long and image is not encrypted
                jsonObject.accumulate("gps_location_coordinate", gps_cordinates);
                jsonObject.accumulate("member_image", Base64.encodeToString(aadharDetailSyncData1.getAadhaarImage(), Base64.DEFAULT));
                String consentPDF=Base64.encodeToString(aadharDetailSyncData1.getConcentForm(), Base64.DEFAULT);
                /* Base64.getEncoder().encodeToString("Hello".getBytes())*/
                AppUtils.getInstance().showLog("consentPDF"+consentPDF,SyncData.class);
                jsonObject.accumulate("consent_pdf",consentPDF);

                jsonAadhaarArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return jsonAadhaarArray;
    }

    public String getBlockCode() {
        String bCode = "";
        List<BlockDaoData> blockDaoData = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().queryBuilder().build().list();
        for (BlockDaoData blCode : blockDaoData) {
            bCode = blCode.getBlockCode();

        }
       /* //BlockDaoData blockDaoData =SplashScreen.getInstance().get
        String bCode = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao()
                .queryBuilder().limit(1).build().unique().getBlockCode();*/
        return bCode;
    }

    /**********************************************for send bank details data**********************************************************/

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject accountSyncJsonObject(Context context) {

        JSONObject accountJsonObject = new JSONObject();
        /*get login id and blockcode from file and or database */
        try {
            accountJsonObject.accumulate("login_id", getEncryptedData(loginId));
            accountJsonObject.accumulate("block_code", getEncryptedData(getBlockCode()));
            accountJsonObject.accumulate("state_short_name", getEncryptedData(shortName));
            accountJsonObject.accumulate("account_data", getEncryptedData(getAccountJsonArray().toString()));
            accountJsonObject.accumulate("imei_no", getEncryptedData(imei));
            accountJsonObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            accountJsonObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtils.getInstance().showLog("accountJsonObject:::" + AppConstant.SYNC_BANK_DETAIL + "......" + accountJsonObject, SyncData.class);
        return accountJsonObject;
    }

    @NonNull
    private JSONArray getAccountJsonArray() {
        JSONArray jsonAccountArray = new JSONArray();
        SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao().detachAll();
        accountDetailSyncDataList = SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao()
                .queryBuilder().where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (AccountDetailSyncData accountDetailSyncData1 : accountDetailSyncDataList) {
            JSONObject jsonObject = new JSONObject();
            try {

                //encypted account data formate
                Cryptography cryptography = new Cryptography();
                String village_code = cryptography.encrypt(accountDetailSyncData1.getVillageCode());
                String shg_code = cryptography.encrypt(accountDetailSyncData1.getShgCode());
                String shg_member_code = cryptography.encrypt(accountDetailSyncData1.getShgMemberCode());
                String bank_name_code = cryptography.encrypt(accountDetailSyncData1.getBankNameCode());
                String bank_branch_code = cryptography.encrypt(accountDetailSyncData1.getBankBranchCode());
                //  String ifsc_number = cryptography.encrypt(accountDetailSyncData1.getIfscNumber());
                String account_number = cryptography.encrypt(accountDetailSyncData1.getAccountNumber());
                String cordinate = cryptography.encrypt(accountDetailSyncData1.getLatLong());
                jsonObject.accumulate("village_code", village_code);
                jsonObject.accumulate("shg_code", shg_code);
                jsonObject.accumulate("shg_member_code", shg_member_code);
                jsonObject.accumulate("bank_code", bank_name_code);
                jsonObject.accumulate("branch_code", bank_branch_code);
                // jsonObject.accumulate("ifsc_number",ifsc_number );
                // jsonObject.accumulate("shg_member_name",ifsc_number );
                jsonObject.accumulate("account_number", account_number);
                //location and image is not encrypted
                jsonObject.accumulate("gps_location_coordinate", cordinate);
                jsonObject.accumulate("passbook_image", Base64.encodeToString(accountDetailSyncData1.getPhotoImage(), Base64.DEFAULT));


                //without encyption
               /*
                jsonObject.accumulate("village_code", accountDetailSyncData1.getVillageCode());
                jsonObject.accumulate("shg_code", accountDetailSyncData1.getShgCode());
                jsonObject.accumulate("shg_member_code", accountDetailSyncData1.getShgMemberCode());
                jsonObject.accumulate("bank_name_code", accountDetailSyncData1.getBankNameCode());
                jsonObject.accumulate("bank_branch_code", accountDetailSyncData1.getBankBranchCode());
                jsonObject.accumulate("ifsc_number", accountDetailSyncData1.getIfscNumber());
                jsonObject.accumulate("account_number", accountDetailSyncData1.getAccountNumber());
                jsonObject.accumulate("location_coordinates", accountDetailSyncData1.getLatLong());
                jsonObject.accumulate("image", Base64.encodeToString(accountDetailSyncData1.getPhotoImage(), Base64.DEFAULT));*/
                jsonAccountArray.put(jsonObject);
            } catch (JSONException e) {
                AppUtils.getInstance().showLog("banksyncobjectExcp" + e, SyncData.class);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return jsonAccountArray;
    }


    /*************************************************inactive data **************************************************************/

    @Nullable
    private String getMemberCodeForAadhar() {
        String memberCodeData = "";
        List<AadharDetailSyncData> aadharDetailSyncData = SplashScreen.getInstance().getDaoSession()
                .getAadharDetailSyncDataDao().queryBuilder()
                .where(AadharDetailSyncDataDao.Properties.AadharSyncStatus.eq("0")).build().list();
        for (AadharDetailSyncData memberCode : aadharDetailSyncData) {
            memberCodeData += memberCode.getShgMemberCode() + ",";

        }
        return AppUtils.getInstance().removeCommaFromLast(memberCodeData);

    }

    private void deleteAadharDupData(@NonNull String[] duplicateData) {
        List<AadharDetailSyncData> aadharDetailSyncData = SplashScreen.getInstance().getDaoSession()
                .getAadharDetailSyncDataDao().queryBuilder()
                .where(AadharDetailSyncDataDao.Properties.AadharSyncStatus.eq("0")).build().list();
        for (String str : duplicateData) {
            for (AadharDetailSyncData s : aadharDetailSyncData) {
                if (str.equalsIgnoreCase(s.getShgMemberCode())) {
                    AppUtils.getInstance().showLog("DeletedAadharDupData" + str, SyncData.class);
                    SplashScreen.getInstance().getDaoSession()
                            .getAadharDetailSyncDataDao().delete(s);
                    List<ShgMemberDetailsData> shgMemberDetailsDataList = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                            .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(s.getShgMemberCode())).build().list();
                    for (ShgMemberDetailsData shgMemberDetailsData : shgMemberDetailsDataList)
                        shgMemberDetailsData.setMemAadharCurrentStatus("R");
                }
            }
        }
        // AppUtils.getInstance().showLog("lengthOfList" + shgMemberRegistrationSyncDataItem.size(), AddShgMemberActivity.class);
    }

    /*************************************************get duplicate data for bank Data **************************************************************/

    @Nullable
    private String getMemberCodeForBank() {
        String memberCodeData = "";
        List<AccountDetailSyncData> accDetailSyncData = SplashScreen.getInstance().getDaoSession()
                .getAccountDetailSyncDataDao().queryBuilder()
                .where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (AccountDetailSyncData memberCode : accDetailSyncData) {
            memberCodeData += memberCode.getShgMemberCode() + ",";

        }
        return AppUtils.getInstance().removeCommaFromLast(memberCodeData);

    }

    private void deleteBankAccountDupData(@NonNull String[] duplicateData) {
        List<AccountDetailSyncData> accDetailSyncData = SplashScreen.getInstance().getDaoSession()
                .getAccountDetailSyncDataDao().queryBuilder()
                .where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (String str : duplicateData) {
            for (AccountDetailSyncData s : accDetailSyncData) {
                if (str.equalsIgnoreCase(s.getShgMemberCode())) {
                    AppUtils.getInstance().showLog("DeletedBankAccData" + str, SyncData.class);
                    SplashScreen.getInstance().getDaoSession()
                            .getAccountDetailSyncDataDao().delete(s);
                    ShgMemberDetailsData shgMemberDetailsData = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                            .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgMemberCode.eq(s.getShgMemberCode())).limit(1).unique();
                    shgMemberDetailsData.setMemBankAccCurrentStatus("R");
                    SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);
                }
            }
        }
    }

    /*=============================Kyc document Sync data api work========================*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callKycDocSyncDataApi(@NonNull Context context) {

        JSONObject kycDocSyncDataObject = getKycSyncdata(context);
        if (kycDocSyncDataList.size() > 0) {
            AppUtils.getInstance().showLog("kycDocSyncDataObject" + kycDocSyncDataObject, SyncData.class);

            kycDocSyncDataObject = AppUtils.getInstance().wantToEncrypt(kycDocSyncDataObject);
            AppUtils.getInstance().showLog("kycDocSyncDataObjectEncrypted" + kycDocSyncDataObject, SyncData.class);

            AppUtils.getInstance().showLog("SyncKycDataApi" + AppConstant.SYNC_KYC_DOC, SyncData.class);


            JsonObjectRequest kycDocSyncDataObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.SYNC_KYC_DOC
                    , kycDocSyncDataObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    /*{"status":"Failure"}*/
                    AppUtils.getInstance().showLog("kycDocSyncDataObjectRequest" + response, SyncData.class);
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("kycDocSyncDataObjectRequestDecrypt" + response, SyncData.class);
                    try {
                        if (response.getString("status").equalsIgnoreCase("Success")) {
                            updateKycDocSyncTable();
                            updateKycDocSyncBackUpFile(context);
                        } else {
                            //show error
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getKycDocApiVolleyError(), AppConstant.KYC_DOC_API_VOLLEY_ERROR, context);
                            AppUtils.getInstance().showLog("kycDocSyncDataObjectResponseStatus" + response.getString("status"), AddShgMemberActivity.class);
                        }

                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("kycDocSyncDataResponseObjectJsonexp" + e.toString(), SyncData.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getKycDocApiVolleyError(), AppConstant.KYC_DOC_API_VOLLEY_ERROR, context);
                    AppUtils.getInstance().showLog("kycDocSyncDataObjectRequestError" + error, SyncData.class);
                }
            });
            kycDocSyncDataObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, 0));
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();
            SingletonVolley.getInstance(context.getApplicationContext()).addToRequestQueue(kycDocSyncDataObjectRequest);
        }
    }


    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getKycSyncdata(Context context) {
        JSONObject kycSynDataObject = new JSONObject();

        try {
            kycSynDataObject.accumulate("imei_no", getEncryptedData(imei));
            kycSynDataObject.accumulate("login_id", getEncryptedData(loginId));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            kycSynDataObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));
            kycSynDataObject.accumulate("device_name", getEncryptedData(deviceInfo));
            kycSynDataObject.accumulate("state_short_name", getEncryptedData(shortName));
            kycSynDataObject.accumulate("kyc_detail", getEncryptedData(getKycDocArray().toString()));
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("kycSynDataObjectExcp" + e.toString(), SyncData.class);
        }

        return kycSynDataObject;
    }

    @NonNull
    public JSONArray getKycDocArray() {
        JSONArray kycSyncArray = new JSONArray();
        kycDocSyncDataList = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (KycDocSyncData kycDocSyncData : kycDocSyncDataList) {

            JSONObject kycDocObject = new JSONObject();
            try {
                kycDocObject.accumulate("village_code", getEncryptedData(kycDocSyncData.getVillage_code()));
                kycDocObject.accumulate("shg_member_code", getEncryptedData(kycDocSyncData.getMemberCode()));
                kycDocObject.accumulate("doc_id", getEncryptedData(kycDocSyncData.getDocId()));
                kycDocObject.accumulate("doc_number", getEncryptedData(kycDocSyncData.getDocNo()));
                kycDocObject.accumulate("shg_code", getEncryptedData(kycDocSyncData.getShgCode()));
                /*images are not the part of encryption*/
                kycDocObject.accumulate("front_image", Base64.encodeToString(kycDocSyncData.getDocFrontImage(), Base64.DEFAULT));
                if (kycDocSyncData.getDocBackImage() == null) {
                    kycDocObject.accumulate("back_image", "");
                } else {
                    kycDocObject.accumulate("back_image", Base64.encodeToString(kycDocSyncData.getDocBackImage(), Base64.DEFAULT));
                }

                kycSyncArray.put(kycDocObject);
            } catch (JSONException e) {
                AppUtils.getInstance().showLog("kycDocObjectExcp" + e.toString(), SyncData.class);
            }
        }

        return kycSyncArray;
    }

    private void updateKycDocSyncTable() {
        List<KycDocSyncData> kycDocSyncDataList = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0")).build().list();

        for (KycDocSyncData kycDocSyncData : kycDocSyncDataList) {
            kycDocSyncData.setSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().update(kycDocSyncData);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateKycDocSyncBackUpFile(Context context) {
        try {
            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.KYC_DOC_FILE_NAME, new Cryptography().encrypt(getKycSyncdata(context).toString()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private void callShgInactivationApi(@NonNull Context context) {

        JSONObject shgInactivationSyncObject = getShgInactivationSyncData(context);
        if (shgInactivationSyncDataList.size() > 0) {
            AppUtils.getInstance().showLog("shgInactivationSyncObject" + shgInactivationSyncObject, SyncData.class);

            shgInactivationSyncObject = AppUtils.getInstance().wantToEncrypt(shgInactivationSyncObject);
            AppUtils.getInstance().showLog("shgInactivationSyncObject" + shgInactivationSyncObject, SyncData.class);

            AppUtils.getInstance().showLog("SyncShgInactivationDataApi" + AppConstant.SYNC_SHG_INACTIVATION_DETAILS_API, SyncData.class);


            JsonObjectRequest shgInactivationSyncObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.SYNC_SHG_INACTIVATION_DETAILS_API
                    , shgInactivationSyncObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AppUtils.getInstance().showLog("shgInactivationSyncObjectResponse" + response, SyncData.class);
                 /*   try {
                        response=new JSONObject().accumulate("data","E1AwP/OGYudweJbvV07CEs+SC3r7kj6fAFK6niDMwSE=");
                        AppUtils.getInstance().showLog("shgInactiveDemolive" + response, SyncData.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("shgInactivationSyncObjectResponseDecrypt" + response, SyncData.class);
                    try {
                        if (response.getString("status").equalsIgnoreCase("Success")) {
                            updateShgInactivationSyncDataTable();
                            updateShgInactivationBackupFile(context);
                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getShgInactivationApiVolleyError(), AppConstant.SHG_INACTIVATION_API_VOLLEY_ERROR, context);
                            AppUtils.getInstance().showLog("shgInactivationSyncObjectResponseStatus" + response.getString("status"), SyncData.class);
                        }
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("shgInactivationSyncObjectResponseExp" + response, SyncData.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getShgInactivationApiVolleyError(), AppConstant.SHG_INACTIVATION_API_VOLLEY_ERROR, context);
                    AppUtils.getInstance().showLog("shgInactivationSyncObjectResponseStatus" + error.toString(), AddShgMemberActivity.class);
                }
            });

            shgInactivationSyncObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    0,
                    0f));

            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();


            SingletonVolley.getInstance(this.context.getApplicationContext()).addToRequestQueue(shgInactivationSyncObjectRequest);
        }
    }

    public void updateShgInactivationBackupFile(Context context) {
        try {
            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.SHG_INACTIVATION_STATUS_FILE_NAME, new Cryptography().encrypt(getShgInactivationSyncData(context).toString()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private void updateShgInactivationSyncDataTable() {
        List<ShgInactivationSyncData> shgInactivationSyncDataList = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao()
                .queryBuilder().where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ShgInactivationSyncData shgInactivationSyncData : shgInactivationSyncDataList) {
            shgInactivationSyncData.setSyncStatus("1");
            SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().update(shgInactivationSyncData);
        }
    }

    @NonNull
    public JSONObject getShgInactivationSyncData(Context context) {
        JSONObject shgInactivationSyncObject = new JSONObject();

        try {
            shgInactivationSyncObject.accumulate("login_id", getEncryptedData(loginId));
            shgInactivationSyncObject.accumulate("state_short_name", getEncryptedData(shortName));
            shgInactivationSyncObject.accumulate("imei_no", getEncryptedData(imei));
            shgInactivationSyncObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            shgInactivationSyncObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));
            // shgInactivationSyncObject.accumulate("flag",loginId);
            shgInactivationSyncObject.accumulate("shg_inactive_details", getEncryptedData(getShgInactivationDetails().toString()));
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("shgInactivationSyncObject" + e.toString(), SyncData.class);
        }
        return shgInactivationSyncObject;
    }

    @NonNull
    private JSONArray getShgInactivationDetails() {
        JSONArray shgInactivationDetails = new JSONArray();

        shgInactivationSyncDataList = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao()
                .queryBuilder().where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list();
        for (ShgInactivationSyncData shgInactivationSyncData : shgInactivationSyncDataList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("village_code", getEncryptedData(shgInactivationSyncData.getVillageCode()));
                jsonObject.accumulate("shg_code", getEncryptedData(shgInactivationSyncData.getShgCode()));
                jsonObject.accumulate("reason_id", getEncryptedData(shgInactivationSyncData.getReasonId()));
                jsonObject.accumulate("loan_status_id", getEncryptedData(shgInactivationSyncData.getLoanStatusId()));
                jsonObject.accumulate("shg_active", getEncryptedData("N"));

                // jsonObject.accumulate("member_inactive_details",getMemberOfInactiveShg(shgInactivationSyncData.getShgCode()));
                shgInactivationDetails.put(jsonObject);
            } catch (JSONException e) {
                AppUtils.getInstance().showLog("shgSyncObject" + e.toString(), SyncData.class);
            }
        }
        return shgInactivationDetails;
    }

    @NonNull
    private JSONArray getMemberOfInactiveShg(String shgCode) {

        JSONArray jsonArray = new JSONArray();

        List<ShgMemberDetailsData> shgMemberDetailsDataList = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao()
                .queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgCode)).build().list();

        for (ShgMemberDetailsData shgMemberDetailsData : shgMemberDetailsDataList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("member_code", shgMemberDetailsData.getShgMemberCode());
                jsonObject.accumulate("Member_status", "Inactive");
                jsonObject.accumulate("member_reason_id", 1);
                jsonObject.accumulate("member_loan_status_id", "OnShgReq");
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    public void callMemberInactivationApi(@NonNull Context context) {
        JSONObject inactivatedMembers = getInactivJson(context);
        if (shgInActivSyncData.size() > 0) {

            AppUtils.getInstance().showLog("inactivatedMembers" + inactivatedMembers, SyncData.class);
            inactivatedMembers = AppUtils.getInstance().wantToEncrypt(inactivatedMembers);
            AppUtils.getInstance().showLog("inactivatedMembersEncrypted" + inactivatedMembers, SyncData.class);
            AppUtils.getInstance().showLog("SyncMemInacApi" + AppConstant.SYNC_MEMBER_STATUS, SyncData.class);

            JsonObjectRequest addMemberSyncRequest = new JsonObjectRequest(Request.Method.POST,
                    AppConstant.SYNC_MEMBER_STATUS,
                    inactivatedMembers, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AppUtils.getInstance().showLog("modifiedMemberSyncResponse" + response, ShgMemberListAdapter.class);
                    //  {"status":"Success"}
                    response = AppUtils.getInstance().wantToDecrypt(response);
                    AppUtils.getInstance().showLog("responseMemInacDcrpt" + response, SyncData.class);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.toString());
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("Success")) {

                            updateInactiveLocalDb();
                            /**update backup file with active status 0**/
                            try {
                                updateMemberStatusBackUpFile(context);
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getMemberInactivationApiVolleyError(), AppConstant.MEMBER_INACTIVATION_API_VOLLEY_ERROR, context);
                            AppUtils.getInstance().showLog("memberInactivationSyncObjectResponseStatus" + response.getString("status"), AddShgMemberActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getMemberInactivationApiVolleyError(), AppConstant.MEMBER_INACTIVATION_API_VOLLEY_ERROR, context);
                    AppUtils.getInstance().showLog("memberInactivationSyncObjectResponseError" + error.toString(), SyncData.class);
                }
            });

            addMemberSyncRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 0, 0));

            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(context).addToRequestQueue(addMemberSyncRequest);
        }
    }

    @NonNull
    public JSONObject getInactivJson(Context context) {
        JSONObject statusChangObject = new JSONObject();
        try {
            statusChangObject.accumulate("login_id", getEncryptedData(loginId));
            statusChangObject.accumulate("state_short_name", getEncryptedData(shortName));
            statusChangObject.accumulate("imei_no", getEncryptedData(imei));
            statusChangObject.accumulate("device_name", getEncryptedData(deviceInfo));
            String[] location = new LoginActivity().getCoordinateLL(context).split(",");
            statusChangObject.accumulate("location_coordinate", getEncryptedData(location[0] + "," + location[1]));
            statusChangObject.accumulate("Member_status_array", getEncryptedData(getInactivJsonArray(context).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtils.getInstance().showLog("statusChangObject" + statusChangObject, ShgMemberListAdapter.class);
        return statusChangObject;
    }

    private void updateInactiveLocalDb() {
        List<ShgInActivSyncData> shgInActivSyncData = SplashScreen.getInstance().getDaoSession()
                .getShgInActivSyncDataDao().queryBuilder().build().list();
        for (ShgInActivSyncData sInactive : shgInActivSyncData) {
            sInactive.setSyncStatus("1");// changed if sucess response get from server
            SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao().update(sInactive);
        }
        AppUtils.getInstance().showLog("UPDATE_SUCESSFULLY::::", ShgMemberListAdapter.class);
    }

    public void updateMemberStatusBackUpFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.MEMBER_STATUS_FILE_NAME, new Cryptography().encrypt(getInactivJson(context).toString()));
        //  Toast.makeText(context, context.getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();

    }

    @NonNull
    private JSONArray getInactivJsonArray(Context context) {
        JSONArray jsonInactivArray = new JSONArray();
        shgInActivSyncData = SplashScreen.getInstance().getDaoSession()
                .getShgInActivSyncDataDao().queryBuilder().where(ShgInActivSyncDataDao.Properties.SyncStatus
                        .eq("0")).build().list();
        for (ShgInActivSyncData sdd : shgInActivSyncData) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("block_code", getEncryptedData(sdd.getBlockCode()));
                jsonObject.accumulate("gp_code", getEncryptedData(sdd.getGpCode()));
                jsonObject.accumulate("village_code", getEncryptedData(sdd.getVillageCode()));
                jsonObject.accumulate("shg_code", getEncryptedData(sdd.getShgCode()));
                jsonObject.accumulate("reason_id", getEncryptedData(sdd.getSyncInactivReson()));
                jsonObject.accumulate("loan_status", getEncryptedData(sdd.getLoanStatus()));
                jsonObject.accumulate("member_code", getEncryptedData(sdd.getShgMemberCode()));
                jsonObject.accumulate("Member_status", getEncryptedData(sdd.getInactiveStatus()));
             /* for shg  loan_status_id,
                        reason_id*/
              /* for member  loan_status,
                        reason_id*/

                jsonInactivArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AppUtils.getInstance().showLog("jsonInactivArray" + jsonInactivArray, ShgMemberListAdapter.class);
        return jsonInactivArray;
    }

    @NonNull
    public String getMemberLocCordnt(Context context, String postalCode, String textAddress) {
        String coordinates = "";
        String cmpltAddressLine = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(textAddress + " " + postalCode, 20);
            AppUtils.getInstance().showLog("addressesCount=" + addresses.size(), SyncData.class);
            AppUtils.getInstance().showLog("TotalAddresses=" + addresses, SyncData.class);
            if (addresses.size() > 0) {
                for (int i = 0; i < addresses.size(); i++) {
                    Address address = addresses.get(i);
                    AppUtils.getInstance().showLog("address" + address, SyncData.class);
                    if (address.hasLatitude() && address.hasLongitude()) {
                        int maxAddressLineIndex = address.getMaxAddressLineIndex();
                        AppUtils.getInstance().showLog("maxAddressLineIndex=" + maxAddressLineIndex, SyncData.class);
                        for (int j = 0; j <= maxAddressLineIndex; j++) {
                            cmpltAddressLine += address.getAddressLine(j);
                        }
                        AppUtils.getInstance().showLog("cmpltAddressLine=" + cmpltAddressLine, SyncData.class);
                        if (address.getPostalCode().equalsIgnoreCase(postalCode)) {
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();
                            coordinates = latitude + "," + longitude;
                            AppUtils.getInstance().showLog("Coordinatefromaddress" + ",,,,,latitude=" + latitude + "longitude=" + longitude, AadharAccountActivity.class);
                            /*latitude=28.691009400000002,hasLongitude=true,longitude=77.4863956*/
                            /*latitude=28.691009400000002,hasLongitude=true,longitude=77.4863956,*/
                            /*   latitude=28.682432000000002,hasLongitude=true,longitude=77.485821*/
                        } else
                            AppUtils.getInstance().showLog("address line does not matched", SyncData.class);
                    }
                }
            }
        } catch (IOException e) {
            AppUtils.getInstance().showLog("getMemberLocCordntExp" + e, SyncData.class);
        }
        return coordinates;
    }

}
