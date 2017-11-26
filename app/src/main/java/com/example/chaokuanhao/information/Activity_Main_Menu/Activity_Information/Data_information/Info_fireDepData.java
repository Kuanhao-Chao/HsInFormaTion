package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/26.
 */

public class Info_fireDepData {
    private boolean isCheck;
    private String lat, lng, name;

    public Info_fireDepData(JSONObject data){
        try {
            lat = data.getString("GPS TWD67 X座標");
            lng = data.getString("GPS TWD67 Y座標");
            name = data.getString("單位名稱");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCheck = false;
    }

    public String getLat() { return lat; }
    public String getLng() { return lng; }
    public String getName() {return name;}
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }
}
