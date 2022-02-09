package com.example.aadharscanner.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aadharscanner.R;
import com.example.aadharscanner.database.AadharDetailSyncDataDao;
import com.example.aadharscanner.database.AccountDetailSyncDataDao;
import com.example.aadharscanner.database.KycDocSyncDataDao;
import com.example.aadharscanner.database.ShgInActivSyncDataDao;
import com.example.aadharscanner.database.ShgInactivationSyncDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncDataDao;
import com.example.aadharscanner.database.WebRequestData;
import com.example.aadharscanner.fragment.DashBoardFragment;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;
import com.example.aadharscanner.utils.SyncData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteVillage extends AppCompatActivity {

    @Nullable
    @BindView(R.id.unsynced_aadharV)
    TextView unsynced_aadhar;

    @Nullable
    @BindView(R.id.unsynced_bankV)
    TextView unsynced_bank;



    @Nullable
    @BindView(R.id.unsynced_add_memberV)
    TextView unsynced_add_member;

    @Nullable
    @BindView(R.id.unsynced_kyc_doc)
    TextView unsynced_kyc_doc;

    @Nullable
    @BindView(R.id.unsynced_modified_shg)
    TextView unsynced_modified_shg;

    @Nullable
    @BindView(R.id.unsynced_modified_memberV)
    TextView unsynced_modified_member;

    @Nullable
    @BindView(R.id.sync_dataTVV)
    TextView sync_dataTV;

    private Context context;

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_village);
        ButterKnife.bind(this);
        context = this;

        int unsyncedAadharCount = SplashScreen.getInstance().getDaoSession().getAadharDetailSyncDataDao()
                .queryBuilder().where(AadharDetailSyncDataDao.Properties.AadharSyncStatus.eq("0")).build().list().size();

        AppUtils.getInstance().showLog("unsyncedAadharCount" + unsyncedAadharCount, DashBoardFragment.class);

        if (unsyncedAadharCount == 0) {
            unsynced_aadhar.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_aadhar.setText(getString(R.string.unsync_aadhar_count) + " " + unsyncedAadharCount);
        } else {
            unsynced_aadhar.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_aadhar.setText(getString(R.string.unsync_aadhar_count) + " " + unsyncedAadharCount);
        }

        int unsyncedBankCount = SplashScreen.getInstance().getDaoSession().getAccountDetailSyncDataDao()
                .queryBuilder().where(AccountDetailSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();

        if (unsyncedBankCount == 0) {
            unsynced_bank.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_bank.setText(getString(R.string.unsync_account_count) + " " + unsyncedBankCount);
        } else {
            unsynced_bank.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_bank.setText(getString(R.string.unsync_account_count) + " " + unsyncedBankCount);
        }

        int unsyncedMembers = SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao()
                .queryBuilder().where(ShgMemberRegistrationSyncDataDao.Properties.SyncStatus.eq("0"))
                .build().list().size();

        if (unsyncedMembers == 0) {
            unsynced_add_member.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_add_member.setText(getString(R.string.unsync_member_count) + " " + unsyncedMembers);
        } else {
            unsynced_add_member.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_add_member.setText(getString(R.string.unsync_member_count) + " " + unsyncedMembers);
        }

        int unsyncedKycDoc = SplashScreen.getInstance().getDaoSession().getKycDocSyncDataDao()
                .queryBuilder().where(KycDocSyncDataDao.Properties.SyncStatus.eq("0"))
                .build().list().size();

        if (unsyncedKycDoc == 0) {
            unsynced_kyc_doc.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_kyc_doc.setText(getString(R.string.unsync_kycdoc_count) + " " + unsyncedKycDoc);
        } else {
            unsynced_kyc_doc.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_kyc_doc.setText(getString(R.string.unsync_kycdoc_count) + " " + unsyncedKycDoc);
        }

        int shgInactvsnUnsyncCount = SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().queryBuilder()
                .where(ShgInactivationSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();

        if (shgInactvsnUnsyncCount == 0) {

            unsynced_modified_shg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_modified_shg.setText(getString(R.string.unsyncedshgs) + " " + shgInactvsnUnsyncCount);
        } else {
            unsynced_modified_shg.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_modified_shg.setText(getString(R.string.unsyncedshgs) + " " + shgInactvsnUnsyncCount);
        }

        int shgModifiedMember = SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao()
                .queryBuilder().where(ShgInActivSyncDataDao.Properties.SyncStatus.eq("0")).build().list().size();

        if (shgModifiedMember == 0) {

            unsynced_modified_member.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            unsynced_modified_member.setText(getString(R.string.unsyncedMembers) + " " + shgModifiedMember);
        } else {
            unsynced_modified_member.setTextColor(getResources().getColor(R.color.colorRed));
            unsynced_modified_member.setText(getString(R.string.unsyncedMembers) + " " + shgModifiedMember);
        }

        AppUtils.getInstance().showLog("unsyncedAadharCount=" + unsyncedAadharCount + ",unsyncedBankCount=" + unsyncedBankCount
                + ",unsyncedMembers=" + unsyncedMembers + ",unsyncedKycDoc=" + unsyncedKycDoc + ",memberModified=" + shgModifiedMember
                + ",shgInactvsnUnsyncCount=" + shgInactvsnUnsyncCount, DeleteVillage.class);

        if (unsyncedAadharCount == 0 && unsyncedBankCount == 0 && unsyncedMembers == 0
                && unsyncedKycDoc == 0 && shgModifiedMember == 0 && shgInactvsnUnsyncCount == 0) {

            sync_dataTV.setVisibility(View.GONE);

            if (NetworkFactory.isInternetOn(this)) {
                ProgressDialog progressDialog = new ProgressDialog(DeleteVillage.this);
                progressDialog.setMessage(getString(R.string.loading_heavy));
                progressDialog.setCancelable(false);
                progressDialog.show();

                String villages = getVillageForDelete();
                if (!villages.isEmpty() || !villages.equalsIgnoreCase("")) {
                    deleteVillRequest(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), DeleteVillage.this)
                            , villages, context, progressDialog);
                }

            } else {

                DialogFactory.getInstance().showMultipleDialog(DeleteVillage.this, 0, AppConstant.NO_INTERNET
                        , getString(R.string.no_internet_dialog), getString(R.string.ok)
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                sync_dataTV.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        }, null, null, false);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.sync_dataTVV)
    public void syncData() {
        if (NetworkFactory.isInternetOn(this)) {
            ProgressDialog progressDialog = new ProgressDialog(DeleteVillage.this);
            progressDialog.setMessage(getString(R.string.loading_heavy));
            progressDialog.setCancelable(false);
            progressDialog.show();

            SyncData.getInstance().syncData(DeleteVillage.this);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String villages = getVillageForDelete();
                    if (!villages.isEmpty() || !villages.equalsIgnoreCase("")) {
                        deleteVillRequest(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), DeleteVillage.this)
                                , villages, context, progressDialog);
                    }
                }
            }, 40000);
        } else {
            DialogFactory.getInstance().showMultipleDialog(DeleteVillage.this, 0, AppConstant.NO_INTERNET
                    , getString(R.string.no_internet_dialog), getString(R.string.ok)
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, null, null, false);
        }
    }

    @NonNull
    private String getVillageForDelete() {
        String villages = "";
        List<WebRequestData> webRequestDataList = SplashScreen.getInstance().getDaoSession().getWebRequestDataDao().queryBuilder()
                .build().list();
        for (WebRequestData webRequestData : webRequestDataList) {
            villages += webRequestData.getVillageCode() + ",";
        }
        AppUtils.getInstance().showLog("villages" + villages, DeleteVillage.class);
        return villages;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private void deleteVillRequest(String userId, String villages, @NonNull Context context, @NonNull ProgressDialog progressDialog) {

        /****************************************************request for post json *********************************************/
        // String JSON_CONFIRM_WEB_REQUEST= AppConstant.HTTP_TYPE+"://"+AppConstant.IP_ADDRESS+"/"+AppConstant.API_TYPE+"/services/sakshamupdate/assign";
        JSONObject deleteVillRequestObject = new JSONObject();
        try {
            deleteVillRequestObject.accumulate("login_id", userId);
            deleteVillRequestObject.accumulate("village_code", AppUtils.getInstance().removeCommaFromLast(villages));
            deleteVillRequestObject.accumulate("imei_no", new LoginActivity().getIMEINo1());
            deleteVillRequestObject.accumulate("device_name", new LoginActivity().getDeviceInfo());
            deleteVillRequestObject.accumulate("location_coordinate", new LoginActivity().getCoordinateLL(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtils.getInstance().showLog("deleteVillRequestObject" + deleteVillRequestObject, DeleteVillage.class);

        deleteVillRequestObject = AppUtils.getInstance().wantToEncrypt(deleteVillRequestObject);
        AppUtils.getInstance().showLog("deleteVillRequestObjectEncrypted" + deleteVillRequestObject, DeleteVillage.class);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.VILLAGE_DELETE_REQUEST, deleteVillRequestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                AppUtils.getInstance().showLog("responsedeletevillage" + response, DashBoardFragment.class);
                response = AppUtils.getInstance().wantToDecrypt(response);
                AppUtils.getInstance().showLog("responseDeleteVillage" + response, DeleteVillage.class);
                if (response.has("status")) {
                    try {
                        String status = response.getString("status");
                        if (status.equalsIgnoreCase("Success")) {

                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefLoginSessionKey(), DeleteVillage.this);
                            Intent intent = new Intent(DeleteVillage.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            DialogFactory.getInstance().showAlertDialog(DeleteVillage.this, R.drawable.ic_launcher_background,
                                    getString(R.string.server_error_dialog),
                                    getString(R.string.sync_data_failed),
                                    getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    DialogFactory.getInstance().showAlertDialog(DeleteVillage.this, R.drawable.ic_launcher_background,
                            getString(R.string.server_error_dialog),
                            getString(R.string.sync_data_failed),
                            getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AppUtils.getInstance().showLog("webRequestServerError" + error, DashBoardFragment.class);
                DialogFactory.getInstance().showErrorAlertDialog(context,
                        getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE),
                        getString(R.string.ok));

            }
        });

        SingletonVolley.getInstance(this.getApplicationContext()).getRequestQueue().getCache().clear();
        SingletonVolley.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}