package com.example.karim.cleanerjava.Activites;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.cleanerjava.Model.Cleaner;
import com.example.karim.cleanerjava.Model.Fees;
import com.example.karim.cleanerjava.Printer.Printer;
import com.example.karim.cleanerjava.R;
import com.example.karim.cleanerjava.sqlLitDB.DBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoneyActivity extends AppCompatActivity {

    
    DBHelper myDB;
    Button btnAdd;
    EditText editTextMoneyType,editTextMoneyQunity;
    ListView lv;
    ArrayList<String>list;
    ArrayAdapter adapter;
    int priceTotal;
        byte FONT_TYPE;
        private static BluetoothSocket btsocket;
        private static OutputStream outputStream;
    Button btnPrint;
    TextView totalText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        totalText=findViewById(R.id.totalText);
        btnPrint=findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextMoneyType.getText().toString()==null)
                    editTextMoneyType.setError("من فضلك ادخل نوع المصورفات");
                else if(editTextMoneyQunity.getText().toString()==null)
                    editTextMoneyQunity.setError("من فضلك ادخل قيمة المصروفات");
                else {
                    Printer printer = new Printer(MoneyActivity.this);
                    printer.printBill();
                }
            }
        });
        list=new ArrayList<>();
        btnAdd=findViewById(R.id.btnAdd);
        lv=findViewById(R.id.lv);
        editTextMoneyType=findViewById(R.id.editTextMoneyType);
        editTextMoneyQunity=findViewById(R.id.editTextMoneyQunity);
        myDB=new DBHelper(this);
        Cursor cursor=myDB.getListContent();
        if(cursor.getCount()!=0)
        {
            StringBuffer buffer=new StringBuffer();
            priceTotal=0;
            while (cursor.moveToNext()) {
                priceTotal+=Integer.valueOf(cursor.getString(2));
                String values=cursor.getString(1)+" : "+cursor.getString(2);
                list.add(values);
                adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
            }
            lv.setAdapter(adapter);
            totalText.setText(String.valueOf(priceTotal));
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Q=editTextMoneyQunity.getText().toString();
                String T=editTextMoneyType.getText().toString();
                if(Q.equals(""))
                    editTextMoneyQunity.setError("من فضلك ادخل قيمة المصورفات");
                else if(T.equals(""))
                    editTextMoneyQunity.setError("من فضلك ادخل نوع المصورفات");
                else{
                    String name=editTextMoneyType.getText().toString();
                    AddData(editTextMoneyQunity.getText().toString(),name, Calendar.getInstance().getTime().toString());
                    priceTotal+=Integer.valueOf((editTextMoneyQunity.getText().toString()));
                    totalText.setText(String.valueOf(priceTotal));
                }
            }
        });
    }

    private void AddData(String count, String type, String date) {
        boolean insertData=myDB.addData(type,Float.parseFloat(count),date);
        if(insertData) {
            Toast.makeText(this, "تم الاضافة بنجاح", Toast.LENGTH_SHORT).show();
            list.add(type+" : "+count);
            adapter.notifyDataSetChanged();

            String Q=editTextMoneyQunity.getText().toString();
            String T=editTextMoneyType.getText().toString();
            String dateTime=Calendar.getInstance().getTime().toString();
            Fees fees=new Fees();
            fees.SetName(T);
            fees.SetCost(Q);

            Locale locale = new Locale("ar");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date date3 =Calendar.getInstance().getTime();
            try {
                date3 = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf = new SimpleDateFormat("EEEE, MMMM yyyy",locale);
            String format = sdf.format(date3);
            fees.SetDate(format);
            FirebaseDatabase.getInstance().getReference().child("Fees").child(Calendar.getInstance().getTime().toString()).setValue(fees).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MoneyActivity.this, "تم اضافى الى الكلود", Toast.LENGTH_SHORT).show();
                    Printer printer = new Printer(MoneyActivity.this);
                    printer.printBill();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MoneyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if(btsocket != null){
                printText(editTextMoneyType.getText().toString() +" : "+editTextMoneyQunity.getText().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
