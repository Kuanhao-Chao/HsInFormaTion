package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.text.TextUtils;
import android.util.Log;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.PlaceInfo;

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
public final class QueryUtils_Report_Accident {
    private static final String LOG_TAG = QueryUtils_Report_Accident.class.getSimpleName();
    private QueryUtils_Report_Accident(){
    }

    public static List<PlaceInfo> fetchBusInfo(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHTTPRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<PlaceInfo> PlaceInfo = extractFeatureFormJson(jsonResponse);
        return  PlaceInfo;
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

    private static List<PlaceInfo> extractFeatureFormJson (String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        return null;
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
