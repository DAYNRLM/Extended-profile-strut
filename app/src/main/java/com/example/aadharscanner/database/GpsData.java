package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GpsData {
   private String blockCode;
   private String gpCode;
   private String gpName;
   @Generated(hash = 465914237)
   public GpsData(String blockCode, String gpCode, String gpName) {
       this.blockCode = blockCode;
       this.gpCode = gpCode;
       this.gpName = gpName;
   }
   @Generated(hash = 1232323744)
   public GpsData() {
   }
   public String getBlockCode() {
       return this.blockCode;
   }
   public void setBlockCode(String blockCode) {
       this.blockCode = blockCode;
   }
   public String getGpCode() {
       return this.gpCode;
   }
   public void setGpCode(String gpCode) {
       this.gpCode = gpCode;
   }
   public String getGpName() {
       return this.gpName;
   }
   public void setGpName(String gpName) {
       this.gpName = gpName;
   }
}
