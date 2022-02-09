package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LoginInfoData {
    String loginId;
    String password;
    String appVersion;
    String serverDateTime;
    String languageId;
    String logOutDays;
    String mobileNumber;
    String state_code;
    String state_short_name;
    String pvgtStatus;
    
    @Generated(hash = 489227761)
    public LoginInfoData(String loginId, String password, String appVersion,
            String serverDateTime, String languageId, String logOutDays,
            String mobileNumber, String state_code, String state_short_name,
            String pvgtStatus) {
        this.loginId = loginId;
        this.password = password;
        this.appVersion = appVersion;
        this.serverDateTime = serverDateTime;
        this.languageId = languageId;
        this.logOutDays = logOutDays;
        this.mobileNumber = mobileNumber;
        this.state_code = state_code;
        this.state_short_name = state_short_name;
        this.pvgtStatus = pvgtStatus;
    }
    @Generated(hash = 1233571755)
    public LoginInfoData() {
    }
    public String getLoginId() {
        return this.loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAppVersion() {
        return this.appVersion;
    }
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
    public String getServerDateTime() {
        return this.serverDateTime;
    }
    public void setServerDateTime(String serverDateTime) {
        this.serverDateTime = serverDateTime;
    }
    public String getLanguageId() {
        return this.languageId;
    }
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
    public String getLogOutDays() {
        return this.logOutDays;
    }
    public void setLogOutDays(String logOutDays) {
        this.logOutDays = logOutDays;
    }
    public String getMobileNumber() {
        return this.mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getState_code() {
        return this.state_code;
    }
    public void setState_code(String state_code) {
        this.state_code = state_code;
    }
    public String getState_short_name() {
        return this.state_short_name;
    }
    public void setState_short_name(String state_short_name) {
        this.state_short_name = state_short_name;
    }
    public String getPvgtStatus() {
        return this.pvgtStatus;
    }
    public void setPvgtStatus(String pvgtStatus) {
        this.pvgtStatus = pvgtStatus;
    }

}
