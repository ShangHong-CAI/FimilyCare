package com.lhu.user.familycare;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;

public class Setting extends AppCompatActivity {
    private Context context;
    private static final int REQUEST_CONTACTS = 103;
    private SharedPreferences preferences;
    private String SOSHelp, SOSHome;

    AutoCompleteTextView ET_SOS_phone, ET_SOS_name;
    EditText ET_address, TV_address, ET_weight, ET_bodytail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
                /*
        * 1.檢查每個資料是否都有輸入
        * 2.檢查每個資料的格式是否正確
        * 3.將資料更新回資料庫
        * */
        checkPermission();
        context = this;
        findView();
        loadSetting();
        setContent();

    }

    public void onClick(View view) {

        SOSHelp = ET_SOS_phone.getText().toString();
        SOSHome = ET_SOS_name.getText().toString();
        saveHomeSetting(SOSHome);
        saveHelpSetting(SOSHelp);
        Intent intent =new Intent();
        intent.setClass(Setting.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void findView() {
        preferences = this.getSharedPreferences(getResources().getString(R.string.PREF_NAME), MODE_PRIVATE);
        ET_SOS_phone = (AutoCompleteTextView) findViewById(R.id.ET_SOS_phone);
        ET_SOS_name = (AutoCompleteTextView) findViewById(R.id.ET_SOS_name);
//        ET_address = findViewById(R.id);
    }

    public void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_CONTACTS, WRITE_CONTACTS},
                    REQUEST_CONTACTS);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得聯絡人權限，進行存取
                } else {
                    //使用者拒絕權限，顯示對話框告知
                    new AlertDialog.Builder(this)
                            .setMessage("必須允許聯絡人權限才能顯示資料")
                            .setPositiveButton("OK", null)
                            .show();
                }
                return;
        }
    }

    private void setContent() {
        ArrayList<String> sprlist = new ArrayList<String>();
        ArrayList<String> namelist = new ArrayList<String>();
        ArrayList<String> phonelist = new ArrayList<String>();
        sprlist.add("請選擇聯絡人");
        //取得聯絡人資料
        ArrayList<String>[] list = fetchContactInformation(sprlist, namelist, phonelist);
        sprlist = list[0];
        namelist = list[1];
        phonelist = list[2];
        //設定姓名輸入框
        ArrayAdapter nameAdapter = new ArrayAdapter(this
                , android.R.layout.simple_spinner_item
                , namelist);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_SOS_phone.setAdapter(nameAdapter);
        ET_SOS_phone.setThreshold(1);

        ArrayAdapter phoneAdapter = new ArrayAdapter(this
                , android.R.layout.simple_spinner_item
                , phonelist);
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_SOS_name.setAdapter(phoneAdapter);
        ET_SOS_name.setThreshold(1);

    }

    //fetchContactInformation用來取得聯絡人資料，回傳重新包好的三個list
    public ArrayList[] fetchContactInformation(ArrayList<String> sprlist
            , ArrayList<String> namelist
            , ArrayList<String> phonelist) {
        String id, name, phoneNumber;
        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));

            //Fetch Phone Number
            Cursor phoneCursor = contentResolver.query(
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
            while (phoneCursor.moveToNext()) {
                phoneNumber = phoneCursor.getString(
                        phoneCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
                sprlist.add(name + ": Phone=" + phoneNumber);
                namelist.add(name);
                phonelist.add(phoneNumber);
            }
            phoneCursor.close();
        }
        cursor.close();
        return new ArrayList[]{sprlist, namelist, phonelist};
    }

    public void loadSetting() {
        String Help = preferences.getString("SOSHelp", null);
        String Home = preferences.getString("SOSHome", null);
        if(Help!=null)
            SOSHelp = preferences.getString("SOSHelp", null);
        if(Home!=null)
        SOSHome = preferences.getString("SOSHome", null);
    }

    public void saveHelpSetting(String helpStr) {

        preferences.edit().putBoolean("hasSOSHelp", true).commit();
        preferences.edit().putString("SOSHelp", helpStr).commit();
        SOSHelp = preferences.getString("SOSHelp", null);
        SOSHome = preferences.getString("SOSHome", null);
    }

    public void saveHomeSetting(String homeStr) {
        preferences.edit().putBoolean("hasSOSHome", true).commit();
        preferences.edit().putString("SOSHome", homeStr).commit();
        SOSHome = preferences.getString("SOSHome", null);
    }
}
