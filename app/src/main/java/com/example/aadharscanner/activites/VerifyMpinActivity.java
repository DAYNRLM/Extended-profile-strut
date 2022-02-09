package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aadharscanner.R;
import com.example.aadharscanner.database.WebRequestData;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class VerifyMpinActivity extends AppCompatActivity {
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private final static String TAG = VerifyMpinActivity.class.getSimpleName();
    @Nullable
    private static String TRUE_CODE;


    private TextView fgtPin;
    private EditText idFgd, passwordFgd;
    /* private ProgressDialog progressDialog;*/
    private Button submitBtn;
    private int exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mpin);
        // progressDialog= DialogFactory.getInstance().showProgressDialog(VerifyMpinActivity.this,false);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        TRUE_CODE = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyMpin(), VerifyMpinActivity.this);
        AppUtils.getInstance().showLog("TRUE_CODE" + TRUE_CODE, VerifyMpinActivity.class);
        fgtPin = (TextView) findViewById(R.id.forgot_pin);
        fgtPin.setPaintFlags(fgtPin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLength(4);
        fgtPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent=new Intent(VerifyMpinActivity.this,ChangeMpinActivity.class);
                //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //   startActivity(intent);
                AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, ChangeMpinActivity.class, false);
                //overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

                //  Toast.makeText(VerifyMpinActivity.this,"Not completed yet",Toast.LENGTH_SHORT).show();
            }
        });
        mPinLockView.setPinLockListener(new PinLockListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onComplete(@NonNull String pin) {
                // Log.d(TAG, "lock code: " + pin);
                if (pin.equals(TRUE_CODE)) {
                    // AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, HomeActivity.class, true);
                    checkWebRequest(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), VerifyMpinActivity.this));
                } else {
                    Toast.makeText(VerifyMpinActivity.this, VerifyMpinActivity.this.getString(R.string.faild_code), Toast.LENGTH_SHORT).show();
                    // AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, VerifyMpinActivity.class, true);
                    mPinLockView.resetPinLockView();
                }
            }

            @Override
            public void onEmpty() {
                // Log.d(TAG, "lock code is empty!");
                Toast.makeText(VerifyMpinActivity.this, getString(R.string.error_in_fill_mpin), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                //Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkWebRequest(String userId) {
        /**********************************************request for post json ***********************************************/
        // String JSON_REQUEST_URL = AppConstant.HTTP_TYPE + "://" + AppConstant.IP_ADDRESS + "/" + AppConstant.VILLAGE_CHECK_REQUEST + "/services/sakshamchk/assignuser";

        if (NetworkFactory.isInternetOn(this)) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            //  progressDialog.show();
            JSONObject requestObject = new JSONObject();
            try {
                requestObject.accumulate("login_id", userId);
                requestObject.accumulate("imei_no", PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getIMEI(), VerifyMpinActivity.this));
                requestObject.accumulate("device_name", new LoginActivity().getDeviceInfo());
                requestObject.accumulate("location_coordinate", new LoginActivity().getCoordinateLL(VerifyMpinActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppUtils.getInstance().showLog("webrequestObject" + requestObject, VerifyMpinActivity.class);

            requestObject = AppUtils.getInstance().wantToEncrypt(requestObject);
            AppUtils.getInstance().showLog("requestObjectEncrypted" + requestObject, VerifyMpinActivity.class);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.VILLAGE_CHECK_REQUEST, requestObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    AppUtils.getInstance().showLog("response" + response, VerifyMpinActivity.class);
                    try {
                        response = AppUtils.getInstance().wantToDecrypt(response);
                        if (response != null) {
                            AppUtils.getInstance().showLog("webrequestresponse" + response, VerifyMpinActivity.class);
                            JSONArray responseArray = response.getJSONArray("data");
                            if (responseArray.getJSONObject(0).has("status")) {
                                JSONObject object = responseArray.getJSONObject(0);
                                String status = object.getString("status");
                                if (status.equalsIgnoreCase("OK !!!")) {
                                    AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, HomeActivity.class, true);
                                }

                            } else {
                                WebRequestData webRequestData = new WebRequestData();
                                AppUtils.getInstance().showLog("webrequestresponseelse" + response, VerifyMpinActivity.class);
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject jsonObject = responseArray.getJSONObject(i);
                                    if (jsonObject.has("village_code")) {
                                        webRequestData.setVillageCode(jsonObject.getString("village_code"));
                                        webRequestData.setStatus(jsonObject.getString("village_status"));
                                        SplashScreen.getInstance().getDaoSession().getWebRequestDataDao().insert(webRequestData);
                                    }
                                }
                                progressDialog.dismiss();
                                AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, DeleteVillage.class, true);
                            }
                        } else
                            AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, HomeActivity.class, true);
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        AppUtils.getInstance().showLog("webRequestExc" + e, VerifyMpinActivity.class);
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, HomeActivity.class, true);
                }
            });
            SingletonVolley.getInstance(this.getApplicationContext()).getRequestQueue().getCache().clear();

            SingletonVolley.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        } else {
            AppUtils.getInstance().makeIntent(VerifyMpinActivity.this, HomeActivity.class, true);
        }

        /************************************************************************************************************************/
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {

        if (exit == 0) {
            Toast.makeText(VerifyMpinActivity.this, getString(R.string.toast_exit_app), Toast.LENGTH_LONG).show();
            exit += 1;
        } else {
            super.onBackPressed();

            System.exit(0);
        }

    }

}


