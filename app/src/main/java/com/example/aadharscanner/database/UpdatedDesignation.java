package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UpdatedDesignation {
    @Id(autoincrement = true)
    private Long id;
    private String designation;
    private String shgCode;
    @Generated(hash = 379975370)
    public UpdatedDesignation(Long id, String designation, String shgCode) {
        this.id = id;
        this.designation = designation;
        this.shgCode = shgCode;
    }
    @Generated(hash = 1553400336)
    public UpdatedDesignation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDesignation() {
        return this.designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }

}
