package com.example.karim.cleanerjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.karim.cleanerjava.Activites.CleanerActivity;
import com.example.karim.cleanerjava.Activites.GlassesActivity;
import com.example.karim.cleanerjava.Activites.MoneyActivity;
import com.example.karim.cleanerjava.Activites.OtherActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    LinearLayout WashLinear,glassesLinear,otherLinear,moneyLinear,familyLinear,busLinear;
    private void GetDesign(){
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Other"))
                    FirebaseDatabase.getInstance().getReference().child("Other").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                otherLinear.setVisibility(View.GONE);
                            else
                                otherLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else{
                    otherLinear.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Cost"))
                    FirebaseDatabase.getInstance().getReference().child("Cost").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                moneyLinear.setVisibility(View.GONE);
                            else
                                moneyLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else
                    moneyLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Family"))
                    FirebaseDatabase.getInstance().getReference().child("Family").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                familyLinear.setVisibility(View.GONE);
                            else
                                familyLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else
                    familyLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Bus"))
                    FirebaseDatabase.getInstance().getReference().child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                busLinear.setVisibility(View.GONE);
                            else
                                busLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else
                    busLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Clean"))
                    FirebaseDatabase.getInstance().getReference().child("Clean").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                WashLinear.setVisibility(View.GONE);
                            else
                                WashLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else
                    WashLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Glass"))
                    FirebaseDatabase.getInstance().getReference().child("Glass").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String check=((Map)dataSnapshot.getValue()).get("Type").toString();
                            if(check.equals("0"))
                                glassesLinear.setVisibility(View.GONE);
                            else
                                glassesLinear.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                else
                    glassesLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        busLinear=findViewById(R.id.busLinear);
        busLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,CleanerActivity.class));
            }
        });
        familyLinear=findViewById(R.id.familyLinear);
        familyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,CleanerActivity.class));
            }
        });
        moneyLinear=findViewById(R.id.moneyLinear);
        moneyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MoneyActivity.class));
            }
        });
        WashLinear=findViewById(R.id.WashLinear);
        WashLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,CleanerActivity.class));
            }
        });
        glassesLinear=findViewById(R.id.glassesLinear);
        glassesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GlassesActivity.class));
            }
        });
        otherLinear=findViewById(R.id.otherLinear);
        otherLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,OtherActivity.class));

            }
        });
        GetDesign();
    }
}
