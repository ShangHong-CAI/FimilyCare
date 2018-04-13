package com.lhu.user.familycare;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lhu.user.familycare.db.Symptom;
import com.lhu.user.familycare.tool.IMGtool;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BodySymptomActivity extends AppCompatActivity {
    private Context context;
    private ImageView bg_bodysymptom,IMG_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_symptom2);
        /*
        * 1.如果性別為女生，要把人體圖換掉
        * 2.已選擇的症狀會出現在畫面下方
        * 3.確定：送出；返回：使用返回按鍵
        * */
        context = this;
        IMG_body = (ImageView) findViewById(R.id.IMG_body);
        IMG_body.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_man));
//        Picasso.with(context).load(R.drawable.body_symptom).into(IMG_body);
        bg_bodysymptom = (ImageView) findViewById(R.id.bg_bodysymptom);
        bg_bodysymptom.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_symptom));
//        Picasso.with(context).load(R.drawable.body_symptom).into(bg_bodysymptom);
        new SymptomThread().start();
    }
    public void onSymptomClick(View view){
        switch (view.getId()){
            case R.id.leg:
            case R.id.ear1:
            case R.id.ear2:
            case R.id.neck:
            case R.id.nose:
            case R.id.head:
            case R.id.abdomen:
            case R.id.chest:
            case R.id.Stomach:

        }
    }
    class SymptomThread extends Thread{
        private InputStream inputStream = null;
        private StringBuilder str = new StringBuilder();
        private OkHttpClient client = new OkHttpClient();
        private InputStreamReader reader;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

                List<Symptom> symptoms = gson.fromJson(str.toString().trim(),new TypeToken<List<Symptom>>() {}.getType());
                Toast.makeText(context,symptoms.size()+"",Toast.LENGTH_LONG).show();
//                    UserDAO userDAO = new UserDAO(context);
//                    if(userDAO.getUser()==null) {
//                        userDAO.insert(user);
//                    }else if(userDAO.getUser().getID()!=user.getID()){
//                        userDAO.delete();
//                        userDAO.insert(user);
//                    }

            }
        };

        public SymptomThread() {

        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            try {
                String URLSTR = "http://140.131.7.72:8080//familycare/callback/Symptom.aspx";

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
