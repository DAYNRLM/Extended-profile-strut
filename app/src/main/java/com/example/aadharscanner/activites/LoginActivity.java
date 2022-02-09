package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.aadharscanner.R;

import com.example.aadharscanner.database.AadharDetailSyncData;
import com.example.aadharscanner.database.AccountDetailSyncData;;
import com.example.aadharscanner.database.BankBranchDetailData;
import com.example.aadharscanner.database.BankDetailData;
import com.example.aadharscanner.database.BankType;
import com.example.aadharscanner.database.BlockDaoData;
import com.example.aadharscanner.database.ChangeDesignationSyncData;
import com.example.aadharscanner.database.GpsData;
import com.example.aadharscanner.database.InactiveReasons;
import com.example.aadharscanner.database.KycDocData;
import com.example.aadharscanner.database.KycDocSyncData;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.database.MemberInactiveReasonData;
import com.example.aadharscanner.database.MemberLoanStatusData;
import com.example.aadharscanner.database.ShgInActivSyncData;
import com.example.aadharscanner.database.ShgInactivationSyncData;
import com.example.aadharscanner.database.ShgLoanStatusData;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncData;
import com.example.aadharscanner.database.UpdatedDesignation;
import com.example.aadharscanner.database.VillageData;

import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DateFactory;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.GpsTracker;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;
import com.example.aadharscanner.utils.SyncData;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.example.aadharscanner.utils.AppConstant.MAX_LOGIN_ATTEMPTS;

public class LoginActivity extends AppCompatActivity {
    /*Input strings................. */
    private String loginId, password, blockCode;
    private TextView login_attempts_remainTV;
    /*Objects........................*/
    private TextInputEditText loginIdTIET, passwordTIET, mobileInputOtp;
    private TextView forgotPassword;
    public TextView loginBTN, sendOtpButton;
    private ImageView logo_imageView;
    private ProgressDialog progressDialog;
    private Context context;
    @Nullable
    private String Mpin;
    private TelephonyManager telephonyManager;
    private Dialog dialog;
    String locationMaster;
    @NonNull
    public String longitude = "";
    @NonNull
    public String latitude = "";
    @NonNull
    String versionName = "";
    String versioncode = "";
    // Runnable r1;
    // Handler handler;
    @NonNull
    String imei = "", deviceInfo = "";
    private int lastLoginCount;
    private String loginTime;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getLoginFaildCount(), LoginActivity.this);
        context = this;


        loginIdTIET = (TextInputEditText) findViewById(R.id.user_IdTIET);
        passwordTIET = (TextInputEditText) findViewById(R.id.user_PassTIET);
        loginBTN = findViewById(R.id.loginBTN);
        forgotPassword = findViewById(R.id.forgotPasswordTV);
        login_attempts_remainTV = findViewById(R.id.login_attempts_remainTV);
        loginIdTIET.setLongClickable(false);
        passwordTIET.setLongClickable(false);
        // handler = new Handler();

        logo_imageView = (ImageView) findViewById(R.id.logo_imageView);

       /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clock_wise_rotation);*/
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        logo_imageView.startAnimation(animation);

        //get version code and version name imei number and device info
        handleSSLHandshake();
        getAndroidVersion();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imei = getIMEINo1();
            deviceInfo = getDeviceInfo();
            getCoordinator();
        }


        // progressDialog = DialogFactory.getInstance().showProgressDialog(LoginActivity.this, false);
        AppUtils.getInstance().showLog("progressDialog" + progressDialog, LoginActivity.class);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loginTime = DateFactory.getInstance().getDateTime();

                AppUtils.getInstance().showLog("loginTime" + loginTime, LoginActivity.class);
                PreferenceFactory.getInstance().saveSharedPrefrecesData("loginTime", loginTime, context);

                loginId = loginIdTIET.getText().toString().trim().toUpperCase();
                password = passwordTIET.getText().toString().trim();
                if (loginId == null || loginId.equalsIgnoreCase("") || loginId.length() < 4) {
                    loginIdTIET.setError(getString(R.string.error_userId));
                } else if (password.length() < 3) {
                    passwordTIET.setError(getString(R.string.error_password));
                } else {
                    AppUtils.getInstance().showLog("userId=" + loginId + "password=" + password, LoginActivity.class);
                    if (NetworkFactory.isInternetOn(LoginActivity.this)) {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage(getString(R.string.loading_heavy));
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        if (imei.isEmpty() || imei == null) {
                            imei = "";
                            AppUtils.getInstance().showLog("imei" + imei, LoginActivity.class);
                        }
                        if (deviceInfo.isEmpty() || deviceInfo == null) {
                            deviceInfo = "";
                            AppUtils.getInstance().showLog("deviceInfo" + deviceInfo, LoginActivity.class);
                        }
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getIMEI(), imei, context);
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getDeviceInfo(), deviceInfo, context);

                        /*delete complete  database*/
                        new HomeActivity().deleteWholeDatabase();

                        /*populate database from backup files*/
                        populateDBFrombackupFiles();

                        /*sync all data to the server*/
                        SyncData.getInstance().syncData(LoginActivity.this);

                        try {
                            String timeStamp = DateFactory.getInstance().changeDateFormat(DateFactory.getInstance().changeDateValue(DateFactory
                                    .getInstance().getTodayDate()), "dd-MM-yyyy", "yyyy-MM-dd");
                                /*String timeStamp= DateFactory.getInstance().changeDateValue(DateFactory
                                        .getInstance().getTodayDate());*/
                            AppUtils.getInstance().showLog("timeStamp" + timeStamp, LoginActivity.class);

                            getMastersFromServer(loginId,
                                    AppUtils.getInstance().getSha256(password),
                                    getIMEINo1(),/*867130042454246*/
                                    getDeviceInfo(),/*Xiaomi-cactus-Redmi 6A*/
                                    AppConstant.APP_VERSION,
                                    DateFactory.getInstance().changeDateValue(DateFactory
                                            .getInstance().getTodayDate()),
                                    "12",
                                    versionName,
                                    versioncode,
                                    latitude + "," + longitude);

                        } catch (Exception e) {
                            AppUtils.getInstance().showLog("DateParseExcp" + e, LoginActivity.class);
                        }
                    /*    getMastersFromServer("UPAGMANMOHAN",
                                "c6024fd19953c32dc6e2b8fe91684a16a889cc8482157f1ec652616517537239",
                                "862144048163517",
                                "Xiaomi-cactus-Redmi 6A",
                                AppConstant.APP_VERSION,
                                DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()),
                                "12",
                                "O_MR1",
                                "9",
                                latitude + "," + longitude);*/


                        // getBankDataFromServer();
                    } else {
                        DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this,
                                getString(R.string.NO_INTERNET_TITLE),
                                getString(R.string.INTERNET_MESSAGE),
                                "OK");
                    }

                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = DialogFactory.getInstance().showCustomDialog(LoginActivity.this, R.layout.forgot_passdialog);
                dialog.show();

                mobileInputOtp = (TextInputEditText) dialog.findViewById(R.id.mobileNoET);
                sendOtpButton = (Button) dialog.findViewById(R.id.send_otpBTN);

                sendOtpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mobileNo = mobileInputOtp.getText().toString().trim();
                        if (mobileNo.length() < 10) {
                            mobileInputOtp.setError(getResources().getString(R.string.enter_valid_mobile_number));
                            sendOtp(mobileNo);

                        } else {
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyMobileNumber(), mobileNo, LoginActivity.this);
                            sendOtp(mobileNo);

                        }
                    }
                });

            }
        });

    }

    @NonNull
    public String getRandomOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(8999);
        //  Toast.makeText(LoginActivity.this, "OTP is =" + otp, Toast.LENGTH_LONG);
        AppUtils.getInstance().showLog("Otp is" + otp, LoginActivity.class);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyOtp(), String.valueOf(otp), LoginActivity.this);

        return "" + otp;

    }

    private void sendOtp(String mobileNo) {

        if (!NetworkFactory.isInternetOn(LoginActivity.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getResources().getString(R.string.no_internet_connection), getResources().getString(R.string.please_open_your_internet_connection), "OK");
        } else {


            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.accumulate("mobileno",mobileNo);
                jsonObject.accumulate("message",getRandomOtp() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }


      /*      String mobile=data.getString("mobileno");
            String messages=data.getString("message");*/

            /*String url = AppConstant.SENT_OTP + mobileNo + "&message=" + "" + "" + getRandomOtp() + " " + getString(R.string.otp_massage);
            */
            AppUtils.getInstance().showLog("OTPRequest="+jsonObject,LoginActivity.class);
            AppUtils.getInstance().showLog("URL_SENT_OTPRequest="+AppConstant.FORGET_PASSWORD,LoginActivity.class);
            ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(LoginActivity.this, false);
            progressDialog.show();


            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, AppConstant.SENT_OTP, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    AppUtils.getInstance().showLog("OTPResponse="+response,LoginActivity.class);
                    try {
                        if (response.getString("status").equalsIgnoreCase("Success")) {
                            AppUtils.getInstance().showLog("mobileresponse" + response, LoginActivity.class);
                            AppUtils.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);
                        }else DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE)
                                , response.getString("meassage"), "ok");

                    } catch (Exception e) {
                        AppUtils.getInstance().showLog("ExceptioninResponse" + e.toString(), LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    if (AppConstant.SERVER_TYPE.equalsIgnoreCase("demo")){
                        AppUtils.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);
                    }else {
                        DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE)
                                , getString(R.string.SERVER_ERROR_MESSAGE), "ok");
                        //       AppUtils.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true); //temprarory added by manish

                        AppUtils.getInstance().showLog("volleyErrorOTPService" + error, LoginActivity.class);
                    }

                }
            });

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.getCache().clear();
                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.getInstance().showLog("Volley Exception:" + e, LoginActivity.class);
            }
/*
            StringRequest requestOtp = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    dialog.dismiss();

                    try {
                        AppUtils.getInstance().showLog("mobileresponse" + response, LoginActivity.class);
                        AppUtils.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true);

                    } catch (Exception e) {
                        AppUtils.getInstance().showLog("Exception" + e.toString(), LoginActivity.class);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this, getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "ok");
                    //       AppUtils.getInstance().makeIntent(LoginActivity.this, OTPVerification.class, true); //temprarory added by manish

                    AppUtils.getInstance().showLog("volleyErrorOTPService" + error, LoginActivity.class);

                }
            });
*//*22 07 1443*//*
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.getCache().clear();
                requestQueue.add(requestOtp);
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.getInstance().showLog("Volley Exception:" + e, LoginActivity.class);
            }*/
        }

    }

    @NonNull
    public String getDeviceInfo() {
        String deviceInfo = "";

        try {
            deviceInfo = Build.MANUFACTURER + "-" + Build.DEVICE + "-" + Build.MODEL;
        } catch (Exception e) {
            AppUtils.getInstance().showLog("ExceptionMain" + e, LoginActivity.class);
        }

        AppUtils.getInstance().showLog("deviceInfo=" + deviceInfo, LoginActivity.class);

        if (deviceInfo.equalsIgnoreCase("") || deviceInfo == null)
            return "";
        return deviceInfo;
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String getIMEINo1() {
        String imeiNo1 = "";
        try {
            if (getSIMSlotCount() > 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imeiNo1 = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    AppUtils.getInstance().showLog("imeiNo1" + imeiNo1, LoginActivity.class);

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imeiNo1 = telephonyManager.getDeviceId(0);

                }
                AppUtils.getInstance().showLog("imeiNo1=" + imeiNo1, LoginActivity.class);


            } else imeiNo1 = telephonyManager.getDeviceId();
        } catch (Exception e) {
            // show dialog
            AppUtils.getInstance().showLog("IMEIExcp" + e, LoginActivity.class);
        }
        return imeiNo1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getSIMSlotCount() {
        int getPhoneCount = 0;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPhoneCount = telephonyManager.getPhoneCount();
        }
        return getPhoneCount;
    }


    public void getAndroidVersion() {
        versioncode = Build.VERSION.RELEASE;
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            versionName = field.getName();
            int fieldValue = -1;
            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                AppUtils.getInstance().showLog("versionName:" + versionName, LoginActivity.class);
                AppUtils.getInstance().showLog("versioRELESED:" + versioncode, LoginActivity.class);
                int Api_version = Build.VERSION.SDK_INT;
                AppUtils.getInstance().showLog("API_VERSION:" + Api_version, LoginActivity.class);
            }
        }
    }

    public void getMastersFromServer(String userId,
                                     String encodedPassword,
                                     String imeiNo,
                                     String deviceInfo,
                                     String appVersion,
                                     String todayDate,
                                     String androidapiversion,
                                     String androidDeviceVersion,
                                     String androidVersionCode,
                                     String latLong) {

        if (!NetworkFactory.isInternetOn(LoginActivity.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this,
                    getString(R.string.NO_INTERNET_TITLE),
                    getString(R.string.INTERNET_MESSAGE),
                    "OK");
        } else {
            String logoutTime = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getLogoutDateTime(), LoginActivity.this);
            if (logoutTime.contentEquals("")) {
                logoutTime = DateFactory.getInstance().getDateTime();
                /*if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpinFilePath(), AppConstant.MPIN_FILE_NAME)) {
                    logoutTime = "NFI";
                } else {
                    logoutTime = "FI";
                }*/
                AppUtils.getInstance().showLog("logoutTime" + logoutTime, LoginActivity.class);
            }
            AppUtils.getInstance().showLog("logoutTime" + logoutTime, LoginActivity.class);
            /**************json request****************************/
            JSONObject masterUrlObject = new JSONObject();
            try {
                AppUtils.getInstance().showLog("encoded_****password" + encodedPassword, LoginActivity.class);


                masterUrlObject.accumulate("login_id", userId);
                masterUrlObject.accumulate("userName", userId);
                masterUrlObject.accumulate("password", encodedPassword);
                masterUrlObject.accumulate("imei_no", imeiNo);
                masterUrlObject.accumulate("device_name", deviceInfo);
                masterUrlObject.accumulate("app_versions", appVersion);
                masterUrlObject.accumulate("date", todayDate);
                masterUrlObject.accumulate("android_version", androidVersionCode);
                masterUrlObject.accumulate("android_api_version", androidapiversion);
                masterUrlObject.accumulate("location_coordinate", latLong);
                masterUrlObject.accumulate("logout_time", logoutTime);
                masterUrlObject.accumulate("app_login_time", loginTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppUtils.getInstance().showLog("requestObject" + masterUrlObject, LoginActivity.class);
            JSONObject requestObject = AppUtils.getInstance().wantToEncryptOldKey(masterUrlObject);

            AppUtils.getInstance().showLog("requestObjectEncrpted" + requestObject, LoginActivity.class);
            AppUtils.getInstance().showLog("LoginApiRequest" + AppConstant.LOCATION_MASTER, LoginActivity.class);

            //  getBankDataFromServer();

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.LOCATION_MASTER, requestObject, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull JSONObject response) {
                    try {
                        String status = "";
                        AppUtils.getInstance().showLog("response" + response, LoginActivity.class);
                        JSONObject jsonObject = null;
                        jsonObject = AppUtils.getInstance().wantToDecryptOldKey(response);
                        AppUtils.getInstance().showLog("jsonObjectDecrptedLogin" + jsonObject, LoginActivity.class);
                        //Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();



                      /*  String objectResponse = "";

                        // jsonObject = new JSONObject(response);
                        if (response.has("data")) {
                            objectResponse = response.getString("data");
                        } else {
                            jsonObject = response;
                        }
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            try {
                                Cryptography cryptography = new Cryptography();
                                jsonObject = new JSONObject(cryptography.decrypt(objectResponse));
                                AppUtils.getInstance().showLog("responseJSON" + jsonObject.toString(), LoginActivity.class);
                            } catch (Exception e) {
                                AppUtils.getInstance().showLog("DecryptEx" + e, LoginActivity.class);
                            }
                        }*/


                        if (jsonObject.has("status")) {
                            /*" Please wait for 15 minutes you exceed limit more than 5 !!!"*/
                            progressDialog.dismiss();
                            status = jsonObject.getString("status");

                            if (jsonObject.has("login_attempt")) {
                                int loginAttempsCount = jsonObject.getInt("login_attempt");
                                if ((loginAttempsCount < MAX_LOGIN_ATTEMPTS) && (loginAttempsCount > 0)) {
                                    login_attempts_remainTV.setText(getString(R.string.login_attempts_msgS) + " " + String.valueOf(MAX_LOGIN_ATTEMPTS - loginAttempsCount) + " " + getString(R.string.login_attempts_msgE));
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                } else if (status.equalsIgnoreCase(" Please wait for 15 minutes you exceed limit more than 5 !!!")) {
                                    login_attempts_remainTV.setText(getString(R.string.error_cridential_block));
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                } else if (loginAttempsCount >= MAX_LOGIN_ATTEMPTS && loginAttempsCount % 2 != 0) {
                                    login_attempts_remainTV.setText(getString(R.string.login_attempts_msgS) + " " + String.valueOf(1) + " " + getString(R.string.login_attempts_msgE));
                                    login_attempts_remainTV.setVisibility(View.VISIBLE);
                                }
                            }

                            if (status.equalsIgnoreCase(" Please wait for 15 minutes you exceed limit more than 5 !!!")) {
                                progressDialog.dismiss();
                                Dialog dialog = new Dialog(LoginActivity.this);
                                dialog.setContentView(R.layout.login_failed_error_layout);
                                Button ok = (Button) dialog.findViewById(R.id.buttonOk);
                                TextView msgTv = (TextView) dialog.findViewById(R.id.msgTv);
                                dialog.show();
                                dialog.setCancelable(false);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                if (status.equalsIgnoreCase("Invalid Password!!!") || status.equalsIgnoreCase("Invalid UserID !!!")) {
                                    DialogFactory.getInstance().showAlertDialog(context, 1, getString(R.string.info)
                                            , getString(R.string.server_error_invalid_userId_and_password), getString(R.string.ok)
                                            , new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }, null, null, false);

                                } else if (status.equalsIgnoreCase("Invalid APP Version !!!")) {
                                    DialogFactory.getInstance().showAlertDialog(context, 1, getString(R.string.server_error__AppVersion)
                                            , getString(R.string.server_error_invalid_AppVersion), getString(R.string.update)
                                            , new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    updateApplication();
                                                }
                                            }, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }, false);
                                } else {
                                    DialogFactory.getInstance().showAlertDialog(context, 1, getString(R.string.info), status, getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);
                                }
                            }

                        } else if (jsonObject.has("device_name")) {
                            progressDialog.dismiss();
                            DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_device_info),
                                    getString(R.string.server_error_device_info_massege) + " (" + jsonObject.getString("device_name") + ") " + ".",
                                    "OK", null, null, false, false);
                        } else if (status.equalsIgnoreCase("Invalid UserID !!!")) {
                            status = getString(R.string.logedin_already);
                            DialogFactory.getInstance().showAlertDialog(context, 1, getString(R.string.info), status, getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, true);
                        } else {

                            parseLocationMaster(jsonObject.toString());
                        }
                    } catch (JSONException jsonException) {
                        progressDialog.dismiss();
                        AppUtils.getInstance().showLog("jsonException" + jsonException, LoginActivity.class);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    progressDialog.dismiss();
                    AppUtils.getInstance().showLog("LoginActivityVolleyError" + error.toString(), LoginActivity.class);
                    DialogFactory.getInstance().showAlertDialog(LoginActivity.this, 1, getString(R.string.info), getString(R.string.server_error_msg)
                            , getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, false
                    );
                }
            });
            loginRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 60000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 1;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                    AppUtils.getInstance().showLog("login error" + error, LoginActivity.class);

                }
            });
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);


            /*******************this is for a string request***********/
            String MASTER_URL = AppConstant.LOCATION_MASTER + "userName="
                    + userId + "&password="
                    + encodedPassword + "&imei_no="
                    + imeiNo + "&device_name="
                    + deviceInfo + "&app_versions="
                    + appVersion + "&date="
                    + todayDate + "&android_version="
                    + androidDeviceVersion + "&android_api_version="
                    + androidapiversion + "&androidVersionCode="
                    + androidVersionCode + "&location_coordinate="
                    + latLong;

            AppUtils.getInstance().showLog("Login_URL:::::" + MASTER_URL, LoginActivity.class);
         /*  StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, MASTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        AppUtils.getInstance().showLog("response" + response, LoginActivity.class);
                        JSONObject jsonObject = null;
                        jsonObject = new JSONObject(response);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            try {
                                // Cryptography cryptography = new Cryptography();
                                // jsonObject = new JSONObject(cryptography.decrypt(response));
                                AppUtils.getInstance().showLog("responseJSON" + jsonObject.toString(), LoginActivity.class);
                            } catch (Exception e) {
                                AppUtils.getInstance().showLog("DecryptEx" + e, LoginActivity.class);
                            }
                        }
                        if (jsonObject.has("status")) {
                            progressDialog.dismiss();
                            String status = jsonObject.getString("status");
                            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                           *//* if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_userId))) {
                                DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_userId), getString(R.string.server_error_invalid_userId_massege), "OK", null, null, true, false);
                            } else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_password))) {
                                DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_password), getString(R.string.server_error_invalid_password_massege), "OK", null, null, true, false);
                            } else if (status.equalsIgnoreCase(getString(R.string.server_error_invalid_version))) {
                                DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_version), getString(R.string.server_error_invalid_version_massege), "OK", "Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //write code for app update
                                        dialog.dismiss();
                                    }
                                }, false, true);
                            } else if (status.equalsIgnoreCase("Invalid Date !!!")) {
                                DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_invalid_date), getString(R.string.server_error_invalid_date_massege), "OK", null, null, true, false);
                            } else {
                                DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_device_info), getString(R.string.server_error_device_info_massege) + " (" + status + ") " + getString(R.string.device), "OK", null, null, false, false);
                            }*//*
                        } else if (jsonObject.has("device_name")) {
                            progressDialog.dismiss();
                            DialogFactory.getInstance().showServerCridentialDialog(LoginActivity.this, getString(R.string.server_error_device_info), getString(R.string.server_error_device_info_massege) + " (" + jsonObject.getString("device_name") + ") " + ".", "OK", null, null, false, false);
                        } else {
                            parseLocationMaster(jsonObject.toString());
                        }
                    } catch (JSONException jsonException) {
                        AppUtils.getInstance().showLog("jsonException" + jsonException, LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AppUtils.getInstance().showLog("VolleyErrorjsonException" + error, LoginActivity.class);
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this,
                            getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "OK");

                  *//*  if (error.equals(new VolleyError("com.android.volley.TimeoutError"))) {
                        DialogFactory.getInstance().showAlertDialog(LoginActivity.this, 0, AppConstant.BAD_NETWORK, getString(R.string.INTERNET_BAD), "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, true);
                    }*//*
                }
            });
            //HttpsURLConnection.setDefaultSSLSocketFactory();
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 30000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 1;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                    AppUtils.getInstance().showLog("login error" + error, LoginActivity.class);

                }
            });
            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);*/


        }
    }

    private void updateApplication() {
        final String appPackageName = context.getPackageName();
        String marketStores = "market://details?id=" + appPackageName;
        String nrlmLiveLocUri = "https://nrlm.gov.in/outerReportAction.do?methodName=showIndex#pop";

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketStores)));
        } catch (android.content.ActivityNotFoundException anfe) {
            //((Activity) context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(nrlmLiveLocUri)));
        }
        //((Activity) context).finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        //handler.removeCallbacks(r1);
        GpsTracker gpsTracker = new GpsTracker(LoginActivity.this);
        if (!AppUtils.isGPSEnabled(LoginActivity.this)) {
            DialogFactory.getInstance().showAlertDialog(LoginActivity.this,
                    R.drawable.ic_launcher_background, getString(R.string.app_name),
                    getString(R.string.gpsMsg),
                    getString(R.string.gotoSetting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }, "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
        } else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);
        }
    }

    public void getCoordinator() {
        GpsTracker gpsTracker = new GpsTracker(LoginActivity.this);
        if (!AppUtils.isGPSEnabled(LoginActivity.this)) {
            DialogFactory.getInstance().showAlertDialog(LoginActivity.this,
                    R.drawable.ic_launcher_background, getString(R.string.app_name),
                    getString(R.string.gpsMsg),
                    getString(R.string.gotoSetting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }, "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
        } else {
            gpsTracker.getLocation();
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void parseLocationMaster(@NonNull String locationMaster) throws JSONException {
        LoginInfoData loginInfoData = new LoginInfoData();
        BlockDaoData blockDaoData = new BlockDaoData();
        GpsData gpsData = new GpsData();
        VillageData villageData = new VillageData();
        InactiveReasons inactiveReasonsE = new InactiveReasons();
        KycDocData kycDocData = new KycDocData();

        JSONObject locationMasterObject = new JSONObject(locationMaster);
        String state_short_name = "", pvtgStatus = "";

        AppUtils.getInstance().showLog("locationMasterObject" + locationMasterObject, LoginActivity.class);
        SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().deleteAll();

        /* *//**add login detail in database**/
        if (locationMasterObject.has("state_short_name") & locationMasterObject.has("state_code")) {
            loginId = locationMasterObject.getString("login_id");
            // String app_version = locationMasterObject.getString("app_version");
            String language_id = String.valueOf(locationMasterObject.getInt("language_id"));
            String logout_days = locationMasterObject.getString("logout_days");
            String user_password = locationMasterObject.getString("password");
            String server_date_time = locationMasterObject.getString("server_date_time");
            state_short_name = locationMasterObject.getString("state_short_name");
            String state_code = locationMasterObject.getString("state_code");
            String mobile_number = locationMasterObject.getString("mobile_number");
            if (locationMasterObject.has("pvtg_status")) {
                pvtgStatus = locationMasterObject.getString("pvtg_status");
            } else {
                pvtgStatus = "N";
            }

            if (pvtgStatus != null && !pvtgStatus.isEmpty()) {
                loginInfoData.setPvgtStatus(pvtgStatus);
            } else loginInfoData.setPvgtStatus(AppConstant.NOT_AVAILABLE);

            if (loginId != null && !loginId.isEmpty()) {
                loginInfoData.setLoginId(loginId);
            } else loginInfoData.setLoginId(AppConstant.NOT_AVAILABLE);

            if (mobile_number != null && !mobile_number.isEmpty()) {
                loginInfoData.setMobileNumber(mobile_number);
            } else loginInfoData.setMobileNumber(AppConstant.NOT_AVAILABLE);

            if (language_id != null && !language_id.isEmpty()) {
                loginInfoData.setLanguageId(language_id);
            } else loginInfoData.setLanguageId(AppConstant.NOT_AVAILABLE);

            if (logout_days != null && !logout_days.isEmpty()) {
                loginInfoData.setLogOutDays(logout_days);
            } else loginInfoData.setLogOutDays(AppConstant.NOT_AVAILABLE);

            if (user_password != null && !user_password.isEmpty()) {
                loginInfoData.setPassword(user_password);
            } else loginInfoData.setPassword(AppConstant.NOT_AVAILABLE);

            if (server_date_time != null && !server_date_time.isEmpty()) {
                loginInfoData.setServerDateTime(server_date_time);
            } else loginInfoData.setServerDateTime(AppConstant.NOT_AVAILABLE);

            if (state_short_name != null && !state_short_name.isEmpty()) {
                loginInfoData.setState_short_name(state_short_name);
            } else loginInfoData.setState_short_name(AppConstant.NOT_AVAILABLE);

            if (state_code != null && !state_code.isEmpty()) {
                loginInfoData.setState_code(state_code);
            } else loginInfoData.setState_code(AppConstant.NOT_AVAILABLE);

            SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().insert(loginInfoData);
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.block_not_assigned), Toast.LENGTH_LONG).show();
        }



        /*"Loanstatus":[{"SHG":["No Outstanding","Outstanding from bank","Outstanding from feedration"],"Member":["No Outstanding","Having loans from SHG"]}]*/


        JSONArray loanStatusArray = locationMasterObject.getJSONArray("Loanstatus");
        for (int q = 0; q < loanStatusArray.length(); q++) {
            JSONObject loanStatus = (JSONObject) loanStatusArray.get(q);
            if (loanStatus.has("SHG")) {

                JSONArray shgArray = loanStatus.getJSONArray("SHG");
                for (int sh = 0; sh < shgArray.length(); sh++) {
                    JSONObject shsLoanStatus = (JSONObject) shgArray.getJSONObject(sh);
                    ShgLoanStatusData shgLoanStatusData = new ShgLoanStatusData();
                    shgLoanStatusData.setLoanStatus(shsLoanStatus.getString("loan_name"));
                    shgLoanStatusData.setLoanStatusId(String.valueOf(shsLoanStatus.getInt("loan_type_id")));
                    SplashScreen.getInstance().getDaoSession().getShgLoanStatusDataDao().insert(shgLoanStatusData);
                }
            } else {
                ShgLoanStatusData shgLoanStatusData = new ShgLoanStatusData();
                shgLoanStatusData.setLoanStatus(AppConstant.NOT_AVAILABLE);
                shgLoanStatusData.setLoanStatusId(AppConstant.NOT_AVAILABLE);
                SplashScreen.getInstance().getDaoSession().getShgLoanStatusDataDao().insert(shgLoanStatusData);
            }

            if (loanStatus.has("Member")) {

                JSONArray memberArray = loanStatus.getJSONArray("Member");
                for (int mm = 0; mm < memberArray.length(); mm++) {
                    JSONObject memberLoanStatus = (JSONObject) memberArray.getJSONObject(mm);
                    MemberLoanStatusData memberLoanStatusData = new MemberLoanStatusData();
                    memberLoanStatusData.setLoanStatus(memberLoanStatus.getString("loan_name"));
                    memberLoanStatusData.setLoanStatusId(String.valueOf(memberLoanStatus.getInt("loan_type_id")));
                    SplashScreen.getInstance().getDaoSession().getMemberLoanStatusDataDao().insert(memberLoanStatusData);
                }
            } else {
                MemberLoanStatusData memberLoanStatusData = new MemberLoanStatusData();
                memberLoanStatusData.setLoanStatus(AppConstant.NOT_AVAILABLE);
                memberLoanStatusData.setLoanStatusId(AppConstant.NOT_AVAILABLE);
                SplashScreen.getInstance().getDaoSession().getMemberLoanStatusDataDao().insert(memberLoanStatusData);
            }
        }

        /**.......................Member inactive reasons data..............................*/

        JSONArray inactiveReasons = locationMasterObject.getJSONArray("list_inactive");
        for (int l = 0; l < inactiveReasons.length(); l++) {

            JSONObject inactiveReason = inactiveReasons.getJSONObject(l);
            if (inactiveReason.has("MemberReason")) {
                JSONArray memberInactiveReasons = inactiveReason.getJSONArray("MemberReason");
                for (int i = 0; i < memberInactiveReasons.length(); i++) {
                    JSONObject memberReason = memberInactiveReasons.getJSONObject(i);
                    MemberInactiveReasonData memberInactiveReasonData = new MemberInactiveReasonData();
                    memberInactiveReasonData.setReasonId(String.valueOf(memberReason.getInt("reason_id")));
                    memberInactiveReasonData.setReason(memberReason.getString("reason"));
                    SplashScreen.getInstance().getDaoSession().getMemberInactiveReasonDataDao().insert(memberInactiveReasonData);
                }
            } else {
                MemberInactiveReasonData memberInactiveReasonData = new MemberInactiveReasonData();
                memberInactiveReasonData.setReasonId(AppConstant.NOT_AVAILABLE);
                memberInactiveReasonData.setReason(AppConstant.NOT_AVAILABLE);
                SplashScreen.getInstance().getDaoSession().getMemberInactiveReasonDataDao().insert(memberInactiveReasonData);
            }

            if (inactiveReason.has("SHGReason")) {
                JSONArray memberInactiveReasons = inactiveReason.getJSONArray("SHGReason");
                for (int i = 0; i < memberInactiveReasons.length(); i++) {
                    JSONObject memberReason = memberInactiveReasons.getJSONObject(i);
                    InactiveReasons memberInactiveReasonData = new InactiveReasons();
                    memberInactiveReasonData.setReasonId(String.valueOf(memberReason.getInt("reason_id")));
                    memberInactiveReasonData.setReason(memberReason.getString("reason"));
                    SplashScreen.getInstance().getDaoSession().getInactiveReasonsDao().insert(memberInactiveReasonData);
                }
            } else {
                InactiveReasons memberInactiveReasonData = new InactiveReasons();
                memberInactiveReasonData.setReasonId(AppConstant.NOT_AVAILABLE);
                memberInactiveReasonData.setReason(AppConstant.NOT_AVAILABLE);
                SplashScreen.getInstance().getDaoSession().getInactiveReasonsDao().insert(memberInactiveReasonData);
            }

        }

        if (locationMasterObject.has("kyc_data")) {
            JSONArray kycDocArray = locationMasterObject.getJSONArray("kyc_data");
            /*{"doc_no_length":10,"doc_name":"Pan Card","doc_type":1,"doc_id":2,"doc_input_type":"alphanumeric"}*/
            for (int m = 0; m < kycDocArray.length(); m++) {
                JSONObject kycDocObject = kycDocArray.getJSONObject(m);
                if(kycDocObject.has("status") && m==0){
                    kycDocData.setDocId(AppConstant.NOT_AVAILABLE);
                    kycDocData.setDocName(AppConstant.NOT_AVAILABLE);
                    kycDocData.setDocType(AppConstant.NOT_AVAILABLE);
                    kycDocData.setDocNolength(AppConstant.NOT_AVAILABLE);
                    kycDocData.setDocInputType(AppConstant.NOT_AVAILABLE);
                    SplashScreen.getInstance().getDaoSession().getKycDocDataDao().insert(kycDocData);
                }else {
                    String doc_id = String.valueOf(kycDocObject.getInt("doc_id"));
                    String doc_name = kycDocObject.getString("doc_name");
                    String doc_type = String.valueOf(kycDocObject.getInt("doc_type"));
                    String doc_no_length = String.valueOf(kycDocObject.getInt("doc_no_length"));
                    String doc_input_type = kycDocObject.getString("doc_input_type");

                    if (doc_id != null && !doc_id.isEmpty()) {
                        kycDocData.setDocId(doc_id);
                    } else kycDocData.setDocId(AppConstant.NOT_AVAILABLE);

                    if (doc_name != null && !doc_name.isEmpty()) {
                        kycDocData.setDocName(doc_name);
                    } else kycDocData.setDocName(AppConstant.NOT_AVAILABLE);

                    if (doc_type != null && !doc_type.isEmpty()) {
                        kycDocData.setDocType(doc_type);
                    } else kycDocData.setDocType(AppConstant.NOT_AVAILABLE);

                    if (doc_no_length != null && !doc_no_length.isEmpty()) {
                        kycDocData.setDocNolength(doc_no_length);
                    } else kycDocData.setDocNolength(AppConstant.NOT_AVAILABLE);

                    if (doc_input_type != null && !doc_input_type.isEmpty()) {
                        kycDocData.setDocInputType(doc_input_type);
                    } else kycDocData.setDocInputType(AppConstant.NOT_AVAILABLE);

                    SplashScreen.getInstance().getDaoSession().getKycDocDataDao().insert(kycDocData);
                }


            }
        } else {
            kycDocData.setDocId(AppConstant.NOT_AVAILABLE);
            kycDocData.setDocName(AppConstant.NOT_AVAILABLE);
            kycDocData.setDocType(AppConstant.NOT_AVAILABLE);
            kycDocData.setDocNolength(AppConstant.NOT_AVAILABLE);
            kycDocData.setDocInputType(AppConstant.NOT_AVAILABLE);
            SplashScreen.getInstance().getDaoSession().getKycDocDataDao().insert(kycDocData);
        }

        /**Start filling blockasign data in database**/
        JSONArray assinedBlocks = locationMasterObject.getJSONArray("block_assign");
        for (int i = 0; i < assinedBlocks.length(); i++) {
            JSONObject block = assinedBlocks.getJSONObject(i);
            if (!block.has("status")) {
                blockCode = block.getString("block_code");
                String blockName = block.getString("block_name");

                if (blockCode != null && !blockCode.isEmpty()) {
                    blockDaoData.setBlockCode(blockCode);
                } else blockDaoData.setBlockCode(AppConstant.NOT_AVAILABLE);

                if (blockName != null && !blockName.isEmpty())
                    blockDaoData.setBlockname(blockName);
                else blockDaoData.setBlockname(AppConstant.NOT_AVAILABLE);

                SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().insert(blockDaoData);
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefBlockName(), blockName, LoginActivity.this);

                /**Start filling GP data in database**/
                JSONArray assinedGps = block.getJSONArray("gp_assign");
                for (int j = 0; j < assinedGps.length(); j++) {
                    JSONObject gP = assinedGps.getJSONObject(j);
                    if (!gP.has("status")) {
                        String gpCode = gP.getString("gp_code");
                        String gpName = gP.getString("gp_name");
                        gpsData.setBlockCode(blockCode);
                        if (gpCode != null && !gpCode.isEmpty())
                            gpsData.setGpCode(gpCode);
                        else gpsData.setGpCode(AppConstant.NOT_AVAILABLE);

                        if (gpName != null && !gpName.isEmpty())
                            gpsData.setGpName(gpName);
                        else gpsData.setGpName(AppConstant.NOT_AVAILABLE);
                        SplashScreen.getInstance().getDaoSession().getGpsDataDao().insert(gpsData);

                        /**Start filling Village data in database**/
                        JSONArray assinedVillages = gP.getJSONArray("village_assign");
                        for (int k = 0; k < assinedVillages.length(); k++) {
                            JSONObject village = assinedVillages.getJSONObject(k);
                            if (!village.has("status")) {
                                String villageCode = village.getString("village_code");
                                String villageName = village.getString("village_name");
                                villageData.setBlockCode(blockCode);
                                villageData.setGpCode(gpCode);
                                if (villageCode != null && !villageCode.isEmpty())
                                    villageData.setVillageCode(villageCode);
                                else villageData.setVillageCode(AppConstant.NOT_AVAILABLE);

                                if (villageName != null && !villageName.isEmpty())
                                    villageData.setVillageName(villageName);
                                else villageData.setVillageName(AppConstant.NOT_AVAILABLE);
                                SplashScreen.getInstance().getDaoSession().getVillageDataDao().insert(villageData);
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.toast_vill_not_assigned), Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.toast_gp_not_assigned), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(LoginActivity.this, getString(R.string.toast_block_not_assigned), Toast.LENGTH_LONG).show();
            }
        }

        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLoginId(), loginId, LoginActivity.this);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLoginPassword(), password, context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyStateShortName(), state_short_name, context);
        //state_short_name

        getBankDataFromServer();

    /*    CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppConstant.BANK_MASTER, bankBranchMasterDataRequestObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    *//*JSONObject jsonObject = null;
                    jsonObject = new JSONObject(response);*//*
                   AppUtils.getInstance().showLog("bankBranchMasterDataRequestResponse"+response,LoginActivity.class);
                    parseBankBranchData(response.toString());
                }catch (JSONException jsonException){
                    AppUtils.getInstance().showLog("jsonExceptionInBankBranchData:::" + jsonException, LoginActivity.class);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AppUtils.getInstance().showLog("bankBranchMasterDataRequestError" + error, LoginActivity.class);
                DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this,
                        getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "OK");

            }
        });*/



       /* JsonObjectRequest bankBranchMasterDataRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.BANK_MASTER, bankBranchMasterDataRequestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(response.toString());

                    if (element.isJsonArray()) {
                        JsonArray array = element.getAsJsonArray();
                        parseBankBranchData(array.toString());
                    } else if (element.isJsonObject()) {
                        JsonObject object = element.getAsJsonObject();
                        //read response here
                    }
                  *//*  JSONObject jsonObject = null;
                    jsonObject = new JSONObject(response);
                    parseBankBranchData(response.toString());*//*
                }catch (JSONException jsonException){
                    AppUtils.getInstance().showLog("jsonExceptionInBankBranchData:::" + jsonException, LoginActivity.class);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AppUtils.getInstance().showLog("bankBranchMasterDataRequestError" + error, LoginActivity.class);
                DialogFactory.getInstance().showErrorAlertDialog(LoginActivity.this,
                        getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "OK");
            }
        });
        bankBranchMasterDataRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

                AppUtils.getInstance().showLog("login error in bank branch:::" + error, LoginActivity.class);

            }
        });
        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(bankBranchMasterDataRequest);*/

    }

    private void getBankDataFromServer() {
        JSONObject bankBranchMasterDataRequestObject = new JSONObject();
        try {
            bankBranchMasterDataRequestObject.accumulate("login_id", loginId);
            bankBranchMasterDataRequestObject.accumulate("block_code", blockCode);
            bankBranchMasterDataRequestObject.accumulate("imei_no", imei);
            bankBranchMasterDataRequestObject.accumulate("app_login_time", loginTime);
            bankBranchMasterDataRequestObject.accumulate("device_name", getDeviceInfo());
            bankBranchMasterDataRequestObject.accumulate("location_coordinate", latitude + "," + longitude);

            AppUtils.getInstance().showLog("BankbarchmasterRequest" + AppConstant.BANK_MASTER + "data" + bankBranchMasterDataRequestObject.toString(), LoginActivity.class);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(bankBranchMasterDataRequestObject);
            AppUtils.getInstance().showLog("bankDataRequest" + jsonArray, LoginActivity.class);

            JSONObject bankDataRequest = AppUtils.getInstance().wantToEncryptOldKey(jsonArray);
            AppUtils.getInstance().showLog("bankDataRequestEncrypted" + bankDataRequest, LoginActivity.class);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.BANK_MASTER, bankDataRequest
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(@NonNull JSONObject response) {
                    try {
                    /*JSONObject jsonObject = null;
                    jsonObject = new JSONObject(response);*/
                        AppUtils.getInstance().showLog("bankBranchMasterDataRequestResponse" + response, LoginActivity.class);
                        parseBankBranchData(response);
                    } catch (Exception jsonException) {
                        AppUtils.getInstance().showLog("jsonExceptionInBankBranchData:::" + jsonException, LoginActivity.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AppUtils.getInstance().showLog("bankBranchMasterDataRequestError" + error, LoginActivity.class);
                    DialogFactory.getInstance().showAlertDialog(LoginActivity.this, 1, getString(R.string.info), getString(R.string.server_error_msg)
                            , getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, false
                    );

                }
            });


            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                    0
                    , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonVolley.getInstance(context.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            AppUtils.getInstance().showLog("bankdatarequestJsonExp" + e.toString(), LoginActivity.class);
        }
    }

    private void parseBankBranchData(@NonNull JSONObject bankBranches) throws JSONException {
        BankType bankType = new BankType();
        BankDetailData bankDetailData = new BankDetailData();
        BankBranchDetailData bankBranchDetailData = new BankBranchDetailData();

        try {
            progressDialog.dismiss();
            String bankBranchEncrpted = new Cryptography().decrypt(bankBranches.getString("data"));
            AppUtils.getInstance().showLog("bankBranchDeceyptedResponse",LoginActivity.class);
            JSONArray bankBranch = new JSONArray(bankBranchEncrpted);
            for (int i = 0; i < bankBranch.length(); i++) {
                JSONObject bantTypeObject = bankBranch.getJSONObject(i);
                if (!bantTypeObject.has("status")) {
                    String banktype_detail = bantTypeObject.getString("banktype_detail");
                    if (banktype_detail != null && !banktype_detail.isEmpty())
                        bankType.setBankType(banktype_detail);
                    else bankType.setBankType(AppConstant.NOT_AVAILABLE);

                    String banktype_code = bantTypeObject.getString("banktype_code");
                    if (banktype_code != null && !banktype_code.isEmpty())
                        bankType.setBankTypeCode(banktype_code);
                    else bankType.setBankTypeCode(AppConstant.NOT_AVAILABLE);

                    SplashScreen.getInstance().getDaoSession().getBankTypeDao().insert(bankType);

                    JSONArray bank_detail = bantTypeObject.getJSONArray("bank_detail");
                    //  AppUtils.getInstance().showLog("bankLoop" + bank_detail.length(), LoginActivity.class);

                    for (int iBankD = 0; iBankD < bank_detail.length(); iBankD++) {
                        //  AppUtils.getInstance().showLog("bankLoop" + iBankD, LoginActivity.class);
                        JSONObject bantDetailObject = bank_detail.getJSONObject(iBankD);
                        if (!bantDetailObject.has("status")) {
                            String bank_code = bantDetailObject.getString("bank_code");
                            if (bank_code != null && !bank_code.isEmpty())
                                bankDetailData.setBankCode(bank_code);
                            else bankDetailData.setBankCode(AppConstant.NOT_AVAILABLE);

                            String eCode = bantDetailObject.getString("entity_code");
                            if (eCode != null && !eCode.isEmpty())
                                bankDetailData.setEntityCode(eCode);
                            else bankDetailData.setEntityCode(AppConstant.NOT_AVAILABLE);

                            String bname = bantDetailObject.getString("bank_name");
                            if (bname != null && !bname.isEmpty())
                                bankDetailData.setBankName(bname);
                            else bankDetailData.setBankName(AppConstant.NOT_AVAILABLE);

                            String banklevel_code = bantDetailObject.getString("banklevel_code");
                            if (banklevel_code != null && !banklevel_code.isEmpty())
                                bankDetailData.setBankLevelCode(banklevel_code);
                            else bankDetailData.setBankLevelCode(AppConstant.NOT_AVAILABLE);

                            String status = bantDetailObject.getString("A/c_length");
                            if (status != null && !status.isEmpty())
                                bankDetailData.setBankAccStatus(status);
                            else bankDetailData.setBankAccStatus(AppConstant.NOT_AVAILABLE);
                            bankDetailData.setBankTypeCode(banktype_code);

                            SplashScreen.getInstance().getDaoSession().getBankDetailDataDao().insert(bankDetailData);

                            JSONArray bank_branch_Detail = bantDetailObject.getJSONArray("bank_branch_detail");
                            for (int iBankBranch = 0; iBankBranch < bank_branch_Detail.length(); iBankBranch++) {
                                JSONObject bantBranchObject = bank_branch_Detail.getJSONObject(iBankBranch);
                                if (!bantBranchObject.has("status")) {
                                    String ifsc_code = bantBranchObject.getString("ifsc_code");
                                    if (ifsc_code != null && !ifsc_code.isEmpty())
                                        bankBranchDetailData.setIfscCode(ifsc_code);
                                    else
                                        bankBranchDetailData.setIfscCode(AppConstant.NOT_AVAILABLE);

                                    String bankCode = bantBranchObject.getString("bank_code");
                                    if (bankCode != null && !bankCode.isEmpty())
                                        bankBranchDetailData.setBankCode(bankCode);
                                    else
                                        bankBranchDetailData.setBankCode(AppConstant.NOT_AVAILABLE);

                                    String bank_branch_name = bantBranchObject.getString("bank_branch_name");
                                    if (bank_branch_name != null && !bank_branch_name.isEmpty())
                                        bankBranchDetailData.setBankBranchName(bank_branch_name);
                                    else
                                        bankBranchDetailData.setBankBranchName(AppConstant.NOT_AVAILABLE);

                                    String branch_entity_code = bantBranchObject.getString("entity_code");
                                    if (branch_entity_code != null && !branch_entity_code.isEmpty())
                                        bankBranchDetailData.setBankBranchEntityCode(branch_entity_code);
                                    else
                                        bankBranchDetailData.setBankBranchEntityCode(AppConstant.NOT_AVAILABLE);

                                    String bank_branch_code = bantBranchObject.getString("bank_branch_code");
                                    if (bank_branch_code != null && !bank_branch_code.isEmpty())
                                        bankBranchDetailData.setBankBranchCode(bank_branch_code);
                                    else
                                        bankBranchDetailData.setBankBranchCode(AppConstant.NOT_AVAILABLE);

                                    SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao().insert(bankBranchDetailData);
                                    //   AppUtils.getInstance().showLog("innerMostLoop" + iBankBranch, LoginActivity.class);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            AppUtils.getInstance().showLog("exceptionBank" + e, LoginActivity.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyLoginBlockCode(), blockCode, LoginActivity.this);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLoginSessionKey(), "logined", LoginActivity.this);
        Mpin = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyMpin(), LoginActivity.this);
        progressDialog.dismiss();
        if (Mpin == null || Mpin == "") {
            try {
                JSONObject mpinFileObject = readMpinFile();
                Mpin = mpinFileObject.getString("mpin");
                AppUtils.getInstance().showLog("Mpinfromfile" + Mpin, LoginActivity.class);
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyMpin(), Mpin, LoginActivity.this);


            } catch (JSONException e) {
                AppUtils.getInstance().showLog("ReadMpinFileExp" + e, VerifyMpinActivity.class);
            }
        }
        if (Mpin.equalsIgnoreCase("") || Mpin == null) {
            progressDialog.dismiss();
            AppUtils.getInstance().makeIntent(LoginActivity.this, MpinActivity.class, true);
            overridePendingTransition(R.anim.in_from_bootem, R.anim.out_to_top);
        } else {
            progressDialog.dismiss();
            AppUtils.getInstance().makeIntent(LoginActivity.this, VerifyMpinActivity.class, true);
            overridePendingTransition(R.anim.in_from_bootem, R.anim.out_to_top);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void populateDBFrombackupFiles() {
        String login_id = "",
                password = "",
                language_id = "",
                logout_days = "",
                server_date_time = "",
                state_code = "",
                mobile_number = "",
                state_short_name = "", block_code = "", block_name = "", pvtg = "N";
        LoginInfoData loginInfoData = new LoginInfoData();
        if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpinFilePath(), AppConstant.MPIN_FILE_NAME)) {
            try {
                JSONObject mpinFileObject = readMpinFile();
                AppUtils.getInstance().showLog("mpinFileObject" + mpinFileObject, LoginActivity.class);
                    /*login_id
                        password
                        language_id
                        logout_days
                        server_date_time
                        state_code
                        mobile_number*/
                mpinFileObject.getString("mpin");
                mpinFileObject.getString("block_code");
                login_id = mpinFileObject.getString("login_id");
                password = mpinFileObject.getString("password");
                language_id = mpinFileObject.getString("language_id");
                logout_days = mpinFileObject.getString("logout_days");
                server_date_time = mpinFileObject.getString("server_date_time");
                state_code = mpinFileObject.getString("state_code");
                mobile_number = mpinFileObject.getString("mobile_number");
                state_short_name = mpinFileObject.getString("state_short_name");
                block_code = mpinFileObject.getString("block_code");
                block_name = mpinFileObject.getString("block_name");
                pvtg = mpinFileObject.getString("pvtg");

                BlockDaoData blockDaoData = new BlockDaoData();
                blockDaoData.setBlockCode(block_code);
                blockDaoData.setBlockname(block_name);
                SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().insert(blockDaoData);
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLoginId(), login_id, context);

                if (login_id != null && !login_id.isEmpty()) {
                    loginInfoData.setLoginId(login_id);
                } else loginInfoData.setLoginId(AppConstant.NOT_AVAILABLE);

                if (mobile_number != null && !mobile_number.isEmpty()) {
                    loginInfoData.setMobileNumber(mobile_number);
                } else loginInfoData.setMobileNumber(AppConstant.NOT_AVAILABLE);

                if (language_id != null && !language_id.isEmpty()) {
                    loginInfoData.setLanguageId(language_id);
                } else loginInfoData.setLanguageId(AppConstant.NOT_AVAILABLE);

                if (logout_days != null && !logout_days.isEmpty()) {
                    loginInfoData.setLogOutDays(logout_days);
                } else loginInfoData.setLogOutDays(AppConstant.NOT_AVAILABLE);

                if (password != null && !password.isEmpty()) {
                    loginInfoData.setPassword(password);
                } else loginInfoData.setPassword(AppConstant.NOT_AVAILABLE);

                if (server_date_time != null && !server_date_time.isEmpty()) {
                    loginInfoData.setServerDateTime(server_date_time);
                } else loginInfoData.setServerDateTime(AppConstant.NOT_AVAILABLE);

                if (state_short_name != null && !state_short_name.isEmpty()) {
                    loginInfoData.setState_short_name(state_short_name);
                } else loginInfoData.setState_short_name(AppConstant.NOT_AVAILABLE);

                if (state_code != null && !state_code.isEmpty()) {
                    loginInfoData.setState_code(state_code);
                } else loginInfoData.setState_code(AppConstant.NOT_AVAILABLE);

                loginInfoData.setPvgtStatus(pvtg);

                SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().insert(loginInfoData);


                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.AADHAR_FILE_NAME)) {
                    try {
                        JSONObject aadharObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.AADHAR_FILE_NAME);
                        AppUtils.getInstance().showLog("aadharObjectFromFile" + aadharObjectFromFile, LoginActivity.class);
                        populateAadharSyncTable(aadharObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("aadharObjectFromFileExp" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.BANK_ACCOUNT_FILE_NAME)) {
                    try {
                        JSONObject bankAccountObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.BANK_ACCOUNT_FILE_NAME);
                        AppUtils.getInstance().showLog("bankAccountObjectFromFile" + bankAccountObjectFromFile, LoginActivity.class);
                        populateBankAccountSyncTable(bankAccountObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("bankAccountObjectFromFile" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.ADD_MEMBER_FILE_NAME)) {
                    try {
                        JSONObject addMemberObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.ADD_MEMBER_FILE_NAME);
                        AppUtils.getInstance().showLog("addMemberObjectFromFile" + addMemberObjectFromFile, LoginActivity.class);
                        populateAddMemberSyncTable(addMemberObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("addMemberObjectFromFileExp" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.KYC_DOC_FILE_NAME)) {

                    try {
                        JSONObject kycDocObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.KYC_DOC_FILE_NAME);
                        AppUtils.getInstance().showLog("kycDocObjectFromFile" + kycDocObjectFromFile, LoginActivity.class);
                        populateKycDocSyncTable(kycDocObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("kycDocObjectFromFileExp" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.SHG_INACTIVATION_STATUS_FILE_NAME)) {

                    try {
                        JSONObject shgInactvStsObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.SHG_INACTIVATION_STATUS_FILE_NAME);
                        AppUtils.getInstance().showLog("shgInactvStsObjectFromFile" + shgInactvStsObjectFromFile, LoginActivity.class);
                        populateShgInactivationSyncTable(shgInactvStsObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("shgInactvStsObjectFromFile" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.MEMBER_STATUS_FILE_NAME)) {

                    try {
                        JSONObject memberInactvStsObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.MEMBER_STATUS_FILE_NAME);
                        AppUtils.getInstance().showLog("memberInactvStsObjectFromFile" + memberInactvStsObjectFromFile, LoginActivity.class);
                        populateMemberInactivationSyncTable(memberInactvStsObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("memberInactvStsObjectFromFile" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.MEMBER_UPDATE_STATUS_FILE_NAME)) {

                    try {
                        JSONObject memberupdateObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.MEMBER_UPDATE_STATUS_FILE_NAME);
                        AppUtils.getInstance().showLog("memberupdateObjectFromFile" + memberupdateObjectFromFile, LoginActivity.class);
                        populateMemberUpdateSyncTable(memberupdateObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("memberupdateObjectFromFileExp" + e, LoginActivity.class);
                    }
                }

                if (FileUtility.getInstance().isFileExist(FileManager.getInstance()
                        .getPathDetails(LoginActivity.this), AppConstant.UPDATED_DESIGNATION_FILE_NAME)) {

                    try {
                        JSONObject updatedDesgObjectFromFile = readFileFromInternal(LoginActivity.this, AppConstant.UPDATED_DESIGNATION_FILE_NAME);
                        AppUtils.getInstance().showLog("updatedDesgObjectFromFile" + updatedDesgObjectFromFile, LoginActivity.class);
                        populateUpdatedDesgSyncTable(updatedDesgObjectFromFile);
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("updatedDesgObjectFromFileExp" + e, LoginActivity.class);
                    }
                }
            } catch (JSONException e) {
                AppUtils.getInstance().showLog("MpinFileReadExp" + e, LoginActivity.class);
            }
        }
    }

    private void populateUpdatedDesgSyncTable(JSONObject updatedDesgObjectFromFile) {
        try {

            JSONArray jsonArray=updatedDesgObjectFromFile.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                UpdatedDesignation updatedDesignation=new UpdatedDesignation();
                updatedDesignation.setShgCode(jsonObject.getString("shgcode"));
                updatedDesignation.setDesignation( jsonObject.getString("designation"));

                SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().insert(updatedDesignation);
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateMemberUpdateSyncTable(JSONObject memberupdateObjectFromFile) {

        try {
            Cryptography cryptography = new Cryptography();
            JSONArray jsonArray=new JSONArray(cryptography.decrypt(memberupdateObjectFromFile.getString("member_data")));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ChangeDesignationSyncData changeDesignationSyncData=new ChangeDesignationSyncData();
                changeDesignationSyncData.setEnityCode(cryptography.decrypt(jsonObject.getString("enity_code")));
                changeDesignationSyncData.setSyncStatus(cryptography.decrypt(jsonObject.getString("sync_status")));
                changeDesignationSyncData.setShgCode(cryptography.decrypt(jsonObject.getString("shg_code")));
                changeDesignationSyncData.setMemberCode(cryptography.decrypt(jsonObject.getString("member_code")));
                changeDesignationSyncData.setUpdatedDesignation(cryptography.decrypt(jsonObject.getString("updated_deg_code")));

                SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().insert(changeDesignationSyncData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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


    private void populateMemberInactivationSyncTable(@NonNull JSONObject memberInactvStsObjectFromFile) {

        try {
            Cryptography cryptography = new Cryptography();

            ShgInActivSyncData shgInActivSyncData = new ShgInActivSyncData();

            JSONArray jsonArray = new JSONArray(cryptography.decrypt(memberInactvStsObjectFromFile.getString("Member_status_array")));
            /* memberInactvStsObjectFromFile.getJSONArray("Member_status_array");*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                shgInActivSyncData.setBlockCode(cryptography.decrypt(jsonObject.getString("block_code")));
                shgInActivSyncData.setGpCode(cryptography.decrypt(jsonObject.getString("gp_code")));
                shgInActivSyncData.setVillageCode(cryptography.decrypt(jsonObject.getString("village_code")));
                shgInActivSyncData.setShgCode(cryptography.decrypt(jsonObject.getString("shg_code")));
                shgInActivSyncData.setShgMemberCode(cryptography.decrypt(jsonObject.getString("member_code")));
                shgInActivSyncData.setInactiveStatus(cryptography.decrypt(jsonObject.getString("Member_status")));
                shgInActivSyncData.setSyncInactivReson(cryptography.decrypt(jsonObject.getString("reason_id")));
                shgInActivSyncData.setLoanStatus(cryptography.decrypt(jsonObject.getString("loan_status")));
                shgInActivSyncData.setSyncStatus("0");
                shgInActivSyncData.setAutoIncrement(null);

                SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao().insert(shgInActivSyncData);
            }
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("memberInactvStsObjectFromFileExp" + e, LoginActivity.class);
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
    }

    private void populateShgInactivationSyncTable(@NonNull JSONObject shgInactvStsObjectFromFile) {

        ShgInactivationSyncData shgInactivationSyncData = new ShgInactivationSyncData();
        try {
            Cryptography cryptography = new Cryptography();

            JSONArray jsonArray = new JSONArray(cryptography.decrypt(shgInactvStsObjectFromFile.getString("shg_inactive_details")));
            /*shgInactvStsObjectFromFile.getJSONArray("shg_inactive_details");*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                shgInactivationSyncData.setVillageCode(cryptography.decrypt(jsonObject.getString("village_code")));
                shgInactivationSyncData.setId(null);
                shgInactivationSyncData.setShgCode(cryptography.decrypt(jsonObject.getString("shg_code")));
                shgInactivationSyncData.setReasonId(cryptography.decrypt(jsonObject.getString("reason_id")));
                shgInactivationSyncData.setLoanStatusId(cryptography.decrypt(jsonObject.getString("loan_status_id")));
                shgInactivationSyncData.setSyncStatus("0");

                SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().insert(shgInactivationSyncData);
            }
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("shgInactvStsObjectFromFileExp" + e, LoginActivity.class);
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

    private void populateKycDocSyncTable(@NonNull JSONObject kycDocObjectFromFile) {

        try {
            Cryptography cryptography = new Cryptography();
            JSONArray kycDocArray = new JSONArray(cryptography.decrypt(kycDocObjectFromFile.getString("kyc_detail")));
            /* kycDocObjectFromFile.getJSONArray("kyc_detail");*/

            for (int i = 0; i < kycDocArray.length(); i++) {
                JSONObject kycDocObject = kycDocArray.getJSONObject(i);

                KycDocSyncData kycDocSyncData = new KycDocSyncData();
                kycDocSyncData.setId(null);
                kycDocSyncData.setSyncStatus("0");
                kycDocSyncData.setVillage_code(cryptography.decrypt(kycDocObject.getString("village_code")));
                kycDocSyncData.setShgCode(cryptography.decrypt(kycDocObject.getString("shg_code")));
                kycDocSyncData.setMemberCode(cryptography.decrypt(kycDocObject.getString("shg_member_code")));
                kycDocSyncData.setDocId(cryptography.decrypt(kycDocObject.getString("doc_id")));
                kycDocSyncData.setDocNo(cryptography.decrypt(kycDocObject.getString("doc_number")));
                kycDocSyncData.setDocFrontImage(bitmapToByteArray(stringToBitMap(kycDocObject.getString("front_image"))));
                String backImage = kycDocObject.getString("back_image");
                if (backImage.equalsIgnoreCase("")) {
                    kycDocSyncData.setDocBackImage(null);
                } else kycDocSyncData.setDocBackImage(bitmapToByteArray(stringToBitMap(backImage)));

                SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().insert(kycDocSyncData);
            }

        } catch (JSONException e) {
            AppUtils.getInstance().showLog("kycDocObjectFromFilejsonExp" + e, LoginActivity.class);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void populateAadharSyncTable(@NonNull JSONObject aadharObjectFromFile) {
        try {
            Cryptography cryptography = new Cryptography();
            AadharDetailSyncData aadharDetailSyncData = new AadharDetailSyncData();
            try {
                JSONArray account_data = new JSONArray(cryptography.decrypt(aadharObjectFromFile.getString("account_data")));
                if (account_data.length() > 0) {
                    for (int i = 0; i < account_data.length(); i++) {

                        aadharDetailSyncData.setVillageCode(cryptography.decrypt(account_data.getJSONObject(i).getString("village_code")));
                        aadharDetailSyncData.setShgMemberCode(cryptography.decrypt(account_data.getJSONObject(i).getString("shg_member_code")));
                        aadharDetailSyncData.setShgCode(cryptography.decrypt(account_data.getJSONObject(i).getString("shg_code")));
                        aadharDetailSyncData.setAadhaarMobileNumber(cryptography.decrypt(account_data.getJSONObject(i).getString("mobile_no")));
                        aadharDetailSyncData.setAadharName(cryptography.decrypt(account_data.getJSONObject(i).getString("member_name")));
                        aadharDetailSyncData.setLatLong(cryptography.decrypt(account_data.getJSONObject(i).getString("gps_location_coordinate")));
                        aadharDetailSyncData.setAadharGender(cryptography.decrypt(account_data.getJSONObject(i).getString("gender")));
                        aadharDetailSyncData.setAadharYOB(cryptography.decrypt(account_data.getJSONObject(i).getString("dob")));
                        aadharDetailSyncData.setAadharFatherName(cryptography.decrypt(account_data.getJSONObject(i).getString("belonging_name")));
                        aadharDetailSyncData.setAadharAddress(cryptography.decrypt(account_data.getJSONObject(i).getString("address")));
                        aadharDetailSyncData.setAadharNumber(cryptography.decrypt(account_data.getJSONObject(i).getString("aadhaar_no")));
                        aadharDetailSyncData.setScanStatus(cryptography.decrypt(account_data.getJSONObject(i).getString("qr_reader")));
                        aadharDetailSyncData.setAadharSyncStatus("0");
                        aadharDetailSyncData.setAutoIncrement(null);
                        aadharDetailSyncData.setPostalCode("123456");
                        aadharDetailSyncData.setAadhaarImage(bitmapToByteArray(stringToBitMap(account_data.getJSONObject(i).getString("member_image"))));
                       // aadharDetailSyncData.setConcentForm(bitmapToByteArray(stringToBitMap(account_data.getJSONObject(i).getString("consent_pdf"))));
                        aadharDetailSyncData.setConcentForm(bitmapToByteArray(stringToBitMap(account_data.getJSONObject(i).getString("consent_pdf"))));
                        /*Base64.getDecoder().decode(new String(name).getBytes("UTF-8"));*/

                        SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao().insert(aadharDetailSyncData);
                    }
                }
            } catch (JSONException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileExp" + e, LoginActivity.class);
            } catch (InvalidKeyException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileInvalidKeyException" + e, LoginActivity.class);
            } catch (InvalidAlgorithmParameterException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileInvalidAlgorithmParameterException" + e, LoginActivity.class);
            } catch (IllegalBlockSizeException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileIllegalBlockSizeException" + e, LoginActivity.class);
            } catch (BadPaddingException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileBadPaddingException" + e, LoginActivity.class);
            } catch (UnsupportedEncodingException e) {
                AppUtils.getInstance().showLog("aadharObjectFromFileUnsupportedEncodingException" + e, LoginActivity.class);
            }
        } catch (NoSuchAlgorithmException e) {
            AppUtils.getInstance().showLog("aadharObjectFromFileNoSuchAlgorithmException" + e, LoginActivity.class);
        } catch (NoSuchPaddingException e) {
            AppUtils.getInstance().showLog("aadharObjectFromFileNoSuchPaddingException" + e, LoginActivity.class);
        }
    }

    private void populateBankAccountSyncTable(@NonNull JSONObject bankAccountObjectFromFile) {
        try {
            Cryptography cryptography = new Cryptography();
            AccountDetailSyncData accountDetailSyncData = new AccountDetailSyncData();
            JSONArray account_data = new JSONArray(cryptography.decrypt(bankAccountObjectFromFile.getString("account_data")));
            if (account_data.length() > 0) {

                for (int i = 0; i < account_data.length(); i++) {

                    accountDetailSyncData.setSyncStatus("0");
                    accountDetailSyncData.setIfscNumber("0");
                    accountDetailSyncData.setAutoIncrement(null);
                    accountDetailSyncData.setVillageCode(cryptography.decrypt(account_data.getJSONObject(i).getString("village_code")));
                    accountDetailSyncData.setShgMemberCode(cryptography.decrypt(account_data.getJSONObject(i).getString("shg_member_code")));
                    accountDetailSyncData.setShgCode(cryptography.decrypt(account_data.getJSONObject(i).getString("shg_code")));
                    accountDetailSyncData.setLatLong(cryptography.decrypt(account_data.getJSONObject(i).getString("gps_location_coordinate")));
                    accountDetailSyncData.setBankBranchCode(cryptography.decrypt(account_data.getJSONObject(i).getString("branch_code")));
                    accountDetailSyncData.setBankNameCode(cryptography.decrypt(account_data.getJSONObject(i).getString("bank_code")));
                    accountDetailSyncData.setAccountNumber(cryptography.decrypt(account_data.getJSONObject(i).getString("account_number")));
                    accountDetailSyncData.setPhotoImage(bitmapToByteArray(stringToBitMap(account_data.getJSONObject(i).getString("passbook_image"))));

                    SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao().insert(accountDetailSyncData);
                }
            }

        } catch (NoSuchAlgorithmException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileNoSuchAlgorithmException" + e, LoginActivity.class);
        } catch (NoSuchPaddingException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileNoSuchPaddingException" + e, LoginActivity.class);
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFile" + e, LoginActivity.class);
        } catch (InvalidKeyException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileInvalidKeyException" + e, LoginActivity.class);
        } catch (InvalidAlgorithmParameterException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileInvalidAlgorithmParameterException" + e, LoginActivity.class);
        } catch (IllegalBlockSizeException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileIllegalBlockSizeException" + e, LoginActivity.class);
        } catch (BadPaddingException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileBadPaddingException" + e, LoginActivity.class);
        } catch (UnsupportedEncodingException e) {
            AppUtils.getInstance().showLog("bankAccountObjectFromFileUnsupportedEncodingException" + e, LoginActivity.class);
        }
    }

    private void populateAddMemberSyncTable(@NonNull JSONObject addMemberObjectFromFile) {

        try {
            Cryptography cryptography = new Cryptography();
            ShgMemberRegistrationSyncData shgMemberRegistrationSyncData = new ShgMemberRegistrationSyncData();

            JSONArray member_data = new JSONArray(cryptography.decrypt(addMemberObjectFromFile.getString("member_data")));
            if (member_data.length() > 0) {
                for (int i = 0; i < member_data.length(); i++) {
                    shgMemberRegistrationSyncData.setAadharNumber(cryptography.decrypt(member_data.getJSONObject(i).getString("aadhaar_no")));
                    shgMemberRegistrationSyncData.setAadharSeededAccount(cryptography.decrypt(member_data.getJSONObject(i).getString("aadhar_seeded_sb_ac")));
                    shgMemberRegistrationSyncData.setAccountNumber(cryptography.decrypt(member_data.getJSONObject(i).getString("account_number")));
                    shgMemberRegistrationSyncData.setMemberAddress(cryptography.decrypt(member_data.getJSONObject(i).getString("address")));
                    shgMemberRegistrationSyncData.setBankNameCode(cryptography.decrypt(member_data.getJSONObject(i).getString("bank_code")));
                    shgMemberRegistrationSyncData.setMemberFathername(cryptography.decrypt(member_data.getJSONObject(i).getString("belonging_name")));
                    shgMemberRegistrationSyncData.setIsShopKeeper(cryptography.decrypt(member_data.getJSONObject(i).getString("book_keeper_member")));
                    shgMemberRegistrationSyncData.setBankBranchCode(cryptography.decrypt(member_data.getJSONObject(i).getString("branch_code")));
                    shgMemberRegistrationSyncData.setDisablity(cryptography.decrypt(member_data.getJSONObject(i).getString("disability")));
                    shgMemberRegistrationSyncData.setMemberYOB(cryptography.decrypt(member_data.getJSONObject(i).getString("dob")));
                    shgMemberRegistrationSyncData.setEducationStandard(cryptography.decrypt(member_data.getJSONObject(i).getString("education")));
                    shgMemberRegistrationSyncData.setPensionScheme(cryptography.decrypt(member_data.getJSONObject(i).getString("enroll_in_hic")));
                    shgMemberRegistrationSyncData.setLifeInsurance(cryptography.decrypt(member_data.getJSONObject(i).getString("enroll_in_lic")));
                    shgMemberRegistrationSyncData.setPmjjy(cryptography.decrypt(member_data.getJSONObject(i).getString("enroll_in_pmjy")));
                    shgMemberRegistrationSyncData.setPmsby(cryptography.decrypt(member_data.getJSONObject(i).getString("enroll_in_pmsby")));
                    shgMemberRegistrationSyncData.setMemberGender(cryptography.decrypt(member_data.getJSONObject(i).getString("gender")));
                    shgMemberRegistrationSyncData.setLeader(cryptography.decrypt(member_data.getJSONObject(i).getString("leader")));
                    shgMemberRegistrationSyncData.setShgMemberuniqueCode(cryptography.decrypt(member_data.getJSONObject(i).getString("member_android_code")));
                    shgMemberRegistrationSyncData.setMemberPhoto(bitmapToByteArray(stringToBitMap(member_data.getJSONObject(i).getString("member_image"))));
                    shgMemberRegistrationSyncData.setMemberName(cryptography.decrypt(member_data.getJSONObject(i).getString("member_name")));
                    shgMemberRegistrationSyncData.setMembermobileNumber(cryptography.decrypt(member_data.getJSONObject(i).getString("mobile_number")));
                    shgMemberRegistrationSyncData.setOtherEducation(cryptography.decrypt(member_data.getJSONObject(i).getString("other_education")));
                    shgMemberRegistrationSyncData.setPassbookPhoto(bitmapToByteArray(stringToBitMap(member_data.getJSONObject(i).getString("passbook_image"))));
                    shgMemberRegistrationSyncData.setConcentForm(bitmapToByteArray(stringToBitMap(member_data.getJSONObject(i).getString("consent_pdf"))));
                    shgMemberRegistrationSyncData.setPipCatagory(cryptography.decrypt(member_data.getJSONObject(i).getString("pip_category")));
                    shgMemberRegistrationSyncData.setReligion(cryptography.decrypt(member_data.getJSONObject(i).getString("religion")));
                    shgMemberRegistrationSyncData.setSeccNo(cryptography.decrypt(member_data.getJSONObject(i).getString("secc_no")));
                    shgMemberRegistrationSyncData.setShgcode(cryptography.decrypt(member_data.getJSONObject(i).getString("shg_code")));
                    shgMemberRegistrationSyncData.setMamberDateOfJoining(cryptography.decrypt(member_data.getJSONObject(i).getString("shg_joining_date")));
                    shgMemberRegistrationSyncData.setSocialCatagory(cryptography.decrypt(member_data.getJSONObject(i).getString("social_catagory")));
                    shgMemberRegistrationSyncData.setVillagecode(cryptography.decrypt(member_data.getJSONObject(i).getString("village_code")));
                    shgMemberRegistrationSyncData.setPvgtStatus(cryptography.decrypt(member_data.getJSONObject(i).getString("pvgt_status")));
                    shgMemberRegistrationSyncData.setScanStatus(cryptography.decrypt(member_data.getJSONObject(i).getString("qr_reader")));
                    shgMemberRegistrationSyncData.setBlockcode("0");
                    shgMemberRegistrationSyncData.setGpcode("0");
                    shgMemberRegistrationSyncData.setBankName("hi");
                    shgMemberRegistrationSyncData.setBankBranch("Hi");
                    shgMemberRegistrationSyncData.setIfscCode("00");
                    shgMemberRegistrationSyncData.setSyncStatus("0");
                    shgMemberRegistrationSyncData.setPostalCode("123456");

                    SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao().insert(shgMemberRegistrationSyncData);
                }
            }

        } catch (NoSuchAlgorithmException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileNoSuchAlgorithmException" + e, LoginActivity.class);
        } catch (NoSuchPaddingException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileNoSuchPaddingException" + e, LoginActivity.class);
        } catch (JSONException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileExp" + e, LoginActivity.class);
        } catch (InvalidKeyException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileInvalidKeyException" + e, LoginActivity.class);
        } catch (InvalidAlgorithmParameterException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileInvalidAlgorithmParameterException" + e, LoginActivity.class);
        } catch (IllegalBlockSizeException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileIllegalBlockSizeException" + e, LoginActivity.class);
        } catch (BadPaddingException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileBadPaddingException" + e, LoginActivity.class);
        } catch (UnsupportedEncodingException e) {
            AppUtils.getInstance().showLog("addMemberObjectFromFileUnsupportedEncodingException" + e, LoginActivity.class);
        }


    }

    @NonNull
    public JSONObject readMpinFile() throws JSONException {
        String mpinFile = "{}";
        if (FileUtility.getInstance().isFileExist(FileManager.getInstance().getMpinFilePath(), AppConstant.MPIN_FILE_NAME)) {
            mpinFile = FileUtility.getInstance().read_file(FileManager.getInstance().getAbsluteMpinPathDetails(LoginActivity.this, AppConstant.MPIN_FILE_NAME)
                    , FileManager.getInstance().getMpinFilePath(), AppConstant.MPIN_FILE_NAME);
            try {
                mpinFile = new Cryptography().decrypt(mpinFile);
                AppUtils.getInstance().showLog("mpinFile" + mpinFile, LoginActivity.class);
            } catch (InvalidKeyException e) {
                AppUtils.getInstance().showLog("DecrptionInvalidKeyException" + e, LoginActivity.class);
            } catch (UnsupportedEncodingException e) {
                AppUtils.getInstance().showLog("DecrptionUnsupportedEncodingException" + e, LoginActivity.class);
            } catch (InvalidAlgorithmParameterException e) {
                AppUtils.getInstance().showLog("DecrptionInvalidAlgorithmParameterException" + e, LoginActivity.class);
            } catch (IllegalBlockSizeException e) {
                AppUtils.getInstance().showLog("DecrptionIllegalBlockSizeException" + e, LoginActivity.class);
            } catch (BadPaddingException e) {
                AppUtils.getInstance().showLog("DecrptionBadPaddingException" + e, LoginActivity.class);
            } catch (NoSuchAlgorithmException e) {
                AppUtils.getInstance().showLog("DecrptionNoSuchAlgorithmException" + e, LoginActivity.class);
            } catch (NoSuchPaddingException e) {
                AppUtils.getInstance().showLog("DecrptionNoSuchPaddingException" + e, LoginActivity.class);
            }
        }
        return new JSONObject(mpinFile);
    }

    @Nullable
    public JSONObject readFileFromInternal(@NonNull Context context, @NonNull String fileName) throws JSONException {
        JSONObject mPinLocalFileObject = null;
        AppUtils.getInstance().showLog("context" + LoginActivity.this, LoginActivity.class);
        String string = FileUtility.getInstance().read_file(FileManager.getInstance().getAbslutePathDetails(context, fileName), FileManager.getInstance().getPathDetails(context), fileName);
        try {
            string = new Cryptography().decrypt(string);
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

        mPinLocalFileObject = new JSONObject(string);
        return mPinLocalFileObject;
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @NonNull
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
            AppUtils.getInstance().showLog("SSLConnectionExp" + ignored.toString(), LoginActivity.class);
        }
    }

   /* @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        r1 = new Runnable() {
            @Override
            public void run() {
                System.exit(0);

            }
        };
        handler.postDelayed(r1, AppConstant.BACKGROUND_TIME);
    }*/

    @NonNull
    public String getCoordinateLL(@NonNull Context context) {
        String latitude = "0.0";
        String longitude = "0.0";
        try{
            GpsTracker gpsTracker = new GpsTracker(context);
            if (!AppUtils.isGPSEnabled(context)) {
                // Toast.makeText(getApplicationContext(),"Open your Gps",Toast.LENGTH_LONG).show();
            } else {
                gpsTracker.getLocation();
                latitude = String.valueOf(gpsTracker.latitude);
                longitude = String.valueOf(gpsTracker.longitude);
            }
        }catch (Exception e){

        }

        return latitude + "," + longitude;
    }

    @Nullable
    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = android.util.Base64.decode(encodedString, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @NonNull
    private byte[] bitmapToByteArray(@NonNull Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


/*    public class MyCounter extends CountDownTimer{

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

    }
}*/


}
