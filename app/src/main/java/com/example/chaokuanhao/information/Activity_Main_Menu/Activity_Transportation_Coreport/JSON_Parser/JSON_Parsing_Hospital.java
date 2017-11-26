package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_AED;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Hospital;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Howard on 2017/7/18.
 */

public class JSON_Parsing_Hospital {

    public JSON_Parsing_Hospital(){
    }
    public static ArrayList<Parameter_Hospital> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("hospital.json");
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
        ArrayList<Parameter_Hospital> Hospital_list = new ArrayList<Parameter_Hospital>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray hospital = root.getJSONArray("hospital");
            for (int i=0; i < hospital.length(); i++){
                try {
                    JSONObject elementsInWrapper = hospital.getJSONObject(i);

                    String a = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String b = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String c = elementsInWrapper.getString("\u5730\u5740");
//                    Log.d("Howard~~", name_fireDep);
                    String d = elementsInWrapper.getString("\u9023\u7d50\u7db2\u5740");
                    String e = elementsInWrapper.getString("\u91ab\u7642\u6a5f\u69cb");
                    String f = elementsInWrapper.getString("\u96fb\u8a71");

                    Parameter_Hospital Hospital = new Parameter_Hospital( a, b, c, d, e, f);

                    Hospital_list.add(Hospital);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return Hospital_list;
    }
}
