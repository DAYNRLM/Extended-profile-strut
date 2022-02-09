package com.example.aadharscanner.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.database.InactiveReasons;
import com.example.aadharscanner.database.ShgDetailData;
import com.example.aadharscanner.database.ShgInactivationSyncData;
import com.example.aadharscanner.database.ShgLoanStatusData;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.fragment.ShgFragment;
import com.example.aadharscanner.fragment.ShgMembersFragment;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.Cryptography;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.FileManager;
import com.example.aadharscanner.utils.FileUtility;
import com.example.aadharscanner.utils.NetworkFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;
import com.example.aadharscanner.utils.SyncData;

import org.greenrobot.greendao.query.QueryBuilder;

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

import static android.view.View.Y;

public class ShgListCustomAdapter extends RecyclerView.Adapter<ShgListCustomAdapter.MyViewHolder> {

    Context context;
    List<ShgDetailData> shgDetailDataItemList;
    ShgMembersFragment shgMembersFragment;

    private Dialog reasonsDialog;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    List<InactiveReasons> inactiveReasons;
    private HashMap<String, List<ShgLoanStatusData>> expandableListDetail;
    @Nullable
    private String villageCode = "", shgCode = "", selectedReasonId = "", selectedLoanStatusId = "", shgCurrentStatus, actvInactvStatus;

    public ShgListCustomAdapter(@NonNull Context context, List<ShgDetailData> shgDetailDataItemList, ShgMembersFragment shgMembersFragment) {
        this.context = context;
        this.shgDetailDataItemList = shgDetailDataItemList;
        this.shgMembersFragment = shgMembersFragment;
        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefKeyVillageCode(), context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View selectShgListView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shg_custom_layout, parent, false);
        return new ShgListCustomAdapter.MyViewHolder(selectShgListView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.shgnameTv.setText(shgDetailDataItemList.get(position).getShgGroupName() + "(" + shgDetailDataItemList.get(position).getShgCode() + ")");

        getTotalShgMember(shgDetailDataItemList.get(position).getShgCode());
        holder.totalShgMemberTv.setText(getTotalShgMember(shgDetailDataItemList
                .get(position).getShgCode()));

        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().detachAll();
        QueryBuilder<ShgMemberDetailsData> dataCountOfBankAadharBuilder = SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().queryBuilder();
        List<ShgMemberDetailsData> completedStatus = dataCountOfBankAadharBuilder.where(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgDetailDataItemList.get(position).getShgCode()), dataCountOfBankAadharBuilder.and(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgDetailDataItemList.get(position).getShgCode()),
                ShgMemberDetailsDataDao.Properties.SggMemberAccountStatus.eq("1"))).build().list();


        int size = completedStatus.size();
        holder.size.setText(String.valueOf(size));
        AppUtils.getInstance().showLog("sizeoflinked" + size + "VillageCode" +
                shgDetailDataItemList.get(position).getVillageCode(), ShgListCustomAdapter.class);

        actvInactvStatus = shgDetailDataItemList.get(position).getShgActInactStatus();
        shgCurrentStatus = shgDetailDataItemList.get(position).getShgCurrentStatus();

        if (shgCurrentStatus.contentEquals("") && actvInactvStatus.contentEquals("Y")) {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.shg_status.setText(context.getResources().getString(R.string.status_active));
        } else if (shgCurrentStatus.contentEquals("P") && actvInactvStatus.contentEquals("Y")) {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorlightorange));
            holder.shg_status.setText(context.getResources().getString(R.string.pending_status));
        } else if (shgCurrentStatus.contentEquals("R") && actvInactvStatus.contentEquals("Y")) {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.shg_status.setText(context.getResources().getString(R.string.rejected_status));
        } else if (shgCurrentStatus.contentEquals("A") && actvInactvStatus.contentEquals("Y")) {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.shg_status.setText(context.getResources().getString(R.string.status_active));
        } else if (shgCurrentStatus.contentEquals("A") && actvInactvStatus.contentEquals("N")) {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.shg_status.setText(context.getResources().getString(R.string.status_inactive));
        } else {
            holder.shg_status.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.shg_status.setText(context.getResources().getString(R.string.status_inactive));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shgCurrentStatus = shgDetailDataItemList.get(position).getShgCurrentStatus();
                actvInactvStatus = shgDetailDataItemList.get(position).getShgActInactStatus();

                AppUtils.getInstance().showLog("actvInactvStatus" + actvInactvStatus + "shgCurrentStatus" + shgCurrentStatus, ShgListCustomAdapter.class);
                if (actvInactvStatus.contentEquals("Y") &&
                        (shgCurrentStatus.contentEquals("") || shgCurrentStatus.contentEquals("R"))) {
                    shgCode = shgDetailDataItemList.get(position).getShgCode();
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefShgName(), shgDetailDataItemList.get(position).getShgGroupName(), context);
                    PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefKeyShgCode(), shgCode, context);
                    AppUtils.getInstance().replaceFragment(shgMembersFragment.getFragmentManager(), ShgFragment.newInstance(),
                            ShgFragment.class.getSimpleName(), true, R.id.fragmentContainer);
                } else {
                    String status = "";
                    if (actvInactvStatus.contentEquals("N"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else status = context.getResources().getString(R.string.pending);
                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.shg_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
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
        holder.change_shg_statusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shgCurrentStatus = shgDetailDataItemList.get(position).getShgCurrentStatus();
                actvInactvStatus = shgDetailDataItemList.get(position).getShgActInactStatus();
                AppUtils.getInstance().showLog("actvInactvStatus=" + actvInactvStatus + ",shgCurrentStatus=" + shgCurrentStatus, ShgListCustomAdapter.class);

                if (actvInactvStatus.contentEquals("Y") &&
                        (shgCurrentStatus.contentEquals("") || shgCurrentStatus.contentEquals("R"))) {

                    shgCode = shgDetailDataItemList.get(position).getShgCode();
                    List<ShgLoanStatusData> shgLoanStatusDataList = SplashScreen.getInstance().getDaoSession().getShgLoanStatusDataDao().queryBuilder().build().list();

                    inactiveReasons = SplashScreen.getInstance().getDaoSession().getInactiveReasonsDao().queryBuilder().build().list();
                    expandableListDetail = new HashMap<String, List<ShgLoanStatusData>>();
                    for (InactiveReasons inactiveReason : inactiveReasons) {
                        expandableListDetail.put(inactiveReason.getReason(), shgLoanStatusDataList);
                    }

                    reasonsDialog = DialogFactory.getInstance().showCustomDialog(context, R.layout.reasons_dialog);
                    reasonsDialog.show();
                    expandableListView = reasonsDialog.findViewById(R.id.loan_statusELV);

                    expandableListAdapter = new ShgReasonlistExpendableAdapter(context, inactiveReasons, expandableListDetail);
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
                       /* Toast.makeText(context.getApplicationContext(),
                                inactiveReasons.get(groupPosition).getReason() + " List Collapsed.",
                                Toast.LENGTH_SHORT).show();*/
                        }
                    });
                    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                            selectedLoanStatusId = expandableListDetail.get(inactiveReasons.get(groupPosition).getReason())
                                    .get(childPosition).getLoanStatusId();

                       /* Toast.makeText(
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
                                        @SuppressLint("ResourceAsColor")
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            reasonsDialog.dismiss();

                                            shgCurrentStatus = "P";
                                            ShgDetailData shgDetailData = shgDetailDataItemList.get(position);
                                            shgDetailData.setShgCurrentStatus(shgCurrentStatus);
                                            SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().update(shgDetailData);

                                            ShgInactivationSyncData shgInactivationSyncData = new ShgInactivationSyncData();

                                            shgInactivationSyncData.setVillageCode(villageCode);
                                            shgInactivationSyncData.setId(null);
                                            shgInactivationSyncData.setShgCode(shgCode);
                                            shgInactivationSyncData.setReasonId(selectedReasonId);
                                            shgInactivationSyncData.setLoanStatusId(selectedLoanStatusId);
                                            shgInactivationSyncData.setSyncStatus("0");

                                            SplashScreen.getInstance().getDaoSession().getShgInactivationSyncDataDao().insert(shgInactivationSyncData);

                                            if (NetworkFactory.isInternetOn(context)) {
                                                ProgressDialog progressDialog = new ProgressDialog(context);
                                                progressDialog.setMessage(context.getResources().getString(R.string.syncing_data));
                                                progressDialog.show();
                                                //sync shg inactivation data
                                                PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getCallingApi(), AppConstant.CALLING_API_SHG_INACTIVATION, context);
                                                SyncData.getInstance().syncData(context);
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String volleyerror = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getShgInactivationApiVolleyError(), context);
                                                        if (AppConstant.SHG_INACTIVATION_API_VOLLEY_ERROR.equalsIgnoreCase(volleyerror)) {
                                                            progressDialog.dismiss();
                                                            PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getShgInactivationApiVolleyError(), context);
                                                            DialogFactory.getInstance().showAlertDialog(context, 0, context.getResources().getString(R.string.info), context.getResources().getString(R.string.SHG_INACTIVE_API_VOLLEY_ERROR),
                                                                    context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(@NonNull DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    }, true);

                                                        } else {
                                                            progressDialog.dismiss();
                                                            holder.shg_status.setTextColor(R.color.color_red);
                                                            holder.shg_status.setText(context.getResources().getString(R.string.pending_status));
                                                            notifyItemChanged(holder.getAdapterPosition());
                                                            Toast.makeText(context, context.getResources().getString(R.string.data_sync_msg), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }, 6000);

                                            } else {
                                                //update shg inactivation file
                                                updateShgInactivationBackupFile(context);
                                                holder.shg_status.setTextColor(R.color.color_red);
                                                holder.shg_status.setText(context.getResources().getString(R.string.pending_status));
                                                notifyItemChanged(holder.getAdapterPosition());
                                                Toast.makeText(context, context.getResources().getString(R.string.data_save_msg), Toast.LENGTH_LONG).show();
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
                } else {
                    String status = "";
                    if (shgCurrentStatus.contentEquals("A") || actvInactvStatus.contentEquals("N"))
                        status = context.getResources().getString(R.string.status_inactive);
                    else status = context.getResources().getString(R.string.pending);

                    DialogFactory.getInstance().showAlertDialog(context
                            , 0
                            , context.getResources().getString(R.string.info)
                            , context.getResources().getString(R.string.shg_is) + " " + status + " " + context.getResources().getString(R.string.shg_inactive_msg)
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

    private void updateShgInactivationBackupFile(@NonNull Context context) {
        try {
            SyncData syncData = SyncData.getInstance();
            syncData.getLogInfo(context);

            FileUtility.getInstance().createFileInMemory(FileManager.getInstance()
                            .getPathDetails(context)
                    , AppConstant.SHG_INACTIVATION_STATUS_FILE_NAME, new Cryptography().encrypt(syncData.getShgInactivationSyncData(context).toString()));
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
    }

    @NonNull
    private String getTotalShgMember(String shgCode) {
        int count = 0;
        String villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefKeyVillageCode(), context);
        List<ShgMemberDetailsData> shgMemberDetailsData = SplashScreen.getInstance().getDaoSession()
                .getShgMemberDetailsDataDao().queryBuilder().where(ShgMemberDetailsDataDao.Properties
                                .ShgCode.eq(shgCode)
                        , ShgMemberDetailsDataDao.Properties.VillageCode.eq(villageCode)).build().list();
        count = shgMemberDetailsData.size();
        return "" + count;
    }

    @Override
    public int getItemCount() {
        return shgDetailDataItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.shgnameTv)
        TextView shgnameTv;

        @Nullable
        @BindView(R.id.totalShgMemberTv)
        TextView totalShgMemberTv;

        @Nullable
        @BindView(R.id.size)
        TextView size;

        @Nullable
        @BindView(R.id.shg_status)
        TextView shg_status;


        @Nullable
        @BindView(R.id.change_shg_statusTV)
        LinearLayout change_shg_statusTV;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
