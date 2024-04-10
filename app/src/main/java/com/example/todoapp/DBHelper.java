package com.example.todoapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper  extends SQLiteOpenHelper  {

    private static final String TABLE_NAME = "MyToDo";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TENCV = "TenCV";
    private static final String COLUMN_DANHMUC = "DanhMuc";
    private static final String COLUMN_THOIGIAN = "ThoiGian";
    private static final String COLUMN_HOANTHANH = "HoanThanh";


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String value = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TENCV + " TEXT, "
                + COLUMN_DANHMUC + " TEXT, "
                + COLUMN_THOIGIAN + " TEXT, "
                + COLUMN_HOANTHANH + " TEXT)";
        db.execSQL(value);
    }

    public void addToDo(MyTodo myTodo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENCV, myTodo.getTenCV());
        values.put(COLUMN_DANHMUC, myTodo.getDanhMuc());
        values.put(COLUMN_THOIGIAN, myTodo.getThoiGian());
        int hoanThanh = 0;
        if (myTodo.isHoanThanh()) hoanThanh =1;
        values.put(COLUMN_HOANTHANH, hoanThanh);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<MyTodo> getAllToDos()  {
        ArrayList<MyTodo> arrayList = new ArrayList<MyTodo>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MyTodo myTodo = new MyTodo();
                myTodo.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                myTodo.setTenCV(cursor.getString(cursor.getColumnIndex(COLUMN_TENCV)));
                myTodo.setDanhMuc(cursor.getString(cursor.getColumnIndex(COLUMN_DANHMUC)));
                // Assuming COLUMN_HOANTHANH is of type INTEGER representing boolean
                int hoanThanhValue = cursor.getInt(cursor.getColumnIndex(COLUMN_HOANTHANH));
                boolean hoanThanh = hoanThanhValue == 1; // 1 represents true, 0 represents false
                myTodo.setHoanThanh(hoanThanh);
                myTodo.setThoiGian(cursor.getString(cursor.getColumnIndex(COLUMN_THOIGIAN)));
                arrayList.add(myTodo);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void deleteALL(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
    }

    public void DeleteByID(int idTodo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(idTodo) });
        db.close();
    }
    @SuppressLint("Range")
    public MyTodo FindByID(int idTodo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TENCV, COLUMN_DANHMUC, COLUMN_THOIGIAN, COLUMN_HOANTHANH},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(idTodo)},
                null, null, null);

        MyTodo myTodo = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                myTodo = new MyTodo();
                myTodo.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                myTodo.setTenCV(cursor.getString(cursor.getColumnIndex(COLUMN_TENCV)));
                myTodo.setDanhMuc(cursor.getString(cursor.getColumnIndex(COLUMN_DANHMUC)));
                myTodo.setThoiGian(cursor.getString(cursor.getColumnIndex(COLUMN_THOIGIAN)));
                // Assuming COLUMN_HOANTHANH is of type INTEGER representing boolean
                int hoanThanhValue = cursor.getInt(cursor.getColumnIndex(COLUMN_HOANTHANH));
                boolean hoanThanh = hoanThanhValue == 1; // 1 represents true, 0 represents false
                myTodo.setHoanThanh(hoanThanh);            }
            cursor.close();
        }
        db.close();
        return myTodo;
    }

    public int UpdateByID(MyTodo myTodo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENCV, myTodo.getTenCV());
        values.put(COLUMN_DANHMUC, myTodo.getDanhMuc());
        values.put(COLUMN_THOIGIAN, myTodo.getThoiGian());
        int hoanThanh = 0;
        if (myTodo.isHoanThanh()) hoanThanh =1;
        values.put(COLUMN_HOANTHANH, hoanThanh);
        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(myTodo.getID())});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
