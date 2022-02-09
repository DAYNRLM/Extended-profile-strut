package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aadharscanner.R;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;
import com.google.android.material.textfield.TextInputEditText;

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

public class OTPVerification extends AppCompatActivity {
    private TextInputEditText enterPasswordTIET, confirmPasswordTIET, enter_UserIdEt;
    private PinEntryEditText enterOtpPEET;
    private Button resetPassBTN, new_passwordBtn;
    private String enteredOTP;
    @Nullable
    private String generatedOTP;
    private LinearLayout passwordCreation;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);

        enter_UserIdEt = (TextInputEditText) findViewById(R.id.enter_UserIdEt);
        enterPasswordTIET = (TextInputEditText) findViewById(R.id.enter_passwordTIET);
        confirmPasswordTIET = (TextInputEditText) findViewById(R.id.confirm_passwordTIET);
        enterOtpPEET = (PinEntryEditText) findViewById(R.id.enter_otpPEET);
        resetPassBTN = (Button) findViewById(R.id.reset_PassBTN);
        new_passwordBtn = (Button) findViewById(R.id.new_passwordBtn);
        passwordCreation = (LinearLayout) findViewById(R.id.reset_passwordLL);
         generatedOTP = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyOtp(), OTPVerification.this);
        enterOtpPEET.setAnimateText(true);
        if (AppConstant.SERVER_TYPE.equalsIgnoreCase("demo"))
            Toast.makeText(OTPVerification.this,"OTP is : "+generatedOTP,Toast.LENGTH_LONG).show();

        if (enterOtpPEET != null) {
            enterOtpPEET.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(@NonNull CharSequence str) {
                    enteredOTP = str.toString().trim();
                }
            });

            new_passwordBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {

                    String enteredPassword = enterPasswordTIET.getText().toString().trim();
                    String confirmPassword = confirmPasswordTIET.getText().toString().trim();
                    String userId = enter_UserIdEt.getText().toString().trim().toUpperCase();

                    if (userId == null || userId.equalsIgnoreCase("") || userId.length() < 4) {
                        enter_UserIdEt.setError(getString(R.string.server_error_invalid_id_or_password_massege));
                    } else if (enteredPassword.length() < 6) {
                        enterPasswordTIET.setError(getString(R.string.server_error_invalid_id_or_password_massege));
                    } else if (confirmPassword.length() < 6) {
                        confirmPasswordTIET.setError(getString(R.string.error_confirm_password));
                    } else {
                        if (enteredPassword.equalsIgnoreCase(confirmPassword)) {

                            AppUtils.getInstance().showLog("enteredOTP=" + enteredOTP + "generatedOTP=" + generatedOTP, OTPVerification.class);
                            if (enteredOTP.equalsIgnoreCase(generatedOTP)) {
                                registerPasswordOnServer(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyMobileNumber(),
                                        OTPVerification.this), confirmPassword, userId);
                            } else {
                                Toast.makeText(OTPVerification.this, getString(R.string.toast_otp_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            confirmPasswordTIET.setError(getString(R.string.error_confirm_password_not_matched));
                        }
                    }

                }
            });

            resetPassBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generatedOTP = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyOtp(), OTPVerification.this);
                    if (enteredOTP.equalsIgnoreCase(generatedOTP)) {
                        passwordCreation.setVisibility(View.VISIBLE);
                        resetPassBTN.setVisibility(View.GONE);
                        new_passwordBtn.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(OTPVerification.this, getString(R.string.toast_otp_wrong), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void registerPasswordOnServer(String mobileNo, @NonNull String registerePassword, String userId) {

        if (!NetworkFactory.isInternetOn(OTPVerification.this)) {
            DialogFactory.getInstance().showErrorAlertDialog(OTPVerification.this, getString(R.string.NO_INTERNET_TITLE), getString(R.string.INTERNET_MESSAGE), "OK");
        } else {
            ProgressDialog progressDialog = DialogFactory.getInstance().showProgressDialog(OTPVerification.this, false);
            progressDialog.show();

            JSONObject resetPassword = new JSONObject();
            try {
                resetPassword.accumulate("mobileno", mobileNo);
                resetPassword.accumulate("password", AppUtils.getInstance().getSha256(registerePassword));
                resetPassword.accumulate("login_id", userId);
                resetPassword.accumulate("imei_no", new LoginActivity().getIMEINo1());
                resetPassword.accumulate("device_name", new LoginActivity().getDeviceInfo());
                resetPassword.accumulate("location_coordinate", new LoginActivity().getCoordinateLL(OTPVerification.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppUtils.getInstance().showLog("resetPassword" + resetPassword, OTPVerification.class);

            resetPassword = AppUtils.getInstance().wantToEncrypt(resetPassword);
            AppUtils.getInstance().showLog("resetPasswordEncrypted" + resetPassword, OTPVerification.class);

            AppUtils.getInstance().showLog("resetPasswordApi" + AppConstant.FORGET_PASSWORD, OTPVerification.class);

            JsonObjectRequest resetPasswordRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.FORGET_PASSWORD, resetPassword, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    AppUtils.getInstance().showLog("responseResetPass" + response, OTPVerification.class);
                    try {
                        response = AppUtils.getInstance().wantToDecrypt(response);
                        AppUtils.getInstance().showLog("responseResetPassDecrypt" + response, OTPVerification.class);
                        status = response.getString("status");
                        if (!status.equalsIgnoreCase("Updated Successfully!!!")) {
                            status = response.getString("status") + getString(R.string.error_in_update_pass_response);
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerification.this);
                        builder.setTitle(R.string.reset_pass_TV);
                        builder.setCancelable(false);
                        builder.setMessage(status);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialogInterface, int i) {

                                if (status.equalsIgnoreCase("Updated Successfully!!!")) {
                                    PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefKeyOtp(), OTPVerification.this);
                                    PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefKeyMobileNumber(), OTPVerification.this);
                                    AppUtils.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                } else {
                                    PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefKeyOtp(), OTPVerification.this);
                                    PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefKeyMobileNumber(), OTPVerification.this);
                                    AppUtils.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
                                }
                                dialogInterface.dismiss();
                            }
                        }).show();
                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("JSONException" + e.getMessage(), OTPVerification.class);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(@NonNull VolleyError error) {
                    progressDialog.dismiss();
                    DialogFactory.getInstance().showErrorAlertDialog(OTPVerification.this, getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "ok");
                    AppUtils.getInstance().showLog("volleyError.getMessage()" + error.getMessage(), OTPVerification.class);
                }
            });
            SingletonVolley.getInstance(this.getApplicationContext()).getRequestQueue().getCache().clear();
            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(resetPasswordRequest);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtils.getInstance().makeIntent(OTPVerification.this, LoginActivity.class, true);
    }

}
