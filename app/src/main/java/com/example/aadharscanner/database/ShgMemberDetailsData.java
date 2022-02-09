package com.example.aadharscanner.database;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ShgMemberDetailsData {
    @Nullable
    @Id(autoincrement = true)
    private Long id=null;
    String villageCode;
    String shgCode;
    String shgMemberCode;
    String shgMemberEntityCode;
    String shgMemberName;
    String activeStatus;
    String shgMemberAadharStatus;
    String sggMemberAccountStatus;
    String kycStatus;
    String memberCurrentStatus;
    String syncStatus;
    String memAadharCurrentStatus;
    String memBankAccCurrentStatus;
    String belonging_name;
    String dob;
    String gender;
    String kycDocId;
    String leadership;
    String updateMemberCurrentStatus;
    
    @Generated(hash = 755886395)
    public ShgMemberDetailsData(Long id, String villageCode, String shgCode,
            String shgMemberCode, String shgMemberEntityCode, String shgMemberName,
            String activeStatus, String shgMemberAadharStatus,
            String sggMemberAccountStatus, String kycStatus,
            String memberCurrentStatus, String syncStatus,
            String memAadharCurrentStatus, String memBankAccCurrentStatus,
            String belonging_name, String dob, String gender, String kycDocId,
            String leadership, String updateMemberCurrentStatus) {
        this.id = id;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
        this.shgMemberEntityCode = shgMemberEntityCode;
        this.shgMemberName = shgMemberName;
        this.activeStatus = activeStatus;
        this.shgMemberAadharStatus = shgMemberAadharStatus;
        this.sggMemberAccountStatus = sggMemberAccountStatus;
        this.kycStatus = kycStatus;
        this.memberCurrentStatus = memberCurrentStatus;
        this.syncStatus = syncStatus;
        this.memAadharCurrentStatus = memAadharCurrentStatus;
        this.memBankAccCurrentStatus = memBankAccCurrentStatus;
        this.belonging_name = belonging_name;
        this.dob = dob;
        this.gender = gender;
        this.kycDocId = kycDocId;
        this.leadership = leadership;
        this.updateMemberCurrentStatus = updateMemberCurrentStatus;
    }
    @Generated(hash = 361685632)
    public ShgMemberDetailsData() {
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
    public String getShgMemberEntityCode() {
        return this.shgMemberEntityCode;
    }
    public void setShgMemberEntityCode(String shgMemberEntityCode) {
        this.shgMemberEntityCode = shgMemberEntityCode;
    }
    public String getShgMemberName() {
        return this.shgMemberName;
    }
    public void setShgMemberName(String shgMemberName) {
        this.shgMemberName = shgMemberName;
    }
    public String getShgMemberAadharStatus() {
        return this.shgMemberAadharStatus;
    }
    public void setShgMemberAadharStatus(String shgMemberAadharStatus) {
        this.shgMemberAadharStatus = shgMemberAadharStatus;
    }
    public String getSggMemberAccountStatus() {
        return this.sggMemberAccountStatus;
    }
    public void setSggMemberAccountStatus(String sggMemberAccountStatus) {
        this.sggMemberAccountStatus = sggMemberAccountStatus;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    @Nullable
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getActiveStatus() {
        return this.activeStatus;
    }
    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    public String getKycStatus() {
        return this.kycStatus;
    }
    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }
    public String getMemberCurrentStatus() {
        return this.memberCurrentStatus;
    }
    public void setMemberCurrentStatus(String memberCurrentStatus) {
        this.memberCurrentStatus = memberCurrentStatus;
    }
    public String getMemAadharCurrentStatus() {
        return this.memAadharCurrentStatus;
    }
    public void setMemAadharCurrentStatus(String memAadharCurrentStatus) {
        this.memAadharCurrentStatus = memAadharCurrentStatus;
    }
    public String getMemBankAccCurrentStatus() {
        return this.memBankAccCurrentStatus;
    }
    public void setMemBankAccCurrentStatus(String memBankAccCurrentStatus) {
        this.memBankAccCurrentStatus = memBankAccCurrentStatus;
    }
    public String getBelonging_name() {
        return this.belonging_name;
    }
    public void setBelonging_name(String belonging_name) {
        this.belonging_name = belonging_name;
    }
    public String getDob() {
        return this.dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getKycDocId() {
        return this.kycDocId;
    }
    public void setKycDocId(String kycDocId) {
        this.kycDocId = kycDocId;
    }
    public String getLeadership() {
        return this.leadership;
    }
    public void setLeadership(String leadership) {
        this.leadership = leadership;
    }
    public String getUpdateMemberCurrentStatus() {
        return this.updateMemberCurrentStatus;
    }
    public void setUpdateMemberCurrentStatus(String updateMemberCurrentStatus) {
        this.updateMemberCurrentStatus = updateMemberCurrentStatus;
    }
}
