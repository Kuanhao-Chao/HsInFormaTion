package com.example.chaokuanhao.information.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_List_Activity;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_Map_Activity;
import com.example.chaokuanhao.information.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = BottomNavigationViewHelper.class.getSimpleName();


    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView : Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.bottom_navigation_list_child:
                        Intent intent1 = new Intent(context, Information_List_Activity.class);     //ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;
                    case R.id.bottom_navigation_map_child:
                        Intent intent2 = new Intent(context, Information_Map_Activity.class);     //ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                }
                return false;
            }
        });
    }
}
