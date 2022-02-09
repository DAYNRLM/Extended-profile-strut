package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ShgDetailData {
    @Id(autoincrement = true)
            private Long id;
    String villageCode;
    String shgCode;
    String shgEntityCode;
    String shgGroupName;
    String shgCurrentStatus;
    String shgActInactStatus;
    String shgMemberCount;


    @Generated(hash = 187383070)
    public ShgDetailData(Long id, String villageCode, String shgCode,
            String shgEntityCode, String shgGroupName, String shgCurrentStatus,
            String shgActInactStatus, String shgMemberCount) {
        this.id = id;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.shgEntityCode = shgEntityCode;
        this.shgGroupName = shgGroupName;
        this.shgCurrentStatus = shgCurrentStatus;
        this.shgActInactStatus = shgActInactStatus;
        this.shgMemberCount = shgMemberCount;
    }
    @Generated(hash = 231384780)
    public ShgDetailData() {
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
    public String getShgEntityCode() {
        return this.shgEntityCode;
    }
    public void setShgEntityCode(String shgEntityCode) {
        this.shgEntityCode = shgEntityCode;
    }
    public String getShgGroupName() {
        return this.shgGroupName;
    }
    public void setShgGroupName(String shgGroupName) {
        this.shgGroupName = shgGroupName;
    }
    public String getShgCurrentStatus() {
        return this.shgCurrentStatus;
    }
    public void setShgCurrentStatus(String shgCurrentStatus) {
        this.shgCurrentStatus = shgCurrentStatus;
    }
    public String getShgActInactStatus() {
        return this.shgActInactStatus;
    }
    public void setShgActInactStatus(String shgActInactStatus) {
        this.shgActInactStatus = shgActInactStatus;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShgMemberCount() {
        return this.shgMemberCount;
    }
    public void setShgMemberCount(String shgMemberCount) {
        this.shgMemberCount = shgMemberCount;
    }
}
