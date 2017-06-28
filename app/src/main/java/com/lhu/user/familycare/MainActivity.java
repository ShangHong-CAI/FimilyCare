package com.lhu.user.familycare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ImageView IMG_Body,IMG_Weather,IMG_SOS,IMG_setting,bg_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findView();
        /*
        1.連到各個畫面
         */


    }
    public void findView(){
        IMG_Body = (ImageView) findViewById(R.id.IMG_Body);
        IMG_setting = (ImageView) findViewById(R.id.IMG_setting);
        IMG_SOS = (ImageView) findViewById(R.id.IMG_SOS);
        IMG_Weather = (ImageView) findViewById(R.id.IMG_Weather);
        bg_main = (ImageView) findViewById(R.id.bg_main);

        IMG_Body.setImageBitmap(IMGtool.readBitMap(context,R.drawable.main_body));
//        Picasso.with(context).load(R.drawable.main_body).into(IMG_Body);
        IMG_setting.setImageBitmap(IMGtool.readBitMap(context,R.drawable.main_setting));
//        Picasso.with(context).load(R.drawable.main_setting).into(IMG_setting);
        IMG_SOS.setImageBitmap(IMGtool.readBitMap(context,R.drawable.main_sos));
//        Picasso.with(context).load(R.drawable.main_sos).into(IMG_SOS);
        IMG_Weather.setImageBitmap(IMGtool.readBitMap(context,R.drawable.main_weather));
//        Picasso.with(context).load(R.drawable.main_weather).into(IMG_Weather);
        bg_main.setImageBitmap(IMGtool.readBitMap(context,R.drawable.main));
//        Picasso.with(context).load(R.drawable.main).into(bg_main);
    }
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.IMG_SOS:
                intent.setClass(MainActivity.this,SOS.class);
                startActivity(intent);
                break;
            case R.id.IMG_setting:
                intent.setClass(MainActivity.this,Setting.class);
                startActivity(intent);
                break;
            case R.id.IMG_Weather:
                intent.setClass(MainActivity.this,Weather.class);
                startActivity(intent);
                break;
            case R.id.IMG_Body:
                intent.setClass(MainActivity.this,Body_info.class);
                startActivity(intent);
                break;
        }
    }
}
