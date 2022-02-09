package com.example.aadharscanner.fragment;

import androidx.annotation.NonNull;

import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.utils.AppUtils;

public class ContactUs extends BaseFragment implements HomeActivity.OnBackPressedListener {

    @NonNull
    public static ContactUs newInstance() {
        ContactUs contactUs = new ContactUs();
        return contactUs;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_contact_us;
    }

    @Override
    public void onFragmentReady() {
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public void doBack() {
        AppUtils.getInstance().replaceFragment(getFragmentManager(), DashBoardFragment.newInstance()
                , DashBoardFragment.class.getSimpleName(), false, R.id.fragmentContainer);
    }
}
