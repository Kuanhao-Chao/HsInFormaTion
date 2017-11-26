package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Activity_Daily_Reminder_Intro;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_AED;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_EmergencyPlace;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_FireDep;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_Hospital;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_Hydrant;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_Police;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.JSON_Parser.JSON_Parsing_RuleShut;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_AED;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Emergency_Place;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Hospital;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Hydrant;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Monitor;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Police;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Ruleshut;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.Place_AutoComplete_Adapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Transportation_Coreport_Map;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.Adapter_PlaceInfo;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.PlaceInfo;
import com.example.chaokuanhao.information.R;
import com.example.chaokuanhao.information.Utils.BottomNavigationViewHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class Information_Map_Activity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private static final String TAG = Information_Map_Activity.class.getSimpleName();
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = Information_Map_Activity.this;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mMainMenu;
    private DrawerLayout mDrawer;
    private ImageView mPolice, mFireDep, mEmergencyPlace, mHydrant, mAED, mMonitor, mRuleShot, mHospital;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Place_AutoComplete_Adapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;


    private static List<Parameter_FireDep> FireDep_Result = new ArrayList<Parameter_FireDep>();
    private static List<Parameter_Police> Police_Result = new ArrayList<Parameter_Police>();
    private static List<Parameter_Emergency_Place> EmergencyPlace_Result = new ArrayList<Parameter_Emergency_Place>();
    private static List<Parameter_Hydrant> Hydrant_Result = new ArrayList<Parameter_Hydrant>();
    private static List<Parameter_AED>  AED_Result = new ArrayList<Parameter_AED>();
    private static List<Parameter_Monitor> Monitor_Result = new ArrayList<Parameter_Monitor>();
    private static List<Parameter_Ruleshut> Ruleshot_Result = new ArrayList<Parameter_Ruleshut>();
    private static List<Parameter_Hospital> Hospital_Result = new ArrayList<Parameter_Hospital>();

    private JSON_Parsing_FireDep json_parsing_fireDep = new JSON_Parsing_FireDep();
    private JSON_Parsing_Police json_parsing_police = new JSON_Parsing_Police();
    private JSON_Parsing_EmergencyPlace json_parsing_emergencyPlace = new JSON_Parsing_EmergencyPlace();
    private JSON_Parsing_Hydrant json_parsing_hydrant = new JSON_Parsing_Hydrant();
    private JSON_Parsing_AED json_parsing_aed = new JSON_Parsing_AED();
    private JSON_Parsing_RuleShut json_parsing_ruleShut = new JSON_Parsing_RuleShut();
    private JSON_Parsing_Hospital json_parsing_hospital = new JSON_Parsing_Hospital();

    private static boolean FireDep_Buttom_is_clicked = false;
    private static boolean Police_Buttom_is_clicked = false;
    private static boolean Accident_Buttom_is_clicked = false;
    private static boolean EmergencyPlace_Buttom_is_clicked = false;
    private static boolean Hydrant_Buttom_is_clicked = false;
    private static boolean AED_Buttom_is_clicked = false;
    private static boolean Monitor_Buttom_is_clicked = false;
    private static boolean RuleShot_Buttom_is_clicked = false;
    private static boolean Hospital_Buttom_is_clicked = false;

    private List<Marker> markers_fireDep = new ArrayList<Marker>();
    private List<Marker> markers_police = new ArrayList<Marker>();
    private List<Marker> markers_accident_point = new ArrayList<Marker>();
    private List<Marker> markers_emergency_place_point = new ArrayList<Marker>();
    private List<Marker> markers_hydrant_point = new ArrayList<Marker>();
    private List<Marker> markers_aed_point = new ArrayList<Marker>();
    private List<Marker> markers_monitor_point = new ArrayList<Marker>();
    private List<Marker> markers_ruleshot_point = new ArrayList<Marker>();
    private List<Marker> markers_hospital_point = new ArrayList<Marker>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_information_map);

        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_action_gps);
        mInfo = (ImageView) findViewById(R.id.place_info);
//        mPlacePicker = (ImageView) findViewById( R.id.place_picker);
        mMainMenu = (ImageView) findViewById( R.id.main_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mPolice = (ImageView) findViewById(R.id.police_station);
        mFireDep = (ImageView) findViewById(R.id.fireDep);
        mEmergencyPlace = (ImageView) findViewById(R.id.emergencyPlace);
        mHydrant = (ImageView) findViewById( R.id.hydrant);
        mAED = (ImageView) findViewById(R.id.AED);
        mMonitor = (ImageView) findViewById(R.id.monitor);
        mRuleShot = (ImageView) findViewById( R.id.ruleshot);
        mHospital = (ImageView) findViewById(R.id.hospital);

        Police_Result = json_parsing_police.extractJsonfrom(mContext);
        FireDep_Result = json_parsing_fireDep.extractJsonfrom(mContext);
        EmergencyPlace_Result = json_parsing_emergencyPlace.extractJsonfrom(mContext);
        Hydrant_Result = json_parsing_hydrant.extractJsonfrom(mContext);
        AED_Result = json_parsing_aed.extractJsonfrom(mContext);
//        Monitor_Result =
        Ruleshot_Result = json_parsing_ruleShut.extractJsonfrom(mContext);
        Hospital_Result = json_parsing_hospital.extractJsonfrom(mContext);

        getLocationPermission();


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_map);         // this one is for old version
//        setSupportActionBar(toolbar);
        // so that toolbar can replace the function of actionbar.
        // return an actionbar that can control the given toolbar
//        toolbar.setTitle("新竹市地圖");
        //~~Important ! Manifest > java > xml

        setupBottomNavigationView();


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//         this one is find the main menu

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    /**
//     * This function is to create the main menu on the toolbar. It is called option menu.
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_additional, menu);
//        return true;
//    }
//
//    /**
//     * This function is to send the MenuItem to the menu.
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_additional_setting) {
//            return true;
//        }
//        if ( id == R.id.menu_additional_rateUs){
//            return true;
//        }
//        if ( id == R.id.menu_additional_help){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


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
        else if ( id == R.id.activity_main_menu_daily_reminder){
            Intent intent = new Intent();
            intent.setClass( Information_Map_Activity.this, Activity_Daily_Reminder_Intro.class);
            startActivity(intent);
        }
        else if ( id == R.id.activity_main_menu_transportation_coreport){
            Intent intent = new Intent();
            intent.setClass( Information_Map_Activity.this, Transportation_Coreport_Map.class);
            startActivity(intent);
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * this function is to control the bottom navigationview for Information_activity!! called in Oncreate
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper bottomNavigationViewHelper = null;
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        bottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    /**
     * ---------------------------  Map related function ------------------------------
     */

    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new Place_AutoComplete_Adapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{
                        Log.d(TAG, "onClick: place info: " + mPlace.toString());
                        mMarker.showInfoWindow();
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException: " + e.getMessage() );
                }
            }
        });

        mMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked main menu");
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });

        mPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));

                if( Police_Buttom_is_clicked == true ){
                    Police_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById( R.id.police_station_textview);
                    textView.setText("警察局");

                    Toast.makeText( Information_Map_Activity.this, "關起警察局圖例", Toast.LENGTH_SHORT).show();

                }
                else if ( Police_Buttom_is_clicked == false ){
                    Police_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById( R.id.police_station_textview);
                    textView.setText("關閉圖例");

                    Toast.makeText( Information_Map_Activity.this, "打開警察局圖例", Toast.LENGTH_SHORT).show();

                }
                try {
                    if ( Police_Buttom_is_clicked ){

//                        Log.d("Howard ~~~~~" , String.valueOf(Police_Result.size()));
                        for ( int i = 0; i < Police_Result.size(); i++ ){
                            LatLng latLng = new LatLng( Double.parseDouble( Police_Result.get(i).getmPolice_lat()), Double.parseDouble( Police_Result.get(i).getmPolice_lng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(Police_Result.get(i).getmPolice_name())
                                    .snippet( "地址：" + Police_Result.get(i).getmPolice_address() + "\n電話：" + Police_Result.get(i).getmPolice_phone() + "\n郵遞區號：" + Police_Result.get(i).getmPolice_zipCode())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_onmap_police));
                            markers_police.add(mMap.addMarker(markerOptions));
                        }
                    }
                    else{
                        for ( int i = 0; i < markers_police.size(); i++ ){
                            markers_police.get(i).remove();
                        }
                    }
                }
                catch ( NullPointerException e){

                }
            }
        });

        mFireDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~" , String.valueOf(FireDep_Result.size() ));

                if( FireDep_Buttom_is_clicked == true ){
                    FireDep_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.fireDep_textview);
                    textView.setText("消防局");

                    Toast.makeText( Information_Map_Activity.this, "關起消防局圖例", Toast.LENGTH_SHORT).show();

                }
                else if ( FireDep_Buttom_is_clicked == false ){
                    FireDep_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.fireDep_textview);
                    textView.setText("關閉圖例");

                    Toast.makeText( Information_Map_Activity.this, "打開消防局圖例", Toast.LENGTH_SHORT).show();

                }
                try {
                    if ( FireDep_Buttom_is_clicked ){
                        for ( int i = 0; i < FireDep_Result.size(); i++ ){
                            Log.d(TAG, '\n' + FireDep_Result.get(i).getmFireDep_lat() + '\t' +  FireDep_Result.get(i).getmFireDep_lng() + '\n' );
                            LatLng latLng = new LatLng( Double.parseDouble(FireDep_Result.get(i).getmFireDep_lat()), Double.parseDouble(FireDep_Result.get(i).getmFireDep_lng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(FireDep_Result.get(i).getmFireDep_name())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_onmap_firedep));
                            markers_fireDep.add(mMap.addMarker(markerOptions));
                        }
                    }
                    else{
                        for ( int i = 0; i < markers_fireDep.size(); i++ ){
                            markers_fireDep.get(i).remove();
                        }
                    }
                }
                catch ( NullPointerException e){
                }
            }
        });

        mEmergencyPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~" , String.valueOf(EmergencyPlace_Result.size() ));

                if( EmergencyPlace_Buttom_is_clicked == true ){
                    EmergencyPlace_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.emergencyPlace_textview);
                    textView.setText("避難場所");

                    Toast.makeText( Information_Map_Activity.this, "關起緊急避難場所圖例", Toast.LENGTH_SHORT).show();

                }
                else if ( EmergencyPlace_Buttom_is_clicked == false ){
                    EmergencyPlace_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.emergencyPlace_textview);
                    textView.setText("關閉圖例");

                    Toast.makeText( Information_Map_Activity.this, "打開緊急避難場所圖例", Toast.LENGTH_SHORT).show();

                }
                try {
                    if ( EmergencyPlace_Buttom_is_clicked ){
                        for ( int i = 0; i < EmergencyPlace_Result.size(); i++ ){
                            Log.d(TAG, '\n' + EmergencyPlace_Result.get(i).getmLat() + '\t' +  EmergencyPlace_Result.get(i).getMlng() + '\n' );
                            LatLng latLng = new LatLng( Double.parseDouble(EmergencyPlace_Result.get(i).getmLat()), Double.parseDouble(EmergencyPlace_Result.get(i).getMlng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(EmergencyPlace_Result.get(i).getmNameOfTheMainAdapter())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.emergencyplace))
                                    .snippet(
                                              "\n編號：" + EmergencyPlace_Result.get(i).getmNumber()
                                            + "\n地址：" + EmergencyPlace_Result.get(i).getmAddress()
                                            + "\n預估收容人數：" + EmergencyPlace_Result.get(i).getmNumberOfPeople()
                                            + "\n鄉鎮市(區)：" + EmergencyPlace_Result.get(i).getmCountry()
                                            + "\n應收容里別：" + EmergencyPlace_Result.get(i).getmPlaceRegion()
                                            + "\n管理人：" + EmergencyPlace_Result.get(i).getmAdministrator()
                                            + "\n聯絡電話：" + EmergencyPlace_Result.get(i).getmPhone()
                                            + "\n手機：" + EmergencyPlace_Result.get(i).getmCellPhone()
                                    );
                            markers_emergency_place_point.add(mMap.addMarker(markerOptions));
                        }
                    }
                    else{
                        for ( int i = 0; i < markers_emergency_place_point.size(); i++ ){
                            markers_emergency_place_point.get(i).remove();
                        }
                    }
                }
                catch ( NullPointerException e){
                }
            }
        });

        mHydrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~" , String.valueOf(Hydrant_Result.size() ));

                if( Hydrant_Buttom_is_clicked == true ){
                    Hydrant_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.hydrant_textview);
                    textView.setText("消防栓");
                    Toast.makeText( Information_Map_Activity.this, "關起消防栓圖例", Toast.LENGTH_SHORT).show();
                }
                else if ( Hydrant_Buttom_is_clicked == false ){
                    Hydrant_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.hydrant_textview);
                    textView.setText("關閉圖例");

                    Toast.makeText( Information_Map_Activity.this, "打開消防栓圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if ( Hydrant_Buttom_is_clicked ){
                        for ( int i = 0; i < Hydrant_Result.size(); i++ ){
                            Log.d(TAG, '\n' + Hydrant_Result.get(i).getmLat() + '\t' +  Hydrant_Result.get(i).getmLng() + '\n' );
                            LatLng latLng = new LatLng( Double.parseDouble(Hydrant_Result.get(i).getmLat()), Double.parseDouble(Hydrant_Result.get(i).getmLng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(Hydrant_Result.get(i).getmNumber())
                                    .snippet("型式：" + Hydrant_Result.get(i).getmType()
                                            + "\n設置地點：" + Hydrant_Result.get(i).getmPlace())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hydrant));
                            markers_hydrant_point.add(mMap.addMarker(markerOptions));
                        }
                    }
                    else{
                        for ( int i = 0; i < markers_hydrant_point.size(); i++ ){
                            markers_hydrant_point.get(i).remove();
                        }
                    }
                }
                catch ( NullPointerException e){
                }
            }
        });
        mAED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~", String.valueOf(AED_Result.size()));

                if (AED_Buttom_is_clicked == true) {
                    AED_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.aed_textview);
                    textView.setText("AED");
                    Toast.makeText(Information_Map_Activity.this, "關起AED圖例", Toast.LENGTH_SHORT).show();
                } else if (AED_Buttom_is_clicked == false) {
                    AED_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.aed_textview);
                    textView.setText("關閉圖例");
                    Toast.makeText(Information_Map_Activity.this, "打開AED圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (AED_Buttom_is_clicked) {
                        for (int i = 0; i < AED_Result.size(); i++) {
                            Log.d(TAG, '\n' + AED_Result.get(i).getmLat() + '\t' + AED_Result.get(i).getmLng() + '\n');
                            LatLng latLng = new LatLng(Double.parseDouble(AED_Result.get(i).getmLat()), Double.parseDouble(AED_Result.get(i).getmLng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(AED_Result.get(i).getmPlaceName())
                                    .snippet("場所編號：" + AED_Result.get(i).getmPlaceNumber()
                                            + "\n場所地址：" + AED_Result.get(i).getmAddress()
                                            + "\n場所類型：" + AED_Result.get(i).getmPlaceType()
                                            + "\nAED地點描述：" + AED_Result.get(i).getmPlace_Discription()
                                            + "\nAED放置地點：" + AED_Result.get(i).getmPlace()
                                            + "\n代理商名稱：" + AED_Result.get(i).getmSeller()
                                            + "\n設置日期：" + AED_Result.get(i).getmPlaceSetupTime()
                                            + "\n保固期限：" + AED_Result.get(i).getmDeadLine()
                                            + "\n管理員姓名：" + AED_Result.get(i).getmAdminister()
                                            + "\n連絡電話：" + AED_Result.get(i).getmPhone()
                                    )
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.aed_small));
                            markers_aed_point.add(mMap.addMarker(markerOptions));
                        }
                    } else {
                        for (int i = 0; i < markers_aed_point.size(); i++) {
                            markers_aed_point.get(i).remove();
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        });

        mRuleShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~", String.valueOf(Ruleshot_Result.size()));

                if (RuleShot_Buttom_is_clicked == true) {
                    RuleShot_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.ruleshot_textview);
                    textView.setText("測速桿");
                    Toast.makeText(Information_Map_Activity.this, "關起測速桿圖例", Toast.LENGTH_SHORT).show();
                } else if (RuleShot_Buttom_is_clicked == false) {
                    RuleShot_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.ruleshot_textview);
                    textView.setText("關閉圖例");
                    Toast.makeText(Information_Map_Activity.this, "打開測速桿圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (RuleShot_Buttom_is_clicked) {
                        for (int i = 0; i < Ruleshot_Result.size(); i++) {
                            Log.d(TAG, '\n' + Ruleshot_Result.get(i).getmLat() + '\t' + Ruleshot_Result.get(i).getmLng() + '\n');
                            LatLng latLng = new LatLng(Double.parseDouble(Ruleshot_Result.get(i).getmLat()), Double.parseDouble(Ruleshot_Result.get(i).getmLng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(Ruleshot_Result.get(i).getmPlace())
                                    .snippet("場所編號：" + Ruleshot_Result.get(i).getmNumber())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ruleshot_small));
                            markers_ruleshot_point.add(mMap.addMarker(markerOptions));
                        }
                    } else {
                        for (int i = 0; i < markers_ruleshot_point.size(); i++) {
                            markers_ruleshot_point.get(i).remove();
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        });

        mHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));
                Log.d("Howard ~~~~~", String.valueOf(Hospital_Result.size()));

                if (Hospital_Buttom_is_clicked == true) {
                    Hospital_Buttom_is_clicked = false;
                    TextView textView = (TextView) findViewById(R.id.hospital_textview);
                    textView.setText("醫院");
                    Toast.makeText(Information_Map_Activity.this, "關起醫院圖例", Toast.LENGTH_SHORT).show();
                } else if (Hospital_Buttom_is_clicked == false) {
                    Hospital_Buttom_is_clicked = true;
                    TextView textView = (TextView) findViewById(R.id.hospital_textview);
                    textView.setText("關閉圖例");
                    Toast.makeText(Information_Map_Activity.this, "打開醫院圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (Hospital_Buttom_is_clicked) {
                        for (int i = 0; i < Hospital_Result.size(); i++) {
                            Log.d(TAG, '\n' + Hospital_Result.get(i).getmLat() + '\t' + Hospital_Result.get(i).getmLng() + '\n');
                            LatLng latLng = new LatLng(Double.parseDouble(Hospital_Result.get(i).getmLat()), Double.parseDouble(Hospital_Result.get(i).getmLng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(Hospital_Result.get(i).getmName())
                                    .snippet("地址：" + Hospital_Result.get(i).getmAddress()
                                            +"電話：" + Hospital_Result.get(i).getmPhone()
                                            +"網頁：" + Hospital_Result.get(i).getmWeb())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_hospital_small));
                            markers_hospital_point.add(mMap.addMarker(markerOptions));
                        }
                    } else {
                        for (int i = 0; i < markers_hospital_point.size(); i++) {
                            markers_hospital_point.get(i).remove();
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        });

        hideSoftKeyboard();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        if (requestCode == PLACE_PICKER_REQUEST ){
            if ( resultCode == RESULT_OK ){
                Place place = PlacePicker.getPlace( this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Information_Map_Activity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(Information_Map_Activity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Information_Map_Activity.this));

        if(placeInfo != null){
            try{
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);
                mMarker = mMap.addMarker(options);

            }catch (NullPointerException e){
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage() );
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.transportation_coreport_map);

        mapFragment.getMapAsync(Information_Map_Activity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
        --------------------------- google places API autocomplete suggestions -----------------
     */

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);

            places.release();
        }
    };

}

