package de.byte_artist.quickmemoplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.byte_artist.quickmemoplus.entity.MemoGroupEntity;

public class MemoGroupDbModel extends DbModel {

    public MemoGroupDbModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MemoGroupDbModel(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public ArrayList<MemoGroupEntity> load() {
        String query = "SELECT * FROM "+TABLE_MEMO_GROUP+" ORDER BY "+COLUMN_MEMO_GROUP_ORDER+", "+COLUMN_MEMO_GROUP_CREATED;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<MemoGroupEntity> collection = new ArrayList<>();

        while (cursor.moveToNext()) {
            MemoGroupEntity memoGroupEntity = new MemoGroupEntity();
            memoGroupEntity.setId(cursor.getInt(0));
            memoGroupEntity.setName(cursor.getString(1));
            memoGroupEntity.setOrder(cursor.getLong(2));
            memoGroupEntity.setIcon(cursor.getString(3));
            memoGroupEntity.setColor(cursor.getString(4));
            memoGroupEntity.setDeleted(1 == cursor.getInt(5));
            memoGroupEntity.setClosed(1 == cursor.getInt(6));
            memoGroupEntity.setCreated(cursor.getString(7));
            memoGroupEntity.setModified(cursor.getString(8));

            collection.add(memoGroupEntity);
        }

        cursor.close();
        db.close();

        return collection;
    }

    public MemoGroupEntity findMemoGroupById(long memoGroupId) {
        String query = "SELECT * FROM "+TABLE_MEMO_GROUP+" WHERE "+COLUMN_MEMO_GROUP_ID+" = "+memoGroupId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        MemoGroupEntity memoGroupEntity = new MemoGroupEntity();

        if (cursor.moveToFirst()) {
            memoGroupEntity.setId(cursor.getInt(0));
            memoGroupEntity.setName(cursor.getString(1));
            memoGroupEntity.setOrder(cursor.getLong(2));
            memoGroupEntity.setIcon(cursor.getString(3));
            memoGroupEntity.setColor(cursor.getString(4));
            memoGroupEntity.setDeleted(1 == cursor.getInt(5));
            memoGroupEntity.setClosed(1 == cursor.getInt(6));
            memoGroupEntity.setCreated(cursor.getString(7));
            memoGroupEntity.setModified(cursor.getString(8));

        } else {
            memoGroupEntity = null;
        }

        cursor.close();
        db.close();

        return memoGroupEntity;
    }

    public void update(MemoGroupEntity memoGroupEntity) {
        ContentValues values = new ContentValues();

        Locale currentLocale = this.context.getResources().getConfiguration().locale;
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, currentLocale);
        Date date = new Date();
        memoGroupEntity.setModified(sdf.format(date));

        values.put(COLUMN_MEMO_GROUP_ID, memoGroupEntity.getId());
        values.put(COLUMN_MEMO_GROUP_NAME, memoGroupEntity.getName());
        values.put(COLUMN_MEMO_GROUP_ICON, memoGroupEntity.getIcon());
        values.put(COLUMN_MEMO_GROUP_COLOR, memoGroupEntity.getColor());
        values.put(COLUMN_MEMO_GROUP_ORDER, memoGroupEntity.getOrder());
        values.put(COLUMN_MEMO_GROUP_CLOSED, memoGroupEntity.isClosed() ? 1 : 0);
        values.put(COLUMN_MEMO_GROUP_DELETED, memoGroupEntity.isDeleted() ? 1 : 0);
        values.put(COLUMN_MEMO_GROUP_CREATED, memoGroupEntity.getCreated());
        values.put(COLUMN_MEMO_GROUP_MODIFIED, memoGroupEntity.getModified());

        SQLiteDatabase db = this.getWritableDatabase();

        final String[] whereArgs = {Long.toString(memoGroupEntity.getId())};
        db.update(TABLE_MEMO_GROUP, values, COLUMN_MEMO_GROUP_ID + " = ?", whereArgs);
        db.close();
    }

    public void insert(MemoGroupEntity memoGroupEntity, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        Locale currentLocale = this.context.getResources().getConfiguration().locale;
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, currentLocale);
        Date date = new Date();
        memoGroupEntity.setModified(sdf.format(date));
        memoGroupEntity.setCreated(sdf.format(date));

        values.put(COLUMN_MEMO_GROUP_NAME, memoGroupEntity.getName());
        values.put(COLUMN_MEMO_GROUP_ICON, memoGroupEntity.getIcon());
        values.put(COLUMN_MEMO_GROUP_COLOR, memoGroupEntity.getColor());
        values.put(COLUMN_MEMO_GROUP_ORDER, memoGroupEntity.getOrder());
        values.put(COLUMN_MEMO_GROUP_CLOSED, memoGroupEntity.isClosed() ? 1 : 0);
        values.put(COLUMN_MEMO_GROUP_DELETED, memoGroupEntity.isDeleted() ? 1 : 0);
        values.put(COLUMN_MEMO_GROUP_CREATED, memoGroupEntity.getCreated());
        values.put(COLUMN_MEMO_GROUP_MODIFIED, memoGroupEntity.getModified());

        memoGroupEntity.setId(db.insertOrThrow(TABLE_MEMO_GROUP, null, values));
    }

    public void insert(MemoGroupEntity memoGroupEntity) {
        SQLiteDatabase db = this.context.openOrCreateDatabase(TABLE_MEMO_GROUP, Context.MODE_PRIVATE, null);

        this.insert(memoGroupEntity, db);
        db.close();
    }

    public void delete(MemoGroupEntity memoGroupEntity) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_MEMO_GROUP_DELETED, 1);
        final String[] whereArgs = {Long.toString(memoGroupEntity.getId())};
        db.update(TABLE_MEMO_GROUP, values, COLUMN_MEMO_GROUP_ID + " = ?", whereArgs);

        db.close();
    }
}
