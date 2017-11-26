package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 26/11/2017.
 */

public class Parameter_Monitor {
    private String mLat;
    private String mLng;
    private String  mPlace;
    private String mNumber;

    public Parameter_Monitor( String a, String b, String c, String d){
        mLat = a;
        mLng = b;
        mPlace = c;
        mNumber = d;
    }

    public String getmLat() {
        return mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public String getmPlace() {
        return mPlace;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}
