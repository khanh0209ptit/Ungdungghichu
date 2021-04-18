package com.example.a131_sqlitetodolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy van khong tra ket qua: Create-tao,Insert-them,delete-xoa...
    public void QueryData(String sql){
        //ghi vao du lieu
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }

    //truy van tra ve ket qua : Select
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        //tra con tro
        return database.rawQuery(sql,null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
