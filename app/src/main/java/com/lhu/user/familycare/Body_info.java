package com.lhu.user.familycare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

public class Body_info extends AppCompatActivity {
private Context context;
private ImageView bg_bodyinfo,imghint,imglnquire,imgrecord,imginputValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_info);
        context= this;
        findView();


    }
    public void findView(){
        bg_bodyinfo = (ImageView) findViewById(R.id.bg_bodyinfo);
        bg_bodyinfo.setImageBitmap(IMGtool.readBitMap(context,R.drawable.bodyinfo));
        imghint = (ImageView) findViewById(R.id.IMG_hint);
        imghint.setImageBitmap(IMGtool.readBitMap(context,R.drawable.bodyinfo_hint));
        imglnquire = (ImageView) findViewById(R.id.IMG_lnquire);
        imglnquire.setImageBitmap(IMGtool.readBitMap(context,R.drawable.bodyinfo_inquire));
        imgrecord = (ImageView) findViewById(R.id.IMG_record);
        imgrecord.setImageBitmap(IMGtool.readBitMap(context,R.drawable.bodyinfo_record));
        imginputValue = (ImageView) findViewById(R.id.IMG_inputValue);
        imginputValue.setImageBitmap(IMGtool.readBitMap(context,R.drawable.bodyinfo_input_value));

    }
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            //查詢身體狀況
            case R.id.IMG_lnquire:
                intent.setClass(Body_info.this,Body_Inquire.class);
                startActivity(intent);
                break;
            //輸入量測值
            case R.id.IMG_inputValue:
                intent.setClass(Body_info.this,Body_inputValue.class);
                startActivity(intent);
                break;
            //紀錄身體狀況
            case R.id.IMG_record:
                intent.setClass(Body_info.this,BodySymptom.class);
                startActivity(intent);
                break;
        }
    }
}
