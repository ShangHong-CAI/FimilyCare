package com.lhu.user.familycare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class SchoolDBHelper extends SQLiteOpenHelper {
   private static SQLiteDatabase database;
    private final static String DB_NAME = "SchoolSQL";
    private final static int DB_VERSION = 1;

    public SchoolDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        //如果db不存在或db的帳號不等於登入使用者，重新建立
        if (database == null || !database.isOpen()) {
            database = new SchoolDBHelper(context).getWritableDatabase();
           return database;
        }
        return database;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //1.建表>>創建4物件>>用來作取值、改值、新增
        db.execSQL(BodyStatusDAO.CREATE_TABLE);
        db.execSQL(SymptomDAO.CREATE_TABLE);
//        db.execSQL(FriendsDAO.CREATE_TABLE);
        db.execSQL(UserDAO.CREATE_TABLE);
        //2.四物件第一次啟用時建表，接著在塞資料

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
