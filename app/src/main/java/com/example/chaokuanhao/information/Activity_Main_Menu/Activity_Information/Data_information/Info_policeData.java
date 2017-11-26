package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/26.
 */

public class Info_policeData {
    private boolean isCheck;
    private String lat, lng, name, addr, tel, mailNo;

    public Info_policeData(JSONObject data){
        try {
            lat = data.getString("lat");
            lng = data.getString("lng");
            name = data.getString("單位");
            addr = data.getString("地址");
            tel = data.getString("電話");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCheck = false;

        try {
            mailNo = data.getString("郵遞區號");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLat() { return lat; }
    public String getLng() { return lng; }
    public String getName() {return name;}
    public String getAddr() {return  addr; }
    public String getTel() {return  tel;}
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }

    public String getMailNo() {
        return mailNo;
    }
}
