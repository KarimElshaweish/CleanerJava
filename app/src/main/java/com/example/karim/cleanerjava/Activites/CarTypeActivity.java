package com.example.karim.cleanerjava.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karim.cleanerjava.R;
import com.example.karim.cleanerjava.Shared.common;

import java.util.ArrayList;

public class CarTypeActivity extends AppCompatActivity {

    ListView lv_carType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);
        lv_carType=findViewById(R.id.lv_carType);
        final ArrayList<String>spinnerCarNameList=new ArrayList<>();
        spinnerCarNameList.add("مرسيدس-بنز");
        spinnerCarNameList.add("فولكس فاجن");
        spinnerCarNameList.add("بي إم دبليو");
        spinnerCarNameList.add("أوبل");
        spinnerCarNameList.add("أودي");
        spinnerCarNameList.add("شيفروليه");
        spinnerCarNameList.add("فيات ");
        spinnerCarNameList.add("فورد ");
        spinnerCarNameList.add("هيونداي  ");
        spinnerCarNameList.add("ميتسوبيشي");
        spinnerCarNameList.add("بيجو");
        spinnerCarNameList.add("رينو ");
        spinnerCarNameList.add("شكودا أوتو ");
        spinnerCarNameList.add("سوزوكي");
        spinnerCarNameList.add("تويوتا");
        spinnerCarNameList.add("فورد");
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,spinnerCarNameList);
        lv_carType.setAdapter(adapter);
        lv_carType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                common.carType=spinnerCarNameList.get(i);
                CleanerActivity cleanerActivity=new CleanerActivity();
                finish();
            }
        });
    }
}
