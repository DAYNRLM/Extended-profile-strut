package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BlockDaoData {
    String blockCode;
    String blockname;
    @Generated(hash = 1832498172)
    public BlockDaoData(String blockCode, String blockname) {
        this.blockCode = blockCode;
        this.blockname = blockname;
    }
    @Generated(hash = 1045559843)
    public BlockDaoData() {
    }
    public String getBlockCode() {
        return this.blockCode;
    }
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
    public String getBlockname() {
        return this.blockname;
    }
    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }
}
