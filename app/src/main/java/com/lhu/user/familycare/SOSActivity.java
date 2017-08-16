package com.lhu.user.familycare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lhu.user.familycare.tool.IMGtool;

import java.io.IOException;
import java.util.List;

public class SOSActivity extends AppCompatActivity {
    private Context context;
    private ImageView IMG_take_me_home,IMG_help_phone,bg_sos;
    private SharedPreferences preferences;
    private boolean hasSOSHome,hasSOSHelp;
    private String SOSHelp, SOSHome;
    private float lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        context=this;
        findView();
        loadSetting();
    }
    public void findView(){
        preferences = this.getSharedPreferences(getResources().getString(R.string.PREF_NAME), MODE_PRIVATE);
        IMG_take_me_home = (ImageView) findViewById(R.id.IMG_take_me_home);
        IMG_take_me_home.setImageBitmap(IMGtool.readBitMap(context,R.drawable.sos_take_me_home));
        IMG_help_phone = (ImageView) findViewById(R.id.IMG_help_phone);
        IMG_help_phone.setImageBitmap(IMGtool.readBitMap(context,R.drawable.sos_help_phone));
        bg_sos = (ImageView) findViewById(R.id.bg_sos);
        bg_sos.setImageBitmap(IMGtool.readBitMap(context,R.drawable.sos));
    }
    public void onClick(View view) throws IOException {
        switch (view.getId()){
            case R.id.IMG_help_phone:
                if(hasSOSHelp){
                    Uri uri = Uri.parse("tel:" + SOSHelp);
                    Intent i = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(i);
                }else {
                    Intent intent = new Intent();
                    intent.setClass(this,SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case  R.id.IMG_take_me_home:
                if(hasSOSHome){
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addresses= geocoder.getFromLocationName(SOSHome,1);
                    Address address =  addresses.get(0);
                    System.out.println(String.format("geo:%s,%s",""+address.getLatitude(),""+address.getLongitude()));
                    Uri uri = Uri.parse(String.format("geo:%s,%s",""+address.getLatitude(),""+address.getLongitude()));
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(this,SettingActivity.class);
                    startActivity(intent);
                }
                break;
        }

    }
    public void loadSetting() {
        hasSOSHome = preferences.getBoolean("hasSOSHome", false);
        hasSOSHelp = preferences.getBoolean("hasSOSHelp", false);
        SOSHelp = preferences.getString("SOSHelp", null);
        SOSHome = preferences.getString("SOSHome", null);
        lat = preferences.getFloat("lat",0);
        lng = preferences.getFloat("lng",0);
    }
}
