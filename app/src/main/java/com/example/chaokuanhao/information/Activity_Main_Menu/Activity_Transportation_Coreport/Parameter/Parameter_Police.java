package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 25/11/2017.
 */

public class Parameter_Police {

    private String mPolice_lat;
    private String mPolice_lng;
    private String mPolice_name;
    private String mPolice_address;
    private String mPolice_zipCode;
    private String mPolice_phone;

    public Parameter_Police( String d, String e, String f, String g, String h, String i){
        mPolice_lat = d;
        mPolice_lng = e;
        mPolice_name =f;
        mPolice_address = g;
        mPolice_zipCode = h;
        mPolice_phone = i;
    }

    public void setmPolice_lat(String mPolice_lat) {
        this.mPolice_lat = mPolice_lat;
    }

    public void setmPolice_lng(String mPolice_lng) {
        this.mPolice_lng = mPolice_lng;
    }

    public void setmPolice_name(String mPolice_name) {
        this.mPolice_name = mPolice_name;
    }

    public void setmPolice_address(String mPolice_address) {
        this.mPolice_address = mPolice_address;
    }

    public void setmPolice_zipCode(String mPolice_zipCode) {
        this.mPolice_zipCode = mPolice_zipCode;
    }

    public void setmPolice_phone(String mPolice_phone) {
        this.mPolice_phone = mPolice_phone;
    }

    public String getmPolice_lat() {
        return mPolice_lat;
    }

    public String getmPolice_lng() {
        return mPolice_lng;
    }

    public String getmPolice_name() {
        return mPolice_name;
    }

    public String getmPolice_address() {
        return mPolice_address;
    }

    public String getmPolice_zipCode() {
        return mPolice_zipCode;
    }

    public String getmPolice_phone() {
        return mPolice_phone;
    }
}
