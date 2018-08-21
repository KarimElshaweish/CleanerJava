package com.example.karim.cleanerjava.sqlLitDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karim on 7/25/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME="moneyWashDB.db";
    public static final String TABLE_NAME="money_data";
    public static final String colo1="ID";

    public static final String colo2="MONEY_TYPE";
    public static final String colo3="MONEY_COUNT";
    public static final String colo4="DATE";

   public DBHelper(Context _ctx){
       super(_ctx,DATABASE_NAME,null,1);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable="CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+"MONEY_TYPE TEXT,MONEY_COUNT FLOAT,DATE TEXT)";
        db.execSQL(creatTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP IF TABLE EXISTS".toString()+TABLE_NAME);
onCreate(db);
    }
    public boolean addData(String moneyType,float moneyCount,String date){
       SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(colo2,moneyType);
        contentValues.put(colo3,moneyCount);
        contentValues.put(colo4,"date");
        long result;
        try {
            result = db.insert(TABLE_NAME, null, contentValues);
        }catch (Exception e){
            throw e;
        }
        if(result==-1)return  false;
        else return true;
    }
     public Cursor getListContent() {
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor data = db.rawQuery("SELECT * FROM ".toString() + TABLE_NAME, null);
         return data;
     }

}
