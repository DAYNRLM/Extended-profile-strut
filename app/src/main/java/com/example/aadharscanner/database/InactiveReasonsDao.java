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
 * DAO for table "INACTIVE_REASONS".
*/
public class InactiveReasonsDao extends AbstractDao<InactiveReasons, Void> {

    public static final String TABLENAME = "INACTIVE_REASONS";

    /**
     * Properties of entity InactiveReasons.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ReasonId = new Property(0, String.class, "reasonId", false, "REASON_ID");
        public final static Property Reason = new Property(1, String.class, "reason", false, "REASON");
    }


    public InactiveReasonsDao(DaoConfig config) {
        super(config);
    }
    
    public InactiveReasonsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INACTIVE_REASONS\" (" + //
                "\"REASON_ID\" TEXT," + // 0: reasonId
                "\"REASON\" TEXT);"); // 1: reason
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INACTIVE_REASONS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, InactiveReasons entity) {
        stmt.clearBindings();
 
        String reasonId = entity.getReasonId();
        if (reasonId != null) {
            stmt.bindString(1, reasonId);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(2, reason);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, InactiveReasons entity) {
        stmt.clearBindings();
 
        String reasonId = entity.getReasonId();
        if (reasonId != null) {
            stmt.bindString(1, reasonId);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(2, reason);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public InactiveReasons readEntity(Cursor cursor, int offset) {
        InactiveReasons entity = new InactiveReasons( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // reasonId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // reason
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, InactiveReasons entity, int offset) {
        entity.setReasonId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setReason(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(InactiveReasons entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(InactiveReasons entity) {
        return null;
    }

    @Override
    public boolean hasKey(InactiveReasons entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}