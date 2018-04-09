package com.lhu.user.familycare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lhu.user.familycare.db.BodyStatus;
import com.lhu.user.familycare.db.BodyStatusDAO;
import com.lhu.user.familycare.db.UserDAO;
import com.lhu.user.familycare.tool.IMGtool;

import java.text.SimpleDateFormat;

public class Body_inputValue extends Activity  {
    private Context context;
    private EditText bloodsugar_editText,heartbeat_editText,diastolicBloodPressure_editText,systolicBloodPressure_editText;
    private BodyStatusDAO bodyStatusDAO;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_input_value2);
        context = this;
        bodyStatusDAO = new BodyStatusDAO(context);
        userDAO = new UserDAO(context);
        findView();
//        creatFragment();
    }

    public void findView() {
        bloodsugar_editText = (EditText) findViewById(R.id.bloodsugar_editText);
        heartbeat_editText = (EditText) findViewById(R.id.heartbeat_editText);
        diastolicBloodPressure_editText = (EditText) findViewById(R.id.diastolicBloodPressure_editText);
        systolicBloodPressure_editText = (EditText) findViewById(R.id.systolicBloodPressure_editText);
        ImageView img_btn_OK = (ImageView) findViewById(R.id.IMG_btn_OK);
        img_btn_OK.setImageBitmap(IMGtool.readBitMap(context, R.drawable.ok));
        img_btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float bloodsugar,heartbeat,diastolicBloodPressure,systolicBloodPressure;

                if(bloodsugar_editText.getText().length()!=0 &&
                   heartbeat_editText.getText().length()!=0 &&
                   diastolicBloodPressure_editText.getText().length()!=0 &&
                   systolicBloodPressure_editText.getText().length()!=0) {
                    bloodsugar = Float.parseFloat(bloodsugar_editText.getText().toString());
                    heartbeat = Float.parseFloat(heartbeat_editText.getText().toString());
                    diastolicBloodPressure = Float.parseFloat(diastolicBloodPressure_editText.getText().toString());
                    systolicBloodPressure = Float.parseFloat(systolicBloodPressure_editText.getText().toString());
                    insert(heartbeat, systolicBloodPressure, diastolicBloodPressure, bloodsugar);
                    Intent intent = new Intent();
                    intent.setClass(Body_inputValue.this, Body_info.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(context,"輸入有誤",Toast.LENGTH_LONG).show();
                }
            }
        });
//        ImageView bg_inputValue = (ImageView) findViewById(R.id.bg_inputValue);
//        bg_inputValue.setImageBitmap(IMGtool.readBitMap(context, R.drawable.bodyinfo));

    }
    private void insert(float heartbeat,float systolicBloodPressure,float diastolicBloodPressure,float bloodSugar){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        BodyStatus bodyStatus = new BodyStatus(date,userDAO.getUser().getName(),heartbeat,systolicBloodPressure,diastolicBloodPressure,bloodSugar,false);
        bodyStatusDAO.insert(bodyStatus);
    }

}
