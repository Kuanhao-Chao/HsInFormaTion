package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.text.TextUtils;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Howard on 2017/7/15.
 */

//the helper function relating to requesting nad receiving the data from Azure
public final class QueryUtils_FireDep {

    private static final String LOG_TAG = QueryUtils_FireDep.class.getSimpleName();
    private QueryUtils_FireDep(){
    }

    public static List<Parameter_FireDep> request_FireDep_Point(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHTTPRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Parameter_FireDep> parameter_FireDep = extractFeatureFormJson(jsonResponse);
        return  parameter_FireDep;
    }

    private  static  URL createUrl (String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000000);
            urlConnection.setConnectTimeout(1500000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code:"+ urlConnection.getResponseCode());
            }
        }
        catch (IOException e ){
            Log.e(LOG_TAG, "Problem retrieving from the JSON results", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static List<Parameter_FireDep> extractFeatureFormJson (String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<Parameter_FireDep> FireDep = new ArrayList<Parameter_FireDep>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray fireDep = root.getJSONArray("fireDep");
            for ( int i = 0; i < fireDep.length(); i++ ){
                JSONObject elementsWrapper = fireDep.getJSONObject(i);
                String fire_lat = elementsWrapper.getString("GPS TWD67 X\u5ea7\u6a19");
                String fire_lng = elementsWrapper.getString("GPS TWD67 Y\u5ea7\u6a19");
                String fire_name = elementsWrapper.getString("\u55ae\u4f4d\u540d\u7a31");
                Parameter_FireDep parameter_FireDep = new Parameter_FireDep( fire_lat, fire_lng, fire_name);
                FireDep.add(parameter_FireDep);
            }
            Log.d(LOG_TAG, "\n Successful in paring JSON file \n");
//            List<Parameter_Police> Police = new ArrayList<Parameter_Police>();
//            JSONArray police = root.getJSONArray("police");
//            for ( int i = 0; i < police.length(); i++ ){
//                JSONObject elementsWrapper = fireDep.getJSONObject(i);
//                String police_lat = elementsWrapper.getString("lat");
//                String police_lng = elementsWrapper.getString("lng");
//                String police_name = elementsWrapper.getString("單位");
//                String police_address = elementsWrapper.getString("地址");
//                String police_zipCode = elementsWrapper.getString("郵遞區號");
//                String police_phone = elementsWrapper.getString("電話");
//                Parameter_Police parameter_Police = new Parameter_Police( police_lat, police_lng, police_name, police_address, police_zipCode, police_phone);
//                Police.add(parameter_Police);
//            }

        }catch (JSONException e ){
            Log.e(LOG_TAG, "Problem parsing the JSON results" + e);
        }
        return FireDep;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while ( line !=  null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return  output.toString();
    }
}
