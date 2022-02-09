package com.example.aadharscanner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aadharscanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static android.content.Context.LOCATION_SERVICE;

public class AppUtils {
    public static AppUtils utilsInstance;
    private static boolean wantToShow = true;
    public static boolean isEncryptDecrypt = true;

    public synchronized static AppUtils getInstance() {
        if (utilsInstance == null) {
            utilsInstance = new AppUtils();
        }
        return utilsInstance;
    }

    public void showLog(@NonNull String logMsg, @NonNull Class application) {
        if (wantToShow) {
            Log.d(application.getName(), logMsg);
        }
    }

    @Nullable
    public JSONObject wantToEncryptOldKey(@NonNull JSONObject jsonObject){
        JSONObject jsonObjectEncrpted=null;
        if (isEncryptDecrypt){
            try {
                String stringEncrpted=new Cryptography().encrypt(jsonObject.toString());
                jsonObjectEncrpted=new JSONObject();
                jsonObjectEncrpted.accumulate("data",stringEncrpted);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            jsonObjectEncrpted= jsonObject;
        }
        return jsonObjectEncrpted;
    }
    @Nullable
    public JSONObject wantToEncryptOldKey(@NonNull JSONArray jsonArray){
        JSONObject jsonObjectEncrpted=null;
        if (isEncryptDecrypt){
            try {
                String stringEncrpted=new Cryptography().encrypt(jsonArray.toString());
                jsonObjectEncrpted=new JSONObject();
                jsonObjectEncrpted.accumulate("data",stringEncrpted);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            /*jsonObjectEncrpted= jsonArray;*/
        }
        return jsonObjectEncrpted;
    }


    @Nullable
    public JSONObject wantToDecryptOldKey(@NonNull JSONObject responseObject){
        JSONObject jsonObject=null;
        if (isEncryptDecrypt){
            try {
                String decryptedString=new Cryptography().decrypt(responseObject.getString("data"));
                jsonObject=new JSONObject(decryptedString);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            jsonObject=responseObject;
        }
        return jsonObject;
    }

    @Nullable
    public JSONObject wantToEncrypt(@NonNull JSONObject jsonObject){
        JSONObject jsonObjectEncrpted=null;
        if (isEncryptDecrypt){
            try {
                String stringEncrpted=new Cryptography().encryptNewKey(jsonObject.toString());
                jsonObjectEncrpted=new JSONObject();
                jsonObjectEncrpted.accumulate("data",stringEncrpted);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            jsonObjectEncrpted= jsonObject;
        }
        return jsonObjectEncrpted;
    }
    @Nullable
    public JSONObject wantToEncrypt(@NonNull JSONArray jsonArray){
        JSONObject jsonObjectEncrpted=null;
        if (isEncryptDecrypt){
            try {
                String stringEncrpted=new Cryptography().encryptNewKey(jsonArray.toString());
                jsonObjectEncrpted=new JSONObject();
                jsonObjectEncrpted.accumulate("data",stringEncrpted);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }/*else {
            jsonObjectEncrpted= jsonObject;
        }*/
        return jsonObjectEncrpted;
    }


    @Nullable
    public JSONObject wantToDecrypt(@NonNull JSONObject responseObject){
        JSONObject jsonObject=null;
        if (isEncryptDecrypt){
            try {
               String decryptedString=new Cryptography().decryptNewKey(responseObject.getString("data"));
                jsonObject=new JSONObject(decryptedString);
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
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            jsonObject=responseObject;
        }
        return jsonObject;
    }



    public void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, boolean addTobackStack, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
       // overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
      //  ft.setCustomAnimations(R.anim.in_from_right, R.anim.out_from_left);
/*
        FragmentTransactionExtended fragmentTransactionExtended=new FragmentTransactionExtended(HomeActivity.context,ft, DashBoardFragment.newInstance(), AddLocation.getInstance(), R.id.fragmentContainer);
*/
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    public void replaceFragmentFromRightToLeft(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, boolean addTobackStack, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        ft.setCustomAnimations(R.anim.out_from_left, R.anim.in_from_right);
/*
        FragmentTransactionExtended fragmentTransactionExtended=new FragmentTransactionExtended(HomeActivity.context,ft, DashBoardFragment.newInstance(), AddLocation.getInstance(), R.id.fragmentContainer);
*/
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    public static void hideSoftKeyboard(@NonNull Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }
    @NonNull
    public String[] getMonthList() {
        String[] monthList = {"January", "Febrary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthList;
    }

    public void makeIntent(@Nullable Context context, Class activityToGo, boolean clearBackActitvity) {
        if (context != null) {
            Intent intent = new Intent(context, activityToGo);

            if (clearBackActitvity) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            context.startActivity(intent);
        }
    }

    @Nullable
    public String loadAssetData(@NonNull Context context, @NonNull String filName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void setLocale(@NonNull String localeName, @NonNull Resources res, Context context) {

        Locale myLocale = new Locale(localeName);
        // Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

       /* if (!localeName.equals(localeName)) {
            Locale  myLocale = new Locale(localeName);
           // Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            *//*Intent refresh = new Intent(context, HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            context.startActivity(refresh);*//*
        } else {
            Toast.makeText(context, "Language already selected!", Toast.LENGTH_SHORT).show();
        }*/
    }

    @NonNull
    public String getSha256(@NonNull String plain_text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = null;
        hash = digest.digest(plain_text.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }
    @NonNull
    private String bytesToHex(@NonNull byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     *
     * @param str
     * @return string
     */
    @Nullable
    public String removeCommaFromLast(@Nullable String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
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

    public static boolean isGPSEnabled(@NonNull Context context) {
        boolean flag = false;
        // getting GPS status
        LocationManager locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;

    }

    public static boolean isValid(@NonNull String s)
    {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isValidPanCardNo(String panCardNo) {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

        Pattern p = Pattern.compile(regex);
        if (panCardNo == null) {
            return false;
        }
        Matcher m = p.matcher(panCardNo);
        return m.matches();
    }

    public static boolean isValidLicenseNo(String str) {
/*
        String  regexs = "^ ( ([A-Z]{2}[0-9]{2}) ( )| ([A-Z]{2}-[0-9]{2}) ) ((19|20)[0-9][0-9]) [0-9]{7} $";
*/
        String regex
                = "^" +
                "(" +
                "([A-Z]{2}[0-9]{2})"+
                "|" +
                "([A-Z]{2}[0-9]" + "{2})" +
                ")" +
                "((19|20)" + "[0-9]" + "[0-9])" +
                "[0-9]{7}" +
                "$";

        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @apiNote to validate the passport of user
     * @param str
     * @return boolean
     */
    public static boolean isValidPassportNo(String str) {
        String regex
                = "^[A-PR-WYa-pr-wy][1-9]\\d"
                + "\\d{4}[1-9]$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     *
     * @param str
     * @return boolean
     */
    public static boolean isValidAadharNumber(String str) {

        String regex = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$";

        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);
        return m.matches();
    }

    public InputFilter setInputFilter(String inputDigitType){
        return  new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i)
                {
                    if (Pattern.compile(inputDigitType).matcher(String.valueOf(source.charAt(i))).matches())
                    {
                        return source;
                    }
                }
                return null;
            }
        };
    }

}


