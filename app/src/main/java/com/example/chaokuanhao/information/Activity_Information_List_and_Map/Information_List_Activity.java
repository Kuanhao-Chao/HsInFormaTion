package com.example.chaokuanhao.information.Activity_Information_List_and_Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chaokuanhao.information.Activity_Main_Menu.Transportation;
import com.example.chaokuanhao.information.Fragment_Information.Information_AED_Fragment;
import com.example.chaokuanhao.information.Fragment_Information.Information_Hydrant_Fragment;
import com.example.chaokuanhao.information.Fragment_Information.Information_Police_Station_Fragment;
import com.example.chaokuanhao.information.R;
import com.example.chaokuanhao.information.Utils.SectionPagerAdapter;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class Information_List_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public enum appBarState {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private appBarState mCurrentState = appBarState.IDLE;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_list_child:

                    return true;

                case R.id.bottom_navigation_map_child:
                    Intent intent = new Intent();
                    intent.setClass( Information_List_Activity.this, Information_Map_Activity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_mainPage);
        setSupportActionBar(toolbar);               // this one is for old version

        initInstanceDrawer( );
        setupViewPager();

        // open drawerlayout before find navigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // this one is find the main menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    private void initInstanceDrawer( ){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar_mainPage);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if ( verticalOffset == 0){
////                    Log.d("VerticalOffser: ", String.valueOf(verticalOffset) );
////                    Log.d("getTotalScrollRange: ",String.valueOf(appBarLayout.getTotalScrollRange()) );
//                    if (mCurrentState != appBarState.EXPANDED){
//
//                    }
//                    mCurrentState = appBarState.EXPANDED;
//                    fab.setVisibility(View.VISIBLE);
//                }
//                else if ( Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
////                    Log.d("VerticalOffser: ", String.valueOf(verticalOffset) );
////                    Log.d("getTotalScrollRange: ",String.valueOf(appBarLayout.getTotalScrollRange()) );
//                    if (mCurrentState != appBarState.COLLAPSED){
//
//                    }
//                    mCurrentState = appBarState.COLLAPSED;
//                    fab.setVisibility(View.INVISIBLE);
//                }
//                else{
////                    Log.d("VerticalOffser: ", String.valueOf(verticalOffset) );
////                    Log.d("getTotalScrollRange: ",String.valueOf(appBarLayout.getTotalScrollRange()) );
//                    if (mCurrentState != appBarState.IDLE){
//
//                    }
//                    mCurrentState = appBarState.IDLE;
//                    if ( Math.abs(verticalOffset) <= 200 ){
//                        fab.setVisibility(View.VISIBLE);
//                    }else{
//                        fab.setVisibility(View.INVISIBLE);
//                    }
//                }
//            }
//        });
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout_mainPage);
        collapsingToolbarLayout.setTitle("新竹市資料");
    }

    /**
     * set up the viewPager to add the fragment
     */
    private void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment( new Information_AED_Fragment());
        adapter.addFragment( new Information_Hydrant_Fragment());
        adapter.addFragment( new Information_Police_Station_Fragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_mainPage);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        fab.show();
//                        break;
//                    case 1:
//                        fab.show();
//                        break;
//                    case 2:
//                        fab.show();
//                        break;
//                    default:
//                        fab.hide();
//                        break;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.getTabAt(0).setText("AED");
        tabLayout.getTabAt(1).setText("消防栓");
        tabLayout.getTabAt(2).setText("警察局");
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
            Intent intent = new Intent();
            intent.setClass( Information_List_Activity.this, Transportation.class);
            startActivity(intent);
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupBottomNavigationView(){

    }
}
