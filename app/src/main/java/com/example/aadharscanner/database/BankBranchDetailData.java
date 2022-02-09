package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BankBranchDetailData {
    String bankCode;
    String ifscCode;
    String bankBranchName;
    String bankBranchEntityCode;
    String bankBranchCode;
    @Generated(hash = 1610838966)
    public BankBranchDetailData(String bankCode, String ifscCode,
            String bankBranchName, String bankBranchEntityCode,
            String bankBranchCode) {
        this.bankCode = bankCode;
        this.ifscCode = ifscCode;
        this.bankBranchName = bankBranchName;
        this.bankBranchEntityCode = bankBranchEntityCode;
        this.bankBranchCode = bankBranchCode;
    }
    @Generated(hash = 1868888413)
    public BankBranchDetailData() {
    }
    public String getBankCode() {
        return this.bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getIfscCode() {
        return this.ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getBankBranchName() {
        return this.bankBranchName;
    }
    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }
    public String getBankBranchEntityCode() {
        return this.bankBranchEntityCode;
    }
    public void setBankBranchEntityCode(String bankBranchEntityCode) {
        this.bankBranchEntityCode = bankBranchEntityCode;
    }
    public String getBankBranchCode() {
        return this.bankBranchCode;
    }
    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }
}
