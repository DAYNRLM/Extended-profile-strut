package com.example.aadharscanner.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.AadharAccountActivity;
import com.example.aadharscanner.activites.AddShgMemberActivity;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.activites.LoginActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.adapters.ShgMemberListAdapter;
import com.example.aadharscanner.database.AadharDetailSyncDataDao;
import com.example.aadharscanner.database.AccountDetailSyncData;
import com.example.aadharscanner.database.AccountDetailSyncDataDao;
import com.example.aadharscanner.database.KycDocSyncDataDao;
import com.example.aadharscanner.database.ShgInActivSyncDataDao;
import com.example.aadharscanner.database.ShgInactivationSyncDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncDataDao;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SyncData;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DashBoardFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {
    private int exit = 0;
    @Nullable
    @BindView(R.id.selectGpListBtn)
    TextView selectGpListBtn;

    @Nullable
    @BindView(R.id.unsynced_aadhar)
    TextView unsynced_aadhar;

    @Nullable
    @BindView(R.id.unsynced_bank)
    TextView unsynced_bank;

    @Nullable
    @BindView(R.id.unsynced_add_member)
    TextView unsynced_add_member;

    @Nullable
    @BindView(R.id.unsynced_kyc_doc)
    TextView unsynced_kyc_doc;

    @Nullable
    @BindView(R.id.unsynced_modified_member)
    TextView unsynced_modified_member;

    @Nullable
    @BindView(R.id.unsynced_modified_shg)
    TextView unsynced_modified_shg;


    @Nullable
    @BindView(R.id.sync_dataTV)
    TextView sync_dataTV;


   /* @BindView(R.id.testImage)
    ImageView testImage;*/

    @NonNull
    public static DashBoardFragment newInstance() {
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        return dashBoardFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.dashboard_fragment;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);

        int unsyncedAadharCount = SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao()
                .queryBuilder().where(AadharDetailSyncDataDao.Properties.AadharSyncStatus.eq("0")).build().list().size();
        AppUtils.getInstance().showLog("unsyncedAadharCount" + unsyncedAadharCount, DashBoardFragment.class);

        if (unsyncedAadharCount == 0) {
            unsynced_aadhar.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_aadhar.setText(getString(R.string.unsync_aadhar_count) + " " + unsyncedAadharCount);
        } else {
            unsynced_aadhar.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_aadhar.setText(getString(R.string.unsync_aadhar_count) + " " + unsyncedAadharCount);
        }

        int unsyncedBankCount = SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao()
                .queryBuilder().where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();

        if (unsyncedBankCount == 0) {
            unsynced_bank.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_bank.setText(getString(R.string.unsync_account_count) + " " + unsyncedBankCount);
        } else {
            unsynced_bank.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_bank.setText(getString(R.string.unsync_account_count) + " " + unsyncedBankCount);
        }

        int unsyncedMembers = SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao()
                .queryBuilder().where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0"))
                .build().list().size();

        if (unsyncedMembers == 0) {
            unsynced_add_member.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_add_member.setText(getString(R.string.unsync_member_count) + " " + unsyncedMembers);
        } else {
            unsynced_add_member.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_add_member.setText(getString(R.string.unsync_member_count) + " " + unsyncedMembers);
        }

        int unsyncedKycDoc = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0"))
                .build().list().size();

        if (unsyncedKycDoc == 0) {
            unsynced_kyc_doc.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_kyc_doc.setText(getString(R.string.unsync_kycdoc_count) + " " + unsyncedKycDoc);
        } else {
            unsynced_kyc_doc.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_kyc_doc.setText(getString(R.string.unsync_kycdoc_count) + " " + unsyncedKycDoc);
        }

        int memberModified = SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao()
                .queryBuilder().where(ShgInActivSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();

        if (memberModified == 0) {

            unsynced_modified_member.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_modified_member.setText(getString(R.string.unsyncedMembers) + " " + memberModified);
        } else {
            unsynced_modified_member.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_modified_member.setText(getString(R.string.unsyncedMembers) + " " + memberModified);
        }

        int shgInactvsnUnsyncCount = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().queryBuilder()
                .where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();


        if (shgInactvsnUnsyncCount == 0) {

            unsynced_modified_shg.setTextColor(getResources().getColor(R.color.colorGreen));
            unsynced_modified_shg.setText(getString(R.string.unsyncedshgs) + " " + shgInactvsnUnsyncCount);
        } else {
            unsynced_modified_shg.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_modified_shg.setText(getString(R.string.unsyncedshgs) + " " + shgInactvsnUnsyncCount);
        }

        AppUtils.getInstance().showLog("unsyncedAadharCount=" + unsyncedAadharCount + ",unsyncedBankCount=" + unsyncedBankCount
                + ",unsyncedMembers=" + unsyncedMembers + ",unsyncedKycDoc=" + unsyncedKycDoc + ",memberModified=" + memberModified
                + ",shgInactvsnUnsyncCount=" + shgInactvsnUnsyncCount, DashBoardFragment.class);

        if (unsyncedAadharCount == 0 && unsyncedBankCount == 0 && unsyncedMembers == 0
                && unsyncedKycDoc == 0 && memberModified == 0 && shgInactvsnUnsyncCount == 0)
            sync_dataTV.setVisibility(View.GONE);
        //Glide.with(this).asGif().load(R.drawable.green_check).into(testImage);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.sync_dataTV)
    public void syncData() {

        // Toast.makeText(getContext().getApplicationContext(),"App is not live.",Toast.LENGTH_LONG).show();
        if (AppUtils.isInternetOn(getContext())) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getContext().getResources().getString(R.string.loading_heavy));
            progressDialog.setCancelable(false);
            progressDialog.show();
            SyncData.getInstance().syncData(getContext());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String AADHAR_API_VOLLEY_ERROR = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getAadharApiVolleyError(), getContext());
                    String BANK_ACC_API_VOLLEY_ERROR = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getBankAccApiVolleyError(), getContext());
                    String ADD_MEMBER_API_VOLLEY_ERROR = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getAddMemberApiVolleyError(), getContext());
                    String DUPLICATE_API_VOLLEY_ERROR = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getDuplicateApiVolleyError(), getContext());
                    String kycDocVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getKycDocApiVolleyError(), getContext());
                    String shgInactiveVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getShgInactivationApiVolleyError(), getContext());
                    String memberInactiveVolleyError = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), getContext());

                    progressDialog.dismiss();

                    if (AADHAR_API_VOLLEY_ERROR.equalsIgnoreCase(AppConstant.AADHAR_API_VOLLEY_ERROR)
                            || BANK_ACC_API_VOLLEY_ERROR.equalsIgnoreCase(AppConstant.BANK_ACC_API_VOLLEY_ERROR)
                            || ADD_MEMBER_API_VOLLEY_ERROR.equalsIgnoreCase(AppConstant.ADD_MEMBER_API_VOLLEY_ERROR)
                            || DUPLICATE_API_VOLLEY_ERROR.equalsIgnoreCase(AppConstant.DUPLICATE_API_VOLLEY_ERROR)
                            || kycDocVolleyError.equalsIgnoreCase(AppConstant.KYC_DOC_API_VOLLEY_ERROR)
                            || shgInactiveVolleyError.equalsIgnoreCase(AppConstant.SHG_INACTIVATION_API_VOLLEY_ERROR)
                            || memberInactiveVolleyError.contentEquals(AppConstant.MEMBER_INACTIVATION_API_VOLLEY_ERROR)) {

                        if (memberInactiveVolleyError.contentEquals(AppConstant.MEMBER_INACTIVATION_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.Member_INACTIVE_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);

                        }

                        if (shgInactiveVolleyError.contentEquals(AppConstant.SHG_INACTIVATION_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getShgInactivationApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.SHG_INACTIVE_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);

                        }

                        if (kycDocVolleyError.contentEquals(AppConstant.KYC_DOC_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getKycDocApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.KYC_DOC_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);

                        }

                        if (DUPLICATE_API_VOLLEY_ERROR.contentEquals(AppConstant.DUPLICATE_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getDuplicateApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.server_error_msg),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            AppUtils.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance()
                                                    , DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
                                        }
                                    }, false);

                        }
                        if (AADHAR_API_VOLLEY_ERROR.contentEquals(AppConstant.AADHAR_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getAadharApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.AADHAR_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);

                        }
                        if (BANK_ACC_API_VOLLEY_ERROR.contentEquals(AppConstant.BANK_ACC_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getBankAccApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.BANK_ACC_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);

                        }
                        if (ADD_MEMBER_API_VOLLEY_ERROR.contentEquals(AppConstant.ADD_MEMBER_API_VOLLEY_ERROR)) {
                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getAddMemberApiVolleyError(), getContext());
                            DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.ADD_MEMBER_API_VOLLEY_ERROR),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, true);
                        }

                    } else {
                        AppUtils.getInstance().replaceFragment(getFragmentManager(), new DashBoardFragment(), DashBoardFragment.class.getSimpleName()
                                , false, R.id.fragmentContainer);
                        DialogFactory.getInstance().showAlertDialog(getContext(), 0, getString(R.string.info), getString(R.string.data_sync_msg),
                                getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                        /*AppUtils.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance()
                                                , DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);*/
                                        Intent intent=new Intent(getContext(), HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(intent);
                                        dialog.dismiss();

                                    }
                                }, false);
                    }
                }
            }, 40000);
        } else {
            DialogFactory.getInstance().showMultipleDialog(getContext(), 0, AppConstant.NO_INTERNET
                    , getString(R.string.no_internet_dialog), getString(R.string.ok)
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, null, null, false);
        }

    }

    @OnClick(R.id.selectGpListBtn)
    public void goToListOfSHG() {
        //Toast.makeText(getContext(),"hii done",Toast.LENGTH_SHORT).show();
        AppUtils.getInstance().replaceFragment(getFragmentManager(), SelectLocationFragment.newInstance(),
                SelectLocationFragment.class.getSimpleName(), true, R.id.fragmentContainer);
    }

    @Override
    public void doBack() {
        if (exit == 0) {
            Toast.makeText(getContext().getApplicationContext(), getString(R.string.toast_exit_app), Toast.LENGTH_LONG).show();
            exit += 1;
        } else
            getActivity().finish();
        // System.exit(0);

    }

}
