package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BankType {
    String bankType;
    String bankTypeCode;
    @Generated(hash = 905135865)
    public BankType(String bankType, String bankTypeCode) {
        this.bankType = bankType;
        this.bankTypeCode = bankTypeCode;
    }
    @Generated(hash = 1352759747)
    public BankType() {
    }
    public String getBankType() {
        return this.bankType;
    }
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    public String getBankTypeCode() {
        return this.bankTypeCode;
    }
    public void setBankTypeCode(String bankTypeCode) {
        this.bankTypeCode = bankTypeCode;
    }
}
