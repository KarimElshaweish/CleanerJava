package com.example.karim.cleanerjava;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.karim.cleanerjava.Model.Selles;
import com.example.karim.cleanerjava.sqlLitDB.OfflineSync;
import com.example.karim.cleanerjava.sqlLitDB.SalesDBHelper;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Karim on 7/31/2018.
 */

public class MyApplication extends Application {
    private boolean isNetworkAvilable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SalesDBHelper salesDBHelper=new SalesDBHelper(getBaseContext());
        if(isNetworkAvilable()) OfflineSync.PushData(salesDBHelper);
    }

}
