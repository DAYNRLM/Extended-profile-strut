package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WebRequestData {
    private String villageCode;
    private String status;
    @Generated(hash = 1707361644)
    public WebRequestData(String villageCode, String status) {
        this.villageCode = villageCode;
        this.status = status;
    }
    @Generated(hash = 440490022)
    public WebRequestData() {
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
