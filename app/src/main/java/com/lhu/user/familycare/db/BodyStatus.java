package com.lhu.user.familycare.db;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class BodyStatus{
    private String Date;
    private String Mid;
    private  float Heartbeat;
    private  float SystolicBloodPressure;
    private  float DiastolicBloodPressure;
    private  float BloodSugar;
    private boolean isUpdate;

    public BodyStatus() {
    }

    public BodyStatus(String date, String mid, float heartbeat, float systolicBloodPressure, float diastolicBloodPressure, float bloodSugar, boolean isUpdate) {
        Date = date;
        Mid = mid;
        Heartbeat = heartbeat;
        SystolicBloodPressure = systolicBloodPressure;
        DiastolicBloodPressure = diastolicBloodPressure;
        BloodSugar = bloodSugar;
        this.isUpdate = isUpdate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public float getHeartbeat() {
        return Heartbeat;
    }

    public void setHeartbeat(float heartbeat) {
        Heartbeat = heartbeat;
    }

    public float getSystolicBloodPressure() {
        return SystolicBloodPressure;
    }

    public void setSystolicBloodPressure(float systolicBloodPressure) {
        SystolicBloodPressure = systolicBloodPressure;
    }

    public float getDiastolicBloodPressure() {
        return DiastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(float diastolicBloodPressure) {
        DiastolicBloodPressure = diastolicBloodPressure;
    }

    public float getBloodSugar() {
        return BloodSugar;
    }

    public void setBloodSugar(float bloodSugar) {
        BloodSugar = bloodSugar;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
