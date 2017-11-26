package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/25.
 */

public class Info_AEDData {
    private String placeDetail, placeName, lat, lng, placeAddress, placeWhere;
    private String company, date, no, type, admin, setTime, tel;
    private boolean isCheck;

    public Info_AEDData(JSONObject data){
        try {
            placeWhere = data.getString("AED地點描述");
            placeAddress = data.getString("場所地址");
            placeDetail = data.getString("AED放置地點");
            placeName = data.getString("場所名稱");
            lat = data.getString("lat");
            lng = data.getString("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCheck = false;

        try {
            company = data.getString("代理商名稱");
            date = data.getString("保固期限");
            no = data.getString("場所編號");
            type = data.getString("場所類型");
            admin = data.getString("管理員姓名");
            setTime = data.getString("設置日期");
            tel = data.getString("連絡電話");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getPlaceDetail(){return  placeDetail; }
    public String getPlaceName(){ return  placeName; }
    public String getLat() {return  lat;}
    public String getLng() {return lng; }
    public String getPlaceAddress() {return placeAddress;}
    public String getPlaceWhere() {return placeWhere;}
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }

    public String getAdmin() {
        return admin;
    }

    public String getCompany() {
        return company;
    }

    public String getDate() {
        return date;
    }

    public String getNo() {
        return no;
    }

    public String getSetTime() {
        return setTime;
    }

    public String getType() {
        return type;
    }

    public String getTel() {
        return tel;
    }
}

