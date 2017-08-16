package com.lhu.user.familycare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class UserDAO {
    private SQLiteDatabase db;
    private static final String TABLE_NAME="Member";

    private static final String M_ID="M_ID";
    private static final String M_Name="M_Name";
    private static final String M_Password="M_Password";
    private static final String M_Birthday="M_Birthday";
    private static final String M_HomeAddress="M_HomeAddress";
    private static final String M_Height="M_Height";
    private static final String M_Weight="M_Weight";
    private static final String M_HelpName="M_HelpName";
    private static final String M_HelpPhone="M_HelpPhone";
    private static final String M_healthWarning="M_healthWarning";
    private static final String M_WeatherWarning="M_WeatherWarning";
    private static final String M_MessageNotice="M_MessageNotice";
    private static final String M_Gender="M_Gender";


    public final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    M_ID + " TEXT PRIMARY KEY, " +
                    M_Name + " TEXT NULL," +
                    M_Password+" TEXT NOT NULL,"+
                    M_Birthday+" TEXT NULL,"+
                    M_HomeAddress+" TEXT NULL,"+
                    M_Height+" INTEGER NULL,"+
                    M_Weight+" INTEGER NULL,"+
                    M_HelpName+" TEXT NULL,"+
                    M_HelpPhone+" TEXT NULL,"+
                    M_healthWarning+" INTEGER NULL,"+
                    M_WeatherWarning+" INTEGER NULL,"+
                    M_MessageNotice+" INTEGER NULL,"+
                    M_Gender+" TEXT NULL"+
                    "); ";
    public UserDAO(Context context) {
        db = SchoolDBHelper.getDatabase(context);
        //此時資料有兩種狀況，已有資料跟沒資料
        //沒資料的情況肯定要先新增不能select
        //定義:dao只有確定有資料時才啟用
    }

    public User getUser(){
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        if(cursor.getCount()<1){
            return null;
        }
        User u = getData(cursor);
        cursor.close();
        return u;
    }
    public void delete(){
        db.delete(TABLE_NAME,null,null);
    }
    public void insert(User user){
// 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(M_ID, user.getID());
        cv.put(M_Name, user.getName());
        cv.put(M_Password, user.getPassword());
        cv.put(M_Birthday, user.getBirthday());
        cv.put(M_HomeAddress, user.getHomeAddress());
        cv.put(M_Height, user.getHeight());
        cv.put(M_Weight, user.getWeight());
        cv.put(M_HelpName, user.getHelpName());
        cv.put(M_HelpPhone, user.getHelpPhone());
        cv.put(M_healthWarning, user.isHealthWarning());
        cv.put(M_WeatherWarning, user.isWeatherWarning());
        cv.put(M_MessageNotice, user.isMessageNotice());
        cv.put(M_Gender, user.getGender());
        db.insert(TABLE_NAME,null,cv);
    }
    private User getData(Cursor cursor){
        if(cursor.moveToFirst()){
            User u = new User();
            u.setID(cursor.getString(0));
            u.setName(cursor.getString(1));
            u.setPassword(cursor.getString(2));
            u.setBirthday(cursor.getString(3));
            u.setHomeAddress(cursor.getString(4));
            u.setHeight(cursor.getInt(5));
            u.setWeight(cursor.getInt(6));
            u.setHelpName(cursor.getString(7));
            u.setHelpPhone(cursor.getString(8));
            u.setHealthWarning(cursor.getInt(9)!=0);
            u.setWeatherWarning(cursor.getInt(10)!=0);
            u.setMessageNotice(cursor.getInt(11)!=0);
            u.setGender(cursor.getString(12));
            return u;
        }
        return null;
    }
}
