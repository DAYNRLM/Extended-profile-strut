package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity()
public class ShgMemberRegistrationSyncData {
    @Id(autoincrement = true)
            Long autoIncrement;
    String aadharNumber;
    String blockcode;
    String scanStatus;
    String gpcode;
    String villagecode;
    String shgcode;
    String shgMemberuniqueCode;
    String memberName;
    String memberGender;
    String memberFathername;
    String memberAddress;
    String memberYOB;
    String membermobileNumber;
    String memberDOB;
    String socialCatagory;
    String disablity;
    String religion;
    String pipCatagory;
    String leader;
    String educationStandard;
    String mamberDateOfJoining;
    String bankName;
    String bankBranch;
    String bankBranchCode;
    String bankNameCode;
    String ifscCode;
    String accountNumber;
    String aadharSeededAccount;
    String pmjjy;
    String pmsby;
    String lifeInsurance;
    String pensionScheme;
    String isShopKeeper;
    String seccNo;
    String otherEducation;
    String syncStatus;
    String pvgtStatus;
    String postalCode;

    private byte[] memberPhoto;
    private byte[] passbookPhoto;
    private byte[] concentForm;
    
    @Generated(hash = 1387104380)
    public ShgMemberRegistrationSyncData(Long autoIncrement, String aadharNumber,
            String blockcode, String scanStatus, String gpcode, String villagecode,
            String shgcode, String shgMemberuniqueCode, String memberName,
            String memberGender, String memberFathername, String memberAddress,
            String memberYOB, String membermobileNumber, String memberDOB,
            String socialCatagory, String disablity, String religion,
            String pipCatagory, String leader, String educationStandard,
            String mamberDateOfJoining, String bankName, String bankBranch,
            String bankBranchCode, String bankNameCode, String ifscCode,
            String accountNumber, String aadharSeededAccount, String pmjjy,
            String pmsby, String lifeInsurance, String pensionScheme,
            String isShopKeeper, String seccNo, String otherEducation,
            String syncStatus, String pvgtStatus, String postalCode,
            byte[] memberPhoto, byte[] passbookPhoto, byte[] concentForm) {
        this.autoIncrement = autoIncrement;
        this.aadharNumber = aadharNumber;
        this.blockcode = blockcode;
        this.scanStatus = scanStatus;
        this.gpcode = gpcode;
        this.villagecode = villagecode;
        this.shgcode = shgcode;
        this.shgMemberuniqueCode = shgMemberuniqueCode;
        this.memberName = memberName;
        this.memberGender = memberGender;
        this.memberFathername = memberFathername;
        this.memberAddress = memberAddress;
        this.memberYOB = memberYOB;
        this.membermobileNumber = membermobileNumber;
        this.memberDOB = memberDOB;
        this.socialCatagory = socialCatagory;
        this.disablity = disablity;
        this.religion = religion;
        this.pipCatagory = pipCatagory;
        this.leader = leader;
        this.educationStandard = educationStandard;
        this.mamberDateOfJoining = mamberDateOfJoining;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.bankBranchCode = bankBranchCode;
        this.bankNameCode = bankNameCode;
        this.ifscCode = ifscCode;
        this.accountNumber = accountNumber;
        this.aadharSeededAccount = aadharSeededAccount;
        this.pmjjy = pmjjy;
        this.pmsby = pmsby;
        this.lifeInsurance = lifeInsurance;
        this.pensionScheme = pensionScheme;
        this.isShopKeeper = isShopKeeper;
        this.seccNo = seccNo;
        this.otherEducation = otherEducation;
        this.syncStatus = syncStatus;
        this.pvgtStatus = pvgtStatus;
        this.postalCode = postalCode;
        this.memberPhoto = memberPhoto;
        this.passbookPhoto = passbookPhoto;
        this.concentForm = concentForm;
    }
    @Generated(hash = 1864026148)
    public ShgMemberRegistrationSyncData() {
    }
    public String getBlockcode() {
        return this.blockcode;
    }
    public void setBlockcode(String blockcode) {
        this.blockcode = blockcode;
    }
    public String getGpcode() {
        return this.gpcode;
    }
    public void setGpcode(String gpcode) {
        this.gpcode = gpcode;
    }
    public String getVillagecode() {
        return this.villagecode;
    }
    public void setVillagecode(String villagecode) {
        this.villagecode = villagecode;
    }
    public String getShgcode() {
        return this.shgcode;
    }
    public void setShgcode(String shgcode) {
        this.shgcode = shgcode;
    }
    public String getShgMemberuniqueCode() {
        return this.shgMemberuniqueCode;
    }
    public void setShgMemberuniqueCode(String shgMemberuniqueCode) {
        this.shgMemberuniqueCode = shgMemberuniqueCode;
    }
    public String getAadharNumber() {
        return this.aadharNumber;
    }
    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
    public String getMemberName() {
        return this.memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getMemberGender() {
        return this.memberGender;
    }
    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }
    public String getMemberFathername() {
        return this.memberFathername;
    }
    public void setMemberFathername(String memberFathername) {
        this.memberFathername = memberFathername;
    }
    public String getMemberAddress() {
        return this.memberAddress;
    }
    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }
    public String getMembermobileNumber() {
        return this.membermobileNumber;
    }
    public void setMembermobileNumber(String membermobileNumber) {
        this.membermobileNumber = membermobileNumber;
    }
    public String getMemberDOB() {
        return this.memberDOB;
    }
    public void setMemberDOB(String memberDOB) {
        this.memberDOB = memberDOB;
    }
    public String getSocialCatagory() {
        return this.socialCatagory;
    }
    public void setSocialCatagory(String socialCatagory) {
        this.socialCatagory = socialCatagory;
    }
    public String getDisablity() {
        return this.disablity;
    }
    public void setDisablity(String disablity) {
        this.disablity = disablity;
    }
    public String getReligion() {
        return this.religion;
    }
    public void setReligion(String religion) {
        this.religion = religion;
    }
    public String getPipCatagory() {
        return this.pipCatagory;
    }
    public void setPipCatagory(String pipCatagory) {
        this.pipCatagory = pipCatagory;
    }
    public String getLeader() {
        return this.leader;
    }
    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getEducationStandard() {
        return this.educationStandard;
    }
    public void setEducationStandard(String educationStandard) {
        this.educationStandard = educationStandard;
    }
    public String getMamberDateOfJoining() {
        return this.mamberDateOfJoining;
    }
    public void setMamberDateOfJoining(String mamberDateOfJoining) {
        this.mamberDateOfJoining = mamberDateOfJoining;
    }
    public String getBankName() {
        return this.bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankBranch() {
        return this.bankBranch;
    }
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    public String getIfscCode() {
        return this.ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAadharSeededAccount() {
        return this.aadharSeededAccount;
    }
    public void setAadharSeededAccount(String aadharSeededAccount) {
        this.aadharSeededAccount = aadharSeededAccount;
    }
    public String getPmjjy() {
        return this.pmjjy;
    }
    public void setPmjjy(String pmjjy) {
        this.pmjjy = pmjjy;
    }
    public String getPmsby() {
        return this.pmsby;
    }
    public void setPmsby(String pmsby) {
        this.pmsby = pmsby;
    }
    public String getLifeInsurance() {
        return this.lifeInsurance;
    }
    public void setLifeInsurance(String lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }
    public String getPensionScheme() {
        return this.pensionScheme;
    }
    public void setPensionScheme(String pensionScheme) {
        this.pensionScheme = pensionScheme;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    public String getBankBranchCode() {
        return this.bankBranchCode;
    }
    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }
    public String getBankNameCode() {
        return this.bankNameCode;
    }
    public void setBankNameCode(String bankNameCode) {
        this.bankNameCode = bankNameCode;
    }
    public String getMemberYOB() {
        return this.memberYOB;
    }
    public void setMemberYOB(String memberYOB) {
        this.memberYOB = memberYOB;
    }
    public String getIsShopKeeper() {
        return this.isShopKeeper;
    }
    public void setIsShopKeeper(String isShopKeeper) {
        this.isShopKeeper = isShopKeeper;
    }
    public String getSeccNo() {
        return this.seccNo;
    }
    public void setSeccNo(String seccNo) {
        this.seccNo = seccNo;
    }
    public String getOtherEducation() {
        return this.otherEducation;
    }
    public void setOtherEducation(String otherEducation) {
        this.otherEducation = otherEducation;
    }
    public Long getAutoIncrement() {
        return this.autoIncrement;
    }
    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    public byte[] getMemberPhoto() {
        return this.memberPhoto;
    }
    public void setMemberPhoto(byte[] memberPhoto) {
        this.memberPhoto = memberPhoto;
    }
    public byte[] getPassbookPhoto() {
        return this.passbookPhoto;
    }
    public void setPassbookPhoto(byte[] passbookPhoto) {
        this.passbookPhoto = passbookPhoto;
    }
    public String getPvgtStatus() {
        return this.pvgtStatus;
    }
    public void setPvgtStatus(String pvgtStatus) {
        this.pvgtStatus = pvgtStatus;
    }
    public String getScanStatus() {
        return this.scanStatus;
    }
    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public byte[] getConcentForm() {
        return this.concentForm;
    }
    public void setConcentForm(byte[] concentForm) {
        this.concentForm = concentForm;
    }

}
