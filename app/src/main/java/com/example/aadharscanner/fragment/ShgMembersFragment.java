package com.example.aadharscanner.fragment;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.adapters.ShgListCustomAdapter;
import com.example.aadharscanner.database.ShgDetailData;
import com.example.aadharscanner.database.ShgDetailDataDao;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

import java.util.List;

import butterknife.BindView;

public class ShgMembersFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {

    @Nullable
    @BindView(R.id.shgMemberRecyclerview)
    RecyclerView shgMemberRecyclerview;

    List<ShgDetailData> shgDetailDataItemList;
    @Nullable
    String villageCode;
    @Nullable
    ShgListCustomAdapter shgListCustomAdapter;

    @NonNull
    public static ShgMembersFragment newInstance() {
        ShgMembersFragment shgMembersFragment = new ShgMembersFragment();
        return shgMembersFragment;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.shg_member_fragment;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        villageCode = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager
                .getPrefKeyVillageCode(), getContext());
        bindData();
        showData();

    }

    private void showData() {
        shgListCustomAdapter = new ShgListCustomAdapter(getContext(), shgDetailDataItemList, this);
        shgMemberRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        shgMemberRecyclerview.setAdapter(shgListCustomAdapter);
        shgListCustomAdapter.notifyDataSetChanged();
    }

    private void bindData() {
        SplashScreen.getInstance().getDaoSession().getShgDetailDataDao().detachAll();
        shgDetailDataItemList = SplashScreen.getInstance().getDaoSession().getShgDetailDataDao()
                .queryBuilder().where(ShgDetailDataDao.Properties.VillageCode.eq(villageCode)).build().list();
        AppUtils.getInstance().showLog("shgDetailDataItemList" + shgDetailDataItemList.size(), ShgMembersFragment.class);
    }

    @Override
    public void doBack() {
        AppUtils.getInstance().replaceFragment(getFragmentManager(), SelectLocationFragment.newInstance()
                , SelectLocationFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
