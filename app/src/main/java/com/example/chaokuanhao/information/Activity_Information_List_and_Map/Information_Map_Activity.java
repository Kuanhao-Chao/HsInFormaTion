package com.example.chaokuanhao.information.Activity_Information_List_and_Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.chaokuanhao.information.R;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class Information_Map_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_list_child:
                    Intent intent = new Intent();
                    intent.setClass( Information_Map_Activity.this, Information_List_Activity.class);
                    startActivity(intent);
                    return true;

                case R.id.bottom_navigation_map_child:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_map);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_map);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Called when an item in the navigation menu is selected.~~ the main menu !!
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.activity_main_menu_ic_emergencyInformation){

        }
        else if ( id == R.id.activity_main_menu_transportationInformation){

        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
