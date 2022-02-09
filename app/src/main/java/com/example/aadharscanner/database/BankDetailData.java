package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BankDetailData {
    String bankTypeCode;
    String bankCode;
    String entityCode;
    String bankName;
    String bankLevelCode;
    String bankAccStatus;
    @Generated(hash = 1700445998)
    public BankDetailData(String bankTypeCode, String bankCode, String entityCode,
            String bankName, String bankLevelCode, String bankAccStatus) {
        this.bankTypeCode = bankTypeCode;
        this.bankCode = bankCode;
        this.entityCode = entityCode;
        this.bankName = bankName;
        this.bankLevelCode = bankLevelCode;
        this.bankAccStatus = bankAccStatus;
    }
    @Generated(hash = 1020946585)
    public BankDetailData() {
    }
    public String getBankTypeCode() {
        return this.bankTypeCode;
    }
    public void setBankTypeCode(String bankTypeCode) {
        this.bankTypeCode = bankTypeCode;
    }
    public String getBankCode() {
        return this.bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getEntityCode() {
        return this.entityCode;
    }
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    public String getBankName() {
        return this.bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankLevelCode() {
        return this.bankLevelCode;
    }
    public void setBankLevelCode(String bankLevelCode) {
        this.bankLevelCode = bankLevelCode;
    }
    public String getBankAccStatus() {
        return this.bankAccStatus;
    }
    public void setBankAccStatus(String bankAccStatus) {
        this.bankAccStatus = bankAccStatus;
    }
}
