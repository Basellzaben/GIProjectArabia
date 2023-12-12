package com.cds_jo.GalaxySalesApp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location_Activity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    LatLng sydney;
    Double LatLocation,LngLocation;
    Double LatLocation2,LngLocation2;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Location_Activity.this, JalMasterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);

        sydney = new LatLng(0.00, 0.00);
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
        LatLocation=0.0;
        LngLocation=0.0;
        LatLocation2=0.0;
        LngLocation2=0.0;
        GetlocationNew();

        Button button20=(Button)findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDirections();
            }
        });

        ImageView back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Location_Activity.this, JalMasterActivity.class);
               startActivity(intent);
              finish();

            }
        });


    }
    public void showDirections() {


        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr="+ LatLocation + "," + LngLocation + "&daddr=" + LatLocation2 + "," + LngLocation2));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions().title("").position(sydney));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.animateCamera(zoom);
      //  mMap.setOnMyLocationChangeListener(myLocationChangeListener);


    }
    public void btn_searchCustomer(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("Scr", "ReportInvoiceLOC");
        FragmentManager Manager = getFragmentManager();
        Select_Customer obj = new Select_Customer();
        obj.setArguments(bundle);
        obj.show(Manager, null);



    }


    public void Set_Cust(String longe,String lat,String namee){
        EditText name=(EditText)findViewById(R.id.name);



        try {
            LatLocation2=Double.parseDouble(lat);
            LngLocation2=Double.parseDouble(longe);

            sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(longe));
    mapFragment.getMapAsync(this);
}catch (Exception e){
            sydney = new LatLng(LatLocation, LngLocation);
            Toast.makeText(Location_Activity.this,"لم يتم اضافة موقع العميل",Toast.LENGTH_LONG).show();

        }

        name.setText(namee);
    }
    public void GetlocationNew() {
        String result;
        String address = "";


        try {
            GetLocation mGPSService = new GetLocation();
            Location l =   mGPSService.CurrentLocation(Location_Activity.this);
            double latitude = l.getLatitude();
            double longitude = l.getLongitude();

            LatLocation=l.getLatitude();
            LngLocation=l.getLongitude();


        } catch (Exception ex) {
               }
    }




}