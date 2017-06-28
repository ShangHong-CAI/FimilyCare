package com.lhu.user.familycare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class BodyStatusDAO {
    private SQLiteDatabase db;
    private static final String TABLE_NAME="BodyStatus";

    private static final String H_Date ="H_Date";
    private static final String M_ID ="M_ID";
    private static final String H_Heartbeat ="H_Heartbeat";
    private static final String H_SystolicBloodPressure ="H_SystolicBloodPressure";
    private static final String H_DiastolicBloodPressure ="H_DiastolicBloodPressure";
    private static final String H_BloodSugar ="H_BloodSugar";
    public void delete(){
        db.delete(TABLE_NAME,null,null);
    }
    public final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    H_Date + " TEXT PRIMARY KEY, " +
                    M_ID + " TEXT NOT NULL," +
                    H_Heartbeat+" INTEGER NULL,"+
                    H_SystolicBloodPressure+" INTEGER NULL,"+
                    H_DiastolicBloodPressure+" INTEGER NULL,"+
                    H_BloodSugar+" INTEGER NULL"+
                    "); ";
    public BodyStatusDAO(Context context) {
        db = SchoolDBHelper.getDatabase(context);
    }

    public ArrayList<BodyStatus> getBodyStatuses_List(){
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<BodyStatus> arrayList = new ArrayList<BodyStatus>();
        while(cursor.moveToNext()) {
            arrayList.add(getData(cursor));
        }
        cursor.close();
        return arrayList;
    }
    public void insert(BodyStatus bodyStatus){
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(H_Date, bodyStatus.getDate());
        cv.put(M_ID, bodyStatus.getMid());
        cv.put(H_Heartbeat, bodyStatus.getHeartbeat());
        cv.put(H_SystolicBloodPressure, bodyStatus.getSystolicBloodPressure());
        cv.put(H_DiastolicBloodPressure, bodyStatus.getDiastolicBloodPressure());
        cv.put(H_BloodSugar, bodyStatus.getBloodSugar());
        db.insert(TABLE_NAME,null,cv);
    }
    private BodyStatus getData(Cursor cursor){
        if(cursor.moveToFirst()){
            BodyStatus bodyStatus = new BodyStatus();
            bodyStatus.setDate(cursor.getString(0));
            bodyStatus.setMid(cursor.getString(1));
            bodyStatus.setHeartbeat(cursor.getInt(2));
            bodyStatus.setSystolicBloodPressure(cursor.getInt(3));
            bodyStatus.setDiastolicBloodPressure(cursor.getInt(4));
            bodyStatus.setBloodSugar(cursor.getInt(5));

            return bodyStatus;
        }
        return null;
    }
}
