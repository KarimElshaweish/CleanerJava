package com.example.karim.cleanerjava.Activites;

import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.karim.cleanerjava.Model.Other;
import com.example.karim.cleanerjava.Printer.Printer;
import com.example.karim.cleanerjava.R;
import com.example.karim.cleanerjava.Shared.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OtherActivity extends AppCompatActivity {
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if(btsocket != null){
                printText("اسم العربة :"+spinnerCarName.getText().toString()+"\n"+" موديل العربة :"+spinnerCarModel.getText().toString()+"\n"+"التكلفة الكلية :"+String.valueOf(priceTotal));
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
    EditText CleanFullPrice,CleanOutPrice,CleanInPrice,PriceCleanGlasesCleaner,PriceCaseMratbDoors,editTextCarNumber;
    Map<String,String> printMap;
    TextView spinnerCarName,spinnerCarModel,totalText;
    CheckBox checkboxCleanFull,checkboxCleanOut,checkboxCleanIn,checkboxGlasesCleaner,checkboxCaseMratbDoors;
    Boolean checkboxCleanFullCheck=false,checkboxCleanOutCheck=false,checkboxCleanInCheck=false,checkboxGlasesCleanerCheck=false
            ,checkboxCaseMratbDoorsCheck=false;
    int priceTotal=0;
    Button printTotla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        editTextCarNumber=findViewById(R.id.editTextCarNumber);
        printTotla=findViewById(R.id.printTotla);
        printTotla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCarNumber.getText().toString()!=null) {
                    Printer printer = new Printer(OtherActivity.this);
                    printer.printBill();
                }else
                    editTextCarNumber.setError("من فضلك ادخل رقم اللوحة");
            }
        });
        totalText=findViewById(R.id.totalText);
        printMap=new HashMap<>();
        common.carType=null;
        common.carName=null;
        spinnerCarModel=findViewById(R.id.spinnerCarModel);
        spinnerCarName=findViewById(R.id.spinnerCarName);
        spinnerCarModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtherActivity.this,CarModelActivity.class));
            }
        });
        spinnerCarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtherActivity.this,CarTypeActivity.class));
            }
        });

        checkboxCaseMratbDoors=findViewById(R.id.checkboxCaseMratbDoors);
        PriceCaseMratbDoors=findViewById(R.id.PriceCaseMratbDoors);
        checkboxCaseMratbDoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxCaseMratbDoorsCheck){
                    priceTotal+=Integer.valueOf(PriceCaseMratbDoors.getText().toString());
                }else{
                    priceTotal-=Integer.valueOf(PriceCaseMratbDoors.getText().toString());
                }
                checkboxCaseMratbDoorsCheck=!checkboxCaseMratbDoorsCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlasesCleaner=findViewById(R.id.checkboxGlasesCleaner);
        PriceCleanGlasesCleaner=findViewById(R.id.PriceCleanGlasesCleaner);
        checkboxGlasesCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlasesCleanerCheck){
                    priceTotal+=Integer.valueOf(PriceCleanGlasesCleaner.getText().toString());
                }else{
                    priceTotal-=Integer.valueOf(PriceCleanGlasesCleaner.getText().toString());
                }
                checkboxGlasesCleanerCheck=!checkboxGlasesCleanerCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxCleanIn=findViewById(R.id.checkboxCleanIn);
        CleanInPrice=findViewById(R.id.CleanInPrice);
        checkboxCleanIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxCleanInCheck){
                    priceTotal+=Integer.valueOf(CleanInPrice.getText().toString());
                }else{
                    priceTotal-=Integer.valueOf(CleanInPrice.getText().toString());
                }
                checkboxCleanInCheck=!checkboxCleanInCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        CleanOutPrice=findViewById(R.id.CleanOutPrice);
        checkboxCleanOut=findViewById(R.id.checkboxCleanOut);
        checkboxCleanOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxCleanOutCheck)
                {
                    priceTotal+=Integer.valueOf(CleanOutPrice.getText().toString());
                }else{
                    priceTotal-=Integer.valueOf(CleanOutPrice.getText().toString());
                }
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        CleanFullPrice=findViewById(R.id.CleanFullPrice);
        checkboxCleanFull=findViewById(R.id.checkboxCleanFull);
        checkboxCleanFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxCleanFullCheck){
                    priceTotal+=Integer.valueOf(CleanFullPrice.getText().toString());
                }else{
                    priceTotal-=Integer.valueOf(CleanFullPrice.getText().toString());
                }
                checkboxCleanFullCheck=!checkboxCleanFullCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
    }
}
