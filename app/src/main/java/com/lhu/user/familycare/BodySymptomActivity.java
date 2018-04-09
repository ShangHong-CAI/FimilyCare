package com.lhu.user.familycare;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

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
    }
}
