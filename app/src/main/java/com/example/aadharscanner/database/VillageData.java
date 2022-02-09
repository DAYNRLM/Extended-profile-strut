package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VillageData {
   private String blockCode;
   private String gpCode;
   private String villageCode;
   private String villageName;
   @Generated(hash = 2104011329)
   public VillageData(String blockCode, String gpCode, String villageCode,
           String villageName) {
       this.blockCode = blockCode;
       this.gpCode = gpCode;
       this.villageCode = villageCode;
       this.villageName = villageName;
   }
   @Generated(hash = 1867084313)
   public VillageData() {
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
   public String getVillageCode() {
       return this.villageCode;
   }
   public void setVillageCode(String villageCode) {
       this.villageCode = villageCode;
   }
   public String getVillageName() {
       return this.villageName;
   }
   public void setVillageName(String villageName) {
       this.villageName = villageName;
   }
   
}
