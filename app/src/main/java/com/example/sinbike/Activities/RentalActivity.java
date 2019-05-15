package com.example.sinbike.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.BicycleViewModel;
import com.example.sinbike.ViewModels.ParkingLotViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RentalActivity extends AppCompatActivity implements OnClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        LocationListener {

    ImageButton RentQR;
    GoogleMap map;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Location lastLocation;
    Marker currentUserLocationMarker;
    static final int Request_User_Location_Code = 99;

    BicycleViewModel bicycleViewModel;
    List <Bicycle> bicycleList = new ArrayList<>();
    List <Bicycle> tempBicycleList = new ArrayList<>();
    AccountViewModel accountViewModel;
    Account account;
    List<Double> latitude = new ArrayList<>();
    List<Double> longtitude = new ArrayList<>();
    Marker bicycleMarker;
    Map<Marker, List<Bicycle>> markerMap;
    SupportMapFragment mapFragment;
    List<GeoPoint> geoPoint = new ArrayList<>();
    ParkingLotViewModel parkingLotViewModel;
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<GeoPoint> parkingCoordinates = new ArrayList<>();
    List<Double> parkingLotLatitude = new ArrayList<>();
    List<Double> parkingLotLongtitude = new ArrayList<>();
    Marker parkingLotMarker;
    Map<Marker, List<ParkingLot>> parkingLotMarkerMap;



    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_main);

        initViews();
        initViewModel();
        FirebaseApp.initializeApp(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Rent Bicycle");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentalActivity.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //geoPoint.clear();
       // parkingLotLatitude.clear();
        //parkingLotLongtitude.clear();
        //parkingCoordinates.clear();
        //tempBicycleList.clear();
        //bicycleList.clear();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

       mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerMap = new HashMap<Marker, List<Bicycle>>();


        bicycleViewModel.getAllBicycles().removeObservers(this);
        bicycleViewModel.getAllBicycles().observe(this, (Resource<List<Bicycle>> listResource) -> {
            bicycleList = listResource.data();
            for(int y=0; y < bicycleList.size();y++){
                tempBicycleList.add(bicycleList.get(y));
               // geoPoint.add(bicycleList.get(y).getCoordinate());

                //latitude.add(geoPoint.get(y).getLatitude());
                //longtitude.add(geoPoint.get(y).getLongitude());
            }
            addBicycleMarker(bicycleList);
        });

        parkingLotMarkerMap = new HashMap<Marker, List<ParkingLot>>();

        parkingLotViewModel.getAllParkinglot().removeObservers(this);
        parkingLotViewModel.getAllParkinglot().observe(this, new Observer<Resource<List<ParkingLot>>>() {
            @Override
            public void onChanged(Resource<List<ParkingLot>> listResource) {
                parkingLots = listResource.data();
                for(int s = 0; s<parkingLots.size(); s++){
                    parkingCoordinates.add(parkingLots.get(s).getAddress());
                    parkingLotLatitude.add(parkingCoordinates.get(s).getLatitude());
                    parkingLotLongtitude.add(parkingCoordinates.get(s).getLongitude());
                }
                addParkingLotMarker(parkingLots);
            }
        });

        RentQR.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentalActivity.this, RentalBarcodeActivity.class));
            }
        });
    }

    private void initViews() {
        RentQR = findViewById(R.id.RentQR);
        RentQR.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));
            map.getUiSettings().setMapToolbarEnabled(false);

            if (!success) {
                Log.e("MainActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MainActivity", "Can't find style. Error: ", e);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();

            map.setMyLocationEnabled(true);
        }
        ReservationClickerAdapter markerInfoWindowAdapter = new ReservationClickerAdapter(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        googleMap.setOnInfoWindowClickListener(this::onInfoWindowClick);
    }

    public void initViewModel(){
        this.bicycleViewModel = ViewModelProviders.of(this).get(BicycleViewModel.class);
        this.bicycleViewModel.setLifecycleOwner(this);
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.parkingLotViewModel = ViewModelProviders.of(this).get(ParkingLotViewModel.class);
        this.parkingLotViewModel.setLifecycleOwner(this);
    }

    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;

        if (currentUserLocationMarker != null)
        {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User Current Location");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_icon));

        currentUserLocationMarker = map.addMarker(markerOptions);

        //  map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
        // map.animateCamera(CameraUpdateFactory.zoomBy(14));



        FirebaseApp.initializeApp(this);

        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(View v) {

    }

   public void addBicycleMarker(List<Bicycle> tempBicycleList) {
        for(int e = 0; e<tempBicycleList.size(); e++) {

                bicycleMarker = this.map.addMarker(new MarkerOptions()
                        .position(new LatLng(tempBicycleList.get(e).getCoordinate().getLatitude(), tempBicycleList.get(e).getCoordinate().getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle_icon)));
        }
        markerMap.put(bicycleMarker, tempBicycleList);
    }

    public void addParkingLotMarker(List<ParkingLot> tempParkingLotList) {
        for(int a = 0; a<tempParkingLotList.size(); a++) {

                parkingLotMarker = this.map.addMarker(new MarkerOptions()
                        .position(new LatLng(tempParkingLotList.get(a).getAddress().getLatitude(), tempParkingLotList.get(a).getAddress().getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_icon)));
        }
        parkingLotMarkerMap.put(parkingLotMarker, tempParkingLotList);
    }

    public boolean onMarkerClick (Marker marker) {
        List<Bicycle> bicycle = markerMap.get(marker);
        Intent intent = new Intent(RentalActivity.this , PopActivity.class);
        startActivity(intent);
        finish();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
       Intent n = new Intent(RentalActivity.this, ReservationPopActivity.class);
       startActivity(n);
    }
}








