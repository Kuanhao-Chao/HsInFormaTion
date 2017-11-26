package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder;

/**
 * Created by yuneng on 2017/11/25.
 */

public class WeatherParameter {
    private String WeatherCondition;
    private String Temparature;

    public WeatherParameter(String W, String T){
        WeatherCondition = W;
        Temparature = T;
    }
    public String getWeatherCondition() {return WeatherCondition;}
    public String getTemparature() { return Temparature;}
}
