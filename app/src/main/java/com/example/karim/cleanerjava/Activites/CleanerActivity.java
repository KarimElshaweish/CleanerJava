package com.example.karim.cleanerjava.Activites;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.cleanerjava.Model.Cleaner;
import com.example.karim.cleanerjava.Model.Price.CleanerPrice;
import com.example.karim.cleanerjava.Model.Selles;
import com.example.karim.cleanerjava.Printer.Printer;
import com.example.karim.cleanerjava.R;
import com.example.karim.cleanerjava.Shared.common;
import com.example.karim.cleanerjava.sqlLitDB.OfflineSync;
import com.example.karim.cleanerjava.sqlLitDB.SalesDBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class CleanerActivity extends AppCompatActivity {
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    private boolean isNetworkAvilable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SalesDBHelper salesDBHelper=new SalesDBHelper(this);
        if(isNetworkAvilable())
            OfflineSync.PushData(salesDBHelper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SalesDBHelper salesDBHelper=new SalesDBHelper(this);
        if(isNetworkAvilable())
            OfflineSync.PushData(salesDBHelper);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if(btsocket != null){
                printText("اسم العربة :"+spinnerCarName.getText().toString()+"\n"+" موديل العربة :"+spinnerCarModel.getText().toString()+"\n"+"التكلفة الكلية :"+String.valueOf(printTotla));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(common.carType!=null)
            spinnerCarName.setText(common.carType);
        if(common.carName!=null)
            spinnerCarModel.setText(common.carName);
    }
    private void openDialog(final String ID, final CheckBox Option){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CleanerActivity.this);
        alertDialog.setTitle("تغير اسم الخدمة");
        alertDialog.setMessage("اسم الخدمة");
        final EditText input = new EditText(CleanerActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString()!=null) {
                            Option.setText(input.getText().toString());
                            Cleaner cleaner=new Cleaner();
                            cleaner.SetName(input.getText().toString());
                            cleaner.SetID(ID);
                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    private void openDialog2(final String ID, final EditText Price){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CleanerActivity.this);
        alertDialog.setTitle("تغير اسم الخدمة");
        alertDialog.setMessage("اسم الخدمة");
        final EditText input = new EditText(CleanerActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString()!=null) {
                            Price.setText(input.getText().toString());
                            CleanerPrice cleaner= new CleanerPrice();
                            cleaner.SetPrice(input.getText().toString());
                            cleaner.SetID(ID);
                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    CheckBox checkboxWashOutIn,checkboxWashOut,checkboxWashin,checkboxWashEngine,checkboxWashBastm;
    EditText editTextCarNumber;
    TextView totalText,spinnerCarName,spinnerCarModel,WashOutInPrice,WashOutPrice,WashinPrice,WashEnginePrice,WashBastmPrice;
    Button printTotla;
    int priceTotal;
    Boolean checkboxWashOutInCheck=false,checkboxWashOutCheck=false,checkboxWashinCheck=false,checkboxWashEngineCheck=false
            ,checkboxWashBastmCheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner);
        SalesDBHelper salesDBHelper=new SalesDBHelper(this);
        if(isNetworkAvilable())
            OfflineSync.PushData(salesDBHelper);
        totalText=findViewById(R.id.textTotal);
        editTextCarNumber=findViewById(R.id.editTextCarNumber);
        printTotla=findViewById(R.id.printTotla);
        printTotla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCarNumber.getText().toString()!=null){
                    if(isNetworkAvilable()) {
                        Selles sales = new Selles();
                        sales.SetCarNumber(editTextCarNumber.getText().toString());
                        sales.SetCarModel(spinnerCarModel.getText().toString());
                        sales.SetCarType(spinnerCarName.getText().toString());
                        sales.SetCost(String.valueOf(priceTotal));
                        FirebaseDatabase.getInstance().getReference().child("Sales").child(Calendar.getInstance().getTime().toString()).
                                setValue(sales).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Printer printer = new Printer(CleanerActivity.this);
                                printer.printBill();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CleanerActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        SalesDBHelper salesDBHelper=new SalesDBHelper(CleanerActivity.this);
                      if(salesDBHelper.AddData(spinnerCarModel.getText().toString(),
                                spinnerCarName.getText().toString(),
                                editTextCarNumber.getText().toString(),
                                String.valueOf(priceTotal),
                                Calendar.getInstance().getTime().toString()))
                          (new Printer(CleanerActivity.this)).printBill();
                      else
                          Toast.makeText(CleanerActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    editTextCarNumber.setError("من فضلك ادخل رقم اللوحه");
                }
            }
        });
        common.carType=null;
        common.carName=null;
        spinnerCarModel=findViewById(R.id.spinnerCarModel);
        spinnerCarName=findViewById(R.id.spinnerCarName);
        spinnerCarModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CleanerActivity.this,CarModelActivity.class));
            }
        });
        spinnerCarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CleanerActivity.this,CarTypeActivity.class));
            }
        });
        WashOutInPrice=findViewById(R.id.WashOutInPrice);
        WashOutPrice=findViewById(R.id.WashOutPrice);
        WashinPrice=findViewById(R.id.WashinPrice);
        WashEnginePrice=findViewById(R.id.WashEnginePrice);
        WashBastmPrice=findViewById(R.id.WashBastmPrice);
        checkboxWashBastm=findViewById(R.id.checkboxWashBastm);
        checkboxWashBastm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxWashBastmCheck)
                    priceTotal+=Integer.valueOf(WashBastmPrice.getText().toString());
                else
                    priceTotal-=Integer.valueOf(WashBastmPrice.getText().toString());
                checkboxWashBastmCheck=!checkboxWashBastmCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxWashEngine=findViewById(R.id.checkboxWashEngine);
        checkboxWashEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxWashEngineCheck)
                    priceTotal+=Integer.valueOf(WashinPrice.getText().toString());
                else
                    priceTotal-=Integer.valueOf(WashinPrice.getText().toString());
                checkboxWashEngineCheck=!checkboxWashEngineCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxWashin=findViewById(R.id.checkboxWashin);
        checkboxWashin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxWashinCheck)
                    priceTotal+=Integer.valueOf(WashinPrice.getText().toString());
                else
                    priceTotal+=Integer.valueOf(WashinPrice.getText().toString());
                checkboxWashinCheck=!checkboxWashinCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxWashOut=findViewById(R.id.checkboxWashOut);
        checkboxWashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxWashOutCheck)
                    priceTotal+=Integer.valueOf(WashOutInPrice.getText().toString());
                else
                    priceTotal-=Integer.valueOf(WashOutPrice.getText().toString());
                totalText.setText(String.valueOf(priceTotal));
                checkboxWashOutCheck=!checkboxWashOutCheck;
            }
        });
        checkboxWashOutIn=findViewById(R.id.checkboxWashOutIn);
        checkboxWashOutIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxWashOutInCheck){
                    priceTotal+=Integer.valueOf(WashOutInPrice.getText().toString());
                }else
                {
                    priceTotal-=Integer.valueOf(WashOutInPrice.getText().toString());
                }
                checkboxWashOutInCheck=!checkboxWashOutInCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
    }
}
