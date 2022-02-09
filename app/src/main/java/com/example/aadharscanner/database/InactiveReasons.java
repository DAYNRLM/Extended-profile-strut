package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InactiveReasons {
    private String reasonId;
    private String reason;
    @Generated(hash = 1719150874)
    public InactiveReasons(String reasonId, String reason) {
        this.reasonId = reasonId;
        this.reason = reason;
    }
    @Generated(hash = 1863915739)
    public InactiveReasons() {
    }
    public String getReasonId() {
        return this.reasonId;
    }
    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
