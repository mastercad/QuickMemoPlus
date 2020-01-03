package de.byte_artist.quickmemoplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.byte_artist.quickmemoplus.entity.MemoEntity;
import de.byte_artist.quickmemoplus.entity.MemoGroupEntity;

public class MemoDbModel extends DbModel {

    public MemoDbModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MemoDbModel(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public ArrayList<MemoEntity> load() {
        String query = "SELECT * FROM "+TABLE_MEMO+" WHERE "+TABLE_MEMO+"."+COLUMN_MEMO_DELETED+"<>1 ORDER BY DATETIME("+COLUMN_MEMO_MODIFIED+") DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<MemoEntity> collection = new ArrayList<>();

        while (cursor.moveToNext()) {
            MemoEntity memoEntity = new MemoEntity();
            long memoGroupId = cursor.getInt(2);
            memoEntity.setId(cursor.getInt(0));
            memoEntity.setText(cursor.getString(1));
            memoEntity.setDeleted(1 == cursor.getInt(3));
            memoEntity.setClosed(1 == cursor.getInt(4));
            memoEntity.setCreated(cursor.getString(5));
            memoEntity.setModified(cursor.getString(6));

            MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(this.context);
            MemoGroupEntity memoGroupEntity = memoGroupDbModel.findMemoGroupById(memoGroupId);

            memoEntity.setGroup(memoGroupEntity);

            collection.add(memoEntity);
        }
        cursor.close();
        db.close();

        return collection;
    }

    public ArrayList<MemoEntity> loadTrash() {
        String query = "SELECT * FROM "+TABLE_MEMO+" WHERE "+TABLE_MEMO+"."+COLUMN_MEMO_DELETED+"=1 ORDER BY DATETIME("+COLUMN_MEMO_MODIFIED+") DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<MemoEntity> collection = new ArrayList<>();

        while (cursor.moveToNext()) {
            MemoEntity memoEntity = new MemoEntity();
            long memoGroupId = cursor.getInt(2);
            memoEntity.setId(cursor.getInt(0));
            memoEntity.setText(cursor.getString(1));
            memoEntity.setDeleted(1 == cursor.getInt(3));
            memoEntity.setClosed(1 == cursor.getInt(4));
            memoEntity.setCreated(cursor.getString(5));
            memoEntity.setModified(cursor.getString(6));

            MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(this.context);
            MemoGroupEntity memoGroupEntity = memoGroupDbModel.findMemoGroupById(memoGroupId);

            memoEntity.setGroup(memoGroupEntity);

            collection.add(memoEntity);
        }
        cursor.close();
        db.close();

        return collection;
    }

    public ArrayList<MemoEntity> load(long memoGroupId) {
        String query = "SELECT * FROM "+TABLE_MEMO+" WHERE "+COLUMN_MEMO_GROUP_FK+" = "+memoGroupId+" ORDER BY DATETIME("+COLUMN_MEMO_MODIFIED+") DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<MemoEntity> collection = new ArrayList<>();

        while (cursor.moveToNext()) {
            MemoEntity memoEntity = new MemoEntity();
            long memoGroupIdFromDb = cursor.getInt(2);
            memoEntity.setId(cursor.getInt(0));
            memoEntity.setText(cursor.getString(1));
            memoEntity.setDeleted(1 == cursor.getInt(3));
            memoEntity.setClosed(1 == cursor.getInt(4));
            memoEntity.setCreated(cursor.getString(5));
            memoEntity.setModified(cursor.getString(6));

            MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(this.context);
            MemoGroupEntity memoGroupEntity = memoGroupDbModel.findMemoGroupById(memoGroupIdFromDb);

            memoEntity.setGroup(memoGroupEntity);

            collection.add(memoEntity);
        }
        cursor.close();
        db.close();

        return collection;
    }

    public void update(MemoEntity memoEntity) {
        ContentValues values = new ContentValues();

        Locale currentLocale = this.context.getResources().getConfiguration().locale;
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, currentLocale);
        Date date = new Date();
        memoEntity.setModified(sdf.format(date));

        values.put(COLUMN_MEMO_ID, memoEntity.getId());
        values.put(COLUMN_MEMO_TEXT, memoEntity.getText());
        values.put(COLUMN_MEMO_GROUP_FK, memoEntity.getGroup().getId());
        values.put(COLUMN_MEMO_CLOSED, memoEntity.isClosed() ? 1 : 0);
        values.put(COLUMN_MEMO_DELETED, memoEntity.isDeleted() ? 1 : 0);
        values.put(COLUMN_MEMO_CREATED, memoEntity.getCreated());
        values.put(COLUMN_MEMO_MODIFIED, memoEntity.getModified());

        SQLiteDatabase db = this.getWritableDatabase();

        final String[] whereArgs = {Long.toString(memoEntity.getId())};
        db.update(TABLE_MEMO, values, COLUMN_MEMO_ID + " = ?", whereArgs);
        db.close();
    }

    private void insert(MemoEntity memoEntity, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        Locale currentLocale = this.context.getResources().getConfiguration().locale;
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, currentLocale);
        Date date = new Date();
        memoEntity.setCreated(sdf.format(date));
        memoEntity.setModified(sdf.format(date));

        values.put(COLUMN_MEMO_TEXT, memoEntity.getText());
        values.put(COLUMN_MEMO_GROUP_FK, memoEntity.getGroup().getId());
        values.put(COLUMN_MEMO_CLOSED, memoEntity.isClosed() ? 1 : 0);
        values.put(COLUMN_MEMO_DELETED, memoEntity.isDeleted() ? 1 : 0);
        values.put(COLUMN_MEMO_CREATED, memoEntity.getCreated());
        values.put(COLUMN_MEMO_MODIFIED, memoEntity.getCreated());

        memoEntity.setId(db.insertOrThrow(TABLE_MEMO, null, values));
    }

    public void insert(MemoEntity memoEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        this.insert(memoEntity, db);
        db.close();
    }

    public void delete(MemoEntity memoEntity) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_MEMO_DELETED, 1);
        final String[] whereArgs = {Long.toString(memoEntity.getId())};
        db.update(TABLE_MEMO, values, COLUMN_MEMO_ID + " = ?", whereArgs);

//        final String[] whereArgsDeleteMemoEntry = {Long.toString(memoEntity.getId())};
//        db.delete(TABLE_MEMO, COLUMN_MEMO_ID + " = ?", whereArgsDeleteMemoEntry);

        db.close();
    }
}
