package com.yc.verbaltalk.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yc.verbaltalk.model.bean.LoveHealDetDetailsBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOVE_HEAL_DET_DETAILS_BEAN".
*/
public class LoveHealDetDetailsBeanDao extends AbstractDao<LoveHealDetDetailsBean, Long> {

    public static final String TABLENAME = "LOVE_HEAL_DET_DETAILS_BEAN";

    /**
     * Properties of entity LoveHealDetDetailsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Love_id = new Property(1, int.class, "love_id", false, "LOVE_ID");
        public final static Property Dialogue_id = new Property(2, int.class, "dialogue_id", false, "DIALOGUE_ID");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Ans_sex = new Property(4, String.class, "ans_sex", false, "ANS_SEX");
        public final static Property SaveTime = new Property(5, long.class, "saveTime", false, "SAVE_TIME");
        public final static Property Title = new Property(6, String.class, "title", false, "TITLE");
    }


    public LoveHealDetDetailsBeanDao(DaoConfig config) {
        super(config);
    }
    
    public LoveHealDetDetailsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOVE_HEAL_DET_DETAILS_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LOVE_ID\" INTEGER NOT NULL ," + // 1: love_id
                "\"DIALOGUE_ID\" INTEGER NOT NULL ," + // 2: dialogue_id
                "\"CONTENT\" TEXT," + // 3: content
                "\"ANS_SEX\" TEXT," + // 4: ans_sex
                "\"SAVE_TIME\" INTEGER NOT NULL ," + // 5: saveTime
                "\"TITLE\" TEXT);"); // 6: title
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_LOVE_HEAL_DET_DETAILS_BEAN_LOVE_ID ON \"LOVE_HEAL_DET_DETAILS_BEAN\"" +
                " (\"LOVE_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOVE_HEAL_DET_DETAILS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoveHealDetDetailsBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLove_id());
        stmt.bindLong(3, entity.getDialogue_id());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String ans_sex = entity.getAns_sex();
        if (ans_sex != null) {
            stmt.bindString(5, ans_sex);
        }
        stmt.bindLong(6, entity.getSaveTime());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoveHealDetDetailsBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLove_id());
        stmt.bindLong(3, entity.getDialogue_id());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String ans_sex = entity.getAns_sex();
        if (ans_sex != null) {
            stmt.bindString(5, ans_sex);
        }
        stmt.bindLong(6, entity.getSaveTime());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LoveHealDetDetailsBean readEntity(Cursor cursor, int offset) {
        LoveHealDetDetailsBean entity = new LoveHealDetDetailsBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // love_id
            cursor.getInt(offset + 2), // dialogue_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ans_sex
            cursor.getLong(offset + 5), // saveTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // title
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoveHealDetDetailsBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLove_id(cursor.getInt(offset + 1));
        entity.setDialogue_id(cursor.getInt(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAns_sex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSaveTime(cursor.getLong(offset + 5));
        entity.setTitle(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoveHealDetDetailsBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoveHealDetDetailsBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoveHealDetDetailsBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
