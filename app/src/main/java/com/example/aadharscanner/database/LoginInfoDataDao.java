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
 * DAO for table "LOGIN_INFO_DATA".
*/
public class LoginInfoDataDao extends AbstractDao<LoginInfoData, Void> {

    public static final String TABLENAME = "LOGIN_INFO_DATA";

    /**
     * Properties of entity LoginInfoData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LoginId = new Property(0, String.class, "loginId", false, "LOGIN_ID");
        public final static Property Password = new Property(1, String.class, "password", false, "PASSWORD");
        public final static Property AppVersion = new Property(2, String.class, "appVersion", false, "APP_VERSION");
        public final static Property ServerDateTime = new Property(3, String.class, "serverDateTime", false, "SERVER_DATE_TIME");
        public final static Property LanguageId = new Property(4, String.class, "languageId", false, "LANGUAGE_ID");
        public final static Property LogOutDays = new Property(5, String.class, "logOutDays", false, "LOG_OUT_DAYS");
        public final static Property MobileNumber = new Property(6, String.class, "mobileNumber", false, "MOBILE_NUMBER");
        public final static Property State_code = new Property(7, String.class, "state_code", false, "STATE_CODE");
        public final static Property State_short_name = new Property(8, String.class, "state_short_name", false, "STATE_SHORT_NAME");
        public final static Property PvgtStatus = new Property(9, String.class, "pvgtStatus", false, "PVGT_STATUS");
    }


    public LoginInfoDataDao(DaoConfig config) {
        super(config);
    }
    
    public LoginInfoDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOGIN_INFO_DATA\" (" + //
                "\"LOGIN_ID\" TEXT," + // 0: loginId
                "\"PASSWORD\" TEXT," + // 1: password
                "\"APP_VERSION\" TEXT," + // 2: appVersion
                "\"SERVER_DATE_TIME\" TEXT," + // 3: serverDateTime
                "\"LANGUAGE_ID\" TEXT," + // 4: languageId
                "\"LOG_OUT_DAYS\" TEXT," + // 5: logOutDays
                "\"MOBILE_NUMBER\" TEXT," + // 6: mobileNumber
                "\"STATE_CODE\" TEXT," + // 7: state_code
                "\"STATE_SHORT_NAME\" TEXT," + // 8: state_short_name
                "\"PVGT_STATUS\" TEXT);"); // 9: pvgtStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOGIN_INFO_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginInfoData entity) {
        stmt.clearBindings();
 
        String loginId = entity.getLoginId();
        if (loginId != null) {
            stmt.bindString(1, loginId);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(2, password);
        }
 
        String appVersion = entity.getAppVersion();
        if (appVersion != null) {
            stmt.bindString(3, appVersion);
        }
 
        String serverDateTime = entity.getServerDateTime();
        if (serverDateTime != null) {
            stmt.bindString(4, serverDateTime);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(5, languageId);
        }
 
        String logOutDays = entity.getLogOutDays();
        if (logOutDays != null) {
            stmt.bindString(6, logOutDays);
        }
 
        String mobileNumber = entity.getMobileNumber();
        if (mobileNumber != null) {
            stmt.bindString(7, mobileNumber);
        }
 
        String state_code = entity.getState_code();
        if (state_code != null) {
            stmt.bindString(8, state_code);
        }
 
        String state_short_name = entity.getState_short_name();
        if (state_short_name != null) {
            stmt.bindString(9, state_short_name);
        }
 
        String pvgtStatus = entity.getPvgtStatus();
        if (pvgtStatus != null) {
            stmt.bindString(10, pvgtStatus);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginInfoData entity) {
        stmt.clearBindings();
 
        String loginId = entity.getLoginId();
        if (loginId != null) {
            stmt.bindString(1, loginId);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(2, password);
        }
 
        String appVersion = entity.getAppVersion();
        if (appVersion != null) {
            stmt.bindString(3, appVersion);
        }
 
        String serverDateTime = entity.getServerDateTime();
        if (serverDateTime != null) {
            stmt.bindString(4, serverDateTime);
        }
 
        String languageId = entity.getLanguageId();
        if (languageId != null) {
            stmt.bindString(5, languageId);
        }
 
        String logOutDays = entity.getLogOutDays();
        if (logOutDays != null) {
            stmt.bindString(6, logOutDays);
        }
 
        String mobileNumber = entity.getMobileNumber();
        if (mobileNumber != null) {
            stmt.bindString(7, mobileNumber);
        }
 
        String state_code = entity.getState_code();
        if (state_code != null) {
            stmt.bindString(8, state_code);
        }
 
        String state_short_name = entity.getState_short_name();
        if (state_short_name != null) {
            stmt.bindString(9, state_short_name);
        }
 
        String pvgtStatus = entity.getPvgtStatus();
        if (pvgtStatus != null) {
            stmt.bindString(10, pvgtStatus);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public LoginInfoData readEntity(Cursor cursor, int offset) {
        LoginInfoData entity = new LoginInfoData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // loginId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // password
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // appVersion
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // serverDateTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // languageId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // logOutDays
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mobileNumber
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // state_code
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // state_short_name
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // pvgtStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginInfoData entity, int offset) {
        entity.setLoginId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPassword(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAppVersion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setServerDateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLanguageId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLogOutDays(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMobileNumber(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setState_code(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setState_short_name(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPvgtStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(LoginInfoData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(LoginInfoData entity) {
        return null;
    }

    @Override
    public boolean hasKey(LoginInfoData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}