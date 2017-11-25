package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 25/11/2017.
 */

public class Parameter_Accident_Point_Coreport {
    private double mLatitude;
    private double mLogitude;
    private String mAccidentType;

    public Parameter_Accident_Point_Coreport( double Latitude, double Longitude, String Accident ){
        mLatitude = Latitude;
        mLogitude = Longitude;
        mAccidentType = Accident;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLogitude() {
        return mLogitude;
    }

    public String getmAccidentType() {
        return mAccidentType;
    }
}
