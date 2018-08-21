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

public class CarModelActivity extends AppCompatActivity {

    ArrayList<String>yearsList;
    private void MakeYears(){
        for(int i=2020;i>=1970;i--){
            yearsList.add(String.valueOf(i));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);
        yearsList=new ArrayList<>();
        MakeYears();
        ListView lv=findViewById(R.id.lv_carName);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,yearsList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                common.carName=yearsList.get(i);
                finish();
            }
        });
    }
}
