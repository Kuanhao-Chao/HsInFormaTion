package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.chaokuanhao.information.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by chaokuanhao on 23/11/2017.
 */

public class Adapter_PlaceInfo implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public Adapter_PlaceInfo(Context context){
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.activity_transportation_coreport_custom_info_window, null);
    }

    private void rendorWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.transportation_coreport_custom_info_title);
        if ( ! tvTitle.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.transportation_coreport_custom_info_snippet);
        if ( ! tvSnippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendorWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendorWindowText(marker, mWindow);
        return mWindow;
    }
}
