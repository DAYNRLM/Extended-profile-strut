package com.example.aadharscanner.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddMemberAadharDetail {
    String uidNumber;

    @Generated(hash = 1687337173)
    public AddMemberAadharDetail(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    @Generated(hash = 580712094)
    public AddMemberAadharDetail() {
    }

    public String getUidNumber() {
        return this.uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }
}
