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
 * DAO for table "SHG_DETAIL_DATA".
*/
public class ShgDetailDataDao extends AbstractDao<ShgDetailData, Long> {

    public static final String TABLENAME = "SHG_DETAIL_DATA";

    /**
     * Properties of entity ShgDetailData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property VillageCode = new Property(1, String.class, "villageCode", false, "VILLAGE_CODE");
        public final static Property ShgCode = new Property(2, String.class, "shgCode", false, "SHG_CODE");
        public final static Property ShgEntityCode = new Property(3, String.class, "shgEntityCode", false, "SHG_ENTITY_CODE");
        public final static Property ShgGroupName = new Property(4, String.class, "shgGroupName", false, "SHG_GROUP_NAME");
        public final static Property ShgCurrentStatus = new Property(5, String.class, "shgCurrentStatus", false, "SHG_CURRENT_STATUS");
        public final static Property ShgActInactStatus = new Property(6, String.class, "shgActInactStatus", false, "SHG_ACT_INACT_STATUS");
        public final static Property ShgMemberCount = new Property(7, String.class, "shgMemberCount", false, "SHG_MEMBER_COUNT");
    }


    public ShgDetailDataDao(DaoConfig config) {
        super(config);
    }
    
    public ShgDetailDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHG_DETAIL_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"VILLAGE_CODE\" TEXT," + // 1: villageCode
                "\"SHG_CODE\" TEXT," + // 2: shgCode
                "\"SHG_ENTITY_CODE\" TEXT," + // 3: shgEntityCode
                "\"SHG_GROUP_NAME\" TEXT," + // 4: shgGroupName
                "\"SHG_CURRENT_STATUS\" TEXT," + // 5: shgCurrentStatus
                "\"SHG_ACT_INACT_STATUS\" TEXT," + // 6: shgActInactStatus
                "\"SHG_MEMBER_COUNT\" TEXT);"); // 7: shgMemberCount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHG_DETAIL_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShgDetailData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(2, villageCode);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(3, shgCode);
        }
 
        String shgEntityCode = entity.getShgEntityCode();
        if (shgEntityCode != null) {
            stmt.bindString(4, shgEntityCode);
        }
 
        String shgGroupName = entity.getShgGroupName();
        if (shgGroupName != null) {
            stmt.bindString(5, shgGroupName);
        }
 
        String shgCurrentStatus = entity.getShgCurrentStatus();
        if (shgCurrentStatus != null) {
            stmt.bindString(6, shgCurrentStatus);
        }
 
        String shgActInactStatus = entity.getShgActInactStatus();
        if (shgActInactStatus != null) {
            stmt.bindString(7, shgActInactStatus);
        }
 
        String shgMemberCount = entity.getShgMemberCount();
        if (shgMemberCount != null) {
            stmt.bindString(8, shgMemberCount);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShgDetailData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String villageCode = entity.getVillageCode();
        if (villageCode != null) {
            stmt.bindString(2, villageCode);
        }
 
        String shgCode = entity.getShgCode();
        if (shgCode != null) {
            stmt.bindString(3, shgCode);
        }
 
        String shgEntityCode = entity.getShgEntityCode();
        if (shgEntityCode != null) {
            stmt.bindString(4, shgEntityCode);
        }
 
        String shgGroupName = entity.getShgGroupName();
        if (shgGroupName != null) {
            stmt.bindString(5, shgGroupName);
        }
 
        String shgCurrentStatus = entity.getShgCurrentStatus();
        if (shgCurrentStatus != null) {
            stmt.bindString(6, shgCurrentStatus);
        }
 
        String shgActInactStatus = entity.getShgActInactStatus();
        if (shgActInactStatus != null) {
            stmt.bindString(7, shgActInactStatus);
        }
 
        String shgMemberCount = entity.getShgMemberCount();
        if (shgMemberCount != null) {
            stmt.bindString(8, shgMemberCount);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ShgDetailData readEntity(Cursor cursor, int offset) {
        ShgDetailData entity = new ShgDetailData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // villageCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // shgCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // shgEntityCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // shgGroupName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // shgCurrentStatus
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // shgActInactStatus
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // shgMemberCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShgDetailData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVillageCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setShgCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setShgEntityCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setShgGroupName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setShgCurrentStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setShgActInactStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setShgMemberCount(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ShgDetailData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ShgDetailData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShgDetailData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
