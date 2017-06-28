package com.lhu.user.familycare.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class SymptomDAO {
    private SQLiteDatabase db;
    private static final String TABLE_NAME="Symptom";

    private static final String Symptom_ID ="Symptom_ID";
    private static final String Symptom_Type ="Symptom_Type";
    private static final String Symptom_Name ="Symptom_Name";
    private static final String Symptom_Solution ="Symptom_Solution";


    public final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    Symptom_ID + " INTEGER PRIMARY KEY, " +
                    Symptom_Type + " TEXT NOT NULL," +
                    Symptom_Name + " TEXT NOT NULL," +
                    Symptom_Solution + " TEXT NOT NULL" +
                    "); ";
    public SymptomDAO(Context context) {
        db = SchoolDBHelper.getDatabase(context);
    }
    public void delete(){
        db.delete(TABLE_NAME,null,null);
    }
    public ArrayList<Symptom> getSymptoms(){
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<Symptom> arrayList = new ArrayList<Symptom>();
        while(cursor.moveToNext()) {
            arrayList.add(getSymptom(cursor));
        }
        cursor.close();
        return arrayList;
    }
    private Symptom getSymptom(Cursor cursor){
        if(cursor.moveToFirst()){
            Symptom symptom = new Symptom();
            symptom.setSymptom_ID(cursor.getInt(0));
            symptom.setSymptom_Type(cursor.getString(1));
            symptom.setSymptom_Name(cursor.getString(2));
            symptom.setSymptom_Solution(cursor.getString(3));
            return symptom;
        }
        return null;
    }
}
