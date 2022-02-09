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
 * DAO for table "SHG_IN_ACTIV_SYNC_DATA".
*/
public class ShgInActivSyncDataDao extends AbstractDao<ShgInActivSyncData, Long> {

    public static final String TABLENAME = "SHG_IN_ACTIV_SYNC_DATA";

    /**
     * Properties of entity ShgInActivSyncData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AutoIncrement = new Property(0, Long.class, "autoIncrement", true, "_id");
        public final static Property BlockCode = new Property(1, String.class, "blockCode", false, "BLOCK_CODE");
        public final static Property GpCode = new Property(2, String.class, "gpCode", false, "GP_CODE");
        public final static Property VillageCode = new Property(3, String.class, "villageCode", false, "VILLAGE_CODE");
        public final static Property ShgCode = new Property(4, String.class, "shgCode", false, "SHG_CODE");
        public final static Property ShgMemberCode = new Property(5, String.class, "shgMemberCode", false, "SHG_MEMBER_CODE");
        public final static Property InactiveStatus = new Property(6, String.class, "inactiveStatus", false, "INACTIVE_STATUS");
        public final static Property SyncStatus = new Property(7, String.class, "syncStatus", false, "SYNC_STATUS");
        public final static Property SyncInactivReson = new Property(8, String.class, "syncInactivReson", false, "SYNC_INACTIV_RESON");
        public final static Property LoanStatus = new Property(9, String.class, "loanStatus", false, "LOAN_STATUS");
    }


    public ShgInActivSyncDataDao(DaoConfig config) {
        super(config);
    }
    
    public ShgInActivSyncDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHG_IN_ACTIV_SYNC_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: autoIncrement
                "\"BLOCK_CODE\" TEXT," + // 1: blockCode
                "\"GP_CODE\" TEXT," + // 2: gpCode
                "\"VILLAGE_CODE\" TEXT," + // 3: villageCode
                "\"SHG_CODE\" TEXT," + // 4: shgCode
                "\"SHG_MEMBER_CODE\" TEXT," + // 5: shgMemberCode
                "\"INACTIVE_STATUS\" TEXT," + // 6: inactiveStatus
                "\"SYNC_STATUS\" TEXT," + // 7: syncStatus
                "\"SYNC_INACTIV_RESON\" TEXT," + // 8: syncInactivReson
                "\"LOAN_STATUS\" TEXT);"); // 9: loanStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHG_IN_ACTIV_SYNC_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShgInActivSyncData entity) {
        stmt.clearBindings();
 
        Long autoIncrement = entity.getAutoIncrement();
        if (autoIncrement != null) {
            stmt.bindLong(1, autoIncrement);
        }
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(2, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(3, gpCode);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(4, villageCode);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(5, shgCode);
        }
 
        String shgMemberCode = entity.getShgMemberCode();
        if (shgMemberCode != null) {
            stmt.bindString(6, shgMemberCode);
        }
 
        String inactiveStatus = entity.getInactiveStatus();
        if (inactiveStatus != null) {
            stmt.bindString(7, inactiveStatus);
        }
 
        String syncStatus = entity.getSyncStatus();
        if (syncStatus != null) {
            stmt.bindString(8, syncStatus);
        }
 
        String syncInactivReson = entity.getSyncInactivReson();
        if (syncInactivReson != null) {
            stmt.bindString(9, syncInactivReson);
        }
 
        String loanStatus = entity.getLoanStatus();
        if (loanStatus != null) {
            stmt.bindString(10, loanStatus);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShgInActivSyncData entity) {
        stmt.clearBindings();
 
        Long autoIncrement = entity.getAutoIncrement();
        if (autoIncrement != null) {
            stmt.bindLong(1, autoIncrement);
        }
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(2, blockCode);
        }
 
        String gpCode = entity.getGpCode();
        if (gpCode != null) {
            stmt.bindString(3, gpCode);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(4, villageCode);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(5, shgCode);
        }
 
        String shgMemberCode = entity.getShgMemberCode();
        if (shgMemberCode != null) {
            stmt.bindString(6, shgMemberCode);
        }
 
        String inactiveStatus = entity.getInactiveStatus();
        if (inactiveStatus != null) {
            stmt.bindString(7, inactiveStatus);
        }
 
        String syncStatus = entity.getSyncStatus();
        if (syncStatus != null) {
            stmt.bindString(8, syncStatus);
        }
 
        String syncInactivReson = entity.getSyncInactivReson();
        if (syncInactivReson != null) {
            stmt.bindString(9, syncInactivReson);
        }
 
        String loanStatus = entity.getLoanStatus();
        if (loanStatus != null) {
            stmt.bindString(10, loanStatus);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ShgInActivSyncData readEntity(Cursor cursor, int offset) {
        ShgInActivSyncData entity = new ShgInActivSyncData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // autoIncrement
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // blockCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // gpCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // villageCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // shgCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // shgMemberCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // inactiveStatus
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // syncStatus
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // syncInactivReson
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // loanStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShgInActivSyncData entity, int offset) {
        entity.setAutoIncrement(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBlockCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGpCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVillageCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setShgCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setShgMemberCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setInactiveStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSyncStatus(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSyncInactivReson(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLoanStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ShgInActivSyncData entity, long rowId) {
        entity.setAutoIncrement(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ShgInActivSyncData entity) {
        if(entity != null) {
            return entity.getAutoIncrement();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShgInActivSyncData entity) {
        return entity.getAutoIncrement() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
