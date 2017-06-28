/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhu.user.familycare.tool.weatherjsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 尚宏
 */
public class WeatherJsoup {
    public static final int LOCATION_CODE_N=1;
    public static final int LOCATION_CODE_C=2;
    public static final int LOCATION_CODE_S=3;
    public static final int LOCATION_CODE_E=4;
    public static final int LOCATION_CODE_I=5;
    public String[] Life_N =new String[]{"基隆市","臺北市","新北市","桃園市","新竹市","新竹縣","苗栗縣"};
    public String[] Life_C =new String[]{"臺中市","彰化縣","南投縣","雲林縣","嘉義市","嘉義縣"};
    public String[] Life_S =new String[]{"臺南市","高雄市","屏東縣"};
    public String[] Life_E =new String[]{"臺東縣","花蓮縣","宜蘭縣"};
    public String[] Life_I =new String[]{"澎湖縣","金門縣","連江縣"};
    private static String WeatherUrl1 = "http://www.cwb.gov.tw/V7/life/Life_N.htm";//北部
    private static String WeatherUrl2 = "http://www.cwb.gov.tw/V7/life/Life_C.htm";//中部
    private static String WeatherUrl3 = "http://www.cwb.gov.tw/V7/life/Life_S.htm";//南部
    private static String WeatherUrl4 = "http://www.cwb.gov.tw/V7/life/Life_E.htm";//東部
    private static String WeatherUrl5 = "http://www.cwb.gov.tw/V7/life/Life_I.htm";//外島


    public CityWeather getWeatherStr(int LOCATION_CODE,int CountryCODE) throws MalformedURLException, IOException {
        URL url = getURL(LOCATION_CODE);
        if(url!=null) {
            String cityName = getCityName(LOCATION_CODE, CountryCODE);
            WeatherJsoup wjsoup = new WeatherJsoup();
            //new一個URL 為欲爬取的網址
            Document xmlDOC = Jsoup.parse(url, 3000);
            Elements title1 = xmlDOC.select("#sublife-1 tbody tr");
//        Elements title2 = xmlDOC.select("#sublife-2 tbody tr");
//        Elements title3 = xmlDOC.select("#sublife-3 tbody tr");
            List<CityWeather> list = wjsoup.getCityWeather(title1);
            for (CityWeather c : list) {
                if (c.getCityName().equals(cityName)) {
                    return c;
                }
            }
        }
        return null;
    }
    private String getCityName(int LOCATION_CODE,int CountryCODE){
        String cityName = null;
//        System.out.println(LOCATION_CODE+":"+CountryCODE);
        switch (LOCATION_CODE) {
            case LOCATION_CODE_N:
                cityName =Life_N[CountryCODE];
                break;
            case LOCATION_CODE_C:
                cityName =Life_C[CountryCODE];
                break;
            case LOCATION_CODE_S:
                cityName =Life_S[CountryCODE];
                break;
            case LOCATION_CODE_E:
                cityName =Life_E[CountryCODE];
                break;
            case LOCATION_CODE_I:
                cityName =Life_I[CountryCODE];
                break;
        }
               return cityName;
    }

    private URL getURL(int LOCATION_CODE) throws MalformedURLException {
        switch (LOCATION_CODE){
            case LOCATION_CODE_N:
                return new URL(WeatherUrl1);
            case LOCATION_CODE_C:
                return new URL(WeatherUrl2);
            case LOCATION_CODE_S:
                return new URL(WeatherUrl3);
            case LOCATION_CODE_E:
                return new URL(WeatherUrl4);
            case LOCATION_CODE_I:
                return new URL(WeatherUrl5);
        }
        return  null;
    }
    public List<CityWeather> getCityWeather(Elements title) {
        List<CityWeather> list = new ArrayList<CityWeather>();
        //封裝
        for (int i = 0; i < title.size(); i++) {
            CityWeather city = new CityWeather();
            Element cityData = title.get(i);//城市名
            city.setCityName(cityData.select("th").text());
            Elements cityTD = title.select("td");
            city.setTodayWeather(cityTD.get(0).child(0).attr("title"));
            city.setClothes(cityTD.get(1).child(0).attr("title"));
            city.setDryingclothes(cityTD.get(2).child(0).attr("title"));
            city.setOutdoor(cityTD.get(3).child(0).attr("title"));
            city.setDriving(cityTD.get(4).child(0).attr("title"));
            city.setAgriculture(cityTD.get(5).child(0).attr("title"));
            city.setEngineering(cityTD.get(6).child(0).attr("title"));
            city.setBusiness(cityTD.get(7).child(0).attr("title"));
            list.add(city);
        }
        return list;
    }

}
