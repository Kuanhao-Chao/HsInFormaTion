package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/26.
 */

public class Info_MonitorData {
    private boolean isCheck;
    private String lat, lng, no, place;

    public Info_MonitorData(JSONObject data){
        try {

            //Log.v("Info_Mo", data.toString());
            //Log.v("Info_Mo", data.toString());
            no = data.getString("編號");
            place = data.getString("攝影機地點 ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCheck = false;


        try {
            lat = data.getString("lat");
            lng = data.getString("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLat() { return lat; }
    public String getLng() { return lng; }
    public String getNo() { return  no; }
    public String getPlace() {return place;}
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }
}
