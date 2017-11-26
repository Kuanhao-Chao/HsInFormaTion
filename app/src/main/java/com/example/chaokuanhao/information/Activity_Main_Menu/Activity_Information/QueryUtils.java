package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Objects;

/**
 * Created by user on 2017/11/25.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String _url = "http://114.34.123.174:8080/emerg";
    private static boolean _isOK;

    private static JSONArray _AED, _fireDep, _police, _hydrant, _emergPalce, _monitor;



    public QueryUtils(){

    }

    public void run(){
        _isOK = false;
        HttpAsyncTask thread = new HttpAsyncTask();
        thread.execute();
    }

    public static boolean isOK() {return _isOK; };
    public static JSONArray getFireDep(){ return _isOK ? _fireDep : null ;}
    public static JSONArray getAED(){ return _isOK ? _AED : null ;}
    public static JSONArray getPolice(){ return _isOK ? _police : null ;}
    public static JSONArray getHydrant(){ return _isOK ? _hydrant : null ;}
    public static JSONArray getEmergPalce(){ return _isOK ? _emergPalce : null ;}
    public static JSONArray getMonitor(){ return _isOK ? _monitor : null ;}


    private  static URL createUrl (String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHTTPRequest() throws IOException {
        Log.v("MakeHttp", "Starting reading network");
        URL url = createUrl(_url);
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000);
            urlConnection.setConnectTimeout(150000);
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
            Log.e(LOG_TAG, "Problem retrieving from the earthquake JSON results", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        Log.v("MakeHttp", "Stop reading network");
        return jsonResponse;
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

    private class HttpAsyncTask extends AsyncTask<Void, Boolean, String> {

        @Override
        protected String doInBackground(Void... v){

            String response = null;
            try {
                response = makeHTTPRequest();
            } catch (IOException e) {
                e.printStackTrace();
                return "Internet";
            }
            Log.v("HttpAsyncTask", response);

            if(response.equals(""))return "Internet";

            return response;

        }

        @Override
        protected void onPostExecute(String ans) {
            super.onPostExecute(ans);
            if(Objects.equals(ans, "Internet"))retry();
            else{
                try {
                    JSONObject root = new JSONObject(ans);
                    _hydrant = root.getJSONArray("hydrant");
                    _fireDep = root.getJSONArray("fireDep");
                    _police = root.getJSONArray("police");
                    _AED = root.getJSONArray("AED");
                    _monitor = root.getJSONArray("monitor");
                    _emergPalce = root.getJSONArray("emergPlace");
                    _isOK = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    retry();
                }
            }
        }

        private void retry(){
            Log.v("Query", "Retrying");
            HttpAsyncTask temp = new HttpAsyncTask();
            temp.execute();
        }
    }
}
