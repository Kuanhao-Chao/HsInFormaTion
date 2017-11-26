package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter;

/**
 * Created by chaokuanhao on 26/11/2017.
 */

public class Parameter_Hospital {

    private String mLat;
    private String mLng;
    private String mName;
    private String mAddress;
    private String mPhone;
    private String mWeb;

    public Parameter_Hospital( String a, String b, String c, String d, String e, String f){
        mLat = a;
        mLng = b;
        mName = c;
        mAddress = d;
        mPhone = e;
        mWeb = f;
    }


    public String getmLat() {
        return mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmWeb() {
        return mWeb;
    }
}
