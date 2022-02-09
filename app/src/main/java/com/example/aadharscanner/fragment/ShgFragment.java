package com.example.aadharscanner.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.AadharAccountActivity;
import com.example.aadharscanner.activites.AddShgMemberActivity;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.adapters.ShgListCustomAdapter;
import com.example.aadharscanner.adapters.ShgMemberListAdapter;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.database.ShgMemberDetailsData;
import com.example.aadharscanner.database.ShgMemberDetailsDataDao;
import com.example.aadharscanner.database.ShgMemberRegistrationSyncDataDao;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

import java.util.List;

import butterknife.BindView;

public class ShgFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {
    @Nullable
    @BindView(R.id.slectShgMemberListRV)
    RecyclerView slectShgMemberListRV;

    @Nullable
    @BindView(R.id.location_TV)
    TextView location_TV;

    @Nullable
    @BindView(R.id.add_memberTV)
    ImageView add_memberTV;

    @Nullable
    @BindView(R.id.shg_name)
    TextView shg_name;


    @Nullable
    ShgMemberListAdapter memberListAdapter;
    @Nullable
    String shgCode, villageCode;
    List<ShgMemberDetailsData> shgMemberDetailsDataItem;

    @NonNull
    public static ShgFragment newInstance() {
        ShgFragment shgFragment = new ShgFragment();
        return shgFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.shglist_fragment;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefKeyVillageCode(), getContext());
        shgCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefKeyShgCode(), getContext());
        shg_name.setText(PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefShgName(), getContext()));
        location_TV.setText("" + PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefGpName()
                , getContext()) + "" + ">>" + PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefVillageName()
                , getContext()));
        PreferenceFactory.getInstance().removeSharedPrefrencesData(PreferenceManager.getPrefKeyShgMemberStatus(), getContext());
        bindData();
        showData();
        add_memberTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalMemberAdded = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao()
                        .queryBuilder().where(ShgDetailDataDao.Properties.ShgCode.eq(shgCode)).build().unique().getShgMemberCount();
                int addedMemCntLocal = SplashScreen.getInstance().getDaoSession().getShgMemberRegistrationSyncDataDao().queryBuilder()
                        .where(ShgMemberRegistrationSyncDataDao.Properties.Shgcode.eq(shgCode)).build().list().size();

                if (new AadharAccountActivity().getLoginInfo().get(0).getState_short_name()
                        .trim().toLowerCase().contains("kr") && (addedMemCntLocal + Integer.parseInt(totalMemberAdded)) < 26) {
                    AppUtils.getInstance().makeIntent(getContext(), AddShgMemberActivity.class, false);
                } else if ((addedMemCntLocal + Integer.parseInt(totalMemberAdded)) < 21) {
                    AppUtils.getInstance().makeIntent(getContext(), AddShgMemberActivity.class, false);
                } else {
                    DialogFactory.getInstance().showAlertDialog(getContext(), R.drawable.ic_member_form_24dp,
                            getContext().getString(R.string.info), getContext().getString(R.string.error_add_member_limit_exceed)
                            , getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, null, null, true);
                }
            }
        });
    }

    private void showData() {
        memberListAdapter = new ShgMemberListAdapter(shgMemberDetailsDataItem, getContext(),shgCode);
        slectShgMemberListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        slectShgMemberListRV.setAdapter(memberListAdapter);
        memberListAdapter.notifyDataSetChanged();
    }

    private void bindData() {
        SplashScreen.getInstance().getDaoSession().getShgMemberDetailsDataDao().detachAll();
        shgMemberDetailsDataItem = SplashScreen.getInstance().getDaoSession()
                .getShgMemberDetailsDataDao().queryBuilder().where(ShgMemberDetailsDataDao.Properties.ShgCode.eq(shgCode)
                        , ShgMemberDetailsDataDao.Properties.VillageCode.eq(villageCode))
                .build().list();
    }

    @Override
    public void doBack() {
        AppUtils.getInstance().replaceFragment(getFragmentManager(), ShgMembersFragment.newInstance()
                , ShgMembersFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
