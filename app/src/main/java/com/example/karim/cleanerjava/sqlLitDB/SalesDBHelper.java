package com.example.karim.cleanerjava.sqlLitDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karim on 7/31/2018.
 */

public class SalesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="salesDB.db";
    public static final String TABLE_NAME="sales";
    public static final String col1="ID";
    public static final String col2="carModel";
    public static final String col3="carNumber";
    public static final String col4="carType";
    public static final String col5="cost";
    public static final String col6="date";

    public SalesDBHelper(Context _ctx) {super(_ctx,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable="CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+"carModel TEXT,carNumber TEXT,carType TEXT,cost TEXT,date TEXT)";
        db.execSQL(creatTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS".toString()+TABLE_NAME);
        onCreate(db);
    }
    public boolean AddData(String carModel,String carType,String carNumber,String cost,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,carModel);
        contentValues.put(col3,carNumber);
        contentValues.put(col4,carType);
        contentValues.put(col5,cost);
        contentValues.put(col6,date);
        long result;
        try {
            result = db.insert(TABLE_NAME, null, contentValues);
        }catch (Exception e){
            throw e;
        }
        if(result==-1)return  false;
        else return true;
    }
    public int DBCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM ".toString() + TABLE_NAME, null);
        return data.getCount();
    }
    public Cursor GetData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM ".toString() + TABLE_NAME, null);
        return data;
    }
    public void DeletAllRecord(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
