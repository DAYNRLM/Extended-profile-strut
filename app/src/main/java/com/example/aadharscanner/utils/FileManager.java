package com.example.aadharscanner.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FileManager {
    private static final String absoultePath = "/storage/emulated/0/";
    @Nullable
    public static FileManager fileManager = null;

    @Nullable
    public static FileManager getInstance() {
        if (fileManager == null)
            fileManager = new FileManager();
        return fileManager;
    }

    //**********************get file path for for creatjing file *****************************************
    @NonNull
    public static String uniqueFilePath(String loginId) {
        String path = "AadharScan" + "/Adhar_" + loginId +"/Ascan_file";
        return path;
    }


    //**************find absolute path for reading file ***************************************************
    @NonNull
    public String AbsoultePathForUniqueFile(String uniqueFilePath, String uniqueFileName) {
        String absoultPth = absoultePath + uniqueFilePath + "/" + uniqueFileName;
        return absoultPth;
    }

    @NonNull
    public String AbsoultePathForMpinFile(String uniqueFilePath, String uniqueFileName) {
        String absoultPth = absoultePath + uniqueFilePath + "/" + uniqueFileName;
        return absoultPth;
    }

    //******************get filr pathe for create and save dalta in local file******************************
    @NonNull
    public String getPathDetails(@NonNull Context context) {
        String loginId = null;
        String path = "";
        loginId = PreferenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager.getPrefLoginId(), context);
       // mobileNumber = PrefrenceFactory.getInstance().getSharedPrefrencesData(PreferenceManager., context);
        try {
            path = uniqueFilePath(loginId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    @NonNull
    public String getMpinFilePath() {
        String path = "";
        try {
            path = "AadharScan";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    //*************for getting the absolute path for retrive file data from ;local save memory ***************
    @NonNull
    public String getAbslutePathDetails(@NonNull Context context, String fileName) {
        String uniqueFilePath = getPathDetails(context);
        String finalAbsolutePath = AbsoultePathForUniqueFile(uniqueFilePath, fileName);
        return finalAbsolutePath;
    }

    @NonNull
    public String getAbsluteMpinPathDetails(Context context, String fileName) {
        String uniqueFilePath = getMpinFilePath();
        String finalAbsolutePath = AbsoultePathForMpinFile(uniqueFilePath, fileName);
        return finalAbsolutePath;
    }

}
