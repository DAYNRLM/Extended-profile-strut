package com.example.aadharscanner.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class KycDocData {

    private String docId;
    private String docName;
    private String docType;
    private String docNolength;
    private String docInputType;

    @Generated(hash = 553658845)
    public KycDocData(String docId, String docName, String docType,
            String docNolength, String docInputType) {
        this.docId = docId;
        this.docName = docName;
        this.docType = docType;
        this.docNolength = docNolength;
        this.docInputType = docInputType;
    }

    @Generated(hash = 408061729)
    public KycDocData() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNolength() {
        return docNolength;
    }

    public void setDocNolength(String docNolength) {
        this.docNolength = docNolength;
    }

    public String getDocInputType() {
        return docInputType;
    }

    public void setDocInputType(String docInputType) {
        this.docInputType = docInputType;
    }
}
