package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShgInActivSyncData {

    @Id(autoincrement = true)
    Long autoIncrement;
    String blockCode;
    String gpCode;
    String villageCode;
    String shgCode;
    String shgMemberCode;
    String inactiveStatus;
    String syncStatus;
    String syncInactivReson;
    String loanStatus;
    
    @Generated(hash = 2099647337)
    public ShgInActivSyncData(Long autoIncrement, String blockCode, String gpCode,
            String villageCode, String shgCode, String shgMemberCode,
            String inactiveStatus, String syncStatus, String syncInactivReson,
            String loanStatus) {
        this.autoIncrement = autoIncrement;
        this.blockCode = blockCode;
        this.gpCode = gpCode;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
        this.inactiveStatus = inactiveStatus;
        this.syncStatus = syncStatus;
        this.syncInactivReson = syncInactivReson;
        this.loanStatus = loanStatus;
    }
    @Generated(hash = 838226121)
    public ShgInActivSyncData() {
    }
    public Long getAutoIncrement() {
        return this.autoIncrement;
    }
    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    public String getBlockCode() {
        return this.blockCode;
    }
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
    public String getGpCode() {
        return this.gpCode;
    }
    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getShgMemberCode() {
        return this.shgMemberCode;
    }
    public void setShgMemberCode(String shgMemberCode) {
        this.shgMemberCode = shgMemberCode;
    }
    public String getInactiveStatus() {
        return this.inactiveStatus;
    }
    public void setInactiveStatus(String inactiveStatus) {
        this.inactiveStatus = inactiveStatus;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    public String getSyncInactivReson() {
        return this.syncInactivReson;
    }
    public void setSyncInactivReson(String syncInactivReson) {
        this.syncInactivReson = syncInactivReson;
    }
    public String getLoanStatus() {
        return this.loanStatus;
    }
    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}
