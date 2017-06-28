package com.lhu.user.familycare.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 尚宏 on 2017/5/4.
 */

public class User {

@SerializedName(value = "ID", alternate = {"M_ID"})
    private String ID;
//    @SerializedName("M_Name")
@SerializedName(value = "Name", alternate = {"M_Name"})
    private String Name;
//    @SerializedName("M_Password")
@SerializedName(value = "Password", alternate = {"M_Password"})
    private String Password;
//    @SerializedName("M_Birthday")
@SerializedName(value = "Birthday", alternate = {"M_Birthday"})
    private String Birthday;
//    @SerializedName("M_HomeAddress")
@SerializedName(value = "HomeAddress", alternate = {"M_HomeAddress"})
    private String HomeAddress;
//    @SerializedName("M_Height")
@SerializedName(value = "Height", alternate = {"M_Height"})
    private int Height;
//    @SerializedName("M_Weight")
@SerializedName(value = "Weight", alternate = {"M_Weight"})
    private int Weight;
//    @SerializedName("M_HelpName")
@SerializedName(value = "HelpName", alternate = {"M_HelpName"})
    private String HelpName;
//    @SerializedName("M_HelpPhone")
@SerializedName(value = "HelpPhone", alternate = {"M_HelpPhone"})
    private String HelpPhone;
//    @SerializedName("M_healthWarning")
@SerializedName(value = "healthWarning", alternate = {"M_healthWarning"})
    private boolean healthWarning;
//    @SerializedName("M_WeatherWarning")
@SerializedName(value = "WeatherWarning", alternate = {"M_WeatherWarning"})
    private boolean WeatherWarning;
//    @SerializedName("M_MessageNotice")
@SerializedName(value = "MessageNotice", alternate = {"M_MessageNotice"})
    private boolean MessageNotice;
    @SerializedName(value = "Gender", alternate = {"M_Gender"})
    private String Gender;
    private boolean isUpdate;

    public User() {
    }

    public User(String ID, String name, String password, String birthday, String homeAddress, int height, int weight, String helpName, String helpPhone, boolean healthWarning, boolean weatherWarning, boolean messageNotice, String gender) {
        this.ID = ID;
        Name = name;
        Password = password;
        Birthday = birthday;
        HomeAddress = homeAddress;
        Height = height;
        Weight = weight;
        HelpName = helpName;
        HelpPhone = helpPhone;
        this.healthWarning = healthWarning;
        WeatherWarning = weatherWarning;
        MessageNotice = messageNotice;
        Gender = gender;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getHelpName() {
        return HelpName;
    }

    public void setHelpName(String helpName) {
        HelpName = helpName;
    }

    public String getHelpPhone() {
        return HelpPhone;
    }

    public void setHelpPhone(String helpPhone) {
        HelpPhone = helpPhone;
    }

    public boolean isHealthWarning() {
        return healthWarning;
    }

    public void setHealthWarning(boolean healthWarning) {
        this.healthWarning = healthWarning;
    }

    public boolean isWeatherWarning() {
        return WeatherWarning;
    }

    public void setWeatherWarning(boolean weatherWarning) {
        WeatherWarning = weatherWarning;
    }

    public boolean isMessageNotice() {
        return MessageNotice;
    }

    public void setMessageNotice(boolean messageNotice) {
        MessageNotice = messageNotice;
    }
}
