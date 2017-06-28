/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhu.user.familycare.tool.weatherjsoup;

/**
 *
 * @author 尚宏
 */
public class CityWeather {
    private String CityName,TodayWeather,clothes,Dryingclothes,outdoor,Driving,Agriculture,engineering,Business;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getTodayWeather() {
        return TodayWeather;
    }

    public void setTodayWeather(String TodayWeather) {
        this.TodayWeather = TodayWeather;
    }

    public String getClothes() {
        return clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getDryingclothes() {
        return Dryingclothes;
    }

    public void setDryingclothes(String Dryingclothes) {
        this.Dryingclothes = Dryingclothes;
    }

    public String getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(String outdoor) {
        this.outdoor = outdoor;
    }

    public String getDriving() {
        return Driving;
    }

    public void setDriving(String Driving) {
        this.Driving = Driving;
    }

    public String getAgriculture() {
        return Agriculture;
    }

    public void setAgriculture(String Agriculture) {
        this.Agriculture = Agriculture;
    }

    public String getEngineering() {
        return engineering;
    }

    public void setEngineering(String engineering) {
        this.engineering = engineering;
    }

    public String getBusiness() {
        return Business;
    }

    public void setBusiness(String Business) {
        this.Business = Business;
    }

    @Override
    public String toString() {
        
        return "CityWeather{" + "\nCityName=" + CityName 
                + ", \nTodayWeather=" + TodayWeather 
                + ", \n穿衣=" + clothes 
                + ", \n曬衣=" + Dryingclothes 
                + ", \n戶外=" + outdoor 
                + ", \n行車=" + Driving 
                + ", \n農務=" + Agriculture 
                + ", \n工程=" + engineering 
                + ", \n商務=" + Business + '}';
    
    //穿衣	
//曬衣	
//戶外	
//行車	
//農務	
//工程	
//商務
    }
    
    
}
