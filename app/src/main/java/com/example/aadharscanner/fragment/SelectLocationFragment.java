package com.example.aadharscanner.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.AadharAccountActivity;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.activites.LoginActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.database.GpsData;
import com.example.aadharscanner.database.GpsDataDao;
import com.example.aadharscanner.database.ShgDetailData;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.VillageData;
import com.example.aadharscanner.database.VillageDataDao;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.CustomJsonArrayRequest;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SingletonVolley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LinconBhalla on 27/5/2020
 */

public class SelectLocationFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {
    @Nullable
    @BindView(R.id.goToNextBtn)
    TextView goToShgList;

    @Nullable
    @BindView(R.id.blockNameTv)
    TextView blockNameTv;

    @Nullable
    @BindView(R.id.totalShgTv)
    TextView totalShgTv;

    @Nullable
    @BindView(R.id.totalShgMemberTv)
    TextView totalShgMemberTv;

    @Nullable
    @BindView(R.id.aadharLinkedTv)
    TextView aadharLinkedTv;

    @Nullable
    @BindView(R.id.aadharPendingTv)
    TextView aadharPendingTv;

    @Nullable
    @BindView(R.id.totalAccountLinked)
    TextView totalAccountLinkedTv;

    @Nullable
    @BindView(R.id.spinner_selectGP)
    MaterialBetterSpinner spinner_selectGP;

    @Nullable
    @BindView(R.id.spinner_selectVillage)
    MaterialBetterSpinner spinner_selectVillage;

    @Nullable
    @BindView(R.id.detailsLL)
    LinearLayout detailsLL;


    //array adapter set on spinner for gp, block and village
    @Nullable
    ArrayAdapter<String> gramPanchyatListDataItemArrayAdapter;
    @Nullable
    ArrayAdapter<String> villageListDataItemArrayAdapter;

    //list for vilage block gp
    private List<GpsData> gpListDataItemList;
    private List<VillageData> villageListDataItemList;

    //string array for set on adapter block village gp
    private String[] gramPanchytNameList;
    private String[] villageNameList;

    //get block gp and volage code click on item on spinner
    private String gpCode = "";
    private String villageCode = "";
    @NonNull
    private String gpNameF = "";
    @NonNull
    private String villageNameF = "";

    private String blockCode = "";
    private String blockName = "";
    @Nullable
    private ProgressDialog progressDialog;

    String loginTime;
    @NonNull
    public static SelectLocationFragment newInstance() {
        SelectLocationFragment selectLocationFragment = new SelectLocationFragment();
        return selectLocationFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.select_location_fragment;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);

        loginTime= PreferenceFactory.getInstance().getSharedPrefrencesData("loginTime", getContext());
        AppUtils.getInstance().showLog("loginTimeSelectLocationFragment"+loginTime,SelectLocationFragment.class);
        getBlockData();
        blockNameTv.setText(blockName);
        spinner_selectVillage.setFocusableInTouchMode(false);
        bindGpData();
        setListner();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }

    private void initializeList() {
        gpListDataItemList = new ArrayList<>();
        villageListDataItemList = new ArrayList<>();
    }

    private void setListner() {
        spinner_selectGP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearFocus(1);
                Animation animation = AnimationUtils.loadAnimation(getContext(),
                        R.anim.slide_up);
                detailsLL.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        detailsLL.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                gpNameF = spinner_selectGP.getText().toString();
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefGpName(), gpNameF, getContext());
                gpCode = getGpCode(gpNameF);
                fillVillageList();
                if (villageListDataItemList.size() == 0) {
                    DialogFactory.getInstance().showErrorAlertDialog(getContext(), getContext().getString(R.string.info)
                            , getContext().getString(R.string.no_village)
                                    + " " + gpNameF + " " + getContext().getString(R.string.gp),
                            getContext().getString(R.string.ok));
                } else {
                    bindVillageData();
                }

            }
        });

        spinner_selectVillage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                villageNameF = spinner_selectVillage.getText().toString();
                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefVillageName(), villageNameF, getContext());
                villageCode = getVillageCode(villageNameF);
                PreferenceFactory.getInstance().saveSharedPrefrecesData("villageCode", villageCode, getContext());
                AppUtils.getInstance().showLog("villageCode" + villageCode, SelectLocationFragment.class);
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
                progressDialog.setCancelable(false);
                progressDialog.show();

                // load data from api if data is not exist in local database
                checkIsVilalgeDataInLocal();

                /***set animation by lincon***/
                detailsLL.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getContext(),
                        R.anim.slide_down);
                detailsLL.startAnimation(animation);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkIsVilalgeDataInLocal() {
        List<ShgDetailData> shgDetailDataItemList = SplashScreen.getInstance().getDaoSession()
                .getShgDetailDataDao().queryBuilder().where(ShgDetailDataDao.Properties
                        .VillageCode.eq(villageCode)).build().list();
        if (shgDetailDataItemList.size() == 0) {
            if (NetworkFactory.isInternetOn(getContext())) {
                AppUtils.getInstance().showLog("dataisnotpresentinlocaldb=" + shgDetailDataItemList.size(), SelectLocationFragment.class);
                dataFromServer();
            } else {
                progressDialog.dismiss();
                DialogFactory.getInstance().showMultipleDialog(getContext(), 0, AppConstant.NO_INTERNET
                        , getString(R.string.no_internet_dialog), getString(R.string.ok)
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, null, null, false);
            }
        } else {
            showDetailOnLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dataFromServer() {
        String lgId = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), getContext());
        // String memberMasterUrl = AppConstant.MEMBER_MASTER;
        JSONArray jsonArray = new JSONArray();
        JSONObject detailsOfVillage = new JSONObject();
        try {
            detailsOfVillage.accumulate("login_id", lgId);
            detailsOfVillage.accumulate("village_code", villageCode);
            detailsOfVillage.accumulate("imei_no", new LoginActivity().getIMEINo1());
            detailsOfVillage.accumulate("device_name", new LoginActivity().getDeviceInfo());
            detailsOfVillage.accumulate("app_login_time",loginTime);
            String[] location = new LoginActivity().getCoordinateLL(getContext()).split(",");
            detailsOfVillage.accumulate("location_coordinate", location[0] + "," + location[1]);
            jsonArray.put(detailsOfVillage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtils.getInstance().showLog("shgmemrequestObject" + jsonArray, SelectLocationFragment.class);
        JSONObject requestObject = AppUtils.getInstance().wantToEncryptOldKey(jsonArray);

        AppUtils.getInstance().showLog("shgmemrequestObjectEncrypted" + requestObject, SelectLocationFragment.class);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstant.MEMBER_MASTER, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(@NonNull JSONObject response) {
                progressDialog.dismiss();
                AppUtils.getInstance().showLog("shgMemDataresponseencrypt" + response, SelectLocationFragment.class);
                try {
                    Cryptography cryptography = new Cryptography();
                    String decryptedResponse = cryptography.decrypt(response.getString("data"));
                    AppUtils.getInstance().showLog("shgMemDataresponsedecrypt" + decryptedResponse, SelectLocationFragment.class);
                    JSONArray jsonArray1 = new JSONArray(decryptedResponse);

                    String str = "";
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray1.getJSONObject(i);
                            if (jsonObject.has("status")) {
                                str = jsonObject.getString("status");
                                AppUtils.getInstance().showLog("status" + str, SelectLocationFragment.class);
                                if (str.equalsIgnoreCase("Invalid login")){

                                    Toast.makeText(getContext(),getString(R.string.not_valid),Toast.LENGTH_LONG).show();

                                  /*  DialogFactory.getInstance().showErrorAlertDialog(getContext(),

                                            getString(R.string.info), getString(R.string.not_valid), getString(R.string.ok));*/
                                }
                            } else {
                                str = "";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (str.equalsIgnoreCase("Shg Not Found !!!")) {
                        DialogFactory.getInstance().showAlertDialog(getContext(), 1, "",
                                getContext().getResources().getString(R.string.no_shg), "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                        // villageCode="";
                                        dialog.dismiss();
                                    }
                                }, "", false);

                    }else {
                        parseShgMembersData(jsonArray1);
                        showDetailOnLayout();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppUtils.getInstance().showLog("Member data error " +error.networkResponse.statusCode+" / " +error, SelectLocationFragment.class);
                progressDialog.dismiss();
                DialogFactory.getInstance().showErrorAlertDialog(getContext(),

                        getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "OK");
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                AppUtils.getInstance().showLog("MemberdataerrorOnRetryPolicy" + error, LoginActivity.class);
                progressDialog.dismiss();
            /*    DialogFactory.getInstance().showErrorAlertDialog(getContext(),

                        getString(R.string.SERVER_ERROR_TITLE), getString(R.string.SERVER_ERROR_MESSAGE), "OK");*/
            }
        });
        SingletonVolley.getInstance(getContext().getApplicationContext()).getRequestQueue().getCache().clear();

        SingletonVolley.getInstance(getContext().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void showDetailOnLayout() {
        progressDialog.dismiss();

        List<ShgDetailData> shgDetailDataItemList = SplashScreen.getInstance().getDaoSession()
                .getShgDetailDataDao().queryBuilder().where(ShgDetailDataDao.Properties
                        .VillageCode.eq(villageCode)).build().list();
        AppUtils.getInstance().showLog("dataPresentInDataBase...", SelectLocationFragment.class);
        ArrayList<Integer> totalAadhrLinked = new ArrayList<>();
        ArrayList<Integer> totalAccountLinked = new ArrayList<>();


        List<ShgMemberDetailsData> shgMemberDetailsDataDaosDataItems = SplashScreen.getInstance().daoSession
                .getShgMemberDetailsDataDao().queryBuilder()
                .where(ShgMemberDetailsDataDao.Properties.VillageCode.eq(villageCode)).build().list();

        int total_shg = shgDetailDataItemList.size();
        int total_member = shgMemberDetailsDataDaosDataItems.size();
        AppUtils.getInstance().showLog("totalShgAndMember" + total_shg + "---" + total_member, SelectLocationFragment.class);

        for (int i = 0; i < shgMemberDetailsDataDaosDataItems.size(); i++) {
            if (shgMemberDetailsDataDaosDataItems.get(i).getShgMemberAadharStatus()
                    .equalsIgnoreCase("y")) {
                totalAadhrLinked.add(i);
            } else if (shgMemberDetailsDataDaosDataItems.get(i)
                    .getSggMemberAccountStatus().equalsIgnoreCase("0")) {
                totalAccountLinked.add(i);
            }

            progressDialog.dismiss();
        }

        AppUtils.getInstance().showLog("totalAadhrLinked:-" + totalAadhrLinked.size(), SelectLocationFragment.class);
        int pendin = shgMemberDetailsDataDaosDataItems.size() - totalAadhrLinked.size();
        AppUtils.getInstance().showLog("pendingAadhrLinked:-" + pendin, SelectLocationFragment.class);
        AppUtils.getInstance().showLog("totalAccountttLinked:-" + totalAccountLinked.size(), SelectLocationFragment.class);
        int pendinA = shgMemberDetailsDataDaosDataItems.size() - totalAccountLinked.size();
        AppUtils.getInstance().showLog("pendingAccountttLinked:-" + pendinA, SelectLocationFragment.class);

        totalShgTv.setText("" + total_shg);
        totalShgMemberTv.setText("" + total_member);
        aadharLinkedTv.setText("" + totalAadhrLinked.size());
        aadharPendingTv.setText("" + pendin);
        totalAccountLinkedTv.setText("" + totalAccountLinked.size());
    }

    //parse shg members data
    private void parseShgMembersData(@NonNull JSONArray shgMembersArray) {
        ShgDetailData shgDetailData = new ShgDetailData();
        ShgMemberDetailsData shgMemberDetailsData = new ShgMemberDetailsData();

        try {
            AppUtils.getInstance().showLog("shg_Detail_Array.length()" + shgMembersArray.length(), SelectLocationFragment.class);
            for (int i = 0; i < shgMembersArray.length(); i++) {
                JSONObject shg_Details_Object = shgMembersArray.getJSONObject(i);

                shgDetailData.setVillageCode(villageCode);
                shgDetailData.setId(null);

                String shg_code = shg_Details_Object.getString("shg_code");
                shgDetailData.setShgCode(shg_code);
                String entity_code = shg_Details_Object.getString("entity_code");
                shgDetailData.setShgEntityCode(entity_code);
                String group_name = shg_Details_Object.getString("group_name");
                shgDetailData.setShgGroupName(group_name);
                shgDetailData.setShgActInactStatus(shg_Details_Object.getString("shg_active"));
                shgDetailData.setShgCurrentStatus(shg_Details_Object.getString("shg_current_status_inactive"));
                shgDetailData.setShgMemberCount(String.valueOf(shg_Details_Object.getInt("shg_mem_count")));

                SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().insert(shgDetailData);

                if (shg_Details_Object.getString("shg_active").equalsIgnoreCase("Y")) {
                    if (shg_Details_Object.has("Member_detail")) {

                        JSONArray shg_Member_Array = shg_Details_Object.getJSONArray("Member_detail");
                        for (int shgM = 0; shgM < shg_Member_Array.length(); shgM++) {
                            JSONObject shg_Member_Object = shg_Member_Array.getJSONObject(shgM);

                            String shg_m_code = shg_Member_Object.getString("shg_code");
                            String shg_member_code = shg_Member_Object.getString("shg_member_code");
                            String member_name = shg_Member_Object.getString("member_name");
                            String shg_member_entity_code = shg_Member_Object.getString("entity_code");
                            String aadhar_status = shg_Member_Object.getString("aadhar_status");
                            String kycStatus = shg_Member_Object.getString("kyc_status");
                            String leadership = shg_Member_Object.getString("leader");

                            String belonging_name = shg_Member_Object.getString("belonging_name");
                            String dob = shg_Member_Object.getString("dob");
                            String gender = shg_Member_Object.getString("gender");
                            String kycDocId = shg_Member_Object.getString("kyc_doc_id");
                            String member_update_current_status = shg_Member_Object.getString("member_update_current_status");

                            /*belonging_name
                             * dob
                             * gender
                             **/

                            shgMemberDetailsData.setVillageCode(villageCode);
                            shgMemberDetailsData.setShgCode(shg_m_code);
                            shgMemberDetailsData.setShgMemberCode(shg_member_code);
                            shgMemberDetailsData.setShgMemberName(member_name);
                            shgMemberDetailsData.setShgMemberEntityCode(shg_member_entity_code);
                            shgMemberDetailsData.setShgMemberAadharStatus(aadhar_status);
                            shgMemberDetailsData.setKycStatus(kycStatus);
                            shgMemberDetailsData.setSyncStatus("0");
                            /*active status*/
                            shgMemberDetailsData.setActiveStatus(shg_Member_Object.getString("active_details"));
                            shgMemberDetailsData.setMemberCurrentStatus(shg_Member_Object.getString("member_current_status_inactive"));
                            shgMemberDetailsData.setMemAadharCurrentStatus(shg_Member_Object.getString("member_current_status_aadhaar"));
                            shgMemberDetailsData.setMemBankAccCurrentStatus(shg_Member_Object.getString("member_current_status_bank"));
                            shgMemberDetailsData.setId(null);
                            shgMemberDetailsData.setBelonging_name(belonging_name);
                            shgMemberDetailsData.setDob(dob);
                            shgMemberDetailsData.setGender(gender);
                            shgMemberDetailsData.setKycDocId(kycDocId);
                            shgMemberDetailsData.setLeadership(leadership);
                            shgMemberDetailsData.setUpdateMemberCurrentStatus(member_update_current_status);


                            JSONArray bank_detail_array = shg_Member_Object.getJSONArray("bank_detail");
                            for (int smb = 0; smb < bank_detail_array.length(); smb++) {
                                JSONObject shg_Bank_Detail_Object = bank_detail_array.getJSONObject(smb);
                                String acc_details = shg_Bank_Detail_Object.getString("acc_details");
                                shgMemberDetailsData.setSggMemberAccountStatus(acc_details);

                                SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().insert(shgMemberDetailsData);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

            AppUtils.getInstance().showLog("jsonparse" + e, SelectLocationFragment.class);
        }
    }

    private String getVillageCode(@NonNull String villageNameF) {
        String villgeCode = "";
        for (int i = 0; i < villageListDataItemList.size(); i++) {
            if (villageNameF.equalsIgnoreCase(villageListDataItemList.get(i).getVillageName())) {
                villgeCode = ((villageListDataItemList.get(i).getVillageCode()));
            }
        }
        return villgeCode;
    }

    private void bindVillageData() {
        villageNameList = new String[villageListDataItemList.size()];
        for (int i = 0; i < villageListDataItemList.size(); i++) {
            villageNameList[i] = villageListDataItemList.get(i).getVillageName();
        }
        villageListDataItemArrayAdapter = new ArrayAdapter<String>(getActivity()
                , R.layout.spinner_textview, villageNameList);
        spinner_selectVillage.setAdapter(villageListDataItemArrayAdapter);
    }

    private void fillVillageList() {
        villageListDataItemList = SplashScreen.getInstance().getDaoSession().getVillageDataDao()
                .queryBuilder().where(VillageDataDao.Properties.GpCode.eq(gpCode)).build().list();
    }

    private String getGpCode(@NonNull String gpNameF) {
        String gpCode = "";
        for (int i = 0; i < gpListDataItemList.size(); i++) {
            if (gpNameF.equalsIgnoreCase(gpListDataItemList.get(i).getGpName())) {
                gpCode = ((gpListDataItemList.get(i).getGpCode()));
            }
        }
        return gpCode;
    }


    private void clearFocus(int i) {
        switch (i) {
            case 1:
                villageCode = "";
                villageNameF = "";
                spinner_selectVillage.setFocusableInTouchMode(false);
                villageNameList = new String[0];
                villageListDataItemList.clear();
                spinner_selectVillage.setText("");
                Arrays.sort(villageNameList);
                ArrayAdapter<String> villageListDataItemArrayAdapter1 = new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_textview, villageNameList);
                spinner_selectVillage.setAdapter(villageListDataItemArrayAdapter1);

                break;
            case 2:
               /* villageCode = "";
                villageNameF="";
                selectVillageSpinner.setFocusableInTouchMode(false);
                trainingErrorRelativelayout.setVisibility(View.GONE);
                villageNameList = new String[0];
                villageListDataItemList.clear();
                selectVillageSpinner.setText("");
                ArrayAdapter<String> villageListDataItemArrayAdapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_textview, villageNameList);
                selectVillageSpinner.setAdapter(villageListDataItemArrayAdapter2);
                break;*/
        }
    }

    private void getBlockData() {
        blockName = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao()
                .queryBuilder().limit(1).build().unique().getBlockname();
        blockCode = SplashScreen.getInstance().getDaoSession().getBlockDaoDataDao()
                .queryBuilder().limit(1).build().unique().getBlockCode();
    }

    private void bindGpData() {
        gpListDataItemList = SplashScreen.getInstance().daoSession.getGpsDataDao().queryBuilder()
                .where(GpsDataDao.Properties.BlockCode.eq(blockCode)).build().list();
        //sort list based on alphabt
        gramPanchytNameList = new String[gpListDataItemList.size()];
        for (int i = 0; i < gpListDataItemList.size(); i++) {
            gramPanchytNameList[i] = gpListDataItemList.get(i).getGpName();
        }
        Arrays.sort(gramPanchytNameList);
        gramPanchyatListDataItemArrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_textview, gramPanchytNameList);
        spinner_selectGP.setAdapter(gramPanchyatListDataItemArrayAdapter);
    }

    @OnClick(R.id.goToNextBtn)
    void getDateFragment() {

        if (villageCode.equalsIgnoreCase("")
                || gpCode.equalsIgnoreCase("")
                || blockCode.equalsIgnoreCase("")) {

            Toast.makeText(getContext(), getString(R.string.please_select_the_location_first), Toast.LENGTH_SHORT).show();
        } else {
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager
                    .getPrefKeyLoginBlockCode(), blockCode, getContext());
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager
                    .getPrefKeyGpCode(), gpCode, getContext());
            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager
                    .getPrefKeyVillageCode(), villageCode, getContext());

            List<ShgDetailData> shgDetailDataItemList = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao()
                    .queryBuilder().where(ShgDetailDataDao.Properties.VillageCode.eq(villageCode)).build().list();
            if (shgDetailDataItemList.size() != 0) {
                AppUtils.getInstance().replaceFragment(getFragmentManager(), ShgMembersFragment.newInstance(),
                        SelectLocationFragment.class.getSimpleName(), true, R.id.fragmentContainer);
            } else {
                DialogFactory.getInstance().showAlertDialog(getContext(), 0, getContext().getResources().getString(R.string.no_data_found)
                        , getContext().getResources().getString(R.string.communicate_to_server),
                        getContext().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, null, null, true);
            }
        }
    }


    @Override
    public void doBack() {
        AppUtils.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance(), DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
