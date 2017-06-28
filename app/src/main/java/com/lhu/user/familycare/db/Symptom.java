package com.lhu.user.familycare.db;

/**
 * Created by 尚宏 on 2017/5/20.
 */

public class Symptom {
    private int Symptom_ID;
    private String Symptom_Type;
    private String Symptom_Name;
    private String Symptom_Solution;

    public int getSymptom_ID() {
        return Symptom_ID;
    }

    public void setSymptom_ID(int symptom_ID) {
        Symptom_ID = symptom_ID;
    }

    public String getSymptom_Type() {
        return Symptom_Type;
    }

    public void setSymptom_Type(String symptom_Type) {
        Symptom_Type = symptom_Type;
    }

    public String getSymptom_Name() {
        return Symptom_Name;
    }

    public void setSymptom_Name(String symptom_Name) {
        Symptom_Name = symptom_Name;
    }

    public String getSymptom_Solution() {
        return Symptom_Solution;
    }

    public void setSymptom_Solution(String symptom_Solution) {
        Symptom_Solution = symptom_Solution;
    }
}
