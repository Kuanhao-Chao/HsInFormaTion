package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Hydrant;
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

public class JSON_Parsing_Hydrant {

    public JSON_Parsing_Hydrant(){
    }
    public static ArrayList<Parameter_Hydrant> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("hydrant.json");
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
        ArrayList<Parameter_Hydrant> Hydrant_list = new ArrayList<Parameter_Hydrant>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("hydrant");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String lat_police = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String lng_police = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String b = elementsInWrapper.getString("\u578b\u5f0f");
//                    Log.d("Howard~~", name_fireDep);
                    String c = elementsInWrapper.getString("\u6d88\u9632\u6813\u7de8\u865f");

                    String d = elementsInWrapper.getString("\u8a2d\u7f6e\u5730\u9ede");

                    Parameter_Hydrant Hydrant = new Parameter_Hydrant( lat_police, lng_police, b, c, d);
                    Hydrant_list.add(Hydrant);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return Hydrant_list;
    }
}
