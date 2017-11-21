package com.example.chaokuanhao.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

import com.example.chaokuanhao.information.Activity_Information_List_and_Map.Information_List_Activity;
import com.example.chaokuanhao.information.Activity_Information_List_and_Map.Information_Map_Activity;

public class MainActivity extends AppCompatActivity {

    // act as blank page if need to modify~~~

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Information_List_Activity.class);
        startActivity(intent);
    }

    /**
     * This function is to create the main menu on the toolbar. It is called option menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }
    /**
     * This function is to send the MenuItem to the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_additional_setting) {
            return true;
        }
        if ( id == R.id.menu_additional_rateUs){
            return true;
        }
        if ( id == R.id.menu_additional_help){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
