package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/11/26.
 */

public class Info_emergData {
    private boolean isCheck;
    private String lat, lng, name, addr, tel, no, cell;
    private String village, admin, town, capacity;

    public Info_emergData(JSONObject data){
        try {
            lat = data.getString("lat");
            lng = data.getString("lng");
            name = data.getString("收容場所名稱");
            addr = data.getString("地址");
            tel = data.getString("聯絡電話");
            no = data.getString("編號");
            cell = data.getString("手機");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCheck = false;

        try {
            village = data.getString("應收容里別");
            admin = data.getString("管理人");
            town = data.getString("鄉鎮市(區)");
            capacity = data.getString("預估收容人數");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLat() { return lat; }
    public String getLng() { return lng; }
    public String getName() {return name;}
    public String getAddr() {return  addr; }
    public String getTel() {return  tel;}
    public String getNo() { return no;}
    public String getCell() { return cell;}
    public boolean isCheck() {return isCheck; }
    public void setCheck(boolean v) { isCheck = v; }

    public String getAdmin() {
        return admin;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTown() {
        return town;
    }

    public String getVillage() {
        return village;
    }
}
