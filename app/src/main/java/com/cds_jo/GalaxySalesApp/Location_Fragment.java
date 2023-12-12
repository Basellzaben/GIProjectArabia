package com.cds_jo.GalaxySalesApp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location_Fragment extends DialogFragment implements OnMapReadyCallback {
   View form;
        private MapView mapView;
        private GoogleMap map;
@Override
public void onStart(){
        super.onStart();
        if (getDialog() == null)
        return;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom( getDialog().getWindow().getAttributes());
        lp.width =700;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(0);

        }
@Override
public View onCreateView(final LayoutInflater inflater   , ViewGroup container  ,Bundle savestate) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        form = inflater.inflate(R.layout.fragment_location_, container, false);

        getDialog().setTitle("Galaxy International Group");
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER);


        return form;
}

        @Override
        public void onMapReady(GoogleMap googleMap) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(21.422510,39.826168))
                .title("New Marker");
                googleMap.addMarker(marker);

                MapsInitializer.initialize(getActivity());
                //LatLng class is google provided class to get latiude and longitude of location.
                //GpsTracker is helper class to get the details for current location latitude and longitude.
                LatLng location = new LatLng(21.422510,39.826168);
                map = googleMap;
                map.addMarker(new MarkerOptions().position(location).title("Marker position"));
                map.moveCamera(CameraUpdateFactory.newLatLng(location));
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        }
}
