package com.lhu.user.familycare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lhu.user.familycare.tool.weatherjsoup.CityWeather;
import com.lhu.user.familycare.tool.weatherjsoup.WeatherJsoup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class WeatherActivity extends AppCompatActivity {
    private LocationManager mLocationManager;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    public static final int LOCATION_CODE_N=1;
    public static final int LOCATION_CODE_C=2;
    public static final int LOCATION_CODE_S=3;
    public static final int LOCATION_CODE_E=4;
    public static final int LOCATION_CODE_I=5;
    public TextView TXTV_time,TXTV_hint,TXTV_region,TXTV_W_time1,TXTV_W_time2,TXTV_W_time3,TXTV_Now,TXTV_info;
    private WeatherWork weatherWork;
    public String[] Life_N =new String[]{"基隆市","臺北市","新北市","桃園市","新竹市","新竹縣","苗栗縣"};
    public String[] Life_C =new String[]{"臺中市","彰化縣","南投縣","雲林縣","嘉義市","嘉義縣"};
    public String[] Life_S =new String[]{"臺南市","高雄市","屏東縣"};
    public String[] Life_E =new String[]{"臺東縣","花蓮縣","宜蘭縣"};
    public String[] Life_I =new String[]{"澎湖縣","金門縣","連江縣"};
    private Context context;
    private Address address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);
        context = this;
        findView();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getCurrentLocation();
        new RunWork().start();
        /*
        * 1.將天氣資料下載
        * 2.地區：抓GPS定位出來的地方
        * 3.時間："最後更新："+抓氣象局的更新時間
        * 4.溫度(TXTV_W_Now)：目前即時溫度
        * 5.天氣說明\n降雨機率(TXTV_info)：用中文顯示目前天氣類別，第二行顯示降雨機率
        * 6.IMG_W系列：隨資料切換對應的圖片
        * 7.TXTV_W系列：隨時間顯示對應的溫度
        * 8.若無法下載資料，則不顯示任何圖片，文字顯示"--"，toast提示：無法下載資料
        * */
    }
    public void findView(){
        TXTV_time = (TextView) findViewById(R.id.TXTV_time);
        TXTV_hint= (TextView) findViewById(R.id.TXTV_hint);
        TXTV_region = (TextView) findViewById(R.id.TXTV_region);
//        TXTV_W_time1= (TextView) findViewById(R.id.TXTV_W_time1);
//        TXTV_W_time2= (TextView) findViewById(R.id.TXT_W_time2);
//        TXTV_W_time3= (TextView) findViewById(R.id.TXTV_W_time3);
        TXTV_Now= (TextView) findViewById(R.id.TXTV_Now);
//        TXTV_info= (TextView) findViewById(R.id.TXTV_info);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(weatherWork!=null){
            weatherWork =null;
        }
    }
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d("d", String.format("%f, %f", location.getLatitude(), location.getLongitude()));

                mLocationManager.removeUpdates(mLocationListener);
            } else {
                Log.d("d", "Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private void getCurrentLocation() {
        try {
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Location location = null;
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                Toast.makeText(this, "error1", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!(isGPSEnabled || isNetworkEnabled))
                Toast.makeText(this, "error2", Toast.LENGTH_SHORT).show();
            else {
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (isGPSEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
            if (location != null) {
                //以上是權限檢查和環境設置，以下開始主程式
                address = getAddress(location.getLatitude(), location.getLongitude());
                int i[] = getStrByAddress(address);
                if (weatherWork == null) {
                    weatherWork = new WeatherWork(i,address.getAddressLine(0));
                    weatherWork.start();
                }

            }
        }catch (Exception e){
            System.err.println("wTool.getWeatherStr fail:"+e.getLocalizedMessage());
            e.printStackTrace();
//            finish();
        }
    }

    private int[] getStrByAddress(Address address){
        String addressStr=address.getAddressLine(0);
//        TXTV_region.setText(addressStr);
        int locationCode=0,countryCode=0;
        for(int i=0;i<Life_N.length;i++){
            String str =Life_N[i];
            if(addressStr.contains(str)){
                locationCode=LOCATION_CODE_N;
                countryCode = i;
            }
        }
        for(int i=0;i<Life_C.length;i++){
            String str =Life_C[i];
            if(addressStr.contains(str)){
                locationCode=LOCATION_CODE_C;
                countryCode = i;
            }
        }
        for(int i=0;i<Life_S.length;i++){
            String str =Life_S[i];
            if(addressStr.contains(str)){
                locationCode=LOCATION_CODE_S;
                countryCode = i;
            }
        }
        for(int i=0;i<Life_E.length;i++){
            String str =Life_E[i];
            if(addressStr.contains(str)){
                locationCode=LOCATION_CODE_E;
                countryCode = i;
            }
        }
        for(int i=0;i<Life_I.length;i++){
            String str =Life_I[i];
            if(addressStr.contains(str)){
                locationCode=LOCATION_CODE_I;
                countryCode = i;
            }
        }
        int result[] ={locationCode,countryCode};
        return result;
    }
    private Address getAddress(double latitude,double longitude) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        try {
            // 解譯地名/地址後可能產生多筆位置資訊，但限定回傳1筆
            addressList = geocoder.getFromLocation(latitude,longitude, 1);

        } catch (IOException ie) {

        }

        if(addressList == null || addressList.isEmpty())
            return null;
        else
            // 因為當初限定只回傳1筆，所以只要取得第1個Address物件即可
            return addressList.get(0);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 101:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得權限，進行檔案存取
                    getCurrentLocation();
                } else {
                    //使用者拒絕權限，停用檔案存取功能
                    Toast.makeText(this,"無權限",Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    class WeatherWork extends Thread{
        int i[];
        String name;
        CityWeather city = null;
        public WeatherWork(int i[],String name) {
            this.i = i;
            this.name = name;
        }

        Runnable runnable =new Runnable() {
            @Override
            public void run() {

                if(city == null){
                    TXTV_region.setText(name);
                    TXTV_Now.setText("地區不支援");
                }else {
                    TXTV_region.setText(city.getOutdoor());
                    String w =  city.getTodayWeather();
                    String[] str = w.split("<BR>");
                    TXTV_Now.setText("溫度 : \n"+str[1]);
                    TXTV_hint.setText(str[2]);
                }
//                textView.append("\n"+city.getCityName()+"\n"+city.getTodayWeather()+"\n");
            }
        };
        @Override
        public void run() {
            WeatherJsoup wTool=new WeatherJsoup();
            try {
                city = wTool.getWeatherStr(i[0],i[1]);
            } catch (IOException e) {
                System.err.println("wTool.getWeatherStr fail:"+e.getLocalizedMessage());
                e.printStackTrace();
            }
            runOnUiThread(runnable);
        }
    }
    class RunWork extends Thread{
        Handler handler =new Handler();
        Date date;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                //需要改動ui、動主執行緒寫在這
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
                date =new Date();
                TXTV_time.setText("現在時間 : "+sdf.format(date));
                handler.postDelayed(this,1000);
            }
        };
        @Override
        public void run() {
            runOnUiThread(runnable);
        }
    }
    class CheckUser extends Thread{
        private InputStream inputStream = null;
        private StringBuilder str = new StringBuilder();
        private OkHttpClient client = new OkHttpClient();
        private InputStreamReader reader;
        private String Mid;
        private String date;
        private Address address;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,str.toString(),Toast.LENGTH_SHORT).show();
            }
        };
        public CheckUser(String mid,String date,Address address) {
            this.Mid = mid;
            this.date = date;
            this.address = address;
        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            try {
                String URLSTR = String.format
                        ("http://140.131.7.63//family_care/callback/GPSInfo.aspx?M_ID=%s&GPS_TIME=%s&Location=&s"
                                ,Mid,date.toString(),address.getLatitude()+","+address.getLongitude());
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
                runOnUiThread(r);

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
