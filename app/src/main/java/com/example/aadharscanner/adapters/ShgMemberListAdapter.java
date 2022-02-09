package com.example.aadharscanner.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.AadharAccountActivity;
import com.example.aadharscanner.activites.AddShgMemberActivity;
import com.example.aadharscanner.activites.KYC;
import com.example.aadharscanner.activites.LoginActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.database.ChangeDesignationSyncData;
import com.example.aadharscanner.database.InactiveReasons;
import com.example.aadharscanner.database.MemberInactiveReasonData;
import com.example.aadharscanner.database.MemberLoanStatusData;
import com.example.aadharscanner.database.ShgInActivSyncData;
import com.example.aadharscanner.database.ShgInActivSyncDataDao;
import com.example.aadharscanner.database.ShgLoanStatusData;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.UpdatedDesignation;
import com.example.aadharscanner.database.UpdatedDesignationDao;
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
import com.example.aadharscanner.utils.SyncData;
import com.google.android.material.snackbar.Snackbar;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShgMemberListAdapter extends RecyclerView.Adapter<ShgMemberListAdapter.MyViewHolder> {
    List<ShgMemberDetailsData> shgMemberDetailsDataItem;
    Context context;


    private static String shgCode, shgMemberCode;
    private static String pendingStatus;

    ArrayAdapter<String> changeStatusAdapter;
    String status[], reason[];
    ProgressDialog progressDialog;


    private Dialog reasonsDialog;
    private Dialog changeDesignationDialog;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    List<MemberInactiveReasonData> inactiveReasons;
    private HashMap<String, List<MemberLoanStatusData>> expandableListDetail;
    private String selectedReasonId, selectedLoanStatusId;


    public ShgMemberListAdapter(@NonNull List<ShgMemberDetailsData> shgMemberDetailsDataItem, Context context, String shgCode) {
        this.shgMemberDetailsDataItem = shgMemberDetailsDataItem;
        this.context = context;
        this.shgCode = shgCode;
        this.status = new String[shgMemberDetailsDataItem.size()];
        for (int i = 0; i < shgMemberDetailsDataItem.size(); i++) {
            status[i] = shgMemberDetailsDataItem.get(i).getMemberCurrentStatus();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View selectShgListView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shg_member_custom_layout, parent, false);
        return new ShgMemberListAdapter.MyViewHolder(selectShgListView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.shgMemberNameTV.setText(shgMemberDetailsDataItem.get(position).getShgMemberName() + "(" + shgMemberDetailsDataItem.get(position).getShgMemberCode() + ")");
        holder.designationTV.setText(shgMemberDetailsDataItem.get(position).getLeadership());

        String aadharStatus = shgMemberDetailsDataItem.get(position).getShgMemberAadharStatus();
        String aadharCurrentStatus = shgMemberDetailsDataItem.get(position).getMemAadharCurrentStatus();

        String accountStatus = shgMemberDetailsDataItem.get(position).getSggMemberAccountStatus();
        String accountCurrentStatus = shgMemberDetailsDataItem.get(position).getMemBankAccCurrentStatus();

        String kycStatus = shgMemberDetailsDataItem.get(position).getKycStatus();

        String memberActiveStatus = shgMemberDetailsDataItem.get(position).getActiveStatus();
        String memberCurrentStatus = status[position];

        AppUtils.getInstance().showLog("memberActiveStatus" + memberActiveStatus + "memberCurrentStatus" + memberCurrentStatus + "name" + shgMemberDetailsDataItem.get(position).getShgMemberName(), ShgMemberListAdapter.class);

        if (memberActiveStatus.equalsIgnoreCase("Active") && (memberCurrentStatus.equalsIgnoreCase("")
                || memberCurrentStatus.equalsIgnoreCase("A"))) {
            holder.member_statusTV.setText(context.getResources().getString(R.string.status_active));
            holder.member_statusTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else if (memberActiveStatus.equalsIgnoreCase("Active") && memberCurrentStatus.equalsIgnoreCase("P")) {
            holder.member_statusTV.setText(context.getResources().getString(R.string.pending));
            holder.member_statusTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
            holder.change_statusMBS.setVisibility(View.GONE);
        } else if (memberActiveStatus.equalsIgnoreCase("Active") && memberCurrentStatus.equalsIgnoreCase("R")) {
            holder.member_statusTV.setText(context.getResources().getString(R.string.rejected_status));
            holder.member_statusTV.setTextColor(context.getResources().getColor(R.color.color_red));
        } else if (memberActiveStatus.equalsIgnoreCase("Inactive") && memberCurrentStatus.equalsIgnoreCase("A")) {
            holder.member_statusTV.setText(context.getResources().getString(R.string.status_inactive));
            holder.member_statusTV.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.change_statusMBS.setVisibility(View.GONE);
        } else if (memberActiveStatus.equalsIgnoreCase("Inactive") && memberCurrentStatus.equalsIgnoreCase("")) {
            holder.member_statusTV.setText(context.getResources().getString(R.string.status_inactive));
            holder.member_statusTV.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.change_statusMBS.setVisibility(View.GONE);
        }


        if (aadharStatus.equalsIgnoreCase("Y") && (aadharCurrentStatus.equalsIgnoreCase("")
                || aadharCurrentStatus.equalsIgnoreCase("A"))) {
            holder.aadharPendingTV.setText(context.getResources().getString(R.string.verified));
            holder.aadharPendingTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else if (aadharStatus.equalsIgnoreCase("N") && aadharCurrentStatus.equalsIgnoreCase("")) {
            holder.aadharPendingTV.setText(context.getResources().getString(R.string.unverified));
            holder.aadharPendingTV.setTextColor(context.getResources().getColor(R.color.colorRed));
        } else if (aadharStatus.equalsIgnoreCase("N") && aadharCurrentStatus.equalsIgnoreCase("P")) {
            holder.aadharPendingTV.setText(context.getResources().getString(R.string.marked_as_verified));
            holder.aadharPendingTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
        } else if (aadharStatus.equalsIgnoreCase("N") && aadharCurrentStatus.equalsIgnoreCase("R")) {
            holder.aadharPendingTV.setText(context.getResources().getString(R.string.rejected_status));
            holder.aadharPendingTV.setTextColor(context.getResources().getColor(R.color.color_red));
        } else if (aadharStatus.equalsIgnoreCase("N") && aadharCurrentStatus.equalsIgnoreCase("A")) {
            holder.aadharPendingTV.setText(context.getResources().getString(R.string.accepted));
            holder.aadharPendingTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }


        if (accountStatus.equalsIgnoreCase("1") && (accountCurrentStatus.equalsIgnoreCase("")
                || accountCurrentStatus.equalsIgnoreCase("A"))) {
            holder.bankAccPendingTV.setText(context.getResources().getString(R.string.verified));
            holder.bankAccPendingTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else if (accountStatus.equalsIgnoreCase("0") && accountCurrentStatus.equalsIgnoreCase("")) {
            holder.bankAccPendingTV.setText(context.getResources().getString(R.string.unverified));
            holder.bankAccPendingTV.setTextColor(context.getResources().getColor(R.color.colorRed));
        } else if (accountStatus.equalsIgnoreCase("0") && accountCurrentStatus.equalsIgnoreCase("P")) {
            holder.bankAccPendingTV.setText(context.getResources().getString(R.string.marked_as_verified));
            holder.bankAccPendingTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
        } else if (accountStatus.equalsIgnoreCase("0") && accountCurrentStatus.equalsIgnoreCase("R")) {
            holder.bankAccPendingTV.setText(context.getResources().getString(R.string.rejected_status));
            holder.bankAccPendingTV.setTextColor(context.getResources().getColor(R.color.color_red));
        } else if (accountStatus.equalsIgnoreCase("0") && accountCurrentStatus.equalsIgnoreCase("A")) {
            holder.bankAccPendingTV.setText(context.getResources().getString(R.string.accepted));
            holder.bankAccPendingTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }

        if (kycStatus.equalsIgnoreCase("1")) {
            holder.kycnotupdatedIV.setVisibility(View.GONE);
            holder.kycupdatedIV.setVisibility(View.VISIBLE);
            // holder.kyc_BTN.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.kyc_BTN.setText(context.getResources().getString(R.string.kyc_completed));
        }


        if (shgMemberDetailsDataItem.get(position).getUpdateMemberCurrentStatus().equalsIgnoreCase("A")) {
            holder.changeDesignationTV.setVisibility(View.GONE);
            holder.designationCurrentStatusTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.designationCurrentStatusTV.setText(context.getResources().getString(R.string.changed));
            holder.designationCurrentStatusTV.setVisibility(View.VISIBLE);
        } else if (shgMemberDetailsDataItem.get(position).getUpdateMemberCurrentStatus().equalsIgnoreCase("P")) {
            holder.changeDesignationTV.setVisibility(View.GONE);
            holder.designationCurrentStatusTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
            holder.designationCurrentStatusTV.setText(context.getResources().getString(R.string.requested));
            holder.designationCurrentStatusTV.setVisibility(View.VISIBLE);
        } else if (shgMemberDetailsDataItem.get(position).getUpdateMemberCurrentStatus().equalsIgnoreCase("") && getUpdatedDesignationCount(shgCode) >= 3) {
            holder.changeDesignationTV.setVisibility(View.GONE);
            holder.designationCurrentStatusTV.setText(context.getResources().getString(R.string.max_update_done));
            holder.designationCurrentStatusTV.setVisibility(View.VISIBLE);
        } else if (shgMemberDetailsDataItem.get(position).getUpdateMemberCurrentStatus().equalsIgnoreCase("") && getUpdatedDesignationCount(shgCode) < 3)
            holder.changeDesignationTV.setVisibility(View.VISIBLE);

        holder.changeDesignationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*memberActiveStatus*/
                if (memberActiveStatus.equalsIgnoreCase("Active") &&
                        (memberCurrentStatus.equalsIgnoreCase("") || memberCurrentStatus.equalsIgnoreCase("R")
                                || memberCurrentStatus.equalsIgnoreCase("A"))){

                changeDesignationDialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.change_designation_dialoge);


                ListView changeDesignationLV = changeDesignationDialog.findViewById(R.id.change_designationLV);
                String[] designations = new String[]{"President", "Secretary", "Treasurer"};

                ChangeDesignationAdapter changeDesignationAdapter = new ChangeDesignationAdapter(context, designations);
                changeDesignationLV.setAdapter(changeDesignationAdapter);
                changeDesignationDialog.show();


                changeDesignationLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                        String selectedDesignation = (String) parent.getItemAtPosition(p);

                        QueryBuilder<UpdatedDesignation> queryBuilder=SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().queryBuilder();

                        int updatedDesignationCount = queryBuilder.where(UpdatedDesignationDao.Properties.Designation.eq(selectedDesignation)
                                ,queryBuilder.and(UpdatedDesignationDao.Properties.ShgCode.eq(shgCode),UpdatedDesignationDao.Properties.Designation.eq(selectedDesignation))).build().list().size();
                        AppUtils.getInstance().showLog("updatedDesignationCount"+updatedDesignationCount,ShgMemberListAdapter.class);

                        AppUtils.getInstance().showLog("invalidRequest"+selectedDesignation+" to "+shgMemberDetailsDataItem.get(holder.getAdapterPosition()).getLeadership(),ShgMemberListAdapter.class);

                        /*if user is updating from president to secratory or treaseror or member then user have to make president first to another */

                        if (selectedDesignation.equalsIgnoreCase(shgMemberDetailsDataItem.get(position).getLeadership())){
                            showSnacks(view,selectedDesignation+" to "+shgMemberDetailsDataItem.get(holder.getAdapterPosition()).getLeadership()+" "+context.getResources().getString(R.string.invalid_reuest_msg));
                            return;
                        }

                        if (updatedDesignationCount>0){
                            showSnacks(view,selectedDesignation+" "+context.getResources().getString(R.string.error_designation));
                            return;
                        }



                        DialogFactory.getInstance().showAlertDialog(context, 0, context.getResources().getString(R.string.info)
                                , context.getResources().getString(R.string.Member_updated_msg), context.getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("Loading....");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        changeDesignationDialog.dismiss();

                                        ShgMemberDetailsData shgMemberDetailsData = shgMemberDetailsDataItem.get(position);
                                        shgMemberDetailsData.setUpdateMemberCurrentStatus("P");
                                        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);


                                        ChangeDesignationSyncData changeDesignationSyncData=new ChangeDesignationSyncData();
                                        changeDesignationSyncData.setEnityCode(shgMemberDetailsDataItem.get(position).getShgMemberEntityCode());
                                        changeDesignationSyncData.setShgCode(shgCode);
                                        changeDesignationSyncData.setMemberCode(shgMemberDetailsDataItem.get(position).getShgMemberCode());
                                        changeDesignationSyncData.setUpdatedDesignation(selectedDesignation);
                                        changeDesignationSyncData.setSyncStatus("0");
                                        SplashScreen.getInstance().getDaoSession().getChangeDesignationSyncDataDao().insert(changeDesignationSyncData);

                                        UpdatedDesignation updatedDesignation=new UpdatedDesignation();
                                        updatedDesignation.setDesignation(selectedDesignation);
                                        updatedDesignation.setShgCode(shgCode);

                                        SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().insert(updatedDesignation);

                                        try {
                                            updateDesignationFile(context);
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


                                        if (NetworkFactory.isInternetOn(context)){
                                            PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_MEMBER_UPDATE, context);
                                            SyncData.getInstance().syncData(context);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    String memberInactVolyErr = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), context);
                                                    if (memberInactVolyErr.contentEquals(AppConstant.MEMBER_UPDATE_API_VOLLEY_ERROR)) {
                                                        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), context);
                                                        progressDialog.dismiss();
                                                        changeDesignationDialog.dismiss();
                                                        DialogFactory.getInstance().showAlertDialog(context, 0, context.getResources().getString(R.string.info)
                                                                , context.getResources().getString(R.string.Member_UPDATE_API_VOLLEY_ERROR), context.getResources().getString(R.string.ok)
                                                                , new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(@NonNull DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                }, null, null, true);

                                                    } else {
                                                        progressDialog.dismiss();
                                                        changeDesignationDialog.dismiss();
                                                        holder.changeDesignationTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
                                                        holder.changeDesignationTV.setText(context.getResources().getString(R.string.requested));
                                                        notifyItemChanged(holder.getAdapterPosition());
                                                        Toast.makeText(context, context.getResources().getString(R.string.data_sync_msg), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }, 6000);


                                            progressDialog.dismiss();
                                            dialog.dismiss();

                                            notifyItemChanged(holder.getAdapterPosition());


                                        }else {
                                            try {
                                                progressDialog.dismiss();
                                                changeDesignationDialog.dismiss();
                                                holder.changeDesignationTV.setTextColor(context.getResources().getColor(R.color.colorlightorange));
                                                holder.changeDesignationTV.setText(context.getResources().getString(R.string.requested));
                                                notifyItemChanged(holder.getAdapterPosition());
                                                updateMberUpdateBackupFile(context);
                                                Toast.makeText(context, context.getResources().getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();

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



                                        }

                                    }
                                }, context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                },false);

                    }
                });

               } else {
                    String status = "";
                    if (memberActiveStatus.contentEquals("Inactive"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else
                        status = context.getResources().getString(R.string.pending);

                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.member_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
                            , context.getResources().getString(R.string.ok)
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, true);
                }

            }
        });

        AppUtils.getInstance().showLog("activeStatus" + memberActiveStatus, ShgMemberListAdapter.class);

     /*   if (memberActiveStatus.equalsIgnoreCase("Active") &&
                (memberCurrentStatus.equalsIgnoreCase("") ||memberCurrentStatus.equalsIgnoreCase("R")
                        || memberCurrentStatus.equalsIgnoreCase("A"))) {} */

        holder.kyc_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memberActiveStatus.equalsIgnoreCase("Active") &&
                        (memberCurrentStatus.equalsIgnoreCase("") || memberCurrentStatus.equalsIgnoreCase("R")
                                || memberCurrentStatus.equalsIgnoreCase("A"))) {
                    if (kycStatus.equalsIgnoreCase("1")) {
                        Toast.makeText(context, context.getResources().getString(R.string.kyc_completed), Toast.LENGTH_LONG).show();
                        Snackbar snackBar = Snackbar.make(v, context.getResources().getString(R.string.KYC_complt), Snackbar.LENGTH_LONG).setAction("",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        snackBar.setBackgroundTint(context.getResources().getColor(R.color.colorWhite));
                        snackBar.setTextColor(context.getResources().getColor(R.color.colorRed));

                        snackBar.show();
                    } else {
                        PreferenceFactory.getInstance().saveSharedPrefrecesData("shgMemberCode", shgMemberDetailsDataItem.get(position).getShgMemberCode(), context);
                        PreferenceFactory.getInstance().saveSharedPrefrecesData("shgMemberName", shgMemberDetailsDataItem.get(position).getShgMemberName(), context);
                        PreferenceFactory.getInstance().saveSharedPrefrecesData("shgCode", shgMemberDetailsDataItem.get(position).getShgCode(), context);
                        AppUtils.getInstance().makeIntent(context, KYC.class, false);
                    }
                } else {
                    String status = "";
                    if (memberActiveStatus.contentEquals("Inactive"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else
                        status = context.getResources().getString(R.string.pending);

                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.member_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
                            , context.getResources().getString(R.string.ok)
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, true);
                }
            }
        });

        changeStatusAdapter = new ArrayAdapter<String>(context,
                R.layout.spinner_textview, context.getResources().getStringArray(R.array.statusArray));
        holder.change_statusMBS.setAdapter(changeStatusAdapter);


        holder.change_statusMBS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                shgMemberCode = shgMemberDetailsDataItem.get(position).getShgMemberCode();
                shgCode = shgMemberDetailsDataItem.get(position).getShgCode();

                if (memberActiveStatus.equalsIgnoreCase("Active") &&
                        (memberCurrentStatus.equalsIgnoreCase("") || memberCurrentStatus.equalsIgnoreCase("R")
                                || memberCurrentStatus.equalsIgnoreCase("A"))) {

                    String changedStatus = holder.change_statusMBS.getText().toString().trim();
                    if (changedStatus.equalsIgnoreCase(AppConstant.MEMBER_ACTIVE_STATUS)) {

                        List<MemberLoanStatusData> shgLoanStatusDataList = SplashScreen.getInstance().getDaoSession().getMemberLoanStatusDataDao().queryBuilder().build().list();

                        inactiveReasons = SplashScreen.getInstance().getDaoSession().getMemberInactiveReasonDataDao().queryBuilder().build().list();
                        expandableListDetail = new HashMap<String, List<MemberLoanStatusData>>();
                        for (MemberInactiveReasonData inactiveReason : inactiveReasons) {
                            expandableListDetail.put(inactiveReason.getReason(), shgLoanStatusDataList);
                        }

                        reasonsDialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.reasons_dialog);
                        reasonsDialog.show();
                        expandableListView = reasonsDialog.findViewById(R.id.loan_statusELV);

                        expandableListAdapter = new ReasonsListExpendableAdapter(context, inactiveReasons, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);
                        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                            @Override
                            public void onGroupExpand(int groupPosition) {

                                selectedReasonId = inactiveReasons.get(groupPosition).getReasonId();

                               /* Toast.makeText(context.getApplicationContext(),
                                        inactiveReasons.get(groupPosition).getReason() + " List Expanded." + "groupid=" + selectedReasonId,
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        });
                        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                            @Override
                            public void onGroupCollapse(int groupPosition) {
                              /*  Toast.makeText(context.getApplicationContext(),
                                        inactiveReasons.get(groupPosition).getReason() + " List Collapsed.",
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        });
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                                selectedLoanStatusId = expandableListDetail.get(inactiveReasons.get(groupPosition).getReason())
                                        .get(childPosition).getLoanStatusId();

                              /*  Toast.makeText(
                                        context.getApplicationContext(),
                                        inactiveReasons.get(groupPosition).getReason()
                                                + " -> "
                                                + expandableListDetail.get(
                                                inactiveReasons.get(groupPosition).getReason()).get(
                                                childPosition).getLoanStatus() + "childid=" + selectedLoanStatusId, Toast.LENGTH_SHORT
                                ).show();*/

                                DialogFactory.getInstance().showAlertDialog(context, R.drawable.ic_launcher_background
                                        , context.getResources().getString(R.string.info)
                                        , context.getResources().getString(R.string.save_data_confirmation)
                                        , context.getResources().getString(R.string.save)
                                        , new DialogInterface.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressDialog = new ProgressDialog(context);
                                                progressDialog.setMessage("Loading....");
                                                progressDialog.setCancelable(false);
                                                progressDialog.show();


                                                pendingStatus = "P";
                                                status[position] = pendingStatus;
                                                ShgMemberDetailsData shgMemberDetailsData = shgMemberDetailsDataItem.get(position);
                                                shgMemberDetailsData.setMemberCurrentStatus("p");
                                                SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().update(shgMemberDetailsData);


                                                /*save all inactive data in inactive sync table */
                                                saveInactiveDataInLocalDB();

                                                if (AppUtils.isInternetOn(context)) {
                                                  /*  if internet is on then call api
                                                     othervise save data in local dastabase in inactivesync
                                                      table as well as in backup files*/

                                                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_MEMBER_INACTIVATION, context);
                                                    SyncData.getInstance().syncData(context);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String memberInactVolyErr = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), context);
                                                            if (memberInactVolyErr.contentEquals(AppConstant.MEMBER_INACTIVATION_API_VOLLEY_ERROR)) {
                                                                PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getMemberInactivationApiVolleyError(), context);
                                                                progressDialog.dismiss();
                                                                reasonsDialog.dismiss();
                                                                DialogFactory.getInstance().showAlertDialog(context, 0, context.getResources().getString(R.string.info)
                                                                        , context.getResources().getString(R.string.Member_INACTIVE_API_VOLLEY_ERROR), context.getResources().getString(R.string.ok)
                                                                        , new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                            }
                                                                        }, null, null, true);

                                                            } else {
                                                                progressDialog.dismiss();
                                                                reasonsDialog.dismiss();
                                                                Toast.makeText(context, context.getResources().getString(R.string.data_sync_msg), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }, 6000);


                                                    progressDialog.dismiss();
                                                    reasonsDialog.dismiss();

                                                    notifyItemChanged(holder.getAdapterPosition());
                                                } else {
                                                    try {
                                                        progressDialog.dismiss();
                                                        reasonsDialog.dismiss();
                                                        updateMemberStatusBackUpFile(context);
                                                        Toast.makeText(context, context.getResources().getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();
                                                        notifyItemChanged(holder.getAdapterPosition());
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
                                                }
                                            }

                                        }, context.getResources().getString(R.string.cancel)
                                        , new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }, false);
                                return false;
                            }
                        });
                    }
                } else {
                    String status = "";
                    if (memberActiveStatus.contentEquals("Inactive"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else
                        status = context.getResources().getString(R.string.pending);

                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.member_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
                            , context.getResources().getString(R.string.ok)
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, true);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppUtils.getInstance().showLog("memberActiveStatus=" + memberActiveStatus + ",memberCurrentStatus=" + memberCurrentStatus, ShgMemberListAdapter.class);

                if (memberActiveStatus.equalsIgnoreCase("Active") &&
                        (memberCurrentStatus.equalsIgnoreCase("") || memberCurrentStatus.equalsIgnoreCase("R")
                                || memberCurrentStatus.equalsIgnoreCase("A"))) {

                    shgMemberCode = shgMemberDetailsDataItem.get(position).getShgMemberCode();
                    String aadharStatusDone = shgMemberDetailsDataItem.get(position).getShgMemberAadharStatus();
                    String accountStatusDone = shgMemberDetailsDataItem.get(position).getSggMemberAccountStatus();
                    String memAadharCurrentStatus = shgMemberDetailsDataItem.get(position).getMemAadharCurrentStatus();
                    String memBankCurrentStatus = shgMemberDetailsDataItem.get(position).getMemBankAccCurrentStatus();

                    AppUtils.getInstance().showLog("aadharStatusDone=" + aadharStatusDone + ",accountStatusDone=" + accountStatusDone
                            + ",memAadharCurrentStatus=" + memAadharCurrentStatus + ",memBankCurrentStatus="
                            + memBankCurrentStatus, ShgMemberListAdapter.class);

                    // Responds to click on the action


                    if (aadharStatusDone.trim().equalsIgnoreCase("Y") && accountStatusDone.trim().equalsIgnoreCase("0")) {
                        // Toast.makeText(context, "go for Bank Only", Toast.LENGTH_LONG).show();
                        if (memBankCurrentStatus.trim().equalsIgnoreCase("") || memBankCurrentStatus.trim().equalsIgnoreCase("R")) {
                            goForBank();
                        } else {
                            showSnackBar(v);
                        }
                    } else if (aadharStatusDone.trim().equalsIgnoreCase("N") && accountStatusDone.trim().equalsIgnoreCase("0")) {

                        if (memAadharCurrentStatus.trim().equalsIgnoreCase("")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("")) {
                            Toast.makeText(context, "go for both", Toast.LENGTH_LONG).show();
                            goForBoth();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("R")) {
                            Toast.makeText(context, "go for both", Toast.LENGTH_LONG).show();
                            goForBoth();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("P")) {
                            Toast.makeText(context, "go for aadhar only", Toast.LENGTH_LONG).show();
                            goForAadhar();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("A")) {
                            Toast.makeText(context, "go for aadhar only", Toast.LENGTH_LONG).show();
                            goForAadhar();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("P")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("")) {
                            Toast.makeText(context, "go for Bank Only", Toast.LENGTH_LONG).show();
                            goForBank();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("P")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("R")) {
                            Toast.makeText(context, "go for Bank Only", Toast.LENGTH_LONG).show();
                            goForBank();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("P")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("P")) {
                            showSnackBar(v);
                            //  Toast.makeText(context, "You can not perform any operation", Toast.LENGTH_LONG).show();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("P")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("A")) {
                            //  Toast.makeText(context, "You can not perform any operation", Toast.LENGTH_LONG).show();
                            showSnackBar(v);
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("R")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("")) {
                            goForBoth();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("R")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("R")) {
                            Toast.makeText(context, "go for both", Toast.LENGTH_LONG).show();
                            goForBoth();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("R")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("P")) {
                            Toast.makeText(context, "go for aadhar only", Toast.LENGTH_LONG).show();
                            goForAadhar();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("R")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("A")) {
                            Toast.makeText(context, "go for aadhar only", Toast.LENGTH_LONG).show();
                            goForAadhar();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("A")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("")) {
                            Toast.makeText(context, "go for Bank Only", Toast.LENGTH_LONG).show();
                            goForBank();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("A")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("R")) {
                            Toast.makeText(context, "go for Bank Only", Toast.LENGTH_LONG).show();
                            goForBank();
                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("A")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("P")) {
                            showSnackBar(v);
                            //   Toast.makeText(context, "You can not perform any operation", Toast.LENGTH_LONG).show();

                        } else if (memAadharCurrentStatus.trim().equalsIgnoreCase("A")
                                && memBankCurrentStatus.trim().equalsIgnoreCase("A")) {
                            showSnackBar(v);
                            //  Toast.makeText(context, "You can not perform any operation", Toast.LENGTH_LONG).show();
                            /**/
                        }
                    } else if (aadharStatusDone.trim().equalsIgnoreCase("N") && accountStatusDone.trim().equalsIgnoreCase("1")) {
                        // Toast.makeText(context, "go for aadhar only", Toast.LENGTH_LONG).show();
                        if (memAadharCurrentStatus.trim().equalsIgnoreCase("") || memAadharCurrentStatus.trim().equalsIgnoreCase("R")) {
                            goForAadhar();
                        } else {
                            showSnackBar(v);
                        }

                    } else if (aadharStatusDone.trim().equalsIgnoreCase("Y") && accountStatusDone.trim().equalsIgnoreCase("1")) {
                        showSnackBar(v);
                        // Toast.makeText(context, "You can not perform any operation", Toast.LENGTH_LONG).show();
                    }

                } else {

                    String status = "";
                    if (memberActiveStatus.contentEquals("Inactive"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else
                        status = context.getResources().getString(R.string.pending);

                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.member_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
                            , context.getResources().getString(R.string.ok)
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@NonNull DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, true);
                }
            }
        });

    }

    public static void updateDesignationFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        JSONObject dataObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();

        List<UpdatedDesignation> updatedDesignationList=SplashScreen.getInstance().getDaoSession().getUpdatedDesignationDao().queryBuilder().build().list();
        try {
        for (UpdatedDesignation ud:updatedDesignationList){
            JSONObject jsonObject=new JSONObject();

                jsonObject.accumulate("designation",ud.getDesignation());
                jsonObject.accumulate("shgcode",ud.getShgCode());
                jsonArray.put(jsonObject);

            }


            dataObject.accumulate("data",jsonArray);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.UPDATED_DESIGNATION_FILE_NAME, new Cryptography().encrypt(dataObject.toString()));


    }

    private void updateMberUpdateBackupFile(Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException{

        SyncData syncData = SyncData.getInstance();
        syncData.getLogInfo(context);


        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.MEMBER_UPDATE_STATUS_FILE_NAME, new Cryptography().encrypt(syncData.getUpdateMemberObject().toString()));
    }

    private void goForAadhar() {
        /*go for only aadhar
         * 0= take data
         * 1= already taken*/
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyAadharLinkStatus()
                , "0", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyBankAccountLinkStatus()
                , "1", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberCode()
                , shgMemberCode, context);
        Intent intent = new Intent(context, AadharAccountActivity.class);
        context.startActivity(intent);
    }

    private void goForBank() {
        /*go for only aadhar
         * 0= take data
         * 1= already taken*/
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyAadharLinkStatus()
                , "1", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyBankAccountLinkStatus()
                , "0", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberCode()
                , shgMemberCode, context);
        Intent intent = new Intent(context, AadharAccountActivity.class);
        context.startActivity(intent);
    }

    private void goForBoth() {
        /*go for only aadhar
         * 0= take data
         * 1= already taken*/
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyAadharLinkStatus()
                , "0", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyBankAccountLinkStatus()
                , "0", context);
        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgMemberCode()
                , shgMemberCode, context);
        Intent intent = new Intent(context, AadharAccountActivity.class);
        context.startActivity(intent);
    }

    private void saveInactiveDataInLocalDB() {

        ShgInActivSyncData shgInActivSyncData = new ShgInActivSyncData();
        shgInActivSyncData.setBlockCode(PreferenceFactory.getInstance()
                .getSharedPrefrencesData(PreferenceManager.getPrefKeyLoginBlockCode()
                        , context));
        shgInActivSyncData.setVillageCode(PreferenceFactory.getInstance()
                .getSharedPrefrencesData(PreferenceManager
                        .getPrefKeyVillageCode(), context));
        shgInActivSyncData.setGpCode(PreferenceFactory.getInstance()
                .getSharedPrefrencesData(PreferenceManager.getPrefKeyGpCode()
                        , context));
        shgInActivSyncData.setShgCode(shgCode);
        shgInActivSyncData.setShgMemberCode(shgMemberCode);
        shgInActivSyncData.setInactiveStatus("Inactive");
        shgInActivSyncData.setLoanStatus(selectedLoanStatusId);
        shgInActivSyncData.setSyncInactivReson(selectedReasonId);//set 0
        shgInActivSyncData.setSyncStatus("0"); // set 0 by default if sync done then chang status is 1

        SplashScreen.getInstance().getDaoSession().getShgInActivSyncDataDao().insert(shgInActivSyncData);
    }

    public static void updateMemberStatusBackUpFile(@NonNull Context context) throws NoSuchPaddingException, NoSuchAlgorithmException
            , IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
            , InvalidKeyException, UnsupportedEncodingException {

        SyncData syncData = SyncData.getInstance();
        syncData.getLogInfo(context);

        FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                        .getPathDetails(context)
                , AppConstant.MEMBER_STATUS_FILE_NAME, new Cryptography().encrypt(syncData.getInactivJson(context).toString()));
        //  Toast.makeText(context, context.getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();

    }


    private int getUpdatedDesignationCount(String shgCode) {
        int updatedDesignationCount = 0;

        QueryBuilder<ShgMemberDetailsData> queryBuilder = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder();
        updatedDesignationCount = queryBuilder.where(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgCode), queryBuilder
                .or(ShgMemberDetailsDataDao.Properties.UpdateMemberCurrentStatus.eq("A")
                        , ShgMemberDetailsDataDao.Properties.UpdateMemberCurrentStatus.eq("P"))).build().list().size();

        return updatedDesignationCount;
    }


    @Override
    public int getItemCount() {
        return shgMemberDetailsDataItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void showSnackBar(View v) {
        //  View snackBarContView=LayoutInflater.from(context).inflate(R.layout.spinner_textview,null,false);

        Snackbar snackBar = Snackbar.make(v, context.getResources().getString(R.string.no_ops), Snackbar.LENGTH_LONG).setAction("",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackBar.setBackgroundTint(context.getResources().getColor(R.color.colorWhite));
        snackBar.setTextColor(context.getResources().getColor(R.color.colorRed));

        snackBar.show();
    }

    public void showSnacks(View v,String msg) {
        //  View snackBarContView=LayoutInflater.from(context).inflate(R.layout.spinner_textview,null,false);

        Snackbar snackBar = Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackBar.setBackgroundTint(context.getResources().getColor(R.color.colorWhite));
        snackBar.setTextColor(context.getResources().getColor(R.color.colorRed));

        snackBar.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.shgMemberNameTV)
        TextView shgMemberNameTV;

        @Nullable
        @BindView(R.id.member_statusTV)
        TextView member_statusTV;
/*
        @BindView(R.id.aadharLinkedImage)
        ImageView aadharLinkedImage;

        @BindView(R.id.bankAccountLinkedImage)
        ImageView bankAccountLinkedImage;

        @BindView(R.id.accountPendingdImage)
        ImageView accountPendingdImage;

        @BindView(R.id.aadharPendingdImage)
        ImageView aadharPendingdImage;*/

        @Nullable
        @BindView(R.id.change_statusMBS)
        MaterialBetterSpinner change_statusMBS;

        @Nullable
        @BindView(R.id.kyc_BTN)
        TextView kyc_BTN;

        @Nullable
        @BindView(R.id.kycnotupdatedIV)
        ImageView kycnotupdatedIV;

        @Nullable
        @BindView(R.id.kycupdatedIV)
        ImageView kycupdatedIV;

        @Nullable
        @BindView(R.id.aadharPendingTV)
        TextView aadharPendingTV;

        @Nullable
        @BindView(R.id.bankAccPendingTV)
        TextView bankAccPendingTV;

        @Nullable
        @BindView(R.id.designationTV)
        TextView designationTV;

        @Nullable
        @BindView(R.id.designation_current_statusTV)
        TextView designationCurrentStatusTV;

        @Nullable
        @BindView(R.id.change_designationRL)
        RelativeLayout changeDesignationRL;

        @Nullable
        @BindView(R.id.change_designationTV)
        TextView changeDesignationTV;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
