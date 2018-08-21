package com.example.karim.cleanerjava.Model;

/**
 * Created by Karim on 7/31/2018.
 */

public class Fees {
    String Name,Cost,Date;



    public String GetDate() {
        return Date;
    }

    public void SetDate(String date) {
        Date = date;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String GetCost() {
        return Cost;
    }

    public void SetCost(String cost) {
        Cost = cost;
    }
}
