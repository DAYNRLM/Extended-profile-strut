package com.example.aadharscanner.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BANK_BRANCH_DETAIL_DATA".
*/
public class BankBranchDetailDataDao extends AbstractDao<BankBranchDetailData, Void> {

    public static final String TABLENAME = "BANK_BRANCH_DETAIL_DATA";

    /**
     * Properties of entity BankBranchDetailData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BankCode = new Property(0, String.class, "bankCode", false, "BANK_CODE");
        public final static Property IfscCode = new Property(1, String.class, "ifscCode", false, "IFSC_CODE");
        public final static Property BankBranchName = new Property(2, String.class, "bankBranchName", false, "BANK_BRANCH_NAME");
        public final static Property BankBranchEntityCode = new Property(3, String.class, "bankBranchEntityCode", false, "BANK_BRANCH_ENTITY_CODE");
        public final static Property BankBranchCode = new Property(4, String.class, "bankBranchCode", false, "BANK_BRANCH_CODE");
    }


    public BankBranchDetailDataDao(DaoConfig config) {
        super(config);
    }
    
    public BankBranchDetailDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BANK_BRANCH_DETAIL_DATA\" (" + //
                "\"BANK_CODE\" TEXT," + // 0: bankCode
                "\"IFSC_CODE\" TEXT," + // 1: ifscCode
                "\"BANK_BRANCH_NAME\" TEXT," + // 2: bankBranchName
                "\"BANK_BRANCH_ENTITY_CODE\" TEXT," + // 3: bankBranchEntityCode
                "\"BANK_BRANCH_CODE\" TEXT);"); // 4: bankBranchCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BANK_BRANCH_DETAIL_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BankBranchDetailData entity) {
        stmt.clearBindings();
 
        String bankCode = entity.getBankCode();
        if (bankCode != null) {
            stmt.bindString(1, bankCode);
        }
 
        String ifscCode = entity.getIfscCode();
        if (ifscCode != null) {
            stmt.bindString(2, ifscCode);
        }
 
        String bankBranchName = entity.getBankBranchName();
        if (bankBranchName != null) {
            stmt.bindString(3, bankBranchName);
        }
 
        String bankBranchEntityCode = entity.getBankBranchEntityCode();
        if (bankBranchEntityCode != null) {
            stmt.bindString(4, bankBranchEntityCode);
        }
 
        String bankBranchCode = entity.getBankBranchCode();
        if (bankBranchCode != null) {
            stmt.bindString(5, bankBranchCode);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BankBranchDetailData entity) {
        stmt.clearBindings();
 
        String bankCode = entity.getBankCode();
        if (bankCode != null) {
            stmt.bindString(1, bankCode);
        }
 
        String ifscCode = entity.getIfscCode();
        if (ifscCode != null) {
            stmt.bindString(2, ifscCode);
        }
 
        String bankBranchName = entity.getBankBranchName();
        if (bankBranchName != null) {
            stmt.bindString(3, bankBranchName);
        }
 
        String bankBranchEntityCode = entity.getBankBranchEntityCode();
        if (bankBranchEntityCode != null) {
            stmt.bindString(4, bankBranchEntityCode);
        }
 
        String bankBranchCode = entity.getBankBranchCode();
        if (bankBranchCode != null) {
            stmt.bindString(5, bankBranchCode);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public BankBranchDetailData readEntity(Cursor cursor, int offset) {
        BankBranchDetailData entity = new BankBranchDetailData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // bankCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ifscCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bankBranchName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bankBranchEntityCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // bankBranchCode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BankBranchDetailData entity, int offset) {
        entity.setBankCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIfscCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBankBranchName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBankBranchEntityCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBankBranchCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(BankBranchDetailData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(BankBranchDetailData entity) {
        return null;
    }

    @Override
    public boolean hasKey(BankBranchDetailData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}