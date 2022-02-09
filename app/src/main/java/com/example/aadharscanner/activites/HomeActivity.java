package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aadharscanner.R;
import com.example.aadharscanner.adapters.ShgMemberListAdapter;
import com.example.aadharscanner.database.DaoSession;
import com.example.aadharscanner.fragment.ChangeLanguageFragment;
import com.example.aadharscanner.fragment.ContactUs;
import com.example.aadharscanner.fragment.DashBoardFragment;
import com.example.aadharscanner.fragment.SelectLocationFragment;
import com.example.aadharscanner.fragment.ShgFragment;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.DateFactory;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SyncData;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HomeActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {
    public Toolbar mToolbar;
    public NavigationView navigationView;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private TextView tvToolbarTitle, tvUserName, tvUserMobile, tvAppVersion;
    private boolean mChangeFragment;
    private int selectedItem;
    public static Context context;


    private HomeActivity.OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
/*       String coordinates= SyncData.getInstance().getMemberLocCordnt(context,"247554","imliya deoband saharanpur");
       AppUtils.getInstance().showLog("coordinatesatHome"+coordinates,HomeActivity.class);
        Toast.makeText(context,"coordinatesatHome"+coordinates,Toast.LENGTH_LONG).show();*/
        String shgMemberStatus = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefKeyShgMemberStatus()
                , HomeActivity.this);
        setupToolbar();
        setupNavigationView();
        if (!shgMemberStatus.equalsIgnoreCase("goToMemberList")) {
            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), DashBoardFragment.newInstance()
                    , DashBoardFragment.class.getSimpleName()
                    , true, R.id.fragmentContainer);
        } else {
            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), ShgFragment.newInstance()
                    , ShgFragment.class.getSimpleName()
                    , true, R.id.fragmentContainer);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 30 * 60 * 1000);*/
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.tbTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
    }

    public void setToolBarTitle(String toolBarTitle) {
        tvToolbarTitle.setText(toolBarTitle);
        assert getSupportActionBar() != null;
        getSupportActionBar().show();
    }

    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setScrimColor(Color.parseColor("#99000000"));
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View headerView = navigationView.getHeaderView(0);

        tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        tvUserName.setText(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), HomeActivity.this));
        tvUserMobile = (TextView) headerView.findViewById(R.id.tvUserMobileNumber);
        // tvUserMobile.setText("9812343583");
        tvAppVersion = (TextView) headerView.findViewById(R.id.tvAppVersion);
        tvAppVersion.setText(AppConstant.APP_VERSION);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDrawerClosed(View drawerView) {

                if (mChangeFragment) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    mChangeFragment = false;
                    switch (selectedItem) {
                        case R.id.menu_dashbord:
                            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), DashBoardFragment.newInstance()
                                    , DashBoardFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                            break;

                        case R.id.change_language:
                            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), ChangeLanguageFragment.newInstance()
                                    , ChangeLanguageFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                            break;

                        case R.id.contact_us:
                            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), ContactUs.newInstance()
                                    , ContactUs.class.getSimpleName(), true, R.id.fragmentContainer);
                            break;

                        case R.id.kyc:
                            AppUtils.getInstance().replaceFragment(getSupportFragmentManager(), new SelectLocationFragment()
                                    , SelectLocationFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                            break;
                        case R.id.menu_logout:

                          /*  ProgressDialog progressDialog=DialogFactory.getInstance().showProgressDialog(HomeActivity.this,false);
                            progressDialog.show();*/
                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getLogoutDateTime(), DateFactory.getInstance().getDateTime(), HomeActivity.this);
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefLoginSessionKey(), HomeActivity.this);
                            /*update back-up files*/

                            try {
                                SyncData syncData = SyncData.getInstance();
                                syncData.getLogInfo(context);

                                syncData.updateAadharBackupFile(context);
                                syncData.updateBankAccountBackupFile(context);
                                syncData.updateAddMemberBackUpFile(context);
                                syncData.updateKycDocSyncBackUpFile(context);
                                syncData.updateMemberStatusBackUpFile(context);
                                syncData.updateShgInactivationBackupFile(context);
                                syncData.updateMberUpdateBackupFile(context);
                                ShgMemberListAdapter.updateDesignationFile(context);

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
                            deleteWholeDatabase();
                            /*  progressDialog.dismiss();*/
                            AppUtils.getInstance().makeIntent(HomeActivity.this, LoginActivity.class, true);
                            break;
                    }
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                AppUtils.hideSoftKeyboard(HomeActivity.this);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);

    }

    @Override
    public void onBackStackChanged() {
        int mBackStackCount = getSupportFragmentManager().getBackStackEntryCount();
        String fName = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        AppUtils.getInstance().showLog("fragment manager" + fName, HomeActivity.class);
        /*if (mBackStackCount > 1) {
            mToolbar.setNavigationIcon(null);
            AppUtils.getInstance().showLog("Fragment count:-" + getSupportFragmentManager().getBackStackEntryCount(), HomeActivity.class);
            AppUtils.getInstance().showLog("fragment manager" + getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName(), HomeActivity.class);
        }*/
        if (fName.equalsIgnoreCase("DashBoardFragment")) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        } else {
          /*  actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);*/
            // mToolbar.setNavigationIcon(null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AppUtils.getInstance().showLog("id", HomeActivity.class);
        item.setChecked(!item.isChecked());
        selectedItem = item.getItemId();
        mDrawerLayout.closeDrawers();
        mChangeFragment = true;
        return true;
    }

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (onBackPressedListener != null) {
                onBackPressedListener.doBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void deleteWholeDatabase() {

        SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getBankBranchDetailDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getBankDetailDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getBankTypeDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getGpsDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getVillageDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getWebRequestDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getKycDocDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getMemberInactiveReasonDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getMemberLoanStatusDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getInactiveReasonsDao().deleteAll();   /*shg's inactive resons*/
        SplashScreen.getInstance().getDaoSession().getShgLoanStatusDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().deleteAll();
        SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().deleteAll();

    }
}
