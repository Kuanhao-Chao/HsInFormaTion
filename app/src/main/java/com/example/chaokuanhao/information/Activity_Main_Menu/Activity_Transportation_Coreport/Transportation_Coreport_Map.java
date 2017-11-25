package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
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
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_List_Activity;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Dialog_Report_Confirm.Fragment_Dialog_Confirm_Report_Information;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Dialog_Report_Confirm.Fragment_Dialog_Confirm_Report_Others;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Police;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.Adapter_PlaceInfo;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_Accident_Point_Coreport;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Parameter.Parameter_FireDep_Police;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.PlaceInfo;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.Place_AutoComplete_Adapter;
import com.example.chaokuanhao.information.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaokuanhao on 22/11/2017.
 */

public class Transportation_Coreport_Map extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     *---------------------------- Map related function -----------------------------------
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        try{
            if (mLocationPermissionsGranted) {
                getDeviceLocation();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().isCompassEnabled();
                mMap.getUiSettings().setAllGesturesEnabled(true);

                init();
            }
            else{
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                getLocationPermission();
            }
        }
        catch (SecurityException e ){
            Log.e( TAG, "onMapReady: SecurityException: " + e.getMessage());
        }

//        for ( int i = 0; i < FireDep_Result.size(); i++ ){
//
//            LatLng latLng = new LatLng( Double.parseDouble(FireDep_Result.get(i).getmFireDep_lat()), Double.parseDouble(FireDep_Result.get(i).getmFireDep_lng()));
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            mMap.addMarker(markerOptions);
//        }

    }

    /**
     * to bind the map to the fragment!!
     */
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.transportation_coreport_map);

        mapFragment.getMapAsync(Transportation_Coreport_Map.this);
    }


    /**
     * --------------------------- Variable declaration -----------------------------------
     */

    private static final String TAG = Transportation_Coreport_Map.class.getCanonicalName();

    // For Asynctask
    private static final String USGS_REQUEST_URL_REPORT_ACCIDENT = "http://192.168.0.110:5000/bell/";
    private static final String USGS_REQUEST_URL_FIREDEP_POLICE = "http://114.34.123.174:8080/emerg";
    private List<Parameter_Accident_Point_Coreport> Accident_Point_Result = new ArrayList<Parameter_Accident_Point_Coreport>();
    private List<Parameter_FireDep> FireDep_Result = new ArrayList<Parameter_FireDep>();
    private List<Parameter_Police> Police_Result = new ArrayList<Parameter_Police>();
    private JSON_Parsing_FireDep json_parsing_fireDep = new JSON_Parsing_FireDep();
    private JSON_Parsing_Police json_parsing_police = new JSON_Parsing_Police();

    private  final Context context = Transportation_Coreport_Map.this;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Location mLastKnownLocation;
    private Location mCurrentLocation;          // to get the state
    private Location mCameraPosition;           // to get the state

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final float DEFAULT_ZOOM = 15f;
    private int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private enum Report_accident_code {
        CAR_PULL_RANDOMLY(0),
        CAR_ACCIDENT(1),
        TRAFFIC_JAM(2),
        ROAD_BLOCK(3),
        BIG_ROAD_OBSTRUCTION(4),
        TRAFFIC_LIGHT_BROKEN(5),
        WIERD_SMELL(6),
        FIRE(7),
        OTHERS(8),

        NULL(9);

        private final int value;
        private Report_accident_code ( int value){
            this.value = value;
        }
        private int getValue(){
            return value;
        }
    }

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker, mMainMenu, mResync, mFireDep, mPolice;
    private DrawerLayout mDrawer;
    private BottomSheetLayout mbottomSheetLayout;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Place_AutoComplete_Adapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;   // this is to store the data whenever we pin a place!!
    private Marker mMarker;
    private Report_accident_code mReportState = Report_accident_code.NULL;
    private boolean self_scroll_down = true;
    private static boolean FireDep_Buttom_is_clicked = false;
    private static boolean Police_Buttom_is_clicked = false;
    private List<Marker> markers_fireDep = new ArrayList<Marker>();
    private List<Marker> markers_police = new ArrayList<Marker>();

    /**
     *------------------------- essential function override ---------------------------------
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_main_menu_transportation_coreport);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_action_gps);
        mInfo = (ImageView) findViewById(R.id.place_info);
        mPlacePicker = (ImageView) findViewById( R.id.ic_place_picker);
        mMainMenu = (ImageView) findViewById( R.id.main_menu);
        mResync = (ImageView) findViewById( R.id.ic_action_resync);
        mFireDep = (ImageView) findViewById(R.id.ic_action_firedep_icon) ;
        mPolice = (ImageView) findViewById(R.id.ic_action_police_icon);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mbottomSheetLayout = (BottomSheetLayout) findViewById(R.id.transportation_coreport_bottomsheet);
        getLocationPermission();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FireDep_Result = json_parsing_fireDep.extractJsonfrom(context);
        Police_Result = json_parsing_police.extractJsonfrom(context);


//        Log.d(TAG, "\nFireDepAsyncTask: Started.\n");
//        Log.d(TAG, "\nFireDepAsyncTask: URL: " + USGS_REQUEST_URL_FIREDEP_POLICE + '\n');
//        FireDepAsyncTask task01 = new FireDepAsyncTask();
//        task01.execute(USGS_REQUEST_URL_FIREDEP_POLICE);

//        Log.d(TAG, "\nFireDepAsyncTask: URL: " + USGS_REQUEST_URL_FIREDEP_POLICE + '\n');
//        PoliceDepAsyncTask task02 = new PoliceDepAsyncTask();
//        task02.execute(USGS_REQUEST_URL_FIREDEP_POLICE);

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
            Intent intent = new Intent();
            intent.setClass( Transportation_Coreport_Map.this, Information_List_Activity.class);
            startActivity(intent);
        }
        else if ( id == R.id.activity_main_menu_daily_reminder){
            Intent intent = new Intent();
            intent.setClass( Transportation_Coreport_Map.this, Activity_Daily_Reminder_Intro.class);
            startActivity(intent);
        }
        else if ( id == R.id.activity_main_menu_transportation_coreport){

        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * -------------------------  member function define ------------------------------------
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

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Builder for a Place Picker launch intent. ( the new activity for choosing location )
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    startActivityForResult( builder.build(Transportation_Coreport_Map.this), PLACE_PICKER_REQUEST );
                }
                catch ( GooglePlayServicesNotAvailableException e){
                    e.printStackTrace();
                }
                catch ( GooglePlayServicesRepairableException e){
                    e.printStackTrace();
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

        mResync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAsyncTask task01 = new UpdateAsyncTask();
                task01.execute(USGS_REQUEST_URL_REPORT_ACCIDENT);
                // here to reset data on the map !! need to reset all the data to the origin
                mReportState = Report_accident_code.NULL;
                //map  redraw
                // curser to the origin point
                // clear the node!!!
            }
        });

        mFireDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MarkerOptions markerOptions1 = new MarkerOptions();
//                markerOptions1.position( new LatLng(25.009501, 121.424763));
//                mMap.addMarker( markerOptions1);

                Log.d("Howard ~~~~~" , String.valueOf(FireDep_Result.size() ));

                if( FireDep_Buttom_is_clicked == true ){
                    FireDep_Buttom_is_clicked = false;
                    Toast.makeText( Transportation_Coreport_Map.this, "關起消防局圖例", Toast.LENGTH_SHORT).show();
                }
                else if ( FireDep_Buttom_is_clicked == false ){
                    FireDep_Buttom_is_clicked = true;
                    Toast.makeText( Transportation_Coreport_Map.this, "打開消防局圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if ( FireDep_Buttom_is_clicked ){
                        for ( int i = 0; i < FireDep_Result.size(); i++ ){
                            Log.d(TAG, '\n' + FireDep_Result.get(i).getmFireDep_lat() + '\t' +  FireDep_Result.get(i).getmFireDep_lng() + '\n' );
                            LatLng latLng = new LatLng( Double.parseDouble(FireDep_Result.get(i).getmFireDep_lat()), Double.parseDouble(FireDep_Result.get(i).getmFireDep_lng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
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

        mPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Police_Buttom_is_clicked == true ){
                    Police_Buttom_is_clicked = false;
                    Toast.makeText( Transportation_Coreport_Map.this, "關起警察局圖例", Toast.LENGTH_SHORT).show();
                }
                else if ( Police_Buttom_is_clicked == false ){
                    Police_Buttom_is_clicked = true;
                    Toast.makeText( Transportation_Coreport_Map.this, "打開警察局圖例", Toast.LENGTH_SHORT).show();
                }
                try {
                    if ( Police_Buttom_is_clicked ){

//                        Log.d("Howard ~~~~~" , String.valueOf(Police_Result.size()));
                        for ( int i = 0; i < Police_Result.size(); i++ ){
                            LatLng latLng = new LatLng( Double.parseDouble( Police_Result.get(i).getmPolice_lat()), Double.parseDouble( Police_Result.get(i).getmPolice_lng()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng)
                                    .title(Police_Result.get(i).getmPolice_name())
                                    .snippet( "地址：" + Police_Result.get(i).getmPolice_address() + "\n電話：" + Police_Result.get(i).getmPolice_phone() + "\n郵遞區號：" + Police_Result.get(i).getmPolice_zipCode());
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

        hideSoftKeyboard();
    }

    /**
     * parent activity is linked with many child activities.
     * When go back from child activity to parent activity with some data, then we need to use onActivityResult!!
     * @param requestCode : to make sure the returned data is from which Activity!! In this case PLACE_PICKER_REQUEST is the request code!!!
     * @param resultCode : the child activity returned resultCode by setResult
     * @param data : the data that are going to return back to the parent activity!!
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        if (requestCode == PLACE_PICKER_REQUEST ){
            if ( resultCode == RESULT_OK ){
                // PlacePicker.getPlace() is api function to get the location of the place picker!!!
                // We store the return data in place !!
                // Place object to store the place information!!! api object!!
                Place place = PlacePicker.getPlace( this, data);
                final String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();

                // Place.getId() ==> to get the ID by place
                // So after we go back from the place slection to the map view, data in mPlace will be updated!!
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                // now the data is updated~~

                // this one is to popup the bottom view by the selfdesigned view
//                mbottomSheetLayout.showWithSheetView(LayoutInflater.from(context).inflate(R.layout.activity_transportation_coreport_map_bottomsheet, mbottomSheetLayout, false));

                mbottomSheetLayout.addOnSheetDismissedListener(new OnSheetDismissedListener() {
                    @Override
                    public void onDismissed(BottomSheetLayout bottomSheetLayout) {

                        if ( self_scroll_down ){
                            Toast.makeText(Transportation_Coreport_Map.this, "回報失敗，請重新點選", Toast.LENGTH_LONG).show();
                            // to reset the report accident
                            mReportState = Report_accident_code.NULL;
                        }
                        else{
                            //Scroll down without doing anything!!
//                            self_scroll_down = true;
                        }

                    }
                });

//                mbottomSheetLayout.addOnSheetDismissedListener( onSheetDismissedListener );

//                mbottomSheetLayout.setTouchscreenBlocksFocus(false);
//                mbottomSheetLayout.scr
                MenuSheetView menuSheetView =
                        new MenuSheetView(Transportation_Coreport_Map.this, MenuSheetView.MenuType.GRID, "請選擇下列事項回報", new MenuSheetView.OnMenuItemClickListener() {

//                            /**
//                             * Adds an {@link OnSheetDismissedListener} which will be notified when the state of the presented sheet changes.
//                             *
//                             * @param onSheetDismissedListener the listener to be notified.
//                             */
//                            @Override
//                            public void addOnSheetDismissedListener(@NonNull OnSheetDismissedListener onSheetDismissedListener){
//
//                            }
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                self_scroll_down = false;
                                Toast.makeText(Transportation_Coreport_Map.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                String id_to_send = null;

                                switch (item.getTitle().toString()){
                                    case "違規停車":
                                        mReportState = Report_accident_code.CAR_PULL_RANDOMLY;
                                        break;
                                    case "車禍":
                                        mReportState = Report_accident_code.CAR_ACCIDENT;
                                        break;
                                    case"塞車":
                                        mReportState = Report_accident_code.TRAFFIC_JAM;
                                        break;
                                    case "道路封閉":
                                        mReportState = Report_accident_code.ROAD_BLOCK;
                                        break;
                                    case "大型障礙物":
                                        mReportState = Report_accident_code.BIG_ROAD_OBSTRUCTION;
                                        break;
                                    case "交通號誌故障":
                                        mReportState = Report_accident_code.TRAFFIC_LIGHT_BROKEN;
                                        break;
                                    case "異常臭味":
                                        mReportState = Report_accident_code.WIERD_SMELL;
                                        break;
                                    case "火災":
                                        mReportState = Report_accident_code.FIRE;
                                        break;
                                    case "其他":
                                        mReportState = Report_accident_code.OTHERS;;
                                        break;
                                    default:
                                        break;
                                }

                                Log.d( TAG, "Howard"+String.valueOf(item.getItemId()));
                                if (mbottomSheetLayout.isSheetShowing()) {
                                    mbottomSheetLayout.dismissSheet();
                                }

                                // the data that is going to transmit!
                                LatLng latLng_dialog = mPlace.getLatlng();
                                String latitude_dialog = String.valueOf(latLng_dialog.latitude);
                                String longitude_dialog = String.valueOf(latLng_dialog.longitude);
                                String toPass = "( "+ latitude_dialog + ", " + longitude_dialog + " )";

                                if ( mReportState == Report_accident_code.OTHERS ){
                                    Fragment_Dialog_Confirm_Report_Others fragment_dialog_confirm_report_others = new Fragment_Dialog_Confirm_Report_Others().newInstance( latitude_dialog,longitude_dialog, item.getTitle().toString(), mReportState.getValue());
                                    fragment_dialog_confirm_report_others.show( getFragmentManager(), "Transportation_coreport_map_popup_dialog_accident_report_other");
                                }

                                else{
                                    // pop up the dialog window!!

                                    Fragment_Dialog_Confirm_Report_Information fragment_dialog_confirm_report_information = new Fragment_Dialog_Confirm_Report_Information().newInstance( latitude_dialog,longitude_dialog, item.getTitle().toString(), mReportState.getValue());
                                    fragment_dialog_confirm_report_information.show(getFragmentManager(), "Transportation_coreport_map_popup_dialog_accident_report");
                                }

                                return true;
                            }
                        });


                menuSheetView.inflateMenu(R.menu.menu_transportation_coreport_accident_report);
                mbottomSheetLayout.showWithSheetView(menuSheetView);
            }
        }
    }

    /**
     *
     */
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Transportation_Coreport_Map.this);
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

    /**
     * to get the current location of the phone!!!
     */
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
                            mLastKnownLocation = mCurrentLocation = currentLocation;

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(Transportation_Coreport_Map.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    /**
     * to set the center of the map on the phone~
     * This one is to send the PlaceInfo instance to call the function
     * @param latLng
     * @param zoom
     * @param placeInfo
     */
    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new Adapter_PlaceInfo(Transportation_Coreport_Map.this));

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

    /**
     * to set the center of the map on the phone~
     * This one is to send the title String to call the function
     * @param latLng
     * @param zoom
     * @param title
     */
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

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     ** ------------- google places API autocomplete suggestions && Store value in mPlace -----------------
     */

    /**
     * the autocomplete list appears when the use is typing~~ this is to set the onclickListener on these dynamic lists
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            // we get the place by ID when we click the autocomplete list~~
            // And data will be stored in mPlace~~~
            // So after clicked the data of mPlace will be changed!!
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    /**
     * this function is to store the details of place into the PlaceInfo object and return ResultCallback~~
     * Pending Result !! data can be retrived back by two way :
     *      1. await() or await( long, TimeUnit )
     *      2. by passing in an object implementing interface ResultCallBack to setResultCallBack
     */
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
                mPlace.setLatlng(place.getLatLng());                        // important ! to return the Latlng to send the data
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

    /**
     * ------------------------------ Permission related function ---------------------------------
     */
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


    /**
     ** ------------------------------ Asynctask to reload map -------------------------------------
     */
    private class UpdateAsyncTask extends AsyncTask< String, Void, List<Parameter_Accident_Point_Coreport> > {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param urls The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Parameter_Accident_Point_Coreport> doInBackground(String... urls) {

            if ( urls.length < 1 || urls[0] == null ){
                return null;
            }

            Log.d(TAG, "UpdateAsyncTask is activated!!");
            urls[0] = urls[0] + "String";
            Log.d(TAG, "urls[0] is "+ urls[0]);

            Accident_Point_Result = QueryUtils_Request_Accident.request_Accident_Point(urls[0]);

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param placeInfos The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<Parameter_Accident_Point_Coreport> placeInfos) {
            if (Accident_Point_Result != null && !Accident_Point_Result.isEmpty()){
                //do nothing~~ don't need to parse the code!!
            }
        }
    }


    /**
     ** ------------------------------ Asynctask to get firedep and police -------------------------------------
     */
    private class FireDepAsyncTask extends AsyncTask< String, Void, List<Parameter_FireDep> > {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param urls The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Parameter_FireDep> doInBackground(String... urls) {

            if ( urls.length < 1 || urls[0] == null ){
                return null;
            }

            Log.d(TAG, "\nFireDepAsyncTask is activated!!\n");
            Log.d(TAG, "\nurls[0] is "+ urls[0] + '\n');

            FireDep_Result = QueryUtils_FireDep.request_FireDep_Point(urls[0]);

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param data The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<Parameter_FireDep> data ) {
            if (FireDep_Result != null && !FireDep_Result.isEmpty()){
                //do nothing~~ don't need to parse the code!!
            }
        }
    }

    private class PoliceDepAsyncTask extends AsyncTask< String, Void, List<Parameter_Police> > {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param urls The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Parameter_Police> doInBackground(String... urls) {

            if ( urls.length < 1 || urls[0] == null ){
                return null;
            }

            Log.d(TAG, "\nFireDepAsyncTask is activated!!\n");
            Log.d(TAG, "\nurls[0] is "+ urls[0] + '\n');

            Police_Result = QueryUtils_Police.request_Police_Point(urls[0]);

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param data The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<Parameter_Police> data ) {
            if (Police_Result != null && !Police_Result.isEmpty()){
                //do nothing~~ don't need to parse the code!!
            }
        }
    }
}