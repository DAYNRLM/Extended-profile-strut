package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.aadharscanner.R;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MpinActivity extends AppCompatActivity {
    private TextView btnMpinProceed;
    private PinEntryEditText pinEntryEditText, pinConfirmMpinEditText;
    @Nullable
    private String mPin, confirmMpin;
    private int exit = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);
        btnMpinProceed = (TextView) findViewById(R.id.btnMpinProceed);
        pinEntryEditText = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        pinConfirmMpinEditText = (PinEntryEditText) findViewById(R.id.txt_pin_entry_confirm);
        mPIN();
    }

    private void mPIN() {


        final PinEntryEditText pinEntry = findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(@NonNull CharSequence str) {
                    mPin = str.toString();
                }
            });
        }

        final PinEntryEditText pinEntry2 = findViewById(R.id.txt_pin_entry_confirm);
        if (pinEntry2 != null) {
            pinEntry2.setAnimateText(true);
            pinEntry2.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(@NonNull CharSequence str) {
                    confirmMpin = str.toString();
                    if (confirmMpin.equalsIgnoreCase(mPin)) {

                        // Toast.makeText(MpinActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyMpin(), confirmMpin, MpinActivity.this);

                    } else {
                        pinEntry2.setError(true);
                        Toast.makeText(MpinActivity.this, getString(R.string.toast_FAIL), Toast.LENGTH_SHORT).show();
                        pinEntry2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pinEntry2.setText(null);
                                confirmMpin = null;
                            }
                        }, 1000);
                    }
                }
            });
        }

        btnMpinProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language_id = "", logout_days = "", server_date_time = "", state_code = "", mobile_number = "", state_short_name = "", pvtg = "N";
                if ((mPin != null && confirmMpin != null) && (mPin.equalsIgnoreCase(confirmMpin))) {
                    JSONObject mpinFileObject = new JSONObject();
                    try {
                        /*login_id
                        password

                        language_id
                        logout_days
                        server_date_time
                        state_code
                        mobile_number
                        state_short_name
                        pvtg*/
                        for (LoginInfoData loginInfoData : new AadharAccountActivity().getLoginInfo()) {
                            language_id = loginInfoData.getLanguageId();
                            logout_days = loginInfoData.getLogOutDays();
                            server_date_time = loginInfoData.getServerDateTime();
                            state_code = loginInfoData.getState_code();
                            mobile_number = loginInfoData.getMobileNumber();
                            state_short_name = loginInfoData.getState_short_name();
                            pvtg = loginInfoData.getPvgtStatus();
                        }
                        mpinFileObject.accumulate("login_id", PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), MpinActivity.this));
                        mpinFileObject.accumulate("mpin", mPin);
                        mpinFileObject.accumulate("password", PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginPassword(), MpinActivity.this));
                        mpinFileObject.accumulate("block_code", PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyLoginBlockCode(), MpinActivity.this));
                        mpinFileObject.accumulate("block_name", PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefBlockName(), MpinActivity.this));
                        mpinFileObject.accumulate("language_id", language_id);
                        mpinFileObject.accumulate("logout_days", logout_days);
                        mpinFileObject.accumulate("server_date_time", server_date_time);
                        mpinFileObject.accumulate("state_code", state_code);
                        mpinFileObject.accumulate("mobile_number", mobile_number);
                        mpinFileObject.accumulate("state_short_name", state_short_name);
                        mpinFileObject.accumulate("pvtg", pvtg);

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            try {
                                AppUtils.getInstance().showLog("responseJSON" + mpinFileObject.toString(), LoginActivity.class);
                                FileUtility.getInstance().createFileInMemory(FileManager.getInstance().getMpinFilePath(), AppConstant.MPIN_FILE_NAME, new Cryptography().encrypt(mpinFileObject.toString()));
                            } catch (Exception e) {
                                AppUtils.getInstance().showLog("mpinFileObjectEncryptExp" + e, LoginActivity.class);
                            }
                        }
                        Toast.makeText(MpinActivity.this, getString(R.string.Mpin_ceated_successfully), Toast.LENGTH_SHORT).show();
                        AppUtils.getInstance().makeIntent(MpinActivity.this, HomeActivity.class, true);

                    } catch (JSONException e) {
                        AppUtils.getInstance().showLog("MpinFileJsonObjectExp" + e, MpinActivity.class);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.mpin_creation), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (exit == 0) {
            Toast.makeText(MpinActivity.this, getString(R.string.toast_exit_app), Toast.LENGTH_LONG).show();
            exit += 1;
        } else {
            super.onBackPressed();
            System.exit(0);
        }

    }
}
