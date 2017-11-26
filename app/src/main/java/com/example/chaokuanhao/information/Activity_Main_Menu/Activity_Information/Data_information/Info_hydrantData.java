package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/25.
 */

public class Info_hydrantData {
    private String lat, lng, type, no, place;
    private boolean isCheck;

    public Info_hydrantData(JSONObject data){
        try {
            lat = data.getString("lat");
            lng = data.getString("lng");
            type = data.getString("型式");
            no = data.getString("消防栓編號");
            place = data.getString("設置地點");
            isCheck = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLat() {return  lat;}
    public String getLng() {return lng; }
    public String getType() {return  type; }
    public String getNo() {return no; }
    public String getPlace() {return place; }
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }
}
