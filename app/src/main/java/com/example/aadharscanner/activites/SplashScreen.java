package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aadharscanner.R;
import com.example.aadharscanner.database.DaoMaster;
import com.example.aadharscanner.database.DaoSession;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.utils.DateFactory;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.PermissionHelper;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    @Nullable
    private static SplashScreen instance = null;
    public DaoSession daoSession;
    public Context context;

    private boolean checkPermision;

    @Nullable
    public synchronized static SplashScreen getInstance() {
        if (instance == null)
            instance = new SplashScreen();
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getLanguageCode();
        context = getApplicationContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aadharscan0-db");
        Database db = helper.getWritableDb();
        if (db != null) {
            daoSession = new DaoMaster(db).newSession();
            SplashScreen.getInstance().setDaoSession(daoSession);
        }
        AppUtils.getInstance().showLog("should logout: " + checkForLogoutDays(), SplashScreen.class);
        if (checkForLogoutDays()) {
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getLogoutDateTime(), DateFactory.getInstance().getDateTime(), SplashScreen.this);
            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefLoginSessionKey(), SplashScreen.this);
        }

        checkPermision = PermissionHelper.getInstance(SplashScreen.this).checkAndRequestPermissions();
        if (checkPermision) {
            loadNextScreenWithDelay();
        }
        AppUtils.getInstance().showLog("checkPermision" + checkPermision, LoginActivity.class);

    }

    private void getLanguageCode() {
        String getLanguageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLanguageCode(), SplashScreen.this);
        if (getLanguageCode.equalsIgnoreCase("")) {
            getLanguageCode = "en";
        }

        AppUtils.getInstance().setLocale(getLanguageCode, getResources(), SplashScreen.this);
    }


    private void loadNextScreenWithDelay() {
        android.os.Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNextScreen();
            }
        }, 3000);
    }

    private void goToNextScreen() {

        String loginSatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginSessionKey(), SplashScreen.this);
        String Mpin = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyMpin(), SplashScreen.this);
        AppUtils.getInstance().showLog("loginStatus" + loginSatus, SplashScreen.class);
        if (loginSatus == null || loginSatus.equalsIgnoreCase("")) {
            AppUtils.getInstance().makeIntent(SplashScreen.this, LoginActivity.class, true);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

        } else {
            if (Mpin.equalsIgnoreCase("") || Mpin == null) {
                AppUtils.getInstance().makeIntent(SplashScreen.this, MpinActivity.class, true);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            } else {
                AppUtils.getInstance().makeIntent(SplashScreen.this, VerifyMpinActivity.class, true);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.getInstance(SplashScreen.this).requestPermissionResult(PermissionHelper.REQUEST_ID_MULTIPLE_PERMISSIONS, permissions, grantResults);
        checkPermision = true;
        if (checkPermision) {
            loadNextScreenWithDelay();
        } else {
            Toast.makeText(SplashScreen.this, getString(R.string.please_allow_permission), Toast.LENGTH_SHORT).show();
        }
    }


  /*  @Override
    protected void onResume() {
        super.onResume();
        if(!AppUtils.isGPSEnabled(SplashScreen.this)){

            DialogFactory.getInstance().showAlertDialog(SplashScreen.this,
                    R.drawable.ic_launcher_background, getString(R.string.app_name),
                    "GPS is not enabled. Do you want to go to settings menu?",
                    "Go to seeting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }, "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
        }else {
           // loadNextScreenWithDelay();
            Toast.makeText(SplashScreen.this,"Location is on",Toast.LENGTH_SHORT).show();
        }
    }*/

    private boolean checkForLogoutDays() {
        boolean performLogout = false;
        String serverDateTimeandDays = getServerDateFromLDB();
        if (serverDateTimeandDays != null) {
            String[] serverDTAndDs = serverDateTimeandDays.split(",");

            AppUtils.getInstance().showLog("serverDTAndDs[0]" + serverDTAndDs[0] + "...." + serverDTAndDs[1], SplashScreen.class);
            String logoutDate = DateFactory.getInstance().getNextDate(serverDTAndDs[0], Integer.parseInt(serverDTAndDs[1]), "dd-MM-yyyy");
            Date convertedLogOutDate = DateFactory.getInstance().getDateFormate(logoutDate);
            AppUtils.getInstance().showLog("logoutDate" + logoutDate, SplashScreen.class);
            String todayDate = DateFactory.getInstance().getTodayDate();
            Date convertedTodayDate = DateFactory.getInstance().getDateFormate(todayDate);
            AppUtils.getInstance().showLog("todayDate" + todayDate, SplashScreen.class);
            if (convertedTodayDate.compareTo(convertedLogOutDate) >= 0) {
                performLogout = true;
            }
        } else {
            serverDateTimeandDays = "null";
            AppUtils.getInstance().showLog("serverDateTime" + serverDateTimeandDays, SplashScreen.class);
        }
        return performLogout;
    }

    @Nullable
    private String getServerDateFromLDB() {
        List<LoginInfoData> loginInfoDataList = SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().queryBuilder().build().list();
        if (loginInfoDataList.size() != 0) {
            AppUtils.getInstance().showLog("datefromdb" + loginInfoDataList.get(0).getServerDateTime() + "," + loginInfoDataList.get(0).getLogOutDays(), SplashScreen.class);
            return loginInfoDataList.get(0).getServerDateTime() + "," + loginInfoDataList.get(0).getLogOutDays();
        }
        return null;
    }

}

