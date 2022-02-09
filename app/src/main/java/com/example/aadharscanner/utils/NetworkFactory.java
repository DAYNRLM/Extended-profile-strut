package com.example.aadharscanner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

public class NetworkFactory {


    public static boolean isNetworkAvailable(@Nullable Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    return true;
                }
            } else {
                // not connected to the internet
                return false;
            }
            return false;
        }
        return false;
    }

    public static boolean isInternetOn(@Nullable Context context) {
        if (context != null) {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

 /*   public static void showNointernetConnectionWindow(final Context context, final Fragment fragment) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet_layout);
        Button retryButton = (Button) dialog.findViewById(R.id.retryButton);
        RelativeLayout nointernet = (RelativeLayout) dialog.findViewById(R.id.nointernet);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fragment.onAttach(context);
            }
        });
        dialog.show();
    }
    public static void showNointernetConnectionWindow(final Context context) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet_layout);
        Button retryButton = (Button) dialog.findViewById(R.id.retryButton);
        RelativeLayout nointernet = (RelativeLayout) dialog.findViewById(R.id.nointernet);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}
