package de.byte_artist.quickmemoplus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.byte_artist.quickmemoplus.entity.MemoGroupEntity;

class DbModel extends SQLiteOpenHelper {

    static final Integer DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "quick_memo_plus.db";

    static final String TABLE_MEMO = "memo";
    static final String TABLE_MEMO_GROUP = "memo_group";
    private static final String TABLE_PREFERENCES = "preferences";

    static final String COLUMN_MEMO_ID = "memo_id";
    static final String COLUMN_MEMO_TEXT = "memo_text";
    static final String COLUMN_MEMO_GROUP_FK = "memo_group_fk";
    static final String COLUMN_MEMO_DELETED = "memo_deleted";
    static final String COLUMN_MEMO_CLOSED = "memo_closed";
    static final String COLUMN_MEMO_CREATED = "memo_created";
    static final String COLUMN_MEMO_MODIFIED = "memo_modified";

    static final String COLUMN_MEMO_GROUP_ID = "memo_group_id";
    static final String COLUMN_MEMO_GROUP_NAME = "memo_group_name";
    static final String COLUMN_MEMO_GROUP_ORDER = "memo_group_order";
    static final String COLUMN_MEMO_GROUP_ICON = "memo_group_icon";
    static final String COLUMN_MEMO_GROUP_COLOR = "memo_group_color";
    static final String COLUMN_MEMO_GROUP_DELETED = "memo_group_deleted";
    static final String COLUMN_MEMO_GROUP_CLOSED = "memo_group_closed";
    static final String COLUMN_MEMO_GROUP_CREATED = "memo_group_created";
    static final String COLUMN_MEMO_GROUP_MODIFIED = "memo_group_modified";

    private static final String COLUMN_PREFERENCES_ID = "preferences_id";
    private static final String COLUMN_PREFERENCES_NAME = "preferences_name";
    private static final String COLUMN_PREFERENCES_VALUE = "preferences_value";

    final Context context;

    DbModel(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name.isEmpty() ? DATABASE_NAME : name, factory, 0 == version ? DATABASE_VERSION : version);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.createMemoGroupTable(db)
            .createDefaultMemoGroup(db)
            .createMemoTable(db)
            .createPreferencesTable(db);
    }

    private DbModel createMemoTable(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_MEMO+" ("+
            COLUMN_MEMO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            COLUMN_MEMO_TEXT+" TEXT NOT NULL, "+
            COLUMN_MEMO_GROUP_FK+" INTEGER NOT NULL REFERENCES "+TABLE_MEMO_GROUP+" ("+COLUMN_MEMO_GROUP_ID+"), "+
            COLUMN_MEMO_DELETED+" INTEGER NOT NULL DEFAULT 0, "+
            COLUMN_MEMO_CLOSED+" INTEGER NOT NULL DEFAULT 0, "+
            COLUMN_MEMO_CREATED+" REAL NOT NULL, "+
            COLUMN_MEMO_MODIFIED+" REAL NOT NULL "+
        ");";

        db.execSQL(createTable);

        return this;
    }

    private DbModel createMemoGroupTable(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_MEMO_GROUP+" ("+
            COLUMN_MEMO_GROUP_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            COLUMN_MEMO_GROUP_NAME+" TEXT NOT NULL UNIQUE, "+
            COLUMN_MEMO_GROUP_ORDER+" INTEGER NOT NULL DEFAULT 0, "+
            COLUMN_MEMO_GROUP_ICON+" TEXT, "+
            COLUMN_MEMO_GROUP_COLOR+" TEXT, "+
            COLUMN_MEMO_GROUP_DELETED+" INTEGER NOT NULL DEFAULT 0, "+
            COLUMN_MEMO_GROUP_CLOSED+" INTEGER NOT NULL DEFAULT 0, "+
            COLUMN_MEMO_GROUP_CREATED+" REAL NOT NULL, "+
            COLUMN_MEMO_GROUP_MODIFIED+" REAL NOT NULL "+
        ")";

        db.execSQL(createTable);

        return this;
    }

    private DbModel createDefaultMemoGroup(SQLiteDatabase db) {
        MemoGroupEntity memoGroupEntity = new MemoGroupEntity();
        memoGroupEntity.setName("memo_group_default");

        MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(this.context);
        memoGroupDbModel.insert(memoGroupEntity, db);

        return this;
    }

    private DbModel createPreferencesTable(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_PREFERENCES+" ("+
            COLUMN_PREFERENCES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            COLUMN_PREFERENCES_NAME+" TEXT NOT NULL UNIQUE, "+
            COLUMN_PREFERENCES_VALUE+" REAL NOT NULL "+
        ");";

        db.execSQL(createTable);

        return this;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
