package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MemberLoanStatusData {
    private String loanStatus;
    private String loanStatusId;

    @Generated(hash = 813094340)
    public MemberLoanStatusData(String loanStatus, String loanStatusId) {
        this.loanStatus = loanStatus;
        this.loanStatusId = loanStatusId;
    }

    @Generated(hash = 845741840)
    public MemberLoanStatusData() {
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
