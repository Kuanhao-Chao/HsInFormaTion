package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Police;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Howard on 2017/7/18.
 */

public class JSON_Parsing_Police {

    public JSON_Parsing_Police(){
    }
    public static ArrayList<Parameter_Police> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("police_point.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
//        Log.d("Howard~~~", byteArrayOutputStream.toString());
        ArrayList<Parameter_Police> Police_List = new ArrayList<Parameter_Police>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("police");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String lat_police = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String lng_police = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String system_police = elementsInWrapper.getString("\u55ae\u4f4d");
//                    Log.d("Howard~~", name_fireDep);
                    String address_police = elementsInWrapper.getString("\u5730\u5740");

                    String zip_police = elementsInWrapper.getString("\u90f5\u905e\u5340\u865f");
                    String phone_police = elementsInWrapper.getString("\u96fb\u8a71");

                    Parameter_Police Police = new Parameter_Police( lat_police, lng_police, system_police, address_police, zip_police, phone_police);
                    Police_List.add(Police);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return Police_List;
    }
}
