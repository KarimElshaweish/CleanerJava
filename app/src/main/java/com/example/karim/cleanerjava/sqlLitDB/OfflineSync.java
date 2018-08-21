package com.example.karim.cleanerjava.sqlLitDB;

import android.database.Cursor;

import com.example.karim.cleanerjava.Model.Selles;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Karim on 7/31/2018.
 */

public class OfflineSync {
    public static void PushData(SalesDBHelper salesDBHelper) {
        Cursor cursor = salesDBHelper.GetData();
        if(salesDBHelper.DBCount()!=0) {
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()) {
                Selles sales = new Selles();
                sales.SetCost(cursor.getString(4));
                sales.SetCarType(cursor.getString(3));
                sales.SetCarNumber(cursor.getString(2));
                sales.SetCarModel(cursor.getString(1));
                FirebaseDatabase.getInstance().getReference().child("Sales").child(cursor.getString(5)).setValue(sales);
                salesDBHelper.DeletAllRecord();
            }
        }
    }
}
