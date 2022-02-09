package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShgLoanStatusData {
    private String loanStatus;
    private String loanStatusId;


    @Generated(hash = 276297912)
    public ShgLoanStatusData(String loanStatus, String loanStatusId) {
        this.loanStatus = loanStatus;
        this.loanStatusId = loanStatusId;
    }

    @Generated(hash = 447990409)
    public ShgLoanStatusData() {
    }

    public String getLoanStatus() {
        return this.loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanStatusId() {
        return this.loanStatusId;
    }

    public void setLoanStatusId(String loanStatusId) {
        this.loanStatusId = loanStatusId;
    }

    
}
