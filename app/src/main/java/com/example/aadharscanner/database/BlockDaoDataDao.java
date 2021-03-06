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
 * DAO for table "BLOCK_DAO_DATA".
*/
public class BlockDaoDataDao extends AbstractDao<BlockDaoData, Void> {

    public static final String TABLENAME = "BLOCK_DAO_DATA";

    /**
     * Properties of entity BlockDaoData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BlockCode = new Property(0, String.class, "blockCode", false, "BLOCK_CODE");
        public final static Property Blockname = new Property(1, String.class, "blockname", false, "BLOCKNAME");
    }


    public BlockDaoDataDao(DaoConfig config) {
        super(config);
    }
    
    public BlockDaoDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BLOCK_DAO_DATA\" (" + //
                "\"BLOCK_CODE\" TEXT," + // 0: blockCode
                "\"BLOCKNAME\" TEXT);"); // 1: blockname
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BLOCK_DAO_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BlockDaoData entity) {
        stmt.clearBindings();
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(1, blockCode);
        }
 
        String blockname = entity.getBlockname();
        if (blockname != null) {
            stmt.bindString(2, blockname);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BlockDaoData entity) {
        stmt.clearBindings();
 
        String blockCode = entity.getBlockCode();
        if (blockCode != null) {
            stmt.bindString(1, blockCode);
        }
 
        String blockname = entity.getBlockname();
        if (blockname != null) {
            stmt.bindString(2, blockname);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public BlockDaoData readEntity(Cursor cursor, int offset) {
        BlockDaoData entity = new BlockDaoData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // blockCode
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // blockname
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BlockDaoData entity, int offset) {
        entity.setBlockCode(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setBlockname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(BlockDaoData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(BlockDaoData entity) {
        return null;
    }

    @Override
    public boolean hasKey(BlockDaoData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
