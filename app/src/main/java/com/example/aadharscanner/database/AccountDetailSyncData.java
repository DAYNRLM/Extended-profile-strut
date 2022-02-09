package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class AccountDetailSyncData {
@Id(autoincrement = true)
        Long autoIncrement;
    String villageCode;
    String shgCode;
    String shgMemberCode;
    String bankBranchCode;
    String bankNameCode;
    String ifscNumber;
    String accountNumber;
    String latLong;
    String syncStatus;
    private byte[] photoImage;
    @Generated(hash = 958163693)
    public AccountDetailSyncData(Long autoIncrement, String villageCode,
            String shgCode, String shgMemberCode, String bankBranchCode,
            String bankNameCode, String ifscNumber, String accountNumber,
            String latLong, String syncStatus, byte[] photoImage) {
        this.autoIncrement = autoIncrement;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
        this.bankBranchCode = bankBranchCode;
        this.bankNameCode = bankNameCode;
        this.ifscNumber = ifscNumber;
        this.accountNumber = accountNumber;
        this.latLong = latLong;
        this.syncStatus = syncStatus;
        this.photoImage = photoImage;
    }
    @Generated(hash = 457139548)
    public AccountDetailSyncData() {
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
    public String getBankBranchCode() {
        return this.bankBranchCode;
    }
    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }
    public String getBankNameCode() {
        return this.bankNameCode;
    }
    public void setBankNameCode(String bankNameCode) {
        this.bankNameCode = bankNameCode;
    }
    public String getIfscNumber() {
        return this.ifscNumber;
    }
    public void setIfscNumber(String ifscNumber) {
        this.ifscNumber = ifscNumber;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    public byte[] getPhotoImage() {
        return this.photoImage;
    }
    public void setPhotoImage(byte[] photoImage) {
        this.photoImage = photoImage;
    }
    public String getLatLong() {
        return this.latLong;
    }
    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }
    public Long getAutoIncrement() {
        return this.autoIncrement;
    }
    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
}
