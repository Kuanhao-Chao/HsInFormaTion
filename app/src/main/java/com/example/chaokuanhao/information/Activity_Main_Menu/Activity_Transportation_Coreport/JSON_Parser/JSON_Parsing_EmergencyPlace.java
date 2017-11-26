package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Emergency_Place;
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

public class JSON_Parsing_EmergencyPlace {

    public JSON_Parsing_EmergencyPlace(){
    }
    public static ArrayList<Parameter_Emergency_Place> extractJsonfrom(Context context){
        String json = "";
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("emergency_place.json");
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
        ArrayList<Parameter_Emergency_Place> Emergency_Place_List = new ArrayList<Parameter_Emergency_Place>();
        try{
//            json = byteArrayOutputStream.toString();
            JSONObject root = new JSONObject(json);
            JSONArray fireDep = root.getJSONArray("emergPlace");
            for (int i=0; i < fireDep.length(); i++){
                try {
                    JSONObject elementsInWrapper = fireDep.getJSONObject(i);

                    String a = elementsInWrapper.getString("lat");
//                    Log.d("Howard~~", lat_fireDep);
                    String b = elementsInWrapper.getString("lng");
//                    Log.d("Howard~~", lng_fireDep);
                    String c = elementsInWrapper.getString("\u5730\u5740");
//                    Log.d("Howard~~", name_fireDep);
                    String d = elementsInWrapper.getString("\u61c9\u6536\u5bb9\u91cc\u5225");

                    String e = elementsInWrapper.getString("\u624b\u6a5f");
                    String f = elementsInWrapper.getString("\u6536\u5bb9\u5834\u6240\u540d\u7a31");
                    String g = elementsInWrapper.getString("\u7ba1\u7406\u4eba");
                    String h = elementsInWrapper.getString("\u7de8\u865f");
                    String j = elementsInWrapper.getString("\u806f\u7d61\u96fb\u8a71");
                    String k = elementsInWrapper.getString("\u9109\u93ae\u5e02(\u5340)");
                    String l = elementsInWrapper.getString("\u9810\u4f30\u6536\u5bb9\u4eba\u6578");

                    Parameter_Emergency_Place Emergency_Place = new Parameter_Emergency_Place( a, b, c, d, e, f, g, h, j, k,l );
                    Emergency_Place_List.add(Emergency_Place);
                }catch (JSONException e){
                    Log.e("", "", e);
                }
            }
        }
        catch (JSONException e){
            Log.e("SetJSONToArrayList", "Problem parsing the earthquake JSON results", e);
        }
        return Emergency_Place_List;
    }
}
