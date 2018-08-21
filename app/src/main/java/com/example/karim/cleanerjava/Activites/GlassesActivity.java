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

import com.example.karim.cleanerjava.Model.Glasses;
import com.example.karim.cleanerjava.Model.Price.CleanerPrice;
import com.example.karim.cleanerjava.Model.Price.GlassesPrice;
import com.example.karim.cleanerjava.Printer.Printer;
import com.example.karim.cleanerjava.R;
import com.example.karim.cleanerjava.Shared.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class GlassesActivity extends AppCompatActivity {
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(common.carType!=null)
            spinnerCarName.setText(common.carType);
        if(common.carName!=null)
            spinnerCarModel.setText(common.carName);
    }
    private void openDialog2(final String ID, final EditText Price){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GlassesActivity.this);
        alertDialog.setTitle("تغير اسم الخدمة");
        alertDialog.setMessage("اسم الخدمة");
        final EditText input = new EditText(GlassesActivity.this);
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
                            GlassesPrice cleaner= new GlassesPrice();
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
    Boolean GlassesFullPriceCheck=false,checkboxGlassesInCheck=false,checkboxglassesTabluAndDecoratCheck=false,
            checkboxGlasesGlassCheck=false,checkboxGlassesMratbCheck=false,checkboxGlassesLightCheck=false,
            checkboxGlassesCoversCheck=false;
    EditText editTextCarNumber;
    CheckBox checkboxGlassesFull,checkboxGlassesIn,checkboxglassesTabluAndDecorat,checkboxGlasesGlass,checkboxGlassesMratb
            ,checkboxGlassesLight,checkboxGlassesCovers;
    TextView spinnerCarName,spinnerCarModel,GlassesFullPrice,GlassesInPrice,glassesTabluAndDecoratInPrice,GlasesGlassPrice
            ,GlassesMratbPrice,GlassesLightPrice,GlassesCoversPrice;
    TextView totalText;
    int priceTotal=0;
    Button printTotla;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glasses);
        editTextCarNumber=findViewById(R.id.editTextCarNumber);

        totalText=findViewById(R.id.totalText);
        common.carType=null;
        common.carName=null;
        printTotla=findViewById(R.id.printTotla);
        printTotla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCarNumber.getText().toString()!=null){
                    Printer printer=new Printer(GlassesActivity.this);
                    printer.printBill();
                }else{
                    editTextCarNumber.setError("من فضلك ادخل رقم اللوحة");
                }
            }
        });
        spinnerCarModel=findViewById(R.id.spinnerCarModel);
        spinnerCarName=findViewById(R.id.spinnerCarName);
        spinnerCarModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GlassesActivity.this,CarModelActivity.class));
            }
        });
        spinnerCarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GlassesActivity.this,CarTypeActivity.class));
            }
        });
        GlassesFullPrice=findViewById(R.id.GlassesFullPrice);
        GlassesInPrice=findViewById(R.id.GlassesInPrice);
        glassesTabluAndDecoratInPrice=findViewById(R.id.glassesTabluAndDecoratInPrice);
        GlasesGlassPrice=findViewById(R.id.GlasesGlassPrice);
        GlassesMratbPrice=findViewById(R.id.GlassesMratbPrice);
        GlassesLightPrice=findViewById(R.id.GlassesLightPrice);
        GlassesCoversPrice=findViewById(R.id.GlassesCoversPrice);
        checkboxGlassesCovers=findViewById(R.id.checkboxGlassesCovers);
        checkboxGlassesCovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlassesCoversCheck)
                    priceTotal+=Integer.parseInt(GlassesCoversPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlassesCoversPrice.getText().toString());
                checkboxGlassesCoversCheck=!checkboxGlassesCoversCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlassesLight=findViewById(R.id.checkboxGlassesLight);
        checkboxGlassesLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlassesLightCheck)
                    priceTotal+=Integer.parseInt(GlassesLightPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlassesLightPrice.getText().toString());
                checkboxGlassesLightCheck=!checkboxGlassesLightCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlassesMratb=findViewById(R.id.checkboxGlassesMratb);
        checkboxGlassesMratb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlassesMratbCheck)
                    priceTotal+=Integer.parseInt(GlassesMratbPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlassesMratbPrice.getText().toString());
                checkboxGlassesMratbCheck=!checkboxGlassesMratbCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlasesGlass=findViewById(R.id.checkboxGlasesGlass);
        checkboxGlasesGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlasesGlassCheck)
                    priceTotal+=Integer.parseInt(GlasesGlassPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlasesGlassPrice.getText().toString());
                checkboxGlasesGlassCheck=!checkboxGlasesGlassCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxglassesTabluAndDecorat=findViewById(R.id.checkboxglassesTabluAndDecorat);
        checkboxglassesTabluAndDecorat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxglassesTabluAndDecoratCheck)
                priceTotal+=Integer.parseInt(glassesTabluAndDecoratInPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(glassesTabluAndDecoratInPrice.getText().toString());
                checkboxglassesTabluAndDecoratCheck=!checkboxglassesTabluAndDecoratCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlassesIn=findViewById(R.id.checkboxGlassesIn);
        checkboxGlassesIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxGlassesInCheck)
                priceTotal+=Integer.parseInt(GlassesInPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlassesInPrice.getText().toString());
                checkboxGlassesInCheck=!checkboxGlassesInCheck;
                totalText.setText(String.valueOf(priceTotal));
            }
        });
        checkboxGlassesFull=findViewById(R.id.checkboxGlassesFull);
        checkboxGlassesFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!GlassesFullPriceCheck)
                    priceTotal += Integer.parseInt(GlassesFullPrice.getText().toString());
                else
                    priceTotal-=Integer.parseInt(GlassesFullPrice.getText().toString());
                GlassesFullPriceCheck=!GlassesFullPriceCheck;
                totalText.setText(String.valueOf(priceTotal));

            }
        });
    }
}
