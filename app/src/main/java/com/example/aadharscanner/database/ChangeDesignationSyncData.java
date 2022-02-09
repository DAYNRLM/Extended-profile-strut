package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChangeDesignationSyncData {

    @Id(autoincrement = true)
private Long id;
    private String enityCode;
    private String shgCode;
    private String memberCode;
    private String updatedDesignation;
    private String syncStatus;
    @Generated(hash = 1688395685)
    public ChangeDesignationSyncData(Long id, String enityCode, String shgCode,
            String memberCode, String updatedDesignation, String syncStatus) {
        this.id = id;
        this.enityCode = enityCode;
        this.shgCode = shgCode;
        this.memberCode = memberCode;
        this.updatedDesignation = updatedDesignation;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 762730050)
    public ChangeDesignationSyncData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEnityCode() {
        return this.enityCode;
    }
    public void setEnityCode(String enityCode) {
        this.enityCode = enityCode;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getMemberCode() {
        return this.memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public String getUpdatedDesignation() {
        return this.updatedDesignation;
    }
    public void setUpdatedDesignation(String updatedDesignation) {
        this.updatedDesignation = updatedDesignation;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
   


}
