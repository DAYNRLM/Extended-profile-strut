package com.example.aadharscanner.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadharscanner.Pojo.LanguagePojo;
import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.activites.SplashScreen;
import com.example.aadharscanner.adapters.SelectLanguageAdaptor;
import com.example.aadharscanner.database.LoginInfoData;
import com.example.aadharscanner.utils.AppConstant;
import com.example.aadharscanner.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChangeLanguageFragment extends BaseFragment implements HomeActivity.OnBackPressedListener {
    @Nullable
    @BindView(R.id.changLanguage_Rv)
    RecyclerView changeLanguageRv;
    @NonNull
    List<LanguagePojo> languagedata = new ArrayList<>();


    @NonNull
    public static ChangeLanguageFragment newInstance() {
        ChangeLanguageFragment changeLanguage = new ChangeLanguageFragment();
        return changeLanguage;
    }

    @Override
    public int getFragmentLayout() {
        return (R.layout.fragment_change_language);
    }

    @Override
    public void onFragmentReady() {
        // ((HomeActivity) getActivity()).setToolBarTitle(getString(R.string.chnage_language));
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        bindData();
        showData();
    }

    void bindData() {

        languagedata = new ArrayList<>();
        String index = "0";

        String languageId = SplashScreen.getInstance().getDaoSession().getLoginInfoDataDao().queryBuilder().build().list().get(0).getLanguageId();
        AppUtils.getInstance().showLog("languageId" + languageId, ChangeLanguageFragment.class);
        /* String languageId = loginInfo.getLanguageId();*/

        for (int i = 0; i < AppConstant.language_id.length; i++) {
            if (AppConstant.language_id[i].equalsIgnoreCase(languageId)) {
                index = String.valueOf(i);
            }
        }
        String languageCode = AppConstant.language_code[Integer.parseInt(index)];
        String localLanguage = AppConstant.local_language[Integer.parseInt(index)];
        String language = AppConstant.language_english[Integer.parseInt(index)];
        String lanId = AppConstant.language_id[Integer.parseInt(index)];

        LanguagePojo englisgLang = new LanguagePojo();
        englisgLang.setLanguagecode(AppConstant.language_code[0]);
        englisgLang.setLocalLanguage(AppConstant.local_language[0]);
        englisgLang.setEnglishLanguage(AppConstant.language_english[0]);
        englisgLang.setLanguageid(AppConstant.language_id[0]);
        languagedata.add(englisgLang);


        LanguagePojo localLangFromDb = new LanguagePojo();
        localLangFromDb.setLanguagecode(languageCode);
        localLangFromDb.setLocalLanguage(localLanguage);
        localLangFromDb.setEnglishLanguage(language);
        localLangFromDb.setLanguageid(lanId);
        languagedata.add(localLangFromDb);
    }

    void showData() {
        SelectLanguageAdaptor selectLanguageAdaptor = new SelectLanguageAdaptor(getContext(), languagedata);
        changeLanguageRv.setLayoutManager(new LinearLayoutManager(getContext()));
        changeLanguageRv.setAdapter(selectLanguageAdaptor);
        selectLanguageAdaptor.notifyDataSetChanged();
    }


    @Override
    public void doBack() {
        DashBoardFragment dashBoardFragment = DashBoardFragment.newInstance();
        AppUtils.getInstance().replaceFragment(getFragmentManager(),
                dashBoardFragment,
                DashBoardFragment.class.getSimpleName(),
                false, R.id.fragmentContainer);
    }


}
