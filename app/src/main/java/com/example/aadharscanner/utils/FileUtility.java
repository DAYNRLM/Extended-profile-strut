package com.example.aadharscanner.utils;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileUtility {
    Context contex;
    @Nullable
    public static FileUtility fileUtility = null;

    @Nullable
    public static FileUtility getInstance() {
        if (fileUtility == null)
            fileUtility = new FileUtility();
        return fileUtility;
    }

    //******************create file in local memory***********************************
    public void createFileInMemory(@NonNull String filePath, @NonNull String fileName, String overWriteValue) {

        File makeFolder = new File(Environment.getExternalStorageDirectory(), filePath);
        makeFolder.mkdirs();
        File newFile = new File(makeFolder, fileName);
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
                newFile.getAbsoluteFile();
                //  AppUtility.getInstance().showLog("fileAbsoultePathAfterCreation" + newFile.getAbsoluteFile(), FileUtility.class);
                // Toast.makeText(contex, "File created", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream fOut = new FileOutputStream(newFile);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fOut);
                outputWriter.write(String.valueOf(overWriteValue));
                outputWriter.close();
                //display file saved message
                // Toast.makeText(contex, "File saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileOutputStream fOut = new FileOutputStream(newFile);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fOut);
                outputWriter.write(String.valueOf(overWriteValue));
                outputWriter.close();
                //display file saved message
                //  Toast.makeText(contex, "File saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //*****************************read file from local memory**********************************
    @NonNull
    public String read_file(String absolutePath, @NonNull String path, @NonNull String fileName) {
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), path);
        File newFile = new File(imagesFolder, fileName);
        if (newFile.exists()) {
            try {
                //FileInputStream fis = new FileInputStream (new File(filename));
                //FileInputStream fis = context.openFileInput(filename);
                FileInputStream fis1 = new FileInputStream(absolutePath);
                InputStreamReader isr = new InputStreamReader(fis1, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            } catch (FileNotFoundException e) {
                return "";
            } catch (UnsupportedEncodingException e) {
                return "";
            } catch (IOException e) {
                return "";
            }

        } else {
            return "No Backup Found.";
        }
    }


    //*********************file exist or not**************************************
    public boolean isFileExist(@NonNull String path, @NonNull String fileName) {
        boolean isFileExist = false;
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), path);
        File newFile = new File(imagesFolder, fileName);
        if (newFile.exists()) {
            isFileExist = true;
        }
        return isFileExist;
    }

}
