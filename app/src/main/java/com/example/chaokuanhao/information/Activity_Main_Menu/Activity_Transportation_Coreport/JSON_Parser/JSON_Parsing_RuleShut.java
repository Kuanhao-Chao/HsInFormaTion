package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Police;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Ruleshut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Howard on 2017/7/18.
 */

public class JSON_Parsing_RuleShut {

    public JSON_Parsing_RuleShut(){
    }
    public static ArrayList<Parameter_Ruleshut> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("rulshot.json");
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
        ArrayList<Parameter_Ruleshut> RuleShut_List = new ArrayList<Parameter_Ruleshut>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("ruleshot");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String lat_police = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String lng_police = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String a = elementsInWrapper.getString("\u651d\u5f71\u6a5f\u5730\u9ede ");
//                    Log.d("Howard~~", name_fireDep);
                    String b = elementsInWrapper.getString("\u7de8\u865f");


                    Parameter_Ruleshut RuleShut = new Parameter_Ruleshut( lat_police, lng_police, a, b);
                    RuleShut_List.add(RuleShut);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return RuleShut_List;
    }
}
