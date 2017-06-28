package com.lhu.user.familycare;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Registered extends AppCompatActivity {
    private EditText ET_account,ET_password,ET_Name;
    private TextView TV_bitrhday;
    private Context context;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        context = this;
        ImageView imageView = (ImageView)findViewById(R.id.bg_registered);
//        imageView.setImageBitmap(IMGtool.readBitMap(context,R.drawable.registered));
        Picasso.with(context).load(R.drawable.main).into(imageView);
        /*
        * 1.檢查填入資料是否正確(都填寫)
        * 2.檢查帳號是否有被使用過
        * 3.將資料寫入資料庫
        * 4.提示註冊成功或失敗
        * 5.若成功，將畫面帶回登入頁面
        * */
        setDatePickerDialog();
        findView();
        Button button = (Button) findViewById(R.id.S_Registered);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = ET_account.getText().toString().trim();
                String password = ET_password.getText().toString().trim();
                String bitrhday = TV_bitrhday.getText().toString().trim();
                String Name = ET_Name.getText().toString().trim();
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(bitrhday) || TextUtils.isEmpty(Name)){
                    Toast.makeText(context,"資料不完全",Toast.LENGTH_SHORT).show();
                }else {
                    new CheckUser(account).start();
                }
            }
        });
    }
    private void setDatePickerDialog(){
        GregorianCalendar calendar = new GregorianCalendar();

        // 實作DatePickerDialog的onDateSet方法，設定日期後將所設定的日期show在textDate上
        datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
            //將設定的日期顯示出來
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                TV_bitrhday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }
    private void register(){
        String account = ET_account.getText().toString().trim();
        String password = ET_password.getText().toString().trim();
        String bitrhday = TV_bitrhday.getText().toString().trim();
        String Name = ET_Name.getText().toString().trim();
        new RegisterWeb(account,password,bitrhday,Name).start();
    }
    public  void findView(){
        ET_account = (EditText) findViewById(R.id.ET_account);
        ET_password = (EditText) findViewById(R.id.ET_password);
        TV_bitrhday = (TextView)findViewById(R.id.TV_bitrhday);
        TV_bitrhday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        ET_Name = (EditText) findViewById(R.id.ET_Name);

    }
    class CheckUser extends Thread{
        private InputStream inputStream = null;
        private StringBuilder str = new StringBuilder();
        private OkHttpClient client = new OkHttpClient();
        private InputStreamReader reader;
        private String Mid;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                boolean result = Boolean.parseBoolean(str.toString());
                if(!result){
                    //注意這邊已確認過有資料且帳號無人使用，呼叫開始註冊
                    register();
                }  else {
                    Toast.makeText(context,"帳號已被申請",Toast.LENGTH_SHORT).show();
                }
            }
        };

        public CheckUser(String Mid) {
            this.Mid = Mid;
        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            try {
                String URLSTR = String.format("http://140.131.7.63//family_care/callback/Member.aspx?M_ID=%s",Mid);
                url = new URL(URLSTR);
                // 替換成 OkHttp 2.0 ---------------------------------------
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                inputStream = response.body().byteStream();
                reader = new InputStreamReader(inputStream, "UTF-8");
                char[] buffer = new char[1];
                while (reader.read(buffer) != -1) {
                    str.append(new String(buffer));
                }
                runOnUiThread(runnable);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
//                    outputStream.close();
                } catch(Exception e) {

                }
        }
    }
}
//    M_ID=M100&M_Name=蔡尚宏&M_Password=100&M_Birthday=2010-5-1
class RegisterWeb extends Thread{
    private InputStream inputStream = null;
    private StringBuilder str = new StringBuilder();
    private OkHttpClient client = new OkHttpClient();
    private InputStreamReader reader;
    private String account,password,bitrhday,Name;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String result = str.toString();
            if(result.equals("1")){
                Toast.makeText(context,"註冊成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Registered.this,Login.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
            }
        }
    };

    public RegisterWeb(String account,String password,String bitrhday,String Name) {
        this.account=account;
        this.password = password;
        this.bitrhday = bitrhday;
        this.Name = Name;
    }

    @Override
    public void run() {
        super.run();
        URL url = null;
        try {
            String URLSTR = String.format("http://140.131.7.63//family_care/callback/Registered.aspx?M_ID=%s&M_Name=%s&M_Password=%s&M_Birthday=%s",account,Name,password,bitrhday);
            url = new URL(URLSTR);
            // 替換成 OkHttp 2.0 ---------------------------------------
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            inputStream = response.body().byteStream();
            reader = new InputStreamReader(inputStream, "UTF-8");
            char[] buffer = new char[1];
            while (reader.read(buffer) != -1) {
                str.append(new String(buffer));
            }
            runOnUiThread(runnable);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
//                    outputStream.close();
            } catch(Exception e) {

            }
        }
    }
}
}
