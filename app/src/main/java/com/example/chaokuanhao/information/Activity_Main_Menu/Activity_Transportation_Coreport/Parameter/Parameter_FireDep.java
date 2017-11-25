package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 25/11/2017.
 */

public class Parameter_FireDep {
    private String mFireDep_lat;
    private String mFireDep_lng;
    private String mFireDep_name;

    public Parameter_FireDep( String a, String b, String c){
        mFireDep_lat = a;
        mFireDep_lng = b;
        mFireDep_name = c;
    }

    public String getmFireDep_lat() {
        return mFireDep_lat;
    }

    public String getmFireDep_lng() {
        return mFireDep_lng;
    }

    public String getmFireDep_name() {
        return mFireDep_name;
    }

    public void setmFireDep_lat(String mFireDep_lat) {
        this.mFireDep_lat = mFireDep_lat;
    }

    public void setmFireDep_lng(String mFireDep_lng) {
        this.mFireDep_lng = mFireDep_lng;
    }

    public void setmFireDep_name(String mFireDep_name) {
        this.mFireDep_name = mFireDep_name;
    }
}
