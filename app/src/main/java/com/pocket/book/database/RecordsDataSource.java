package com.pocket.book.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.pocket.book.model.Record;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by anil on 8/5/2016.
 */

public class RecordsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DBRecord dbHelper;
    private String[] allColumns = { DBRecord.KEY_ID,
            DBRecord.KEY_TITLE, DBRecord.KEY_DESCRIPTION, DBRecord.KEY_IMAGE_PATH, DBRecord.KEY_CREATED_DATE };

    public RecordsDataSource(Context context) {
        dbHelper = new DBRecord(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Record createRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(DBRecord.KEY_TITLE, record.getTitle());
        values.put(DBRecord.KEY_DESCRIPTION, record.getDescription());
        values.put(DBRecord.KEY_IMAGE_PATH, record.getImagePath());
        values.put(DBRecord.KEY_CREATED_DATE, record.getCreatedDate());

        long insertId = database.insert(DBRecord.TABLE_RECORD, null,
                values);

        Cursor cursor = database.query(DBRecord.TABLE_RECORD,
                allColumns, DBRecord.KEY_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Record newRecord = cursorToComment(cursor);
        cursor.close();
        return newRecord;
    }

    public void deleteRecord(Record record) {
        int id = record.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DBRecord.TABLE_RECORD, DBRecord.KEY_ID
                + " = " + id, null);
    }

    public ArrayList<Record> getAllRecords() {

        ArrayList<Record> recordArrayList = new ArrayList<Record>();

        Cursor cursor = database.query(DBRecord.TABLE_RECORD,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Record record = cursorToComment(cursor);
            recordArrayList.add(record);
            cursor.moveToNext();
        }

        cursor.close();
        return recordArrayList;
    }

    private Record cursorToComment(Cursor cursor) {

        Record record = new Record();
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.setTitle(cursor.getString(1));
        record.setDescription(cursor.getString(2));
        record.setImagePath(cursor.getString(3));
        record.setCreatedDate(cursor.getString(4));

        return record;
    }

}
