package com.lhu.user.familycare;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

public class Body_inputValue extends FragmentActivity implements View.OnClickListener {
    private Context context;
    private int address = 1;
    private Fragment fragmentBloodSugar, fragmentBloodPressure;
    private ImageView body_btn_blood_pressure, body_btn_blood_sugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_input_value);
        context = this;
        findView();
        creatFragment();
    }

    public void findView() {
        body_btn_blood_pressure = (ImageView) findViewById(R.id.body_btn_blood_pressure);
        body_btn_blood_sugar = (ImageView) findViewById(R.id.body_btn_blood_sugar);
        body_btn_blood_pressure.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_pressure_y));
        body_btn_blood_sugar.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_sugar_n));
        body_btn_blood_pressure.setOnClickListener(this);
        body_btn_blood_sugar.setOnClickListener(this);


        ImageView bg_inputValue = (ImageView) findViewById(R.id.bg_inputValue);
        bg_inputValue.setImageBitmap(IMGtool.readBitMap(context, R.drawable.bodyinfo));
        ImageView img_btn_OK = (ImageView) findViewById(R.id.IMG_btn_OK);
        img_btn_OK.setImageBitmap(IMGtool.readBitMap(context, R.drawable.ok));
        img_btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Body_inputValue.this, Body_info.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void creatFragment() {
        fragmentBloodPressure = new Fragment_blood_pressure();
        fragmentBloodSugar = new Fragment_blood_sugar();
        FragmentManager fragmentMgr = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.add(R.id.fragment, fragmentBloodSugar);
        fragmentTrans.hide(fragmentBloodSugar);
        fragmentTrans.add(R.id.fragment, fragmentBloodPressure);
        fragmentTrans.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentMgr = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        switch (view.getId()) {
            case R.id.body_btn_blood_pressure:
                if (address == 2) {
                    address = 1;
                    body_btn_blood_pressure.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_pressure_y));
                    body_btn_blood_sugar.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_sugar_n));
                    fragmentTrans.show(fragmentBloodPressure);
                    fragmentTrans.hide(fragmentBloodSugar);
                }
                break;
            case R.id.body_btn_blood_sugar:
                if (address == 1) {
                    address = 2;
                    body_btn_blood_pressure.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_pressure_n));
                    body_btn_blood_sugar.setImageBitmap(IMGtool.readBitMap(context, R.drawable.body_btn_blood_sugar_y));
                    fragmentTrans.hide(fragmentBloodPressure);
                    fragmentTrans.show(fragmentBloodSugar);
                }
                break;
        }
        fragmentTrans.commit();
    }
}
