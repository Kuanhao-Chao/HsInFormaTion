package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;
import com.example.chaokuanhao.information.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;


/**
 * Created by Howard on 2017/7/18.
 */

public class JSON_Parsing_FireDep {

    public JSON_Parsing_FireDep(){
    }
    public static ArrayList<Parameter_FireDep> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("firedep_point.json");
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
        ArrayList<Parameter_FireDep> FireDepList = new ArrayList<Parameter_FireDep>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("fireDep");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String lat_fireDep = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String lng_fireDep = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String name_fireDep = elementsInWrapper.getString("\u55ae\u4f4d\u540d\u7a31");
//                    Log.d("Howard~~", name_fireDep);
                    Parameter_FireDep FireDep = new Parameter_FireDep( lat_fireDep, lng_fireDep, name_fireDep);
                    FireDepList.add(FireDep);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return FireDepList;
    }
}
