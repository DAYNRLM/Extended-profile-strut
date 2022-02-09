package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class ShgInactivationSyncData {
    @Id(autoincrement = true)
    private Long id;
    private String shgCode;
    private String villageCode;
    private String reasonId;
    private String loanStatusId;
    private String syncStatus;
    
    @Generated(hash = 248433939)
    public ShgInactivationSyncData(Long id, String shgCode, String villageCode,
            String reasonId, String loanStatusId, String syncStatus) {
        this.id = id;
        this.shgCode = shgCode;
        this.villageCode = villageCode;
        this.reasonId = reasonId;
        this.loanStatusId = loanStatusId;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 87905162)
    public ShgInactivationSyncData() {
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getReasonId() {
        return this.reasonId;
    }
    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }
    public String getLoanStatusId() {
        return this.loanStatusId;
    }
    public void setLoanStatusId(String loanStatusId) {
        this.loanStatusId = loanStatusId;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    

}
