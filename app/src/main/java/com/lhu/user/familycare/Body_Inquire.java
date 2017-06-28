package com.lhu.user.familycare;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

public class Body_Inquire extends AppCompatActivity {
    private Context context;
    private ImageView bg_quire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_inquire);
        context = this;
        bg_quire = (ImageView) findViewById(R.id.bg_quire);
        bg_quire.setImageBitmap(IMGtool.readBitMap(context,R.drawable.body_inquire));
    }
}
