package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class AadharDetailSyncData {
     @Id(autoincrement = true)
     Long autoIncrement;
    String villageCode;
    String shgCode;
    String shgMemberCode;
    String aadharNumber;
    String aadharName;
    String aadharYOB;
    String aadharGender;
    String aadharAddress;
    String postalCode;
    String aadharSyncStatus;
    String aadharFatherName;
    String aadhaarMobileNumber;
    String latLong;
    String scanStatus;
    
    private byte[] aadhaarImage;
    private byte[] concentForm;
    
    @Generated(hash = 2095388140)
    public AadharDetailSyncData(Long autoIncrement, String villageCode,
            String shgCode, String shgMemberCode, String aadharNumber,
            String aadharName, String aadharYOB, String aadharGender,
            String aadharAddress, String postalCode, String aadharSyncStatus,
            String aadharFatherName, String aadhaarMobileNumber, String latLong,
            String scanStatus, byte[] aadhaarImage, byte[] concentForm) {
        this.autoIncrement = autoIncrement;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
        this.aadharNumber = aadharNumber;
        this.aadharName = aadharName;
        this.aadharYOB = aadharYOB;
        this.aadharGender = aadharGender;
        this.aadharAddress = aadharAddress;
        this.postalCode = postalCode;
        this.aadharSyncStatus = aadharSyncStatus;
        this.aadharFatherName = aadharFatherName;
        this.aadhaarMobileNumber = aadhaarMobileNumber;
        this.latLong = latLong;
        this.scanStatus = scanStatus;
        this.aadhaarImage = aadhaarImage;
        this.concentForm = concentForm;
    }
    @Generated(hash = 1485633744)
    public AadharDetailSyncData() {
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

    public String getAadharNumber() {
        return this.aadharNumber;
    }
    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
    public String getAadharName() {
        return this.aadharName;
    }
    public void setAadharName(String aadharName) {
        this.aadharName = aadharName;
    }
    public String getAadharYOB() {
        return this.aadharYOB;
    }
    public void setAadharYOB(String aadharYOB) {
        this.aadharYOB = aadharYOB;
    }
    public String getAadharGender() {
        return this.aadharGender;
    }
    public void setAadharGender(String aadharGender) {
        this.aadharGender = aadharGender;
    }
    public String getAadharAddress() {
        return this.aadharAddress;
    }
    public void setAadharAddress(String aadharAddress) {
        this.aadharAddress = aadharAddress;
    }
    public String getAadharSyncStatus() {
        return this.aadharSyncStatus;
    }
    public void setAadharSyncStatus(String aadharSyncStatus) {
        this.aadharSyncStatus = aadharSyncStatus;
    }
    public String getAadharFatherName() {
        return this.aadharFatherName;
    }
    public void setAadharFatherName(String aadharFatherName) {
        this.aadharFatherName = aadharFatherName;
    }
    public byte[] getAadhaarImage() {
        return this.aadhaarImage;
    }
    public void setAadhaarImage(byte[] aadhaarImage) {
        this.aadhaarImage = aadhaarImage;
    }
    public String getAadhaarMobileNumber() {
        return this.aadhaarMobileNumber;
    }
    public void setAadhaarMobileNumber(String aadhaarMobileNumber) {
        this.aadhaarMobileNumber = aadhaarMobileNumber;
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
    public String getScanStatus() {
        return this.scanStatus;
    }
    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public byte[] getConcentForm() {
        return this.concentForm;
    }
    public void setConcentForm(byte[] concentForm) {
        this.concentForm = concentForm;
    }
}
