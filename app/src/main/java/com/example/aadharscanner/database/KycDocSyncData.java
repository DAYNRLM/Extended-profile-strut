package com.example.aadharscanner.database;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class KycDocSyncData {
    @Nullable
    @Id(autoincrement = true)
    private Long id=null;
    private String memberCode;
    private String village_code;
    private String shgCode;
    private String docId;
    private String docNo;
    private byte[] docFrontImage;
    private byte[] docBackImage;
    private String syncStatus;

    @Generated(hash = 2007543430)
    public KycDocSyncData(Long id, String memberCode, String village_code,
            String shgCode, String docId, String docNo, byte[] docFrontImage,
            byte[] docBackImage, String syncStatus) {
        this.id = id;
        this.memberCode = memberCode;
        this.village_code = village_code;
        this.shgCode = shgCode;
        this.docId = docId;
        this.docNo = docNo;
        this.docFrontImage = docFrontImage;
        this.docBackImage = docBackImage;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 633521121)
    public KycDocSyncData() {
    }
    public String getMemberCode() {
        return this.memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public String getVillage_code() {
        return this.village_code;
    }
    public void setVillage_code(String village_code) {
        this.village_code = village_code;
    }
    public String getDocId() {
        return this.docId;
    }
    public void setDocId(String docId) {
        this.docId = docId;
    }
    public String getDocNo() {
        return this.docNo;
    }
    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }
    public byte[] getDocFrontImage() {
        return this.docFrontImage;
    }
    public void setDocFrontImage(byte[] docFrontImage) {
        this.docFrontImage = docFrontImage;
    }
    public byte[] getDocBackImage() {
        return this.docBackImage;
    }
    public void setDocBackImage(byte[] docBackImage) {
        this.docBackImage = docBackImage;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    @Nullable
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }

}
