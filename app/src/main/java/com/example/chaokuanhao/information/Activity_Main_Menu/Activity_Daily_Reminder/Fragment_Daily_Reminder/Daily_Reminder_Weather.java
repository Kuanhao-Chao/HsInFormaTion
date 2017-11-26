package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chaokuanhao.information.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chaokuanhao on 22/11/2017.
 */

public final class Daily_Reminder_Weather extends Fragment implements ISlideBackgroundColorHolder {
    private LinearLayout layoutcontainer;
    private WeatherParameter weather;
    private ImageView img;
    private TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_reminder_weather, container, false);
        layoutcontainer = (LinearLayout) view.findViewById(R.id.daily_reminder_weather);
        img = (ImageView) view.findViewById(R.id.daily_reminder_weather_image);
        text = (TextView) view.findViewById(R.id.daily_reminder_weather_description);
        new WeatherAsynctask().execute("http://114.34.123.174:8080/weather");
        return view;
    }

    private void setView() {
        String toHtml= "<a href='http://www.cwb.gov.tw/V7/'> ";
        switch (weather.getWeatherCondition()){
            case "Clear":
                img.setImageResource(R.drawable.sun);
                toHtml += " 晴朗，氣溫";
                break;
            case "Clouds":
                img.setImageResource(R.drawable.rain_and_sun);
                toHtml += " 多雲，氣溫";
                break;
            case "Rain":
                img.setImageResource(R.drawable.rain);
                toHtml += " 雨天，氣溫";
                break;
            default:
                img.setImageResource(R.drawable.rain_and_sun);
                toHtml += " 氣溫";
                break;
        }
        toHtml += (weather.getTemparature() + "度 </a>");
        text.setText(Html.fromHtml(toHtml));
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        return Color.parseColor("#8042F4");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        // Set the background color of the view within your slide to which the transition should be applied.
        if (layoutcontainer!= null) {
            layoutcontainer.setBackgroundColor(backgroundColor);
        }
    }

    private class WeatherAsynctask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                setJsontoWeather(result);
                setView();
                text.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private void setJsontoWeather(String result) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date cur = new Date(System.currentTimeMillis()), temp;
            Long currentime = cur.getTime(), temptime;
            JSONObject timeblock;
            try{
                JSONObject txt = new JSONObject(result);
                JSONArray listarray = txt.getJSONArray("list");
                for(int i = 0; i < listarray.length(); i++){
                    JSONObject timeObj = listarray.getJSONObject(i);
                    temp = formatter.parse(timeObj.getString("dt_txt"));;
                    temptime = temp.getTime();
                    if( temptime <= currentime && ( currentime - temptime ) / (1000*60*60) <= 3 ){
                        String temparature = String.format("%.1f",new Double(timeObj.getJSONObject("main").getDouble("temp")).doubleValue());
                        String condition = timeObj.getJSONArray("weather").getJSONObject(0).getString("main");
                        weather = new WeatherParameter(condition, temparature);
                        break;
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

        }
    }
}


