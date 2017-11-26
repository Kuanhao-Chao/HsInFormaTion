package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_AED;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Howard on 2017/7/18.
 */

public class JSON_Parsing_AED {

    public JSON_Parsing_AED(){
    }
    public static ArrayList<Parameter_AED> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("aed.json");
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
        ArrayList<Parameter_AED> AED_List = new ArrayList<Parameter_AED>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("AED");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String a = elementsInWrapper.getString("AED\u5730\u9ede\u63cf\u8ff0");
//                    Log.d("Howard~~", lat_fireDep);
                    String b = elementsInWrapper.getString("AED\u653e\u7f6e\u5730\u9ede");
//                    Log.d("Howard~~", lng_fireDep);
                    String c = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", name_fireDep);
                    String d = elementsInWrapper.getString("lng");
                    String e = elementsInWrapper.getString("\u4ee3\u7406\u5546\u540d\u7a31");
                    String f = elementsInWrapper.getString("\u4fdd\u56fa\u671f\u9650");
                    String g = elementsInWrapper.getString("\u5834\u6240\u540d\u7a31");
                    String h = elementsInWrapper.getString("\u5834\u6240\u5730\u5740");
                    String m = elementsInWrapper.getString("\u5834\u6240\u7de8\u865f");
                    String j = elementsInWrapper.getString("\u5834\u6240\u985e\u578b");
                    String k = elementsInWrapper.getString("\u7ba1\u7406\u54e1\u59d3\u540d");
                    String l = elementsInWrapper.getString("\u8a2d\u7f6e\u65e5\u671f");
                    String n = elementsInWrapper.getString("\u9023\u7d61\u96fb\u8a71");



                    Parameter_AED AED = new Parameter_AED( a, b, c, d, e, f, g, h, m, j, k, l, n);

                    AED_List.add(AED);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return AED_List;
    }
}
