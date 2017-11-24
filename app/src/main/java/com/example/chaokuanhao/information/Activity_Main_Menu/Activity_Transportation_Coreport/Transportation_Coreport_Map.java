package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Activity_Daily_Reminder_Intro;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_List_Activity;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_Map_Activity;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.Adapter_PlaceInfo;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.PlaceInfo;
import com.example.chaokuanhao.information.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataApi;
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

    private static final String TAG = Transportation_Coreport_Map.class.getCanonicalName();

    private  final Context context = Transportation_Coreport_Map.this;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private enum Report_accident_code {
        PROBLEM_SOLVED,
        CAR_ACCIDENT,
        TRAFFIC_JAM,
        ROAD_BLOCK,
        BIG_ROAD_OBSTRUCTION,
        TRAFFIC_LIGHT_BROKEN,
        WIERD_SMELL,
        FIRE,
        OTHERS,

        NULL;
    }

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker, mMainMenu;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_transportation_coreport);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_action_gps);
        mInfo = (ImageView) findViewById(R.id.place_info);
        mPlacePicker = (ImageView) findViewById( R.id.place_picker);
        mMainMenu = (ImageView) findViewById( R.id.main_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mbottomSheetLayout = (BottomSheetLayout) findViewById(R.id.transportation_coreport_bottomsheet);
        getLocationPermission();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                                        id_to_send = "ic_action_car_accident";
                                        break;
                                    case "車禍":
                                        id_to_send = "ic_action_caraccident";
                                        break;
                                    case"塞車":
                                        id_to_send = "ic_action_traffic_jam";
                                        break;
                                    case "道路封閉":
                                        id_to_send = "ic_action_car_road_block";
                                        break;
                                    case "大型障礙物":
                                        id_to_send = "ic_action_road_big_block";
                                        break;
                                    case "交通號誌故障":
                                        id_to_send = "ic_action_traffic_light_broken";
                                        break;
                                    case "異常臭味":
                                        id_to_send = "ic_action_smelly";
                                        break;
                                    case "火災":
                                        id_to_send = "ic_action_fire";
                                        break;
                                    case "其他":
                                        id_to_send = "ic_action_others";
                                        break;
                                    default:
                                        break;
                                }

                                Log.d( TAG, "Howard"+String.valueOf(item.getItemId()));
                                if (mbottomSheetLayout.isSheetShowing()) {
                                    mbottomSheetLayout.dismissSheet();
                                }
                                // pop up the dialog window!!
                                LatLng latLng_dialog = mPlace.getLatlng();
                                String latitude_dialog = String.valueOf(latLng_dialog.latitude);
                                String longitude_dialog = String.valueOf(latLng_dialog.longitude);
                                String toPass = "( "+ latitude_dialog + ", " + longitude_dialog + " )";
                                Fragment_Dialog_Confirm_Report_Information fragment_dialog_confirm_report_information = new Fragment_Dialog_Confirm_Report_Information().newInstance( latitude_dialog,longitude_dialog, item.getTitle().toString(),id_to_send);
                                fragment_dialog_confirm_report_information.show(getFragmentManager(), "Transportation_coreport_map_popup_dialog_accident_report");

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

    /**
     * to bind the map to the fragment!!
     */
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.transportation_coreport_map);

        mapFragment.getMapAsync(Transportation_Coreport_Map.this);
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
}